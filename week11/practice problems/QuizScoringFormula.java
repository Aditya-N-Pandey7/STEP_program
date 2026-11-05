import java.util.ArrayDeque;
import java.util.Deque;

public class QuizScoringFormula {
    // Two stacks, single left-to-right scan; integer division.
    static int evaluate(String expr) {
        Deque<Integer> values = new ArrayDeque<>();
        Deque<Character> operators = new ArrayDeque<>();

        int i = 0;
        while (i < expr.length()) {
            char ch = expr.charAt(i);

            if (ch == ' ') {
                i++;
            } else if (Character.isDigit(ch)) {
                int number = 0;
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    number = number * 10 + (expr.charAt(i) - '0');
                    i++;
                }
                values.push(number);
            } else if (ch == '(') {
                operators.push(ch);
                i++;
            } else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    apply(values, operators.pop());
                }
                if (!operators.isEmpty()) operators.pop(); // discard '('
                i++;
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (!operators.isEmpty() && operators.peek() != '('
                        && precedence(operators.peek()) >= precedence(ch)) {
                    apply(values, operators.pop());
                }
                operators.push(ch);
                i++;
            } else {
                throw new IllegalArgumentException("Invalid character: " + ch);
            }
        }

        while (!operators.isEmpty()) {
            apply(values, operators.pop());
        }

        return values.pop();
    }

    private static int precedence(char op) {
        return (op == '*' || op == '/') ? 2 : 1;
    }

    private static void apply(Deque<Integer> values, char op) {
        int right = values.pop();
        int left = values.pop();

        switch (op) {
            case '+': values.push(left + right); break;
            case '-': values.push(left - right); break;
            case '*': values.push(left * right); break;
            case '/': values.push(left / right); break;
            default: throw new IllegalArgumentException("Unknown operator");
        }
    }

    public static void main(String[] args) {
        System.out.println(evaluate("2 * (3 + 4) - 10 / 5")); // 12
        System.out.println(evaluate("(8 - 2) * (1 + 2) / 4")); // 4
    }
}