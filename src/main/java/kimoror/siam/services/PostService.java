package kimoror.siam.services;

import kimoror.siam.models.Post;
import kimoror.siam.repositories.mongo.PostRepository;
import kimoror.siam.rest.requests.PostRequest;
import kimoror.siam.rest.responses.BaseResponse;
import kimoror.siam.rest.responses.ResponseValues;
import kimoror.siam.rest.responses.respParams.PostResponseParams;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public ResponseEntity<?> createPost(PostRequest postRequest){
        Long userId = userService.getCurrentUserId();

        if(userId == null){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(new BaseResponse<>(ResponseValues.CURRENT_USER_NOT_FOUND.getErrorCode(),
                    ResponseValues.CURRENT_USER_NOT_FOUND.getErrorMessage()), headers, HttpStatus.BAD_GATEWAY);
        }

        Post post = new Post(userId, postRequest.text());

        postRepository.save(post);

        return ResponseEntity.ok(new BaseResponse<>(ResponseValues.SUCCESSES.getErrorCode(),
                ResponseValues.SUCCESSES.getErrorMessage()));
    }

    public ResponseEntity<?> getAllCurrentUserPosts(){
        Long userId = userService.getCurrentUserId();

        if(userId == null){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(new BaseResponse<>(ResponseValues.CURRENT_USER_NOT_FOUND.getErrorCode(),
                    ResponseValues.CURRENT_USER_NOT_FOUND.getErrorMessage()), headers, HttpStatus.BAD_GATEWAY);
        }

        List<Post> posts = postRepository.getAllByUserId(userId);
        List<PostResponseParams> postResponseParamsList = new ArrayList<>();

        for(Post post : posts) {

            postResponseParamsList.add(new PostResponseParams(post.getId(), post.getUserId(),
                    post.getText()));
        }


        return ResponseEntity.ok(new BaseResponse<>(ResponseValues.SUCCESSES.getErrorCode(),
                ResponseValues.SUCCESSES.getErrorMessage(), posts));
    }

    public ResponseEntity<?> deletePost(String id){
        postRepository.deleteById(id);
        return ResponseEntity.ok(new BaseResponse<>(ResponseValues.SUCCESSES.getErrorCode(),
                ResponseValues.SUCCESSES.getErrorMessage()));
    }

    public ResponseEntity<?> getPostsByCurrentDay(){
        List<Post> posts = postRepository.findAllByCreationDateGreaterThanEqual(Date.from(
                LocalDate.now()
                        .atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
            )
        );

        return ResponseEntity.ok(new BaseResponse<>(ResponseValues.SUCCESSES.getErrorCode(),
                ResponseValues.SUCCESSES.getErrorMessage(), posts));

    }
}
