import re


action_regex = re.compile(r'^([a-z]+|R|A)$')
comparison_regex = re.compile(r'^([xmas])(<|>)([0-9]+):([a-z]+|[AR])$')


def count(action, workflows, ranges):
    if action == 'R':
        return 0
    elif action == 'A':
        num_accepted = 1
        for lower, upper in ranges.values():
            num_accepted *= upper - lower + 1
        return num_accepted 
   

    total = 0
 
    x_lower, x_upper = ranges['x']
    m_lower, m_upper = ranges['m']
    a_lower, a_upper = ranges['a']
    s_lower, s_upper = ranges['s']

    for rule in workflows[action]:
        if re.match(action_regex, rule):
            new_ranges = {
                'x': (x_lower, x_upper),
                'm': (m_lower, m_upper),
                'a': (a_lower, a_upper),
                's': (s_lower, s_upper)
            }
            total += count(rule, workflows, new_ranges)
            break
        
        category, operator, value, new_action = re.match(comparison_regex, rule).groups()
        if operator == '>':
            
            if category == 'x':

                new_x_lower = x_lower
                if x_lower > int(value):
                    new_x_lower = x_lower
                elif x_upper > int(value):
                    new_x_lower = min(int(value)+1, x_upper) 

                if x_upper > int(value):
                    new_ranges = {
                        'x': (new_x_lower, x_upper),
                        'm': (m_lower, m_upper),
                        'a': (a_lower, a_upper),
                        's': (s_lower, s_upper)
                    }
                    total += count(new_action, workflows, new_ranges)
                

                if x_lower > int(value):
                    break
                elif x_upper > int(value):
                    x_upper = int(value)
                


            elif category == 'm':

                new_m_lower = m_lower
                if m_lower > int(value):
                    new_m_lower = m_lower
                else:
                    new_m_lower = min(int(value)+1, m_upper)
                
                if m_upper > int(value):
                    new_ranges = {
                        'x': (x_lower, x_upper),
                        'm': (new_m_lower, m_upper),
                        'a': (a_lower, a_upper),
                        's': (s_lower, s_upper)
                    }
                    total += count(new_action, workflows, new_ranges)
                if m_lower > int(value):
                    break
                elif m_upper > int(value):
                    m_upper = int(value)
            elif category == 'a':
                new_a_lower = a_lower
                if a_lower > int(value):
                    new_a_lower = a_lower
                else:
                    new_a_lower = min(int(value)+1, a_upper)
                
                if a_upper > int(value):
                    new_ranges = {
                        'x': (x_lower, x_upper),
                        'm': (m_lower, m_upper),
                        'a': (new_a_lower, a_upper),
                        's': (s_lower, s_upper)
                    }
                    total += count(new_action, workflows, new_ranges)
                if a_lower > int(value):
                    break
                elif a_upper > int(value):
                    a_upper = int(value)
            elif category == 's':
                new_s_lower = s_lower
                if s_lower > int(value):
                    new_s_lower = s_lower
                else:
                    new_s_lower = min(int(value)+1, s_upper)


                if s_upper > int(value):
                    new_ranges = {
                        'x': (x_lower, x_upper),
                        'm': (m_lower, m_upper),
                        'a': (a_lower, a_upper),
                        's': (new_s_lower, s_upper)
                    }
                    total += count(new_action, workflows, new_ranges)
                if s_lower > int(value):
                    break
                elif s_upper > int(value):
                    s_upper = int(value)

        if operator == '<':
            
            if category == 'x':
                new_x_lower = x_lower
                if x_upper < int(value):
                    new_x_upper = x_upper
                else:
                    new_x_upper = max(int(value)-1, x_lower) 

                if x_lower < int(value):
                    new_ranges = {
                        'x': (new_x_lower, new_x_upper),
                        'm': (m_lower, m_upper),
                        'a': (a_lower, a_upper),
                        's': (s_lower, s_upper)
                    }
                    total += count(new_action, workflows, new_ranges)

                if x_upper < int(value):
                    break
                elif x_lower < int(value):
                    x_lower = int(value)
                else:
                    x_lower = x_lower 

                
            elif category == 'm':
                if m_upper < int(value):
                    new_m_upper = m_upper
                else:
                    new_m_upper = max(int(value)-1, m_lower) 

                if m_lower < int(value):
                    new_ranges = {
                        'x': (x_lower, x_upper),
                        'm': (m_lower, new_m_upper),
                        'a': (a_lower, a_upper),
                        's': (s_lower, s_upper)
                    }
                    total += count(new_action, workflows, new_ranges)
                if m_upper < int(value):
                    break
                elif m_lower < int(value):
                    m_lower = int(value)
                else:
                    m_lower =m_lower
            elif category == 'a':

                if a_upper < int(value):
                    new_a_upper = a_upper
                else:
                    new_a_upper = max(int(value)-1, a_lower) 

                if a_lower < int(value):
                    new_ranges = {
                        'x': (x_lower, x_upper),
                        'm': (m_lower, m_upper),
                        'a': (a_lower, new_a_upper),
                        's': (s_lower, s_upper)
                    }
                    total += count(new_action, workflows, new_ranges)
                if a_upper < int(value):
                    break
                elif a_lower < int(value):
                    a_lower = int(value)
                else:
                    a_lower = a_lower
            elif category == 's':

                
                if s_upper < int(value):
                    new_s_upper = s_upper
                else:
                    new_s_upper = max(int(value)-1, s_lower) 

                if s_lower < int(value):
                    new_ranges = {
                        'x': (x_lower, x_upper),
                        'm': (m_lower, m_upper),
                        'a': (a_lower, a_upper),
                        's': (s_lower, new_s_upper)
                    }
                    total += count(new_action, workflows, new_ranges)
                if s_upper < int(value):
                    break
                elif s_lower < int(value):
                    s_lower = int(value)
                else:
                    s_lower = s_lower

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
        if count('in', workflows, ranges):
            for value, _ in ranges.values():
                part1 += value
            
    part2 = count('in', workflows, {key: (1, 4000) for key in "xmas"})

    print(f'part1: {part1}, part2: {part2}')


if __name__ == '__main__':
    main()