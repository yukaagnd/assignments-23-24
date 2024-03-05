package assignments.assignment1;

import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

    public static void showMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    public static void showLoopMenu(){
        System.out.println("Pilih menu:");
        System.err.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
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
                System.out.println("Nama Restoran tidak valid!");
                continue;
            }

            System.out.print("Tanggal Pemesanan: ");
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
        String orderID;
        String lokasiPengiriman;
        
        while (true){
            System.out.println();
            System.out.print("Order ID: ");
            orderID = input.nextLine();
            if (orderID.length() < 16){
                System.out.println("Order ID minimal 16 karakter");
                continue;
            }
            else if (orderID.length() > 16){
                System.out.println("Silahkan masukkan Order ID yang valid!");
                continue;
            }
            else{
                String ngecheckSum = cekSum(orderID.substring(0, 14));
                if (orderID.substring(14).equals(ngecheckSum)){;}
                else {
                    System.out.println("Silahkan masukkan Order ID yang valid!");
                    continue;
                }
            }
            System.out.print("Lokasi Pengiriman: ");
            lokasiPengiriman = input.nextLine().toUpperCase();
            if (lokasiPengiriman.equals("P") || lokasiPengiriman.equals("U") || lokasiPengiriman.equals("T") || lokasiPengiriman.equals("S") || lokasiPengiriman.equals("B")){;}
            else { 
                System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                continue;
            }
            generateBill(orderID, lokasiPengiriman);
            break;
        }

    }

    public static String generateBill(String OrderID, String lokasi){
        String bill = "";
        bill += "Bill:\n";
        bill += "Order ID: " + OrderID + "\n";
        bill += "Tanggal Pemesanan: " + OrderID.substring(4,6) + "/" + OrderID.substring(6,8) + "/" + OrderID.substring(8,12) + "\n";
        bill += "Lokasi Pengiriman: " + lokasi.toUpperCase() + "\n";
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
    }

    public static void main(String[] args) {
        showMenu();
        while(true) {
            System.out.println("--------------------------------");
            System.out.print("Pilihan menu: ");
            String command = input.nextLine();

            if (command.equals("1")){
                System.out.println("Order ID " + handleMenuGenerateID() + " diterima!");
            } 
            
            else if (command.equals("2")){
                handleGenerateBill();
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

    private static String cekDigitHP(String noTelepon){
        int digitHP = 0;
        int totalDigitHP = 0;
        int[] noHP;
        int panjangHP = (noHP = pengubahArray(noTelepon)).length;
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
    
            if (i % 2 == 0) {
                if (asciiValue < 58) {
                    genap += Character.getNumericValue(kodeNow);
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

    public static int[] pengubahArray(String s) {
        int[] result = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            result[i] = Character.getNumericValue(s.charAt(i));
        }
        return result;
    }
    
}
