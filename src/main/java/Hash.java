import lombok.Data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class Hash {
    private int a;
    private int b;
    private int p;
    private int m;
    private long position;
    private BigInteger value;

    public Hash(int a, int b, int p, int m) {
        this.a = a;
        this.b = b;
        this.p = p;
        this.m = m;
    }
    public long generate(long x){
        position = (long)((long)((long)a*x+(long)b) %(long) p) %(long) m ;
        return position;
    }
    public static void universalHash(int numbersRange, int m, int dataSet){
        System.out.println("started...");
        System.out.println("Trudny hash || m= " + m);

        long position;
        int a,b,p,counter;
        Map<Long, Integer> treeMap = new TreeMap<>();
        for(long i=0; i<m; i++){
            treeMap.put(i,0);
        }
        BigInteger value = new BigInteger(String.valueOf(m));

        //p>m
        p = value.nextProbablePrime().intValue();

        Random rand = new Random(0);
        a = rand.nextInt(p - 1) + 1;
        b = rand.nextInt(p);
        switch(dataSet){
            //Kolejne liczby z zakresu od 0 do 10^8 ? 1
            case 1: {
                counter=0;
                long startTime = System.nanoTime();
                for(long i=0; i < numbersRange; i++){
                    position = ((a*i+b) % p) % m ;

                    treeMap.put(position,treeMap.get(position)+1);
                    counter++;
                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap,10);
                System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
//                System.out.println("Liczba liczb: " + counter);
                calculateEntropy(m,treeMap,counter);
                calculateMediumSquareError(m,treeMap,counter);
                break;
            }
            //parzyste
            case 2: {
                counter=0;
                long startTime = System.nanoTime();

                for(int i=0; i < numbersRange; i+=2){
                    position = (long)((long)((long)a*i+(long)b) %(long) p) %(long) m ;
//                    System.out.println("Position: " + position+ " liczba: "+ i+" a: " + a+ " b: "+ b + " p:" + p);
                    treeMap.put(position,treeMap.get(position)+1);
                    counter++;
                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap,10);
                System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
//                System.out.println("Liczba liczb: " + counter);
                calculateEntropy(m,treeMap,counter);
                calculateMediumSquareError(m,treeMap,counter);
                break;
            }
            //nieparzyste
            case 3: {
                counter=0;
                long startTime = System.nanoTime();
                for(int i=1; i < numbersRange; i+=2){
                    position = (long)((long)((long)a*i+(long)b) %(long) p) %(long) m ;
                    treeMap.put(position,treeMap.get(position)+1);
                    counter++;
                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap,10);
                System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
//                System.out.println("Liczba liczb: " + counter);
                calculateEntropy(m,treeMap,counter);
                calculateMediumSquareError(m,treeMap,counter);
                break;
            }
            default: break;
        }
    }
    public static void simpleHash(int numbersRange, int m, int dataSet){
        System.out.println("started...");
        System.out.println("Prosty hash || M= " + m);
        long position;
        int  counter;
        Map<Long, Integer> treeMap = new TreeMap<>();
        for(long i=0; i<m; i++){
            treeMap.put(i,0);
        }
        switch(dataSet){
            //Kolejne liczby z zakresu od 0 do 10^8 ? 1
            case 1: {
                counter=0;
                long startTime = System.nanoTime();
                for(long i=0; i < numbersRange; i++){
                    position = Math.toIntExact(i % m);
                    treeMap.put(position,treeMap.get(position)+1);
                    counter++;
                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap,10);
                System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
//                System.out.println("Liczba liczb: " + counter);
                calculateEntropy(m,treeMap,counter);
                calculateMediumSquareError(m,treeMap,counter);
                break;
            }
            //parzyste
            case 2: {
                counter=0;
                long startTime = System.nanoTime();
                for(long i=0; i < numbersRange; i+=2){
                    position = Math.toIntExact(i % m);
                    treeMap.put(position,treeMap.get(position)+1);
                    counter++;
                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap,10);
                System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
//                System.out.println("Liczba liczb: " + counter);
                calculateEntropy(m,treeMap,counter);
                calculateMediumSquareError(m,treeMap,counter);
                break;
            }
            //nieparzyste
            case 3: {
                counter=0;
                long startTime = System.nanoTime();
                for(long i=1; i < numbersRange; i+=2){
                    position = Math.toIntExact(i % m);
                    treeMap.put(position,treeMap.get(position)+1);
                    counter++;
                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap,10);
                System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
//                System.out.println("Liczba liczb: " + counter);
                calculateEntropy(m,treeMap,counter);
                calculateMediumSquareError(m,treeMap,counter);
                break;
            }
            default: break;
        }
    }

    public static void calculateEntropy(int m, Map<Long, Integer> treeMap, int n){
        Double sum = new Double(0);
        for(long i =0; i<m;i++){
            Double pi = new Double(treeMap.get(i))/n;
            if(pi!=0)
                sum= sum +  (pi * Math.log10(pi));
        }
        sum=-sum;

        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340); // 340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
        Double E_star = - Math.log10((1/new Double(m)));
        Double diff = sum- E_star;
        System.out.println("E= " + df.format(sum) + "  | E*= " +df.format(E_star) + "  | E*-E= " + df.format(Math.abs(diff)) );
    }
    public static long simpleHash(long x, int m){
        return Math.toIntExact(x % m);
    }
    public static void simpleHashMSDC(int m){
        System.out.println("started...");
        System.out.println("Prosty Hash || m=" + m + " dane MSDC");
        String csvFile = "facts-nns.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Map<Long, Integer> treeMap = new TreeMap<>();
        for(long i=0; i<m; i++){
            treeMap.put(i,0);
        }
        int lines = 0;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            long startTime = System.nanoTime();
            while ((line = br.readLine()) != null) {
                if(lines ==0) {lines++; continue;}

                String[] facts = line.split(cvsSplitBy);
                long position = simpleHash(Long.parseLong(facts[1]), m);
                treeMap.put(position,treeMap.get(position)+1);
                lines++;
            }
            long estimatedTime = System.nanoTime() - startTime;
            printData(treeMap,10);
            System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
            calculateEntropy(m,treeMap,lines);
          /*  int sum=0;
            for (long i =0; i<treeMap.size(); i++) {
                sum+=treeMap.get(i);
            }
            System.out.println("Suma elementow: " + sum);*/
            calculateMediumSquareError(m,treeMap,lines);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void universalHashMSDC(int m){
        System.out.println("started...");
        System.out.println("Trudny hash || m=" + m + " dane MSDC");
        String csvFile = "facts-nns.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Random rand = new Random(0);

        BigInteger value =new BigInteger(String.valueOf(m));
        int p = value.nextProbablePrime().intValue();
      int a =   ThreadLocalRandom.current().nextInt(1, p);
        int b =   ThreadLocalRandom.current().nextInt(0, p);
        Hash hash = new Hash(a,b,p,m);
        Map<Long, Integer> treeMap = new TreeMap<>();
        for(long i=0; i<m; i++){
            treeMap.put(i,0);
        }
        int lines = 0;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            long startTime = System.nanoTime();
            while ((line = br.readLine()) != null) {
                if(lines ==0) {lines++; continue;}

                String[] facts = line.split(cvsSplitBy);
                long position = hash.generate(Integer.parseInt(facts[1]));
                treeMap.put(position,treeMap.get(position)+1);
                lines++;
            }
            long estimatedTime = System.nanoTime() - startTime;
            printData(treeMap,10);
            System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
            calculateEntropy(m,treeMap,lines);
     /*       int sum = 0;
            for (long i =0; i<treeMap.size(); i++) {
                sum+=treeMap.get(i);
            }
            System.out.println("Suma elementow: " + sum);*/
            calculateMediumSquareError(m,treeMap,lines);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void calculateMediumSquareError(int m, Map<Long, Integer> treeMap, int n){
        float sum=0, tmp=0;
        for(long i =0; i<m;i++){
            float pi = (float) treeMap.get(i)/n;
            tmp = pi-(float) (1/(float)m);
            sum+=(float) Math.pow(tmp,2);
        }
        float mse = ((1/(float)m) * sum);
        System.out.printf("MSE= %.8f \n",mse );
    }
    public static void  printData(Map<Long,Integer> map, long n){
        for(long i = 0; i<n; i++){
            System.out.println("Liczba zmapowanych wartosci kluczy: " + i +" : "+ map.get(i));
        }

    }
}
