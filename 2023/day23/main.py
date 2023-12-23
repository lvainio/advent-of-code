from collections import deque

def find_start_and_end(grid):
    return (0, grid[0].index('.')), (len(grid)-1, grid[-1].index('.'))


def get_neighbours(grid, r, c, path):
    neighbours = []
    for dr, dc, slope in [(1, 0, 'v'), (-1, 0, '^'), (0, 1, '>'), (0, -1, '<')]: 
        nr = r + dr
        nc = c + dc

        if (nr, nc) not in path and \
            grid[r][c] == '.' and \
            not grid[nr][nc] == '#' and \
            0 <= nr < len(grid) and \
            0 <= nc < len(grid[0]):
            neighbours.append((nr, nc))

        if (nr, nc) not in path and \
            grid[r][c] == slope and \
            not grid[nr][nc] == '#' and \
            0 <= nr < len(grid) and \
            0 <= nc < len(grid[0]):
            neighbours.append((nr, nc))     

    return neighbours


def find_max_length(grid):
    (sr, sc), (er, ec) = find_start_and_end(grid)

    queue = deque()

    queue.append((sr, sc, set((sr, sc))))

    longest_path = 0

    while queue:
        r, c, path = queue.popleft()


        if (r, c) == (er, ec):
            
            
            longest_path = len(path)  - 2
            
            continue

        for nr, nc in get_neighbours(grid, r, c, path):

            
            new_path = path.copy()
            new_path.add((nr, nc))

            queue.append((nr, nc, new_path))

    return longest_path




def part2(grid):
    return 0


def main():
    with open('input.txt') as file:
        grid = file.read().splitlines()

    part1 = find_max_length(grid)
    
    for row in range(len(grid)):
        for char in ['v', '^', '<', '>']:
            grid[row] = grid[row].replace(char, '.')

    part2 = find_max_length(grid)

    print(f'part1: {part1}, part2: {part2}')


if __name__ == '__main__':
    main()