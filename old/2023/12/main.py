from functools import lru_cache


@lru_cache(maxsize=None) 
def count(record, sizes):
    if sizes == () and not '#' in record:
        return 1
    elif sizes == () or record == '':
        return 0

    total = 0
    if record[0] in '.?': # Treat as '.'
        total += count(record[1:], sizes)

    if record[0] in "#?": # Treat as '#'
        fits_in_record = sizes[0] <= len(record)
        contains_no_dots = "." not in record[:sizes[0]]

        if fits_in_record and contains_no_dots:
            valid_next_char = (sizes[0] == len(record) or record[sizes[0]] != "#")

            if valid_next_char:
                total += count(record[sizes[0]+1:], sizes[1:])

    return total


def main():
    with open('input.txt') as file:
        part1 = 0
        part2 = 0
        for line in file.read().splitlines():
            record, sizes = line.split()
            sizes = tuple(int(x) for x in sizes.split(','))
            
            part1 += count(record, sizes)
            part2 += count('?'.join([record]*5), sizes*5)

        print(f'part1: {part1}, part2: {part2}')


if __name__ == '__main__':
    main()