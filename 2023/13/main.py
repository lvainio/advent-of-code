def count_mismatches(grid, i):
    num_mismatches = 0
    for row1, row2 in zip(grid[:i][::-1], grid[i:]):
        num_mismatches += sum(c1 != c2 for c1, c2 in zip(row1, row2))
    return num_mismatches


with open('input.txt') as file:
    grids = [g.splitlines() for g in file.read().split('\n\n')]

part1 = 0
part2 = 0
for grid in grids:
    for i in range(1, len(grid)):
        num_mismatches = count_mismatches(grid, i)
        part1 += 100 * i if num_mismatches == 0 else 0
        part2 += 100 * i if num_mismatches == 1 else 0
    for i in range(1, len(grid[0])):
        num_mismatches = count_mismatches(list(zip(*grid)), i)
        part1 += i if num_mismatches == 0 else 0
        part2 += i if num_mismatches == 1 else 0

print(f'part1: {part1}, part2: {part2}')