package com.micro.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Import your DTOs
import com.micro.authservice.dto.AuthDto;
import com.micro.authservice.dto.SignupDto;

// Import your new custom exceptions
import com.micro.authservice.exception.UserAlreadyExistsException;
import com.micro.authservice.exception.UserNotFoundException;
import com.micro.authservice.exception.InvalidCredentialsException;

import com.micro.authservice.repository.AuthRepository;


@Service
public class AuthService {

    private final AuthRepository _AuthRepository;
    private final PasswordEncoder _PasswordEncoder;     

    @Autowired
    AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder)
    {
        this._AuthRepository = authRepository;
        this._PasswordEncoder = passwordEncoder;
    }

    /**
     * Refactored SignUp method.
     * It now returns void and throws an exception on failure.
     *
     * @param cred SignupDto containing user details
     * @throws UserAlreadyExistsException if the email is already in use
     */
    public void SignUp(SignupDto cred) throws UserAlreadyExistsException {
        // Encode the password before doing anything else
        cred.set_Password(_PasswordEncoder.encode(cred.get_Password()));
        
        StringBuffer status = new StringBuffer();
        
        // Attempt to sign up the user via the repository
        boolean isSuccess = _AuthRepository.SignUp(cred, status);
        
        if (!isSuccess) {
            // If the repository failed (e.g., user exists), throw our custom exception.
            // We use the status message from the repository for a detailed error.
            throw new UserAlreadyExistsException("User registration failed: " + status.toString());
        }
        
        // If isSuccess is true, the method just returns normally.
        // The controller will interpret a normal return as a success.
    }

    /**
     * Refactored Authenticate method.
     * It now returns void and throws specific exceptions for "user not found" 
     * or "wrong password".
     *
     * @param cred AuthDto containing login credentials
     * @throws UserNotFoundException if the provided email does not exist
     * @throws InvalidCredentialsException if the email exists but the password is incorrect
     */
    public void Authenticate(AuthDto cred) throws UserNotFoundException, InvalidCredentialsException {
        String email = cred.get_Email();
        String password = cred.get_Password();
        StringBuffer status = new StringBuffer();
        StringBuffer passwordFromDB = new StringBuffer();

        // 1. Check if user even exists by fetching their password hash
        Boolean userExists = _AuthRepository.getPasswordFromEmail(email, passwordFromDB, status);
        
        if (!userExists) {
            // This means the email was not found. Throw UserNotFoundException.
            // The status buffer likely contains "User not found".
            throw new UserNotFoundException("Authentication failed: " + status.toString());
        }

        // 2. If user exists, THEN check the password
        String dbHash = passwordFromDB.toString();
        if (!_PasswordEncoder.matches(password, dbHash)) {
            // The password does not match. Throw InvalidCredentialsException.
            throw new InvalidCredentialsException("Authentication failed: Invalid credentials.");
        }

        // If no exceptions are thrown by this point, authentication is successful.
        // (In a real-world app, this method would now generate and return a JWT token).
    }
}