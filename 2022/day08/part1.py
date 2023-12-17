
def is_visible(trees, i, j):
    height = int(trees[i][j])

    # check from above
    taller_found = False
    for k in range(i):
        if int(trees[k][j]) >= height:
            taller_found = True
            break
    if not taller_found:
        return True
    
    # check from below
    taller_found = False
    for k in range(i+1, len(trees)):
        if int(trees[k][j]) >= height:
            taller_found = True
            break
    if not taller_found:
        return True

    # check from left
    taller_found = False
    for tree in trees[i][:j]:
        if int(tree) >= height:
            taller_found = True
            break
    if not taller_found:
        return True

    # check from right
    taller_found = False
    for tree in trees[i][j+1:]:
        if int(tree) >= height:
            taller_found = True
            break
    return not taller_found

with open("input.txt") as file:
    trees = file.read().splitlines()

    # The outside trees are all visible.
    num_visible = len(trees)*2 + len(trees[0])*2 - 4

    for i in range(1, len(trees)-1):
        for j in range(1, len(trees[0])-1):
            if is_visible(trees, i, j):
                num_visible += 1

    print(num_visible)

