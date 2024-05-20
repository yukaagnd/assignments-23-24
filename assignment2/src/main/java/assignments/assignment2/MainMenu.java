package main.java.assignments.assignment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;

    public static void main(String[] args) {
        // Inisiasi list restaurant
        restoList = new ArrayList<Restaurant>();
        boolean programRunning = true;
        printHeader();
        // Memulai program utama
        while(programRunning){
            startMenu();
            int command = input.nextInt();
            input.nextLine();

            if(command == 1){
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                // Memvalidasi input login
                User userLoggedIn = getUser(nama, noTelp);
                boolean isLoggedIn = true;

                // Memastikan user sesuai dnegan data program
                if (userLoggedIn == null) {
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                    isLoggedIn = false;
                    continue;
                }

                // Apabila user sesuai maka akan mencetak pilihan command sesuari dengan rolenya (customer/admin)
                System.out.printf("Selamat Datang %s!", nama);
                if(userLoggedIn.getRole().equalsIgnoreCase("Customer")){
                    while (isLoggedIn){
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan(userLoggedIn);
                            case 2 -> handleCetakBill(userLoggedIn);
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan(userLoggedIn);
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }else{
                    while (isLoggedIn){
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            } else if(command == 2){
                programRunning = false;
            }else{
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood!");
    }
    // Method untuk mendapatkan daftar user dari userList
    public static User getUser(String nama, String nomorTelepon){
        initUser();
        for (User user : userList) {
            if (user.getNama().equalsIgnoreCase(nama) && user.getNomorTelepon().equalsIgnoreCase(nomorTelepon)) {
                return user;
            }
        }
        // Jika ditemukan akan mereturn user, jika tidak akan mereturn null
        return null;
    }

    // Method untuk handle pesanan customer
    public static void handleBuatPesanan(User user){
        // Daftar variabel yang dibutuhkan
        String namaResto, tanggalPemesanan, inputMakanan, orderID;
        boolean foundResto, foundMakanan;
        int jumlahPesanan, ongkir;
        String[] pesanan;

        System.out.println("---------------Buat Pesanan---------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            namaResto = input.nextLine();

            Restaurant restaurant = null;
            foundResto = false;

            // Memvalidasi apakah restaurant yang dimasukkan sudah terdaftar
            if (restoList != null) {
                for (Restaurant resto : restoList) {
                    if (resto.getNama().equalsIgnoreCase(namaResto)) {
                        foundResto = true;
                        restaurant = resto;
                        break;
                    }
                }
            }

            // Jika resto tidak ditemukan maka akan meminta input ulang
            if (!foundResto){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

            System.out.print("Tanggal Pemesanan (DD/MM/YYY): ");
            tanggalPemesanan = input.nextLine();
            // Jika tanggal tidak sesuai dengan format DD//MM/YYYY maka akan meminta input ulang
            if (tanggalPemesanan.length() != 10 || tanggalPemesanan.charAt(2) != '/' || tanggalPemesanan.charAt(5) != '/'){
                System.out.println("Tanggal pemesanan dalam format (DD/MM/YYYY) !\n");
                continue;
            }

            // Membuat ORDER ID menggunakan potongan code dari tp 1
            orderID = generateOrderID(namaResto, tanggalPemesanan, user.getNomorTelepon());
            ongkir = cekOngkir(user.getLokasi());
            Order order = new Order(orderID, tanggalPemesanan, ongkir, restaurant);

            System.out.print("Jumlah Pesanan: ");
            jumlahPesanan = Integer.parseInt(input.nextLine());
            System.out.println("Order: ");
            pesanan =  new String[jumlahPesanan];

            // Memasukkan daftar makanan yang di pesan ke array sementara
            for (int i=0; i<jumlahPesanan; i++){
                inputMakanan = input.nextLine();
                pesanan[i] = inputMakanan;
            }

            // Mengecek apakah menu yang dipesan sesuai dengan menu yang ada pada restoran yang dipilih
            boolean validMenu = true;
            for (String checkMakanan : pesanan) {
                foundMakanan = false;
                for (Menu menu : restaurant.getMenuList()) {
                    if (menu.getNamaMakanan().equalsIgnoreCase(checkMakanan)) {
                        // Jika ditemukan maka menu yang dipesan akan ditambahkan kepada list pesanan pada Class Order
                        order.addPesanan(menu);
                        foundMakanan = true;
                        break;
                    }
                }
                if (!foundMakanan) {
                    validMenu = false;
                    break;
                }
            }
            // Jika menu tidak ditemukan maka akan meminta input ulang
            if(!validMenu) {
                System.out.println("Mohon memesan menu yang tersedia di Restoran!\n");
                user.getOrderList().remove(order);
                continue;
            }
            // Jika semua input sudah valid maka order akan berhasil dan disimpan
            System.out.printf("Pesanan dengan ID %s diterima", orderID);
            user.addOrder(order);
            break;
        }
    }

    // Method untuk handle cetak bill customer
    public static void handleCetakBill(User user){
        String orderID;
        boolean validOrder = false;
        Order order = null;
        System.out.println("---------------Cetak Bill---------------");
        int totalHarga = 0;

        while(true) {
            System.out.print("Masukkan Order ID: ");
            orderID = input.nextLine();
            // Memvalidasi apakah restaurant yang dimasukkan sudah terdaftar
            for (Order currentOrder : user.getOrderList()) {
                if (currentOrder.getOrderId().equalsIgnoreCase(orderID)) {
                    validOrder = true;
                    order = currentOrder;
                    break;
                }
            }

            // Jika orderID tidak ditemukan maka akan meminta input ulang
            if (!validOrder){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }

            // Mencetak bill
            System.out.println("\nBill: ");
            System.out.printf("Order ID: %s\n", order.getOrderId());
            System.out.printf("Tanggal Pemesanan: %s\n", order.getTanggalPemesanan());
            System.out.printf("Restaurant: %s\n", order.getRestaurant().getNama());
            System.out.printf("Lokasi Pengiriman: %s\n", user.getLokasi());
            System.out.printf("Status Pengiriman: %s\n", order.getStatusPesanan());
            System.out.println("Pesanan:");
            for (Menu menu : order.getPesananMenuList()){
                System.out.printf("- %s %.0f\n", menu.getNamaMakanan(), menu.getHarga());
                totalHarga += (int) menu.getHarga();
            }
            System.out.printf("Biaya Ongkir: Rp %d\n", order.getBiayaOngkosKirim());
            totalHarga += order.getBiayaOngkosKirim();
            System.out.printf("Total Biaya: Tp %d\n", totalHarga);
            break;
        }
    }

    // Method untuk handle lihat menu
    public static void handleLihatMenu(){
        String namaResto;
        boolean foundResto = false;
        Restaurant restoNow = null;

        System.out.println("---------------Lihat Menu----------------");
        while(true){
            System.out.print("Nama Restoran: ");
            namaResto = input.nextLine();
            // Mengecek apakah nama Restoran valid
            for (Restaurant resto : restoList) {
                if (resto.getNama().equalsIgnoreCase(namaResto)) {
                    foundResto = true;
                    restoNow = resto;
                    break;
                }
            }
            // Jika Restoran tidak ditemukan maka akan meminta input ulang
            if (!foundResto) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            // Ngesort menu bedasaran harga, jika harga sama makan diurutkan bedasarkan huruf awal
            handleSortMenuByPriceAndName(restoNow.getMenuList());
            // Mencetak menu
            System.out.println("Menu:");
            int i = 1;
            for (Menu menu : restoNow.getMenuList()){
                System.out.printf("%d. %s %.0f\n", i, menu.getNamaMakanan(), menu.getHarga());
                i++;
            }
            break;
        }
    }

    // Method untuk handle update status pesanan
    public static void handleUpdateStatusPesanan(User user){
        String orderID, statusPesanan;
        boolean foundOrder = false;
        Order order = null;

        System.out.println("---------------Update Status Pesanan----------------");
        while(true) {
            System.out.print("Masukkan order ID: ");
            orderID = input.nextLine();
            // Mengecek apakah order ID ditemukan dalam pesanan user
            for (Order ordernow : user.getOrderList()) {
                if (ordernow.getOrderId().equalsIgnoreCase(orderID)) {
                    foundOrder = true;
                    order = ordernow;
                    break;
                }
            }
            // Jika orderID tidak ditemukan maka akan meminta input ulang
            if (!foundOrder) {
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }

            System.out.print("Status : ");
            statusPesanan = input.nextLine();

            if (statusPesanan.equalsIgnoreCase(order.getStatusPesanan())){
                System.out.printf("Status pesanan dengan ID %s tidak berhasil diupdate!\n", order.getOrderId());
                continue;
            } else {
                order.setStatusPesanan(statusPesanan);
                System.out.printf("Status pesanan dengan ID %s berhasil diupdate!", order.getOrderId());
                break;
            }
        }
    }

    public static void handleTambahRestoran(){
        String nama, inputMakanan, namaMakanan;
        boolean foundResto, menuValid;
        int jumlahMakanan;
        double hargaMakanan;

        System.out.println("---------------Tambah Restoran---------------");
        while (true) {
            System.out.print("Nama: ");
            nama = input.nextLine();
            // Mengecek apakah nama restoran >= 4
            if (nama.length() <= 4){
                System.out.println("Nama Restoran tidak valid!\n");
                continue;
            }
            foundResto = false;
            // Mengecek apakah nama restoran sudah pernah didaftarkan atau belum
            if (restoList != null) {
                for (Restaurant resto : restoList) {
                    if (resto.getNama().equalsIgnoreCase(nama)) {
                        foundResto = true;
                        System.out.printf("Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda!\n", nama);
                        break;
                    }
                }
            }
            if (foundResto) continue;

            System.out.print("Jumlah Makanan: ");
            jumlahMakanan = Integer.parseInt(input.nextLine());

            // Membuat objek baru & menambahkan objek barunya ke restoList
            Restaurant restaurant = new Restaurant(nama);
            restoList.add(restaurant);

            // Mengecek apakah harga menu merupakan bilangan bulan
            menuValid = true;
            for (int i = 0; i < jumlahMakanan; i++) {
                inputMakanan = input.nextLine();
                String[] items = inputMakanan.split(" ");
                namaMakanan = String.join(" ", Arrays.copyOfRange(items, 0, items.length - 1));
                if (!isNumeric(items[items.length-1])){
                    menuValid = false;
                    continue;
                }
                // Jika iya, maka menu akan ditambahkan ke restoran
                hargaMakanan = Double.parseDouble(items[items.length - 1]);
                Menu menu = new Menu(namaMakanan, hargaMakanan);
                restaurant.addMenu(menu);
            }

            // Jika menu tidak valid makan restoran akan dihapus
            if (!menuValid){
                restoList.remove(restaurant);
                System.out.println("Harga menu harus bilangan bulat!\n");
                continue;
            }

            System.out.printf("Restaurant %s Berhasil terdaftar.", nama);
            break;
        }
    }

    // Method untung menghandle hapus restoran
    public static void handleHapusRestoran(){
        String nama;
        boolean foundResto;

        System.out.println("---------------Hapus Restoran---------------");
        while (true) {
            System.out.print("Nama: ");
            nama = input.nextLine();
            foundResto = false;
            Restaurant restoNow = null;
            // Mencari apakah restoran sudah terdaftar atau belum
            for (Restaurant resto : restoList) {
                if (resto.getNama().equalsIgnoreCase(nama)) {
                    foundResto = true;
                    restoNow = resto;
                    break;
                }
            }
            // Jika restoran tidak ditemukan maka akan meminta input ulang
            if (!foundResto) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.println("Restoran berhasil dihapus.");
            // Menghapus restoran dari list
            restoList.remove(restoNow);
            break;
        }
    }

    public static void initUser(){
       userList = new ArrayList<User>();
       userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
       userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
       userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
       userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
       userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

       userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
       userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    private static boolean isNumeric(String str) {
        char[] var4;
        int var3 = (var4 = str.toCharArray()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            char c = var4[var2];
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        String id = "";
        id = id + namaRestoran.substring(0, 4).toUpperCase();
        id = id + tanggalOrder.substring(0,2) + tanggalOrder.substring(3,5) + tanggalOrder.substring(6);
        // Memanggil method cekDigitHP untuk menghitung digit yang akan dipakai
        id = id + cekDigitHP(noTelepon);
        // Memanggil method cekSum untuk menghitung digit checksum yang akan dipakai
        id = id + cekSum(id);

        return id;
    }

    private static String cekDigitHP(String noTelepon){
        int digitHP = 0;
        int totalDigitHP = 0;
        int[] noHP;
        int panjangHP = (noHP = pengubahArray(noTelepon)).length;
        // Menjumlahkan semua digit-digitnya
        for (int i = 0; i < panjangHP; i++){
            totalDigitHP = totalDigitHP + noHP[i];
        }
        digitHP = totalDigitHP%100;
        if (digitHP < 10){
            return "0" + Integer.toString(digitHP);
        } else {
            return Integer.toString(digitHP);
        }
    }

    private static String cekSum(String kode) {
        int genap = 0;
        int ganjil = 0;

        for (int i = 0; i < kode.length(); i++) {
            char kodeNow = kode.charAt(i);
            // Mencari nilai ascii dari tiap karakternya
            if (i % 2 == 0) {
                // Jika berupa angka, maka yang akan dijumlahkan adalah nilai dari angka itu sendiri
                if ((int) kodeNow < 58) {
                    genap += Character.getNumericValue(kodeNow);
                    // Jika berupa huruf maka yang akan dijumlahkan adalah nilai ascii dari huruf tersebut - 55
                } else {
                    genap += (int) kodeNow - 55;
                }
            } else {
                if ((int) kodeNow < 58) {
                    ganjil += Character.getNumericValue(kodeNow);
                } else {
                    ganjil += (int) kodeNow - 55;
                }
            }
        }

        String fixGenap;
        String fixGanjil;
        genap = genap % 36;
        if (genap > 9) {
            genap = (char) (genap + 55);
            fixGenap = Character.toString((char) genap);
        } else {
            fixGenap = Integer.toString(genap);
        }

        ganjil = ganjil % 36;
        if (ganjil > 9) {
            ganjil = (char) (ganjil + 55);
            fixGanjil = Character.toString((char) ganjil);
        }else {
            fixGanjil = Integer.toString(ganjil);
        }

        return fixGenap + fixGanjil;
    }

    public static int[] pengubahArray(String s) {
        int[] result = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            result[i] = Character.getNumericValue(s.charAt(i));
        }
        return result;
    }

    public  static  int cekOngkir (String lokasi){
        return switch (lokasi.toUpperCase()) {
            case "P" -> 10000;
            case "U" -> 20000;
            case "T" -> 35000;
            case "S" -> 40000;
            case "B" -> 60000;
            default -> 0;
        };
    }

    public static void handleSortMenuByPriceAndName(ArrayList<Menu> menuList) {
        // Mengurutkan menu berdasarkan harga menggunakan Bubble Sort
        for (int i = 0; i < menuList.size() - 1; i++) {
            for (int j = 0; j < menuList.size() - i - 1; j++) {
                if (menuList.get(j).getHarga() > menuList.get(j + 1).getHarga()) {
                    // Menukar menu jika harga lebih tinggi
                    Menu temp = menuList.get(j);
                    menuList.set(j, menuList.get(j + 1));
                    menuList.set(j + 1, temp);
                } else if (menuList.get(j).getHarga() == menuList.get(j + 1).getHarga()) {
                    // Jika harga sama, urutkan berdasarkan nama
                    if (menuList.get(j).getNamaMakanan().compareToIgnoreCase(menuList.get(j + 1).getNamaMakanan()) > 0) {
                        // Menukar menu jika nama lebih besar dalam urutan alfabet
                        Menu temp = menuList.get(j);
                        menuList.set(j, menuList.get(j + 1));
                        menuList.set(j + 1, temp);
                    }
                }
            }
        }
    }

}
