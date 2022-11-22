import java.io.Serializable;
import java.util.ArrayList;

public class VM implements Serializable {
    public String type;
    public String owner;
    public ArrayList<String> log;
    public int id;

    public VM(String type, String owner, int id) {
        this.type = type;
        this.owner = owner;
        this.id = id;
        this.log = new ArrayList<>();
    }
}
