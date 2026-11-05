public class CampusPortalBrowserHistory {
    static class Node {
        String url;
        Node prev, next;
        Node(String url) { this.url = url; }
    }

    static class BrowserHistory {
        private Node current;

        BrowserHistory(String homepage) {
            current = new Node(homepage);
        }

        // O(1); removes all forward-history nodes from the active list.
        void visit(String url) {
            Node newNode = new Node(url);
            current.next = newNode;
            newNode.prev = current;
            current = newNode;
        }

        // O(steps), stops at the homepage.
        String back(int steps) {
            while (steps > 0 && current.prev != null) {
                current = current.prev;
                steps--;
            }
            return current.url;
        }

        // O(steps), stops at the last visited page.
        String forward(int steps) {
            while (steps > 0 && current.next != null) {
                current = current.next;
                steps--;
            }
            return current.url;
        }
    }

    public static void main(String[] args) {
        BrowserHistory browser = new BrowserHistory("srm.edu");
        browser.visit("a.com");
        browser.visit("b.com");
        browser.visit("c.com");
        System.out.println(browser.back(1));    // b.com
        System.out.println(browser.back(1));    // a.com
        System.out.println(browser.forward(1)); // b.com
        browser.visit("d.com");
        System.out.println(browser.forward(2)); // d.com
        System.out.println(browser.back(2));    // a.com
        System.out.println(browser.back(7));    // srm.edu
    }
}