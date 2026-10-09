import java.util.*;

abstract class ExamQuestion {
    private final String prompt;
    private final String correctAnswer;

    public ExamQuestion(String prompt, String correctAnswer) {
        this.prompt = prompt;
        this.correctAnswer = correctAnswer;
    }

    public String getPrompt() {
        return prompt;
    }

    public abstract boolean isCorrect(String answer, String expectedAnswer);

    public boolean checkAnswer(String answer) {
        return isCorrect(answer, correctAnswer);
    }
}

class MultipleChoiceQuestion extends ExamQuestion {
    public MultipleChoiceQuestion(String prompt, String correctAnswer) {
        super(prompt, correctAnswer);
    }

    @Override
    public boolean isCorrect(String answer, String expectedAnswer) {
        return answer != null && answer.equalsIgnoreCase(expectedAnswer);
    }
}

class TrueFalseQuestion extends ExamQuestion {
    public TrueFalseQuestion(String prompt, String correctAnswer) {
        super(prompt, correctAnswer);
    }

    @Override
    public boolean isCorrect(String answer, String expectedAnswer) {
        return answer != null && answer.trim().equalsIgnoreCase(expectedAnswer);
    }
}

class Examination {
    private String title;
    private List<ExamQuestion> questions = new ArrayList<>();

    public Examination(String title) {
        this.title = title;
    }

    public void addQuestion(ExamQuestion question) {
        questions.add(question);
    }

    public String getTitle() {
        return title;
    }

    public List<ExamQuestion> getQuestions() {
        return Collections.unmodifiableList(questions);
    }
}

class ExamAttempt {
    private final Examination examination;
    private final Map<Integer, String> answers = new HashMap<>();
    private boolean submitted;

    public ExamAttempt(Examination examination) {
        this.examination = examination;
    }

    public void answer(int questionNumber, String answer) {
        if (submitted) throw new IllegalStateException("Attempt already submitted");
        if (questionNumber < 1 || questionNumber > examination.getQuestions().size())
            throw new IllegalArgumentException("Invalid question number");
        answers.put(questionNumber, answer);
        System.out.println("Question " + questionNumber + " answered with '" + answer + "'.");
    }

    public void submit() {
        if (submitted) throw new IllegalStateException("Attempt already submitted");
        submitted = true;
        int score = 0;
        List<ExamQuestion> questions = examination.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).checkAnswer(answers.get(i + 1))) score++;
        }
        System.out.println("Examination '" + examination.getTitle() + "' submitted successfully.");
        System.out.println("Result for '" + examination.getTitle() + "' attempt: "
                + score + "/" + questions.size() + " correct");
    }

    public boolean isSubmitted() {
        return submitted;
    }
}

class Student {
    private String name;
    private Map<String, ExamAttempt> attempts = new HashMap<>();

    public Student(String name) {
        this.name = name;
    }

    public ExamAttempt start(Examination examination) {
        ExamAttempt old = attempts.get(examination.getTitle());
        if (old != null && old.isSubmitted())
            throw new IllegalStateException("A submitted attempt already exists for this examination");
        if (old != null) return old;
        ExamAttempt attempt = new ExamAttempt(examination);
        attempts.put(examination.getTitle(), attempt);
        System.out.println("Examination '" + examination.getTitle() + "' started by " + name + ".");
        return attempt;
    }
}

public class OnlineExaminationSystem {
    public static void main(String[] args) {
        Examination exam = new Examination("Math Quiz");
        exam.addQuestion(new MultipleChoiceQuestion("2 + 2?", "A"));
        exam.addQuestion(new MultipleChoiceQuestion("3 + 3?", "B"));

        Student student = new Student("Student");
        ExamAttempt attempt = student.start(exam);
        attempt.answer(1, "A");
        attempt.answer(2, "C");
        attempt.submit();
    }
}
