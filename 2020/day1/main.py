from functools import reduce
from itertools import combinations
        
def find_product(nums, n):
    for combination in combinations(nums, n):
        if sum(combination) == 2020:
            return reduce((lambda x, y: x * y), combination)


def main():
    with open('input.txt') as file:
        nums = [int(x) for x in file.read().splitlines()]

        print(f'part1: {find_product(nums, 2)}, part2: {find_product(nums, 3)}')


if __name__ == '__main__':
    main()