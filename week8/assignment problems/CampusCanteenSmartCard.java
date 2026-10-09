import java.util.*;

interface PricingPlan {
    double price(double fullPrice);
    String getName();
}
class DayScholarPlan implements PricingPlan {
    public double price(double fullPrice) { return fullPrice; }
    public String getName() { return "Day Scholar"; }
}
class HostellerPlan implements PricingPlan {
    public double price(double fullPrice) { return fullPrice * 0.90; }
    public String getName() { return "Hosteller"; }
}
class StaffPlan implements PricingPlan {
    public double price(double fullPrice) { return fullPrice * 0.80; }
    public String getName() { return "Staff"; }
}

class Transaction {
    private String description;
    private double amount;
    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
}

class Purchase {
    private String itemName;
    private double charged;
    private boolean refunded;

    public Purchase(String itemName, double charged) {
        this.itemName = itemName;
        this.charged = charged;
    }
    public String getItemName() { return itemName; }
    public double getCharged() { return charged; }
    public boolean isRefunded() { return refunded; }
    public void markRefunded() { refunded = true; }
}

class SmartCard {
    private String cardId;
    private PricingPlan plan;
    private double balance;
    private boolean blocked;
    private List<Transaction> transactions = new ArrayList<>();
    private List<Purchase> purchases = new ArrayList<>();

    public SmartCard(String cardId, PricingPlan plan) {
        this.cardId = cardId;
        this.plan = plan;
    }

    public void topUp(double amount) {
        if (blocked) {
            System.out.println("Top-up rejected: card is blocked.");
            return;
        }
        if (amount < 100) {
            System.out.println("Top-up rejected: minimum top-up is ₹100.00.");
            return;
        }
        if (balance + amount > 5000) {
            System.out.println("Top-up rejected: maximum balance is ₹5,000.00.");
            return;
        }
        balance += amount;
        transactions.add(new Transaction("Top-up", amount));
        System.out.printf("%s topped up with ₹%.2f. Balance: ₹%.2f.%n", cardId, amount, balance);
    }

    public Purchase purchase(String itemName, double fullPrice) {
        if (blocked) {
            System.out.println("Purchase failed: card is blocked.");
            return null;
        }
        double charge = plan.price(fullPrice);
        if (balance < charge) {
            System.out.printf("Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                    charge, balance);
            return null;
        }
        balance -= charge;
        transactions.add(new Transaction(itemName, -charge));
        Purchase purchase = new Purchase(itemName, charge);
        purchases.add(purchase);
        System.out.printf("%s purchased for ₹%.2f. Balance: ₹%.2f.%n", itemName, charge, balance);
        return purchase;
    }

    public void refund(Purchase purchase) {
        if (purchase == null || purchase.isRefunded()) {
            System.out.println("Refund rejected: purchase has already been refunded or is invalid.");
            return;
        }
        purchase.markRefunded();
        balance += purchase.getCharged();
        transactions.add(new Transaction("Refund: " + purchase.getItemName(), purchase.getCharged()));
        System.out.printf("Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n",
                purchase.getCharged(), purchase.getItemName(), balance);
    }

    public void block() { blocked = true; }
    public void unblock() { blocked = false; }

    public void miniStatement() {
        StringBuilder report = new StringBuilder("Mini-statement for " + cardId + ": ");
        double sum = 0;
        for (Transaction t : transactions) {
            if (t.getAmount() >= 0) report.append("+");
            report.append(String.format("%.2f", t.getAmount())).append(", ");
            sum += t.getAmount();
        }
        if (!transactions.isEmpty()) report.setLength(report.length() - 2);
        report.append(" = ₹").append(String.format("%.2f", sum)).append(".");
        System.out.println(report);
        if (Math.abs(sum - balance) > 0.001)
            throw new IllegalStateException("Balance invariant violated");
    }

    public double getBalance() { return balance; }
}

public class CampusCanteenSmartCard {
    public static void main(String[] args) {
        SmartCard card = new SmartCard("C-2045", new HostellerPlan());
        card.topUp(500);
        Purchase thali = card.purchase("Veg Thali", 120);
        card.purchase("Cold Coffee", 60);
        card.purchase("Large Meal", 400);
        card.refund(thali);
        card.refund(thali);
        card.miniStatement();
    }
}
