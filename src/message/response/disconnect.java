package message.response;

public class disconnect extends Response{
    private static final long serialVersionUID = 1L;
    private final String name;
    public disconnect(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "connection{" +
                "name='" + name + '\'' +
                "} disconnect";
    }
}
