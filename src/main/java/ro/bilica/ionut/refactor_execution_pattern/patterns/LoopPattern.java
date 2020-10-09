package ro.bilica.ionut.refactor_execution_pattern.patterns;

import org.w3c.dom.Document;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Supplier;

public class LoopPattern {

    public <T> Optional<T> tryUntilTimeout(Supplier<Optional<T>> attempt, Duration timeout, Duration sleepBetweenSteps) {
        return tryUntil(attempt, new Timeout(timeout), sleepBetweenSteps);
    }

    public <T> Optional<T> tryUntil(Supplier<Optional<T>> attempt, StopCondition stopCondition, Duration sleepBetweenSteps) {
        Optional<T> result;
        do {
            result = attempt.get();
            if (result.isEmpty()) {
                sleep(sleepBetweenSteps);
            }
        } while (result.isEmpty() && !stopCondition.shouldStop() && !Thread.currentThread().isInterrupted());
        return result;
    }

    private void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public interface StopCondition {
        boolean shouldStop();
    }
}
