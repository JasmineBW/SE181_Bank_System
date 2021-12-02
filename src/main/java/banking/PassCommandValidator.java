package banking;

import java.util.Objects;

public class PassCommandValidator extends Validator {

    public static boolean validate(String command, String months, String extraArguments) {
        if (!commandChecker(command) || !monthChecker(months) || !(Objects.equals(extraArguments, ""))) {
            return false;

        } else {
            return true;
        }
    }
}
