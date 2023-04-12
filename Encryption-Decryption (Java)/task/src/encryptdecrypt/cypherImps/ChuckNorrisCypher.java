package encryptdecrypt.cypherImps;

import encryptdecrypt.Cypher;

import java.util.ArrayList;
import java.util.List;

public class ChuckNorrisCypher implements Cypher {

    @Override
    public String encrypt(String rawData) {

        char[] code = rawData.toCharArray();
        StringBuilder res = new StringBuilder();
        StringBuilder newBinary = new StringBuilder();

        String binaryCode;
        for (int i = 0; i < code.length; i++) {
            binaryCode =String.format("%07d", Integer.valueOf(Integer.toBinaryString(code[i])));
            newBinary.append(binaryCode);
        }

        char[] binaryCodeArray = String.valueOf(newBinary).toCharArray(); //

        int n = 0;
        for (; n < binaryCodeArray.length; n++) {
            switch (binaryCodeArray[n]) {
                case '0'-> {
                    String te = " 00 0";
                    res.append(te);
                }
                case '1' -> {
                    String tep = " 0 0";
                    res.append(tep);
                }
            }
            int m = n+1;
            for (; m<binaryCodeArray.length; m++) {
                if( binaryCodeArray[m] == binaryCodeArray[n]) {
                    res.append("0");
                }else{
                    break;
                }
            }
            n=m-1;
        }
        return res.toString();
    }

    @Override
    public String decrypt(String rawData) {
        String[] strings =  rawData.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        int error = 0; // changed to 1 if the input is invalid
        for (int i = 0; i < strings.length; ){ // looping to change to binary
            if (strings[i].matches("0{3,}")) {
                error =1;
                break;
            }
            if (strings[i].equals("0")){
                stringBuilder.append(strings[i+1].replace("0","1"));
                i += 2;
            } else if (strings[i].equals("00")){
                stringBuilder.append(strings[i+1]);
                i += 2;
            }else  {
                i++;
            }
        }
        List<String> arrays = new ArrayList<>();
        int end = Math.min(stringBuilder.length(), 7);
        for (int i = 0; i < stringBuilder.length(); ) {
            String str = stringBuilder.substring(i, end);
            if (end - i != 7) {
                error = 1;
                break;
            }
            arrays.add(str);
            i = end;
            if (stringBuilder.length() < end + 7) {
                end = stringBuilder.length();
            } else {
                end += 7;
            }
        }
       final var result = new StringBuilder();
        if (!arrays.isEmpty() && error ==0 ){
//            System.out.println("Decoded string:");
            arrays.forEach(e -> {
                Long characterObj = Long.valueOf(e, 2);
                long character = characterObj.longValue();
                result.append((char)character);
            });
        }
//        else {
//            System.out.println("Encoded string is not valid.");
//        }
        return result.toString();
    }
}
