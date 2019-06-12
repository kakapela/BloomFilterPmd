
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        double factor =20;
        int k = 5;
        int n =1000000;
        int range = 99999999;
        int m = (int) Math.round(factor * n);

        /*BloomFilter bloomFilter = new BloomFilter(k,m,range);
        bloomFilter.addMultipleRandoms(n);
        Stats.checkStats(bloomFilter);
        Stats.calculateProbabilityOfFP(bloomFilter,n);*/
//        for (int i =0; i<10; i++){
        /*    BloomFilter bloomFilter = new BloomFilter(2,1000,999999999,3);
            bloomFilter.prepareMsdcData();
            bloomFilter.filterForMSDC();*/
//        }
      //  BloomFilter.filterForMSDC();
        /*Zakres danych:
         99999999
        199999999 - tylko parzyste!
        199999999 - tylko nieparzyste!
        */
        Hash.simpleHash(1000000,10,1);
    }
}