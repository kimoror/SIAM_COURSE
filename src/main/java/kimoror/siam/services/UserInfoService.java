package kimoror.siam.services;

import kimoror.siam.mappers.UserInfoMapper;
import kimoror.siam.models.UserInfo;
import kimoror.siam.models.Resume;
import kimoror.siam.repositories.ResumeRepository;
import kimoror.siam.repositories.UserInfoRepository;
import kimoror.siam.repositories.UserRepository;
import kimoror.siam.rest.requests.ResumeRequest;
import kimoror.siam.rest.dto.UserInfoDto;
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
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService {
    final UserInfoRepository userInfoRepository;
    final ResumeRepository resumeRepository;
    final UserService userService;
    final UserInfoMapper mapper;

    public UserInfoService(UserInfoRepository userInfoRepository, ResumeRepository resumeRepository,
                           UserRepository userRepository, UserService userService, UserInfoMapper mapper) {
        this.userInfoRepository = userInfoRepository;
        this.resumeRepository = resumeRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    public UserInfo addUser(UserInfo user) {
        return userInfoRepository.save(user);
    }

    public ResponseEntity<BaseResponse<?>> saveResume(ResumeRequest resumeRequest) throws IOException {
        if (resumeRequest.file() == null) {
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

    public ResponseEntity<?> getResumeByEmail(String resumeName) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userEmail = userDetails.getEmail();

        Optional<Resume> resumeOpt = resumeRepository.findByUserEmailAndResumeName(userEmail, resumeName);

        if (resumeOpt.isPresent()) {
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

    public ResponseEntity<?> getLastResumeByEmail(String email) {


        Optional<Resume> resumeOpt = resumeRepository.findFirstByUserEmailOrderByCreationDateDesc(email);

        if (resumeOpt.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity<>(resumeOpt.get().getFile(), headers, HttpStatus.OK);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(new BaseResponse<>(ResponseValues.CONTENT_NOT_FOUND.getErrorCode(),
                    ResponseValues.CONTENT_NOT_FOUND.getErrorMessage()), headers, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getResumeNamesByEmail() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userEmail = userDetails.getEmail();

        Optional<List<String>> resumeNamesOpt = resumeRepository.findResumeNamesByEmail(userEmail);

        if (resumeNamesOpt.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(resumeNamesOpt.get(), headers, HttpStatus.OK);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(new BaseResponse<>(ResponseValues.CONTENT_NOT_FOUND.getErrorCode(),
                    ResponseValues.CONTENT_NOT_FOUND.getErrorMessage()), headers, HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity<?> addUserInfo(UserInfoDto userInfoDto) {

        Long userId = userService.getCurrentUserId();
        if (userId == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(new BaseResponse<>(ResponseValues.CURRENT_USER_NOT_FOUND.getErrorCode(),
                    ResponseValues.CURRENT_USER_NOT_FOUND.getErrorMessage()), headers, HttpStatus.BAD_GATEWAY);
        }

        try {
            UserInfo userInfo = UserInfo.infoFromRequest(userId, userInfoDto);
            userInfoRepository.save(userInfo);
        } catch (DataIntegrityViolationException iae) {
            return ResponseEntity.internalServerError()
                    .body(new BaseResponse<>(
                            ResponseValues.BAD_PARAMETERS.getErrorCode(),
                            ResponseValues.BAD_PARAMETERS.getErrorMessage()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(new BaseResponse<>(ResponseValues.SUCCESSES.getErrorCode(),
                ResponseValues.SUCCESSES.getErrorMessage()));
    }

    public ResponseEntity<?> getInfo() {
        Long id = userService.getCurrentUserId();
        if (id == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(new BaseResponse<>(ResponseValues.CURRENT_USER_NOT_FOUND.getErrorCode(),
                    ResponseValues.CURRENT_USER_NOT_FOUND.getErrorMessage()), headers, HttpStatus.BAD_GATEWAY);
        }

        UserInfo userInfo = userInfoRepository.getById(id);

        UserInfoDto userInfoDto = mapper.userInfoToDto(userInfo);

        return ResponseEntity.ok(new BaseResponse<>(ResponseValues.SUCCESSES.getErrorCode(),
                ResponseValues.SUCCESSES.getErrorMessage(), userInfoDto));
    }

    public ResponseEntity<?> getInfo(Long id) {

        UserInfo userInfo = userInfoRepository.getById(id);

        UserInfoDto userInfoDto = mapper.userInfoToDto(userInfo);

        return ResponseEntity.ok(new BaseResponse<>(ResponseValues.SUCCESSES.getErrorCode(),
                ResponseValues.SUCCESSES.getErrorMessage(), userInfoDto));
    }

    public ResponseEntity<?> deleteResumeByObjectId(String resumeName) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userEmail = userDetails.getEmail();

       resumeRepository.deleteByUserEmailAndResumeName(userEmail, resumeName);
       return ResponseEntity.ok(new BaseResponse<>(ResponseValues.SUCCESSES.getErrorCode(),
                ResponseValues.SUCCESSES.getErrorMessage()));

    }

}
