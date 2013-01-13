(ns ml-clj.nkk
  (:use (incanter core)
        (ml-clj utils)))

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
