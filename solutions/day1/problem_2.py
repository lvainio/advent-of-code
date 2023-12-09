import re

digits = ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine"]

string_to_digit = {}
regex_first = r"([1-9]"
regex_last = r"([1-9]"

for i in range(1,10):
    string_to_digit[digits[i-1]] = str(i)
    regex_first += r"|" + digits[i-1]
    regex_last += r"|" + digits[i-1][::-1]

regex_first += r")"
regex_last += r")"

with open('input.txt') as f:
    sum = 0

    lines = f.readlines()
    for line in lines:
        first = re.search(regex_first, line).group()
        last = re.search(regex_last, line[::-1]).group()[::-1]

        if first in digits:
            first = string_to_digit[first]
        if last in digits:
            last = string_to_digit[last]

        sum += int(first + last)

    print(sum)