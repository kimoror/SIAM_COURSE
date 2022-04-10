package kimoror.siam.rest.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record SignupRequest(@NotBlank @Size(max = 50) @Email String email,
                            Set<String> role,
                            @NotBlank @Size(min = 6, max = 40) String password) {
}
