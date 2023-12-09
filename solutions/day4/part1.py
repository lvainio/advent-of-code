lines = [line.split(':')[1] for line in open("input.txt").read().splitlines()]

sum = 0
for line in lines:
    card_numbers, winning_numbers = [x.split() for x in line.split("|")]

    count = len([x for x in card_numbers if x in winning_numbers])

    if count > 0:
        sum += 2 ** (count - 1)

print(sum)


