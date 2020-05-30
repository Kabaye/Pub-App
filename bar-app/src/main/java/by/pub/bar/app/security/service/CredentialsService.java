package by.pub.bar.app.security.service;


import by.pub.bar.app.security.entity.Credentials;

public interface CredentialsService {

    boolean checkCredentials(String name, String password);

    Credentials saveCredentials(Credentials credentials);

}
