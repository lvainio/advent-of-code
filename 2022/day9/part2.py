
visited = set()
visited.add((0, 0))

positions = [[0, 0] for _ in range(10)]

def move_head(move):
    if move == 'U':
        positions[0][1] += 1
    elif move == 'D':
        positions[0][1] -= 1
    elif move == 'L':
        positions[0][0] -= 1
    elif move == 'R':
        positions[0][0] += 1
    else:
        print("invalid move")

def update_knots():
    for i in range(1, 10):
        x_diff = positions[i-1][0] - positions[i][0]
        y_diff = positions[i-1][1] - positions[i][1]
        if abs(x_diff) == abs(y_diff) == 2:
            if x_diff < 0:
                positions[i][0] -= 1
            else:
                positions[i][0] += 1
            if y_diff < 0:
                positions[i][1] -= 1
            else: 
                positions[i][1] += 1

        x_diff = positions[i-1][0] - positions[i][0]
        y_diff = positions[i-1][1] - positions[i][1]
        if abs(x_diff) == 1 and abs(y_diff) == 2:
            positions[i][0] = positions[i-1][0]
        elif abs(y_diff) == 1 and abs(x_diff) == 2:
            positions[i][1] = positions[i-1][1]

        x_diff = positions[i-1][0] - positions[i][0]
        y_diff = positions[i-1][1] - positions[i][1]
        if x_diff == -2:
            positions[i][0] -= 1
        elif x_diff == 2:
            positions[i][0] += 1
        
        if y_diff == -2:
            positions[i][1] -= 1
        elif y_diff == 2:
            positions[i][1] += 1

with open("input.txt") as file: 
    for line in file.read().splitlines():
        move, times = line.split()

        for i in range(int(times)):
            move_head(move)
            update_knots()
            visited.add((positions[9][0], positions[9][1]))

print(len(visited))

