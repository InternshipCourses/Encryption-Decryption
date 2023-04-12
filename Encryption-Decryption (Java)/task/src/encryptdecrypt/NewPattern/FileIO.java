package encryptdecrypt.NewPattern;

import encryptdecrypt.exception.AppFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileIO implements IO {
    private String fileLocation;
    public FileIO(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public String read() {
        StringBuilder fileInputData;
        try (Scanner scannerFile = new Scanner(new File(this.fileLocation)) ){
            fileInputData = new StringBuilder();
            while ( scannerFile.hasNext()) {
                fileInputData.append(scannerFile.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new AppFileException("File was not found",e);
            //logger.info("Please prove a valid input!!!");
        }
        return String.valueOf(fileInputData);
    }

    @Override
    public void write(String date) {
        try (FileWriter fileWriter = new FileWriter(this.fileLocation )) {
            fileWriter.write(date);
//            logger.info("Save completed");\
            System.out.println("saved");
        } catch (IOException e) {
            throw new AppFileException("Error Saving File");
        }
    }
}
