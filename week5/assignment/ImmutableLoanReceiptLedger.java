import java.util.Arrays;

public class ImmutableLoanReceiptLedger {
    public static final String TYPE = "LOAN_RECEIPT";

    public static final class LoanReceipt {
        private final String memberId;
        private final String[] bookIds;

        public LoanReceipt(String memberId, String[] bookIds) {
            if (memberId == null || bookIds == null) throw new IllegalArgumentException("Invalid receipt");
            for (String id : bookIds) {
                if (!isValidBookId(id)) throw new IllegalArgumentException("Invalid book ID");
            }
            this.memberId = memberId;
            this.bookIds = bookIds.clone();
        }

        public String[] getBookIds() {
            return bookIds.clone();
        }

        public LoanReceipt withCorrectedBookId(int index, String newId) {
            if (index < 0 || index >= bookIds.length || !isValidBookId(newId)) {
                throw new IllegalArgumentException("Invalid corrected book ID");
            }
            String[] corrected = bookIds.clone();
            corrected[index] = newId;
            return new LoanReceipt(memberId, corrected);
        }

        private static boolean isValidBookId(String id) {
            if (id == null || id.length() != 6 || !id.startsWith("BK-")) return false;
            return Character.isDigit(id.charAt(3))
                    && Character.isDigit(id.charAt(4))
                    && Character.isDigit(id.charAt(5));
        }
    }

    public static class ReferenceOnlyLoanReceipt {
        private final String memberId;
        private final String[] bookIds;
        private final String roomNumber;

        public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
            if (memberId == null || bookIds == null) throw new IllegalArgumentException("Invalid receipt");
            for (String id : bookIds) {
                if (!isValidBookId(id)) throw new IllegalArgumentException("Invalid book ID");
            }
            this.memberId = memberId;
            this.bookIds = bookIds.clone();
            this.roomNumber = roomNumber;
        }

        private static boolean isValidBookId(String id) {
            return id != null && id.matches("BK-[0-9]{3}");
        }
    }

    public static String processNightlyCirculation(Object[] receipts) {
        int processed = 0, nullSkipped = 0, referenceOnly = 0, regular = 0;
        for (Object receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
            } else if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
                processed++;
            } else if (receipt instanceof LoanReceipt) {
                regular++;
                processed++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | " + regular + " regular";
    }

    public static void main(String[] args) {
        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println(r.getBookIds()[0]);

        Object[] batch = {
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };
        System.out.println(processNightlyCirculation(batch));
    }
}
