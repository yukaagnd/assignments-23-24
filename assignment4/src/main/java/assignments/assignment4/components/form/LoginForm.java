package assignments.assignment4.components.form;

import assignments.assignment4.program.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;
import assignments.assignment4.program.systemCLI.CreditCardPayment;
import assignments.assignment4.program.systemCLI.DebitPayment;

import java.util.ArrayList;

public class LoginForm {
    public static ArrayList<User> userList;
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameInput;
    private TextField phoneInput;

    public LoginForm(Stage stage, MainApp mainApp) { // Pass MainApp instance to constructor
        this.stage = stage;
        this.mainApp = mainApp; // Store MainApp instance
    }

    // Scene yang berisi formulir login.
    private Scene createLoginForm() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: #f2ebdf;");

        Text title = new Text("Welcome to DepeFood");
        title.setFont(Font.font("Inter", 36));
        title.setStyle("-fx-font-weight: bold; -fx-fill: #d95964;");
        grid.add(title, 0, 0, 2, 1); // Span across 2 columns

        Label nameLabel = new Label("Nama:");
        nameLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 20));
        nameLabel.setStyle("-fx-text-fill: #195942;");
        grid.add(nameLabel, 0, 1);

        nameInput = new TextField();
        grid.add(nameInput, 1, 1);

        Label phoneLabel = new Label("Nomor Telepon:");
        phoneLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 20));
        phoneLabel.setStyle("-fx-text-fill: #195942;");
        grid.add(phoneLabel, 0, 2);

        phoneInput = new TextField();
        grid.add(phoneInput, 1, 2);

        Button loginButton = new Button("Login");
        loginButton.setFont(Font.font("Inter"));
        loginButton.setStyle("-fx-background-color: #ffe2e9; -fx-text-fill: #195942;");
        grid.add(loginButton, 1, 3);
        GridPane.setHalignment(loginButton, HPos.RIGHT);

        loginButton.setOnAction(e -> handleLogin());

        return new Scene(grid, 400, 800);
    }

    // Menangani proses login.
    private void handleLogin() {
        initUser();
        String nama = nameInput.getText().trim();
        String noTelp = phoneInput.getText().trim();

        // Mendapatkan pengguna berdasarkan nama dan nomor telepon
        User userLoggedIn = getUser(nama, noTelp);

        if (userLoggedIn == null) {
            // Menampilkan peringatan jika pengguna tidak ditemukan
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Peringatan");
            alert.setHeaderText("Pengguna tidak ditemukan");
            alert.setContentText("Silakan periksa kembali nama dan nomor telepon Anda.");
            alert.showAndWait();
            // Membersihkan isian formulir login
            nameInput.clear();
            phoneInput.clear();
        } else {
            mainApp.setUser(userLoggedIn);
            // Memeriksa peran pengguna dan menjalankan sistem sesuai peran
            if (userLoggedIn.getRole().equals("Customer")) {
                new CustomerMenu(stage, mainApp, userLoggedIn).createBaseMenu(); // Menjalankan menu pelanggan
            } else if (userLoggedIn.getRole().equals("Admin")) {
                new AdminMenu(stage, mainApp, userLoggedIn).createBaseMenu(); // Menjalankan menu admin
            }
        }
    }

    // Method untuk inisialisasi daftar pengguna
    public static void initUser(){
        userList = new ArrayList<User>();
        userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer", new DebitPayment(), 500000));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer", new CreditCardPayment(), 2000000));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer", new DebitPayment(), 750000));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer", new CreditCardPayment(), 1800000));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer", new DebitPayment(), 650000));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
        userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
    }

    // Method untuk mendapatkan pengguna berdasarkan nama dan nomor telepon
    public static User getUser(String nama, String nomorTelepon){
        for(User user: userList){
            if(user.getNama().equals(nama.trim()) && user.getNomorTelepon().equals(nomorTelepon.trim())){
                return user;
            }
        }
        return null;
    }

    public Scene getScene(){
        return this.createLoginForm();
    }
    
}
