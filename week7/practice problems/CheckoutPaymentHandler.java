abstract class PaymentMethod {
    private static int transactionCounter = 1000;
    private final String transactionId;

    public PaymentMethod() {
        transactionId = "TXN-" + (++transactionCounter);
    }

    public abstract String processPayment(double amount);

    public String processPayment(double amount, String note) {
        return processPayment(amount) + " (" + note + ")";
    }

    public String getTransactionId() {
        return transactionId;
    }

    static void printConfirmation(PaymentMethod payment, double amount) {
        System.out.println(payment.processPayment(amount));
    }
}

class CreditCardPayment extends PaymentMethod {
    private String cardNumberLastFour;

    public CreditCardPayment(String cardNumberLastFour) {
        this.cardNumberLastFour = cardNumberLastFour;
    }

    @Override
    public String processPayment(double amount) {
        return "Charged $" + amount + " to card ending "
                + cardNumberLastFour + " - Txn " + getTransactionId();
    }
}

class CashPayment extends PaymentMethod {
    public CashPayment() {
        super();
    }

    @Override
    public String processPayment(double amount) {
        return "Received $" + amount + " in cash - Txn " + getTransactionId();
    }
}

public class CheckoutPaymentHandler {
    static void testUpcasting() {
        CreditCardPayment cc = new CreditCardPayment("4471");
        PaymentMethod ref = cc; // upcasting
        PaymentMethod.printConfirmation(ref, 250.0);
    }

    public static void main(String[] args) {
        CreditCardPayment cc = new CreditCardPayment("4471");
        CashPayment cash = new CashPayment();

        System.out.println(cc.processPayment(250.0));
        System.out.println(cash.processPayment(40.0));
        System.out.println(cc.processPayment(250.0, "Birthday gift"));

        testUpcasting();

        // PaymentMethod p = new PaymentMethod(); // does not compile: abstract class
    }
}
