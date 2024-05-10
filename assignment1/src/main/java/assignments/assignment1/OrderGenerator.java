package assignments.assignment1;

import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);
<<<<<<< HEAD

<<<<<<< HEAD
    // Method untuk mencetak main menu
=======
>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
    public static void showMenu(){
=======
    private static final int ORDER_ID_LENGTH = 16;

    /*
     * Anda boleh membuat method baru sesuai kebutuhan Anda
     * Namun, Anda tidak boleh menghapus ataupun memodifikasi return type method
     * yang sudah ada.
     */

    /*
     * Method ini untuk menampilkan DepeFood
     */
    public static void initMenu() {
>>>>>>> 2de87934941926ea08452f88727b4221a5edf9d5
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.err.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
    }

    /*
     * Method ini untuk menampilkan menu
     */
    public static void showMenu() {
        System.out.println("Pilih menu:");
        System.err.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

<<<<<<< HEAD
<<<<<<< HEAD
    // Method untuk mencetak loop menu
=======
>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
    public static void showLoopMenu(){
        System.out.println("Pilih menu:");
        System.err.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
<<<<<<< HEAD
    }

    // Method untuk menghandle dan memvalidasi input pada generate ID
    public static String handleMenuGenerateID(){
        String namaResto;
        String tanggalOrder;
        String noTelepon;
        while (true){
            System.out.println();
            System.out.print("Nama Restoran: ");
            // Menghilangkan spasi yang tidak diperlukan
            namaResto = input.nextLine().replace(" ", "");
            // Memvalidasi input resto apakah panjangnya >= 4
            if (namaResto.length() < 4){
                System.out.println("Nama Restoran tidak valid!");
                continue;
            }

            System.out.print("Tanggal Pemesanan: ");
            tanggalOrder = input.nextLine();
            // Memvalidasi input tanggal apakah sudah sesuai dengan format
            if (tanggalOrder.length() != 10 || tanggalOrder.charAt(2) != '/' || tanggalOrder.charAt(5) != '/'){
                    System.out.println("Tanggal pemesanan dalam format DD/MM/YYYY!");
                    continue;
            }
            
            // Memvalidasi input no telepon apakah berupa digit
            System.out.print("No. Telpon: ");
            noTelepon = input.nextLine(); 
            if (!isNumeric(noTelepon)){
                System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.");
                continue;
            }
            break;
        }
        // Memasukkan hasil input yang sudah divalidasi ke method generateOrderID
        return generateOrderID(namaResto, tanggalOrder, noTelepon);
    }

    // Method untuk menyatukan kode OrderID sesuai ketentuan
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

    //Method untuk menghandle dan memvalidasi input pada generate Bill
    public static String handleGenerateBill(){
=======
    }

    public static String handleMenuGenerateID(){
        String namaResto;
        String tanggalOrder;
        String noTelepon;
        while (true){
            System.out.println();
            System.out.print("Nama Restoran: ");
            namaResto = input.nextLine().replace(" ", "");
            if (namaResto.length() < 4){
=======
    /*
     * Method ini digunakan untuk membuat ID
     * dari nama restoran, tanggal order, dan nomor telepon
     *
     * @return String Order ID dengan format sesuai pada dokumen soal
     */
    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {

        String restaurantCode = getRestaurantCode(namaRestoran);
        String formattedDate = formatDate(tanggalOrder);
        String phoneNumberChecksum = getPhoneNumberChecksum(noTelepon);

        String id = restaurantCode + formattedDate + phoneNumberChecksum;
        id = id.toUpperCase();
        String checksum = calculateChecksum(id);

        return id + checksum;
    }

    /*
     * Method ini digunakan untuk membuat bill
     * dari order id dan lokasi
     *
     * @return String Bill dengan format sesuai di bawah:
     * Bill:
     * Order ID: [Order ID]
     * Tanggal Pemesanan: [Tanggal Pemesanan]
     * Lokasi Pengiriman: [Kode Lokasi]
     * Biaya Ongkos Kirim: [Total Ongkos Kirim]
     */
    public static String generateBill(String OrderID, String lokasi) {
        String formattedDate = OrderID.substring(4, 12);
        String tanggalPemesanan = formattedDate.substring(0, 2) + "/" + formattedDate.substring(2, 4) + "/"
                + formattedDate.substring(4, 8);

        return outputBill(OrderID, tanggalPemesanan, lokasi, calculateDeliveryCost(lokasi));
    }

    public static boolean validateRestaurantName(String restaurantName) {
        return restaurantName != null && !restaurantName.isEmpty() && getRestaurantCode(restaurantName).length() >= 4;
    }

    public static boolean validateDate(String date) {
        String[] parts = date.split("/");
        if (parts.length != 3) {
            return false;
        }

        for (String part : parts) {
            if (!part.chars().allMatch(Character::isDigit)) {
                return false;
            }
        }

        return parts[0].length() == 2 && parts[1].length() == 2 && parts[2].length() == 4;
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.chars().allMatch(Character::isDigit);
    }

    public static boolean validateLocation(String location) {
        char[] locationList = { 'P', 'U', 'T', 'S', 'B' };

        return location.length() == 1 && new String(locationList).contains(location);
    }

    public static boolean validateOrderID(String orderID) {
        if (orderID.length() != ORDER_ID_LENGTH) {
            System.out.println("Order ID minimal 16 karakter");
            return false;
        }

        if (!orderID.chars().allMatch(Character::isLetterOrDigit) || !checkIfChecksumValid(orderID)) {
            System.out.println("Silahkan masukkan Order ID yang valid!");
            return false;
        }

        return true;
    }

    public static boolean checkIfChecksumValid(String id) {
        String idWithoutChecksum = id.substring(0, id.length() - 2);
        String checksum = id.substring(id.length() - 2);

        return calculateChecksum(idWithoutChecksum).equals(checksum);
    }

    public static String getRestaurantCode(String restaurantName) {
        String[] words = restaurantName.split(" ");

        StringBuilder code = new StringBuilder();

        for (String word : words) {
            code.append(word);
        }

        return code.substring(0, Math.min(code.length(), 4));
    }

    public static String formatDate(String date) {
        String[] parts = date.split("/");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];

        return day + month + year;
    }

    public static String getPhoneNumberChecksum(String phoneNumber) {
        int sum = 0;
        for (char c : phoneNumber.toCharArray()) {
            if (Character.isDigit(c)) {
                sum += Character.getNumericValue(c);
            }
        }
        int checksum = sum % 100;
        return (checksum < 10) ? "0" + checksum : String.valueOf(checksum);
    }

    public static String calculateChecksum(String id) {
        int sumEven = 0;
        int sumOdd = 0;

        for (int i = 0; i < id.length(); i++) {
            char c = id.charAt(i);
            int numericValue = getNumericValue(c);
            if (i % 2 == 0) {
                sumEven += numericValue;
            } else {
                sumOdd += numericValue;
            }
        }
        int remainderEven = sumEven % 36;
        int remainderOdd = sumOdd % 36;
        return reverseAssignment(remainderEven) + reverseAssignment(remainderOdd);
    }

    public static int getNumericValue(char c) {
        if (Character.isDigit(c)) {
            return Character.getNumericValue(c);
        } else {
            return c - 'A' + 10;
        }
    }

    public static String reverseAssignment(int remainder) {
        String code39CharacterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return String.valueOf(code39CharacterSet.charAt(remainder));
    }

    public static int calculateDeliveryCost(String location) {
        switch (location) {
            case "P":
                return 10000;
            case "U":
                return 20000;
            case "T":
                return 35000;
            case "S":
                return 40000;
            case "B":
                return 60000;
            default:
                return 0;
        }
    }

    public static String outputBill(String orderID, String tanggalPemesanan, String lokasiPengiriman,
            int biayaOngkosKirim) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');

        decimalFormat.setDecimalFormatSymbols(symbols);

        return "Bill:\n" + "Order ID: " + orderID + "\n" + "Tanggal Pemesanan: " + tanggalPemesanan + "\n"
                + "Lokasi Pengiriman: " + lokasiPengiriman + "\n" + "Biaya Ongkos Kirim: Rp "
                + decimalFormat.format(biayaOngkosKirim) + "\n";
    }

    /*
     * Method ini untuk memproses ID Order
     */
    public static void processGenerateOrder() {
        boolean isInputValid = false;

        while (!isInputValid) {
            System.out.println();
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine().toUpperCase();
            if (!validateRestaurantName(namaRestoran)) {
>>>>>>> 2de87934941926ea08452f88727b4221a5edf9d5
                System.out.println("Nama Restoran tidak valid!");
                continue;
            }

            System.out.print("Tanggal Pemesanan: ");
<<<<<<< HEAD
            tanggalOrder = input.nextLine();
            if (tanggalOrder.length() != 10 || tanggalOrder.charAt(2) != '/' || tanggalOrder.charAt(5) != '/'){
                    System.out.println("Tanggal pemesanan dalam format DD/MM/YYYY!");
                    continue;
            }
            
            System.out.print("No. Telpon: ");
            noTelepon = input.nextLine(); 
            if (!isNumeric(noTelepon)){
                System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.");
                continue;
            }
            break;
        }
        return generateOrderID(namaResto, tanggalOrder, noTelepon);
    }

    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        String id = "";
        id = id + namaRestoran.substring(0, 4).toUpperCase();
        id = id + tanggalOrder.substring(0,2) + tanggalOrder.substring(3,5) + tanggalOrder.substring(6);
        id = id + cekDigitHP(noTelepon);
        id = id + cekSum(id);

        return id;
    }

    public static void handleGenerateBill(){
>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
        String orderID;
        String lokasiPengiriman;
        
        while (true){
            System.out.println();
            System.out.print("Order ID: ");
            orderID = input.nextLine();
<<<<<<< HEAD
            // Memvalidasi order id agar panjangnya 16
=======
>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
            if (orderID.length() < 16){
                System.out.println("Order ID minimal 16 karakter");
                continue;
            }
            else if (orderID.length() > 16){
<<<<<<< HEAD
                System.out.println("Order ID maksimal 16 karakter");
                continue;
            }
            // Memvalidasi order id agar ceksumnya sudah memenuhi syarat perhitungan
=======
                System.out.println("Silahkan masukkan Order ID yang valid!");
                continue;
            }
>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
            else{
                String ngecheckSum = cekSum(orderID.substring(0, 14));
                if (orderID.substring(14).equals(ngecheckSum)){;}
                else {
                    System.out.println("Silahkan masukkan Order ID yang valid!");
                    continue;
                }
            }
<<<<<<< HEAD
            // Memvalidasi lokasi agar lokasi yang dimasukkan valid
=======
>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
            System.out.print("Lokasi Pengiriman: ");
            lokasiPengiriman = input.nextLine().toUpperCase();
            if (lokasiPengiriman.equals("P") || lokasiPengiriman.equals("U") || lokasiPengiriman.equals("T") || lokasiPengiriman.equals("S") || lokasiPengiriman.equals("B")){;}
            else { 
                System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                continue;
            }
<<<<<<< HEAD
            // Memanggil method generateBill untuk mereturn output yang sesuai
            System.out.println();
            return generateBill(orderID, lokasiPengiriman);
        }

    }

     // Method untuk menyatukan bill sesuai ketentuan
=======
            generateBill(orderID, lokasiPengiriman);
            break;
        }

    }

