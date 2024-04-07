package message.response;

import message.Status;

import java.io.Serializable;

public class Response implements Serializable {
    private message.Status status=Status.SERVER_SENT_RESPONSE;
    private String errorMessage;
    public void setStatus(message.Status status){
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
    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", errorMessage='" + errorMessage + '\'' +
                "\n}";
    }
}
