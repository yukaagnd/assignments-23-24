package assignments.assignment4.program;

import java.util.ArrayList;
import assignments.assignment4.program.systemCLI.CreditCardPayment;
import assignments.assignment4.program.systemCLI.DebitPayment;
import assignments.assignment4.program.systemCLI.DepeFoodPaymentSystem;

// Kelas untuk merepresentasikan pengguna
public class User {
    
    private String nama;
    private String nomorTelepon;
    private String email;
    private ArrayList<Order> orderHistory;
    private String role;
    private DepeFoodPaymentSystem payment;
    private long saldo;
    private String lokasi;

    // Konstruktor untuk User
    public User(String nama, String nomorTelepon, String email, String lokasi, String role, DepeFoodPaymentSystem payment, long saldo){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        orderHistory = new ArrayList<>();
        this.payment = payment;
        this.saldo = saldo;
    }
    
    public String getEmail() {
        return email;
    }
    public String getNama() {
        return nama;
    }
    public String getLokasi() {
        return lokasi;
    }
    public String getNomorTelepon() {
        return nomorTelepon;
    }
    public DepeFoodPaymentSystem getPayment() {
        return payment;
    }
    public void setPayment(DepeFoodPaymentSystem payment) {
        this.payment = payment;
    }
    public long getSaldo() {
        return saldo;
    }
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }
        
    public void addOrderHistory(Order order){
        orderHistory.add(order);
    }
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }
    // Method untuk memeriksa apakah pesanan dimiliki oleh pengguna
    public boolean isOrderBelongsToUser(String orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                return true;
            }
        }
        return false;
    }
    // Method untuk merepresentasikan objek User dalam bentuk String
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format("User dengan nama %s dan nomor telepon %s", nama, nomorTelepon);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
