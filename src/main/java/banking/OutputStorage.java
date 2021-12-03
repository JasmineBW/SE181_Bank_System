package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

public class OutputStorage {
    private static ArrayList<String> invalidCommandsOutput;
    private ArrayList<String> commands;
    private Account account;

    public OutputStorage(Bank bank) {
        invalidCommandsOutput = new ArrayList<String>();
        commands = new ArrayList<String>();
    }

    public ArrayList accessInvalidOutputList() {
        return invalidCommandsOutput;
    }

    public ArrayList accessOutputsList(Bank bank) {

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.FLOOR);

        for (Map.Entry<Integer, Account> accountEntry : bank.getListOfAccounts().entrySet()) {
            account = accountEntry.getValue();
            account.validCommandsStorage.add(0, capitalize(account.getAccountType()) + " " + account.getID() + " " +
                    df.format(account.getAccountBalance()) + " " + df.format(account.getAPR()));
            commands.addAll(account.validCommandsStorage);
        }
        commands.addAll(invalidCommandsOutput);
        return commands;
    }

    public void updateInvalidCommandsList(String userInput) {
        invalidCommandsOutput.add(userInput);
    }

    private String capitalize(String string) {
        String firstletter = string.substring(0, 1);
        String restletters = string.substring(1);
        firstletter = firstletter.toUpperCase();
        string = firstletter + restletters;
        return string;
    }

}
