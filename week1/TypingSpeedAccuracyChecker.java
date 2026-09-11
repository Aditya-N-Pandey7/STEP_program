public class TypingSpeedAccuracyChecker {
    static void checkTypingAccuracy(String original, String typed) {
        int total = Math.min(original.length(), typed.length());
        int matched = 0;
        int firstMismatch = -1;

        for (int i = 0; i < total; i++) {
            if (original.charAt(i) == typed.charAt(i)) matched++;
            else if (firstMismatch == -1) firstMismatch = i;
        }

        double accuracy = original.length() == 0 ? 100.0 : (matched * 100.0) / original.length();
        System.out.printf("Matched: %d/%d | Accuracy: %.2f%%", matched, original.length(), accuracy);

        if (firstMismatch == -1 && original.length() == typed.length()) {
            System.out.println(" | No Mismatches");
        } else {
            if (firstMismatch == -1) firstMismatch = total;
            char expected = firstMismatch < original.length() ? original.charAt(firstMismatch) : '-';
            char actual = firstMismatch < typed.length() ? typed.charAt(firstMismatch) : '-';
            System.out.println(" | First Mismatch at position " + (firstMismatch + 1)
                    + " ('" + expected + "' vs '" + actual + "')");
        }
    }

    public static void main(String[] args) {
        checkTypingAccuracy("hello world", "hello worlt");
        checkTypingAccuracy("coding", "coding");
    }
}
