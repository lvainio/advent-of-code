from heapq import heappush, heappop


def dijkstra(grid, lower, upper):
    num_rows = len(grid)
    num_cols = len(grid[0])

    dir_map = {  
        "up": (-1, 0),     
        "down": (1, 0),    
        "left": (0, -1),   
        "right": (0, 1)    
    }

    visited = set()
    min_heap = [(0, 0, 0, None, 0)]

    while min_heap:
        loss, row, col, dir, count = heappop(min_heap)
        
        if (row, col) == (num_rows-1, num_cols-1):
            return loss

        if (row, col, dir, count) in visited:
            continue

        visited.add((row, col, dir, count))
        
        if count < upper and dir is not None:
            new_row = row + dir_map[dir][0]
            new_col = col + dir_map[dir][1]
            if 0 <= new_row < len(grid) and 0 <= new_col < len(grid[0]):
                heappush(min_heap, (loss + grid[new_row][new_col], new_row, new_col, dir, count + 1))

        if count >= lower or dir is None:
            for new_dir in ['up', 'down', 'left', 'right']:
                dr, dc = dir_map[new_dir]
                if dir is None or (new_dir != dir and dir_map[dir] != (-dr, -dc)):
                    new_row = row + dr
                    new_col = col + dc
                    if 0 <= new_row < len(grid) and 0 <= new_col < len(grid[0]):
                        heappush(min_heap, (loss + grid[new_row][new_col], new_row, new_col, new_dir, 1))


def main():
    with open('input.txt', 'r') as file:
        grid = [list(map(int, line)) for line in file.read().splitlines()]

    part1 = dijkstra(grid, 0, 3)
    part2 = dijkstra(grid, 4, 10)

    print(f'part1: {part1}, part2: {part2}')

if __name__ == '__main__':
    main()