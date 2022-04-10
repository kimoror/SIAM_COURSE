package kimoror.siam.rest.responses;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class BaseResponse<T> {
    private int respCode;
    private String respMessage;
    private T params;

    public BaseResponse(int respCode, String respMessage, T params) {
        this.respCode = respCode;
        this.respMessage = respMessage;
        this.params = params;
    }

    public BaseResponse(int respCode, String respMessage) {
        this.respCode = respCode;
        this.respMessage = respMessage;
    }

    public int getRespCode() {
        return respCode;
    }

    public String getRespMessage() {
        return respMessage;
    }

    public T getParams() {
        return params;
    }
}
