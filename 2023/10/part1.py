from collections import deque
# index (i, j) of S in grid
def find_start_position(grid):
    for i, row in enumerate(grid):
        try:
            j = row.index('S')
            return (i, j)
        except ValueError:
            continue
    return None 

# checks whether indexes are within the grid.
def is_within_grid(grid, position):
    i, j = position
    return 0 <= i < len(grid) and 0 <= j < len(grid[0])

def get_neighbors(grid, node):
    i, j = node
    symbol = grid[i][j]

    neighbors = {
        '|': [(i-1, j), (i+1, j)],                      # North, South
        '-': [(i, j-1), (i, j+1)],                      # West, East
        'L': [(i-1, j), (i, j+1)],                      # North, East
        'J': [(i-1, j), (i, j-1)],                      # North, West
        '7': [(i, j-1), (i+1, j)],                      # West, South
        'F': [(i, j+1), (i+1, j)],                      # East, South
        '.': []
    }

    return [n for n in neighbors[symbol] if is_within_grid(grid, n)]

# find length of the cycle my man
def get_cycle_length(grid, start):
    visited = set()
    stack = deque([(start, 0)])

    while stack:
        node, length = stack.pop()

        if node in visited:
            if node == start and length > 1:
                return length
            continue

        visited.add(node)
        for neighbor in get_neighbors(grid, node):
            if neighbor not in visited or (neighbor == start and length > 1):
                stack.append((neighbor, length + 1))

    return 0
            
def main():
    with open('input.txt') as file:
        grid = file.read().splitlines()

        start = find_start_position(grid)
        grid[start[0]] = grid[start[0]].replace('S', 'F')

        length = get_cycle_length(grid, start)

        print(length // 2)



if __name__ == "__main__": 
    main()


    
