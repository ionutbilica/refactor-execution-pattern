(ns ro.bilica.ionut.refactor-execution-pattern.patterns-test
  (:require [clojure.test :refer :all])
  (:require [ro.bilica.ionut.refactor-execution-pattern.patterns :refer :all])
  (:import (java.time Duration)))

(defn create-dummy-attempt [succeed-at]
  (let [current-try (atom 0)]
    (fn [str]
      (swap! current-try inc)
      (println @current-try)
      (when (= succeed-at @current-try)
        str
      ))))

(defn get-result [succeed-at timeout]
  (let [dummy-attempt (create-dummy-attempt succeed-at)]
    (tryUntil #(dummy-attempt "Bingo!")
              (create-timeout (Duration/ofSeconds timeout))
              (Duration/ofSeconds 1))))

(deftest tryUntil-test
  (is (= "Bingo!" (get-result 1 3)))
  (is (= "Bingo!" (get-result 3 4)))
  (is (nil? (get-result 3 2)))

  )

(run-tests)