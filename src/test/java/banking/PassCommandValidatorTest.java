package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassCommandValidatorTest {

    private String command = "pass";
    private String months;
    private String extraArguments = "";
    private boolean output;


    @Test
    void valid_pass_command_entered() {
        months = "10";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertTrue(output);
    }

    @Test
    void typo_in_pass_command() {
        command = "pas";
        months = "2";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertFalse(output);
    }

    @Test
    void pass_command_omitted() {
        command = "20";
        months = "";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertFalse(output);
    }

    @Test
    void insufficient_arguments_supplied() {
        command = "pass";
        months = "";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertFalse(output);
    }

    @Test
    void extra_arguments_supplied() {
        command = "pass";
        months = "26789101";
        extraArguments = "20";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertFalse(output);
    }

    @Test
    void months_entered_is_not_a_number() {
        months = "fifteen";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertFalse(output);
    }

    @Test
    void months_entered_is_a_number_but_not_an_integer() {
        months = "1.5";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertFalse(output);
    }

    @Test
    void months_entered_is_negative() {
        months = "-50";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertFalse(output);
    }

    @Test
    void months_entered_less_than_1_and_equal_to_0() {
        months = "0";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertFalse(output);
    }

    @Test
    void months_entered_equal_to_2() {
        months = "2";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertTrue(output);
    }

    @Test
    void months_entered_equal_to_1() {
        months = "1";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertTrue(output);
    }

    @Test
    void months_entered_greater_than_1() {
        months = "30";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertTrue(output);
    }

    @Test
    void months_entered_greater_than_60() {
        months = "80";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertFalse(output);
    }

    @Test
    void months_entered_equal_to_60() {
        months = "60";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertTrue(output);
    }

    @Test
    void months_entered_less_than_60() {
        months = "30";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertTrue(output);
    }


    @Test
    void months_entered_equal_to_59() {
        months = "59";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertTrue(output);
    }

    @Test
    void months_entered_equal_to_61() {
        months = "61";
        output = PassCommandValidator.validate(command, months, extraArguments);
        assertFalse(output);
    }
}
