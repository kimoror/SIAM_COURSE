package kimoror.siam.services;

import jakarta.validation.Valid;
import kimoror.siam.models.Role;
import kimoror.siam.models.User;
import kimoror.siam.models.ERole;
import kimoror.siam.rest.responses.BaseResponse;
import kimoror.siam.rest.responses.ResponseValues;
import kimoror.siam.repositories.RoleRepository;
import kimoror.siam.repositories.UserRepository;
import kimoror.siam.rest.requests.LoginRequest;
import kimoror.siam.rest.requests.SignupRequest;
import kimoror.siam.rest.responses.respParams.JwtResponseParams;
import kimoror.siam.security.jwt.JwtUtils;
import kimoror.siam.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final String ADMIN = "admin";

    final
    AuthenticationManager authenticationManager;

    final
    UserRepository userRepository;

    final
    RoleRepository roleRepository;

    final
    PasswordEncoder encoder;

    final
    JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<BaseResponse<?>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new BaseResponse<>(
                ResponseValues.SUCCESSES.getErrorCode(),
                ResponseValues.SUCCESSES.getErrorMessage(),
                new JwtResponseParams(jwt,
                    userDetails.getId(),
                    userDetails.getEmail(),
                    roles)
        ));
    }

    public ResponseEntity<BaseResponse<?>> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.email())) {
            return ResponseEntity
                    .badRequest()
                    .body(new BaseResponse<>(ResponseValues.EMAIL_ALREADY_USED.getErrorCode(),
                            ResponseValues.EMAIL_ALREADY_USED.getErrorMessage()));
        }

        // Create new user's account
        User user = new User(signUpRequest.email(), encoder.encode(signUpRequest.password()));

        Set<String> strRoles = signUpRequest.role();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Optional<Role> userRoleOpt = roleRepository.findByName(ERole.ROLE_USER);
            if(userRoleOpt.isPresent()) {
                roles.add(userRoleOpt.get());
            } else {
                return ResponseEntity.badRequest().body(new BaseResponse<>(ResponseValues.DEFAULT_ROLE_NOT_FOUND_IN_DB.getErrorCode(),
                        ResponseValues.DEFAULT_ROLE_NOT_FOUND_IN_DB.getErrorMessage()));
            }
        } else {
            for(String role : strRoles){
                Optional<Role> roleOpt;
                if (ADMIN.equals(role)){
                    roleOpt = roleRepository.findByName(ERole.ROLE_ADMIN);
                } else {
                    roleOpt = roleRepository.findByName(ERole.ROLE_USER);
                }
                if(roleOpt.isPresent()) {
                    roles.add(roleOpt.get());
                } else {
                    return ResponseEntity.badRequest().body(new BaseResponse<>(ResponseValues.ROLE_NOT_FOUND_IN_DB.getErrorCode(),
                            ResponseValues.ROLE_NOT_FOUND_IN_DB.getErrorMessage()));
                }
            }
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new BaseResponse<>(ResponseValues.SUCCESSES.getErrorCode(),
                ResponseValues.SUCCESSES.getErrorMessage()));
    }

}
