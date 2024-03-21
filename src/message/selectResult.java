package message;

public class selectResult implements Message{
    private final boolean result;
    public selectResult(boolean result){
        this.result = result;
    }
    public boolean getResult(){
        return result;
    }
}
