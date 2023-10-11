package org.example;

import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<Integer , Integer>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();


        for (int i = 0; i < 1000; i++) {

            Thread thread = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int count = 0;
                int maxCount = 0;
                for (int j = 0; j < route.length(); j++ ){
                    if (route.charAt(j) == 'R'){
                        count += 1;
                    } else if (count > maxCount) {
                        maxCount = count;
                        count = 0;
                    } else {
                        count = 0;
                    }
                }

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(maxCount)){
                        sizeToFreq.put(maxCount, sizeToFreq.get(maxCount) + 1);
                    } else {
                        sizeToFreq.put(maxCount, 1);
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int maxValueKey = Collections.max(sizeToFreq.entrySet(), Map.Entry.comparingByValue()).getKey();

        System.out.println("Самое частое количество повторений " + maxValueKey +  " (встретилось " + sizeToFreq.get(maxValueKey) + " раз)");
        System.out.println("Другие размеры:");

        for (Integer key : sizeToFreq.keySet()) {
            if (key != maxValueKey) {
                System.out.println("- " + key + " (" + sizeToFreq.get(key) + " раз)");
            }
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}