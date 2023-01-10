package A;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Ex2_1 {

    public static void main(String[] args) throws InterruptedException {

        Ex2_1 ex = new Ex2_1();
        String[] arr;
        System.out.println("Creates the files......");
        arr=createTextFiles(2000,1,100000);
        System.out.println("The number of files created is:1000");

        long startTime = System.nanoTime();
        System.out.println("Runs getNumOfLines...");
        int x = getNumOfLines(arr);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("getNumOfLines took " + duration + " milliseconds");
        System.out.println("The number of lines is:="+x);

        System.out.println("-----------------------------------");

        startTime = System.nanoTime();
        System.out.println("Runs getNumOfLinesThreads...");
        x = ex.getNumOfLinesThreads(arr);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("getNumOfLinesThreads took " + duration + " milliseconds");
        System.out.println("The number of lines is:="+x);

        System.out.println("-----------------------------------");

        startTime = System.nanoTime();
        System.out.println("Runs getNumOfLinesThreadPool...");
        x = ex.getNumOfLinesThreadPool(arr);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("getNumOfLinesThreadPool took " + duration + " milliseconds");
        System.out.println("The number of lines is:="+x);




    }

    /**
     *
     * @param n - A natural number representing the number of text files
     * @param seed - t's basically a magic number that a function uses randomly to generate for multiple rows.
     * If you do the same seed it will toss the exact same numbers
     * @param bound-Range of the toss
     * @return array of the file names
     */
    public static String[] createTextFiles(int n, int seed, int bound){
        String[] temp = new String[n];
        Random rand = new Random(seed);
        for (int i = 0; i < n; i++) {
            try {
                FileWriter f = new FileWriter("file_" + (i + 1) + ".txt");
                PrintWriter outs = new PrintWriter(f);
                int  rows = rand.nextInt(bound);
                for (int j = 0; j < rows; j++) {
                    try {
                        outs.println("World Hello");

                    } catch (Exception e) {
                        System.err.println("Error writing file\n");
                        e.printStackTrace();
                    }
                }
                outs.close();
                f.close();
            }catch (Exception e) {
                System.err.println("An error occurred.");
                e.printStackTrace();
            }
            temp[i]="file_" + (i + 1) + ".txt";
        }
        return temp;
    }

    /**
     *A helper function that receives the name of a file and calculates the number of lines for it
     * @param fileName-A string that is the name of a file
     * @return The number of lines in the file
     */
    private static long numOfLines(String fileName){
        Path path = Paths.get(fileName);

        long lines = 0;
        try {
            lines = Files.lines(path).count();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    /**
     *A function that receives an array of file names and calculates the sum of all lines in all files in your array using treads
     * @param fileNames - An array of file names
     * @return The total number of lines in all the files in the given array
     */
    public static int getNumOfLines(String[] fileNames){
        long sum =0;
        for (int i = 0; i < fileNames.length; i++) {
            sum+=numOfLines(fileNames[i]);
        }
        return (int)sum;
    }
    public  int getNumOfLinesThreads(String[] fileNames){
        int sum =0;
        fileTrhead[] arr = new fileTrhead[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            fileTrhead ft = new fileTrhead(fileNames[i]);
            arr[i]=ft;
            ft.start();
        }
        for (int i = 0; i < arr.length; i++) {
            try {
                arr[i].join();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        for (int i = 0; i < arr.length; i++) {
            sum+=arr[i].getNumberOfRows();
        }
        return sum;
    }
    /**
     *A function that receives an array of filenames and calculates the sum of all lines in all files in your array using pooltread
     * @param fileNames - An array of file names
     * @return The total number of lines in all the files in the given array
     */
    public  int getNumOfLinesThreadPool(String[] fileNames) {
        int cnt=0;
        List<Future<Integer>> list = new ArrayList<Future<Integer>>();
        ExecutorService pool = Executors.newFixedThreadPool(fileNames.length);
        for (int i = 0; i < fileNames.length; i++) {
            Callable callable = new fileTreadPool(fileNames[i]);
            Future<Integer> future = pool.submit(callable);
            list.add(future);
        }
        for(Future<Integer> ft:list){
            try {
                cnt += ft.get();
            }catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        pool.shutdown();
        return cnt;
    }
}
