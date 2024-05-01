package assignments.assignment3.systemCLI;

import java.util.Scanner;

public abstract class UserSystemCLI {
    protected Scanner input = new Scanner(System.in);
    public void run() {
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command = Integer.parseInt(input.nextLine());
            isLoggedIn = handleMenu(command);
        }
    }
    abstract void displayMenu();
    abstract boolean handleMenu(int command);
}
