package devangeliste.MailSenderValidation.model.dto;

public record EmailSimpleDTO(String[] toUsers, String subject, String message) { }
