package assignments.assignment3.systemCLI;

import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import assignments.assignment3.MainMenu;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.payment.DepeFoodPaymentSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import assignments.assignment3.Order;
import assignments.assignment3.Restaurant;
import assignments.assignment3.Menu;
import assignments.assignment3.User;
import assignments.assignment3.MainMenu;
import assignments.assignment3.OrderGenerator;

// Kelas untuk mengelola sistem bagi pelanggan
public class CustomerSystemCLI extends UserSystemCLI{
    protected static Scanner input = new Scanner(System.in);
    // Method untuk menangani perintah dalam menu pelanggan
    @Override
    protected boolean handleMenu(int choice){
        switch(choice){
            case 1 -> handleBuatPesanan();
            case 2 -> handleCetakBill();
            case 3 -> handleLihatMenu();
            case 4 -> handleBayarBill();
            case 5 -> handleCekSaldo();
            case 6 -> {
                return false;
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

   // Method untuk menampilkan menu pelanggan
    @Override
    protected void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    // Method untuk menangani pembuatan pesanan oleh pelanggan
    protected static void handleBuatPesanan(){
        System.out.println("--------------Buat Pesanan----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = getRestaurantByName(restaurantName);
            if(restaurant == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalPemesanan = input.nextLine().trim();
            if(!OrderGenerator.validateDate(tanggalPemesanan)){
                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)");
                System.out.println();
                continue;
            }
            System.out.print("Jumlah Pesanan: ");
            int jumlahPesanan = Integer.parseInt(input.nextLine().trim());
            System.out.println("Order: ");
            List<String> listMenuPesananRequest = new ArrayList<>();
            for(int i=0; i < jumlahPesanan; i++){
                listMenuPesananRequest.add(input.nextLine().trim());
            }
            if(! validateRequestPesanan(restaurant, listMenuPesananRequest)){
                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                continue;
            };
            Order order = new Order(
                    OrderGenerator.generateOrderID(restaurantName.toUpperCase(), tanggalPemesanan, MainMenu.userLoggedIn.getNomorTelepon()),
                    tanggalPemesanan, 
                    OrderGenerator.calculateDeliveryCost(MainMenu.userLoggedIn.getLokasi()), 
                    restaurant, 
                    getMenuRequest(restaurant, listMenuPesananRequest));
            System.out.printf("Pesanan dengan ID %s diterima!", order.getOrderId().toUpperCase());
            MainMenu.userLoggedIn.addOrderHistory(order);
            return;
        }
    }

    // Method untuk memvalidasi pesanan yang diminta oleh pelanggan
    protected static boolean validateRequestPesanan(Restaurant restaurant, List<String> listMenuPesananRequest){
        return listMenuPesananRequest.stream().allMatch(pesanan -> 
            restaurant.getMenu().stream().anyMatch(menu -> menu.getNamaMakanan().equals(pesanan))
        );
    }

    // Method untuk mendapatkan menu yang diminta oleh pelanggan
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

    // Method untuk mendapatkan output pesanan
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

    // Method untuk mendapatkan output tagihan pesanan
    protected static String outputBillPesanan(Order order) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);
        return String.format("Bill:%n" +
                         "Order ID: %s%n" +
                         "Tanggal Pemesanan: %s%n" +
                         "Lokasi Pengiriman: %s%n" +
                         "Status Pengiriman: %s%n"+
                         "Pesanan:%n%s%n"+
                         "Biaya Ongkos Kirim: Rp %s%n"+
                         "Total Biaya: Rp %s%n",
                         order.getOrderId(),
                         order.getTanggal(),
                         MainMenu.userLoggedIn.getLokasi(),
                         !order.getOrderFinished()? "Not Finished":"Finished",
                         getMenuPesananOutput(order),
                         decimalFormat.format(order.getOngkir()),
                         decimalFormat.format(order.getTotalHarga())
                         );
    }
    // Method untuk mendapatkan restoran berdasarkan nama
    protected static Restaurant getRestaurantByName(String name){
        Optional<Restaurant> restaurantMatched = MainMenu.restoList.stream().filter(restoran -> restoran.getNama().toLowerCase().equals(name.toLowerCase())).findFirst();
        if(restaurantMatched.isPresent()){
            return restaurantMatched.get();
        }
        return null;
    }

    // Method untuk menangani pencetakan tagihan pesanan
    protected static void handleCetakBill(){
        System.out.println("--------------Cetak Bill----------------");
        while (true) {
            System.out.print("Masukkan Order ID: ");
            String orderId = input.nextLine().trim();
            Order order = getOrderOrNull(orderId);
            if(order == null){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            System.out.println("");
            System.out.print(outputBillPesanan(order));
            return;
        }
        
    }

    // Method untuk mendapatkan pesanan berdasarkan ID
    protected static Order getOrderOrNull(String orderId) {
        for (User user : MainMenu.userList) {
            for (Order order : user.getOrderHistory()) {
                if (order.getOrderId().equals(orderId)) {
                    return order;
                }
            }
        }
        return null;
    }

    // Method untuk menangani melihat menu restoran
    protected static void handleLihatMenu(){
        System.out.println("--------------Lihat Menu----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = getRestaurantByName(restaurantName);
            if(restaurant == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print(restaurant.printMenu());
            return;
        }
    }

    protected static void handleUpdateStatusPesanan(){
        System.out.println("--------------Update Status Pesanan---------------");
        while (true) {
            System.out.print("Order ID: ");
            String orderId = input.nextLine().trim();
            Order order = getOrderOrNull(orderId);
            if(order == null){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            System.out.print("Status: ");
            String newStatus = input.nextLine().trim();
            if(newStatus.toLowerCase().equals("SELESAI".toLowerCase())){
                if(order.getOrderFinished() == true){
                    System.out.printf("Status pesanan dengan ID %s tidak berhasil diupdate!", order.getOrderId());
                }
                else{
                    System.out.printf("Status pesanan dengan ID %s berhasil diupdate!", order.getOrderId());
                    order.setOrderFinished(true);
                }
            }
            return;
        }

    }

    // Method untuk menangani pembayaran tagihan pesanan
    protected void handleBayarBill(){
        System.out.println("--------------Bayar Bill---------------");
        while (true) {
            System.out.print("Order ID: ");
            String orderId = input.nextLine().trim();
            Order order = getOrderOrNull(orderId);
            if(order == null){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            if(order.getOrderFinished()){
                System.out.println("Pesanan dengan ID ini sudah lunas!");
                continue;
            }

            System.out.println(outputBillPesanan(order));

            System.out.println("Opsi Pembayaran:");
            System.out.println("1. Credit Card");
            System.out.println("2. Debit");
            System.out.print("Pilihan Metode Pembayaran: ");
            int choice = Integer.parseInt(input.nextLine().trim());

            if (choice == 1){
                if (!(MainMenu.userLoggedIn.getPayment() instanceof CreditCardPayment)) {
                    System.out.println("User belum memiliki metode pembayaran ini!");
                    continue;
                }
                handleCreditCardPayment(order);
                return;
            }
            else if (choice == 2){
                if (!(MainMenu.userLoggedIn.getPayment() instanceof DebitPayment)) {
                    System.out.println("User belum memiliki metode pembayaran ini!");
                    continue;
                }
                handleDebitPayment(order);
                return;
            }
            else{
                System.out.println("Pilihan metode pembayaran tidak valid.");
                return;
            }
        }
    }

    // Method untuk menangani pembayaran dengan kartu kredit
    protected void handleCreditCardPayment(Order order) {
        CreditCardPayment creditCardPayment = new CreditCardPayment();
        long totalBiaya = (long) order.getTotalHarga();
        long totalBayar = (long) creditCardPayment.processPayment(totalBiaya);
        long transactionFee = (long) totalBayar - totalBiaya;
    
        if (totalBayar > MainMenu.userLoggedIn.getSaldo()) {
            System.out.println("Saldo tidak mencukupi, mohon menggunakan metode pembayaran lain.");
            return;
        }
    
        MainMenu.userLoggedIn.setSaldo((long) MainMenu.userLoggedIn.getSaldo() - totalBayar);
        order.setOrderFinished(true);
        Restaurant restaurant = order.getRestaurant();
        restaurant.setSaldo((long) restaurant.getSaldo() + totalBiaya);
        
        System.out.printf("Berhasil Membayar Bill sebesar Rp %d dengan biaya transaksi sebesar Rp %d%n", totalBiaya, transactionFee);
        return;
    }
    
    // Method untuk menangani pembayaran dengan kartu debit
    protected void handleDebitPayment(Order order) {
        DebitPayment debitPayment = new DebitPayment();
        long totalBayar = (long) debitPayment.processPayment((long) order.getTotalHarga());
        if (totalBayar == 0) {
            System.out.println("Total harga pesanan tidak memenuhi syarat atau saldo tidak mencukupi");
            return;
        }
        if (totalBayar > (long) MainMenu.userLoggedIn.getSaldo()) {
            System.out.println("Saldo tidak mencukupi, mohon menggunakan metode pembayaran lain.");
            return;
        }

        MainMenu.userLoggedIn.setSaldo((long) MainMenu.userLoggedIn.getSaldo() - totalBayar);
        order.setOrderFinished(true);
        Restaurant restaurant = order.getRestaurant();
        restaurant.setSaldo((long)restaurant.getSaldo() + (long) order.getTotalHarga());
    
        System.out.printf("Berhasil Membayar Bill sebesar Rp %d%n", totalBayar);
        return;
    }
    
    // Method untuk menangani pengecekan saldo
    protected void handleCekSaldo(){
        System.out.println("--------------Cek Saldo---------------\n");
        System.out.printf("Sisa saldo sebesar Rp %d%n", MainMenu.userLoggedIn.getSaldo());
    }
}
