package encryptdecrypt.cypher.impl;

import encryptdecrypt.cypher.Cypher;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ChuckNorrisCypher implements Cypher {
    Logger logger = Logger.getLogger(ChuckNorrisCypher.class.getName());
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
        final StringBuilder result = new StringBuilder();
        if (!arrays.isEmpty() && error ==0 ){
            arrays.forEach(e -> {
                long character = Long.valueOf(e, 2);
                result.append((char)character);

            });
        } else {
            logger.warning("Encoded string is not valid.");
        }
        return result.toString();
    }

    @Override
    public String encrypt(String rawData) {
        char[] code = rawData.toCharArray();
        StringBuilder result = new StringBuilder();
        StringBuilder newBinary = new StringBuilder();

        String binaryCode;
        for (char c : code) {
            binaryCode = String.format("%07d", Integer.valueOf(Integer.toBinaryString(c)));
            newBinary.append(binaryCode);
        }

        char[] binaryCodeArray = String.valueOf(newBinary).toCharArray(); //

        int n = 0;

        for (; n < binaryCodeArray.length; n++) {
            if (binaryCodeArray[n] == '0') {
                String te = " 00 0";
                result.append(te);
            } else if (binaryCodeArray[n] == '1') {
                String tep = " 0 0";
                result.append(tep);
            }
            int m = n+1;
            for (; m<binaryCodeArray.length; m++) {
                if( binaryCodeArray[m] == binaryCodeArray[n]) {
                    result.append("0");
                }else{
                    break;
                }
            }
            n=m-1;
        }
        return result.toString().trim();
    }
}
