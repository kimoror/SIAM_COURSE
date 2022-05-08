package kimoror.siam.services;

import kimoror.siam.models.Company;
import kimoror.siam.repositories.jpa.CompanyRepository;
import kimoror.siam.rest.requests.CompanyRequest;
import kimoror.siam.rest.responses.BaseResponse;
import kimoror.siam.rest.responses.ResponseValues;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public ResponseEntity<?> addCompnay(CompanyRequest companyRequest){
        Company company = new Company(companyRequest);

        try{
            companyRepository.save(company);
        } catch (IllegalArgumentException iae){
            return ResponseEntity.internalServerError()
                    .body(new BaseResponse<>(
                            ResponseValues.BAD_PARAMETERS.getErrorCode(),
                            ResponseValues.BAD_PARAMETERS.getErrorMessage()));
        }
        return ResponseEntity.ok(new BaseResponse<>(ResponseValues.SUCCESSES.getErrorCode(),
                ResponseValues.SUCCESSES.getErrorMessage()));
    }
}
