package encryptdecrypt;

import encryptdecrypt.exception.AppFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class AppFileImp implements AppFile {

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Override
    public String read(String fileLocation) {
        StringBuilder fileInputData;
        try (Scanner scannerFile = new Scanner(new File(fileLocation)) ){
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
    public void write(String fileLocation,String outputData) {

        try (FileWriter fileWriter = new FileWriter(fileLocation)) {
            fileWriter.write(outputData);
            logger.info("Save completed");
        } catch (IOException e) {
            throw new AppFileException("Error Saving File");
        }
    }
}
