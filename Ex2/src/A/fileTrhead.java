package A;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class fileTrhead extends Thread {
    private String fileName;
    private int NumberOfRows;
    public fileTrhead(String fN){
        this.fileName=fN;
        this.NumberOfRows=0;

    }

    @Override
    public void run() {
        Path path = Paths.get(fileName);

        long lines = 0;
        try {
            lines = Files.lines(path).count();

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.NumberOfRows=(int)lines;
    }
    public int getNumberOfRows(){
        return this.NumberOfRows;
    }
}
