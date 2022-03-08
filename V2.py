import sys
import getopt
import time

startT = time.time()

variant = 1
start = 7
end = 7

helpMessage = \
"""V2.py [OPTION]

    -v, --variant[=VARIANT]     The variant testing, 
                                1 for including 2 as prime
                                2 for exlucding 2 as prime

    -s, --start[=START]         The starting value
    -e, --end[=END]             The ending value
    -h, --help                  Display this help message"""

if (len(sys.argv) == 1): # no arguments
    print(helpMessage)
    sys.exit(1)

try:
    opts, args = getopt.getopt(sys.argv[1:], "v:s:e:h", ["variant=", "start=", "end=", "help"])
except getopt.GetoptError as err:
    print(err)
    sys.exit(2)
for opt, arg in opts:
    if (opt in ("-h", "--help")):
        print(helpMessage)
        sys.exit(1)
    elif (opt in ("-v", "--variant")):
        variant = int(arg)
    elif (opt in ("-s", "--start")):
        start = int(arg)
    elif (opt in ("-e", "--end")):
        end = int(arg)
    else:
        print("Invalid option-argument pair: {}, {}".format(opt, arg))

        
# ====== Beginning of Actual Checking ======
primes = [] # all prime number up to end
primesSet = set()

# for i in range(1, 51): # read prime files
#     filename = "Primes/primes{}.txt".format(i)
#     file = open(filename, "r")
#     for line in file:
#         primes.extend(list(map(int, line.split())))
#         primesSet.update(set(map(int, line.split())))
#     if (primes[-1] >= end):
#         break

# TODO need to remove below
for i in range(0, 100):
    filename = "Primes/BigPrimes{}.txt".format(i);
    file = open(filename, "r");
    for line in file:
        prime = int(line)
        primes.append(prime);
        primesSet.add(prime);
    if (primes[-1] >= end):
        break;

if (variant == 2):
    primes.pop(0)
    primesSet.remove(2)

if (start % 2 == 0): # start from odd
    start += 1

i = start;
doublePrimeIndex = 0;
while (i < end + 1):
    # TODO will need to read on demand
    doublePrime = primes[doublePrimeIndex]; 
    if (i - 2 * doublePrime in primesSet): # found trio
        doublePrimeIndex = -1; # reset prime index to beginning
        i += 2; # continue to the next odd number
        if (i % 100000 == 1): # sense of progress
            print(i, "%.2f%%" % ((i - start)/(end - start) * 100))
    doublePrimeIndex += 1 # move onto next prime
    if (2 * doublePrime > i): # impossible to find trio
        print(i, "failed conjecture");
        break
else:
    print("All numbers between {} and {} satisfy our conjecture".format(start, end))

# for i in range(start, end + 1, 2): # for every odd number
    
#     if (i % 100000 == 1): # sense of progress
#         print(i, "%.2f%%" % ((i - start)/(end - start) * 100))
    
#     check = False # assume the number does not satisfy conjecture
#     for p in primes: # iterate through all possible p from smallest to biggest
#         if (2 * p > i): # 2p breached the odd number, cannot possibly find a (p, q) pair now
#             break
#         if ((i - 2 * p) in primesSet):
#             check = True
#             break
#     if (not check): # Found counterexample
#         print(i, "failed our conjecture")
#         break
# else: # did not find counterexample
#     print("All numbers between {} and {} satisfy our conjecture".format(start, end))

print("The program finished in {} seconds".format(time.time() - startT)) # runtime
