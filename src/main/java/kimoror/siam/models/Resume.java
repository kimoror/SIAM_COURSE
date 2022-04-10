package kimoror.siam.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "resumes")
public class Resume {
    private String userEmail;
    private String resumeName;
    private final byte[] file;
    private Date creationDate = new Date();

    public Resume(String userEmail, String resumeName,  byte[] file) {
        this.userEmail = userEmail;
        this.file = file;
        this.resumeName = resumeName;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public byte[] getFile() {
        return file;
    }


}
