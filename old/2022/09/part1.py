
visited = set()
visited.add((0, 0))

Hpos = [0, 0]
Tpos = [0, 0]

def make_move(move):
    # move H
    if move == 'U':
        Hpos[1] += 1
    elif move == 'D':
        Hpos[1] -= 1
    elif move == 'L':
        Hpos[0] -= 1
    elif move == 'R':
        Hpos[0] += 1
    else:
        print("invalid move")

    # move T
    if abs(Hpos[0]-Tpos[0]) == 1 and abs(Hpos[1]-Tpos[1]) == 2:
        Tpos[0] = Hpos[0]
    elif abs(Hpos[1]-Tpos[1]) == 1 and abs(Hpos[0]-Tpos[0]) == 2:
        Tpos[1] = Hpos[1]

    if Hpos[0] - Tpos[0] == -2:
        Tpos[0] -= 1
    elif Hpos[0] - Tpos[0] == 2:
        Tpos[0] += 1
    
    if Hpos[1] - Tpos[1] == -2:
        Tpos[1] -= 1
    elif Hpos[1] - Tpos[1] == 2:
        Tpos[1] += 1


with open("input.txt") as file: 
    for line in file.read().splitlines():
        move, times = line.split()

        for i in range(int(times)):
            make_move(move)
            visited.add((Tpos[0], Tpos[1]))

print(len(visited))

