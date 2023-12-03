
import re

ROWS = 140
COLS = 140

lines = []

num_regex = r'\d+'
star_regex = r'[*]'

def get_adjacent_numbers(row, col):
    adjacent_nums = []

    indices = [col-1, col, col+1]

    # check above
    if row > 0:
        matches = re.finditer(num_regex, lines[row-1])

        for match in matches:
            number = match.group()
            index = match.start()
            size = len(number)

            for i in range(index, index+size):
                if i in indices:
                    adjacent_nums.append(int(number))
                    break

    # check below
    if row < ROWS-1:
        matches = re.finditer(num_regex, lines[row+1])

        for match in matches:
            number = match.group()
            index = match.start()
            size = len(number)

            for i in range(index, index+size):
                if i in indices:
                    adjacent_nums.append(int(number))
                    break

    # check to the left
    if col > 0 and lines[row][col-1].isdigit():
        adjacent_nums.append(int(re.search(num_regex, lines[row][:col][::-1]).group()[::-1]))

    # check to the right
    if col + 1 < COLS and lines[row][col+1].isdigit():
        adjacent_nums.append(int(re.search(num_regex, lines[row][col+1:]).group()))

    return adjacent_nums


for line in open("input.txt"):
    lines.append(line[:COLS])

sum = 0
for row in range(ROWS):
    matches = re.finditer(star_regex, lines[row])

    for match in matches:
        number = match.group()
        col = match.start()
        size = len(number)

        adjacent_numbers = get_adjacent_numbers(row, col)

        if len(adjacent_numbers) == 2:
            sum += adjacent_numbers[0] * adjacent_numbers[1]

print("sum: ", sum)

