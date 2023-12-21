import Data.List (sort)
import qualified Data.Map as Map

stoi:: String -> Int
stoi s = read s

count :: [Int] -> Int
count adapters = let
    mm = foldl process_adapter Map.empty adapters
    in Map.findWithDefault 0 (last adapters) mm

process_adapter :: Map.Map Int Int -> Int -> Map.Map Int Int
process_adapter mm curr = let
    num_paths = sum $ map (\x -> Map.findWithDefault 0 x mm) [curr-3, curr-2, curr-1]
    new_count = if num_paths == 0 then 1 else num_paths 
    in Map.insert curr new_count mm

main = do
    contents <- readFile "input.txt"

    let numbers = sort (map stoi (lines contents))
    let adapters = [0] ++ numbers ++ [maximum numbers + 3]
    let diffs = zipWith (-) (tail adapters) adapters

    let part1 = length (filter (== 1) diffs) * length (filter (== 3) diffs)
    let part2 = count adapters

    print (part1)
    print (part2)