package assignments.assignment3.systemCLI;

import java.util.Scanner;

// Kelas abstrak untuk antarmuka baris perintah sistem pengguna
public abstract class UserSystemCLI {
    protected Scanner input = new Scanner(System.in);
    // Metode untuk menjalankan antarmuka baris perintah sistem pengguna
    public void run() {
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command = Integer.parseInt(input.nextLine());
            isLoggedIn = handleMenu(command);
        }
    }
    // Metode abstrak untuk menampilkan menu
    abstract void displayMenu();

    // Metode abstrak untuk menangani perintah menu
    abstract boolean handleMenu(int command);
}
