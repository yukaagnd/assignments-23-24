package assignments.assignment4.program.systemCLI;

// Implementasi kelas pembayaran dengan kartu kredit
public class CreditCardPayment implements DepeFoodPaymentSystem{
    // Persentase biaya transaksi
    private static final double TRANSACTION_FEE_PERCENTAGE = 0.02;

    // Metode untuk memproses pembayaran dengan kartu kredit
    @Override
    public long processPayment(long amount) {
        // Hitung biaya transaksi berdasarkan persentase
        long transactionFee = (long) (amount * TRANSACTION_FEE_PERCENTAGE);
        // Kembalikan total biaya termasuk biaya transaksi
        return amount + transactionFee;
    }
}
