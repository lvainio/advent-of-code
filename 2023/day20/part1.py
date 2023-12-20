from collections import deque


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
            
    def press_button(self):
        queue = deque([('low', 'btn', 'bc')])
     
        low = 0
        high = 0
        while queue:
            in_pulse, from_id, to_id = queue.popleft()

            if in_pulse == 'low':
                low += 1
            elif in_pulse == 'high':
                high += 1

            if to_id == 'rx':
                continue

            to_node = self.nodes[to_id]

            out_pulse = to_node.receive_pulse(in_pulse, from_id)

            if out_pulse is None:
                continue

            for neighbor_id in self.adjacency_list[to_id]:
                queue.append((out_pulse, to_id, neighbor_id))

        return low, high
    def __str__(self):
        graph_str = "Graph:\n"
        for node_id, neighbours in self.adjacency_list.items():
            node_str = f"{node_id} -> {', '.join(map(str, neighbours))}\n"
            graph_str += node_str
        return graph_str

def main():
    graph = Graph()

    with open('input.txt') as file:
        for line in file.read().splitlines():
            graph.add_node(line)
        graph.add_inputs_for_conjunctions()
   

    low = 0
    high = 0
    for _ in range(1000):
        num_low, num_high = graph.press_button()
        low += num_low
        high += num_high

    part1 = low * high

    print(f'part1: {part1}')

if __name__ == '__main__':
    main()