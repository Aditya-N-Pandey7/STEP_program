import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CustomerRegistry {
    static class Customer {
        int id;
        String name;

        Customer(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Customer)) return false;
            Customer other = (Customer) obj;
            return id == other.id && Objects.equals(name, other.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "(" + id + ", " + name + ")";
        }
    }

    public static void main(String[] args) {
        Set<Customer> registry = new HashSet<>();

        System.out.println(registry.add(new Customer(101, "Asha")));
        System.out.println(registry.add(new Customer(101, "Asha")));
        System.out.println(registry.add(new Customer(102, "Ravi")));

        Customer search = new Customer(101, "Asha");
        System.out.println("unique count " + registry.size());
        System.out.println("contains: " + registry.contains(search));
    }
}