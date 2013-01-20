(ns mal.utils
  (:use incanter.core))

(let [distance
      (memoize (fn
                 [a b]
                 (double ($= ((a - b) ** 2)))))]

  (defn euclidean
    "The distance between two points"
    [a b]
    (sqrt (loop [sum 0, as a, bs b]
            (if (or (empty? as) (empty? bs)) sum
              (recur (+ sum (distance (first as) (first bs)))
                     (rest as) (rest bs)))))))

(let [normalize-field
      (fn [field]
          (let [col ($ field)
                max-val (apply max col)
                min-val (apply min col)
                norm (fn [v]
                       (if (= max-val min-val) 0
                       ($= (v - min-val) / (max-val - min-val))))]
            ($map norm field)))]

  (defn normalize
    "normalize a set of fields in a data set, each to be labeled field'"
    [data & fields]
    (with-data data
      (col-names
        (apply conj-cols data (map normalize-field fields))
        (concat (col-names data) (map #(keyword (str (name %) "'")) fields))))))
