from collections import defaultdict
import re


def calculate_hash(str):
    hash_value = 0
    for c in str:
        hash_value = (hash_value + ord(c)) * 17 % 256
    return hash_value


def main():
    with open('input.txt') as file:
        tokens = file.read().split(',')

    boxes = defaultdict(list) 
    regex = re.compile(r'([a-z]+)([=\-])([0-9]*)')

    for token in tokens:
        match = re.search(regex, token)
        lens_id, operator, number = match.groups()
        hash_value = calculate_hash(lens_id)

        if operator == '-':
            boxes[hash_value] = [lens for lens in boxes[hash_value] if lens[0] != lens_id]

        elif operator == '=':
            for pair in boxes[hash_value]:
                if pair[0] == lens_id:
                    pair[1] = number
                    break
            else:
                boxes[hash_value].append([lens_id, number])

    part1 = 0
    part2 = 0

    for token in tokens:
        part1 += calculate_hash(token)

    for box_index, box in boxes.items():
        for slot, lens in enumerate(box):
            part2 += (box_index + 1) * (slot + 1) * int(lens[1])
    
    print(f'part1: {part1}, part2: {part2}')


if __name__ == '__main__':
    main()