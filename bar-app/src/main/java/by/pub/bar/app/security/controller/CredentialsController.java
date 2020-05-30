package by.pub.bar.app.security.controller;

import by.pub.bar.app.security.entity.Credentials;
import by.pub.bar.app.security.service.CredentialsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/credentials")
public class CredentialsController {

    private final CredentialsService credentialsService;

    public CredentialsController(
        CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @PostMapping
    public Credentials saveCredentials(@RequestBody Credentials credentials) {
        return credentialsService.saveCredentials(credentials);
    }
}
