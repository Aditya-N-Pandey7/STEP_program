public class ReorderLinkedListAlternately {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    // In-place: middle finding, split, reverse second half, alternate merge.
    // O(n) time, O(1) extra space; does not create or copy list nodes.
    static void reorderList(Node head) {
        if (head == null || head.next == null) return;

        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node second = slow.next;
        slow.next = null;
        second = reverse(second);

        Node first = head;
        while (second != null) {
            Node firstNext = first.next;
            Node secondNext = second.next;

            first.next = second;
            second.next = firstNext;

            first = firstNext;
            second = secondNext;
        }
    }

    private static Node reverse(Node head) {
        Node previous = null;
        Node current = head;
        while (current != null) {
            Node next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        return previous;
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
        Node even = new Node(1);
        even.next = new Node(2);
        even.next.next = new Node(3);
        even.next.next.next = new Node(4);
        reorderList(even);
        printList(even); // 1 -> 4 -> 2 -> 3

        Node odd = new Node(1);
        odd.next = new Node(2);
        odd.next.next = new Node(3);
        odd.next.next.next = new Node(4);
        odd.next.next.next.next = new Node(5);
        reorderList(odd);
        printList(odd); // 1 -> 5 -> 2 -> 4 -> 3
    }
}