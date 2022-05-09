package kimoror.siam.models;

import kimoror.siam.rest.dto.UserInfoDto;

import javax.persistence.*;
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
    private LocalDate birthday;

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
        this.birthday = birhthday;
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

    public static UserInfo infoFromRequest(long id, UserInfoDto userInfoDto) throws ParseException {
        return new UserInfo(id, userInfoDto.getName(), userInfoDto.getSurname(), userInfoDto.getMiddleName(),
                LocalDate.parse(userInfoDto.getBirthday()), userInfoDto.getAddress(), userInfoDto.getStatus(),
                userInfoDto.getCompany_id(), userInfoDto.getWorkPosition(), userInfoDto.getEducation(),
                userInfoDto.getSchool(), userInfoDto.getUniversity(), userInfoDto.getPhoneNumber());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birhthday) {
        this.birthday = birhthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}