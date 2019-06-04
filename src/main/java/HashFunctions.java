import lombok.Data;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class HashFunctions {

    // a,b - a od 1 do p-1, b od 0 do p-1
    //p>m

    public static int universalHash(int x ,int m){

        BigInteger value = new BigInteger(String.valueOf(m));
        int p = value.nextProbablePrime().intValue();
        //bound is p-1, a oraz b losowane z przedzialu
        int a = ThreadLocalRandom.current().nextInt(1, p);
        int b = ThreadLocalRandom.current().nextInt(0, p);

        int position = ((a*x+b) % p) % m ;

        return position;
    }

    public static void universalHash(int numbersRange, int m, int dataSet){
        System.out.println("started...");

        int position,a,b,p;
        Map<Integer, Integer> treeMap = new TreeMap<>();
        for(int i=0; i<10; i++){
            treeMap.put(i,0);
        }
        BigInteger value = new BigInteger(String.valueOf(m));

        //p>m
        p = value.nextProbablePrime().intValue();
        a = ThreadLocalRandom.current().nextInt(1, p);
        b = ThreadLocalRandom.current().nextInt(0, p);
        System.out.println("Wygenerowane wartoœci a: " + a +" | b: "+ b);

        switch(dataSet){
            //Kolejne liczby z zakresu od 0 do 10^8 ? 1
            case 1: {
                long startTime = System.nanoTime();
                for(int i=0; i < numbersRange; i++){
                    position = ((a*i+b) % p) % m ;
//                    System.out.println(i+" : " + position);
                    if(position >= 0 && position <10)
                        treeMap.put(position,treeMap.get(position)+1);
                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap);
                System.out.println("Czas obliczeñ: " + estimatedTime/1_000_000_000.0 + " sec");
                break;
            }
            //parzyste
            case 2: {
                long startTime = System.nanoTime();
                for(int i=0; i < numbersRange; i+=2){

                    position = ((a*i+b) % p) % m ;
//                   System.out.println(i+" : " + position);
                    if(position >= 0 && position <10)
                        treeMap.put(position,treeMap.get(position)+1);

                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap);
                System.out.println("Czas obliczeñ: " + estimatedTime/1_000_000_000.0 + " sec");
                break;
            }
            //nieparzyste
            case 3: {
                long startTime = System.nanoTime();
                for(int i=1; i < numbersRange; i+=2){

                    position = ((a*i+b) % p) % m ;
//                    System.out.println(i+" : " + position);
                    if(position >= 0 && position <10)
                        treeMap.put(position,treeMap.get(position)+1);

                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap);
                System.out.println("Czas obliczeñ: " + estimatedTime/1_000_000_000.0 + " sec");
                break;
            }
            default: break;
        }
    }

    public static int simpleHash(long x, int m){
        return Math.toIntExact(x % m);
    }
    public static void simpleHash(int numbersRange, int m, int dataSet){
        System.out.println("started...");

        int position;
        Map<Integer, Integer> treeMap = new TreeMap<>();
        for(int i=0; i<10; i++){
            treeMap.put(i,0);
        }

        switch(dataSet){
            //Kolejne liczby z zakresu od 0 do 10^8 ? 1
            case 1: {
                long startTime = System.nanoTime();
                for(int i=0; i < numbersRange; i++){
                    position = Math.toIntExact(i % m);
//                    System.out.println(i+" : " + position);
                    if(position >= 0 && position <10)
                        treeMap.put(position,treeMap.get(position)+1);

                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap);
                System.out.println("Czas obliczeñ: " + estimatedTime/1_000_000_000.0 + " sec");

                System.out.println(treeMap.get(0));
                System.out.println(numbersRange);


                double sum=0;
                for(int i =0; i<10;i++){
                double liczba = (double) treeMap.get(i)/numbersRange;
                System.out.println("Dla "+ i+ " : " + liczba);
                sum+=liczba;
                    System.out.println("Suma pdp wynosi: " + sum);
                }
                break;
            }
            //parzyste
            case 2: {
                long startTime = System.nanoTime();
                for(int i=0; i < numbersRange; i+=2){

                    position = Math.toIntExact(i % m);
//                    System.out.println(i+" : " + position);
                    if(position >= 0 && position <10)
                        treeMap.put(position,treeMap.get(position)+1);

                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap);
                System.out.println("Czas obliczeñ: " + estimatedTime/1_000_000_000.0 + " sec");
                break;
            }
            //nieparzyste
            case 3: {
                long startTime = System.nanoTime();
                for(int i=1; i < numbersRange; i+=2){

                    position = Math.toIntExact(i % m);
//                    System.out.println(i+" : " + position);
                    if(position >= 0 && position <10)
                        treeMap.put(position,treeMap.get(position)+1);

                }
                long estimatedTime = System.nanoTime() - startTime;
                printData(treeMap);
                System.out.println("Czas obliczeñ: " + estimatedTime/1_000_000_000.0 + " sec");
                break;
            }
            default: break;
        }


    }

    //print hash map < value, hashedKey >
    public static void printData(Map<Integer,Integer> map){
   //     Map<Integer, Integer> treeMap = new TreeMap<>(map);
        for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
            System.out.println("Liczba zmapowanych wartoœci kluczy: "
                    + entry.getKey()+" : "+entry.getValue());
        }

    }

}