>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
    public static String generateBill(String OrderID, String lokasi){
        String bill = "";
        bill += "Bill:\n";
        bill += "Order ID: " + OrderID + "\n";
        bill += "Tanggal Pemesanan: " + OrderID.substring(4,6) + "/" + OrderID.substring(6,8) + "/" + OrderID.substring(8,12) + "\n";
        bill += "Lokasi Pengiriman: " + lokasi.toUpperCase() + "\n";
<<<<<<< HEAD
        // Mengkategorikan biaya ongkos kirim 
=======
>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
        switch (lokasi.toUpperCase()) {
            case "P":
                bill += "Biaya Ongkos Kirim: Rp 10.000\n";
                break;
            case "U":
                bill += "Biaya Ongkos Kirim: Rp 20.000\n";
                break;
            case "T":
                bill += "Biaya Ongkos Kirim: Rp 35.000\n";
                break;
            case "S":
                bill += "Biaya Ongkos Kirim: Rp 40.000\n";
                break;
            case "B":
                bill += "Biaya Ongkos Kirim: Rp 60.000\n";
                break;
        }
        return bill;
=======
            String tanggalOrder = input.nextLine();

            if (!validateDate(tanggalOrder)) {
                System.out.println("Tanggal Pemesanan dalam format DD/MM/YYYY!");
                continue;
            }

            System.out.print("No. Telpon: ");
            String noTelepon = input.nextLine();
            if (!validatePhoneNumber(noTelepon)) {
                System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.");
                continue;
            }

            System.out.println(
                    "Order ID " + generateOrderID(namaRestoran, tanggalOrder, noTelepon) + " diterima!");

            isInputValid = true;
        }
    }

    /*
     * Method ini untuk memproses generate bill
     */
    public static void processGenerateBill() {
        boolean isInputValid = false;

        while (!isInputValid) {
            System.out.println();
            System.out.print("Order ID: ");
            String orderID = input.nextLine().toUpperCase();
            if (!validateOrderID(orderID)) {
                continue;
            }

            System.out.print("Lokasi Pengiriman: ");
            String lokasi = input.nextLine().toUpperCase();
            if (!validateLocation(lokasi)) {
                System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                continue;
            }

            System.out.println(generateBill(orderID, lokasi));
            isInputValid = true;
        }
>>>>>>> 2de87934941926ea08452f88727b4221a5edf9d5
    }

    //Method main
    public static void main(String[] args) {
<<<<<<< HEAD
        showMenu();
        while(true) {
            System.out.println("--------------------------------");
            System.out.print("Pilihan menu: ");
            String command = input.nextLine();

            if (command.equals("1")){
                System.out.println("Order ID " + handleMenuGenerateID() + " diterima!");
            } 
            
            else if (command.equals("2")){
<<<<<<< HEAD
                System.out.println(handleGenerateBill());
=======
                handleGenerateBill();
>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
            } 
            
            else {
                if (command.equals("3")){
                    System.out.println("Terima kasih telah menggunakan DepeFood!");
                    return;
                }
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
            System.out.println("--------------------------------");
            showLoopMenu();
        }
    }

<<<<<<< HEAD
    // Method untuk mencari digitHP yang dipakai
=======
>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
    private static String cekDigitHP(String noTelepon){
        int digitHP = 0;
        int totalDigitHP = 0;
        int[] noHP;
        int panjangHP = (noHP = pengubahArray(noTelepon)).length;
<<<<<<< HEAD
        // Menjumlahkan semua digit-digitnya
=======
>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
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
            int asciiValue = (int) kodeNow;
<<<<<<< HEAD
            // Mencari nilai ascii dari tiap karakternya 
            if (i % 2 == 0) {
                // Jika berupa angka, maka yang akan dijumlahkan adalah nilai dari angka itu sendiri
                if (asciiValue < 58) {
                    genap += Character.getNumericValue(kodeNow);
                // Jika berupa huruf maka yang akan dijumlahkan adalah nilai ascii dari huruf tersebut - 55
=======
    
            if (i % 2 == 0) {
                if (asciiValue < 58) {
                    genap += Character.getNumericValue(kodeNow);
>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
                } else {
                    genap += asciiValue - 55;
                }
            } else {
                if (asciiValue < 58) {
                    ganjil += Character.getNumericValue(kodeNow);
                } else {
                    ganjil += asciiValue - 55;
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

<<<<<<< HEAD
    // Method untuk ngecheck apakah isi String berupa angka 
=======

>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
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

<<<<<<< HEAD
    // Method untuk mengubah string menjadi array dengan tipe data integer
=======
>>>>>>> 5e6d71e440c3e0c2a0aa592a1416d08a798e259a
    public static int[] pengubahArray(String s) {
        int[] result = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            result[i] = Character.getNumericValue(s.charAt(i));
        }
        return result;
    }
    
=======
        boolean isRunning = true;

        initMenu();
        while (isRunning) {
            showMenu();
            System.out.println("--------------------------------------------");
            System.out.print("Pilihan Menu: ");
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    processGenerateOrder();
                    break;
                case 2:
                    processGenerateBill();
                    break;
                case 3:
                    isRunning = false;
                    System.out.println("Terima kasih telah menggunakan DepeFood!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid");
                    break;
            }
            System.out.println("--------------------------------------------");
        }
    }
>>>>>>> 2de87934941926ea08452f88727b4221a5edf9d5
}
