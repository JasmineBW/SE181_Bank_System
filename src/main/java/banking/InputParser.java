package banking;

import java.util.Objects;

public class InputParser {
    static String command = "", accountType = "", id = "", apr = "", amount = "", idFrom = "", idTo = " ", months = "", extra = "";

    private static String[] assignComponents(String[] split, String[] components) {
        int i;
        for (i = 0; i < split.length; i++) {
            components[i] = split[i];
        }
        return components;
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

    public static String[] split(String input) {
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
            return components;

        } else if (Objects.equals(command, "deposit") || Objects.equals(command, "withdraw")) {
            split = input.split(" ", 4);
            String[] components = {command, id, amount, extra};
            assignComponents(split, components);
            id = components[1];
            amount = components[2];
            extra = components[3];
            return components;

        } else if (Objects.equals(command, "transfer")) {
            split = input.split(" ", 5);
            String[] components = {command, idFrom, idTo, amount, extra};
            assignComponents(split, components);
            idFrom = components[1];
            idTo = components[2];
            amount = components[3];
            extra = components[4];
            return components;

        } else if (Objects.equals(command, "pass")) {
            split = input.split(" ", 3);
            String[] components = {command, months, extra};
            assignComponents(split, components);
            months = components[1];
            extra = components[2];
            return components;

        } else {
            split = input.split(" ", 2);
            String[] components = {command, extra};
            assignComponents(split, components);
            extra = components[1];
            return components;
        }
    }

    public static String getCommand() {
        return command;
    }

    public static String getAccountType() {
        return accountType;
    }

    public static String getId() {
        return id;
    }

    public static String getIdFrom() {
        return idFrom;
    }

    public static String getIdTo() {
        return idTo;
    }

    public static String getAmount() {
        return amount;
    }

    public static String getApr() {
        return apr;
    }

    public static String getMonths() {
        return months;
    }

    public static String getExtra() {
        return extra;
    }
}
