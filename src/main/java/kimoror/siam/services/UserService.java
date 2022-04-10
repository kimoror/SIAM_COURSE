package kimoror.siam.services;

import kimoror.siam.models.User;
import kimoror.siam.repositories.UserRepository;
import kimoror.siam.rest.responses.BaseResponse;
import kimoror.siam.rest.responses.ResponseValues;
import kimoror.siam.security.services.UserDetailsImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public Long getCurrentUserId(){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userEmail = userDetails.getEmail();

        Optional<Long> userIdOpt= userRepository.getIdByEmail(userEmail);
        if(userIdOpt.isEmpty()){
            return null;
        } else {
            return userIdOpt.get();
        }
    }
}
