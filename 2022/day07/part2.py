from collections import defaultdict
import re

regex = re.compile(r"""
    (?P<cd_back>^\$\scd\s\.\.$)|
    (?P<cd_forw>^\$\scd\s[a-z]+$)|
    (?P<cd_root>^\$\scd\s/$)|
    (?P<ls>^\$\sls$)|
    (?P<file>^[0-9]+\s[\.a-z]+$)|
    (?P<dir>^dir\s[a-z]+)
""", re.VERBOSE)

def get_group(line):
    """ Return the type/group of the line. """

    group_match = re.search(regex, line)
    for group_name, m in group_match.groupdict().items():
        if m is not None:
            return group_name
    return None



with open("input.txt") as file: 
    directory_sizes = defaultdict(int)
    stack = []

    for line in file:
        line = line.strip()
        group = get_group(line)

        if group == "cd_back":
            stack.pop()

        elif group == "cd_forw":
            stack.append(line[5:])

        elif group == "cd_root":
            stack = ["/"]

        elif group == "file":
            file_size = int(re.search(r"\d+", line).group())
            for i in range(len(stack)):    
                directory_sizes["/".join(stack[:i+1])] += file_size

           

total_disk_space = 70_000_000
needed_disk_space = 30_000_000
used_disk_space = directory_sizes["/"]

min_valid_size = float("inf")
for size in directory_sizes.values():
    space_after_removal = total_disk_space - used_disk_space + size
    if space_after_removal >= needed_disk_space:
        min_valid_size = min(min_valid_size, size)

print(min_valid_size)