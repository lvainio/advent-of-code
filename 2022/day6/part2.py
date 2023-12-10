
with open("input.txt") as file:
    datastream = file.read()

    for i in range(0, len(datastream)-13):
        window = datastream[i:i+14]

        if len(set(window)) == len(window):
            print(i+14)
            break