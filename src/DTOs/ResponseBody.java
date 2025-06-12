package DTOs;

public class ResponseBody<T> {

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private String responseStatus;
    private String message;

    private T data;

    public ResponseBody( String responseStatus,String message, T data) {
        this.message = message;
        this.responseStatus = responseStatus;
        this.data = data;
    }
}
