package kimoror.siam.rest;

import kimoror.siam.models.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {

    @GetMapping("/simpleBaseRequest")
    public ResponseEntity<?> getBaseRequest(){
        return ResponseEntity.ok(new Company(1L, "asf", "asdf", "asdf", "asdf"));
    }
}
