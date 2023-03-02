package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Main {


    public static String encryption(String input, int key){
        String result = "";

        char[] characterArray = input.toCharArray();

        char firstCharacter = ' '; // using 'empty space' the first (32)

        char lastCharacter = '~' ; // using '~' has the last (126)

        for (char current: characterArray) {

            if ((current + key ) > lastCharacter){
                int tempKey = key - (lastCharacter - current);
                current = (char) (tempKey + firstCharacter);
            }else{
                current = (char) (current + key);
            }

            result = result + current ;
        }

        return result;
    }
    public static String decryption(String input, int key){
        String result = "";

        char[] characterArray = input.toCharArray();

        char firstCharacter = ' '; // using 'empty space' the first (32)

        char lastCharacter = '~' ; // using '~' has the last (126)

        for (char currentCharater : characterArray){

            if ((currentCharater - key ) < firstCharacter){

                int tempKey = key - (currentCharater - firstCharacter) ;
                currentCharater = (char) ( lastCharacter - tempKey );

            }else{
                currentCharater = (char) (currentCharater - key);
            }

            result = result + currentCharater;
        }

        return result;
    }

    public static String encryptionShift(String input, int key){
        String result ="";
        char[] inputArray = input.toCharArray();

        for (char currentCharacter : inputArray) {

            if(Character.isUpperCase(currentCharacter) || Character.isLowerCase(currentCharacter) ){ // checking if its a valid letter

                char startLetter  =  (Character.isUpperCase(currentCharacter)) ? 'A':'a';
                char endLetter  =  (Character.isUpperCase(currentCharacter)) ? 'Z':'z';

                if((currentCharacter + key) > endLetter){
                    // new Key to use at the beginning of the alphabet
                    int tempKey = key - (endLetter - currentCharacter) - 1;
                    currentCharacter = (char) (tempKey + startLetter);
                }else{
                    currentCharacter = (char) (currentCharacter + key);
                }
            }

            result = result + currentCharacter;
        }
        return  result;
    }

    public static String decryptionShift(String input, int key){
        String result ="";
        char[] inputArray = input.toCharArray();

        for (char currentCharacter : inputArray) {

            if(Character.isUpperCase(currentCharacter) || Character.isLowerCase(currentCharacter) ){ // checking if its a valid letter

                char startLetter  =  (Character.isUpperCase(currentCharacter)) ? 'A':'a';
                char endLetter  =  (Character.isUpperCase(currentCharacter)) ? 'Z':'z';

                if((currentCharacter - key) < startLetter){
                    // (key + (startLetter - currentCharacter) - 1)  == new key to use at the end of the alphabet
                    int tempKey = (key + (startLetter - currentCharacter) -1) ;// key - (endLetter - currentCharacter) - 1;
                    currentCharacter = (char) (endLetter - tempKey );
                }else{
                    currentCharacter = (char) (currentCharacter - key);
                }
            }

            result = result + currentCharacter;
        }
        return  result;
    }



    public static void main(String[] args) {

        Map<String,String> operator = new HashMap<>();

        for (int i = 0; i < args.length; i = i+2) {

            operator.put(args[i],args[i+1]);
        }

        File xFile = new File(operator.get("-in"));

        try(Scanner scannerFile = new Scanner(xFile)) {

            while (scannerFile.hasNext()){
                operator.put("-in",scannerFile.nextLine());
            }
        } catch (FileNotFoundException e) {
            operator.put("-in","");
        }

        String result ="";
        String inputData = operator.getOrDefault("-data", operator.getOrDefault("-in",""));
        int inputKey = Integer.parseInt(operator.getOrDefault("-key","0")) ;

        switch(operator.getOrDefault("-alg","shift")){

            case "shift":

                switch (operator.getOrDefault("-mode","enc")) {

                    case "enc":
                        result = encryptionShift(inputData ,inputKey);
                        break;
                    case "dec":
                        result = decryptionShift(inputData,inputKey);
                        break;
                }

                break;

            case "unicode":

                switch (operator.getOrDefault("-mode","enc")) {
                    case "enc":
                        result = encryption(inputData ,inputKey);
                        break;
                    case "dec":
                        result = decryption(inputData,inputKey);
                        break;
                }
                break;
        }

        try {
            FileWriter fileWriter = new FileWriter(operator.get("-out"));
            fileWriter.write(result);
            fileWriter.close();

        } catch (IOException e) {
            System.out.println(result);
        }

        System.out.println(result);


    }

}
