package assignments.assignment4.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import assignments.assignment4.program.Menu;
import assignments.assignment4.program.Order;
import assignments.assignment4.program.User;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.*;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Optional;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BillPrinter {
    private Stage stage;
    private MainApp mainApp;
    private User user;

    protected String backgroundColor = ("#f2ebdf");
    protected Color titleColor = Color.web("#d95964");
    protected String textColor = "195942ff";
    protected String buttolColor = "f2a29bff";
    protected Color textFillColor = Color.web("#195942");

    public BillPrinter(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
    }

    private Scene createBillPrinterForm() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Lihat Bill");
        titleLabel.setFont(Font.font("Inter", FontWeight.BOLD, 30));
        titleLabel.setTextFill(Color.web("#20248D"));
    
        Label orderIdLabel = new Label("Order ID");
        TextField orderIdInput = new TextField();
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Kembali");

        submitButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        submitButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");
        backButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
        backButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");
    
        submitButton.setOnAction(e -> {
            String orderId = orderIdInput.getText().trim();
            printBill(orderId);
        });
    
        backButton.setOnAction(e -> stage.setScene(new CustomerMenu(stage, mainApp, user).createBaseMenu()));
    
        layout.getChildren().addAll(titleLabel, orderIdLabel, orderIdInput, submitButton, backButton);
        return new Scene(layout, 400, 800);
    }
    
    private void printBill(String orderId) {
        Order order = findUserOrderById(orderId);
    
        if (order == null) {
            showAlert("Peringatan", "Order ID tidak ditemukan!", "Masukkan Order ID yang valid.", Alert.AlertType.WARNING);
        } else {
            VBox billLayout = new VBox(10);
            billLayout.setPadding(new Insets(20));
            billLayout.setAlignment(Pos.CENTER);
    
            DecimalFormat decimalFormat = new DecimalFormat();
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            decimalFormat.setDecimalFormatSymbols(symbols);
    
            String pesananOutput = getMenuPesananOutput(order);
    
            Label orderIdLabel = new Label("Order ID: " + order.getOrderId());
            orderIdLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
            orderIdLabel.setTextFill(textFillColor);
            
            Label tanggalPemesananLabel = new Label("Tanggal Pemesanan: " + order.getTanggal());
            tanggalPemesananLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
            tanggalPemesananLabel.setTextFill(textFillColor);
            
            Label lokasiPengirimanLabel = new Label("Lokasi Pengiriman: " + mainApp.getUser().getLokasi());
            lokasiPengirimanLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
            lokasiPengirimanLabel.setTextFill(textFillColor);
            
            Label statusPengirimanLabel = new Label("Status Pengiriman: " + (!order.getOrderFinished() ? "Not Finished" : "Finished"));
            statusPengirimanLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
            statusPengirimanLabel.setTextFill(textFillColor);
            
            Label pesananLabel = new Label("Pesanan:\n" + pesananOutput);
            pesananLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
            pesananLabel.setTextFill(textFillColor);
            
            Label biayaOngkosKirimLabel = new Label("Biaya Ongkos Kirim: Rp " + decimalFormat.format(order.getOngkir()));
            biayaOngkosKirimLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
            biayaOngkosKirimLabel.setTextFill(textFillColor);
            
            Label totalBiayaLabel = new Label("Total Biaya: Rp " + decimalFormat.format(order.getTotalHarga()));
            totalBiayaLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 15));
            totalBiayaLabel.setTextFill(textFillColor);
            
            Button backButton = new Button("Kembali");
            backButton.setFont(Font.font("Inter", FontWeight.NORMAL, 17));
            backButton.setStyle("-fx-background-color: " + buttolColor + "; -fx-text-fill: #" + textColor + ";");
            backButton.setOnAction(e -> stage.setScene(new CustomerMenu(stage, mainApp, user).createBaseMenu()));            
    
            billLayout.getChildren().addAll(orderIdLabel, tanggalPemesananLabel, lokasiPengirimanLabel, statusPengirimanLabel, pesananLabel, biayaOngkosKirimLabel, totalBiayaLabel, backButton);
            stage.setScene(new Scene(billLayout, 400, 800));
        }
    }
    
    private void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    // Tempatkan metode getMenuPesananOutput di sini jika belum ada
    protected static String getMenuPesananOutput(Order order){
        StringBuilder pesananBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols);
        for (Menu menu : order.getSortedMenu()) {
            pesananBuilder.append("- ").append(menu.getNamaMakanan()).append(" ").append(decimalFormat.format(menu.getHarga())).append("\n");
        }
        if (pesananBuilder.length() > 0) {
            pesananBuilder.deleteCharAt(pesananBuilder.length() - 1);
        }
        return pesananBuilder.toString();
    }

    public Scene getScene() {
        return this.createBillPrinterForm();
    }

    public class MenuItem {
        private final StringProperty itemName;
        private final StringProperty price;

        public MenuItem(String itemName, String price) {
            this.itemName = new SimpleStringProperty(itemName);
            this.price = new SimpleStringProperty(price);
        }

        public StringProperty itemNameProperty() {
            return itemName;
        }

        public StringProperty priceProperty() {
            return price;
        }

        public String getItemName() {
            return itemName.get();
        }

        public String getPrice() {
            return price.get();
        }
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
