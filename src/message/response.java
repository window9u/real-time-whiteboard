package message;

public class response implements Message{
    private final boolean result;
    public response(boolean result){
        this.result = result;
    }
    public boolean getResult(){
        return result;
    }
}
