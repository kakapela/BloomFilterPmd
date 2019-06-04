import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Data
public class HashFunctions {
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
