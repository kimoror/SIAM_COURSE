package kimoror.siam.repositories.mongo;

import kimoror.siam.models.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends MongoRepository<Resume, Long> {
    Optional<Resume> findByUserEmailAndResumeName(String userEmail, String resumeName);
    Optional<List<Resume>> findAllByUserEmail(String userEmail);

    @Query(value="{'userEmail': '?0'}",  fields="{'resumeName': 1}")
    Optional<List<String>> findResumeNamesByEmail (String email);

    void deleteByUserEmailAndResumeName(String userEmail, String resumeName);

    Optional<Resume> findFirstByUserEmailOrderByCreationDateDesc(String userEmail);

}
