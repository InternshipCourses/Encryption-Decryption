package encryptdecrypt.input_output.impl;

import encryptdecrypt.exception.AppFileException;
import encryptdecrypt.input_output.impl.IO;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Logger;

public class FileIO implements IO {
    private final String fileLocation;
    private final Logger logger = Logger.getLogger(FileIO.class.getName());
    public FileIO(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public String read() {
        StringBuilder fileInputData = new StringBuilder();

        String input;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.fileLocation))){
            while ((input = reader.readLine()) != null){
                fileInputData.append(input);
            }
        } catch (FileNotFoundException e) {
            throw new AppFileException("File was not found",e);
        } catch (IOException e) {
            throw new AppFileException("IO Error has occurred ",e);
        }

        return String.valueOf(fileInputData);
    }

    @Override
    public void write(String date) {
        try (FileWriter fileWriter = new FileWriter(this.fileLocation )) {
            fileWriter.write(date);
            logger.info("Save completed");
        } catch (IOException e) {
            throw new AppFileException("Error Saving File",e);
        }
    }
}
