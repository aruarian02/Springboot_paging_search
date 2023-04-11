package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.data.dto.GuestbookDTO;
import org.zerock.guestbook.data.dto.PageRequestDTO;
import org.zerock.guestbook.data.dto.PageResultDTO;
import org.zerock.guestbook.data.entity.Guestbook;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookServiceImplTest {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister() {
        GuestbookDTO dto = GuestbookDTO.builder()
                .title("Sample title...")
                .content("Sample content...")
                .writer("user0")
                .build();

        service.register(dto);
    }

    @Test
    public void testList() {
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("한글")
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(requestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("================================");
        for (GuestbookDTO dto : resultDTO.getDtoList()) {
            System.out.println(dto);
        }

        System.out.println("================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

}