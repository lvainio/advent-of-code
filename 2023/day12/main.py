import re

def main():
    with open('input.txt') as file:
        lines = file.read().splitlines()

        for line in lines:
            record, sizes = line.split()

            springs = re.findall(r'[#?]+', record)
            sizes = [int(x) for x in sizes.split(',')]

            # brute force is one way ofc but seems annoying

            # '#' must be spring right, '?' can be either. 

            # can there be 0 arrangements?

            # trying possible arrangements of ?







if __name__ == '__main__':
    main()