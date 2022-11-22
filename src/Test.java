import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
// ./pat ta -judge 3-21210108-金炫宇/ -pwd
public class Test {
    final static String dataDir = "./data";

    public static Scanner in = new Scanner(System.in);
    public static boolean LoggedIn = false;
    public static String CurUserID = "";
    public static String SelectedCourse = "";
    
    public static String CurMode = "";
    public static HashMap<String, Person> MapID2Person = new HashMap<>();

    public static HashMap<String, Course> MapID2Course = new HashMap<>();

    public static HashMap<String, Ware> MapID2Ware = new HashMap<>();

    public static HashMap<String, Task> MapID2Task = new HashMap<>();

    public static HashMap<String, ArrayList<VM>> MapCourse2VMs = new HashMap();

    public static HashMap<String , VM> MapStudentAndCourse2VM = new HashMap<>();


    
    // check ID
    public static boolean CheckID(String ID) {
        try {
            if (!(ID.length() == 5 || ID.length() == 8 || ID.length() == 9)) {
                return false;
            }

            int Year, Faculty, Class, Remain;

            // Teacher
            if (ID.length() == 5) {
                if (Integer.parseInt(ID) > 99999 || Integer.parseInt(ID) < 1) {
                    return false;
                }
            }
            // Bachelor
            else if (ID.length() == 8) {
                Year = Integer.parseInt(ID.substring(0, 2));
                Faculty = Integer.parseInt(ID.substring(2, 4));
                Class = Integer.parseInt(ID.substring(4, 5));
                Remain = Integer.parseInt(ID.substring(5, 8));
                if (Year < 17 || Year > 22) {
                    return false;
                }
                if (Faculty < 1 || Faculty > 43) {
                    return false;
                }
                if (Class < 1 || Class > 6) {
                    return false;
                }
                if (Remain < 1) {
                    return false;
                }
            }
            // PhD or Master
            else {
                String StudentType = ID.substring(0, 2);
                Year = Integer.parseInt(ID.substring(2, 4));
                Faculty = Integer.parseInt(ID.substring(4, 6));
                Class = Integer.parseInt(ID.substring(6, 7));
                Remain = Integer.parseInt(ID.substring(7, 9));

                if (!(StudentType.equals("SY") || StudentType.equals("BY") || StudentType.equals("ZY"))) {
                    return false;
                }
                if (StudentType.equals("SY") || StudentType.equals("ZY")) {
                    if (Year < 19 || Year > 22) {
                        return false;
                    }
                } else {
                    if (Year < 17 || Year > 22) {
                        return false;
                    }
                }
                if (Faculty < 1 || Faculty > 43) {
                    return false;
                }
                if (Class < 1 || Class > 6) {
                    return false;
                }
                if (Remain < 1) {
                    return false;
                }
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean CheckName(String FirstName, String LastName) {
        String pattern = "[A-Z]{1}[a-z]{0,19}";
        return Pattern.matches(pattern, FirstName) && Pattern.matches(pattern, LastName);
    }

    public static boolean CheckEmail(String Email) {
        String pattern = "[a-zA-Z0-9_]+@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9]+){1,}";
        return Pattern.matches(pattern, Email);
    }
    public static boolean CheckPasswordConsistent(String Password, String PasswordAgain) {
        return Password.equals(PasswordAgain);
    }

    public static boolean CheckPassword(String Password) {
        String pattern ="[a-zA-Z][A-Z0-9a-z_]{7,15}";
        return Pattern.matches(pattern, Password);
    }

    public static boolean CheckCourseID(String courseID) {
        String pattern = "C[0-9]{4}";
        if (!Pattern.matches(pattern, courseID)) {
            return false;
        }
        String year = courseID.substring(1, 3);
        String latter = courseID.substring(3, 5);
        if (Integer.parseInt(year) < 17 || Integer.parseInt(year) > 22) {
            return false;
        }
        if (Integer.parseInt(latter) == 0) {
            return false;
        }
        return true;
    }

    public static boolean CheckWareID(String WareID) {
        String pattern = "W[0-9]{6}";
        if (!Pattern.matches(pattern, WareID)) {
            return false;
        }
        String former = WareID.substring(1, 5);
        String latter = WareID.substring(5, 7);
        if (latter.equals("00")) {
            return false;
        }
        if (!former.equals(SelectedCourse.substring(1, 5))) {
            return false;
        }
        return true;
    }

    public static boolean CheckWareName(String WareName) {
        if (WareName.length() > 16 || WareName.length() < 6) {
            return false;
        }
        String pattern = "[a-zA-Z0-9_]{1,14}\\.[a-zA-Z0-9]{1,14}";
        return Pattern.matches(pattern, WareName);
    }

    public static boolean CheckTaskID(String TaskID) {
        String pattern = "T[0-9]{6}";
        if (!Pattern.matches(pattern, TaskID)) {
            return false;
        }
        String former = TaskID.substring(1, 5);
        String latter = TaskID.substring(5, 7);
        if (latter.equals("00")) {
            return false;
        }
        if (!former.equals(SelectedCourse.substring(1, 5))) {
            return false;
        }
        return true;
    }

    public static boolean CheckTaskName(String TaskName) {
        if (TaskName.length() > 16 || TaskName.length() < 6) {
            return false;
        }
        String pattern = "[a-zA-Z0-9_]{1,14}\\.[a-zA-Z0-9]{1,14}";
        return Pattern.matches(pattern, TaskName);
    }

    public static boolean CompareTime(String startTime, String finishTime) {
        try {
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            Date start = date.parse(startTime);
            Date finish = date.parse(finishTime);

            return finish.after(start);
        } catch (Exception e) {
            System.out.println("err in compare date");
        }

        return false;
    }

    public static boolean CheckTime(String Time) {
        String[] terms = Time.split("-");
        if (terms.length != 4) {
            return false;
        }
        try {
            String hourMinSec = terms[3];
            String[] HMS = hourMinSec.split(":");
            if (HMS.length != 3) {
                return false;
            }
            int year = Integer.parseInt(terms[0]);
            if (year < 1999 || year > 9999) {
                return false;
            }
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            date.setLenient(false);
            date.parse(Time);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean CheckCourseName(String courseName) {
        String pattern = pattern = "[a-zA-Z0-9_]{6,16}";
        return Pattern.matches(pattern, courseName);
    }
    
    public static boolean CheckIsAssistant(String user) {
        for (Course c : MapID2Course.values()) {
            if (c.Assistants.contains(user)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean CheckIsTeacher(String user) {
        if (MapID2Person.get(user).Type.equals("T")) {
            return true;
        }
        return false;
    }

    public static boolean CheckIsStudent(String user) {
        if (MapID2Person.get(user).Type.equals("S")) {
            return true;
        }
        return false;
    }
    
    public static boolean CheckArgumentsNum(String[] params, int... nums) {
        for(int num : nums) {
            if (params.length == num) {
                return true;
            }
        }
        System.out.println("arguments illegal");
        return false;
    }
    

    public static void HandleRegister(String[] params) {
        if (!CheckArgumentsNum(params, 6)) {
            return;
        }

        String ID = params[0];
        String FirstName = params[1];
        String SecondName = params[2];
        String Email = params[3];
        String Password = params[4];
        String PasswordAgain = params[5];

        if (LoggedIn) {
            System.out.println("already logged in");
            return;
        }

        if (!CheckID(ID)) {
            System.out.println("user id illegal");
            return;
        }

        if (MapID2Person.containsKey(ID)) {
            System.out.println("user id duplication");
            return;
        }

        if (!CheckName(FirstName, SecondName)) {
            System.out.println("user name illegal");
            return;
        }

        if (!CheckEmail(Email)){
            System.out.println("email address illegal");
            return;
        }

        if (!CheckPassword(Password)) {
            System.out.println("password illegal");
            return;
        }

        if (!CheckPasswordConsistent(Password, PasswordAgain)) {
            System.out.println("passwords inconsistent");
            return;
        }

        Person NewUser = new Person(ID, FirstName, SecondName, Email, Password);
        MapID2Person.put(ID, NewUser);

        System.out.println("register success");
        return;
    }

    public static void HandleLogin(String[] params) {
        if (!CheckArgumentsNum(params, 2)) {
            return;
        }

        if (LoggedIn) {
            System.out.println("already logged in");
            return;
        }

        String ID = params[0];
        String Password = params[1];

        if (!CheckID(ID)) {
            System.out.println("user id illegal");
            return;
        }

        if (!MapID2Person.containsKey(ID)) {
            System.out.println("user id not exist");
            return;
        }

        if (!MapID2Person.get(ID).Password.equals(Password)) {
            System.out.println("wrong password");
            return;
        }

        Person User = MapID2Person.get(ID);
        if (User.Type.equals("T")) {
            System.out.println("Hello Professor " + User.LastName +  "~");
        } else {
            System.out.println("Hello " + User.FirstName +  "~");
            CurMode = "Student";
        }

        LoggedIn = true;
        CurUserID = ID;
    }

    public static void HandleLogout(String[] params) {
        if (!CheckArgumentsNum(params, 0)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        System.out.println("Bye~");

        if (CheckIsStudent(CurUserID)) {
            CurMode = "";
        }
        CurUserID = "";
        SelectedCourse = "";
        LoggedIn = false;
    }

    public static void HandlePrintInfo(String[] params) {
        if (!CheckArgumentsNum(params, 0, 1)) {
            return;
        }

        String ID = "";
        if (params.length == 2) {
            ID = params[0];
        }

        if (!LoggedIn) {
            System.out.println("login first");
            return;
        }

        if (!ID.equals("") && CheckIsStudent(CurUserID)) {
            System.out.println("permission denied");
            return;
        }

        if (!ID.equals("") && !CheckID(ID)) {
            System.out.println("user id illegal");
            return;
        }

        if (!ID.equals("") && !MapID2Person.containsKey(ID)) {
            System.out.println("user id not exist");
            return;
        }

        Person User;
        if (ID.equals("")) {
            User = MapID2Person.get(CurUserID);
        } else {
            User = MapID2Person.get(ID);
        }

        User.PrintInfo();
        return;
    }

    public static void HandleAddCourse(String[] params) {
        if (!CheckArgumentsNum(params, 2)) {
            return;
        }

        String courseId = params[0];
        String courseName = params[1];
        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (!CheckIsTeacher(CurUserID)) {
            System.out.println("permission denied");
            return;
        }

        if (!CheckCourseID(courseId)) {
            System.out.println("course id illegal");
            return;
        }

        if (MapID2Course.containsKey(courseId)) {
            System.out.println("course id duplication");
            return;
        }

        if (!CheckCourseName(courseName)) {
            System.out.println("course name illegal");
            return;
        }

        Course course = new Course(courseId, courseName);
        course.Teachers.add(CurUserID);
        MapID2Course.put(courseId, course);
        System.out.println("add course success");
    }

    public static void HandleRemoveCourse(String[] params) {
        if (!CheckArgumentsNum(params, 1)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (!CheckIsTeacher(CurUserID)) {
            System.out.println("permission denied");
            return;
        }

        String courseId = params[0];
        if (!CheckCourseID(courseId)) {
            System.out.println("course id illegal");
            return;
        }

        if (!MapID2Course.containsKey(courseId) || !MapID2Course.get(courseId).Teachers.contains(CurUserID)) {
            System.out.println("course id not exist");
            return;
        }

        MapID2Course.remove(courseId);
        System.out.println("remove course success");
    }

    public static void HandleListCourse(String[] params) {
        if (!CheckArgumentsNum(params, 0)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        // SCS 2
//        if (!CheckIsTeacher(CurUserID)) {
//            System.out.println("permission denied");
//            return;
//        }
        // SCS 3

        ArrayList<String> courses = new ArrayList<>();
        for (Course c : MapID2Course.values()) {
            if (c.Teachers.contains(CurUserID) || c.Assistants.contains(CurUserID) || c.Students.contains(CurUserID)) {
                courses.add(c.DescSelf());
            }
        }

        if (courses.isEmpty()) {
            System.out.println("course not exist");
            return;
        }

        courses.sort(Comparator.naturalOrder());
        for (String c : courses) {
            System.out.println(c);
        }
    }

    public static void HandleSelectCourse(String[] params) {
        if (!CheckArgumentsNum(params, 1)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

// SCS 2
//        if (!(CheckIsTeacher(CurUserID) || CheckIsAssistant(CurUserID))) {
//            System.out.println("permission denied");
//            return;
//        }


        String courseId = params[0];
        if (!CheckCourseID(courseId)) {
            System.out.println("course id illegal");
            return;
        }

        if (!MapID2Course.containsKey(courseId) ||
                !(MapID2Course.get(courseId).Teachers.contains(CurUserID)
                        || MapID2Course.get(courseId).Assistants.contains(CurUserID)
                        || MapID2Course.get(courseId).Students.contains(CurUserID))) {
            System.out.println("course id not exist");
            return;
        }

        SelectedCourse = courseId;
        System.out.println("select course success");
    }

    public static void HandleAddAdmin(String[] params) {
        if (params.length == 0) {
            System.out.println("arguments illegal");
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (!CheckIsTeacher(CurUserID)) {
            System.out.println("permission denied");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        for (String user : params) {
            if (!CheckID(user)) {
                System.out.println("user id illegal");
                return;
            }
        }

        for (String user : params) {
            if (!MapID2Person.containsKey(user)) {
                System.out.println("user id not exist");
                return;
            }
        }

        for (String user : params) {
            if (MapID2Person.get(user).Type.equals("T")) {
                MapID2Course.get(SelectedCourse).Teachers.add(user);
            } else {
                MapID2Course.get(SelectedCourse).Assistants.add(user);
            }
        }

        System.out.println("add admin success");
    }

    public static void HandleRemoveAdmin(String[] params) {
        if (!CheckArgumentsNum(params, 1)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (!CheckIsTeacher(CurUserID)) {
            System.out.println("permission denied");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        String user = params[0];
        if (!CheckID(user)) {
            System.out.println("user id illegal");
            return;
        }

        if (!MapID2Person.containsKey(user) || MapID2Course.get(SelectedCourse).Teachers.contains(user)) {
            System.out.println("user id not exist");
            return;
        }

        if (MapID2Person.get(user).Type.equals("T")) {
            MapID2Course.get(SelectedCourse).Teachers.remove(user);
        } else {
            MapID2Course.get(SelectedCourse).Assistants.remove(user);
        }

        System.out.println("remove admin success");
    }

    public static void HandleListAdmin(String[] params) {
        if (!CheckArgumentsNum(params, 0)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

// SCS 2
//        if (!(CheckIsTeacher(CurUserID) || CheckIsAssistant(CurUserID))){
//                System.out.println("permission denied");
//                return;
//        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        String[] teachers = new String[MapID2Course.get(SelectedCourse).Teachers.size()];
        String[] assistants = new String[MapID2Course.get(SelectedCourse).Assistants.size()];
        String[] admins = new String[teachers.length + assistants.length];
        MapID2Course.get(SelectedCourse).Teachers.toArray(teachers);
        MapID2Course.get(SelectedCourse).Assistants.toArray(assistants);

        System.arraycopy(teachers, 0, admins, 0, teachers.length);
        System.arraycopy(assistants, 0, admins, teachers.length, assistants.length);
        Arrays.sort(admins);

        if (CheckIsTeacher(CurUserID) || CheckIsAssistant(CurUserID)){
            for (String w : admins) {
                System.out.println(MapID2Person.get(w).DescSelf());
            }
        } else {
            for (String w : admins) {
                System.out.println(MapID2Person.get(w).DescSelfWithoutID());
            }
        }
    }
    
    public static void HandleChangeRole(String[] params) {
        if (!CheckArgumentsNum(params, 0)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }
        
        if (CheckIsTeacher(CurUserID)) {
            System.out.println("permission denied");
            return;
        }

        if (!CheckIsAssistant(CurUserID)) {
            System.out.println("permission denied");
            return;
        }

        if (CurMode.equals("Student")) {
            CurMode = "Assistant";
            System.out.println("change into Assistant success");
        } else {
            CurMode = "Student";
            System.out.println("change into Student success");
        }

        SelectedCourse = "";
    }

    public static void HandleAddWare(String[] params) {
        if (!CheckArgumentsNum(params, 2)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        // MySCS2
//        if (!CheckIsTeacher(CurUserID)) {
//            System.out.println("permission denied");
//            return;
//        }

        if (!(CheckIsTeacher(CurUserID) || CheckIsAssistant(CurUserID))) {
            System.out.println("permission denied");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        // MySCS2
//        if (!CheckWareID(params[0])) {
//            System.out.println("ware id illegal");
//            return;
//        }



        if (MapID2Ware.containsKey(params[0])) {
            System.out.println("ware id duplication");
            return;
        }
        if (!CheckWareName(params[1])) {
            System.out.println("ware name illegal");
            return;
        }

        MapID2Ware.put(params[0], new Ware(params[0], params[1]));
        MapID2Course.get(SelectedCourse).Wares.add(params[0]);
        System.out.println("add ware success");
    }

    public static void HandleRemoveWare(String[] params) {
        if (!CheckArgumentsNum(params, 1)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (!CheckIsTeacher(CurUserID)) {
            System.out.println("permission denied");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        if (!CheckWareID(params[0])) {
            System.out.println("ware id illegal");
            return;
        }

        if (!MapID2Course.get(SelectedCourse).Wares.contains(params[0]) ||
            !MapID2Ware.containsKey(params[0])) {
            System.out.println("ware id not exist");
            return;
        }

        MapID2Ware.remove(params[0]);
        MapID2Course.get(SelectedCourse).Wares.remove(params[0]);
        System.out.println("remove ware success");
    }

    public static void HandleListWare(String[] params) {
        if (!CheckArgumentsNum(params, 0)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (!(CheckIsTeacher(CurUserID)|| CheckIsAssistant(CurUserID))) {
            System.out.println("permission denied");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }
        String[] wares = new String[MapID2Course.get(SelectedCourse).Wares.size()];
        MapID2Course.get(SelectedCourse).Wares.toArray(wares);
        Arrays.sort(wares);

        for (String w : wares) {
            System.out.println(MapID2Ware.get(w).DescSelf());
        }
    }

    public static void HandleAddTask(String[] params) {
        if (!CheckArgumentsNum(params, 4)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (!(CheckIsTeacher(CurUserID)|| CheckIsAssistant(CurUserID))) {
            System.out.println("permission denied");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        if (!CheckTaskID(params[0])) {
            System.out.println("task id illegal");
            return;
        }

        if (MapID2Task.containsKey(params[0])) {
            System.out.println("task id duplication");
            return;
        }
        if (!CheckTaskName(params[1])) {
            System.out.println("task name illegal");
            return;
        }
        if (!(CheckTime(params[2]) && CheckTime(params[3]) && CompareTime(params[2], params[3]))){
            System.out.println("task time illegal");
            return;
        }
        MapID2Task.put(params[0], new Task(params[0], params[1], params[2], params[3]));
        MapID2Course.get(SelectedCourse).Tasks.add(params[0]);
        System.out.println("add task success");
    }

    public static void HandleRemoveTask(String[] params) {
        if (!CheckArgumentsNum(params, 1)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (!(CheckIsTeacher(CurUserID)|| CheckIsAssistant(CurUserID))) {
            System.out.println("permission denied");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        if (!CheckTaskID(params[0])) {
            System.out.println("task id illegal");
            return;
        }

        if (!MapID2Course.get(SelectedCourse).Tasks.contains(params[0]) ||
                !MapID2Task.containsKey(params[0])) {
            System.out.println("task id not exist");
            return;
        }

        MapID2Task.remove(params[0]);
        MapID2Course.get(SelectedCourse).Tasks.remove(params[0]);
        System.out.println("remove task success");
    }

    public static void HandleListTask(String[] params) {
        if (!CheckArgumentsNum(params, 0)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (!(CheckIsTeacher(CurUserID)|| CheckIsAssistant(CurUserID))) {
            System.out.println("permission denied");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        String[] tasks = new String[MapID2Course.get(SelectedCourse).Tasks.size()];
        MapID2Course.get(SelectedCourse).Tasks.toArray(tasks);
        Arrays.sort(tasks);

        for (String w : tasks) {
            System.out.println(MapID2Task.get(w).DescSelf());
        }
    }

    public static void HandleAddStudents(String[] params) {
        if (params.length == 0) {
            System.out.println("arguments illegal");
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (!(CheckIsTeacher(CurUserID)|| CheckIsAssistant(CurUserID))) {
            System.out.println("permission denied");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        for (String u : params) {
            if (!CheckID(u)) {
                System.out.println("user id illegal");
                return;
            }
            if (!MapID2Person.containsKey(u)) {
                System.out.println("user id not exist");
                return;
            }
            if (CheckIsTeacher(u)) {
                System.out.println("I'm professor rather than student!");
                return;
            }
        }

        for (String u : params) {
            MapID2Course.get(SelectedCourse).Students.add(u);
        }
        System.out.println("add student success");
    }

    public static void HandleRemoveStudent(String[] params) {
        if (!CheckArgumentsNum(params, 1)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (!(CheckIsTeacher(CurUserID)|| CheckIsAssistant(CurUserID))) {
            System.out.println("permission denied");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        if (!CheckID(params[0])) {
            System.out.println("user id illegal");
            return;
        }

        if (!(MapID2Person.containsKey(params[0]) &&
                MapID2Course.get(SelectedCourse).Students.contains(params[0]))){
            System.out.println("user id not exist");
            return;
        }

        MapID2Course.get(SelectedCourse).Students.remove(params[0]);
        System.out.println("remove student success");
    }

    public static void HandleListStudent(String[] params) {
        if (!CheckArgumentsNum(params, 0)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (!(CheckIsTeacher(CurUserID)|| CheckIsAssistant(CurUserID))) {
            System.out.println("permission denied");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        String[] students = new String[MapID2Course.get(SelectedCourse).Students.size()];
        MapID2Course.get(SelectedCourse).Students.toArray(students);
        Arrays.sort(students);

        for (String w : students) {
            System.out.println(MapID2Person.get(w).DescSelfForStudent());
        }
    }

    public static void HandleRequestVM(String[] params) {
        if (!CheckArgumentsNum(params, 1)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        if (!MapCourse2VMs.containsKey(SelectedCourse)) {
            MapCourse2VMs.put(SelectedCourse, new ArrayList<>());
        }
        String systemType = params[0];
        VM newVM = new VM(systemType,  CurUserID, MapCourse2VMs.get(SelectedCourse).size() + 1);
        MapCourse2VMs.get(SelectedCourse).add(newVM);
        MapStudentAndCourse2VM.put(CurUserID + SelectedCourse, newVM);

        System.out.println("requestVM success");
    }

    public static void HandleClearVM(String[] params) {
        if (!CheckArgumentsNum(params, 1)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (!(CheckIsTeacher(CurUserID)|| CheckIsAssistant(CurUserID))) {
            System.out.println("permission denied");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }


        int id = Integer.valueOf(params[0]);
        VM toDelete = MapCourse2VMs.get(SelectedCourse).remove(id - 1);
        if (toDelete.equals(MapStudentAndCourse2VM.get(toDelete.owner + SelectedCourse))) {
            MapStudentAndCourse2VM.remove(toDelete.owner + SelectedCourse);
        }
        System.out.println("clear "  + toDelete.type + " success");
    }

    public static void HandleStartVM(String[] params) {
        if (!CheckArgumentsNum(params, 0)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        VM vm = MapStudentAndCourse2VM.get(CurUserID + SelectedCourse);
        if (vm == null) {
            System.out.println("no VM");
            return;
        } else {
            System.out.println("welcome to " + vm.type);
        }


        String argStr = "";
        while (true) {
            argStr = in.nextLine();
            if (argStr.equals("EOF")) {
                System.out.println("quit " + vm.type);
                break;
            } else {
                vm.log.add(argStr);
            }
        }
    }

    public static void HandleLogVM(String[] params) {
        if (!CheckArgumentsNum(params, 0)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        VM vm = MapStudentAndCourse2VM.get(CurUserID + SelectedCourse);
        if (vm == null) {
            System.out.println("no log");
            return;
        }

        if (vm.log.isEmpty()) {
            System.out.println("no log");
            return;
        }

        for (String s : vm.log) {
            System.out.println(s);
        }
    }

    public static void HandleUploadVM(String[] params) throws Exception{
        if (!CheckArgumentsNum(params, 1)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        String path = params[0];
        File f = new File(path);

        //判断目录是否存在
        if (!f.getParentFile().exists()){
            f.getParentFile().mkdirs();
        }
        //判断文件是否存在
        if (!f.exists()){
            f.createNewFile();
        }

        OutputStream outputStream = new FileOutputStream(f);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(MapStudentAndCourse2VM.get(CurUserID + SelectedCourse));
        objectOutputStream.close();
        outputStream.close();

        System.out.println("uploadVM success");
    }

    public static void HandleDownloadVM(String[] params) throws Exception{
        if (!CheckArgumentsNum(params, 1)) {
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        if (SelectedCourse.isEmpty()) {
            System.out.println("no course selected");
            return;
        }

        String path = params[0];
        InputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object obj = objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();

        VM vm = (VM) obj;
        vm.owner = CurUserID;
        if (!MapCourse2VMs.containsKey(SelectedCourse)) {
            MapCourse2VMs.put(SelectedCourse, new ArrayList<>());
        }
        vm.id = MapCourse2VMs.get(SelectedCourse).size() + 1;
        MapCourse2VMs.get(SelectedCourse).add(vm);
        MapStudentAndCourse2VM.put(CurUserID + SelectedCourse, vm);

        System.out.println("downloadVM success");
    }

    public static void main(String[] args) {

        String argStr = "";
        while (true) {
            try {
                argStr = in.nextLine();
                if (argStr.equals("QUIT")) {
                    System.out.println("----- Good Bye! -----");
                    break;
                }
                String[] param = argStr.split("\\s+");
                String command = param[0];
                String[] params;
                if (param.length > 1) {
                    params = new String[param.length - 1];
                } else {
                    params = new String[]{};
                }
                System.arraycopy(param, 1, params, 0, params.length);
                switch (command) {
                    case "register": {
                        HandleRegister(params);
                        break;
                    }

                    case "login": {
                        HandleLogin(params);
                        break;
                    }

                    case "logout": {
                        HandleLogout(params);
                        break;
                    }
                    case "printInfo": {
                        HandlePrintInfo(params);
                        break;
                    }
                    case "addCourse": {
                        HandleAddCourse(params);
                        break;
                    }
                    case "removeCourse": {
                        HandleRemoveCourse(params);
                        break;
                    }
                    case "listCourse": {
                        HandleListCourse(params);
                        break;
                    }
                    case "selectCourse": {
                        HandleSelectCourse(params);
                        break;
                    }
                    case "addAdmin": {
                        HandleAddAdmin(params);
                        break;
                    }
                    case "removeAdmin": {
                        HandleRemoveAdmin(params);
                        break;
                    }
                    case "listAdmin": {
                        HandleListAdmin(params);
                        break;
                    }
                    case "changeRole": {
                        HandleChangeRole(params);
                        break;
                    }
                    case "addWare": {
                        HandleAddWare(params);
                        break;
                    }
                    case "removeWare": {
                        HandleRemoveWare(params);
                        break;
                    }
                    case "listWare": {
                        HandleListWare(params);
                        break;
                    }
                    case "addTask": {
                        HandleAddTask(params);
                        break;
                    }
                    case "removeTask": {
                        HandleRemoveTask(params);
                        break;
                    }
                    case "listTask": {
                        HandleListTask(params);
                        break;
                    }
                    case "addStudent": {
                        HandleAddStudents(params);
                        break;
                    }
                    case "removeStudent": {
                        HandleRemoveStudent(params);
                        break;
                    }
                    case "listStudent": {
                        HandleListStudent(params);
                        break;
                    }
                    case "requestVM": {
                        HandleRequestVM(params);
                        break;
                    }
                    case "startVM": {
                        HandleStartVM(params);
                        break;
                    }
                    case "logVM": {
                        HandleLogVM(params);
                        break;
                    }
                    case "uploadVM": {
                        HandleUploadVM(params);
                        break;
                    }
                    case "downloadVM": {
                        HandleDownloadVM(params);
                        break;
                    }
                    case "clearVM": {
                        HandleClearVM(params);
                        break;
                    }
                    default: {
                        System.out.println("command " + "\'" + command + "\'" + " not found");
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println("err: " + argStr);
                System.out.println(ex.getMessage());
                System.out.println(ex.getStackTrace());;
                continue;
            }
        }
    }
}

