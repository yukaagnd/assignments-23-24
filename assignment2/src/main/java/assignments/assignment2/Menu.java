package main.java.assignments.assignment2;

public class Menu {
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    private double harga;
    private String namaMakanan;
    // Konstruktor untuk membuat objek Menu dengan nama dan harga tertentu
    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }
    // Method untuk mengambil dan mengeset variable private di class Menu
    public String getNamaMakanan() {
        return namaMakanan;
    }
    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }
    public double getHarga() {
        return harga;
    }
    public void setHarga(double harga) {
        this.harga = harga;
    }
     
}
