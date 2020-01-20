package zxh.demo.tw.guess.number;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        assertEquals(4, result.getA());
    }

    @Test
    public void should_return_2_A_when_match_2_digit() {
        GuessNumber guessNumber = spy(new GuessNumber());
        int[] secret = {1, 2, 3, 4};
        when(guessNumber.generateSecretNumber()).thenReturn(secret);
        int[] guessed = {1, 4, 3, 2};

        guessNumber.prepareToGuess();
        GuessResult result = guessNumber.compareWith(guessed);

        assertEquals(2, result.getA());
    }

    @Test
    public void should_return_0_A_when_none_match() {
        GuessNumber guessNumber = spy(new GuessNumber());
        int[] secret = {1, 2, 3, 4};
        when(guessNumber.generateSecretNumber()).thenReturn(secret);
        int[] guessed = {5, 6, 7, 8};

        guessNumber.prepareToGuess();
        GuessResult result = guessNumber.compareWith(guessed);

        assertEquals(0, result.getA());
    }

    @Test
    public void should_return_4_B_when_all_equal_but_none_match() {
        GuessNumber guessNumber = spy(new GuessNumber());
        int[] secret = {1, 2, 3, 4};
        when(guessNumber.generateSecretNumber()).thenReturn(secret);
        int[] guessed = {4, 3, 2, 1};

        guessNumber.prepareToGuess();
        GuessResult result = guessNumber.compareWith(guessed);

        assertEquals(4, result.getB());
    }

    @Test
    public void should_return_2_B_when_none_match_but_2_equal() {
        GuessNumber guessNumber = spy(new GuessNumber());
        int[] secret = {1, 2, 3, 4};
        when(guessNumber.generateSecretNumber()).thenReturn(secret);
        int[] guessed = {4, 5, 2, 0};

        guessNumber.prepareToGuess();
        GuessResult result = guessNumber.compareWith(guessed);

        assertEquals(2, result.getB());
    }

    @Test
    public void should_return_0_B_when_none_match_none_equal() {
        GuessNumber guessNumber = spy(new GuessNumber());
        int[] secret = {1, 2, 3, 4};
        when(guessNumber.generateSecretNumber()).thenReturn(secret);
        int[] guessed = {5, 6, 7, 8};

        guessNumber.prepareToGuess();
        GuessResult result = guessNumber.compareWith(guessed);

        assertEquals(0, result.getB());
    }

    @Test
    public void should_return_2_A_2_B_when_2_match_2_equal() {
        GuessNumber guessNumber = spy(new GuessNumber());
        int[] secret = {1, 2, 3, 4};
        when(guessNumber.generateSecretNumber()).thenReturn(secret);
        int[] guessed = {1, 4, 3, 2};

        guessNumber.prepareToGuess();
        GuessResult result = guessNumber.compareWith(guessed);

        assertEquals(2, result.getA());
        assertEquals(2, result.getB());
    }
}
