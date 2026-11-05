public class LinkedListPalindrome {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    // O(n) time, O(1) extra space. Restores the list after checking.
    static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;

        Node slow = head;
        Node fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node reversedHalf = reverse(slow.next);
        Node restore = reversedHalf;
        Node first = head;
        boolean result = true;

        while (reversedHalf != null) {
            if (first.data != reversedHalf.data) {
                result = false;
                break;
            }
            first = first.next;
            reversedHalf = reversedHalf.next;
        }

        slow.next = reverse(restore);
        return result;
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

    public static void main(String[] args) {
        Node palindrome = new Node(1);
        palindrome.next = new Node(2);
        palindrome.next.next = new Node(2);
        palindrome.next.next.next = new Node(1);

        Node notPalindrome = new Node(1);
        notPalindrome.next = new Node(2);
        notPalindrome.next.next = new Node(3);

        System.out.println(isPalindrome(palindrome)); // true
        System.out.println(isPalindrome(notPalindrome)); // false
    }
}