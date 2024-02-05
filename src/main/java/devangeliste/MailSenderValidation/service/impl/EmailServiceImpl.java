package devangeliste.MailSenderValidation.service.impl;

import devangeliste.MailSenderValidation.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${email.sender}")
    private String emailUser;

    @Override
    public void sendSimpleEmail(String[] toUsers, String subject, String message){
        // DEFINIMOS EL OBJETO QUE NOS VA A AYUDAR A CONSTRUIR NUESTRO MENSAJE
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        // PERSONALIZAMOS EL EMAIL
        mailMessage.setFrom(emailUser);
        mailMessage.setTo(toUsers);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        // ENVIAMOS EL CORREO ELECTRONICO
        mailSender.send(mailMessage);
    }

    @Override
    public void sendEmailWithFile(String[] toUsers, String subject, String message, File file){
        try {
            // DEFINIMOS EL OBJETO QUE NOS VA A AYUDAR A CONSTRUIR NUESTRO MENSAJE
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,StandardCharsets.UTF_8.name());

            // PERSONALIZAMOS EL EMAIL
            mimeMessageHelper.setFrom(emailUser);
            mimeMessageHelper.setTo(toUsers);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message);
            mimeMessageHelper.addAttachment(file.getName(), file);

            // ENVIAMOS EL CORREO ELECTRONICO
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email with attachment" + e.getMessage());
        }
    }
}
