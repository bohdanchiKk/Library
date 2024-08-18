package org.example.library.security;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.library.entity.User;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class UserMailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    private String verificationMailContent(User user){
        Context context = new Context();
        String verificationUrl = String.format("http://localhost:8080/verify?code=%s", user.getVerificationCode());
        context.setVariable("applicationUrl", verificationUrl);
        return templateEngine.process("user-verify", context);
    }

    private MimeMessage createMessage(User user, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setText(content, true);
        message.setSubject("Welcome to MyApp XYZ");
        message.setFrom("noreply@myapp.xyz");
        message.setTo(user.getEmail());
        return mimeMessage;
    }

    public void sendVerificationMail(User user) {
        String content = verificationMailContent(user);
        try {
            mailSender.send(createMessage(user, content));
        } catch (MessagingException ex) {
            throw new MailAuthenticationException("Could not send e-mail to verify user with e-mail '" + user.getEmail() + "'", ex);
        }
    }
}
