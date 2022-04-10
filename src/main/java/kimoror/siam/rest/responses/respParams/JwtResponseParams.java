package kimoror.siam.rest.responses.respParams;

import java.util.List;

public class JwtResponseParams {
    private final String token;
    private final String type = "Bearer";
    private final Long userId;
    private final String email;
    private final List<String> roles;

    public JwtResponseParams(String accessToken, Long userId, String email, List<String> roles) {
        this.token = accessToken;
        this.userId = userId;
        this.email = email;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
