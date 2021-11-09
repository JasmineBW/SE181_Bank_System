import java.util.ArrayList;

public class Output {
    private static ArrayList<String> invalidCommandsOutput;

    public Output() {
        invalidCommandsOutput = new ArrayList<String>();
    }

    public ArrayList accessInvalidOutputList() {
        return invalidCommandsOutput;
    }

    public void updateInvalidCommandsList(String userInput) {
        invalidCommandsOutput.add(userInput);
    }

}
