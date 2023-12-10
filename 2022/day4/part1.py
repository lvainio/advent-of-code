
import re

num_regex = r"\d+"

total = 0

with open("input.txt") as file:
    for pair in file.read().splitlines():
        nums = [int(x) for x in re.findall(num_regex, pair)]

        if (nums[0] >= nums[2] and nums[1] <= nums[3]) \
        or (nums[2] >= nums[0] and nums[3] <= nums[1]):
            total += 1

print(total)