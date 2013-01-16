(ns ml-clj.knn-test
  (:use clojure.test
        incanter.core
        incanter.datasets
        ml-clj.knn))

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
                    (classify [-5 1] d features label k)))))
  (testing "That it correctly classifies a more complex data set"
           (let [d (get-dataset :iris)
                 features [:Sepal.Length :Sepal.Width :Petal.Length :Petal.Width]
                 label :Species
                 k 10]
             (is (= "setosa"
                 (classify [4.4 3.1 1.8 0.3] d features label k)
                 (classify [5 4 2 0.1] d features label k)))
             (is (= "versicolor"
                 (classify [6 3.1 5 1] d features label k)
                 (classify [5.7 2.4 4.1 2] d features label k)))
             (is (= "virginica"
                 (classify [7 3 5 2] d features label k)
                 (classify [6 4 7 2.6] d features label k))))))
