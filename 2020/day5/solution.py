import math

def _calculateSeatRowColumn(boardingPass):
    # Calculate row
    rmin = 0
    rmax = 127
    row = None
    for c in boardingPass[:7]:
        if c == 'F':
            # Update max, auto round down via int division
            rmax = int(math.floor((rmin + rmax) / 2))
        elif c == 'B':
            # Update min, round up
            rmin = int(math.ceil((rmin + rmax) / 2.0))
    # Calculate column
    cmin = 0
    cmax = 7    
    for c in boardingPass[7:]:
        if c == 'L':
            # Update max, auto round down via int division
            cmax = int(math.floor((cmin + cmax) / 2))
        elif c == 'R':
            # Update min, round up
            cmin = int(math.ceil((cmin + cmax) / 2.0))
    return rmin, cmin

def part1(text):
    maxId = 0
    for l in text:
        (row, col) = _calculateSeatRowColumn(l)        
        id = row * 8 + col
        if id > maxId:
            maxId = id        
    return maxId

def part2(text, maxId):
    seats = set()
    for l in text:
        (row, col) = _calculateSeatRowColumn(l)        
        id = row * 8 + col
        seats.add(id)
    diff = set(range(48, maxId+1)) - seats    
    return list(diff)[0]


if __name__ == "__main__":
    text = None
    with open('input.txt') as f:
        text = f.read()
    text = text.split('\n')
    
    v = part1(text)
    print(v)

    v = part2(text, v)
    print(v)