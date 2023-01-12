package A;

import org.junit.jupiter.api.Test;

import static A.Ex2_1.createTextFiles;
import static A.Ex2_1.getNumOfLines;
import static org.junit.jupiter.api.Assertions.*;

class Ex2_1Test {

    @Test
    void test1() {

        Ex2_1 ex = new Ex2_1();
        String[] arr;
        arr=Ex2_1.createTextFiles(2000,1,100000);
        assertEquals(arr.length,2000);
        System.out.println("finish test createTextFiles");



        int x = getNumOfLines(arr);
        assertEquals(x,99896879);


        System.out.println("finish test getNumOfLines");


        x = ex.getNumOfLinesThreads(arr);
        assertEquals(x,99896879);


        System.out.println("finish test getNumOfLinesThreads");


        x = ex.getNumOfLinesThreadPool(arr);
        assertEquals(x,99896879);
        System.out.println("finish test getNumOfLinesThreadPool");



    }
}