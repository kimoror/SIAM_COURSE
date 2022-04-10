package kimoror.siam.rest;

import jakarta.validation.Valid;
import kimoror.siam.rest.requests.CompanyRequest;
import kimoror.siam.services.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CompanyController {
    CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/company/add")
    public ResponseEntity<?> addCompany(@Valid @RequestBody CompanyRequest companyRequest){
       return companyService.addCompnay(companyRequest);
    }
}
