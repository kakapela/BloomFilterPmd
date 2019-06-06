import lombok.Data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class HashFunctions {

    // a,b - a od 1 do p-1, b od 0 do p-1
    //p>m

    public static long universalHash(long x ,int m){

        BigInteger value = new BigInteger(String.valueOf(m));
        int p = value.nextProbablePrime().intValue();
        //bound is p-1, a oraz b losowane z przedzialu
        int a = ThreadLocalRandom.current().nextInt(1, p);
        int b = ThreadLocalRandom.current().nextInt(0, p);

        long position = (long)((a*x+b) % p) % m ;

        return position;
    }

    public static void universalHash(int numbersRange, int m, int dataSet){
        System.out.println("started...");

        long position;
        int a,b,p,counter;
        Map<Long, Integer> treeMap = new TreeMap<>();
        for(long i=0; i<m; i++){
            treeMap.put(i,0);
        }
        BigInteger value = new BigInteger(String.valueOf(m));

        //p>m
        p = value.nextProbablePrime().intValue();

        switch(dataSet){
            //Kolejne liczby z zakresu od 0 do 10^8 ? 1
            case 1: {
                counter=0;
                long startTime = System.nanoTime();
                for(long i=0; i < numbersRange; i++){
                    a = ThreadLocalRandom.current().nextInt(1, p);
                    b = ThreadLocalRandom.current().nextInt(0, p);
                    position = ((a*i+b) % p) % m ;
                        treeMap.put(position,treeMap.get(position)+1);
                        counter++;
                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap);
//                System.out.println("Czas obliczen: " + estimatedTime/1_000_000_000.0 + " sec");
                System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
                System.out.println("Liczba liczb: " + counter);
                calculateEntropy(m,treeMap,counter);
                calculateMediumSquareError(m,treeMap,counter);
                break;
            }
            //parzyste
            case 2: {
                counter=0;
                long startTime = System.nanoTime();
                for(int i=0; i < numbersRange; i+=2){
                    a = ThreadLocalRandom.current().nextInt(1, p);
                    b = ThreadLocalRandom.current().nextInt(0, p);
                    position = ((a*i+b) % p) % m ;
//                   System.out.println(i+" : " + position);
                        treeMap.put(position,treeMap.get(position)+1);
                        counter++;

                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap);
//                System.out.println("Czas obliczen: " + estimatedTime/1_000_000_000.0 + " sec");
                System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
                System.out.println("Liczba liczb: " + counter);
                calculateEntropy(m,treeMap,counter);
                calculateMediumSquareError(m,treeMap,counter);
                break;
            }
            //nieparzyste
            case 3: {
                counter=0;
                long startTime = System.nanoTime();
                for(int i=1; i < numbersRange; i+=2){
                    a = ThreadLocalRandom.current().nextInt(1, p);
                    b = ThreadLocalRandom.current().nextInt(0, p);
                    position = ((a*i+b) % p) % m ;
//                    System.out.println(i+" : " + position);
                        treeMap.put(position,treeMap.get(position)+1);
                        counter++;
                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap);
//                System.out.println("Czas obliczen: " + estimatedTime/1_000_000_000.0 + " sec");
                System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
                System.out.println("Liczba liczb: " + counter);
                calculateEntropy(m,treeMap,counter);
                calculateMediumSquareError(m,treeMap,counter);
                break;
            }
            default: break;
        }
    }

    public static long simpleHash(long x, int m){
        return Math.toIntExact(x % m);
    }
    public static void simpleHash(int numbersRange, int m, int dataSet){
        System.out.println("started...");

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
//                    System.out.println(i+" : " + position);
                        treeMap.put(position,treeMap.get(position)+1);
                        counter++;
                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap);
                System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
                System.out.println("Liczba liczb: " + counter);
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
//                    System.out.println(i+" : " + position);
                        treeMap.put(position,treeMap.get(position)+1);
                        counter++;
                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap);
                System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
                System.out.println("Liczba liczb: " + counter);
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
//                    System.out.println(i+" : " + position);
                        treeMap.put(position,treeMap.get(position)+1);
                        counter++;
                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap);
