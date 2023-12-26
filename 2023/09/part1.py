
with open('input.txt') as file:
    total = 0
    for line in file.read().splitlines():
        numbers = [int(x) for x in line.split()]

        differences = [numbers]

        for i in range(len(numbers)):
            diff = [n2 - n1 for n1, n2 in zip(differences[i][:-1], differences[i][1:])]
            if all(x == 0 for x in diff):
                break
            differences.append(diff)

        sum = 0
        for i in range(len(differences) - 1, -1, -1):
            sum += differences[i][-1]
        total += sum

    print(f'Total sum: {total}')

        

        