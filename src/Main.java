import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = Authenticator.login(username, password);

        if (user != null) {
            user.showMenu();
        }

        sc.close();
    }
}
