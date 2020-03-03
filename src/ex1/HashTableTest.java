package ex1;

import ex1.HashTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HashTableTest {
    HashTable hashTable = new HashTable();

    @Test
    void size() {
    }

    @Test
    void realSize() {
        // 1. En esta primera voy a probar que en el caso de no haber
        // añadido ningun valor a la hashTable el realSize no varía
        Assertions.assertEquals(16, hashTable.realSize());

        // 2. En esta segunda prueba he probado de añadir diferentes
        // elementos, los cuales estan colisionados y en diferentes buckets
        hashTable.put("11","Tomas Apuntes");
        hashTable.put("22","Tomas Alvino");
        hashTable.put("33","Tomas Turbao");
        hashTable.put("2","Elsa Capuntas");
        hashTable.put("Armando Casas","12");

        Assertions.assertEquals(16, hashTable.realSize());

        // 3. En esta tercera prueba se puede ver que al acceder un
        // elemento en concreto, ya sea colisionado o no el realSize
        // no augmenta.
        hashTable.get("2");
        hashTable.get("22");
        hashTable.get("Armando Casas");

        Assertions.assertEquals(16,hashTable.realSize());

        // 4. Borramos elementos colisionados y elementos normales
        // y en el caso de que haya funcionado bien, el realSize sera 16
        hashTable.drop("11");

        Assertions.assertEquals(16, hashTable.realSize());
    }

    @Test
    void put() {
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
        // 1. La primera prueba consiste en hacer get de un
        // elemento que no ha sido creado o que en este caso es null
        Assertions.assertNull(hashTable.get("0"));
        Assertions.assertNull(hashTable.get("23"));

        // 2. La segunda prueba crearemos elementos con diferentes
        // valores null y intentaremos hacer get en colisionados,
        // el primer elemento y el ultimo de una lista de elementos colisionados
        hashTable.put("11","");
        hashTable.put("22","");
        hashTable.put("12","");
        hashTable.put("33","");
        hashTable.put("","");
        hashTable.put("4984375834658634853874536453","");

        // 2.1 Get del primer elemento del bucket
        Assertions.assertEquals("",hashTable.get("11"));

        // 2.2 Get de un elemento colisionado del mismo bucket
        Assertions.assertEquals("",hashTable.get("22"));

        // 2.3 Get del ultimo elemento de un bucket
        Assertions.assertEquals("",hashTable.get("33"));

        // 2.4 Get de un elemento que tiene la clave y el valor nulos
        Assertions.assertEquals("",hashTable.get(""));
    }

    @Test
    void drop() {
        hashTable.put("11", "");
        hashTable.put("22", "");
        hashTable.put("12", "LA PAMPARAAAAA");
        hashTable.put("33", "");
        hashTable.put("2", "Pica Chu");

        hashTable.get("33");
    }
}