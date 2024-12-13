package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;





// Use this class to write your request handlers
@Controller
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @GetMapping("/")
    public String getLandingPage(Model model) {
        Notice notice = new Notice();
        model.addAttribute("notice",notice);
        return "notice";
    }

    @PostMapping("/notice")
    public String postForm(@Valid @ModelAttribute("notice") Notice notice,BindingResult result,Model model) throws Exception {
        //TODO: process POST request
        if (result.hasErrors()) {
            return "notice";
        }

        // try {
        //     noticeService.postToNoticeServer(notice);
        //     String responseString = noticeService.saveNoticeResponse(notice);
        //     model.addAttribute("postingid",responseString);
        // } catch (Exception ex) {
        //     // String errorResponse = "I/O error on POST request for http://localhost:4000/notice: Connection refused";
        //     model.addAttribute("errorMessage", ex.getStackTrace());
        //     return "view3";
        // }
        
        ResponseEntity<String> noticeResponse = noticeService.postToNoticeServer(notice);

        //if successful
        if (noticeResponse.getStatusCode().value() == 201 || noticeResponse.getStatusCode().value() == 200) {

            String postingId = noticeService.saveSuccessfulNoticeResponse(notice);

            model.addAttribute("postingid",postingId);
            return "view2";
        } else { // if error
            String errorResponse = noticeResponse.getBody();
            model.addAttribute("errorMessage",errorResponse);
            return "view3";
        }


    }
    
    @GetMapping("/status")
    
    public ModelAndView getHealth() {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());

        try {
            // noticeService.checkHealth();
            mav.setStatus(HttpStatusCode.valueOf(200));
            mav.setViewName("healthy");
            mav.addObject("Status","Healthy");
            
        } catch (Exception ex) {
            mav.setStatus(HttpStatusCode.valueOf(503));
            mav.setViewName("unhealthy");

        }
        return mav;
    }
    
    
}
