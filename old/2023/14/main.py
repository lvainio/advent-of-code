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
                

def calculate_score(grid):
    num_rows = len(grid)
    num_cols = len(grid[0])
    score = 0
    for row, col in product(range(num_rows), range(num_cols)):
        if grid[row][col] == 'O':
            score += num_rows - row
    return score


def main():
    with open('input.txt') as file:
        grid = [list(row) for row in file.read().splitlines()]

    part1 = 0
    part2 = 0

    # north(grid)
    # part1 = calculate_score(grid)

    scores = defaultdict(list)

    cycle_start = None
    cycle_end = None
    for i in range(1, 1_000_000_000):
        cycle(grid)
        score = calculate_score(grid)
        scores[score].append(i)
        if len(scores[score]) == 2:
            cycle_start = scores[score][0]
            cycle_end = scores[score][1]
            break

    cycle_length = cycle_end - cycle_start
    cycle_idx = (1_000_000_000 - cycle_start) % cycle_length + cycle_start

    for score, cycle_indices in scores.items():
        if cycle_idx in cycle_indices:
            part2 = score
            break
                            
    print(f'part1: {part1}, part2: {part2}')

    
if __name__ == '__main__':
    main()