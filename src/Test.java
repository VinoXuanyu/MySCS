import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Test {
    public static boolean LoggedIn = false;
    public static String CurUserID = "";
    public static HashMap<String, Person> MapID2Person = new HashMap<>();

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

    public static void HandleRegister(String[] params) {
        if (params.length != 7) {
            System.out.println("arguments illegal");
            return;
        }

        String ID = params[1];
        String FirstName = params[2];
        String SecondName = params[3];
        String Email = params[4];
        String Password = params[5];
        String PasswordAgain = params[6];

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
        if (params.length != 3) {
            System.out.println("arguments illegal");
            return;
        }

        if (LoggedIn) {
            System.out.println("already logged in");
            return;
        }

        String ID = params[1];
        String Password = params[2];

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
        }

        LoggedIn = true;
        CurUserID = ID;
    }

    public static void HandleLogout(String[] params) {
        if (params.length != 1) {
            System.out.println("arguments illegal");
            return;
        }

        if (!LoggedIn) {
            System.out.println("not logged in");
            return;
        }

        System.out.println("Bye~");

        LoggedIn = false;
        CurUserID = "";

    }

    public static void HandlePrintInfo(String[] params) {
        if (!(params.length == 1 || params.length == 2)) {
            System.out.println("arguments illegal");
            return;
        }

        String ID = "";
        if (params.length == 2) {
            ID = params[1];
        }

        if (!LoggedIn) {
            System.out.println("login first");
            return;
        }

        if (!ID.equals("") && MapID2Person.get(CurUserID).Type.equals("S")) {
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

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String argStr = "";
        while (true) {
            try {
                argStr = in.nextLine();
                if (argStr.equals("QUIT")) {
                    System.out.println("----- Good Bye! -----");
                    break;
                }
                String[] params = argStr.split("\\s+");
                String command = params[0];

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
                    default: {
                        System.out.println("command " + "\'" + command + "\'" + " not found");
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println(argStr);
                continue;
            }
        }
    }


}

