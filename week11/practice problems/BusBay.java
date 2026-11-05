public class BusBay {
    private final int[] buses;
    private final int capacity;
    private int front;
    private int size;

    public BusBay(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.buses = new int[capacity];
        this.front = 0;
        this.size = 0;
    }

    public boolean insertFront(int bus) {
        if (isFull()) return false;
        front = (front - 1 + capacity) % capacity;
        buses[front] = bus;
        size++;
        return true;
    }

    public boolean insertLast(int bus) {
        if (isFull()) return false;
        int rearIndex = (front + size) % capacity;
        buses[rearIndex] = bus;
        size++;
        return true;
    }

    public boolean deleteFront() {
        if (isEmpty()) return false;
        front = (front + 1) % capacity;
        size--;
        return true;
    }

    public boolean deleteLast() {
        if (isEmpty()) return false;
        size--;
        return true;
    }

    public int getFront() {
        return isEmpty() ? -1 : buses[front];
    }

    public int getRear() {
        return isEmpty() ? -1 : buses[(front + size - 1) % capacity];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public static void main(String[] args) {
        BusBay b = new BusBay(3);
        System.out.println(b.insertLast(101));  // true
        System.out.println(b.insertLast(102));  // true
        System.out.println(b.insertFront(100)); // true
        System.out.println(b.insertFront(99));   // false
        System.out.println(b.getRear());         // 102
        System.out.println(b.isFull());          // true
        System.out.println(b.deleteLast());       // true
        System.out.println(b.insertFront(98));    // true
        System.out.println(b.getFront());         // 98
    }
}