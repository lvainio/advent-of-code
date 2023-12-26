from itertools import product, combinations

def distance(n, g1, g2, empty_rows, empty_cols):
    distance = abs(g2[0] - g1[0]) + abs(g2[1] - g1[1])

    for row_index in range(min(g1[0], g2[0])+1, max(g1[0], g2[0])):
        if row_index in empty_rows:
            distance += n-1

    for col_index in range(min(g1[1], g2[1])+1, max(g1[1], g2[1])):
        if col_index in empty_cols:
            distance += n-1

    return distance

def main():
    with open("input.txt") as file:
        universe = file.read().splitlines()

    galaxies = []
    for row, col in product(range(len(universe)), range(len(universe[0]))):
        if universe[row][col] == '#':
            galaxies.append((row, col))

    empty_rows = [i for i, row in enumerate(universe) if '#' not in row]
    empty_cols = [j for j, col in enumerate(zip(*universe)) if '#' not in col]
    
    part1, part2 = 0, 0
    for g1, g2 in combinations(galaxies, 2):
        part1 += distance(2, g1, g2, empty_rows, empty_cols)
        part2 += distance(1_000_000, g1, g2, empty_rows, empty_cols)
    print(f'part1: {part1}, part2: {part2}')
        

        
if __name__ == '__main__':
    main()