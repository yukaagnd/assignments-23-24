package assignments.assignment4;
//./gradlew :assignment4:run --console plain

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import assignments.assignment4.program.User;
import assignments.assignment4.program.Restaurant;
import assignments.assignment4.components.form.LoginForm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import assignments.assignment4.program.systemCLI.CreditCardPayment;
import assignments.assignment4.program.systemCLI.DebitPayment;


public class MainApp extends Application {

    private Stage window;
    private Map<String, Scene> allScenes = new HashMap<>();
    private Scene currentScene;
    private static User user;
    public static ArrayList<User> userList;
    private static ArrayList<Restaurant> restoList = new ArrayList<>();

    // Method titik masuk untuk aplikasi JavaFX.
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("DepeFood Ordering System");
        initUser(); // Initialize users

        // Inisialisasi semua adegan
        Scene loginScene = new LoginForm(window, this).getScene();

        // Menambahkan adegan login ke peta semua adegan
        allScenes.put("Login", loginScene);

        // Mengatur adegan awal aplikasi ke adegan login
        setScene(loginScene);
        window.show();
    }

    public void setUser(User newUser) {
        user = newUser;
    }

    public static User getUser() {
        return user;
    }

    public void setScene(Scene scene) {
        window.setScene(scene);
        currentScene = scene;
    }

    public Scene getScene(String sceneName) {
        return allScenes.get(sceneName);
    }

    public void addScene(String sceneName, Scene scene){
        allScenes.put(sceneName, scene);
    }

    public static ArrayList<Restaurant> getRestoList() {
        return restoList;
    }

    //  Melakukan logout pengguna dan kembali ke adegan login.
    public void logout() {
        setUser(null); // Menghapus pengguna saat ini
        setScene(getScene("Login")); // Beralih ke adegan login
    }

    public static void main(String[] args) {
        launch(args);
    }

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
    
}


