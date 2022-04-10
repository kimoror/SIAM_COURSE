package kimoror.siam.services;

import kimoror.siam.models.UserInfo;
import kimoror.siam.models.Resume;
import kimoror.siam.repositories.ResumeRepository;
import kimoror.siam.repositories.UserInfoRepository;
import kimoror.siam.repositories.UserRepository;
import kimoror.siam.rest.requests.ResumeRequest;
import kimoror.siam.rest.requests.UserInfoRequest;
import kimoror.siam.rest.responses.BaseResponse;
import kimoror.siam.rest.responses.ResponseValues;
import kimoror.siam.security.services.UserDetailsImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

@Service
public class UserInfoService{
    final UserInfoRepository userInfoRepository;
    final ResumeRepository resumeRepository;
    final UserService userService;

    public UserInfoService(UserInfoRepository userInfoRepository, ResumeRepository resumeRepository,
                           UserRepository userRepository, UserService userService) {
        this.userInfoRepository = userInfoRepository;
        this.resumeRepository = resumeRepository;
        this.userService = userService;
    }

    public UserInfo addUser(UserInfo user){
        return userInfoRepository.save(user);
    }

    public ResponseEntity<BaseResponse<?>> saveResume(ResumeRequest resumeRequest) throws IOException {
        if(resumeRequest.file() == null){
            return ResponseEntity.badRequest().body(new BaseResponse<>(ResponseValues.BAD_PARAMETERS.getErrorCode(),
                    ResponseValues.BAD_PARAMETERS.getErrorMessage()));
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String email = userDetails.getEmail();
        byte[] resume = resumeRequest.file().getBytes();
        String resumeName = resumeRequest.resumeName();

        resumeRepository.save(new Resume(email, resumeName, resume));
        return ResponseEntity.ok(new BaseResponse<>(ResponseValues.SUCCESSES.getErrorCode(),
                ResponseValues.SUCCESSES.getErrorMessage()));
    }

    public ResponseEntity<?> getResumeByEmail(String resumeName){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userEmail = userDetails.getEmail();

        Optional<Resume> resumeOpt = resumeRepository.findByUserEmailAndResumeName(userEmail, resumeName);

        if(resumeOpt.isPresent()){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity<>(resumeOpt.get().getFile(), headers, HttpStatus.OK);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(new BaseResponse<>(ResponseValues.CONTENT_NOT_FOUND.getErrorCode(),
                    ResponseValues.CONTENT_NOT_FOUND.getErrorMessage()), headers, HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity<?> addUserInfo(UserInfoRequest userInfoRequest){

        Long userId = userService.getCurrentUserId();
        if(userId == null){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(new BaseResponse<>(ResponseValues.CURRENT_USER_NOT_FOUND.getErrorCode(),
                    ResponseValues.CURRENT_USER_NOT_FOUND.getErrorMessage()), headers, HttpStatus.BAD_GATEWAY);
        }

        try{
            UserInfo userInfo = UserInfo.infoFromRequest(userId, userInfoRequest);
            userInfoRepository.save(userInfo);
        } catch (DataIntegrityViolationException iae){
            return ResponseEntity.internalServerError()
                    .body(new BaseResponse<>(
                            ResponseValues.BAD_PARAMETERS.getErrorCode(),
                            ResponseValues.BAD_PARAMETERS.getErrorMessage()));
        } catch (ParseException e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(new BaseResponse<>(ResponseValues.SUCCESSES.getErrorCode(),
                ResponseValues.SUCCESSES.getErrorMessage()));
    }
}
