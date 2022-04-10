package kimoror.siam.models;

import kimoror.siam.rest.requests.CompanyRequest;

import javax.persistence.*;

@Table(name = "company", schema = "siam", indexes = {
        @Index(name = "company_name_uindex", columnList = "name", unique = true)
})
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "activity_field", nullable = false)
    private String activityField;

    @Column(name = "description")
    private String description;

    public Company(Long id, String name, String address, String activityField, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.activityField = activityField;
        this.description = description;
    }

    public Company() {
    }

    public Company(CompanyRequest companyRequest){
        this.name = companyRequest.name();
        this.address = companyRequest.address();
        this.activityField = companyRequest.activityField();
        this.description = companyRequest.description();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getActivityField() {
        return activityField;
    }

    public String getDescription() {
        return description;
    }
}