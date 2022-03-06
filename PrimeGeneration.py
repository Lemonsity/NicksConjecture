from time import process_time

N = int(input("Enter the upper limit (not including) of the primes: "))

sieve = [True] * N
for i in range(3, int(N ** 0.5) + 1, 2):
    if (i % 100 == 1):
        print("Sieving:", i, "%.2f%%" % (i / int(N ** 0.5)))
    if sieve[i]:
        sieve[i * i : : 2 * i] = [False] * ((N - 1 - i * i) // (2 * i) + 1)
result = [2] + [i for i in range(3, N, 2) if sieve[i]]

print(result[-1])

file = open("Primes/primes1.txt", "r")
for i in range(50000000, len(result), 1):
    if (i % 100000 == 0):
        print("File output:", i, "%.2f%%" % ((i - 50000000) / (len(result) - 50000000)))
    if ((i % 1000000) == 0):
        file.close()
        filename = "Primes/primes{}.txt".format(i // 1000000 + 1)
        file = open(filename, "w")
    file.write(str(result[i]) + " ")
    if (i % 8 == 0):
        file.write("\n")
file.close()
