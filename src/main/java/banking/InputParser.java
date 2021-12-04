package banking;

import java.util.Objects;

public class InputParser {
    static String command = "", accountType = "", id = "", apr = "", amount = "", idFrom = "", idTo = " ", months = "", extra = "";

    private static void assignComponents(String[] split, String[] components) {
        int i;
        for (i = 0; i < split.length; i++) {
            components[i] = split[i];
        }
    }

    private static void clearMemory() {
        command = "";
        accountType = "";
        id = "";
        apr = "";
        amount = "";
        idFrom = "";
        idTo = " ";
        months = "";
        extra = "";
    }

    public static void split(String input) {
        String split[] = input.split(" ", 0);
        clearMemory();
        command = split[0].toLowerCase();


        if (Objects.equals(command, "create")) {
            split = input.split(" ", 6);
            String[] components = {command, accountType, id, apr, amount, extra};
            assignComponents(split, components);
            accountType = components[1];
            id = components[2];
            apr = components[3];
            amount = components[4];
            extra = components[5];

        } else if (Objects.equals(command, "deposit") || Objects.equals(command, "withdraw")) {
            split = input.split(" ", 4);
            String[] components = {command, id, amount, extra};
            assignComponents(split, components);
            id = components[1];
            amount = components[2];
            extra = components[3];

        } else if (Objects.equals(command, "transfer")) {
            split = input.split(" ", 5);
            String[] components = {command, idFrom, idTo, amount, extra};
            assignComponents(split, components);
            idFrom = components[1];
            idTo = components[2];
            amount = components[3];
            extra = components[4];

        } else if (Objects.equals(command, "pass")) {
            split = input.split(" ", 3);
            String[] components = {command, months, extra};
            assignComponents(split, components);
            months = components[1];
            extra = components[2];

        } else {
            split = input.split(" ", 2);
            String[] components = {command, extra};
            assignComponents(split, components);
            extra = components[1];

        }
    }


}
