
import time

start_time = time.time()

data = open("input.txt").read().split("\n\n")

seeds = [int(i) for i in data[0].split()[1:]]

maps= []
for item in data[1:8]:
    maps.append([list(map(int, line.split())) for line in item.split("\n")[1:]])

min_location = float('inf')

counter = 0

for i in range(0, len(seeds), 2): 

    for seed in range(seeds[i], seeds[i]+seeds[i+1]):

        counter += 1
        if counter % 1_000_000 == 0:
            end_time = time.time()
            elapsed_time = end_time - start_time
            print(f"Elapsed time: {elapsed_time} seconds")
            print("hi")

        number = seed
        for i in range(len(maps)): 
            for j in range(len(maps[i])):
                [dest, src, r] = maps[i][j]
                if src <= number < src + r: 
                    number = dest + (number - src) 
                    break

        if number < min_location:
            min_location = number

    break

print(min_location)
            
# would take around 8 hours to run this program
# so another approach is needed lol