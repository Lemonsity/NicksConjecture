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
    
    static final int NUMBER_SEGMENTS = 1;
    static final long SEGMENT_LENGTH = 100;
    static final int SEGMENT_ARRAY_LENGTH = (int) (SEGMENT_LENGTH >> 4) + 1;

    static byte[] segment = new byte[SEGMENT_ARRAY_LENGTH];
    public static void main(String[] args) throws IOException{
        primesArrayGeneration();
        for (int i = 0; i < NUMBER_SEGMENTS; i++) {
            sieve(i);
            writeSegment(i);
        }
    }

    public static void sieve(int segmentNum) {
        System.out.println("Sieving");
        
        Arrays.fill(segment, (byte) 0);

        long begin = segmentNum * SEGMENT_LENGTH;
        long end = (segmentNum + 1) * SEGMENT_LENGTH - 1;
        long endSqrt = (long) Math.sqrt(end) + 1;

        int prime = -1;
        int primeIndex = 1;
        while (prime < endSqrt && primeIndex < primes.length) {
            prime = primes[primeIndex];
            primeIndex++;
            long moddedIndex = prime - (begin % prime);
            moddedIndex += (moddedIndex % 2 == 0) ? prime : 0; // if starting index is even, skip over, and start at an odd index
            if (begin + moddedIndex == prime) {
                moddedIndex += 2 * prime;
            }
            while (moddedIndex < SEGMENT_LENGTH) {
                System.out.println(moddedIndex);
                segment[(int) (moddedIndex >> 4)] |= 1 << ((moddedIndex >> 1) & 7); // mark as not prime
                moddedIndex += 2 * prime;
            }
        }
    }

    public static void writeSegment(int segmentNum) throws IOException {
        BufferedWriter bw = null;
        try {
            long begin = segmentNum * SEGMENT_LENGTH;
            long end = (segmentNum + 1) * SEGMENT_LENGTH - 1;

            File file = new File(String.format(".\\Primes\\BigPrimes%d.txt", segmentNum));
            if (!file.exists()) {
                file.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(file));

            long i = begin;

            if (segmentNum == 0) {
                bw.write(2 + "");
                bw.newLine();
                i = 3;
            }

            for (; i <= end; i += 2) {
                long relativeIndex = i - begin;
                if ((segment[(int) (relativeIndex >> 4)] & (1 << ((relativeIndex >> 1) & 7))) == 0) {
                    bw.write(String.valueOf(i));
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