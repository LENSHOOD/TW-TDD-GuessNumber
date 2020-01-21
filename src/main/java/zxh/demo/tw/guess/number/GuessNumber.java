package zxh.demo.tw.guess.number;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * zxh.demo.tw.guess.number.GuessNumber:
 * @author zhangxuhai
 * @date 2020/1/20
*/
public class GuessNumber {
    private static final Random RANDOM = new Random();
    private static final int SECRET_LEN = 4;
    private int[] secretNumber;
    private List<String> resultCache = Lists.newArrayList();

    void prepareToGuess() {
        secretNumber = generateSecretNumber();
    }

    int[] generateSecretNumber() {
        Set<Integer> secretSet = Sets.newTreeSet();
        do {
            secretSet.add(RANDOM.nextInt(10));
        } while (secretSet.size() != SECRET_LEN);

        return secretSet.stream().mapToInt(i -> i).toArray();
    }

    public GuessResult compareWith(int[] guessed) {
        GuessResult result = new GuessResult();
        Set<Integer> ignoreForB = Sets.newHashSet();
        for (int i = 0; i < SECRET_LEN; i++) {
            if (guessed[i] == secretNumber[i]) {
                result.increaseA();
                ignoreForB.add(guessed[i]);
            }
        }

        int distinctLength = IntStream.concat(IntStream.of(guessed), IntStream.of(secretNumber))
                .filter(i -> !ignoreForB.contains(i))
                .distinct()
                .toArray()
                .length;

        result.setB(guessed.length + secretNumber.length - ignoreForB.size() * 2 - distinctLength);
        return result;
    }

    int[] takeInput(String input) {
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

    public String outputResult(String result) {
        resultCache.add(result);
        return String.join("\n", resultCache);
    }
}
