import re

string_to_digit = {}
regex_first = r"([1-9])"
regex_last = r"([1-9])"

with open('input.txt') as f:
    sum = 0

    lines = f.readlines()
    for line in lines:
        first = re.search(regex_first, line).group()
        last = re.search(regex_last, line[::-1]).group()[::-1]

        sum += int(first + last)

    print(sum)