package message.request;

public class init extends Request{
    private final String name;
    public init(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    @Override
    public String toString() {
        return super.toString()+"name{" +
                "name='" + name + '\'' +
                '}';
    }
}
