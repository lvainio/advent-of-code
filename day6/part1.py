with open("input.txt") as file:
    race_times, record_distances = file.read().splitlines()

    race_times = [int(time) for time in race_times.split()[1:]]
    record_distances = [int(dist) for dist in record_distances.split()[1:]]

    num_races = len(race_times)
    num_wins = [0 for _ in range(num_races)]

    for race in range(num_races):
        record = record_distances[race]

        for push_down_time in range(1, race_times[race]): 
            race_dist = push_down_time * (race_times[race] - push_down_time)

            if race_dist > record:
                num_wins[race] += 1

res = 1
for num in num_wins:
    res *= num
print(res)
            
