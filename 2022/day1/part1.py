
with open("input.txt") as file:
    max_calories = float("-inf")
    for elf in file.read().split("\n\n"):
        calories = sum([int(x) for x in elf.split("\n")])

        max_calories = max(calories, max_calories)

    print(max_calories)