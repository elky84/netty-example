import java.util.Date;

public class ChatData {

    private final String value;

    public ChatData(String str) {
        this.value=str;
    }

    public int getLength() {
        return value.length();
    }

    public String getString() {
        return value;
    }

    public byte[] getBytes() {
        return value.getBytes();
    }

    @Override
    public String toString() {
        return value;
    }
}