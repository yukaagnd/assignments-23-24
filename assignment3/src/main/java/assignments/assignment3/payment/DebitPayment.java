package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem{
    private static final long MINIMUM_TOTAL_PRICE = 50000;

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
