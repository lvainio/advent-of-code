(ns leo.aoc.year2017.day2.solver)

(defn parse-row [row]
  (->> (clojure.string/split row #"\s")
       (map #(Integer/parseInt %))))

(defn row-difference [row]
  (let [minValue (apply min row)
        maxValue (apply max row)]
    (- maxValue minValue)))

(defn solve-part1 [input]
  (->> (clojure.string/split input #"\n")
       (map parse-row)
       (map row-difference)
       (reduce +)
       (str)))

(defn compute-divisible-pair [row]
  (first (for [x row
               y row
               :when (and (not= x y) (= 0 (mod x y)))]
    (/ x y))))

(defn solve-part2 [input]
  (->> (clojure.string/split input #"\n")
       (map parse-row)
       (map compute-divisible-pair)
       (reduce +)
       (str)))


