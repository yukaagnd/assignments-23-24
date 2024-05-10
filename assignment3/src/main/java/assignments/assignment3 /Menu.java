package assignments.assignment3;

// Kelas untuk merepresentasikan menu makanan
public class Menu {
    
    private String namaMakanan;
    private double harga; 

    // Konstruktor untuk Menu
    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }
    public double getHarga() {
        return harga;
    }
    public String getNamaMakanan() {
        return namaMakanan;
    }
}
