package kimoror.siam.rest.requests;

import org.springframework.web.multipart.MultipartFile;

public record ResumeRequest(String resumeName, MultipartFile file ) {
}
