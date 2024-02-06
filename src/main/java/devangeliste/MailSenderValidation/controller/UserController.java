package devangeliste.MailSenderValidation.controller;

import devangeliste.MailSenderValidation.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    @Operation(summary = "Find All Users", description = "Search for all users in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users were found in the database."),
            @ApiResponse(responseCode = "404", description = "No users were found in the database.")})
    public ResponseEntity<?> findAll() {
        List<?> users = userService.findAll();
        return (!users.isEmpty()) ? ResponseEntity.ok(users) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
