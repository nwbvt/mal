(ns ml-clj.knn
  (:use (incanter core)
        (ml-clj utils)))

(defn- dist-func
  "Create a distance function for the distance to a point"
  [point]
  (fn [& other] (euclidean point other)))

(defn- compute-dists
  "compute the distances from a data point to a data set"
  [from-point data features]
  (let [dist-col ($map (dist-func from-point) features data)]
    (col-names (conj-cols data dist-col)
               (conj (col-names data) :dist))))

(defn classify
  "use the knn classification algorithm to classify the new datum"
  [to-classify data features label k]
  (->>
    (compute-dists to-classify data features)
    ($order :dist :asc)
    ($ (range k) [label :dist])
    ($rollup :count :votes :type)
    ($order :votes :desc)
    ($ 0 :type)))
