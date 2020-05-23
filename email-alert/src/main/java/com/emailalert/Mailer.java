package com.emailalert;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.io.StringWriter;

public class Mailer {
    private MailSender mailSender;
    private VelocityEngine velocityEngine;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void sendCustomMail(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(mail.getMailFrom());
        message.setTo(mail.getMailTo());
        message.setSubject(mail.getMailSubject());

        Template template = velocityEngine.getTemplate("./templates/" + mail.getTemplateName());

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("message", mail.getMailContent());

        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);
        message.setText(stringWriter.toString());

        mailSender.send(message);
    }

    public void sendJobMail(Mail mail, ScheduleJob job) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(mail.getMailFrom());
        message.setTo(mail.getMailTo());
        message.setSubject(mail.getMailSubject());

        Template template = velocityEngine.getTemplate("./templates/" + mail.getTemplateName());

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("jobId", job.getJobId());
        velocityContext.put("jobName", job.getJobName());
        velocityContext.put("userName", job.getUserName());
        velocityContext.put("startTimeStamp", job.getStartTimeStamp());
        velocityContext.put("endTimeStamp", job.getEndTimeStamp());
        velocityContext.put("status", job.getStatus().getStatus());

        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);
        message.setText(stringWriter.toString());

        mailSender.send(message);
    }
}
