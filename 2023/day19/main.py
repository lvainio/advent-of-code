import re


action_regex = re.compile(r'^([a-z]+|R|A)$')
comparison_regex = re.compile(r'^([xmas])(<|>)([0-9]+):([a-z]+|[AR])$')


def count_valid(action, workflows, ranges):
    if action == 'R':
        return 0
    elif action == 'A':
        num_accepted = 1
        for lower, upper in ranges.values():
            num_accepted *= upper - lower + 1
        return num_accepted 
   
    ranges = {
        'x': [ranges['x'][0], ranges['x'][1]],
        'm': [ranges['m'][0], ranges['m'][1]],
        'a': [ranges['a'][0], ranges['a'][1]],
        's': [ranges['s'][0], ranges['s'][1]]
    }
    total = 0

    for rule in workflows[action]:
        if re.match(action_regex, rule):
            total += count_valid(rule, workflows, ranges)
            break
        
        category, operator, value, new_action = re.match(comparison_regex, rule).groups()
        if operator == '>':
            if ranges[category][0] > int(value):
                new_lower = ranges[category][0]
            elif ranges[category][1] > int(value):
                new_lower = min(int(value) + 1, ranges[category][1])

            if ranges[category][1] > int(value):
                new_ranges = {
                    'x': (ranges['x'][0], ranges['x'][1]),
                    'm': (ranges['m'][0], ranges['m'][1]),
                    'a': (ranges['a'][0], ranges['a'][1]),
                    's': (ranges['s'][0], ranges['s'][1])
                }
                new_ranges[category] = (new_lower, ranges[category][1])
                total += count_valid(new_action, workflows, new_ranges)

            if ranges[category][0] > int(value):
                break
            elif ranges[category][1] > int(value):
                ranges[category][1] = int(value)
                
        if operator == '<':
            if ranges[category][1] < int(value):
                new_upper = ranges[category][1]
            else:
                new_upper = max(int(value) - 1, ranges[category][0]) 
            
            if ranges[category][0] < int(value):
                new_ranges = {
                    'x': (ranges['x'][0], ranges['x'][1]),
                    'm': (ranges['m'][0], ranges['m'][1]),
                    'a': (ranges['a'][0], ranges['a'][1]),
                    's': (ranges['s'][0], ranges['s'][1])
                }
                new_ranges[category] = (ranges[category][0], new_upper)
                total += count_valid(new_action, workflows, new_ranges)

            if ranges[category][1] < int(value):
                break
            elif ranges[category][0] < int(value):
                ranges[category][0] = int(value)
            else:
                ranges[category][0] = ranges[category][0] 

    return total
      

def main():
    with open('input.txt') as file:
        wf_data, part_data = file.read().split('\n\n')
    wf_data = wf_data.split('\n')
    part_data = part_data.split('\n')

    workflows = {}
    for workflow in wf_data:
        id, rules = workflow.split('{')
        rules = rules[:-1].split(',')
        workflows[id] = rules

    part1 = 0
    part2 = 0

    for part in part_data:
        ranges = {}
        for item in part[1:-1].split(','):
            category, value = item.split('=')
            ranges[category] = (int(value), int(value))
        if count_valid('in', workflows, ranges):
            for value, _ in ranges.values():
                part1 += value
            
    part2 = count_valid('in', workflows, {key: (1, 4000) for key in "xmas"})

    print(f'part1: {part1}, part2: {part2}')


if __name__ == '__main__':
    main()