import java.util.ArrayList;
import java.util.HashSet;
import java.util.jar.JarEntry;

public class Course {
    String ID;
    String Name;

    HashSet<String> Teachers;
    HashSet<String> Assistants;
    HashSet<String> Students;
    HashSet<String> Wares;
    HashSet<String> Tasks;
    public Course(String ID, String name) {
        this.ID = ID;
        Name = name;
        Teachers = new HashSet<>();
        Assistants = new HashSet<>();
        Students = new HashSet<>();
        Wares = new HashSet<>();
        Tasks = new HashSet<>();
    }

    public String DescSelf() {
        return "[ID:" + this.ID +
                "] [Name:" + this.Name +
                "] [TeacherNum:" + Teachers.size()+
                "] [AssistantNum:" + Assistants.size() +
                "] [StudentNum:" + Students.size() +
                "]";
    }


}
