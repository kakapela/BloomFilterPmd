
import java.math.BigInteger;
import java.util.*;

public class BloomFilter2 {
    private int filterSize = 0;
    private int numberOfElements = 0;
    private int numberOfHashFunctions = 0;
    private int prime;
    private int valueRange;
    private BitSet vector;
    private ArrayList<HashFunction> functions = new ArrayList<>();

    private BloomFilter2(int filterSize, int numberOfHashFunctions, int numberOfElements, int valueRange) {
        this.filterSize = filterSize;
        this.numberOfHashFunctions = numberOfHashFunctions;
        this.numberOfElements = numberOfElements;
        this.valueRange = valueRange;

        // Set BitSet vector
        this.vector = new BitSet(filterSize);

        // Calculate prime number and store it in object
        this.prime = this.findPrimeNumber();
        // Run generator hash functions
        this.prepareHashFunctions();
    }

    private int findPrimeNumber() {
        BigInteger value = new BigInteger(String.valueOf(this.valueRange));
        return value.nextProbablePrime().intValue();
    }

    private void prepareHashFunctions() {
        Random rand = new Random(0);

        for(int i = 1; i <= this.numberOfHashFunctions; ++i) {
            int a = rand.nextInt(this.prime - 1) + 1;
            int b = rand.nextInt(this.prime);
            HashFunction hashFunction = new HashFunction(a, b);
            this.functions.add(hashFunction);
        }
    }

    private int generateHash(int value, HashFunction function) {
        return (int)(function.hash(value, this.prime) % this.filterSize);
    }

    private void add(int value) {
        int position;

        for(HashFunction function : this.functions) {
            position = this.generateHash(value, function);
            this.vector.set(position);
        }
    }

    private Boolean contains(int value) {
        int position;
        boolean found = true;

        for(HashFunction function : this.functions) {
            position = this.generateHash(value, function);

            // When found position with 0 (false) - break loop because value not exist in BloomFilter2
            if (!this.vector.get(position)) {
                found = false;
                break;
            }
        }

        return found;
    }

    private double calculateExpectedFP() {
        return Math.pow(1 -
                        Math.exp(-this.numberOfHashFunctions * (double)this.numberOfElements / this.filterSize),
                this.numberOfHashFunctions);
    }

    private void generateStats(int valueRange, HashSet<Integer> set) {
        int TP = 0, FP = 0, TN = 0, FN = 0, key;

        for (int i = 0; i < valueRange; i++) {
            key = i;
            Boolean containsBF = this.contains(key);
            Boolean containsHS = set.contains(key);

            if (containsBF && containsHS) {
                TP++;
            } else if (!containsBF && !containsHS) {
                TN++;
            } else if (!containsBF) {
                FN++;
            } else {
                FP++;
            }
        }

        System.out.println("TP = " + String.format("%6d", TP) + "\tTPR = "
                + String.format("%1.4f", (double) TP / (double) numberOfElements));
        System.out.println("TN = " + String.format("%6d", TN) + "\tTNR = "
                + String.format("%1.4f", (double) TN / (double) (valueRange - numberOfElements)));
        System.out.println("FN = " + String.format("%6d", FN) + "\tFNR = "
                + String.format("%1.4f", (double) FN / (double) (numberOfElements)));
        System.out.println("FP = " + String.format("%6d", FP) + "\tFPR = "
                + String.format("%1.6f", (double) FP / (double) (valueRange - numberOfElements)));
        System.out.println("Expected FPR = " + String.format("%1.6f", this.calculateExpectedFP()));
    }

    public static void main(String[] args) {
        double factor = 10; //zwykly mnoznik
        int k = 10; //x - liczba funkcji haszujacych pojedynczy element
        int n = 100_000; //n liczba elementow w zadanym zbiorze
        int range = 100_000_000; //przedzial z ktorego beda losowane elementy

        int m = (int) Math.round(factor * n); //m - rozmiar tablicy bitowej

        Random random = new Random(0);
        BloomFilter2 bf = new BloomFilter2(m, k, n, range);
        HashSet<Integer> set = new HashSet<>(n);

        // Generate random keys to store in BloomFilter2
        for(int i = 0; i <= n; ++i) {
            set.add(random.nextInt(range));
        }

        // Add all generated items to bitmap
        for (int item : set) {
            bf.add(item);
        }

        // Calculate stats and verify implementation

        // bf.generateStats(range, set);



      /*Zakres danych:
         99999999
        199999999 - tylko parzyste!
        199999999 - tylko nieparzyste!
        */

      HashFunction xd = new HashFunction(3,7);
        long hashedNumber = xd.hash(10, 23);
        long position = hashedNumber % 20;
   /*     System.out.println("Zahaszowana 10 to: " + position);
        HashFunctions.universalHash(10,20);*/

   HashFunctions.universalHash( 199999999 , 100000,3);
        //HashFunctions.simpleHash(199999999,10,3);

  //      Map<Integer, Integer
        //      > keys = HashFunctions.simpleHash(data, 8);
//HashFunctions.initDataSet(99999999);
    }
}