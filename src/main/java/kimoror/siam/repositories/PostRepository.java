package kimoror.siam.repositories;

import kimoror.siam.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, Long> {
    List<Post> getAllByUserId(Long userId);
}
