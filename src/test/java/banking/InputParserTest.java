package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputParserTest {
    String input;
    String components[];

    @Test
    void invalid_command_correctly_split_up() {
        input = "creat checking 12345678 1.0 2000";
        InputParser.split(input);
        assertEquals("checking 12345678 1.0 2000", InputParser.extra);
    }

    @Test
    void valid_create_cd_command_correctly_split_up() {
        input = "create cd 24681012 2 1200";
        InputParser.split(input);
        System.out.println(InputParser.accountType);
        assertEquals("create", InputParser.command);
        assertEquals("1200", InputParser.amount);
    }

    @Test
    void extra_arguments_correctly_labelled() {
        input = "withdraw 23456789 400 2000";
        InputParser.split(input);
        assertEquals("2000", InputParser.extra);
    }

    @Test
    void invalid_inputs_are_still_correctly_split() {
        input = "d 200";
        InputParser.split(input);
        assertEquals("d", InputParser.command);
        assertEquals("200", InputParser.extra);
    }

    @Test
    void extra_spaces_before_and_within_input_is_caught() {
        input = "deposit   23456789  300";
        InputParser.split(input);
        assertEquals("", InputParser.id);
        assertEquals("23456789  300", InputParser.extra);
    }
}
