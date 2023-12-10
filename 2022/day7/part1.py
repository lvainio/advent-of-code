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

        words = line.strip().split()

        if group == "cd_back":
            stack.pop()

        elif group == "cd_forw":
            stack.append(line[5:])

        elif group == "cd_root":
            stack = ["/"]

        elif group == "file":
            file_size = int(re.search(r"\d+", line).group())
            for i in range(len(stack)):    
                directory_sizes['/'.join(stack[:i+1])] += file_size

           

total = 0
for directory, size in directory_sizes.items():
    if size <= 100_000:
        total += size
print(total)