
import re

ROWS = 140
COLS = 140

lines = []

num_regex = r'\d+'
num_dot_regex = r'^[\d.]+$'

def contains_special(string):
    return not bool(re.match(num_dot_regex, string))

def is_part_number(row, col, size):
    col_from = col-1
    if col_from < 0:
        col_from = 0
    col_to = col + size + 1
    if col_to > COLS-1:
        col_to = COLS-1

    # Check row above for special character
    if row > 0:
        sub_string = lines[row-1][col_from:col_to]
        if contains_special(sub_string):
            return True
        
    # Check row below for special character
    if row < ROWS-1:
        sub_string = lines[row+1][col_from:col_to]
        if contains_special(sub_string):
            return True
        
    # Check character to the left for special character
    if col > 0:
        character = lines[row][col-1]
        if contains_special(character):
            return True
        
    # Check character to the right for special character
    if col + size < COLS:
        character = lines[row][col+size]
        if contains_special(character):
            return True

    return False



for line in open("input.txt"):
    lines.append(line[:COLS])

sum = 0
for row in range(ROWS):
    matches = re.finditer(num_regex, lines[row])

    for match in matches:
        number = match.group()
        col = match.start()
        size = len(number)

        if is_part_number(row, col, size):
            sum += int(number)

print(sum)

