import java.util.ArrayList;

public class OutputStorage {
    private static ArrayList<String> invalidCommandsOutput;

    public OutputStorage() {
        invalidCommandsOutput = new ArrayList<String>();
    }

    public ArrayList accessInvalidOutputList() {
        return invalidCommandsOutput;
    }

    public void updateInvalidCommandsList(String userInput) {
        invalidCommandsOutput.add(userInput);
    }

}
