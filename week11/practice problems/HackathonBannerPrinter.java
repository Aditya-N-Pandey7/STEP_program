import java.util.ArrayDeque;
import java.util.Deque;

public class HackathonBannerPrinter {
    // Iterative stack simulation; avoids recursion.
    // Time is proportional to input plus expanded output size.
    static String expandBanner(String code) {
        Deque<Integer> counts = new ArrayDeque<>();
        Deque<StringBuilder> builders = new ArrayDeque<>();
        StringBuilder current = new StringBuilder();
        int count = 0;

        for (int i = 0; i < code.length(); i++) {
            char ch = code.charAt(i);

            if (Character.isDigit(ch)) {
                count = count * 10 + (ch - '0');
            } else if (ch == '[') {
                counts.push(count);
                builders.push(current);
                current = new StringBuilder();
                count = 0;
            } else if (ch == ']') {
                int repeat = counts.pop();
                StringBuilder parent = builders.pop();
                for (int j = 0; j < repeat; j++) {
                    parent.append(current);
                }
                current = parent;
            } else {
                current.append(ch);
            }
        }

        return current.toString();
    }

    public static void main(String[] args) {
        System.out.println(expandBanner("3[ab]2[c]")); // abababcc
        System.out.println(expandBanner("2[x3[y]]z")); // xyyyxyyyz
    }
}