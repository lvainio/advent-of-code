import sys
sys.setrecursionlimit(3000)


def move(grid, dir, row, col, visited):
    if (row, col, dir) in visited:
        return
    elif not (0 <= row < len(grid)):
        return
    elif not (0 <= col < len(grid[0])):
        return

    visited.add((row, col, dir))
    dir_map = {'right': (0, 1), 'left': (0, -1), 'up': (-1, 0), 'down': (1, 0)}

    if grid[row][col] == '.':
        move(grid, dir, row + dir_map[dir][0], col + dir_map[dir][1], visited)

    elif grid[row][col] == '-' and dir in ['right', 'left']:
        move(grid, dir, row + dir_map[dir][0], col + dir_map[dir][1], visited)

    elif grid[row][col] == '|' and dir in ['up', 'down']:
        move(grid, dir, row + dir_map[dir][0], col + dir_map[dir][1], visited)

    elif grid[row][col] == '-' and dir in ['up', 'down']:
        move(grid, 'right', row, col+1, visited)
        move(grid, 'left', row, col-1, visited)

    elif grid[row][col] == '|' and dir in ['right', 'left']:
        move(grid, 'up', row-1, col, visited)
        move(grid, 'down', row+1, col, visited)

    elif grid[row][col] == '/' and dir == 'right':
        move(grid, 'up', row-1, col, visited)

    elif grid[row][col] == '/' and dir == 'left':
        move(grid, 'down', row+1, col, visited)

    elif grid[row][col] == '/' and dir == 'up':
        move(grid, 'right', row, col+1, visited)

    elif grid[row][col] == '/' and dir == 'down':
        move(grid, 'left', row, col-1, visited)

    elif grid[row][col] == '\\' and dir == 'right':
        move(grid, 'down', row+1, col, visited)

    elif grid[row][col] == '\\' and dir == 'left':
        move(grid, 'up', row-1, col, visited)

    elif grid[row][col] == '\\' and dir == 'up':
        move(grid, 'left', row, col-1, visited)

    elif grid[row][col] == '\\' and dir == 'down':
        move(grid, 'right', row, col+1, visited)


def count_energized_tiles(grid, start_row, start_col, dir):
    visited = set()
    move(grid, dir, start_row, start_col, visited)
    energized_tiles = {(x, y) for x, y, _ in visited}
    return len(energized_tiles)


def main():
    with open('input.txt') as file:
        grid = file.read().splitlines()

    part1 = count_energized_tiles(grid, 0, 0, 'right')
    part2 = 0

    for col in range(len(grid[0])):
        part2 = max(part2, count_energized_tiles(grid, 0, col, 'down'))
        part2 = max(part2, count_energized_tiles(grid, len(grid) - 1, col, 'up'))

    for row in range(len(grid)):
        part2 = max(part2, count_energized_tiles(grid, row, 0, 'right'))
        part2 = max(part2, count_energized_tiles(grid, row, len(grid[0]) - 1, 'left'))

    print(f'part1: {part1}, part2: {part2}')


if __name__ == '__main__':
    main()