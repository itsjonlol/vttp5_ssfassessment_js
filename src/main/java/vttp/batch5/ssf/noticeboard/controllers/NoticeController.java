package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;




// Use this class to write your request handlers
@Controller
public class NoticeController {


    @GetMapping("/")
    public String getLandingPage(Model model) {
        Notice notice = new Notice();
        model.addAttribute("notice",notice);
        return "notice";
    }

    @PostMapping("/notice")
    public String postForm(@Valid @ModelAttribute("notice") Notice notice,BindingResult result,Model model) {
        //TODO: process POST request
        if (result.hasErrors()) {
            return "notice";
        }
        return "redirect:/";
    }
    
    
}
