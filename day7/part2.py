
from collections import Counter
from functools import cmp_to_key

card_values = {'A': 13, 'K': 12, 'Q': 11, 'T': 10,
               '9': 9, '8': 8, '7': 7, '6': 6, '5': 5, '4': 4, '3': 3, '2': 2, 'J': 1}

hand_values = {'five_of_a_kind': 7, 'four_of_a_kind': 6, 'full_house': 5, 
               'three_of_a_kind': 4, 'two_pairs': 3, 'pair': 2, 'all_distinct': 1}

def get_type(hand):
    """
    Returns the type of the hand as an integer. The
    stronger the hand the greater the integer.
    """

    counts = Counter(hand)
    values = counts.values()
    keys = counts.keys()

    if 5 in values:
        return hand_values['five_of_a_kind']
    
    elif 4 in values:
        if 'J' in keys: 
            # AAAAJ or JJJJA -> AAAAA
            return hand_values['five_of_a_kind']
        else:
            return hand_values['four_of_a_kind']
    
    elif 2 in values and 3 in values:
        if 'J' in keys: 
            # AAAJJ or JJJAA -> AAAAA
            return hand_values['five_of_a_kind']
        else:
            return hand_values['full_house']

    elif 3 in values:
        if 'J' in keys:
            # AAAJQ or JJJAQ -> AAAAQ
            return hand_values['four_of_a_kind']
        else:
            return hand_values['three_of_a_kind']

    elif list(values).count(2) == 2:
        if 'J' in keys:
            if counts['J'] == 1:
                # AAQQJ -> AAQQA 
                return hand_values['full_house']
            else: 
                # AAJJQ -> AAAAQ
                return hand_values['four_of_a_kind']
        else:
            return 3

    elif 2 in values:
        if 'J' in keys:
            # AAJKQ -> AAAKQ
            return hand_values['three_of_a_kind']
        else:
            return hand_values['pair'] 
        
    else:
        if 'J' in keys:
            # AJKQT -> AAKQT 
            return hand_values['pair']
        else:
            return hand_values['all_distinct']

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
