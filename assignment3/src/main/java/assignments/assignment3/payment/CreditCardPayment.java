package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem{
    private static final double TRANSACTION_FEE_PERCENTAGE = 0.02;

    @Override
    public long processPayment(long amount) {
        // Hitung biaya transaksi berdasarkan persentase
        long transactionFee = (long) (amount * TRANSACTION_FEE_PERCENTAGE);
        // Kembalikan total biaya termasuk biaya transaksi
        return amount + transactionFee;
    }
}