//                System.out.println("Czas obliczen: " + estimatedTime/1_000_000_000.0 + " sec");
                System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
                System.out.println("Liczba liczb: " + counter);
                calculateEntropy(m,treeMap,counter);
                calculateMediumSquareError(m,treeMap,counter);
                break;
            }
            default: break;
        }


    }
    public static void calculateEntropy(int m, Map<Long, Integer> treeMap, int n){
        float sum=0;
        for(long i =0; i<m;i++){
            float pi = (float)treeMap.get(i)/(float)n;
//               System.out.println("Dla "+ i+ " : " + pi);
            if(pi!=0)
                sum+=(float)pi*(float)Math.log10((float)pi);
            else
                sum+=(float)0;
//               System.out.println("Dla " + i + " suma : " + sum );
        }
        float E = -1 * (float)sum;
        float E_star = (float) (-1 * Math.log10(1/(float)m));
        double E_star_minus_E = (double)(E_star-E);
        System.out.printf("E= %.5f  |  E*= %.5f  |  E*-E= %.5f \n", E, E_star, E_star_minus_E);
//                System.out.println("E= " + E + " E*= " + E_star+ " E*-E = " + E_star_minus_E);
    }
    public static void calculateMediumSquareError(int m, Map<Long, Integer> treeMap, int n){
        float sum=0, tmp=0;

        for(long i =0; i<m;i++){
            float pi = (float) treeMap.get(i)/n;
//               System.out.println("Dla "+ i+ " : " + pi);
            tmp = pi-(float) (1/(float)m);
            sum+=(float) Math.pow(tmp,2);
//            System.out.println("Suma: " + sum);
//               System.out.println("Dla " + i + " suma : " + sum );
        }
        float mse = ((1/(float)m) * sum);
        System.out.printf("MSE= %.8f \n",mse );

//        System.out.printf("E= %.5f  |  E*= %.5f  |  E*-E= %.5f \n", E, E_star, E_star_minus_E);
//                System.out.println("E= " + E + " E*= " + E_star+ " E*-E = " + E_star_minus_E);
    }
    public static void printData(Map<Long,Integer> map){
        //     Map<Integer, Integer> treeMap = new TreeMap<>(map);
        for (Map.Entry<Long,Integer> entry : map.entrySet()) {
            System.out.println("Liczba zmapowanych wartosci kluczy: "
                    + entry.getKey()+" : "+entry.getValue());
        }
    }

    public static void simpleHashMSDC(int m){
        System.out.println("started...");
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
                // use comma as separator
                String[] facts = line.split(cvsSplitBy);

                long position = simpleHash(Long.parseLong(facts[1]), m);
                treeMap.put(position,treeMap.get(position)+1);
//                System.out.println("[user_id= " + facts[0] + " , song_id=" + facts[1] + "]");

                lines++;
            }
            long estimatedTime = System.nanoTime() - startTime;
            printData(treeMap);
            System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
            calculateEntropy(m,treeMap,lines);
            calculateMediumSquareError(m,treeMap,lines);

            System.out.println("Ilosc wierszy w pliku: " + lines);
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
                // use comma as separator
                String[] facts = line.split(cvsSplitBy);

                long position = universalHash(Long.parseLong(facts[1]), m);
                treeMap.put(position,treeMap.get(position)+1);
//                System.out.println("[user_id= " + facts[0] + " , song_id=" + facts[1] + "]");

                lines++;
            }
            long estimatedTime = System.nanoTime() - startTime;
            printData(treeMap);
            System.out.printf("Czas obliczen: %.6f sec\n", estimatedTime/1_000_000_000.0 );
            calculateEntropy(m,treeMap,lines);
            calculateMediumSquareError(m,treeMap,lines);

            System.out.println("Ilosc wierszy w pliku: " + lines);
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


}
