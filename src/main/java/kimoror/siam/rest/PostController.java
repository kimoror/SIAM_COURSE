package kimoror.siam.rest;

import jakarta.validation.Valid;
import kimoror.siam.rest.requests.PostRequest;
import kimoror.siam.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/post")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostRequest postRequest){
        return postService.createPost(postRequest);
    }

    @GetMapping("/getAllPosts")
    public ResponseEntity<?> getAllPosts(){
        return postService.getAllCurrentUserPosts();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        return postService.deletePost(id);
    }

}
