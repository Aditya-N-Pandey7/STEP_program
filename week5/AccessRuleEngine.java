package week5;

/**
 * AccessRuleEngine simulates Java compile-time access control checking.
 * It classifies field access based on the field modifier and the accessor context,
 * mirroring Java language visibility rules.
 */
public class AccessRuleEngine {

    /**
     * Classifies whether an access attempt to a field with the given modifier
     * is permitted from the specified accessor context.
     *
     * @param fieldModifier   Access modifier: "private", "default", "protected", "public"
     * @param accessorContext Context: "SAME_CLASS", "SAME_PACKAGE", "DIFFERENT_PACKAGE",
     *                        "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
     * @return "ALLOWED" if the access is valid according to Java rules, otherwise "DENIED"
     */
    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }

        String modifier = fieldModifier.trim().toLowerCase();
        String context = accessorContext.trim().toUpperCase();

        switch (modifier) {
            case "public":
                // Public fields are accessible everywhere
                return "ALLOWED";

            case "private":
                // Private fields are strictly visible only within the enclosing class
                return "SAME_CLASS".equals(context) ? "ALLOWED" : "DENIED";

            case "default":
                // Package-private visibility: accessible in same class and same package
                if ("SAME_CLASS".equals(context) || "SAME_PACKAGE".equals(context)) {
                    return "ALLOWED";
                }
                return "DENIED";

            case "protected":
                // Protected visibility rules:
                // 1. Same class and same package always allowed.
                // 2. Subclass in a different package can ONLY access protected members
                //    through a reference of its own type (or a subtype).
                // 3. Access through a parent reference from outside the package is illegal at compile time.
                if ("SAME_CLASS".equals(context) || "SAME_PACKAGE".equals(context)) {
                    return "ALLOWED";
                }
                if ("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(context)) {
                    return "ALLOWED";
                }
                return "DENIED";

            default:
                // Unrecognized modifier
                return "DENIED";
        }
    }

    /**
     * Evaluates a batch of access attempts and returns a summary count of allowed and denied attempts.
     *
     * @param attempts A 2D array where each row is {fieldModifier, accessorContext}
     * @return Formatted summary string: "Allowed: X | Denied: Y"
     */
    public static String summarizeBatch(String[][] attempts) {
        if (attempts == null) {
            return "Allowed: 0 | Denied: 0";
        }

        int allowedCount = 0;
        int deniedCount = 0;

        for (String[] attempt : attempts) {
            if (attempt == null || attempt.length < 2) {
                deniedCount++;
                continue;
            }

            String decision = classifyAccess(attempt[0], attempt[1]);
            if ("ALLOWED".equals(decision)) {
                allowedCount++;
            } else {
                deniedCount++;
            }
        }

        return "Allowed: " + allowedCount + " | Denied: " + deniedCount;
    }

    /**
     * Converts an uppercase, underscore-separated context code into a human-readable title-cased sentence.
     * Example: "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE" -> "Subclass Different Package Parent Type"
     *
     * @param accessorContext The uppercase context string
     * @return Readable title-cased string
     */
    public static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.trim().isEmpty()) {
            return "";
        }

        String[] tokens = accessorContext.trim().split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.isEmpty()) {
                continue;
            }

            if (sb.length() > 0) {
                sb.append(" ");
            }

            // Convert word to Title Case: first letter upper, remainder lower
            sb.append(Character.toUpperCase(token.charAt(0)));
            if (token.length() > 1) {
                sb.append(token.substring(1).toLowerCase());
            }
        }

        return sb.toString();
    }
}
