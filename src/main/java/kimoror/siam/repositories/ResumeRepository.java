package kimoror.siam.repositories;

import kimoror.siam.models.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ResumeRepository extends MongoRepository<Resume, Long> {
    Optional<Resume> findByUserEmailAndResumeName(String userEmail, String resumeName);
}
