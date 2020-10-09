(ns ro.bilica.ionut.refactor-execution-pattern.patterns
  (:import (java.time LocalDateTime Duration)))

(defn create-timeout [duration]
  (let [expires-at (.plus (LocalDateTime/now) duration)]
    (fn []
      (.isAfter (LocalDateTime/now) expires-at))))

(defn sleep [duration]
  (try
    (Thread/sleep (.toMillis duration))
    (catch Exception e
      (.interrupt (Thread/currentThread)))))

(defn tryUntil [attempt should-stop? sleep-between-steps]
  (loop [result (attempt)]
    (if (some? result)
      result
      (do
        (sleep sleep-between-steps)
        (if (and (not (should-stop?)) (not (.isInterrupted (Thread/currentThread))))
          (recur (attempt))
          result
          )))))


;(my-function p1 p2)
;(println "hello" "world")
;(println "hello" "world" (+ 2 2))
;
;(let [x 3]
;  (println x)
;  )
;
;(defn my-func [x y]
;  (println x y)
;  (* x y)
;  )
;
;(println (my-func 3 5))
;
;(println (.plusDays (LocalDateTime/now) 4))