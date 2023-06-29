package encryptdecrypt;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @Test
    void encryptionTest() {
        assertEquals(Main.encryption("Welcome to hyperskill!",5),"\\jqhtrj%yt%m~ujwxpnqq&");
    }

    @Test
    void decryptionTest() {
        assertEquals(Main.decryption("\\jqhtrj%yt%m~ujwxpnqq&",5),"Welcome to hyperskill!" );
    }

    @Test
    void encryptionShiftTest() {
        assertEquals(Main.encryptionShift("Welcome to hyperskill!",5),"bjqhtrj yt mdujwxpnqq");
    }

    @Test
    void decryptionShiftTest() {
        assertEquals(Main.decryptionShift("bjqhtrj yt mdujwxpnqq",5),"Welcome to hyperskill!");
    }

    @Test
    void cypherOperationTest() {
        final var map = new HashMap<String, String>();
        map.put("-key", "10");
        map.put("-mode","dec");
        map.put("-alg","shift");
        assertAll(
            ()->assertEquals( Main.cypherOperation(map,"dbokcebo").toString(),"treasure"),
            ()->map.replace("-mode","enc"),
            ()->assertEquals( Main.cypherOperation(map,"treasure").toString(),"dbokcebo"),
            ()-> map.replace("-alg","unicode"),
            ()->assertEquals( Main.cypherOperation(map,"Hello World").toString(),"Rovvy*ay|vn"),
            ()->map.replace("-mode","dec"),
            ()->assertEquals( Main.cypherOperation(map,"Rovvy*ay|vn").toString(),"Hello World")
       );
    }
}