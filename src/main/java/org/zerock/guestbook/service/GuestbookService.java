package org.zerock.guestbook.service;

import org.zerock.guestbook.data.dto.GuestbookDTO;
import org.zerock.guestbook.data.dto.PageRequestDTO;
import org.zerock.guestbook.data.dto.PageResultDTO;
import org.zerock.guestbook.data.entity.Guestbook;

public interface GuestbookService {

    void register(GuestbookDTO dto);

    GuestbookDTO read(Long gno);

    void remove(Long gno);

    void modify(GuestbookDTO dto);

    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto) {
        return Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
    }

    default GuestbookDTO entityToDto(Guestbook entity) {
        return GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }
}
