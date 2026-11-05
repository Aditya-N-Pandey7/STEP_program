public class AddTwoNumbersLinkedLists {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    // Digits are stored in reverse order. O(max(m,n)) time and output space.
    static Node addNumbers(Node a, Node b) {
        Node dummy = new Node(0);
        Node tail = dummy;
        int carry = 0;

        while (a != null || b != null || carry != 0) {
            int sum = carry;

            if (a != null) {
                sum += a.data;
                a = a.next;
            }
            if (b != null) {
                sum += b.data;
                b = b.next;
            }

            carry = sum / 10;
            tail.next = new Node(sum % 10);
            tail = tail.next;
        }

        return dummy.next;
    }

    static void printList(Node head) {
        while (head != null) {
            System.out.print(head.data);
            if (head.next != null) System.out.print(" -> ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node a = new Node(2);
        a.next = new Node(4);
        a.next.next = new Node(3);

        Node b = new Node(5);
        b.next = new Node(6);
        b.next.next = new Node(4);

        printList(addNumbers(a, b)); // 7 -> 0 -> 8

        Node x = new Node(9);
        x.next = new Node(9);
        Node y = new Node(1);
        printList(addNumbers(x, y)); // 0 -> 0 -> 1
    }
}