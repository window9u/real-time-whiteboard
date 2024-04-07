package message.response;

public class disconnect extends Response{
    private static final long serialVersionUID = 1L;
    private String name;
    public disconnect(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "connection{" +
                "name='" + name + '\'' +
                '}';
    }
}
