
with open("input.txt") as file:
    cycle = 0
    total = 0
    register = 1

    for line in file.read().splitlines():
        cycle += 1
        if cycle % 40 == 20:
            total += register * cycle

        if line[:4] == 'addx':
            cycle += 1    
            if cycle % 40 == 20: # I think this check should be after register has been added but whatever
                total += register * cycle
            register += int(line[5:])

    print(total)