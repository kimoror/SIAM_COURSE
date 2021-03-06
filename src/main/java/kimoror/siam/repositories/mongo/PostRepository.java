package kimoror.siam.repositories.mongo;

import kimoror.siam.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> getAllByUserId(Long userId);

    List<Post> findAllByCreationDateGreaterThanEqual(Date date);
}
