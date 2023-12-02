
import fileinput
import re

NUM_RED = 12
NUM_GREEN = 13
NUM_BLUE = 14

num_regex = r'[0-9]+'
color_regex = r'(red|green|blue)'
split_regex = r'[;,]'

sum = 0
for line in fileinput.input(files ='input.txt'): 
    counts = re.split(split_regex, line[8:])

    valid = True

    max_red = 0
    max_green = 0
    max_blue = 0

    for count in counts:

        num = int(re.search(num_regex, count).group())
        color = re.search(color_regex, count).group()

        if color == "red":
            max_red = max(max_red, num)
        elif color == "green":
            max_green = max(max_green, num)
        else:
            max_blue = max(max_blue, num)

    sum += max_red * max_green * max_blue

print(sum)
