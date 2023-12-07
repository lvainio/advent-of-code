
from collections import Counter
from functools import cmp_to_key

card_values = {'A': 14, 'K': 13, 'Q': 12, 'J': 11, 'T': 10,
               '9': 9, '8': 8, '7': 7, '6': 6, '5': 5, '4': 4, '3': 3, '2': 2}

def get_type(hand):
    """
    Returns the type of the hand as an integer. The
    stronger the hand the greater the integer. There
    are seven possible types so numbers 1-7 are used.
    """

    counts = Counter(hand).values()
    if 5 in counts: # Five of a kind
        return 7
    elif 4 in counts:  # Four of a kind
        return 6
    elif 2 in counts and 3 in counts: # Full house
        return 5
    elif 3 in counts: # Three of a kind
        return 4
    elif list(counts).count(2) == 2: # Two pairs
        return 3
    elif 2 in counts: # Single pair
        return 2 
    else: # All distinct
        return 1

def compare_hands(a, b):
    """
    Return -1 if a is a worse hand than b, 1 if
    a is a better hand than b, and 0 if they are 
    exactly the same.
    """

    hand_a = a[0]
    hand_b = b[0]

    type_a = get_type(hand_a)
    type_b = get_type(hand_b)

    if type_a < type_b:
        return -1
    elif type_a > type_b:
        return 1
    
    for ca, cb in zip(hand_a, hand_b):
        if card_values[ca] < card_values[cb]:
            return -1
        elif card_values[ca] > card_values[cb]:
            return 1
    
    return 0
    
with open("input.txt") as file:
    lines = file.read().splitlines()

    hands = [(line.split()[0], int(line.split()[1])) for line in lines]

    sorted_hands = sorted(hands, key=cmp_to_key(compare_hands))

    total = 0
    for i, (_, bid) in enumerate(sorted_hands):
        total += (i+1) * bid
    print(total)
