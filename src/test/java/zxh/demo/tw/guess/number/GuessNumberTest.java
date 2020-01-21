package zxh.demo.tw.guess.number;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * zxh.demo.tw.guess.number.GuessNumberTest:
 * @author zhangxuhai
 * @date 2020/1/20
*/
public class GuessNumberTest {
    @Test
    public void should_generate_4_digits_number_when_build() {
        GuessNumber guessNumber = new GuessNumber();
        assertEquals(4, guessNumber.generateSecretNumber().length);
    }

    @Test
    public void should_generate_4_digits_number_every_digit_from_0_to_9() {
        GuessNumber guessNumber = new GuessNumber();

        int[] secret = guessNumber.generateSecretNumber();

        IntPredicate isFrom0To9 = i -> i >= 0 && i <= 9;
        IntStream.of(secret).forEach(i -> assertTrue(isFrom0To9.test(i)));
    }

    @Test
    public void should_generate_4_digits_number_with_no_duplicate_digit() {
        GuessNumber guessNumber = new GuessNumber();

        int[] secret = guessNumber.generateSecretNumber();

        assertEquals(4, IntStream.of(secret).distinct().toArray().length);
    }

    @Test
    public void should_return_4_A_when_all_match() {
        GuessNumber guessNumber = spy(new GuessNumber());
        int[] secret = {1, 2, 3, 4};
        when(guessNumber.generateSecretNumber()).thenReturn(secret);
        int[] guessed = Arrays.copyOf(secret, 4);

        guessNumber.prepareToGuess();
        GuessResult result = guessNumber.compareWith(guessed);

        assertEquals(4, result.getA().size());
    }

    @Test
    public void should_return_2_A_when_match_2_digit() {
        GuessNumber guessNumber = spy(new GuessNumber());
        int[] secret = {1, 2, 3, 4};
        when(guessNumber.generateSecretNumber()).thenReturn(secret);
        int[] guessed = {1, 4, 3, 2};

        guessNumber.prepareToGuess();
        GuessResult result = guessNumber.compareWith(guessed);

        assertEquals(2, result.getA().size());
    }

    @Test
    public void should_return_0_A_when_none_match() {
        GuessNumber guessNumber = spy(new GuessNumber());
        int[] secret = {1, 2, 3, 4};
        when(guessNumber.generateSecretNumber()).thenReturn(secret);
        int[] guessed = {5, 6, 7, 8};

        guessNumber.prepareToGuess();
        GuessResult result = guessNumber.compareWith(guessed);

        assertEquals(0, result.getA().size());
    }

    @Test
    public void should_return_4_B_when_all_equal_but_none_match() {
        GuessNumber guessNumber = spy(new GuessNumber());
        int[] secret = {1, 2, 3, 4};
        when(guessNumber.generateSecretNumber()).thenReturn(secret);
        int[] guessed = {4, 3, 2, 1};

        guessNumber.prepareToGuess();
        GuessResult result = guessNumber.compareWith(guessed);

        assertEquals(4, result.getB().size());
    }

    @Test
    public void should_return_2_B_when_none_match_but_2_equal() {
        GuessNumber guessNumber = spy(new GuessNumber());
        int[] secret = {1, 2, 3, 4};
        when(guessNumber.generateSecretNumber()).thenReturn(secret);
        int[] guessed = {4, 5, 2, 0};

        guessNumber.prepareToGuess();
        GuessResult result = guessNumber.compareWith(guessed);

        assertEquals(2, result.getB().size());
    }

    @Test
    public void should_return_0_B_when_none_match_none_equal() {
        GuessNumber guessNumber = spy(new GuessNumber());
        int[] secret = {1, 2, 3, 4};
        when(guessNumber.generateSecretNumber()).thenReturn(secret);
        int[] guessed = {5, 6, 7, 8};

        guessNumber.prepareToGuess();
        GuessResult result = guessNumber.compareWith(guessed);

        assertEquals(0, result.getB().size());
    }

