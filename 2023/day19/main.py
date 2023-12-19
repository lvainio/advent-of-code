import re


action_regex = re.compile(r'^(?P<workflow>[a-z]+)$|^(?P<accept>A)$|^(?P<reject>R)$')
comparison_regex = re.compile(r'^([xmas])(<|>)([0-9]+):([a-z]+|[AR])$')


def process(action, workflows, categories):
    if action == 'A':
        return True
    elif action == 'R':
        return False

    for rule in workflows[action]:
        action_match = re.match(action_regex, rule)
        if action_match:
            if action_match.group('accept'):
                return True
            elif action_match.group('reject'):
                return False
            elif action_match.group('workflow'):
                return process(rule, workflows, categories)
        
        category, operator, value, action = re.match(comparison_regex, rule).groups()
        if operator == '>' and categories[category] > int(value):
            return process(action, workflows, categories)
        if operator == '<' and categories[category] < int(value):
            return process(action, workflows, categories)


def explore(action, workflows, ranges):
    for lower, upper in ranges.values(): 
        if upper < lower:
            return 0

    if action == 'A':
        num_accepted = 1
        for lower, upper in ranges.values():
            num_accepted *= upper - lower + 1
        return num_accepted
       
    elif action == 'R':
        return 0

    total = 0

    # TODO: make into a map instead for ease modification. 
    x_lower, x_upper = ranges['x']
    m_lower, m_upper = ranges['m']
    a_lower, a_upper = ranges['a']
    s_lower, s_upper = ranges['s']

    for rule in workflows[action]:
        action_match = re.match(action_regex, rule)
        if action_match:
            new_ranges = {
                'x': (x_lower, x_upper),
                'm': (m_lower, m_upper),
                'a': (a_lower, a_upper),
                's': (s_lower, s_upper)
            }
            total += explore(rule, workflows, new_ranges)
            break
        
        category, operator, value, action = re.match(comparison_regex, rule).groups()
        if operator == '>':
            if category == 'x':
                new_ranges = {
                    'x': (int(value)+1, x_upper),
                    'm': (m_lower, m_upper),
                    'a': (a_lower, a_upper),
                    's': (s_lower, s_upper)
                }
                total += explore(action, workflows, new_ranges)
                x_upper = int(value)
            elif category == 'm':
                new_ranges = {
                    'x': (x_lower, x_upper),
                    'm': (int(value)+1, m_upper),
                    'a': (a_lower, a_upper),
                    's': (s_lower, s_upper)
                }
                total += explore(action, workflows, new_ranges)
                m_upper = int(value)
            elif category == 'a':
                new_ranges = {
                    'x': (x_lower, x_upper),
                    'm': (m_lower, m_upper),
                    'a': (int(value)+1, a_upper),
                    's': (s_lower, s_upper)
                }
                total += explore(action, workflows, new_ranges)
                a_upper = int(value)
            elif category == 's':
                new_ranges = {
                    'x': (x_lower, x_upper),
                    'm': (m_lower, m_upper),
                    'a': (a_lower, a_upper),
                    's': (int(value)+1, s_upper)
                }
                total += explore(action, workflows, new_ranges)
                s_upper = int(value)

        if operator == '<':
            if category == 'x':
                new_ranges = {
                    'x': (x_lower, int(value)-1),
                    'm': (m_lower, m_upper),
                    'a': (a_lower, a_upper),
                    's': (s_lower, s_upper)
                }
                total += explore(action, workflows, new_ranges)
                x_lower = int(value)
            elif category == 'm':
                new_ranges = {
                    'x': (x_lower, x_upper),
                    'm': (m_lower, int(value)-1),
                    'a': (a_lower, a_upper),
                    's': (s_lower, s_upper)
                }
                total += explore(action, workflows, new_ranges)
                m_lower = int(value)
            elif category == 'a':
                new_ranges = {
                    'x': (x_lower, x_upper),
                    'm': (m_lower, m_upper),
                    'a': (a_lower, int(value)-1),
                    's': (s_lower, s_upper)
                }
                total += explore(action, workflows, new_ranges)
                a_lower = int(value)
            elif category == 's':
                new_ranges = {
                    'x': (x_lower, x_upper),
                    'm': (m_lower, m_upper),
                    'a': (a_lower, a_upper),
                    's': (s_lower, int(value)-1)
                }
                total += explore(action, workflows, new_ranges)
                s_lower = int(value)

    return total
      



def main():
    with open('input.txt') as file:
        data1, data2 = file.read().split('\n\n')
    data1 = data1.split('\n')
    data2 = data2.split('\n')

    part1 = 0
    part2 = 0

    workflows = {}
    for workflow in data1:
        name, rules = workflow.split('{')
        rules = rules[:-1].split(',')
        workflows[name] = rules

    for part in data2:
        categories = {item.split('=')[0]: int(item.split('=')[1]) for item in part[1:-1].split(',')}
        is_valid = process('in', workflows, categories)

        if is_valid:
            part1 += sum(categories.values())

    ranges = {
        'x': (1, 4000),
        'm': (1, 4000),
        'a': (1, 4000),
        's': (1, 4000)
    }

    part2 = explore('in', workflows, ranges)

    print(f'part1: {part1}, part2: {part2}')


if __name__ == '__main__':
    main()