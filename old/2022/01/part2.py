with open("input.txt") as file:
    max_calories = [float("-inf"), float("-inf"), float("-inf")]
    for elf in file.read().split("\n\n"):
        calories = sum([int(x) for x in elf.split("\n")])

        min_val = min(max_calories)
        min_idx = max_calories.index(min_val)

        max_calories[min_idx] = max(min_val, calories)

    print(sum(max_calories))

