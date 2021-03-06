(ns mal.knn
  (:use (incanter core)
        (mal utils)))

(defn- dist-func
  "Create a distance function for the distance to a point"
  [point]
  (fn [& other] (euclidean point other)))

(defn- compute-dists
  "compute the distances from a data point to a data set"
  [from-point features data]
  (let [dist-col ($map (dist-func from-point) features data)]
    (col-names (conj-cols data dist-col)
               (conj (col-names data) :dist))))

(defn- get-rows
  "get the rows
   same as $ when requesting multiple rows, but when only one is requested,
   it will still return a dataset"
 [rows cols data]
 (let [result ($ rows cols data)]
  (if (seq? result)
   (dataset cols [result])
   result)))

(defn classify
  "use the knn classification algorithm to classify the new datum"
  [to-classify & {:keys [label data features k]
                        :or {data $data k 3}}]
  ; if no feature set was provided, use everything other than the label
  (let [use-label (or label (last (col-names data)))
        use-features (or features (remove #{= label} (col-names data)))]
    (->>
      data
      (compute-dists to-classify use-features)
      ($order :dist :asc)
      (get-rows (range k) [use-label :dist])
      ($rollup :count :votes use-label)
      ($order :votes :desc)
      ($ 0 use-label))))
