package kimoror.siam.rest.requests;

import kimoror.siam.models.Company;

import java.util.Date;

public record UserInfoRequest(String name, String surname, String middleName, String birthday,
                              String address, String role, String status, Long company_id, String workPosition,
                              String education, String school, String university, String phoneNumber) {}
