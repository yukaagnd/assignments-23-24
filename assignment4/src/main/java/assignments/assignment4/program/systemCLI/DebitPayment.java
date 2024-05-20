// Implementasi kelas pembayaran dengan kartu debit
package assignments.assignment4.program.systemCLI;

public class DebitPayment implements DepeFoodPaymentSystem{
    // Nilai minimum total harga untuk pembayaran dengan kartu debit
    private static final long MINIMUM_TOTAL_PRICE = 50000;

    // Metode untuk memproses pembayaran dengan kartu debit
    @Override
    public long processPayment(long amount) {
        if (amount < MINIMUM_TOTAL_PRICE) {
            System.out.println("Total harga pesanan tidak memenuhi syarat atau saldo tidak mencukupi");
            return 0; 
        } else {
            return amount;
        }
    }
}
