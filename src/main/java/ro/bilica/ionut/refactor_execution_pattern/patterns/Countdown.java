package ro.bilica.ionut.refactor_execution_pattern.patterns;

public class Countdown implements LoopPattern.StopCondition {

    private int triesLeft;

    public Countdown(int maxTries) {
        triesLeft = maxTries;
    }

    @Override
    public boolean shouldStop() {
        triesLeft--;
        return triesLeft == 0;
    }
}
