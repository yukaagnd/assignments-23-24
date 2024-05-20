package assignments.assignment4.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import assignments.assignment4.program.Restaurant;
import assignments.assignment4.program.User;
import assignments.assignment4.program.Menu;
import assignments.assignment4.MainApp;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Kelas AdminMenu menangani antarmuka pengguna dan logika untuk admin.
 * Ini memungkinkan admin menambah restoran, menambah menu, dan melihat daftar restoran.
 */

public class AdminMenu extends MemberMenu {
    private Stage stage;
    private Scene scene;
    private User user;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private static ArrayList<Restaurant> restoList = MainApp.getRestoList();
    private MainApp mainApp; // Reference to MainApp instance
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ListView<String> menuItemsListView = new ListView<>();

    protected String backgroundColor = ("#f2ebdf");
    protected Color titleColor = Color.web("#d95964");
    protected String textColor = "195942ff";
    protected String buttolColor = "f2a29bff";
    protected Color textFillColor = Color.web("#195942");

    public AdminMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();
    }

     /**
     * Membuat formulir untuk antarmuka menu utama admin.
     *
     * @return Scene untuk formulir menu utama admin.
     */
    @Override
    public Scene createBaseMenu() {
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: #f2ebdf;");
    

        Label titleLabel = new Label("Hello Admin");
        titleLabel.setFont(Font.font("Inter", FontWeight.BOLD, 30));
        titleLabel.setTextFill(titleColor);

        Button addRestaurantButton = new Button("Tambah Restoran");
        addRestaurantButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        addRestaurantButton.setStyle("-fx-background-color: #" + buttolColor +"; -fx-text-fill: #"+ textColor +";");
        addRestaurantButton.setOnAction(e -> stage.setScene(addRestaurantScene));

        Button addMenuButton = new Button("Tambah Menu Restoran");
        addMenuButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        addMenuButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");
        addMenuButton.setOnAction(e -> stage.setScene(addMenuScene));

        Button viewRestaurantsButton = new Button("Lihat Daftar Restoran");
        viewRestaurantsButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        viewRestaurantsButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");
        viewRestaurantsButton.setOnAction(e -> stage.setScene(viewRestaurantsScene));

        Button logoutButton = new Button("Logout");
        logoutButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        logoutButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");
        logoutButton.setOnAction(e -> mainApp.logout());

        menuLayout.getChildren().addAll(titleLabel, addRestaurantButton, addMenuButton, viewRestaurantsButton, logoutButton);
        Scene scene = new Scene(menuLayout, 400, 800);
        stage.setScene(scene); // Set the scene to the stage here
        return scene;
    }

    private Scene createAddRestaurantForm() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: " + backgroundColor);

        Label titleLabel = new Label("Tambah Restoran");
        titleLabel.setFont(Font.font("Inter", FontWeight.BOLD, 30));
        titleLabel.setTextFill(Color.web("#20248D"));

        Label nameLabel = new Label("Restaurant Name:");
        nameLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
        nameLabel.setTextFill(textFillColor);

        TextField nameInput = new TextField();
        nameInput.setFont(Font.font("Inter", 15));

        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        submitButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");

        Button backButton = new Button("Kembali");
        backButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        backButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");

        submitButton.setOnAction(e -> {
            String nama = nameInput.getText().trim();
            String validName = getValidRestaurantName(nama);

            if (validName != null) {
                handleTambahRestoran(validName);
                nameInput.clear();
            } else {
                nameInput.clear(); // Clear text field for re-input
            }
        });

        backButton.setOnAction(e -> stage.setScene(createBaseMenu()));

        layout.getChildren().addAll(titleLabel, nameLabel, nameInput, submitButton, backButton);

        return new Scene(layout, 400, 800);
    }

    private Scene createAddMenuForm() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: " + backgroundColor);
    
        Label titleLabel = new Label("Lihat Daftar Restoran");
        titleLabel.setFont(Font.font("Inter", FontWeight.BOLD, 30));
        titleLabel.setTextFill(Color.web("#20248D"));

        Label restaurantLabel = new Label("Restaurant Name");
        restaurantLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
        restaurantLabel.setTextFill(textFillColor);

        TextField restaurantInput = new TextField();
        restaurantInput.setFont(Font.font("Inter", 15));

        Label itemNameLabel = new Label("Menu Item Name");
        itemNameLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
        itemNameLabel.setTextFill(textFillColor);

        TextField itemNameInput = new TextField();
        itemNameInput.setFont(Font.font("Inter", 15));

        Label priceLabel = new Label("Price");
        priceLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
        priceLabel.setTextFill(textFillColor);

        TextField priceInput = new TextField();
        priceInput.setFont(Font.font("Inter", 15));

        Button submitButton = new Button("Add Menu Item");
        submitButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        submitButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");

        Button backButton = new Button("Kembali");
        backButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        backButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");

        submitButton.setOnAction(e -> {
            String restaurantName = restaurantInput.getText().trim();
            String itemName = itemNameInput.getText().trim();
            String priceText = priceInput.getText().trim();

            if (restaurantName.isEmpty() || itemName.isEmpty() || priceText.isEmpty()) {
                showAlert("Peringatan", null, "Semua field harus diisi!", AlertType.WARNING);
                return;
            }

            Restaurant restaurant = restoList.stream()
                    .filter(r -> r.getNama().equalsIgnoreCase(restaurantName))
                    .findFirst()
                    .orElse(null);

            if (restaurant == null) {
                showAlert("Peringatan", null, "Restoran tidak ditemukan!", AlertType.WARNING);
                restaurantInput.clear();
                itemNameInput.clear();
                priceInput.clear();
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                handleTambahMenuRestoran(restaurant, itemName, price);
                restaurantInput.clear();
                itemNameInput.clear();
                priceInput.clear();
            } catch (NumberFormatException ex) {
                showAlert("Peringatan", null, "Harga harus berupa angka!", AlertType.WARNING);
                priceInput.clear();
            }
        });

        backButton = new Button("Kembali");
        backButton.setOnAction(e -> stage.setScene(scene));

        layout.getChildren().addAll(titleLabel, restaurantLabel, restaurantInput, itemNameLabel, itemNameInput, priceLabel, priceInput, submitButton, backButton);

        return new Scene(layout, 400, 800);
    }
    
    
    private Scene createViewRestaurantsForm() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: " + backgroundColor);
    
        Label titleLabel = new Label("Lihat Daftar Restoran");
        titleLabel.setFont(Font.font("Inter", FontWeight.BOLD, 30));
        titleLabel.setTextFill(Color.web("#20248D"));
    
        Label nameLabel = new Label("Nama Restoran");
        nameLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
        nameLabel.setTextFill(textFillColor);
    
        TextField nameInput = new TextField();
        nameInput.setFont(Font.font("Inter", 15));
    
        Button searchButton = new Button("Search");
        searchButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        searchButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");
        searchButton.setOnAction(e -> {
            String restaurantName = nameInput.getText().trim();
            if (restaurantName.isEmpty()) {
                showAlert("Peringatan",null , "Nama restoran harus diisi!", AlertType.WARNING);
                menuItemsListView.getItems().clear();
                return;
            }
    
            Restaurant restaurant = restoList.stream()
                    .filter(r -> r.getNama().equalsIgnoreCase(restaurantName))
                    .findFirst()
                    .orElse(null);
    
            if (restaurant == null) {
                showAlert("Peringatan", null, "Restoran tidak ditemukan!", AlertType.WARNING);
                nameInput.clear();
                menuItemsListView.getItems().clear();
                return;
            }
    
            // Menggunakan metode printMenu untuk mendapatkan daftar menu restoran
            String menuString = restaurant.printMenu();
            menuItemsListView.setItems(FXCollections.observableArrayList(menuString.split("\n")));
        });
    
        Button backButton = new Button("Kembali");
        backButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        backButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");
        backButton.setOnAction(e -> {
            nameInput.clear();
            stage.setScene(scene);
        });

        menuItemsListView.getItems().clear();
        layout.getChildren().addAll(titleLabel, nameLabel, nameInput, searchButton, menuItemsListView, backButton);
        return new Scene(layout, 400, 800);
    }
       

    private void handleTambahRestoran(String nama) {
        Restaurant restaurant = new Restaurant(nama);
        restoList.add(restaurant);
        showAlert("Pendaftaran Berhasil", null, "Restaurant " + restaurant.getNama() + " berhasil terdaftar.", AlertType.INFORMATION);
        stage.setScene(scene);
    }

    private String getValidRestaurantName(String nama) {
        boolean isRestaurantExist = restoList.stream()
                .anyMatch(restoran -> restoran.getNama().toLowerCase().equals(nama.toLowerCase()));
        boolean isRestaurantNameLengthValid = nama.length() >= 4;
    
        if (isRestaurantExist) {
            showAlert("Peringatan", null, String.format("Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda!", nama), AlertType.WARNING);
            return null;
        } else if (!isRestaurantNameLengthValid) {
            showAlert("Peringatan", null, "Nama Restoran tidak valid! Minimal 4 karakter diperlukan.", AlertType.WARNING);
            return null;
        } else {
            return nama;
        }
    }
    

    private void handleTambahMenuRestoran(Restaurant restaurant, String itemName, double price) {
        if (restaurant == null || itemName.isEmpty() || price <= 0) {
            showAlert("Peringatan", null, "Restoran tidak ditemukan atau input tidak valid!", AlertType.WARNING);
        } else {
            restaurant.addMenu(new Menu(itemName, price));
            showAlert("Pendaftaran Berhasil", null, "Menu Item added successfully", AlertType.INFORMATION);
            stage.setScene(scene);
        }
    }

    public static List<Restaurant> getRestoList() {
        return restoList;
    }
}
