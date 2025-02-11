
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
        opponent_move, outcome = game.split()

        my_move = "ROCK"
        if (opponent_move == "A" and outcome == "Z") \
        or (opponent_move == "B" and outcome == "Y") \
        or (opponent_move == "C" and outcome == "X"):
            my_move = "PAPER"

        if (opponent_move == "A" and outcome == "X") \
        or (opponent_move == "B" and outcome == "Z") \
        or (opponent_move == "C" and outcome == "Y"):
            my_move = "SCISSOR"
        
        if my_move == "ROCK":
            total += ROCK
        elif my_move == "PAPER":
            total += PAPER
        else:
            total += SCISSOR

        if outcome == "Y":
            total += DRAW
        elif outcome == "Z":
            total += WIN

print(total)