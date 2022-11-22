import java.util.ArrayList;
import java.util.HashSet;

public class Person {

    String ID;
    String FirstName;
    String LastName;
    String Email;
    String Password;
    String Type;


    public Person(String id, String firstName, String lastName, String email, String password) {
        ID = id;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Password = password;

        if (this.ID.length() == 5) {
            this.Type = "T";
        } else {
            this.Type = "S";
        }
    }

    public String DescSelfForStudent() {
        return "[ID:" + this.ID +
                "] [Name:" + this.LastName + " " + this.FirstName +
                "] [Email:" + this.Email +
                "]";
    }
    public String DescSelf() {
        if (this.Type.equals("T")) {
            return "[ID:" + this.ID +
                    "] [Name:" + this.LastName + " " + this.FirstName +
                    "] [Type:" + "Professor" +
                    "] [Email:" + this.Email +
                    "]";
        } else {
            return "[ID:" + this.ID +
                    "] [Name:" + this.LastName + " " + this.FirstName +
                    "] [Type:" + "Assistant" +
                    "] [Email:" + this.Email +
                    "]";
        }
    }
    public String DescSelfWithoutID() {
        if (this.Type.equals("T")) {
            return  "[Name:" + this.LastName + " " + this.FirstName +
                    "] [Type:" + "Professor" +
                    "] [Email:" + this.Email +
                    "]";
        } else {
            return  "[Name:" + this.LastName + " " + this.FirstName +
                    "] [Type:" + "Assistant" +
                    "] [Email:" + this.Email +
                    "]";
        }
    }
    public void PrintInfo() {
        System.out.println("Name: " + this.FirstName + " " + this.LastName);
        System.out.println("ID: " + this.ID);
        if (this.Type.equals("S")) {
            System.out.println("Type: Student");
        } else {
            System.out.println("Type: Professor");
        }
        System.out.println("Email: " + this.Email);
    }
}
