(ns ml-clj.nkk
  (:use (incanter core)))

(defn euclidean
  "The distance between two points"
  [a b]
  (sqrt (loop [sum 0, as a, bs b]
          (if (or (empty? as) (empty? bs)) sum
            (recur ($= ((first as) - (first bs)) ** 2 + sum)
                   (rest as) (rest bs))))))

(let [dist-func (fn [point] (fn [& other] (euclidean point other))),
      compute-dists (fn [from-point data features]
                     (let [dist-col ($map (dist-func from-point) features data)]
                       (col-names (conj-cols data dist-col)
                                  (conj (col-names data) :dist))))]
  (defn classify
    "use the knn classification algorithm to classify the new datum"
    [to-classify data features label k]
    (with-data (compute-dists to-classify data features)
               ($ 0 :type 
                  ($order :votes :desc 
                     ($rollup :count :votes :type 
                        ($ (range k) [label :dist] ($order :dist :asc))))))))
