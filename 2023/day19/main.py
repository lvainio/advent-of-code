import re


workflow_regex = re.compile(r'^[a-z]+$') # change to action regex for more clarity
comparison_regex = re.compile(r'^([xmas])(<|>)([0-9]+):([a-z]+|[AR])$')


def process(action, workflows, categories):
    if action == 'A':
        return True
    elif action == 'R':
        return False

    for rule in workflows[action]:
        if rule == 'A':
            return True
        elif rule == 'R':
            return False
        
        if re.match(workflow_regex, rule):
            return process(rule, workflows, categories)
        
        category, operator, value, action = re.match(comparison_regex, rule).groups()
        if operator == '>' and categories[category] > int(value):
            return process(action, workflows, categories)

        if operator == '<' and categories[category] < int(value):
            return process(action, workflows, categories)


def explore(workflows):
    start = 'in'
    ranges = {
        'x': (1, 4000),
        'm': (1, 4000),
        'a': (1, 4000),
        's': (1, 4000)
    }

    # starting from in we go all paths and for each path we keep track of a lower and higher bound for each number maybe idk. 
    # The total for each path would be the range sizes multiplied together no?

    # if we reach a terminal state and it is accepting. We should just be able to take the ranges and multiply them together and then yeah. 
    # one issue might be overlapping things...

    # maybe we can afford to check the overlaps however? and just take into consideration the overlaps in our calculations and subtract it. 



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

    print(f'part1: {part1}, part2: {part2}')


if __name__ == '__main__':
    main()