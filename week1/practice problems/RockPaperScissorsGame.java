import java.util.Random;
import java.util.Scanner;

public class RockPaperScissorsGame {
    static String playRound(String playerMove, String computerMove) {
        if (playerMove.equals(computerMove)) return "Draw";
        if ((playerMove.equals("Rock") && computerMove.equals("Scissors"))
                || (playerMove.equals("Paper") && computerMove.equals("Rock"))
                || (playerMove.equals("Scissors") && computerMove.equals("Paper"))) {
            return "Player Wins";
        }
        return "Computer Wins";
    }

    public static void main(String[] args) {
        String[] moves = {"Rock", "Paper", "Scissors"};
        String[] playerMoves = {"Rock", "Paper", "Scissors", "Rock", "Paper"};
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int wins = 0, losses = 0, draws = 0;
        System.out.println("Round | Player Move | Computer Move | Result");

        for (int i = 0; i < playerMoves.length; i++) {
            String playerMove = playerMoves[i];
            String computerMove = moves[random.nextInt(3)];
            String result = playRound(playerMove, computerMove);

            if (result.equals("Player Wins")) wins++;
            else if (result.equals("Computer Wins")) losses++;
            else draws++;

            System.out.println((i + 1) + " | " + playerMove + " | " + computerMove + " | " + result);
        }

        double winPercentage = (wins * 100.0) / playerMoves.length;
        System.out.printf("Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%%n", wins, losses, draws, winPercentage);
        scanner.close();
    }
}
