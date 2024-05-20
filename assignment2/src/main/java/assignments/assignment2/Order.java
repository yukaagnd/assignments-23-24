package main.java.assignments.assignment2;

import java.util.ArrayList;

public class Order {
    private int biayaOngkosKirim;
    private String orderId;
    private Restaurant restaurant;
    private String tanggalPemesanan;
    private ArrayList <Menu> pesananMenuList;
    private String statusPesanan;
    // Konstruktor untuk membuat objek Order dengan orderId, tanggal, biaya ongkir, dan objek Restaurant tertentu
    public Order(String orderId, String tanggal, int ongkir, Restaurant resto){
        this.biayaOngkosKirim = ongkir;
        this.orderId = orderId;
        this.restaurant = resto;
        this.tanggalPemesanan = tanggal;
        this.statusPesanan = "Not Finished";
    }
    // Method untuk mengambil dan mengeset variable private di class Order
    public int getBiayaOngkosKirim() {
        return biayaOngkosKirim;
    }

    public void setBiayaOngkosKirim(int biayaOngkosKirim) {
        this.biayaOngkosKirim = biayaOngkosKirim;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getTanggalPemesanan() {
        return tanggalPemesanan;
    }

    public void setTanggalPemesanan(String tanggalPemesanan) {
        this.tanggalPemesanan = tanggalPemesanan;
    }

    // Method untuk menambahkan menu ke dalam pesanan
    public void addPesanan(Menu menuItem) {
        if (pesananMenuList == null) {
            pesananMenuList = new ArrayList<>();
        }
        pesananMenuList.add(menuItem);
    }

    public String getStatusPesanan() {
        return statusPesanan;
    }

    public void setStatusPesanan(String statusPesanan) {
        this.statusPesanan = statusPesanan;
    }

    public ArrayList<Menu> getPesananMenuList() {
        return pesananMenuList;
    }
}
