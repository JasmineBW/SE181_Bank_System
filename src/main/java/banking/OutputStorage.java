package banking;

import java.util.ArrayList;
import java.util.Map;

public class OutputStorage {
    private static ArrayList<String> invalidCommandsOutput;
    private ArrayList<String> commands;

    public OutputStorage(Bank bank) {
        invalidCommandsOutput = new ArrayList<String>();
        commands = new ArrayList<String>();
    }

    public ArrayList accessInvalidOutputList() {
        return invalidCommandsOutput;
    }

    public ArrayList accessOutputsList(Bank bank) {
        for (Map.Entry<Integer, Account> accountEntry : bank.getListOfAccounts().entrySet()) {
            commands.addAll(accountEntry.getValue().validCommandsStorage);
        }
        commands.addAll(invalidCommandsOutput);
        return commands;
    }

    public void updateInvalidCommandsList(String userInput) {
        invalidCommandsOutput.add(userInput);
    }

}
