# refactor-execution-pattern
This code was written during a refactoring session for learning purposes.

Refactoring a method to extract the often duplicated execution pattern of try-action-until-timeout is a trip with many interesting stops: abstracting the action, handling of InterruptedException, abstracting the stop condition (timeout, countdown, etc), and other clean code considerations.

When the trip is done, we'll go from gods on planet Java to banging rocks together on planet Clojure. Firmly holding on to our perfect Java code, in tiny steps we'll replicate it in this functional, dynamically typed, gorgeous language.
