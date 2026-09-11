class BrokenLibraryMember {
    static String name;
    static String memberId;
    static int booksIssued;

    BrokenLibraryMember(String name, String memberId, int booksIssued) {
        BrokenLibraryMember.name = name;
        BrokenLibraryMember.memberId = memberId;
        BrokenLibraryMember.booksIssued = booksIssued;
    }
}

class FixedLibraryMember {
    private String name;
    private String memberId;
    private int booksIssued;
    static String libraryName = "Central Library";
    static int memberCount = 1000;

    FixedLibraryMember(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;
        memberCount++;
        this.memberId = "LM-" + memberCount;
    }

    void printMemberCard() {
        System.out.println(name + " | " + memberId);
    }

    static void printTotalMembers() {
        System.out.println("Total members: " + (memberCount - 1000));
    }
}

public class LibraryMemberStaticBoundary {
    public static void main(String[] args) {
        // Broken: name, memberId and booksIssued are per-member data, so static makes all objects share one copy.
        BrokenLibraryMember first = new BrokenLibraryMember("Aditi", "LM-1001", 1);
        BrokenLibraryMember second = new BrokenLibraryMember("Rohan", "LM-1002", 2);
        System.out.println(BrokenLibraryMember.name);
        System.out.println(BrokenLibraryMember.name);

        // Fixed: member-specific data is instance state; libraryName/memberCount are genuinely shared class state.
        FixedLibraryMember a = new FixedLibraryMember("Aditi", 1);
        FixedLibraryMember r = new FixedLibraryMember("Rohan", 2);
        a.printMemberCard();
        r.printMemberCard();
        FixedLibraryMember.printTotalMembers();
    }
}
