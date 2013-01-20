(ns mal.utils
  (:use incanter.core))

(defn euclidean
  "The distance between two points"
  [a b]
  (sqrt (loop [sum 0, as a, bs b]
          (if (or (empty? as) (empty? bs)) sum
            (recur (double ($= (((first as) - (first bs)) ** 2) + sum))
                   (rest as) (rest bs))))))

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
