import lombok.Data;

import java.math.BigInteger;
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
        int a = ThreadLocalRandom.current().nextInt(0, p);
        int b = ThreadLocalRandom.current().nextInt(1, p);

        int position = ((a*x+b) % p) % m ;

        return position;
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
