package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputParserTest {
    String input;
    String components[];

    @Test
    void invalid_command_correctly_split_up() {
        input = "creat checking 12345678 1.0 2000";
        components = InputParser.split(input);
        assertEquals(2, components.length);
        assertEquals("checking 12345678 1.0 2000", InputParser.getExtra());
    }

    @Test
    void valid_create_cd_command_correctly_split_up() {
        input = "create cd 24681012 2 1200";
        components = InputParser.split(input);
        System.out.println(InputParser.getAccountType());
        assertEquals("create", InputParser.getCommand());
        assertEquals("1200", InputParser.getAmount());
    }

    @Test
    void extra_arguments_correctly_labelled() {
        input = "withdraw 23456789 400 2000";
        components = InputParser.split(input);
        assertEquals("2000", InputParser.getExtra());
    }

    @Test
    void invalid_inputs_are_still_correctly_split() {
        input = "d 200";
        components = InputParser.split(input);
        assertEquals("d", InputParser.getCommand());
        assertEquals("200", InputParser.getExtra());
    }

    @Test
    void extra_spaces_before_and_within_input_is_caught() {
        input = "deposit   23456789  300";
        components = InputParser.split(input);
        assertEquals("", components[1]);
        assertEquals("23456789  300", InputParser.getExtra());
    }
}
