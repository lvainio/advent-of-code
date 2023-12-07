
from collections import Counter
from functools import cmp_to_key

card_values = {'A': 13, 'K': 12, 'Q': 11, 'T': 10,
               '9': 9, '8': 8, '7': 7, '6': 6, '5': 5, '4': 4, '3': 3, '2': 2, 'J': 1}

def get_type(hand):
    """
    Returns the type of the hand as an integer. The
    stronger the hand the greater the integer. There
    are seven possible types so numbers 1-7 are used.
    """

    counts = Counter(hand)
    values = counts.values()
    keys = counts.keys()

    if 5 in values: # Five of a kind
        return 7
    
    elif 4 in values:  # Four of a kind
        if 'J' in keys: # Joker convert to 5 of a kind
            return 7
        else:
            return 6
    
    elif 2 in values and 3 in values: # Full house
        if 'J' in keys: # Jokers convert to 5 of a kind
            return 7
        else:
            return 5

    elif 3 in values: # Three of a kind
        if 'J' in keys: # Joker converts to four of a kind
            return 6
        else:
            return 4

    elif list(values).count(2) == 2: # Two pairs
        if 'J' in keys:
            if counts['J'] == 1: # 1 Joker converts to full house
                return 5
            else: # 2 jokers converts to 4 of a kind
                return 6
        else:
            return 3

    elif 2 in values: # Single pair
        if 'J' in keys: # Joker converts to 3 of a kind
            return 4
        else:
            return 2 
        
    else: # All distinct
        if 'J' in keys: # Joker converts to pair
            return 2
        else:
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
