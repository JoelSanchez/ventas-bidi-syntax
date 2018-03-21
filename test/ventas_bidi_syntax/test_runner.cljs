(ns ventas-bidi-syntax.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [ventas-bidi-syntax.core-test]))

(enable-console-print!)

(doo-tests 'ventas-bidi-syntax.core-test)
