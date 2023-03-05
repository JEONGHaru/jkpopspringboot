package com.haru.jkpop.controller;

import com.haru.jkpop.dto.BoardDTO;
import com.haru.jkpop.dto.PageRequestDTO;
import com.haru.jkpop.dto.PageResultDTO;
import com.haru.jkpop.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("BoardController list -----------------------------");
        log.info("PageRequestDTo ------------------------- : " + pageRequestDTO);
        model.addAttribute("result",boardService.getList(pageRequestDTO));
    }

    @GetMapping("register")
    public void register(){
        log.info("BoardController register ---------------------------");
    }

    @PostMapping("register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){
        log.info("BoardController registerPost ------------------- dto :" + dto);
        Long bno = boardService.register(dto);
        log.info("BNO : " + bno);
        redirectAttributes.addFlashAttribute("msg",bno);
        return "redirect:list";
    }

    @GetMapping({"read","modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Long bno, Model model){
        log.info("BoardController read --------------------------  bno : " + bno);
        BoardDTO dto = boardService.get(bno);
        log.info("BoardDTO : " + dto);
        model.addAttribute("dto",dto);
    }

    @PostMapping("remove")
    public String remove(Long bno,RedirectAttributes redirectAttributes){
        log.info("BoardController remove --------------------------  bno : " + bno);
        boardService.removeWithReplies(bno);
        redirectAttributes.addFlashAttribute("msg",bno);
        return "redirect:list";
    }

    @PostMapping("modify")
    public String modify(BoardDTO dto,@ModelAttribute("requestDTO") PageRequestDTO requestDTO,
    RedirectAttributes redirectAttributes){
        log.info("BoardController modify --------------------------  dto : " + dto);

        boardService.modify(dto);

        redirectAttributes.addAttribute("bno",dto.getBno());
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        return "redirect:read";

    }
}
