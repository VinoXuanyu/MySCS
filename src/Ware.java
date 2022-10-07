public class Ware {
    String ID;
    String Name;

    public Ware(String ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public String DescSelf() {
        return "[ID:" + this.ID +
                "] [Name:" + this.Name +
                "]";
    }
}
