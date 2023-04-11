package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.zerock.guestbook.data.dto.GuestbookDTO;
import org.zerock.guestbook.data.dto.PageRequestDTO;
import org.zerock.guestbook.data.dto.PageResultDTO;
import org.zerock.guestbook.data.entity.Guestbook;
import org.zerock.guestbook.service.GuestbookService;

import javax.servlet.http.HttpServletResponse;

@RestController
@Log4j2
@RequestMapping(value = "/guestbook")
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping
    public RedirectView index() {
        return new RedirectView("/guestbook/list");
    }

    @GetMapping("/list")
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        log.info("list..........." + requestDTO);
        return service.getList(requestDTO);
    }

    @PostMapping("/register")
    public RedirectView registerPost(GuestbookDTO dto, HttpServletResponse response) {
        log.info("dto...." + dto);
        service.register(dto);
        return new RedirectView("/guestbook/list");
    }

    @GetMapping("/read")
    public GuestbookDTO getRead(long gno, @ModelAttribute PageRequestDTO requestDTO) {
        log.info(gno);
        return service.read(gno);
    }

    @PostMapping("/remove")
    public RedirectView removePost(long gno) {
        service.remove(gno);
        return new RedirectView("/guestbook/list");
    }

    @PostMapping("/modify")
    public RedirectView modifyPost(GuestbookDTO dto, @ModelAttribute PageRequestDTO requestDTO) {
        log.info("post modify..........");
        log.info("dto: " + dto);

        service.modify(dto);

        return new RedirectView("/guestbook/list");
    }
}
