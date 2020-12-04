def _passportHasRequiredFields(passport):
    for id in ['byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid']:
        if id not in passport:
            return False
    return True

def _passportHasValidFields(passport):
    byr = passport['byr']
    if len(byr) != 4 or int(byr) < 1920 or int(byr) > 2002:
        return False
    iyr = passport['iyr']
    if len(iyr) != 4 or int(iyr) < 2010 or int(iyr) > 2020:
        return False
    eyr = passport['eyr']
    if len(eyr) != 4 or int(eyr) < 2020 or int(eyr) > 2030:
        return False
    hgt = passport['hgt']
    if hgt and hgt[-2:] == 'cm':
        amt = hgt[:-2]
        if int(amt) < 150 or int(amt) > 193:
            return False
    elif hgt and hgt[-2:] == 'in':
        amt = hgt[:-2]
        if int(amt) < 59 or int(amt) > 76:
            return False
    else:
        return False
    hcl = passport['hcl']
    if len(hcl) != 7:
        return False
    if hcl[0] != '#':
        return False
    for char in hcl[1:]:
        if char not in ['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f']:
            return False
    ecl = passport['ecl']
    if ecl not in ['amb', 'blu', 'brn', 'gry', 'grn', 'hzl', 'oth']:
        return False
    pid = passport['pid']
    if len(pid) != 9:
        return False
    for c in pid:
        if c not in ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']:
            return False
    
    return True
    
    
def _isPassportValid(passport):
    if _passportHasRequiredFields(passport):
        return _passportHasValidFields(passport)
    else:
        return False

def part1(text):
    passport = dict()
    valid = 0
    for line in text:
        # Current passport done, do the final checks
        if not line:            
            if _passportHasRequiredFields(passport):
                valid += 1
            passport = dict()
            continue
        # Process and store passport fields in dict
        parts = line.split(' ')    
        for part in parts:            
            kv = part.split(":")
            key = kv[0]
            val = kv[1]
            passport[key] = val
    return valid
    

def part2(text):
    passport = dict()
    valid = 0
    for line in text:
        # Current passport done, do the final checks
        if not line:            
            if _isPassportValid(passport):
                valid += 1
            passport = dict()
            continue
        # Process and store passport fields in dict
        parts = line.split(' ')    
        for part in parts:            
            kv = part.split(":")
            key = kv[0]
            val = kv[1]
            passport[key] = val
    return valid
    

if __name__ == "__main__":
    text = None
    with open('input.txt') as f:
        text = f.read()
    text = text.split('\n')
    val = part1(text)
    print(val)

    val = part2(text)
    print(val)