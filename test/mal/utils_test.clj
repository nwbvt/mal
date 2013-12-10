(ns mal.utils-test
  (:use clojure.test
        incanter.core
        mal.utils))

(deftest test-euclidean
  (testing "That it returns the distance between two points"
           (is (= 5.0
                  (euclidean [0 0] [0 5])
                  (euclidean [5 0] [0 0])
                  (euclidean [0 0] [3 4])
                  (euclidean [1 1] [4 5])))))

(defn v= [& vectors]
  (loop [vs vectors]
    (if (empty? (first vs)) true
      (if (apply == (map first vs))
        (recur (map rest vs))
        false))))

(deftest test-normalize
  (testing "That it normalizes a set of data"
           (let [d (dataset [:big :little]
                            [[42 0.042]
                             [64 0.064]
                             [0 0.000]
                             [100 0.100]])
                 d' (normalize d :big :little)]
             (is (v= [0.42 0.64 0.0 1.0]
                     ($ :big' d')
                     ($ :little' d')))))
  (testing "That it handles data that does not vary"
           (let [d (dataset [:same :different]
                            [[5 1]
                             [5 1.5]
                             [5 2]
                             [5 1.25]])
                 d' (normalize d :same :different)]
             (is (v= [0 0.5 1 0.25] ($ :different' d')))
             (is (v= [0 0 0 0] ($ :same' d'))))))

(defn round
  "rounds f to the nth decimal place"
  [f n]
  (let [tens (Math/pow 10 n)]
    (double (/ (int (* f tens)) tens))))

(deftest test-entropy
  (testing "That it computes the entropy of a set of data (copied from my book (because I'm lazy))"
           (let [d [:yes :yes :no :no :no]]
             (is (= 0.97095 (round (BigDecimal. (float (entropy d))) 5))))))
