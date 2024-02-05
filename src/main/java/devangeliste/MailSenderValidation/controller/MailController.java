package devangeliste.MailSenderValidation.controller;

import devangeliste.MailSenderValidation.model.dto.EmailFileDTO;
import devangeliste.MailSenderValidation.model.dto.EmailSimpleDTO;
import devangeliste.MailSenderValidation.service.IEmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email")
public class MailController {

    @Autowired
    private IEmailService emailService;

    @PostMapping("/sendSimpleMessage")
    @Operation(summary = "Send simple message by email", description = "Send a text-only email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The email was sent successfully.")})
    public ResponseEntity<Map<String, String>> receiveRequestEmail(@RequestBody EmailSimpleDTO email) {
        emailService.sendSimpleEmail(email.toUsers(), email.subject(), email.message());

        Map<String, String> response = new HashMap<>();
        response.put("estado", "Enviado");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sendMessageFile")
    @Operation(summary = "Send message with file by email", description = "Send an email containing text and attachments.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The email was sent successfully."),
            @ApiResponse(responseCode = "409", description = "Error sending email.")})
    public ResponseEntity<Map<String, String>> receiveRequestEmailWithFile(@ModelAttribute EmailFileDTO email) {
        try {
            String fileName = email.file().getOriginalFilename();

            // DEFINIMOS LA RUTA DONDE VAMOS A GUARDAR EL ARCHIVO Y LO GUARDAMOS EN CASO DE QUE NO EXISTA, SI YA EXISTE LO REEMPLAZAMOS
            Path path = Paths.get("src/main/resources/files/" + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(email.file().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            File file = path.toFile();
            emailService.sendEmailWithFile(email.toUsers(), email.subject(), email.message(), file);

            Map<String, String> response = new HashMap<>();
            response.put("estado", "Enviado");
            response.put("archivo", fileName);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            throw new RuntimeException("Error sending email with attachment" + e.getMessage());
        }
    }
}
