package com.tunemerge.tunemerge.Controller;

import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling login-related endpoints.
 *
 * @author Somendra
 */
@RestController
public class LoginController {

    /**
     * Handles user login.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     * @return a String indicating the login status.
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Implement login logic here
        return "Login successful for user: " + username;
    }

    /**
     * Handles user logout.
     *
     * @return a String indicating the logout status.
     */
    @PostMapping("/logout")
    public String logout() {
        // Implement logout logic here
        return "Logout successful";
    }
}