from collections import defaultdict
import random


def parse_data():
    G = defaultdict(list)
    with open('input.txt') as file:
        for line in file.read().splitlines():
            node, neighbours = line.split(': ')
            for neighbour in neighbours.split(' '):
                G[node].append(neighbour)
                G[neighbour].append(node)
    return G


def karger(G):
    G_copy = {u: list(v) for u, v in G.items()}
    vertex_sets = {u: {u} for u in G}

    while len(G_copy) > 2:
        u = random.choice(list(G_copy.keys()))  
        v = random.choice(G_copy[u])            

        G_copy[u].extend(G_copy[v])
        for neighbor in G_copy[v]:
            G_copy[neighbor].remove(v)
            if neighbor != u:
                G_copy[neighbor].append(u)
        G_copy[u] = [x for x in G_copy[u] if x != u]
        
        vertex_sets[u].update(vertex_sets[v])

        del vertex_sets[v]
        del G_copy[v]

    min_cut_size = len(G_copy[list(G_copy.keys())[0]])
    
    return list(vertex_sets.values()), min_cut_size


def main():
    G = parse_data()

    min_cut = 0
    sets = None
    while min_cut != 3:
        sets, min_cut= karger(G)

    print(f"Sets: {len(sets[0])} {len(sets[1])} {len(sets[0])*len(sets[1])}")
    print(f"Minimum cut: {min_cut}")


if __name__ == '__main__':
    main()