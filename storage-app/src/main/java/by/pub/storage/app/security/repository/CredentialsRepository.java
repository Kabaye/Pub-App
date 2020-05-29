package by.pub.storage.app.security.repository;

import by.pub.storage.app.security.entity.Credentials;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CredentialsRepository extends MongoRepository<Credentials, String> {

    Optional<Credentials> findByNameAndPassword(String name, String password);

}

