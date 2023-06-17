package org.ibo.manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {

    /*
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private String port;
    @Qualifier("mailSender")
    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setProtocol("SMTP");
        javaMailSender.setHost(host);
        javaMailSender.setPort(Integer.parseInt(port));
        return javaMailSender;
    }
    */

    private JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl( JavaMailSender emailSender ) {
        this.emailSender = emailSender;
    }


    public void sendEmail( String to, String subject, String text ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply.kdrs@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
