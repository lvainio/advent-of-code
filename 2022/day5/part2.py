
from collections import deque
import re

stacks = [deque() for _ in range(9)]
moves = []

with open("input.txt") as file:
    crates, procedures = file.read().split("\n\n")

    # Parse crates.
    for line in crates.splitlines()[:-1][::-1]:
        for i, crate in enumerate(re.findall(r"(\[[A-Z]\]|....)", line)):
            match = re.search(r"[A-Z]", crate) 

            if match:
                stacks[i].append(match.group())

    # Parse moves.
    for move in procedures.splitlines():
        quantity, from_stack, to_stack = [int(x) for x in re.findall("\d+", move)]

        moves.append((quantity, from_stack, to_stack))

# Perform the moves.
for move in moves:
    quantity, from_stack, to_stack = move

    moved_crates = []
    for i in range(quantity):
        moved_crates.append(stacks[from_stack-1].pop())

    for c in moved_crates[::-1]:
        stacks[to_stack-1].append(c)

# Check which crates are at the top.
top_crates = ""
for i in range(9):
    top_crates += stacks[i][-1]

print(top_crates)

