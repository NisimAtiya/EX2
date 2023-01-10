package A;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class fileTreadPool implements Callable<Integer> {
    private String fileName;
    public fileTreadPool(String fN){
        this.fileName=fN;
    }
    @Override
    public Integer call(){
        Path path = Paths.get(fileName);

        long lines = 0;
        try {
            lines = Files.lines(path).count();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return (int)lines;
    }
}
