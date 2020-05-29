package by.pub.storage.app.security.service;

import by.pub.storage.app.security.entity.Credentials;
import by.pub.storage.app.security.repository.CredentialsRepository;
import org.springframework.stereotype.Service;

@Service
public class CredentialsServiceImpl implements CredentialsService {

    private final CredentialsRepository credentialsRepository;

    public CredentialsServiceImpl(
        CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public boolean checkCredentials(String name, String password) {
        return credentialsRepository.findByNameAndPassword(name, password).isPresent();
    }

    @Override
    public Credentials saveCredentials(Credentials credentials) {
        return credentialsRepository.save(credentials);
    }
}
