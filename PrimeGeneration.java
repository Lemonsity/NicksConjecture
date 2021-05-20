/**
 * Credit to original author from:
 * http://compoasso.free.fr/primelistweb/page/prime/accueil_en.php and
 * http://compoasso.free.fr/primelistweb/ressource/Eratos4.java
 * 
 * I simply made some revision, fix some potential bugs
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class PrimeGeneration {
    static Integer[] primes = null; // This array is used for sieve, contains primes up to sqrt(N)
    
    static final int NUMBER_SEGMENTS = 100; // number of segments
    static final long SEGMENT_LENGTH = 1000000000; // size of each segment, depend on computer, 1 billion is stretching the upper limit

    static final int SEGMENT_ARRAY_LENGTH = (int) (SEGMENT_LENGTH >> 4) + 1;

    // if a bit is 1, then it is Composite number
    // if a bit is 0, then it is Prime number
    static byte[] segment = new byte[SEGMENT_ARRAY_LENGTH];
    public static void main(String[] args) throws IOException{
        primesArrayGeneration();
        for (int i = 0; i < NUMBER_SEGMENTS; i++) {
            sieve(i);
            writeSegment(i);
        }
    }

    public static void sieve(int segmentNum) {
        Arrays.fill(segment, (byte) 0);

        long begin = segmentNum * SEGMENT_LENGTH; // starting number
        long end = (segmentNum + 1) * SEGMENT_LENGTH - 1; // ending number (both inclusion)
        long endSqrt = (long) Math.sqrt(end) + 1; // Biggest number to check up to

        System.out.printf("Sieving segment: %d\n", segmentNum);
        System.out.printf("Upper limit of prime to check: %d\n", endSqrt);
        int upperboundIndex = Arrays.binarySearch(primes, (int) endSqrt);
        upperboundIndex *= (upperboundIndex < 0) ? -1 : 1;
        
        int prime = -1;
        int primeIndex = 1;
        while (prime < endSqrt && primeIndex < primes.length) {
            // get next prime
            prime = primes[primeIndex];
            primeIndex++;

            if (primeIndex % 1000 == 0) { // rough progress
                System.out.printf("Segment %d progress: %.2f%% (estimate)\n", segmentNum, (double)(100 *  primeIndex) / upperboundIndex);
            }

            // index relative to begin
            // This starts at the first multiple of prime that is >= to begin
            long relativeIndex = prime - (begin % prime); 

            relativeIndex += (relativeIndex % 2 == 0) ? prime : 0; // if index is even, skip over, and start at an odd index
            
            if (begin + relativeIndex == prime) { // if the index represent a prime itself, then we DO NOT mark it as composite 
                relativeIndex += 2 * prime;
            }

            while (relativeIndex < SEGMENT_LENGTH) {
                segment[(int) (relativeIndex >> 4)] |= 1 << ((relativeIndex >> 1) & 7); // mark as not prime
                relativeIndex += 2 * prime;
            }
        }
    }

    public static void writeSegment(int segmentNum) throws IOException {

        System.out.printf("Writing segment: %d\n", segmentNum);

        BufferedWriter bw = null;
        try {
            long begin = segmentNum * SEGMENT_LENGTH;
            long end = (segmentNum + 1) * SEGMENT_LENGTH - 1;

            File file = new File(String.format(".\\Primes\\BigPrimes%d.txt", segmentNum));
            if (!file.exists()) {
                file.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(file));

            long i = begin + ((begin % 2 == 0) ? 1 : 0); // only odd number can be prime

            if (segmentNum == 0) { // special case
                bw.write(2 + "");
                bw.newLine();
                i = 3;
            }

            for (; i <= end; i += 2) {

                if (i % 50000000 == 1) {
                    System.out.printf("Segment %d writing progress: %.2f%%\n", segmentNum, (double)(100 * (i - begin)) / SEGMENT_LENGTH);
                }

                long relativeIndex = i - begin;
                if ((segment[(int) (relativeIndex >> 4)] & (1 << ((relativeIndex >> 1) & 7))) == 0) { // if bit is 0, then it is prime
                    bw.write(i + "");
                    bw.newLine();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
        
    }

    /**
     * Generate primes up to sqrt(N)
     * These primes are used for sieving
     */
    public static void primesArrayGeneration() { 
        ArrayList<Integer> primesList = new ArrayList<>();
        long N = NUMBER_SEGMENTS * SEGMENT_LENGTH;
        int maxPrimeForSieve = (int) Math.sqrt((double) N);
        primesList.add(2);
        for (int i = 3; i <= maxPrimeForSieve; i += 2) {
            boolean flag = true;
            for (int prime : primesList) {
                if (prime > Math.sqrt(i)) {
                    break;
                }
                if (i % prime == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                primesList.add(i);
            }
        }
        primes = primesList.toArray(new Integer[0]);
    }
}