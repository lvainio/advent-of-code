
LOWER_CASE_CONVERSION = 96
UPPER_CASE_CONVERSION = 38

with open("input.txt") as file:
    total = 0

    elf_groups = file.read().splitlines()

    for i in range(0, len(elf_groups), 3):
        first = elf_groups[i]
        second = elf_groups[i+1]
        third = elf_groups[i+2]

        [common_char] = set(first) & set(second) & set(third)
        if common_char.islower():
            total += ord(common_char) - LOWER_CASE_CONVERSION
        else:
            total += ord(common_char) - UPPER_CASE_CONVERSION

    print(total)