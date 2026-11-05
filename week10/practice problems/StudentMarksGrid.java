public class StudentMarksGrid {
    // Use -1 to represent a missing mark.
    public static void main(String[] args) {
        String[] students = {"Anu", "Ravi", "Meena"};
        String[] subjects = {"Math", "Sci", "Eng"};
        int[][] marks = {
            {80, 90, 70},
            {60, 85, -1},
            {95, 75, 88}
        };

        for (int i = 0; i < students.length; i++) {
            int total = 0;
            for (int j = 0; j < subjects.length; j++) {
                if (marks[i][j] >= 0) total += marks[i][j];
            }
            System.out.println("Total " + students[i] + " " + total);
        }

        for (int j = 0; j < subjects.length; j++) {
            int topScore = -1;
            String topper = "None";
            for (int i = 0; i < students.length; i++) {
                if (marks[i][j] > topScore) {
                    topScore = marks[i][j];
                    topper = students[i];
                }
            }
            System.out.println("Topper " + subjects[j] + " " + topper + " " + topScore);
        }
    }
}