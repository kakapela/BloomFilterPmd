
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        double factor =8;
        int k = 1;
        int n =100000;
        int range = 99999999;
        int m = (int) Math.round(factor * n);

        System.out.println("Pamiêæ filtra Blooma: " + m);
        BloomFilter bloomFilter = new BloomFilter(k,m,range,n);
        bloomFilter.addMultipleRandoms();
//        Stats.checkStats(bloomFilter);
//        Stats.calculateProbabilityOfFP(bloomFilter,n);
        HashSet<Integer> set = bloomFilter.getSet();

        ReferenceFilter referenceFilter = new ReferenceFilter(n, set);
        referenceFilter.initAdding();
        referenceFilter.checkStats(range);
//        for (int i =0; i<10; i++){
            /*BloomFilter bloomFilter = new BloomFilter(2,1000,999999999,3);
            bloomFilter.prepareMsdcData();
            bloomFilter.filterForMSDC();
//        }
      //  BloomFilter.filterForMSDC();
        /*Zakres danych:
         99999999
        199999999 - tylko parzyste!
        199999999 - tylko nieparzyste!
        */
        //Hash.universalHashMSDC(100000);
    //Hash.universalHashMSDC(1000);
    }

}