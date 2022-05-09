package kimoror.siam.services;

import kimoror.siam.models.User;
import kimoror.siam.repositories.jpa.UserRepository;
import kimoror.siam.security.services.UserDetailsImpl;
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

    public String getEmail(Long id){
        Optional<String> userEmailOpt = userRepository.getEmailById(id);
        return userEmailOpt.orElse("");
    }
}
