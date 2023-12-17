
LOWER_CASE_CONVERSION = 96
UPPER_CASE_CONVERSION = 38

with open("input.txt") as file:
    total = 0
    for rucksack in file.read().splitlines():
        first, second = rucksack[:len(rucksack)//2], rucksack[len(rucksack)//2:]

        [common_char] = set(first) & set(second)

        if common_char.islower():
            total += ord(common_char) - LOWER_CASE_CONVERSION
        else:
            total += ord(common_char) - UPPER_CASE_CONVERSION

    print(total)
        
