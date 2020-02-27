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
//        hashTable.put("0", "Tomas Turbao");
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
        hashTable.put("11", "Hola guapo");
        hashTable.put("22", "Hola feo");
        hashTable.put("12", "LA PAMPARAAAAA");
        hashTable.put("33", "Esto sa mamao");
        hashTable.put("2", "Pica Chu");

        System.out.println(hashTable);
        System.out.println(hashTable.getCollisionsForKey("3",5));
    }

    @Test
    void get() {
    }

    @Test
    void drop() {
    }
}