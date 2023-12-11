import itertools

def expand(universe):
    expanded_rows = [line for line in universe for _ in (range(2) if '#' not in line else range(1))]
    expanded_cols = [""] * len(expanded_rows)

    num_rows = len(expanded_rows)
    num_cols = len(expanded_rows[0])
    for col in range(num_cols):
        contains_galaxy = any('#' in expanded_rows[row][col] for row in range(num_rows))
        for row in range(num_rows):
            if not contains_galaxy:
                expanded_cols[row] += expanded_rows[row][col] * 2
            else:
                expanded_cols[row] += expanded_rows[row][col]
            
    return expanded_cols

def manhattan_distance(g1, g2):
    return abs(g2[0] - g1[0]) + abs(g2[1] - g1[1])

def main():
    with open("input.txt") as file:
        universe = file.read().splitlines()

    expanded = expand(universe)
    num_rows = len(expanded)
    num_cols = len(expanded[0])

    galaxies = []
    for row, col in itertools.product(range(num_rows), range(num_cols)):
        if expanded[row][col] == '#':
            galaxies.append((row, col))

    sum = 0
    for g1, g2 in itertools.combinations(galaxies, 2):
        sum += manhattan_distance(g1, g2)
    print(sum)
        

 
    


        


                






if __name__ == '__main__':
    main()