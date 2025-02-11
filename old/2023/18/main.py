import re
from itertools import product


def flood_fill(grid, row, col):
    if grid[row][col] != '.':
        return

    num_rows = len(grid)
    num_cols = len(grid[0])

    stack = [(row, col)]
    while stack:
        row, col = stack.pop()

        if not (0 <= row < num_rows) or not (0 <= col < num_cols) or grid[row][col] != '.':
            continue

        grid[row][col] = 'O'

        stack.append((row - 1, col))
        stack.append((row + 1, col))
        stack.append((row, col - 1))
        stack.append((row, col + 1))


def count_inside_points(grid):
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
    return num_rows*num_cols - count


def part1(lines):
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

        for _ in range(int(num_times)):
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

    return count_inside_points(grid)


def shoelace(points):
    total = 0
    for p1, p2 in zip(points[:-1], points[1:]):
        total += (p1[0] * p2[1] - p2[0] * p1[1])
    return int(abs(total / 2))

   
def part2(lines):
    regex = re.compile(r'^([UDLR]) ([0-9]+) \(#([0-9a-f]{6})\)$')

    dir_map = {  
        3: (-1, 0),     
        1: (1, 0),    
        2: (0, -1),   
        0: (0, 1)    
    }

    points = []
    x = 0
    y = 0
    points.append((x,y))
    edge_distance = 0
    for line in lines:
        match = re.search(regex, line)
        _, _, color = match.groups()

        num = int(color[:5], 16)
        dir = int(color[5])

        x += dir_map[dir][0] * num
        y += dir_map[dir][1] * num

        edge_distance += num

        points.append((x, y))

    return shoelace(points) + edge_distance//2 + 1


def main():
    with open('input.txt') as file:
        lines = file.read().splitlines()

    print(f'part1: {part1(lines)}, part2: {part2(lines)}')
        


if __name__ == '__main__':
    main()