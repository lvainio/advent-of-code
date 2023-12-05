
def parse_seed_data(data):
    """Parse first line of input and return a list of (start, length) tuples"""

    seeds = [int(i) for i in data.split()[1:]]
    return list(zip(seeds[::2], seeds[1::2]))

def parse_mappings(data):
    """Parse the mappings and return a list of all mappings"""

    mappings= []
    for item in data:
        mappings.append([list(map(int, line.split())) for line in item.split("\n")[1:]])
    return mappings

with open("input.txt") as file:
    data = file.read().split("\n\n")

seed_ranges = parse_seed_data(data[0])
mappings = parse_mappings(data[1:])

range_tuples = [seed_ranges, [],[],[],[],[],[],[]]

for i, mapping in enumerate(mappings):
    # Sort mapping in ascending order based on source value.
    sorted_mapping = sorted(mapping, key=lambda x: x[1])

    for start, length in range_tuples[i]:  
        # Keep track of where to start the next range
        range_start = start 

        for dest, source, r in sorted_mapping:
            # Mapping range is to the left of this tuple.
            if source+r-1 < start: 
                continue
            # Mapping range is to the right of this tuple.
            if source >= start+length:
                break

            tuple_start = dest
            tuple_range = r

            # Mapping range starts before the tuple's range.
            if source < start:
                tuple_start = dest + start - source
                tuple_range -= start-source

            # Mapping range is to the right of where next range should start so we need to 
            # insert a tuple that has no mapping.
            if source > range_start:
                range_tuples[i+1].append((range_start, source-range_start))

            # Mapping ends within the range of our tuple's range.
            if source + r <= start + length:
                range_tuples[i+1].append((tuple_start, tuple_range))
                range_start = source+r

            # Mapping ends outside of our tuple's range.
            else:
                tuple_range -= (source+r) - (start+length)
                range_tuples[i+1].append((tuple_start, tuple_range))
                range_start = start+length # Set start of next range beyond the limit 
                break

        # If mappings have not covered all range we need to add a tuple to cover the last 
        # bit of our range.
        if range_start < start+length:
            range_tuples[i+1].append((range_start, start+length-range_start))

min_location = float('inf')
for tuple in range_tuples[7]:
    min_location = min(min_location, tuple[0])

print(min_location)
