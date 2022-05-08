package siam.services;

import kimoror.siam.models.Role;
import kimoror.siam.models.ERole;
import kimoror.siam.repositories.jpa.RoleRepository;
import kimoror.siam.repositories.jpa.UserInfoRepository;
import kimoror.siam.repositories.jpa.UserRepository;
import kimoror.siam.rest.requests.SignupRequest;
import kimoror.siam.rest.responses.ResponseValues;
import kimoror.siam.security.jwt.JwtUtils;
import kimoror.siam.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    UserInfoRepository userInfoRepository;

    @Mock
    Authentication authentication;

    AuthService authService;

    @BeforeEach
    public void setUp() {
        authService = new AuthService(
                authenticationManager,
                userRepository, roleRepository,
                passwordEncoder, jwtUtils);
    }

    @DisplayName("Single test successful")
    @Test
    public void simpleStringTest(){
        System.out.println("\n\n\nIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII\n\n\n");
    }

    @DisplayName("registerUser: user already exists")
    @Test
    public void registrationAlreadyExist(){
        String email = "testMail@kimoror.com";
        String password = "I_WANT_PIZZA";
        Set<String> roles = Set.of("ROLE_USER");

        SignupRequest signUpRequest = new SignupRequest(email, roles, password);

        when(userRepository.existsByEmail(signUpRequest.email())).thenReturn(true);

        var resp = authService.registerUser(signUpRequest);

        assertEquals(Objects.requireNonNull(resp.getBody()).getRespCode(),
                ResponseValues.EMAIL_ALREADY_USED.getErrorCode());
        assertEquals(Objects.requireNonNull(resp.getBody().getRespMessage()),
                ResponseValues.EMAIL_ALREADY_USED.getErrorMessage());
    }

    @DisplayName("registerUser: set of roles is not represented and ROLE_USER not found")
    @Test
    public void registerUserRoleNot(){
        String email = "testMail@kimoror.com";
        String password = "I_WANT_PIZZA";
        SignupRequest signUpRequest = new SignupRequest(email, null, password);

        when(userRepository.existsByEmail(signUpRequest.email())).thenReturn(false);

        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.empty());

        var resp = authService.registerUser(signUpRequest);

        assertEquals(Objects.requireNonNull(resp.getBody()).getRespCode(),
                ResponseValues.DEFAULT_ROLE_NOT_FOUND_IN_DB.getErrorCode());
        assertEquals(Objects.requireNonNull(resp.getBody().getRespMessage()),
                ResponseValues.DEFAULT_ROLE_NOT_FOUND_IN_DB.getErrorMessage());
    }

    @DisplayName("registerUser: strRoles == null ROLE_USER found")
    @Test
    public void registerUserDefaultRoleFound(){
        String email = "testMail@kimoror.com";
        String password = "I_WANT_PIZZA";
        SignupRequest signUpRequest = new SignupRequest(email, null, password);

        when(userRepository.existsByEmail(signUpRequest.email())).thenReturn(false);

        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(new Role()));

        var resp = authService.registerUser(signUpRequest);

        verify(userRepository).save(any());

        assertEquals(Objects.requireNonNull(resp.getBody()).getRespCode(),
                ResponseValues.SUCCESSES.getErrorCode());
        assertEquals(Objects.requireNonNull(resp.getBody().getRespMessage()),
                ResponseValues.SUCCESSES.getErrorMessage());
    }

    @DisplayName("registerUser: strRoles != null ROLE_ADMIN found")
    @Test
    public void registerAdminRoleFound(){
        String email = "testMail@kimoror.com";
        String password = "I_WANT_PIZZA";
        Set<String> roles = Set.of("admin");
        SignupRequest signUpRequest = new SignupRequest(email, roles, password);

        when(userRepository.existsByEmail(signUpRequest.email())).thenReturn(false);

        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(new Role()));

        var resp = authService.registerUser(signUpRequest);

        verify(userRepository).save(any());

        assertEquals(Objects.requireNonNull(resp.getBody()).getRespCode(),
                ResponseValues.SUCCESSES.getErrorCode());
        assertEquals(Objects.requireNonNull(resp.getBody().getRespMessage()),
                ResponseValues.SUCCESSES.getErrorMessage());
    }

    @DisplayName("registerUser: strRoles != null ROLE_ADMIN not found")
    @Test
    public void registerAdminRoleNotFound(){
        String email = "testMail@kimoror.com";
        String password = "I_WANT_PIZZA";
        Set<String> roles = Set.of("admin");
        SignupRequest signUpRequest = new SignupRequest(email, roles, password);

        when(userRepository.existsByEmail(signUpRequest.email())).thenReturn(false);

        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.empty());

        var resp = authService.registerUser(signUpRequest);

        assertEquals(Objects.requireNonNull(resp.getBody()).getRespCode(),
                ResponseValues.ROLE_NOT_FOUND_IN_DB.getErrorCode());
        assertEquals(Objects.requireNonNull(resp.getBody().getRespMessage()),
                ResponseValues.ROLE_NOT_FOUND_IN_DB.getErrorMessage());
    }
}
