import java.util.List;

public abstract class Validator {

    private static final List<String> approvedAccounts = List.of("savings", "checking", "cd");
    private static List<String> approvedCommands = List.of("create", "deposit", "withdrawal", "transfer", "pass", "output");
    public OutputStorage invalidCommandsOutput = new OutputStorage();
    protected Bank bank;

    public static boolean commandChecker(String command) {
        command = command.toLowerCase();
        return approvedCommands.contains(command);
    }

    public static boolean accountTypeChecker(String accountType) {
        accountType = accountType.toLowerCase();
        return approvedAccounts.contains(accountType);
    }

    public static boolean idChecker(String id) {
        int integerId;
        try {
            integerId = Integer.valueOf(id).intValue();
        } catch (NumberFormatException e) {
            return false;
        }

        if (id.length() != 8) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean aprChecker(String apr) {
        double doubleApr;
        try {
            doubleApr = Double.valueOf(apr).doubleValue();
        } catch (NumberFormatException e) {
            return false;
        }
        if (doubleApr < 0 || doubleApr > 10) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean amountChecker(String amount) {
        double doubleAmount;
        try {
            doubleAmount = Double.valueOf(amount).doubleValue();
        } catch (NumberFormatException e) {
            return false;
        }

        if (doubleAmount < 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean hasAccountWithIdCheck(String id) {
        int integerId = Integer.valueOf(id).intValue();
        if (bank.containsIdNumber(integerId)) {
            return true;
        } else {
            return false;
        }
    }
}