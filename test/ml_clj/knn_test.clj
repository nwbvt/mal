(ns ml-clj.knn-test
  (:use clojure.test
        incanter.core
        incanter.datasets
        ml-clj.knn))

(deftest test-classify
  (testing "That it correctly classifies the simple data set"
           (with-data (dataset [:one :two :type]
                            [[1 1.1 :A]
                             [1 1 :A]
                             [0 0 :B]
                             [0 0.1 :B]])
             (is (= :A 
                    (classify [1.1 1])
                    (classify [20 15])))
             (is (= :B
                    (classify [0 0])
                    (classify [-5 1])))))
  (testing "That it correctly classifies a more complex data set"
           (with-data (get-dataset :iris)
             (is (= "setosa"
                 (classify [4.4 3.1 1.8 0.3] :label :Species)
                 (classify [5 4 2 0.1] :label :Species)))
             (is (= "versicolor"
                 (classify [6 3.1 5 1] :label :Species)
                 (classify [5.7 2.4 4.1 2] :label :Species)))
             (is (= "virginica"
                 (classify [7 3 5 2] :label :Species)
                 (classify [6 4 7 2.6] :label :Species))))))
