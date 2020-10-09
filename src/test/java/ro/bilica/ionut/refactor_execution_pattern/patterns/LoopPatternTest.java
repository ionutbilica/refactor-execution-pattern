package ro.bilica.ionut.refactor_execution_pattern.patterns;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.Optional;

public class LoopPatternTest extends TestCase {

    @Test
    public void testTryUntilTimeout() {
        Assert.assertEquals(Optional.of("Bingo!"), getResult(1, 2));
        Assert.assertEquals(Optional.of("Bingo!"), getResult(3, 4));
        Assert.assertEquals(Optional.empty(), getResult(4, 2));
    }

    @Test
    public void testTryUntilCountdown() {
        Assert.assertEquals(Optional.of("Bingo!"), getCountdownResult(1, 2));
        Assert.assertEquals(Optional.of("Bingo!"), getCountdownResult(3, 4));
        Assert.assertEquals(Optional.empty(), getCountdownResult(4, 2));
    }

    private Optional<String> getResult(int succeedAt, int timeout) {
        DummyAttempt dummyAttempt = new DummyAttempt(succeedAt);
        Optional<String> bingoOnFirstTry = new LoopPattern().tryUntilTimeout(() -> dummyAttempt.attempt("Bingo!"), Duration.ofSeconds(timeout), Duration.ofSeconds(1));
        return bingoOnFirstTry;
    }

    private Optional<String> getCountdownResult(int succeedAt, int maxTries) {
        DummyAttempt succeedOnFirstTry = new DummyAttempt(succeedAt);
        Optional<String> bingoOnFirstTry = new LoopPattern().tryUntil(() -> succeedOnFirstTry.attempt("Bingo!"), new Countdown(maxTries), Duration.ofSeconds(1));
        return bingoOnFirstTry;
    }

    private static class DummyAttempt {

        private int currentTry;
        private int succeedAt;

        public DummyAttempt(int succeedAt) {
            currentTry = 0;
            this.succeedAt = succeedAt;
        }

        public Optional<String> attempt(String str) {
            currentTry++;
            if (currentTry == succeedAt) {
                return Optional.of(str);
            } else {
                return Optional.empty();
            }
        }
    }
}