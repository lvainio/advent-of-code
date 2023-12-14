from collections import defaultdict
from itertools import product


def north(grid):
    num_rows = len(grid)
    num_cols = len(grid[0])
    for row, col in product(range(num_rows), range(num_cols)):
        if grid[row][col] == 'O': 
            new_row = row
            while new_row > 0 and grid[new_row-1][col] == '.':
                new_row -= 1
            grid[row][col] = '.'
            grid[new_row][col] = 'O'


def west(grid):
    num_rows = len(grid)
    num_cols = len(grid[0])
    for row, col in product(range(num_rows), range(num_cols)):
        if grid[row][col] == 'O':
            new_col = col
            while new_col > 0 and grid[row][new_col-1] == '.':
                new_col -= 1
            grid[row][col] = '.'
            grid[row][new_col] = 'O'


def south(grid):
    num_rows = len(grid)
    num_cols = len(grid[0])
    for row in range(num_rows - 1, -1, -1):
        for col in range(num_cols):
            if grid[row][col] == 'O':
                new_row = row
                while new_row < num_rows - 1 and grid[new_row + 1][col] == '.':
                    new_row += 1
                grid[row][col] = '.'
                grid[new_row][col] = 'O'


def east(grid):
    num_rows = len(grid)
    num_cols = len(grid[0])
    for row in range(num_rows):
        for col in range(num_cols - 1, -1, -1):
            if grid[row][col] == 'O':
                new_col = col
                while new_col < num_cols - 1 and grid[row][new_col + 1] == '.':
                    new_col += 1
                grid[row][col] = '.'
                grid[row][new_col] = 'O'


def cycle(grid):
    north(grid)
    west(grid)
    south(grid)
    east(grid)
                

def score(grid):
    num_rows = len(grid)
    num_cols = len(grid[0])
    score = 0
    for row, col in product(range(num_rows), range(num_cols)):
        if grid[row][col] == 'O':
            score += num_rows - row
    return score


def print_grid(grid):
    grid_list_of_strings = [''.join(row) for row in grid]
    for row in grid_list_of_strings:
        print(row)


def main():
    with open('input.txt') as file:
        grid = [list(row) for row in file.read().splitlines()]

    part1 = 0
    part2 = 0

    grid_tuple = tuple([''.join(map(str, inner_list)) for inner_list in grid])
    seen = {grid_tuple}
    array = [grid_tuple]

    iter = 0
    while True:
        iter += 1
        cycle(grid)
        gg = tuple([''.join(map(str, inner_list)) for inner_list in grid])
        if gg in seen:
            break
        tup = tuple([''.join(map(str, inner_list)) for inner_list in grid])
        seen.add(tup)
        array.append(tup)

    tupp = tuple([''.join(map(str, inner_list)) for inner_list in grid])
    first = array.index(tupp)
    
    wanted_grid = array[(1000000000 - first) % (iter - first) + first]

    print((1000000000 - first) % (iter - first) + first)

    print(score(wanted_grid))
            
    
    print(f'part1: {part1}, part2: {part2}')

    
    
if __name__ == '__main__':
    main()