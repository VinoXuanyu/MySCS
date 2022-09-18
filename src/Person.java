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
