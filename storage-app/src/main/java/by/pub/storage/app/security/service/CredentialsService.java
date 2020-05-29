package by.pub.storage.app.security.service;

import by.pub.storage.app.security.entity.Credentials;

public interface CredentialsService {

    boolean checkCredentials(String name, String password);

    Credentials saveCredentials(Credentials credentials);

}
