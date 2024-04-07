package message.response;

public class connect extends Response{
    private final String name;
    public connect(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    @Override
    public String toString() {
        return super.toString() +
                "name='" + name + '\'' +
                "connected";
    }
}
