def find_start(grid):
    for row_index, row in enumerate(grid):
        if 'S' in row:
            return (row_index, row.index('S'))

def is_in_grid(grid, node):
    return 0 <= node[0] < len(grid) and 0 <= node[1] < len(grid[0])

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

    return [n for n in neighbors[symbol] if is_in_grid(grid, n)]

def get_cycle(grid, start):
    visited = set()
    stack = [(start, set())]

    while stack:
        node, path = stack.pop()

        if node in visited:
            if node == start and len(path) > 1:
                return path
            continue

        visited.add(node)
        for neighbor in get_neighbors(grid, node):
            if neighbor not in visited or (neighbor == start and len(path) > 1):
                stack.append((neighbor, path | {node}))

def is_inside(grid, cycle, node): 
    if node in cycle:
        return False
    
    num_boundaries = 0
    first_corner = None
    for offset, symbol in enumerate(grid[node[0]][node[1]+1:]):
        if not (node[0], node[1]+offset+1) in cycle:
            continue

        if symbol in ['F', 'L']:
            first_corner = symbol
        elif symbol == '|' or (first_corner, symbol) in [('F', 'J'), ('L', '7')]:
            num_boundaries += 1

    return num_boundaries % 2 == 1
    


def main():
    with open('input.txt') as file:
        grid = file.read().splitlines()

    start = find_start(grid)
    grid[start[0]] = grid[start[0]].replace('S', 'L') # Change 'L' to whatever fits the map

    cycle = get_cycle(grid, start)

    num_inside = 0
    for i in range(len(grid)):
        for j in range(len(grid[0])):
            if is_inside(grid, cycle, (i, j)):
                num_inside += 1
    print(num_inside)



if __name__ == "__main__": 
    main()

    
