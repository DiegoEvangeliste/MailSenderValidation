package devangeliste.MailSenderValidation.model.dto;

import org.springframework.web.multipart.MultipartFile;

public record EmailFileDTO (String[] toUsers, String subject, String message, MultipartFile file) { }
