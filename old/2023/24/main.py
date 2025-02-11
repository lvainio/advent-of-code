from itertools import combinations


def parse_data():
    P = []
    V = []
    with open('input.txt') as file:
        lines = file.read().splitlines()
        for line in lines:
            position, velocity = line.split(' @ ')
            P.append(tuple(int(p) for p in position.split(', ')))
            V.append(tuple(int(v) for v in velocity.split(', ')))
    return P, V


def is_moving_towards(x, y, dx, dy, x_intersect, y_intersect):
    intersection_dir_x = x_intersect - x
    intersection_dir_y = y_intersect - y
    return dx * intersection_dir_x + dy * intersection_dir_y > 0


def part1(P, V):
    count = 0
    for i, j in combinations(range(len(P)), 2):
        x1, y1, _ = P[i]
        dx1, dy1, _ = V[i]
        k1 = dy1 / dx1
        m1 = y1 - k1 * x1

        x2, y2, _ = P[j]
        dx2, dy2, _ = V[j]
        k2 = dy2 / dx2
        m2 = y2 - k2 * x2

        if k1 != k2: 
            x_intersect = (m2 - m1) / (k1 - k2)
            y_intersect = k1 * x_intersect + m1
            
            if 200000000000000 <= x_intersect <= 400000000000000 and 200000000000000 <= y_intersect <= 400000000000000:
                is_moving_toward_1 = is_moving_towards(x1, y1, dx1, dy1, x_intersect, y_intersect)
                is_moving_toward_2 = is_moving_towards(x2, y2, dx2, dy2, x_intersect, y_intersect)

                if is_moving_toward_1 and is_moving_toward_2:
                    count += 1
    return count


def part2(P, V):
    print(P[0], V[0])
    print(P[1], V[1])
    print(P[2], V[2])

    # 9 unknowns: x, y, z, dx, dy, dz, t1, t2, t3
    # 9 equations from three arbitrary points:
    #   - x + t1 * dx = x1 + t1 * dx1
    #   - x + t2 * dx = x2 + t2 * dx2
    #   - x + t3 * dx = x3 + t3 * dx3

    #   - y + t1 * dy = y1 + t1 * dy1
    #   - y + t2 * dy = y2 + t2 * dy2
    #   - y + t3 * dy = y3 + t3 * dy3

    #   - z + t1 * dz = z1 + t1 * dz1
    #   - z + t2 * dz = z2 + t2 * dz2
    #   - z + t3 * dz = z3 + t3 * dz3



def main():
    P, V = parse_data()

    print(f'part1: {part1(P, V)}, part2: {part2(P, V)}')


if __name__ == '__main__':
    main()