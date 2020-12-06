def part1(text):
    total = 0
    yeses = set()
    for l in text:
        if not l:
            total += len(yeses)
            yeses.clear()
            continue
        for c in l:
            yeses.add(c)
    return total        

def part2(text):
    total = 0
    groupSize = 0
    yeses = [0] * 26
    print
    for l in text:
        if not l:            
            total += sum(1 for i in yeses if i == groupSize)
            groupSize = 0
            yeses = [0] * 26
            continue
        for c in l:
            yeses[ord(c) % ord('a')] += 1 
        groupSize += 1

    return total        


if __name__ == "__main__":
    text = None
    with open('input.txt') as f:
        text = f.read()
    text = text.split('\n')
    
    v = part1(text)
    print(v)

    v = part2(text)
    print(v)
