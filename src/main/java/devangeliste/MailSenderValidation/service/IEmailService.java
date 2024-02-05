package devangeliste.MailSenderValidation.service;

import java.io.File;

public interface IEmailService {
    void sendSimpleEmail(String[] toUsers, String subject, String message);
    void sendEmailWithFile(String[] toUsers, String subject, String message, File file);
}
