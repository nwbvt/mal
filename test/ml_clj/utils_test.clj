(ns ml-clj.utils-test
  (:use clojure.test
        ml-clj.utils))

(deftest test-euclidean
  (testing "That it returns the distance between two points"
           (is (= 5.0
                  (euclidean [0 0] [0 5])
                  (euclidean [5 0] [0 0])
                  (euclidean [0 0] [3 4])
                  (euclidean [1 1] [4 5])))))

