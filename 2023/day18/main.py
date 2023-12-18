import re
from itertools import product

import sys
sys.setrecursionlimit(30000)


def flood_fill(grid, row, col):
    rows, cols = len(grid), len(grid[0])

    if not (0 <= row < rows) or not (0 <= col < cols) or grid[row][col] != '.':
        return

    grid[row][col] = 'O'

    flood_fill(grid, row - 1, col)  # Up
    flood_fill(grid, row + 1, col)  # Down
    flood_fill(grid, row, col - 1)  # Left
    flood_fill(grid, row, col + 1)  # Right


def count_outside_points(grid):
    num_rows = len(grid)
    num_cols = len(grid[0])

    for row in range(num_rows):
        if grid[row][0] == '.':
            flood_fill(grid, row, 0)

        if grid[row][num_cols-1] == '.':
            flood_fill(grid, row, num_cols-1)

    for col in range(num_cols):
        if grid[0][col] == '.':
            flood_fill(grid, 0, col)

        if grid[num_rows-1][col] == '.':
            flood_fill(grid, num_rows-1, col)

    count = 0
    for row in grid:
        count += row.count('O')
    return count



def print_grid(grid):
    for row in grid:
        print(' '.join(row))


def main():
    with open('input.txt') as file:
        lines = file.read().splitlines()

    regex = re.compile(r'^([UDLR]) ([0-9]+) \(#([0-9a-f]{6})\)$')
    visited = set()

    dir_map = {  
        'U': (-1, 0),     
        'D': (1, 0),    
        'L': (0, -1),   
        'R': (0, 1)    
    }

    x = 0
    y = 0

    visited.add((0, 0))

    for line in lines:
        match = re.search(regex, line)
        dir, num_times, color = match.groups()

        for _ in range(int(num_times)*2):
            x += dir_map[dir][0]
            y += dir_map[dir][1]
            visited.add((x, y))

    max_x = max(coord[0] for coord in visited)
    max_y = max(coord[1] for coord in visited)
    min_x = min(coord[0] for coord in visited)
    min_y = min(coord[1] for coord in visited)

    grid_rows = max_x - min_x + 1
    grid_cols = max_y - min_y + 1

    grid = [['.' for _ in range(grid_cols)] for _ in range(grid_rows)]
    for coord in visited:
        x, y = coord
        grid[x - min_x][y - min_y] = '#' 
             
    num_outside = count_outside_points(grid)
    
  

    num_rows = len(grid)
    num_cols = len(grid[0])

    total = num_rows*num_cols - num_outside

    print(total)
        


if __name__ == '__main__':
    main()