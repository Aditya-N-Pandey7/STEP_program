public class BankTransactionReferenceGeneratorValidator {
    static String normalizeReference(String raw) {
        String reference = raw.trim();

        if (reference.length() < 3) {
            return reference.toUpperCase();
        }

        return reference.substring(0, 3).toUpperCase() + reference.substring(3);
    }

    static String validateAndFormat(String reference) {
        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        for (int i = 3; i < 14; i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: body must contain only digits";
            }
        }

        String bankCode = reference.substring(0, 3);
        String date = reference.substring(3, 5) + "/"
                + reference.substring(5, 7) + "/"
                + reference.substring(7, 9);
        String sequence = reference.substring(9, 14);

        StringBuilder result = new StringBuilder();
        result.append("[").append(bankCode).append("] DATE: ")
                .append(date).append(" | SEQ: ").append(sequence);

        return result.toString();
    }

    public static void main(String[] args) {
        String reference1 = normalizeReference(" hdf03022600042 ");
        System.out.println(validateAndFormat(reference1));

        String reference2 = normalizeReference("12F03022600042");
        System.out.println(validateAndFormat(reference2));
    }
}
