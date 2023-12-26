
LOSS = 0
DRAW = 3
WIN = 6

ROCK = 1
PAPER = 2
SCISSOR = 3

total = 0

with open("input.txt") as file:
    games = file.read().splitlines()

    for game in games:
        opponent_move, my_move = game.split()

        if my_move == "X":
            total += ROCK
        elif my_move == "Y":
            total += PAPER
        else:
            total += SCISSOR

        if (opponent_move == "A" and my_move == "X") \
        or (opponent_move == "B" and my_move == "Y") \
        or (opponent_move == "C" and my_move == "Z"):
            total += DRAW

        if (opponent_move == "A" and my_move == "Y") \
        or (opponent_move == "B" and my_move == "Z") \
        or (opponent_move == "C" and my_move == "X"):
            total += WIN

print(total)