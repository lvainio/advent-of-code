from collections import deque
from itertools import product


def find_start(grid):
    for row, col in product(range(len(grid)), range(len(grid[0]))):
        if grid[row][col] == 'S':
            return (row, col)
        

def is_valid(grid, row, col):
    return 0 <= row < len(grid) and 0 <= col < len(grid[0]) and grid[row][col] != '#'


def part1(grid, num_steps, start_row, start_col):
    seen = set()
    queue = deque([(start_row, start_col, 0)])
    while queue:
        if queue[0][2] == num_steps:
            return len(queue)
        row, col, level = queue.popleft()      
        for dr, dc in [(1, 0), (-1, 0), (0, 1), (0, -1)]: 
            nr = row + dr
            nc = col + dc
            if is_valid(grid, nr, nc) and not (nr, nc, level+1) in seen:
                queue.append((nr, nc, level+1))
                seen.add((nr, nc, level+1))


def part2(grid, num_steps):
    size = len(grid)
    start_row, start_col = find_start(grid)
    
    num_grids_to_end = num_steps // size - 1

    odd_count = part1(grid, size, start_row, start_col)
    even_count = part1(grid, size + 1, start_row, start_col)

    num_odd_grids = (num_grids_to_end // 2 * 2 + 1) ** 2
    num_even_grids = ((num_grids_to_end + 1) // 2 * 2) ** 2

    num_reached = (num_odd_grids * odd_count) + (num_even_grids * even_count)

    num_reached += (num_grids_to_end + 2) * part1(grid, size//2, start_row, start_col) + (3 * num_grids_to_end + 2) * odd_count

    num_reached +=  (num_grids_to_end + 1) * (even_count - part1(grid, size//2-1, start_row, start_col))

    return num_reached


def main():
    with open('input.txt') as file:
        grid = file.read().splitlines()

    start_row, start_col = find_start(grid)

    p1 = part1(grid, 64, start_row, start_col)
    p2 = part2(grid, 26501365)

    print(f'part1: {p1}, part2: {p2}')

if __name__ == '__main__':
    main()
 