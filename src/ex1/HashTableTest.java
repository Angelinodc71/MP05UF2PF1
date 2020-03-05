package ex1;

import ex1.HashTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HashTableTest {
    HashTable hashTable = new HashTable();

    @Test
    void size() {
        // 1. La primera prueba consiste en eliminar un
        // elemento que no existe o que es nulo y que el
        // size no se vea afectado.
        hashTable.drop("1119845 778364");

        Assertions.assertEquals(0,hashTable.size());

        // 2. Añadir elementos de forma normal, es decir en
        // diferentes buckets y colisionados.
        hashTable. put("11","Tomas Apuntes");
        hashTable.put("22","Tomas Alvino");
        hashTable.put("33","Tomas Turbao");
        hashTable.put("2","Elsa Capuntas");
        hashTable.put("Armando Casas","12");
        hashTable.put("44", "Tomas Vi-no");

        Assertions.assertEquals(6,hashTable.size());

        // 2.1 Al hacer un put de un elemento que ya
        // existe no se debería sumar porque no es un
        // elemento nuevo sino uno que esta siendo modificado.
        hashTable. put("11","Tomas Notas");

        Assertions.assertEquals(6,hashTable.size());

        // 3. Eliminar elementos de forma normal, es decir
        // en diferentes buckets y colisionados.
        hashTable.drop("11");
        hashTable.drop("22");
        hashTable.drop("33");
        hashTable.drop("2");

        Assertions.assertEquals(2,hashTable.size());

    }

    @Test
    void realSize() {
        // 1. En esta primera voy a probar que en el caso de no haber
        // añadido ningun valor a la hashTable el realSize no varía.
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
        // y en el caso de que haya funcionado bien, el realSize sera 16.
        hashTable.drop("11");

        Assertions.assertEquals(16, hashTable.realSize());
    }

    @Test
    void put() {
        // 1. En la primera prueba quiero probar que en el caso
        // añadir un primer elemento se añada correctamente.
        hashTable.put("Coronavirus","11888");

        Assertions.assertEquals("\n bucket[1] = [Coronavirus, 11888]", hashTable.toString());

        // 2. Probamos a modificar el primer elemento añadido
        hashTable.put("Coronavirus","god");

        Assertions.assertEquals("\n bucket[1] = [Coronavirus, god]", hashTable.toString());

        // 3. Añadimos diferentes elementos con diferentes,
        // hash. Se deberian añadir en diferentes buckets.
        hashTable.put("Hola","guapo");
        hashTable.put("Adios","Feo");

        Assertions.assertEquals("\n bucket[1] = [Coronavirus, god]" +
                "\n bucket[10] = [Adios, Feo]" +
                "\n bucket[12] = [Hola, guapo]",hashTable.toString());

        // 4. Añadiendo diferentes elementos con el mismo
        // hash el resultado deberia ser una colision.
        // Los elementos colisionaran con un elemento ya creado.
        hashTable.put("1","Vin Disell");
        hashTable.put("01","Maik Tagüers");
        hashTable.put("12","Maluma beibe");
        hashTable.put("23","Conejo Bad");

        Assertions.assertEquals("\n bucket[1] = [Coronavirus, god] -> [1, Vin Disell] -> [01, Maik Tagüers] -> [12, Maluma beibe] -> [23, Conejo Bad]" +
                "\n bucket[10] = [Adios, Feo]" +
                "\n bucket[12] = [Hola, guapo]",hashTable.toString());

        // 5. Añadiendo diferentes elementos con el mismo
        // hash el resultado deberia ser una colision.
        // Los elementos colisionaran con un elemento nuevo
        hashTable.put("Coronao","Coronaoooooo");
        hashTable.put("30","Coronaooo");
        hashTable.put("41","Coronaoooo");
        hashTable.put("52","Coronaoooo");

        Assertions.assertEquals("\n bucket[1] = [Coronavirus, god] -> [1, Vin Disell] -> [01, Maik Tagüers] -> [12, Maluma beibe] -> [23, Conejo Bad]" +
                "\n bucket[10] = [Adios, Feo]" +
                "\n bucket[12] = [Hola, guapo]" +
                "\n bucket[13] = [Coronao, Coronaoooooo] -> [30, Coronaooo] -> [41, Coronaoooo] -> [52, Coronaoooo]",hashTable.toString());

        // 6. Y por ultimo probamos a modificar diferentes
        // elementos colisionados, en diferentes buckets y
        // con diferentes indices.

        // 6.1 Probamos a modificar un elemento colisionado
        // en la mitad.
        hashTable.put("30","Coronavirusssss");
        hashTable.put("01","Sakuraaaaa");

        Assertions.assertEquals("\n bucket[1] = [Coronavirus, god] -> [1, Vin Disell] -> [01, Sakuraaaaa] -> [12, Maluma beibe] -> [23, Conejo Bad]" +
                "\n bucket[10] = [Adios, Feo]" +
                "\n bucket[12] = [Hola, guapo]" +
                "\n bucket[13] = [Coronao, Coronaoooooo] -> [30, Coronavirusssss] -> [41, Coronaoooo] -> [52, Coronaoooo]",hashTable.toString());

        // 6.2 Modificar elemenentos colisionados en la
        // primera posicion.
        hashTable.put("Coronavirus","of War");
        hashTable.put("Coronao","Vin Dieses");

        Assertions.assertEquals("\n bucket[1] = [Coronavirus, of War] -> [1, Vin Disell] -> [01, Sakuraaaaa] -> [12, Maluma beibe] -> [23, Conejo Bad]" +
                "\n bucket[10] = [Adios, Feo]" +
                "\n bucket[12] = [Hola, guapo]" +
                "\n bucket[13] = [Coronao, Vin Dieses] -> [30, Coronavirusssss] -> [41, Coronaoooo] -> [52, Coronaoooo]",hashTable.toString());

        // 6.3 Modificar elemenentos colisionados en la
        // ultima posicion.
        hashTable.put("23","Bad Bunny");
        hashTable.put("52","Emosido Engañado");

        Assertions.assertEquals("\n bucket[1] = [Coronavirus, of War] -> [1, Vin Disell] -> [01, Sakuraaaaa] -> [12, Maluma beibe] -> [23, Bad Bunny]" +
                "\n bucket[10] = [Adios, Feo]" +
                "\n bucket[12] = [Hola, guapo]" +
                "\n bucket[13] = [Coronao, Vin Dieses] -> [30, Coronavirusssss] -> [41, Coronaoooo] -> [52, Emosido Engañado]",hashTable.toString());
    }

    @Test
    void get() {
        // 1. La primera prueba consiste en hacer get de un
        // elemento que no ha sido creado o que en este caso es null.
        Assertions.assertNull(hashTable.get("0"));
        Assertions.assertNull(hashTable.get("23"));

        // 2. La segunda prueba crearemos elementos con diferentes
        // valores null y intentaremos hacer get en colisionados,
        // el primer elemento y el ultimo de una lista de elementos colisionados.
        hashTable.put("11","");
        hashTable.put("22","");
        hashTable.put("12","");
        hashTable.put("33","");
        hashTable.put("","");
        hashTable.put("4984375834658634853874536453","halo");

        // 2.1 Get del primer elemento del bucket.
        Assertions.assertEquals("",hashTable.get("11"));

        // 2.2 Get de un elemento colisionado del mismo bucket.
        Assertions.assertEquals("",hashTable.get("22"));

        // 2.3 Get del ultimo elemento de un bucket.
        Assertions.assertEquals("",hashTable.get("33"));

        // 2.4 Get de un elemento que tiene la clave y el valor nulos.
        Assertions.assertEquals("",hashTable.get(""));

        // 3. Hacemos get de un elemento con valores y colisionado.
        Assertions.assertEquals("halo",hashTable.get("4984375834658634853874536453"));
    }

    @Test
        void drop() {
        hashTable. put("11","Tomas Apuntes");
        hashTable.put("22","Tomas Alvino");
        hashTable.put("33","Tomas Turbao");
        hashTable.put("2","Elsa Capuntas");
        hashTable.put("Armando Casas","12");
        hashTable.put("44", "Tomas Vi-no");

        // 1. En la primera prueba vamos ha hacer un drop de
        // un elemento nulo o que en este caso no existe. Para
        // ello vamos a comprobar que tanto el size y la hashTable
        // no se ven modificadas.
        hashTable.drop("874367834872398732967");

        Assertions.assertEquals(6,hashTable.size());
        Assertions.assertEquals("\n bucket[0] = [11, Tomas Apuntes] -> [22, Tomas Alvino] -> [33, Tomas Turbao] -> [44, Tomas Vi-no]" +
                "\n bucket[2] = [2, Elsa Capuntas]" +
                "\n bucket[11] = [Armando Casas, 12]",hashTable.toString());

        // 2. En esta prueba voy a comprobar que al borrar
        // el primer elemento de la tabla no se borran los demás.
        hashTable.drop("11");

        Assertions.assertEquals(5,hashTable.size());
        Assertions.assertEquals("\n bucket[0] = [22, Tomas Alvino] -> [33, Tomas Turbao] -> [44, Tomas Vi-no]" +
                "\n bucket[2] = [2, Elsa Capuntas]" +
                "\n bucket[11] = [Armando Casas, 12]",hashTable.toString());

        // 3. Borrar un elemento que no esta colisionado.
        hashTable.drop("2");

        Assertions.assertEquals(4,hashTable.size());
        Assertions.assertEquals("\n bucket[0] = [22, Tomas Alvino] -> [33, Tomas Turbao] -> [44, Tomas Vi-no]" +
                "\n bucket[11] = [Armando Casas, 12]",hashTable.toString());

        // 4. Borramos un elemento colisionado que esta entre
        // el primer y ultimo elemento de la tabla.
        hashTable.put("3","Elsa Pato");
        hashTable.put("55","Tomas Apuntes");
        hashTable.drop("44");

        Assertions.assertEquals(5,hashTable.size());
        Assertions.assertEquals("\n bucket[0] = [22, Tomas Alvino] -> [33, Tomas Turbao] -> [55, Tomas Apuntes]" +
                "\n bucket[3] = [3, Elsa Pato]" +
                "\n bucket[11] = [Armando Casas, 12]",hashTable.toString());

        // 5. Borramos un elemento colisionado en la
        // ultima posicion.
        hashTable.drop("55");

        Assertions.assertEquals(4,hashTable.size());
        Assertions.assertEquals("\n bucket[0] = [22, Tomas Alvino] -> [33, Tomas Turbao]" +
                "\n bucket[3] = [3, Elsa Pato]" +
                "\n bucket[11] = [Armando Casas, 12]",hashTable.toString());

        // Lo importante de estos casos de prueba es tener claro que
        // hay en la hastTable porque al hacer un drop y volver a añadir
        // los elementos cambian de orden.
    }
}