(ns leo.aoc.year2017.day1.solver)

(defn solve-part1 [input]
  (let [firstDigits (seq input)
        secondDigits (conj (vec (rest input)) (last input))]
    (->> (map vector firstDigits secondDigits)
         (filter #(= (first %) (second %)))
         (map #(Character/digit (first %) 10))
         (reduce +)
         (str))))

(defn solve-part2 [input]
  (let [firstDigits  (seq input)  
        size         (count firstDigits)
        halfSize     (/ size 2)
        secondDigits (map #(nth input (mod (+ % halfSize) size)) (range size))] 
    (->> (map vector firstDigits secondDigits)  
         (filter #(= (first %) (second %))) 
         (map #(Character/digit (first %) 10))  
         (reduce +)  
         (str)))) 