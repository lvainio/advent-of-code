from collections import defaultdict


def solve_part1(supported_by, supports, n):
    count = 0
    for id in range(n):
        will_cause_fall = False
        for s_id in supports[id]:
            if len(supported_by[s_id]) == 1:
                will_cause_fall = True
        if not will_cause_fall:
            count += 1
    return count


def solve_part2(supported_by, supports, n):
    total = 0
    for id in range(len(supports)):
        fallen = {id}
        remaining = {x for x in range(n)} - {id}
        prev_length = 0
        while prev_length != len(fallen):
            to_remove = set()
            for s_id in remaining:
                if len(supported_by[s_id]) > 0 and supported_by[s_id].issubset(fallen):
                    fallen.add(s_id)
                    to_remove.add(s_id)
            remaining -= to_remove
            prev_length = len(fallen)
        total += prev_length-1
    return total
    

def build_maps(coordinates):
    stacks = [[[] for _ in range(10)] for _ in range(10)]
    supported_by = defaultdict(set)
    supports = defaultdict(set)
    for id, ((x1, y1, z1), (x2, y2, z2)) in enumerate(coordinates): 
        height = 0
        for x in range(x1, x2+1):
            for y in range(y1, y2+1):
                if stacks[x][y]: 
                    height = max(height, stacks[x][y][-1][1])
                
        for z in range(z2-z1+1):
            for x in range(x1, x2+1):
                for y in range(y1, y2+1):
                    if stacks[x][y]:
                        prev_id, prev_height = stacks[x][y][-1]
                        if prev_id != id and height == prev_height:
                            supported_by[id].add(prev_id)
                            supports[prev_id].add(id)
                    stacks[x][y].append((id, height+z+1))

    return supported_by, supports


def main():
    with open('input.txt') as file:
        lines = file.read().splitlines()
        blocks = [line.split('~') for line in lines]
        coordinates = []
        for block in blocks:
            x1, y1, z1 = [int(p) for p in block[0].split(',')]
            x2, y2, z2 = [int(p) for p in block[1].split(',')]
            coordinates.append((( x1, y1, z1), (x2, y2, z2)))
        sorted_coordinates = sorted(coordinates, key=lambda x: x[0][2])

        supported_by, supports = build_maps(sorted_coordinates)

        part1 = solve_part1(supported_by, supports, len(coordinates))
        part2 = solve_part2(supported_by, supports, len(coordinates))

        print(f'part1: {part1}, part2: {part2}')


if __name__ == '__main__':
    main()