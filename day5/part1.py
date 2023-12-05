
data = open("input.txt").read().split("\n\n")

seeds = [int(i) for i in data[0].split()[1:]]

maps= []
for item in data[1:8]:
    maps.append([list(map(int, line.split())) for line in item.split("\n")[1:]])

min_location = float('inf')
for seed in seeds: 
    number = seed
    for i in range(len(maps)): 
        for j in range(len(maps[i])):
            [dest, src, r] = maps[i][j]
            if src <= number < src + r: 
                number = dest + (number - src) 
                break

    min_location = min(number, min_location)

print(min_location)