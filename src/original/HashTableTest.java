package original;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @Test
    void size() {
    }

    @Test
    void realSize() {
    }

    @Test
    void put() {
        HashTable hashTable = new HashTable();
        Assertions.assertEquals("", hashTable.toString());
        hashTable.put("1", "Tomas Turbao");
        hashTable.put("2", "Elsa Capuntas");
        hashTable.put("1", "Elsa Pato");
        hashTable.put("12", "Koko");
        hashTable.put("12", "VINNNN DISELLLLL");
        hashTable.put("67","Tomas Apuntes");
        Assertions.assertEquals("\n bucket[1] = [1, Elsa Pato] -> [12, VINNNN DISELLLLL] -> [67, Tomas Apuntes]" +
                "\n bucket[2] = [2, Elsa Capuntas]", hashTable.toString());
        System.out.println(hashTable.size());
        Assertions.assertEquals(2, hashTable.size());
    }

    @Test
    void get() {
    }

    @Test
    void drop() {
    }
}