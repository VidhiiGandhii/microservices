package com.micro.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Import your DTOs
import com.micro.authservice.dto.AuthDto;
import com.micro.authservice.dto.ReturnDto;
import com.micro.authservice.dto.SignupDto;

// Import your service
import com.micro.authservice.service.AuthService;

// Import your custom exceptions
import com.micro.authservice.exception.UserAlreadyExistsException;
import com.micro.authservice.exception.UserNotFoundException;
import com.micro.authservice.exception.InvalidCredentialsException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService _AuthService;

    @Autowired
    AuthController(AuthService authService) {
        this._AuthService = authService;
    }

    /**
     * Refactored SignUp endpoint.
     * It now returns a ResponseEntity<ReturnDto> and handles exceptions.
     */
    @PostMapping("/signup")
    public ResponseEntity<ReturnDto> SignUp(@RequestBody SignupDto cred) {
        // This is the response DTO from your professor's example.
        ReturnDto response = new ReturnDto();
        response.set_Email(cred.get_Email()); // Set email for context, even in case of error

        try {
            // --- Basic Input Validation (from Example 2) ---
            if (cred.get_Email() == null || cred.get_Email().isEmpty()) {
                response.set_Status("Bad request: Email cannot be empty");
                return ResponseEntity.badRequest().body(response);
            }
            if (cred.get_Password() == null || cred.get_Password().isEmpty()) {
                response.set_Status("Bad request: Password cannot be empty");
                return ResponseEntity.badRequest().body(response);
            }
            
            // --- Call the service layer ---
            // This method now throws UserAlreadyExistsException on failure
            _AuthService.SignUp(cred);

            // --- Handle Success ---
            // If no exception is thrown, we've succeeded.
            response.set_Status("Success");
            return ResponseEntity.ok(response); // Returns HTTP 200 OK

        } catch (UserAlreadyExistsException ex) {
            // --- Handle Specific Exception ---
            // We caught the exception from the service layer.
            response.set_Status(ex.getMessage());
            // Return HTTP 409 CONFLICT (as per REST standards for "already exists")
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

        } catch (Exception ex) {
            // --- Handle All Other General Errors ---
            // This catches database errors or any other unexpected problems.
            response.set_Status("An internal server error occurred: " + ex.getMessage());
            // Return HTTP 500 INTERNAL_SERVER_ERROR
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Refactored Authenticate (Login) endpoint.
     * It now returns a ResponseEntity<ReturnDto> and handles exceptions.
     */
    @PostMapping("/login")
    public ResponseEntity<ReturnDto> Authenticate(@RequestBody AuthDto cred) {
        ReturnDto response = new ReturnDto();
        response.set_Email(cred.get_Email());

        try {
            // --- Call the service layer ---
            // This method throws UserNotFoundException or InvalidCredentialsException
            _AuthService.Authenticate(cred);
            
            // --- Handle Success ---
            // If no exception is thrown, login is successful.
            response.set_Status("Login successful");
            // (Later, you would add a JWT token to this response)
            return ResponseEntity.ok(response); // Returns HTTP 200 OK

        } catch (UserNotFoundException ex) {
            // --- Handle User Not Found ---
            response.set_Status(ex.getMessage());
            // Return HTTP 404 NOT_FOUND
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (InvalidCredentialsException ex) {
            // --- Handle Wrong Password ---
            response.set_Status(ex.getMessage());
            // Return HTTP 401 UNAUTHORIZED
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        } catch (Exception ex) {
            // --- Handle All Other General Errors ---
            response.set_Status("An internal server error occurred: " + ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}