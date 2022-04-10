package kimoror.siam.models;

import javax.persistence.*;

@Table(name = "roles", schema = "siam")
@Entity
public class Role {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private ERole name;

    public ERole getName() {
        return name;
    }
}