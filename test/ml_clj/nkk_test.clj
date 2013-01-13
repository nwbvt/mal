(ns ml-clj.nkk-test
  (:use clojure.test
        incanter.core
        ml-clj.nkk))

(deftest test-euclidean
  (testing "That it returns the distance between two points"
           (is (= 5.0
                  (euclidean [0 0] [0 5])
                  (euclidean [5 0] [0 0])
                  (euclidean [0 0] [3 4])
                  (euclidean [1 1] [4 5])))))

(deftest test-classify
  (testing "That it correctly classifies the simple data set"
           (let [d (dataset [:one :two :type] 
                            [[1 1.1 :A]
                             [1 1 :A]
                             [0 0 :B]
                             [0 0.1 :B]])
                 features [:one :two]
                 label :type
                 k 3]
             (is (= :A 
                    (classify [1.1 1] d features label k)
                    (classify [20 15] d features label k)))
             (is (= :B
                    (classify [0 0] d features label k)
                    (classify [-5 1] d features label k))))))
