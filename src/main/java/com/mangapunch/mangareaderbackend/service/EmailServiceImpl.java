package com.mangapunch.mangareaderbackend.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.mangapunch.mangareaderbackend.models.User;

@Component
public class EmailServiceImpl implements EmailService {
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    private UserService userService;

    @Value("${email.hostname}")
    private String emailHostname;

    @Override
    public String sendPasswordChangeEmail(String to) {
        try {
            User user = userService.findByUsernameOrEmail(to, to);
            String link = emailHostname + "/change-password?userid=" + user.getId();
            MimeMessage message = emailSender.createMimeMessage();

            boolean multipart = true;

            MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

            String htmlMsg = "<div>Vui lòng bấm vào link dưới đây để thay đổi mật khẩu</div>"
                    + "<div><a href=" + link + ">" + link + "</a><div>";

            message.setContent(htmlMsg, "text/html; charset=UTF-8");
            helper.setFrom("noreply@mangapunch.xyz");
            helper.setTo(to);
            helper.setSubject("Thay đổi mật khẩu mangapunch");
            this.emailSender.send(message);
            return "Email sent!";
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Email fail!";
    }
}
