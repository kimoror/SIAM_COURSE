package kimoror.siam.models;

import kimoror.siam.rest.requests.UserInfoRequest;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;

@Table(name = "user_info", schema = "siam", indexes = {
        @Index(name = "user_info_company_id_uindex", columnList = "company_id", unique = true)
})
@Entity
public class UserInfo {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 70)
    private String name;

    @Column(name = "surname", length = 70)
    private String surname;

    @Column(name = "middle_name", length = 70)
    private String middleName;

    @Column(name = "birthday", nullable = false)
    private LocalDate birhthday;

    @Column(name = "address")
    private String address;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @Column(name = "company_id")
    private Long company_id;

    @Column(name = "work_position")
    private String workPosition;

    @Column(name = "education")
    private String education;

    @Column(name = "school")
    private String school;

    @Column(name = "unversity")
    private String university;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    public UserInfo(Long id, String name, String surname, String middleName, LocalDate birhthday, String address,
                    String status, Long company_id, String workPosition, String education, String school,
                    String university, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.birhthday = birhthday;
        this.address = address;
        this.status = status;
        this.company_id = company_id;
        this.workPosition = workPosition;
        this.education = education;
        this.school = school;
        this.university = university;
        this.phoneNumber = phoneNumber;
    }

    public UserInfo() {

    }

    public static UserInfo infoFromRequest(long id,UserInfoRequest userInfoRequest) throws ParseException {
        return new UserInfo(id, userInfoRequest.name(), userInfoRequest.surname(), userInfoRequest.middleName(),
                LocalDate.parse(userInfoRequest.birthday()), userInfoRequest.address(), userInfoRequest.status(),
                userInfoRequest.company_id(), userInfoRequest.workPosition(), userInfoRequest.education(),
                userInfoRequest.school(), userInfoRequest.university(), userInfoRequest.phoneNumber());
    }
}