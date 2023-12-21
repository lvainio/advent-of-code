from collections import deque
from itertools import product

def is_valid(grid, row, col):
    return 0 <= row < len(grid) and 0 <= col < len(grid[0]) and grid[row][col] != '#'


def part1(grid, num_steps):
    seen = set()
    queue = deque([(65, 65, 0)])
    while queue:
        r, c, level = queue.popleft()      
        if level == num_steps:
            return len(queue) + 1
        for dr, dc in [(1, 0), (-1, 0), (0, 1), (0, -1)]: 
            nr = r + dr
            nc = c + dc
            if is_valid(grid, nr, nc) and not (nr, nc, level+1) in seen:
                queue.append((nr, nc, level+1))
                seen.add((nr, nc, level+1))


def part2(grid, num_steps):
    size = len(grid)
    
    num_grids_to_end = num_steps // size - 1

    odd_count = part1(grid, size)
    even_count = part1(grid, size + 1)

    num_odd_grids = (num_grids_to_end // 2 * 2 + 1) ** 2
    num_even_grids = ((num_grids_to_end + 1) // 2 * 2) ** 2

    # Fully reached grids.
    num_reached = (num_odd_grids * odd_count) + (num_even_grids * even_count)

    # Corner and edge odd grids.
    num_reached += (num_grids_to_end + 2) * part1(grid, size//2) + (3 * num_grids_to_end + 2) * odd_count

    # Edge even grids.
    num_reached +=  (num_grids_to_end + 1) * (even_count - part1(grid, size//2-1))

    return num_reached


def main():
    with open('input.txt') as file:
        grid = file.read().splitlines()

    print(f'part1: {part1(grid, 64)}, part2: {part2(grid, 26501365)}')


if __name__ == '__main__':
    main()
 