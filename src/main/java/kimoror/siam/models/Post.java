package kimoror.siam.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
public class Post {
    private String id;
    private Long userId;
    private String text;

    public Post(Long userId, String text) {
        this.userId = userId;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Long getUserId() {
        return userId;
    }
}
