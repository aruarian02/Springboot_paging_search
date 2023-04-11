package org.zerock.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.data.dto.GuestbookDTO;
import org.zerock.guestbook.data.dto.PageRequestDTO;
import org.zerock.guestbook.data.dto.PageResultDTO;
import org.zerock.guestbook.data.entity.Guestbook;
import org.zerock.guestbook.data.entity.QGuestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository;

    @Override
    public void register(GuestbookDTO dto) {
        log.info("DTO--------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);
        log.info(entity);

        repository.save(entity);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        log.info("gno---------" + gno);

        Optional<Guestbook> optGuestbook = repository.findById(gno);
        return optGuestbook.map(entity -> entityToDto(entity)).orElse(null);
    }

    @Override
    public void remove(Long gno) {
        log.info("gno---------" + gno);

        Optional<Guestbook> optGuestbook = repository.findById(gno);

        optGuestbook.ifPresent(guestbook -> {
            repository.deleteById(gno);
        });
    }

    @Override
    public void modify(GuestbookDTO dto) {
        log.info("dto----------" + dto);

        Optional<Guestbook> optGuestbook = repository.findById(dto.getGno());

        optGuestbook.ifPresent(guestbook -> {
            guestbook.changeTitle(dto.getTitle());
            guestbook.changeContent(dto.getContent());

            repository.save(guestbook);
        });
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        Function<Guestbook, GuestbookDTO> fn = entity -> entityToDto(entity);

        return new PageResultDTO<>(result, fn);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = requestDTO.getKeyword();

        // gno > 0 조건만 생성
        BooleanExpression expression = qGuestbook.gno.gt(0L);

        booleanBuilder.and(expression);

        // 검색 조건이 없는 경우
        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        // 검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
