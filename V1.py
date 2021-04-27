import time
startT = time.time()


start = int(input("Input starting number: "))
end = int(input("Input ending number: "))

primes = [] # all prime number up to end
primesSet = set()

for i in range(1, 51):
    filename = "primes{}.txt".format(i)
    file = open(filename, "r")
    for line in file:
        primes.extend(list(map(int, line.split())))
        primesSet.update(set(map(int, line.split())))
    if (primes[-1] >= end):
        break

if (start % 2 == 0): # start from odd
    start += 1
for i in range(start, end + 1, 2): # for every odd number
    
    if (i % 100000 == 1): # sense of progress
        print(i)
    
    check = False # assume the number does not satisfy conjecture
    for p in primes: # iterate through all possible p from smallest to biggest
        if (2 * p > i): # 2p breached the odd number, cannot possibly find a (p, q) pair now
            break
        if ((i - 2 * p) in primesSet):
            check = True
            break
    if (not check): # Found counterexample
        print(i, "failed our conjecture")
        break
else: # did not find counterexample
    print("All numbers between {} and {} satisfy our conjecture".format(start, end))

print("The program finished in {} seconds".format(time.time() - startT)) # runtime