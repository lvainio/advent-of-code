
time = 47_986_698
record = 400_121_310_111_540

ways_to_win = 0
for speed in range(time): 
    distance = speed * (time - speed)

    if distance > record:
        ways_to_win += 1

print(ways_to_win)
            
