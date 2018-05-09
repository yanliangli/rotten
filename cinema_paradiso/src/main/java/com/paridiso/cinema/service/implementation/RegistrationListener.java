package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.User;
import com.paridiso.cinema.event.OnRegistrationCompleteEvent;
import com.paridiso.cinema.security.JwtTokenGenerator;
import com.paridiso.cinema.security.JwtUser;
import com.paridiso.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.UUID;
@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private JwtTokenGenerator generator;

/*
    @Autowired
    private MessageSource messages;
*/
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RegUserServiceImpl userService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        String recipientAddress = user.getEmail();
        System.out.println("Email is "+ user.getEmail());
        String subject = "Cinema Paradiso Registration Confirmation";
        String confirmationUrl
                = "/user/regitrationConfirm?token=" + token;

        //String message = messages.getMessage("message.regSucc", null, event.getLocale());
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        //email.setText(message + " rn" + "http://localhost:8080" + confirmationUrl);
        email.setText("<a href = 'http://localhost:8080" + confirmationUrl+"'>Click To Verify Your Account\n \n Notice: This link will be Invalid in ten minutes!");
        mailSender.send(email);
    }

}
