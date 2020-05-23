package com.emailalert;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendMail(String sender, String[] receiver, String subject, String message, String templateName) {
        Mail mail = new Mail();
        mail.setMailFrom(sender);
        mail.setMailTo(receiver);
        mail.setMailSubject(subject);
        mail.setMailContent(message);
        mail.setTemplateName(templateName);
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        Mailer mailer = (Mailer) context.getBean("mailer");
        mailer.sendCustomMail(mail);
    }

    public void sendJobMail(String sender, String[] receiver, ScheduleJob job) {
        Mail mail = new Mail();
        mail.setMailFrom(sender);
        mail.setMailTo(receiver);
        mail.setMailSubject( job.getJobName() + " " + job.getStatus().getStatus());
        if (JobStatus.COMPLETED == job.getStatus()) {
            mail.setTemplateName("confirmed-template.vm");
        } else {
            mail.setTemplateName("failed-template.vm");
        }
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        Mailer mailer = (Mailer) context.getBean("mailer");
        mailer.sendJobMail(mail, job);
    }
}
