import re

with open("input.txt") as file:
    moves, nodes = file.read().split("\n\n")

    graph = {}
    for i, node in enumerate(nodes.splitlines()):
        name, left, right = re.findall("[A-Z]{3}", node)
        graph[name] = {'L': left, 'R': right}
        
    current = 'AAA'
    steps = 0
    while current != 'ZZZ':
        move = moves[steps % len(moves)]

        current = graph[current][move]   
        steps += 1

    print(steps)
