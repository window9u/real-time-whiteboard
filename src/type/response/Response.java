package type.response;

import type.Status;

public class Response {
    private type.Status status=Status.SERVER_SENT_RESPONSE;
    private String errorMessage;
    public void setStatus(type.Status status){
        this.status = status;
    }
    public boolean isReply(){
        return status == Status.REPLY;
    }
    public boolean isError(){
        return status == Status.ERROR;
    }
    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
}
