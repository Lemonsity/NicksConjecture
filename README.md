# Nick's Conjecture
The conjecture is thought up by Nick Cheng from Univerity of Toronto Scarborough Campus (UTSC), later found out to be same as [Lemoine's conjecture](https://en.wikipedia.org/wiki/Lemoine%27s_conjecture#:~:text=In%20number%20theory%2C%20Lemoine's%20conjecture,number%20and%20an%20even%20semiprime.). This repository contains program that are designed to confirm/disprove the conjecture through brute force.
More details will be added in the future

## Preparation
**Warning: ~50GB of free space is needed**
Compile and execute `PrimeGeneration.java` in the root folder
100 `BigPrime[X].txt` files will be generated within `Primes/` directory

## Instruction
Run following command line in root folder:
```
py V2.py <args>
```

## Arguments
- `-v, --variant`: `1` for including 2 as prime, `2` for excluding 2
- `-s, --start`: Starting number
- `-e, --end`: Ending number

## Codes
- `V2.py`: Code for checking Nick's Conjecture
- `PrimeGeneration.py`: Original prime generating algorithm
- `PrimeGeneration.java`: Prime generating algorithm allows for up to multi-billion

## Output and Other
`[X] failed conjecture ` will be printed if a number had failed
`All numbers between [start] and [end] satisfy our conjecture` will be printed if all number between interval satisfies the conjecture
Progress will be printed throughout (Noticable when checking large internval)
**Could take a long time load prime**, be patient, grab a cup of coffee, get some sleep
## Special Thanks:
- [The first fifty million primes](https://primes.utm.edu/lists/small/millions/): Proving the `primeX.txt` files used in this program
- [Prime I.T.](http://compoasso.free.fr/primelistweb/page/prime/accueil_en.php): Sieve algorithm that allows for generating primes up to multi-billions
- [MakeTheBrainHappy](https://www.makethebrainhappy.com/2019/06/lemoines-conjecture-verified-to-1010.html): Modified code for checking