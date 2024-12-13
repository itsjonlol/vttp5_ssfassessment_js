package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
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

        ResponseEntity<String> noticeResponse = noticeService.postToNoticeServer(notice);

        //if successful
        if (noticeResponse.getStatusCode().value() == 201 || noticeResponse.getStatusCode().value() == 200) {
            //only save the successful notice response
            String postingId = noticeService.saveSuccessfulNoticeResponse(notice);

            model.addAttribute("postingid",postingId);
            return "view2";
        } else { // if error, display the whole error message
            String errorResponse = noticeResponse.getBody();
            model.addAttribute("errorMessage",errorResponse);
            return "view3";
        }

    }
    
    @GetMapping("/status")
    @ResponseBody
    public ResponseEntity<String> getHealth()  {

        try {
            noticeService.checkHealth();
            JsonObject successJson = Json.createObjectBuilder()
                                       .add("status","healthy")
                                       .build();
            return ResponseEntity.status(200).header("Content-Type", "application/json").body(successJson.toString());

        } catch (Exception ex) {
            JsonObject errorJson = Json.createObjectBuilder()
                                       .add("status","not healthy")
                                       .build();
            return ResponseEntity.status(503).header("Content-Type", "application/json").body(errorJson.toString());


        }
        

      
        
    }
    
    
}
