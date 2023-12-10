
import fileinput
import re

NUM_RED = 12
NUM_GREEN = 13
NUM_BLUE = 14

num_regex = r'[0-9]+'
color_regex = r'(red|green|blue)'
split_regex = r'[;,]'

sum = 0
game_id = 1
for line in fileinput.input(files ='input.txt'): 
    counts = re.split(split_regex, line[8:])

    valid = True
    for count in counts:

        num = re.search(num_regex, count).group()
        color = re.search(color_regex, count).group()

        if (color == "red" and int(num) > NUM_RED) or \
            (color == "green" and int(num) > NUM_GREEN) or \
            (color == "blue" and int(num) > NUM_BLUE):
                valid = False

        if not valid:
            break

    if valid:
        sum += game_id

    game_id += 1

print(sum)
