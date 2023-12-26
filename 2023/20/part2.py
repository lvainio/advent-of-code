from collections import deque
from math import gcd


class Node:
    def __init__(self, id):
        self.id = id
    
    def receive_pulse(self, pulse, from_node):
        raise NotImplementedError("Subclass needs to define receive_pulse method.")


class BroadcastNode(Node):
    def __init__(self, id):
        super().__init__(id)

    def receive_pulse(self, pulse, from_node):
        if pulse in ['high', 'low']:
            return pulse
        else:
            raise ValueError(f"Invalid pulse {pulse}.")
        

class FlipFlopNode(Node):
    def __init__(self, id):
        super().__init__(id)
        self.state = 'off'

    def receive_pulse(self, pulse, from_node): 
        if pulse == 'high':
            return None
        elif pulse == 'low':
            if self.state == 'off':
                self.state = 'on'
                return 'high'
            else:
                self.state = 'off'
                return 'low'
        else:
            raise ValueError(f"Invalid pulse {pulse}.")

        
class ConjunctionNode(Node):
    def __init__(self, id):
        super().__init__(id)
        self.inputs = {} 

    def receive_pulse(self, pulse, from_node):
        if not from_node in self.inputs:
            raise KeyError(f"Node '{from_node}' not in inputs for conjunction node {self.id}.")
        self.inputs[from_node] = pulse
        if all(p == 'high' for p in self.inputs.values()):
            return 'low'
        else:
            return 'high'
        
    def add_input(self, from_node):
        self.inputs[from_node] = 'low'


class Graph:
    def __init__(self):
        self.adjacency_list = {}
        self.nodes = {}

    def add_node(self, line):
        tokens = line.split()

        id = None
        node_type = None
        neighbours = [item[:2] for item in tokens[2:]]

        if tokens[0] == 'broadcaster':
            id = 'bc'
            node_type = 'bc'
        else:
            id = tokens[0][1:3]
            node_type = tokens[0][0]
            
        node = None
        if node_type == 'bc':
            node = BroadcastNode(id)   
        elif node_type == '%':
            node = FlipFlopNode(id)
        elif node_type == '&':
            node = ConjunctionNode(id)
        else:
            raise ValueError(f"Invalid node type {node_type}.")
        
        self.adjacency_list[id] = neighbours
        self.nodes[id] = node

    def add_inputs_for_conjunctions(self):
        for id, node in self.nodes.items():
            if isinstance(node, ConjunctionNode):
                for from_node, neighbors in self.adjacency_list.items():
                    if id in neighbors:
                        node.add_input(from_node)
            
    def press_button(self, cycles, visited, button_presses):
        queue = deque([('low', 'btn', 'bc')])
     
        while queue:
            in_pulse, from_id, to_id = queue.popleft()
            if to_id == 'rx':
                continue

            if to_id == 'cs' and in_pulse == 'high':
                visited.add(from_id)
                if from_id not in cycles:
                    cycles[from_id] = button_presses
                
            to_node = self.nodes[to_id]
            out_pulse = to_node.receive_pulse(in_pulse, from_id)
            if out_pulse is not None:
                for neighbor_id in self.adjacency_list[to_id]:
                    queue.append((out_pulse, to_id, neighbor_id))
                    

def main():
    graph = Graph()

    with open('input.txt') as file:
        for line in file.read().splitlines():
            graph.add_node(line)
        graph.add_inputs_for_conjunctions()
    
    cycles = {}
    visited = set()
    button_presses = 1
    while graph.press_button(cycles, visited, button_presses):
        if len(visited) == 4:
            x = 1
            for cycle in cycles.values():
                x = x * cycle // gcd(x, cycle)
            print(x)
            break
        button_presses += 1
        

if __name__ == '__main__':
    main()