import re

total = 0
with open("input.txt") as file:
    for pair in file.read().splitlines():
        [x1, y1, x2, y2] = [int(x) for x in re.findall(r"\d+", pair)]
        
        if not (y1 < x2 or y2 < x1):
            total += 1

print(total)