    @Test
    public void should_return_2_A_2_B_when_2_match_2_equal() {
        GuessNumber guessNumber = spy(new GuessNumber());
        int[] secret = {1, 2, 3, 4};
        when(guessNumber.generateSecretNumber()).thenReturn(secret);
        int[] guessed = {1, 4, 3, 2};

        guessNumber.prepareToGuess();
        GuessResult result = guessNumber.compareWith(guessed);

        assertEquals(2, result.getA().size());
        assertEquals(2, result.getB().size());
    }

    @Test
    public void should_throw_invalid_guess_number_exception_when_input_number_not_4_digits() {
        GuessNumber guessNumber = new GuessNumber();
        assertThrows(InvalidGuessNumberException.class, () -> guessNumber.takeInput("12345"));
    }

    @Test
    public void should_throw_invalid_guess_number_exception_when_input_number_not_integer() {
        GuessNumber guessNumber = new GuessNumber();
        assertThrows(InvalidGuessNumberException.class, () -> guessNumber.takeInput("1a2b"));
    }

    @Test
    public void should_throw_invalid_guess_number_exception_when_input_number_have_duplicates() {
        GuessNumber guessNumber = new GuessNumber();
        assertThrows(InvalidGuessNumberException.class, () -> guessNumber.takeInput("1123"));
    }

    @Test
    public void should_1_2_3_4_int_array_when_input_1234() throws InvalidGuessNumberException {
        GuessNumber guessNumber = new GuessNumber();

        int[] guessInput = guessNumber.takeInput("1234");

        assertArrayEquals(new int[]{1, 2, 3, 4}, guessInput);
    }

    @Test
    public void should_return_1A1B_2A2B_when_output_twice() {
        GuessNumber guessNumber = new GuessNumber();
        GuessResult guessResult = new GuessResult();
        guessResult.addA(1);
        guessResult.addB(2);
        guessNumber.outputResult(guessResult);
        guessResult.addA(3);
        guessResult.addB(4);

        String result = guessNumber.outputResult(guessResult);

        assertArrayEquals(new String[]{"1A1B", "2A2B"}, result.split("\n"));
    }

    @Test
    public void should_return_1A1B_2A2B_wrong_input_input_again_when_output_third() {
        GuessNumber guessNumber = new GuessNumber();
        GuessResult guessResult = new GuessResult();
        guessResult.addA(1);
        guessResult.addB(2);
        guessNumber.outputResult(guessResult);
        guessResult.addA(3);
        guessResult.addB(4);
        guessNumber.outputResult(guessResult);

        String result = guessNumber.outputResult(GuessResult.createInvalidResult());

        assertArrayEquals(new String[]{"1A1B", "2A2B", "Wrong input, input again"}, result.split("\n"));
    }

    @Test
    public void should_return_wrong_input_input_again_1A1B_4A0B_when_guess_correct_after_third_time_guess() {
        GuessNumber guessNumber = spy(new GuessNumber());
        when(guessNumber.generateSecretNumber()).thenReturn(new int[]{1, 2, 3, 4});
        guessNumber.prepareToGuess();

        guessNumber.guess("1167");
        guessNumber.guess("1367");
        String result = guessNumber.guess("1234");

        assertArrayEquals(new String[]{"Wrong input, input again", "1A1B", "4A0B"}, result.split("\n"));
        assertTrue(Thread.interrupted());
    }

    @Test
    public void should_return_wrong_input_input_again_1A1B_1A1B_1A1B_1A1B_1A1B_when_after_sixth_time_guess() {
        GuessNumber guessNumber = spy(new GuessNumber());
        when(guessNumber.generateSecretNumber()).thenReturn(new int[]{1, 2, 3, 4});
        guessNumber.prepareToGuess();

        guessNumber.guess("1167");
        guessNumber.guess("1367");
        guessNumber.guess("1367");
        guessNumber.guess("1367");
        guessNumber.guess("1367");
        String result = guessNumber.guess("1367");

        assertArrayEquals(new String[]{"Wrong input, input again", "1A1B", "1A1B", "1A1B", "1A1B", "1A1B"}, result.split("\n"));
        assertTrue(Thread.interrupted());
    }
}
