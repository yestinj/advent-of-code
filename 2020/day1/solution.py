
def part2():
    input_data = None
    with open("input.txt") as f:
        input_data = f.read()

    lines = input_data.split("\n")

    for i in range(len(lines)):
        for j in range(i+1, len(lines)):
            for k in range(i+2, len(lines)):
                if not (lines[i] and lines[j] and lines[k]):
                    continue
                one = int(lines[i])
                two = int(lines[j])
                three = int(lines[k])
                if (one + two + three) == 2020:
                    print('Found: ' + str(one) + " " + str(two) + " " + str(three))
                    print(one * two * three)
                    return

if __name__ == "__main__":
    part2()
