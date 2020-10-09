package ro.bilica.ionut.refactor_execution_pattern.original;

import org.w3c.dom.Document;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FitnesseTestFixtures {

    private static final long TIMEOUT_IN_MS = 3000;
    public static final long SLEEP_BETWEEN_TRIES_IN_MS = 1000;

    public Optional<Document> findPerfectMatch(Document expected, File location, List<String> tagsToIgnore) {
        Optional<Document> perfectMatch;
        long startTime = System.currentTimeMillis();
        do {
           List<File> possibleMatches = getAllPossibleMatches(location);
           List<Document> actuals = possibleMatches.stream()
                   .map(this::parse)
                   .collect(Collectors.toList());
           perfectMatch = actuals.stream()
                   .filter(actualDoc -> getMatch(expected, actualDoc, tagsToIgnore).isPerfect())
                   .findAny();
           if (perfectMatch.isEmpty()) {
               try {
                   Thread.sleep(SLEEP_BETWEEN_TRIES_IN_MS);
               } catch (InterruptedException e) {
                   throw new RuntimeException("FIRE!!!");
               }
           }
        } while (perfectMatch.isEmpty() && System.currentTimeMillis() - startTime < TIMEOUT_IN_MS);
        return perfectMatch;
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
                    Thread.sleep(SLEEP_BETWEEN_TRIES_IN_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException("FIRE!!!");
                }
            }
        } while (!bestMatch.isPerfect() && System.currentTimeMillis() - startTime < TIMEOUT_IN_MS);
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
