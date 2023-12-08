import math
import re

def lcm_of_list(numbers):
    lcm = numbers[0]
    for i in range(1, len(numbers)):
        lcm = (lcm * numbers[i]) // math.gcd(lcm, numbers[i])
    return lcm

def find_cycle_length(graph, start):
    steps = 0
    current = start
    while not current.endswith('Z'):
        move = moves[steps % len(moves)]
        current = graph[current][move]   
        steps += 1
    return steps

with open("input.txt") as file:
    moves, nodes = file.read().split("\n\n")

    graph = {}
    for i, node in enumerate(nodes.splitlines()):
        name, left, right = re.findall("[A-Z]{3}", node)
        graph[name] = {'L': left, 'R': right}
        
    current_nodes = []
    for node in graph.keys():
        if node.endswith('A'):
            current_nodes.append(node)

    lengths = [find_cycle_length(graph, node) for node in current_nodes]
    print(lcm_of_list(lengths))

 # ----- EXPLANATION ----- #
 # Every node that ends with 'A' results in a cycle when following the 'left' and
 # 'right' instructions for infinity. If the starting node is 'AAA' as an example
 # and it takes n steps to get to 'ZZZ' it will then take another n steps to get to
 # 'ZZZ' again and so on. So the cycle will hit an ending node every kn steps where 
 # k = 1, 2, 3, ... and n is the cycle length. At no other points does the cycle 
 # hit a node that ends with a 'Z' so by finding the cycle length for each of the 
 # starting nodes we can then get the answer by finding the LCM for these cycle lengths. 
