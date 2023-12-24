from collections import deque, defaultdict
from itertools import product


def find_start_and_end(grid):
    return (0, grid[0].index('.')), (len(grid)-1, grid[-1].index('.'))


def is_conjunction(grid, r, c):
    if grid[r][c] == '#':
        return False
    count = 0
    for dr, dc in [(1, 0), (-1, 0), (0, 1), (0, -1)]: 
        nr = r + dr
        nc = c + dc
        if 0 <= nr < len(grid) and 0 <= nc < len(grid[0]) and grid[nr][nc] != '#':
            count += 1
    return count > 2


def find_conjunctions(grid):
    conjunctions = set()
    for r, c in product(range(len(grid)), range(len(grid[0]))):
        if is_conjunction(grid, r, c):
            conjunctions.add((r, c))
    return conjunctions


def get_neighbours(grid, r, c): 
    neighbours = []
    for dr, dc, slope in [(1, 0, 'v'), (-1, 0, '^'), (0, 1, '>'), (0, -1, '<')]: 
        nr = r + dr
        nc = c + dc

        if  0 <= nr < len(grid) and \
            0 <= nc < len(grid[0]) and \
            grid[r][c] == '.' and \
            not grid[nr][nc] == '#':
            neighbours.append((nr, nc))

        if  0 <= nr < len(grid) and \
            0 <= nc < len(grid[0]) and \
            grid[r][c] == slope and \
            not grid[nr][nc] == '#':
            neighbours.append((nr, nc))     

    return neighbours


def build_graph(grid):
    (sr, sc), (er, ec) = find_start_and_end(grid)
    nodes = find_conjunctions(grid) | {(sr, sc), (er, ec)}
    graph = defaultdict(list)
    for node in nodes:
        stack = deque()
        visited = set()
        stack.append((node[0], node[1], 0))
        while stack:
            r, c, length = stack.pop()
            visited.add((r, c))
            for nr, nc in get_neighbours(grid, r, c):
                if (nr, nc) != node and (nr, nc) in nodes:
                    graph[node].append((nr, nc, length + 1))
                    break
                if not (nr, nc) in visited:
                    stack.append((nr, nc, length+1))
    return graph

visited = set()
def find_longest_path(node, end_node, length, graph):
    if node == end_node:
        return length
    if node in visited:
        return 0
    
    visited.add(node)
    longest_path = 0
    for nr, nc, distance in graph[node]:
        longest_path = max(find_longest_path((nr, nc), end_node, length+distance, graph), longest_path)
    visited.remove(node)
    return longest_path


def main():
    with open('input.txt') as file:
        grid = file.read().splitlines()

    start_node, end_node = find_start_and_end(grid)

    graph1 = build_graph(grid)
    part1 = find_longest_path(start_node, end_node, 0, graph1)

    for i in range(len(grid)):
        for char in ['v', '^', '>', '<']:
            grid[i] = grid[i].replace(char, '.')
    graph2 = build_graph(grid)
    part2 = find_longest_path(start_node, end_node, 0, graph2)

    print(f'part1: {part1}, part2: {part2}')


if __name__ == '__main__':
    main()