package kimoror.siam.rest;

import jakarta.validation.Valid;
import kimoror.siam.rest.dto.UserInfoDto;
import kimoror.siam.rest.requests.ResumeRequest;
import kimoror.siam.services.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserInfoService userInfoService;

    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/addInfo")
    public ResponseEntity<?> addUserInfo(@Valid @RequestBody UserInfoDto userInfoDto){
        return userInfoService.addUserInfo(userInfoDto);
    }

    @PostMapping(value = "/uploadResume", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadResume(ResumeRequest request) throws IOException {
        return userInfoService.saveResume(request);
    }

    @GetMapping(value = "/getResume")
    public ResponseEntity<?> getResume(@RequestParam String resumeName){
        return userInfoService.getResumeByEmail(resumeName);
    }

    @GetMapping(value = "/getLastResume/{email}")
    public ResponseEntity<?> getLastResumeByEmail(@PathVariable String email){
        return userInfoService.getLastResumeByEmail(email);
    }

    @GetMapping(value = "/getAllResumeNameByEmail")
    public ResponseEntity<?> getAllResumesByEmail(){
        return userInfoService.getResumeNamesByEmail();
    }

    @DeleteMapping(value = "/deleteResume/{id}")
    public ResponseEntity<?> deleteResume(@PathVariable String id){
        return userInfoService.deleteResumeByObjectId(id);
    }

    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfo() {
        return userInfoService.getInfo();
    }

    @GetMapping("/getInfoById/{id}")
    public ResponseEntity<?> getInfo(@PathVariable Long id) {
        return userInfoService.getInfo(id);
    }

}
