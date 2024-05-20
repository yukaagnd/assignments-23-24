package assignments.assignment4.page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.program.Menu;
import assignments.assignment4.program.Restaurant;
import assignments.assignment4.program.User;
import assignments.assignment4.program.systemCLI.CreditCardPayment;
import assignments.assignment4.program.systemCLI.DebitPayment;
import assignments.assignment4.program.OrderGenerator;
import assignments.assignment4.program.Order;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter;
import assignments.assignment4.components.form.LoginForm;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Kelas CustomerMenu mewakili menu utama untuk pelanggan dalam aplikasi pemesanan makanan.
 * Kelas ini menyediakan fungsionalitas untuk membuat pesanan, mencetak tagihan, membayar tagihan, dan memeriksa saldo pengguna.
 */

public class CustomerMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private BillPrinter billPrinter; // Instance of BillPrinter
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private static Label label = new Label();
    private MainApp mainApp;
    private ArrayList<Restaurant> restoList = MainApp.getRestoList();
    private User user;

    protected String backgroundColor = ("#f2ebdf");
    protected Color titleColor = Color.web("#d95964");
    protected String textColor = "195942ff";
    protected String buttolColor = "f2a29bff";
    protected Color textFillColor = Color.web("#195942");

    /**
     * Konstruktor untuk kelas CustomerMenu.
     *
     * Menerima stage utama aplikasi, referensi ke instance MainApp, dan pengguna yang sedang masuk.
     */

    public CustomerMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        this.billPrinter = new BillPrinter(stage, mainApp, this.user); // Pass user to BillPrinter constructor
        this.printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();
    }

    @Override
    public Scene createBaseMenu() {
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: #f2ebdf;");

        Label titleLabel = new Label("Hello Customer");
        titleLabel.setFont(Font.font("Inter", FontWeight.BOLD, 30));
        titleLabel.setTextFill(titleColor);
    
        Button buatPesananButton = new Button("Buat Pesanan");
        buatPesananButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        buatPesananButton.setStyle("-fx-background-color: #" + buttolColor +"; -fx-text-fill: #"+ textColor +";");
        buatPesananButton.setOnAction(e -> stage.setScene(addOrderScene));
    
        Button cetakBillButton = new Button("Cetak Bill");
        cetakBillButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        cetakBillButton.setStyle("-fx-background-color: #" + buttolColor +"; -fx-text-fill: #"+ textColor +";");
        cetakBillButton.setOnAction(e -> stage.setScene(printBillScene));
    
        Button bayarBillButton = new Button("Bayar Bill");
        bayarBillButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        bayarBillButton.setStyle("-fx-background-color: #" + buttolColor +"; -fx-text-fill: #"+ textColor +";");
        bayarBillButton.setOnAction(e -> stage.setScene(payBillScene));
    
        Button cekSaldoButton = new Button("Cek Saldo");
        cekSaldoButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        cekSaldoButton.setStyle("-fx-background-color: #" + buttolColor +"; -fx-text-fill: #"+ textColor +";");
        cekSaldoButton.setOnAction(e -> stage.setScene(cekSaldoScene));
    
        Button logoutButton = new Button("Logout");
        logoutButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        logoutButton.setStyle("-fx-background-color: #" + buttolColor +"; -fx-text-fill: #"+ textColor +";");
        logoutButton.setOnAction(e -> mainApp.logout());
    
        menuLayout.getChildren().addAll(titleLabel, buatPesananButton, cetakBillButton, bayarBillButton, cekSaldoButton, logoutButton);
        Scene scene = new Scene (menuLayout, 400,800);
        stage.setScene(scene);
        return scene;
    }

    private Scene createTambahPesananForm() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: " + backgroundColor);

        Label titleLabel = new Label("Buat Pesanan");
        titleLabel.setFont(Font.font("Inter", FontWeight.BOLD, 30));
        titleLabel.setTextFill(Color.web("#20248D"));
    
        // ChoiceBox untuk memilih restoran
        Label restaurantLabel = new Label("Select Restaurant:");
        restaurantLabel.setFont(Font.font("Inter", 15));
        restoList.forEach(resto -> restaurantComboBox.getItems().add(resto.getNama()));
    
        // TextField untuk menerima tanggal
        Label dateLabel = new Label("Tanggal (DD/MM/YYYY):");
        dateLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
        dateLabel.setTextFill(textFillColor);

        TextField dateInput = new TextField();
        dateInput.setFont(Font.font("Inter", 15));

        // Container untuk menampilkan menu restoran
        VBox menuContainer = new VBox(10);
        menuContainer.setAlignment(Pos.CENTER);
    
        // Button untuk menampilkan menu
        Button menuButton = new Button("Menu");
        menuButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        menuButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");
        menuButton.setOnAction(e -> {
            String selectedRestaurant = restaurantComboBox.getValue();
            Restaurant restaurant = getRestaurantByName(selectedRestaurant);
            if (restaurant != null) {
                menuContainer.getChildren().clear();
                for (Menu menuItem : restaurant.getMenu()) {
                    CheckBox menuCheckBox = new CheckBox(menuItem.getNamaMakanan());
                    menuContainer.getChildren().add(menuCheckBox);
                }
            } else {
                showAlert("Peringatan", null, "Restoran tidak ditemukan!", AlertType.WARNING);
            }
        });
    
        // Button untuk membuat pesanan
        Button orderButton = new Button("Buat Pesanan");
        orderButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        orderButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");
        orderButton.setOnAction(e -> {
            String restaurantName = restaurantComboBox.getValue();
            String tanggalPemesanan = dateInput.getText().trim();
            List<String> menuItems = new ArrayList<>();
            for (Node node : menuContainer.getChildren()) {
                if (node instanceof CheckBox && ((CheckBox) node).isSelected()) {
                    menuItems.add(((CheckBox) node).getText());
                }
            }
            handleBuatPesanan(restaurantName, tanggalPemesanan, menuItems);
        });
    
        Button backButton = new Button("Kembali");
        backButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        backButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");
        backButton.setOnAction(e -> stage.setScene(createBaseMenu()));
    
        layout.getChildren().addAll(titleLabel, restaurantLabel, restaurantComboBox, dateLabel, dateInput, menuButton, menuContainer, orderButton, backButton);
        return new Scene(layout, 400, 800);
    }
    

    private Scene createBillPrinter() {
        return billPrinter.getScene();
    }
    
    private Scene createBayarBillForm() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: " + backgroundColor);

        Label titleLabel = new Label("Bayar Bill");
        titleLabel.setFont(Font.font("Inter", FontWeight.BOLD, 30));
        titleLabel.setTextFill(Color.web("#20248D"));

        Label orderIdLabel = new Label("Order ID:");
        orderIdLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
        orderIdLabel.setTextFill(textFillColor);

        TextField orderIdInput = new TextField();
        orderIdInput.setFont(Font.font("Inter", 15));

        Label paymentMethodLabel = new Label("Metode Pembayaran:");
        paymentMethodLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
        paymentMethodLabel.setTextFill(textFillColor);

        ComboBox<String> paymentMethodComboBox = new ComboBox<>();
        paymentMethodComboBox.getItems().addAll("Credit Card", "Debit");
    
        Button payButton = new Button("Bayar");
        payButton.setOnAction(e -> {
            String orderId = orderIdInput.getText().trim();
            String paymentMethod = paymentMethodComboBox.getValue();
            if (orderId.isEmpty() || paymentMethod == null) {
                showAlert("Peringatan", "Input Tidak Valid", "Order ID dan Metode Pembayaran harus diisi!", Alert.AlertType.WARNING);
            } else {
                int paymentChoice = paymentMethod.equals("Credit Card") ? 1 : 2;
                handleBayarBill(orderId, paymentChoice);
            }
        });
    
        Button backButton = new Button("Kembali");
        backButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        backButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");
        backButton.setOnAction(e -> stage.setScene(new CustomerMenu(stage, mainApp, user).createBaseMenu()));
    
        layout.getChildren().addAll(titleLabel, orderIdLabel, orderIdInput, paymentMethodLabel, paymentMethodComboBox, payButton, backButton);
        return new Scene(layout, 400, 600);
    }

    private Scene createCekSaldoScene() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: " + backgroundColor);

        Label titleLabel = new Label("Cek Saldo");
        titleLabel.setFont(Font.font("Inter", FontWeight.BOLD, 30));
        titleLabel.setTextFill(Color.web("#20248D"));

        Label saldoLabel = new Label("Saldo Anda: Rp. " + user.getSaldo());
        saldoLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
        saldoLabel.setTextFill(textFillColor);

        Button backButton = new Button("Kembali");
        backButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        backButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");
        backButton.setOnAction(e -> stage.setScene(createBaseMenu()));

        layout.getChildren().addAll(titleLabel, saldoLabel, backButton);

        return new Scene(layout, 400, 600);
    }

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        try {
            // Validasi tanggal
            if (!OrderGenerator.validateDate(tanggalPemesanan)) {
                showAlert("Peringatan", null, "Masukkan tanggal sesuai format (DD/MM/YYYY)", AlertType.WARNING);
                return;
            }
    
            // Validasi pemilihan restoran
            Restaurant restaurant = getRestaurantByName(namaRestoran);
            if (restaurant == null) {
                throw new IllegalArgumentException("Silakan pilih restoran yang valid!");
            }
    
            // Validasi pemilihan menu
            if (menuItems.isEmpty()) {
                throw new IllegalArgumentException("Silakan pilih minimal satu menu!");
            }
            String orderID  = OrderGenerator.generateOrderID(namaRestoran.toUpperCase(), tanggalPemesanan, user.getNomorTelepon());
            // Membuat pesanan
            Order order = new Order(orderID, tanggalPemesanan,  OrderGenerator.calculateDeliveryCost(user.getLokasi()), restaurant, getMenuRequest(restaurant, menuItems)
            );
            System.out.printf("Pesanan dengan ID %s diterima!", order.getOrderId().toUpperCase());
            user.addOrderHistory(order);
            showAlert("Sukses", null, ("Pesanan dengan ID"+ orderID + " berhasil dibuat!"), AlertType.INFORMATION);
            stage.setScene(scene);
        } catch (IllegalArgumentException e) {
            showAlert("Peringatan", null, e.getMessage(), AlertType.WARNING);
        } catch (Exception e) {
            showAlert("Peringatan", null, "Terjadi kesalahan dalam membuat pesanan: " + e.getMessage(), AlertType.ERROR);
        }
    }

    protected Restaurant getRestaurantByName(String name){
        Optional<Restaurant> restaurantMatched = AdminMenu.getRestoList().stream().filter(restoran -> restoran.getNama().toLowerCase().equals(name.toLowerCase())).findFirst();
        if(restaurantMatched.isPresent()){
            return restaurantMatched.get();
        }
        return null;
    }
    
    private void handleBayarBill(String orderID, int pilihanPembayaran) {
        try {
            Order order = findUserOrderById(orderID);
            if (order == null) {
                showAlert("Peringatan", "Order ID tidak ditemukan!", "Masukkan Order ID yang valid.", Alert.AlertType.WARNING);
                return;
            }
            if (order.getOrderFinished) {
                showAlert("Peringatan", "Order sudah selesai!", "Pengguna sudah melakukan pembayaran", Alert.AlertType.WARNING);
                return;
            }
            else{
                if (pilihanPembayaran == 1) {
                    if (!(user.getPayment() instanceof CreditCardPayment)) {
                        showAlert("Peringatan", "Metode pembayaran tidak tersedia!", "Pengguna belum memiliki metode pembayaran ini.", Alert.AlertType.WARNING);
                        return;
                    }
                    handleCreditCardPayment(order);
                } else if (pilihanPembayaran == 2) {
                    if (!(user.getPayment() instanceof DebitPayment)) {
                        showAlert("Peringatan", "Metode pembayaran tidak tersedia!", "Pengguna belum memiliki metode pembayaran ini.", Alert.AlertType.WARNING);
                        return;
                    }
                    handleDebitPayment(order);
                } else {
                    showAlert("Peringatan", "Metode Pembayaran tidak valid!", "Pilih metode pembayaran yang valid.", Alert.AlertType.WARNING);
                }
            }
        } catch (Exception e) {
            showAlert("Kesalahan", "Terjadi kesalahan saat memproses pembayaran!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    
    private void handleCreditCardPayment(Order order) {
        CreditCardPayment creditCardPayment = new CreditCardPayment();
        long totalBiaya = (long) order.getTotalHarga();
        long totalBayar = (long) creditCardPayment.processPayment(totalBiaya);
        long transactionFee = totalBayar - totalBiaya;
    
        if (totalBayar > user.getSaldo()) {
            showAlert("Peringatan", "Saldo tidak mencukupi!", "Mohon menggunakan metode pembayaran lain.", Alert.AlertType.WARNING);
            return;
        }
    
        user.setSaldo(user.getSaldo() - totalBayar);
        order.setOrderFinished(true);
        Restaurant restaurant = order.getRestaurant();
        restaurant.setSaldo(restaurant.getSaldo() + totalBiaya);
    
        showAlert("Sukses", "Pembayaran Berhasil", String.format("Berhasil Membayar Bill sebesar Rp %d dengan biaya transaksi sebesar Rp %d", totalBiaya, transactionFee), Alert.AlertType.INFORMATION);
        stage.setScene(scene);
    }
    
    private void handleDebitPayment(Order order) {
        DebitPayment debitPayment = new DebitPayment();
        long totalBayar = (long) debitPayment.processPayment((long) order.getTotalHarga());
    
        if (totalBayar == 0) {
            showAlert("Peringatan", "Pembayaran Gagal", "Total harga pesanan tidak memenuhi syarat atau saldo tidak mencukupi", Alert.AlertType.WARNING);
            return;
        }
        if (totalBayar > user.getSaldo()) {
            showAlert("Peringatan", "Saldo tidak mencukupi!", "Mohon menggunakan metode pembayaran lain.", Alert.AlertType.WARNING);
            return;
        }
    
        user.setSaldo(user.getSaldo() - totalBayar);
        order.setOrderFinished(true);
        Restaurant restaurant = order.getRestaurant();
        restaurant.setSaldo(restaurant.getSaldo() + (long) order.getTotalHarga());
    
        showAlert("Sukses", "Pembayaran Berhasil", String.format("Berhasil Membayar Bill sebesar Rp %d", totalBayar), Alert.AlertType.INFORMATION);
        stage.setScene(scene);
    }
    

    protected static Menu[] getMenuRequest(Restaurant restaurant, List<String> listMenuPesananRequest){
        Menu[] menu = new Menu[listMenuPesananRequest.size()];
        for(int i=0;i<menu.length;i++){
            for(Menu existMenu : restaurant.getMenu()){
                if(existMenu.getNamaMakanan().equals(listMenuPesananRequest.get(i))){
                    menu[i] = existMenu;
                }
            }
        }
        return menu;
    }

    public Order findUserOrderById(String orderId) {
        ArrayList<Order> orderHistory = user.getOrderHistory();
        
        for (Order order : orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                return order; 
            }   
        }
        return null; 
    }

}
