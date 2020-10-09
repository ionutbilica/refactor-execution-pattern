package ro.bilica.ionut.refactor_execution_pattern.patterns;

import java.time.Duration;
import java.time.LocalDateTime;

public class Timeout implements LoopPattern.StopCondition {

    private LocalDateTime expiresAt;

    public Timeout(Duration duration) {
        expiresAt = LocalDateTime.now().plus(duration);
    }

    @Override
    public boolean shouldStop() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
