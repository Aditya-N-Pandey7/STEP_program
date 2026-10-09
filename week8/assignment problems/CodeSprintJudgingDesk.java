import java.util.*;

interface ScoringRule {
    double calculate(double idea, double execution, double presentation);
    String getTrackName();
}

class InnovationTrack implements ScoringRule {
    public double calculate(double idea, double execution, double presentation) {
        return idea * 0.5 + execution * 0.3 + presentation * 0.2;
    }
    public String getTrackName() { return "Innovation"; }
}

class OpenTrack implements ScoringRule {
    public double calculate(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
    }
    public String getTrackName() { return "Open"; }
}

class Student {
    private String name;
    public Student(String name) { this.name = name; }
    public String getName() { return name; }
}

class Project {
    private String title;
    private Team team;
    private double finalScore;
    private boolean scored;

    public Project(String title, Team team) {
        this.title = title;
        this.team = team;
    }

    public String getTitle() { return title; }
    public Team getTeam() { return team; }

    public void score(double idea, double execution, double presentation, ScoringRule rule) {
        if (idea < 0 || idea > 10 || execution < 0 || execution > 10
                || presentation < 0 || presentation > 10)
            throw new IllegalArgumentException("Each rating must be between 0 and 10");
        finalScore = rule.calculate(idea, execution, presentation);
        scored = true;
    }

    public double getFinalScore() {
        if (!scored) throw new IllegalStateException("Project has not been scored");
        return finalScore;
    }
}

class Team {
    private String name;
    private List<Student> members;
    private ScoringRule track;
    private Project project;

    public Team(String name, List<Student> members, ScoringRule track) {
        if (members == null || members.size() < 2 || members.size() > 4)
            throw new IllegalArgumentException("A team must have 2 to 4 members");
        this.name = name;
        this.members = new ArrayList<>(members);
        this.track = track;
    }

    public String getName() { return name; }
    public ScoringRule getTrack() { return track; }

    public Project submitProject(String title) {
        if (project != null) throw new IllegalStateException("A team can submit only one project");
        project = new Project(title, this);
        return project;
    }
}

class Hackathon {
    enum State { OPEN, JUDGING, PUBLISHED }
    private State state = State.OPEN;
    private List<Team> teams = new ArrayList<>();
    private Set<Student> registeredStudents = new HashSet<>();
    private List<Project> projects = new ArrayList<>();

    public void register(Team team) {
        if (state != State.OPEN) throw new IllegalStateException("Registration is closed");
        for (Student s : getMembers(team)) {
            if (registeredStudents.contains(s))
                throw new IllegalArgumentException(s.getName() + " already belongs to a team");
        }
        teams.add(team);
        registeredStudents.addAll(getMembers(team));
        System.out.println("Team " + team.getName() + " registered ("
                + getMembers(team).size() + " members, " + team.getTrack().getTrackName() + " track).");
    }

    private List<Student> getMembers(Team team) {
        try {
            java.lang.reflect.Field f = Team.class.getDeclaredField("members");
            f.setAccessible(true);
            return (List<Student>) f.get(team);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    public void submit(Team team, String title) {
        if (state == State.PUBLISHED) throw new IllegalStateException("Results published");
        if (!teams.contains(team)) throw new IllegalArgumentException("Team is not registered");
        Project p = team.submitProject(title);
        projects.add(p);
        state = State.JUDGING;
        System.out.println("Project '" + title + "' submitted by " + team.getName() + ".");
    }

    public void score(Project project, double idea, double execution, double presentation) {
        if (state == State.PUBLISHED) {
            System.out.println("Rescore rejected: Results have already been published.");
            return;
        }
        project.score(idea, execution, presentation, project.getTeam().getTrack());
        System.out.println("Score recorded for '" + project.getTitle() + "'.");
        System.out.printf("Final score: %.2f%n", project.getFinalScore());
    }

    public void publishResults() {
        state = State.PUBLISHED;
        System.out.println("Results published.");
    }
}

public class CodeSprintJudgingDesk {
    public static void main(String[] args) {
        Hackathon h = new Hackathon();
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");
        Team team = new Team("ByteBusters",
                Arrays.asList(asha, ravi, neha), new InnovationTrack());
        h.register(team);

        try {
            h.register(new Team("SoloCoder",
                    Arrays.asList(new Student("Kiran")), new OpenTrack()));
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
        }

        h.submit(team, "SmartAttend");
        Project p = team.submitProject("Duplicate");
    }
}
