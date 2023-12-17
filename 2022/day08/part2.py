
def calc_scenic_score(trees, i, j):
    height = int(trees[i][j])

    up = 0
    for k in range(i-1, -1, -1):
        up += 1
        if int(trees[k][j]) >= height:
            break

    down = 0
    for k in range(i+1, len(trees)):
        down += 1
        if int(trees[k][j]) >= height:
            break

    left = 0
    for tree in trees[i][:j][::-1]:
        left += 1
        if int(tree) >= height:
            break
        
    right = 0
    for tree in trees[i][j+1:]:
        right += 1
        if int(tree) >= height:
            break

    return up * down * left * right

with open("input.txt") as file:
    trees = file.read().splitlines()

    max_score = 0
    for i in range(len(trees)):
        for j in range(len(trees[0])):
            max_score = max(max_score, calc_scenic_score(trees, i, j))

    print(max_score)

