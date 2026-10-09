import java.util.*;

interface IPaymentMethod {
    boolean pay(double amount);
    String getName();
}

class CreditCardPayment implements IPaymentMethod {
    private boolean succeeds;
    public CreditCardPayment(boolean succeeds) { this.succeeds = succeeds; }
    public boolean pay(double amount) { return succeeds; }
    public String getName() { return "Credit Card"; }
}

class DigitalWalletPayment implements IPaymentMethod {
    private boolean succeeds;
    public DigitalWalletPayment(boolean succeeds) { this.succeeds = succeeds; }
    public boolean pay(double amount) { return succeeds; }
    public String getName() { return "Digital Wallet"; }
}

class CashOnDeliveryPayment implements IPaymentMethod {
    public boolean pay(double amount) { return true; }
    public String getName() { return "Cash on Delivery"; }
}

class FoodItem {
    private String name;
    private double price;
    public FoodItem(String name, double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        this.name = name;
        this.price = price;
    }
    public String getName() { return name; }
    public double getPrice() { return price; }
}

class LineItem {
    private FoodItem item;
    private int quantity;
    public LineItem(FoodItem item, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        this.item = item;
        this.quantity = quantity;
    }
    public double getTotal() { return item.getPrice() * quantity; }
    public String describe() { return item.getName() + " (Qty " + quantity + ")"; }
}

class Customer {
    private String name;
    public Customer(String name) { this.name = name; }
    public String getName() { return name; }
    public void notifyCustomer(String message) {
        System.out.println("Notification: " + message);
    }
}

class FoodOrder {
    enum Status { CREATED, PENDING_PAYMENT, PAID }
    private static int counter = 122;
    private final int orderId = ++counter;
    private Customer customer;
    private List<LineItem> items = new ArrayList<>();
    private Status status = Status.CREATED;

    public FoodOrder(Customer customer) {
        this.customer = customer;
        System.out.println("Order created.");
    }

    public void addItem(FoodItem item, int quantity) {
        if (status != Status.CREATED) throw new IllegalStateException("Order can no longer be edited");
        LineItem line = new LineItem(item, quantity);
        items.add(line);
        System.out.println("Added " + line.describe() + ".");
    }

    public double getTotal() {
        double total = 0;
        for (LineItem item : items) total += item.getTotal();
        return total;
    }

    public boolean place(IPaymentMethod payment) {
        if (items.isEmpty()) {
            System.out.println("Cannot place order: Order must contain at least one item.");
            return false;
        }
        status = Status.PENDING_PAYMENT;
        boolean paid = payment.pay(getTotal());
        if (paid) {
            status = Status.PAID;
            System.out.println("Order placed successfully.");
            System.out.println("Payment via " + payment.getName() + " successful.");
            System.out.println("Order status: Paid.");
            customer.notifyCustomer("Order #" + orderId + " placed and paid.");
        } else {
            System.out.println("Order placed.");
            System.out.println("Payment via " + payment.getName() + " failed.");
            System.out.println("Order status: Pending Payment.");
            customer.notifyCustomer("Order #" + orderId + " placed, awaiting payment.");
        }
        return paid;
    }
}

public class FoodOrderFlexiblePaymentSystem {
    public static void main(String[] args) {
        Customer customer = new Customer("Alex");
        FoodOrder empty = new FoodOrder(customer);
        empty.place(new CreditCardPayment(true));

        FoodOrder first = new FoodOrder(customer);
        first.addItem(new FoodItem("Pizza", 12.0), 2);
        first.addItem(new FoodItem("Soda", 2.0), 1);
        first.place(new CreditCardPayment(true));

        FoodOrder second = new FoodOrder(customer);
        second.addItem(new FoodItem("Burger", 8.0), 1);
        second.place(new DigitalWalletPayment(false));
    }
}
