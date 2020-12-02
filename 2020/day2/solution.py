def part1():
    data = None    
    with open('input.txt') as f:
        data = f.read()
    lines = data.split('\n')
    valid = 0
    for line in lines:
        if not line:
            pass        
        pair = line.split(': ')
        rule = pair[0]
        password = pair[1]
        pair = rule.split(' ')
        char = pair[1]
        limits = pair[0]
        pair = limits.split('-')
        lower = pair[0]
        upper = pair[1]
        char_count = password.count(char)
        #print(rule + " : " + lower + " : " + upper + " : " + char + " : " + password)
        if char_count < int(lower) or char_count > (int(upper)):            
            continue
        valid += 1
    return valid


def part2():
    data = None    
    with open('input.txt') as f:
        data = f.read()
    lines = data.split('\n')
    valid = 0
    for line in lines:
        if not line:
            pass        
        pair = line.split(': ')
        rule = pair[0]
        password = pair[1]
        pair = rule.split(' ')
        char = pair[1]
        limits = pair[0]
        pair = limits.split('-')
        lower = pair[0]
        upper = pair[1]
        # account for 1-based index
        pos1 = int(lower) - 1
        pos2 = int(upper) - 1
        char1 = password[pos1]
        char2 = password[pos2]
        if (char == char1) ^ (char == char2):
            valid += 1

    return valid    


if __name__ == "__main__":
    valid_passwords = part1()
    print(valid_passwords)
    valid_passwords = part2()
    print(valid_passwords)