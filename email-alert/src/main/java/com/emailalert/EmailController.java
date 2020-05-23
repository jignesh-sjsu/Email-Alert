package com.emailalert;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class EmailController {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    @Autowired
    private EmailService emailService;

    @PostMapping("/emailAlert")
    public String emailAlert(@RequestParam(value = "body") String body) {
        try {
            Date date = new Date();
            String subject = sdf.format(date.getTime()) + " ";
            String message = "";

            JSONObject object = new JSONObject(body);
            if (object.has("subject")) {
                try {
                    subject += (String) object.get("subject");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (object.has("message")) {
                try {
                    message = (String) object.get("message");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            emailService.sendMail(CommonConstant.SENDER, CommonConstant.RECEIVER, subject, message, "email-template.vm");
            return new JSONObject().put("success", Boolean.TRUE).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject().put("success", Boolean.FALSE).toString();
        }
    }
}