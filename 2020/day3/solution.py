def countTrees(map, xStart, yStart, xInc, yInc):
    treeCount = 0
    xPos = xStart
    yPos = yStart
    while yPos < len(lines):
        char = lines[yPos][xPos]
        #print(str(yPos) + ' : ' + str(xPos) + ' - ' + char)
        if char == '#':
            treeCount += 1
        xPos += xInc
        # Wrap back to zero on the x
        if xPos >= len(lines[yPos]):
            xPos = xPos % len(lines[yPos])
        yPos += yInc
    return treeCount


def part1(map):
    return countTrees(map, 0, 0, 3, 1)


def part2(map):
    slopes = [
        [1, 1],
        [3, 1],
        [5, 1],
        [7, 1],
        [1, 2]
    ]
    total = 1
    for slope in slopes:
        count = countTrees(map, 0, 0, slope[0], slope[1])
        print(str(slope[0]) + " : " + str(slope[1]) + " - " + str(count))
        total *= count
    return total


if __name__ == "__main__":
    map = None
    with open('input.txt') as f:
        map = f.read()
    lines = map.split('\n')
    
    count = part1(map)
    print('Part 1 = ' + str(count))

    total = part2(map)
    print(total)
