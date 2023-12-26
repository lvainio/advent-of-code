lines = [line.split(':')[1] for line in open("input.txt").read().splitlines()]

counts = {i: 0 for i in range(len(lines))}

num_copies = 0
for i, line in enumerate(lines):
    card_numbers, winning_numbers = [x.split() for x in line.split("|")]

    num_winning = len([x for x in card_numbers if x in winning_numbers])

    for j in range(num_winning):
        counts[i+j+1] += counts[i] + 1
        num_copies += counts[i] + 1

print(num_copies + len(lines))