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

(defn cal-log
  "Calculate a log for number and base"
  [n base]
  (/ (Math/log n) (Math/log base)))

(defn entropy
  "Calculate the Shannon Entropy in the data set"
  [data]
  (let [labels (set data)
        count-labels (fn [label] 
                       (count (filter #(= label %) data)))
        total (count data)]
    (reduce - 0 (map
                  #(let [prob (/ (count-labels %) total)]
                     (* prob (cal-log prob 2)))
                  labels))))
