package ro.bilica.ionut.refactor_execution_pattern.original;

import org.w3c.dom.Document;
import ro.bilica.ionut.refactor_execution_pattern.patterns.LoopPattern;

import java.io.File;
import java.time.Duration;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FitnesseTestFixturesRef {

    private static final Duration TIMEOUT = Duration.ofSeconds(3);
    public static final Duration SLEEP_BETWEEN_TRIES = Duration.ofSeconds(1);

    public Optional<Document> findPerfectMatch(Document expected, File location, List<String> tagsToIgnore) {
        return new LoopPattern().tryUntilTimeout(() -> {
            List<File> possibleMatches = getAllPossibleMatches(location);
            List<Document> actuals = possibleMatches.stream()
                    .map(this::parse)
                    .collect(Collectors.toList());
            return actuals.stream()
                    .filter(actualDoc -> getMatch(expected, actualDoc, tagsToIgnore).isPerfect())
                    .findAny();
        }, TIMEOUT, SLEEP_BETWEEN_TRIES);

    }

    public Match findBestMatch(Document expected, File location, List<String> tagsToIgnore) {
        Match bestMatch;
        long startTime = System.currentTimeMillis();
        do {
            List<File> possibleMatches = getAllPossibleMatches(location);
            List<Document> actuals = possibleMatches.stream()
                    .map(this::parse)
                    .collect(Collectors.toList());
            bestMatch = actuals.stream()
                    .map(actualDoc -> getMatch(expected, actualDoc, tagsToIgnore))
                    .max(Comparator.comparingInt(Match::differencesCount))
                    .orElse(Match.NO_MATCH);
            if (!bestMatch.isPerfect()) {
                try {
                    Thread.sleep(SLEEP_BETWEEN_TRIES.toMillis());
                } catch (InterruptedException e) {
                    throw new RuntimeException("FIRE!!!");
                }
            }
        } while (!bestMatch.isPerfect() && System.currentTimeMillis() - startTime < TIMEOUT.toMillis());
        return bestMatch;
    }

    private Match getMatch(Document expectedDoc, Document actualDoc, List<String> tagsToIgnore) {
        return new Match(); //TODO implement;
    }

    private Document parse(File expected) {
        return null; //TODO implement;
    }

    private List<File> getAllPossibleMatches(File location) {
        return Collections.emptyList(); //TODO implement;
    }

    private static class Match {
        public static final Match NO_MATCH = new Match(); //TODO implement;

        public boolean isPerfect() {
            return false; //TODO implement;
        }

        public int differencesCount() {
            return 0;
        }
    }
}
