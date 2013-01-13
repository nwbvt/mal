(ns ml-clj.utils
  (:use incanter.core))

(defn euclidean
  "The distance between two points"
  [a b]
  (sqrt (loop [sum 0, as a, bs b]
          (if (or (empty? as) (empty? bs)) sum
            (recur ($= ((first as) - (first bs)) ** 2 + sum)
                   (rest as) (rest bs))))))
