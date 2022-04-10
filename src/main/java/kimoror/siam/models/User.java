package kimoror.siam.models;

import jakarta.validation.constraints.Size;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "users",  schema = "siam", indexes = {
        @Index(name = "user_email_uindex", columnList = "email", unique = true)
})
@Entity
public class User {
    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    @Size(max = 255)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( schema = "siam", name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "password", nullable = false)
    @Size(max = 255)
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}