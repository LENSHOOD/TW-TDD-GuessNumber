package zxh.demo.tw.guess.number;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * zxh.demo.tw.guess.number.GuessNumber:
 * @author zhangxuhai
 * @date 2020/1/20
*/
public class GuessNumber {
    private static final Random RANDOM = new Random();
    private static final int SECRET_LEN = 4;
    private static final int MAX_GUESS_TRY = 6;
    public static final String CORRECT_RESULT = "4A0B";

    private int[] secretNumber;
    private List<String> resultCache = Lists.newArrayList();
    private int guessTimes;

    public void prepareToGuess() {
        secretNumber = generateSecretNumber();
    }

    int[] generateSecretNumber() {
        Set<Integer> secretSet = Sets.newTreeSet();
        do {
            secretSet.add(RANDOM.nextInt(10));
        } while (secretSet.size() != SECRET_LEN);

        return secretSet.stream().mapToInt(i -> i).toArray();
    }

    public String guess(String input) {
        guessTimes++;
        String result = "";
        try {
            result =  outputResult(compareWith(takeInput(input)));
            return result;
        } catch (InvalidGuessNumberException e) {
            result = outputResult(GuessResult.createInvalidResult());
            return result;
        } finally {
            String lastOutput = result.split("\n")[guessTimes - 1];
            if (CORRECT_RESULT.equals(lastOutput) || guessTimes == MAX_GUESS_TRY) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public GuessResult compareWith(int[] guessed) {
        GuessResult result = new GuessResult();
        for (int i = 0; i < SECRET_LEN; i++) {
            for (int j = 0; j < SECRET_LEN; j++) {
                if (secretNumber[i] != guessed[j]) {
                    continue;
                }

                if (i == j) {
                    result.addA(guessed[j]);
                } else {
                    result.addB(guessed[j]);
                }
            }
        }
        return result;
    }

    int[] takeInput(String input) throws InvalidGuessNumberException {
        if (input.length() != SECRET_LEN) {
            throw new InvalidGuessNumberException();
        }

        Set<Integer> guessNumberSet = Sets.newTreeSet();
        for (String c : input.split("")) {
            try {
                guessNumberSet.add(Integer.valueOf(c));
            } catch (NumberFormatException e) {
                throw new InvalidGuessNumberException();
            }
        }

        if (guessNumberSet.size() != SECRET_LEN) {
            throw new InvalidGuessNumberException();
        }

        return guessNumberSet.stream().mapToInt(i -> i).toArray();
    }

    public String outputResult(GuessResult guessResult) {
        String output = guessResult.isInvalid()
                ? "Wrong input, input again"
                : guessResult.getA().size() + "A" + guessResult.getB().size() + "B";
        resultCache.add(output);
        return String.join("\n", resultCache);
    }

    public static void main(String[] args) {
        GuessNumber guessNumber = new GuessNumber();
        guessNumber.prepareToGuess();

        Scanner scanner = new Scanner(System.in);
        while (!Thread.interrupted()) {
            String input = scanner.nextLine();
            System.out.println(guessNumber.guess(input));
        }
    }
}
