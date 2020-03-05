package ex2;

// Original source code: https://gist.github.com/amadamala/3cdd53cb5a6b1c1df540981ab0245479
// Modified by Fernando Porrino Serrano for academic purposes.

import java.util.ArrayList;

public class HashTable {
    private int INITIAL_SIZE = 16;
    private int size = 0;
    private boolean modificado = true;
    private HashEntry[] entries = new HashEntry[INITIAL_SIZE];

    public int size(){
        return this.size;
    }

    public int realSize(){
        return this.INITIAL_SIZE;
    }

    public void put(String key, String value) {
        int hash = getHash(key);
        final HashEntry hashEntry = new HashEntry(key, value);

        if(entries[hash] == null) {
            entries[hash] = hashEntry;
        }
        else {
            HashEntry temp = entries[hash];
            // Al hacer un put de un elemento que ya existe
            // se debería modificar y no borrar el nuevo valor
            // y dejar el anterior, y con esto lo que hago
            // es comparar la key de la hashTable con la
            // que quiero añadir y modifico el valor (es lo único que cambia)
            if (isEquals(key, temp)) {
                temp.value=hashEntry.value;
                // En el caso de estar modificando un valor el
                // size no deberia incrementarse
                modificado = false;
            }
            else {
                temp = getHashEntry(key, hashEntry, temp);
                if (temp == null) return;
                temp.next = hashEntry;
                hashEntry.prev = temp;
            }
        }
        // El size no se iba incrementando, por lo tanto cuando
        // se añadia un elemento es como si no lo hiciera
        if (modificado) {
            size++;
        }
    }

    // Haría una extracción de metodos para este while que se repite en
    // el drop, el unico problema seria que en el put para controlar los
    // key que son iguales y en vez de añadir, modfique utilizo un boolean
    // en el que controlo eso. Y por eso a lo mejor la refaccion por metodos
    // no tendria mucho sentido.
    private HashEntry getHashEntry(String key, HashEntry hashEntry, HashEntry temp) {
        while (temp.next != null) {
            temp = temp.next;
            // Al hacer un put de un elemento colisionado
            // que ya existe se debería modificar y no borrar
            // el nuevo valor y dejar el anterior, y con esto
            // lo que hago es comparar la key de la hashTable con
            // la que quiero añadir y modifico el valor
            if (isEquals(key, temp)) {
                temp.value=hashEntry.value;
                // Con la variable modificado puedo saber cuando
                // estoy añadiendo un key que ya existe y se esta
                // modificando, por lo tanto el size no debería sumar
                modificado = false;
                return null;
            }
        }
        return temp;
    }

    /**
     * Returns 'null' if the element is not found.
     */
    public String get(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {
            HashEntry temp = entries[hash];

            while(!isEquals(key, temp)) {
                // Al intentar hacer un get de una key que no tiene valor y
                // es el ultimo elemento colisionado nos da un NullPointer
                if (temp.next == null) return null;
                temp = temp.next;
            }
            return temp.value;
        }
        return null;
    }

    // La unica manera de hacer un refactor es de lineas que se
    // repiten mucho como por ejemplo esta, que es para ver cuando
    // una key coincide, pues eliminar o modificar una key o valor (dependiendo).
    private boolean isEquals(String key, HashEntry temp) {
        return temp.key.equals(key);
    }

    public void drop(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {

            HashEntry temp = entries[hash];
            // Cuando eliminamos algun elemento, ya se en primera posicion,
            // entre medias borra todoo lo que tiene detras.
            // La forma de arreglarlo es muy parecida a la del put y consiste
            // en eliminar el elemento borrando todas las relaciones que tiene
            // con en anterior y el posterior (.next y .prev), de cierta manera
            // estamos poniendo el valor del .next en el elemento que queremos eliminar.
            if (isEquals(key, temp)) {
                if (temp.next != null) {
                    temp.next.prev = null;                                //esborrar element únic (no col·lissió)
                    entries[hash]=temp.next;
                    size--;
                    return;
                }
            }
            temp = getHashEntry(key, temp);
            if(isaBoolean(temp.next)) temp.next.prev = temp.prev;   //esborrem temp, per tant actualitzem l'anterior al següent
            temp.prev.next = temp.next;                         //esborrem temp, per tant actualitzem el següent de l'anterior
            size--;
        }
    }

    private HashEntry getHashEntry(String key, HashEntry temp) {
        while( !isEquals(key, temp)) {
            temp = temp.next;
        }
        return temp;
    }

    private boolean isaBoolean(HashEntry next) {
        return next != null;
    }

    private int getHash(String key) {
        // piggy backing on java string
        // hashcode implementation.
        // Ya no hay numeros negativos :)
        return Math.abs(key.hashCode()) % INITIAL_SIZE;
    }

    private class HashEntry {
        String key;
        String value;

        // Linked list of same hash entries.
        HashEntry next;
        HashEntry prev;


        public HashEntry(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }

    @Override
    public String toString() {
        int bucket = 0;
        StringBuilder hashTableStr = new StringBuilder();
        for (HashEntry entry : entries) {
            if(entry == null) {
                bucket++;
                continue;
            }
            hashTableStr.append("\n bucket[")
                    .append(bucket)
                    .append("] = ")
                    .append(entry.toString());
            bucket++;
            HashEntry temp = entry.next;
            while(temp != null) {
                hashTableStr.append(" -> ");
                hashTableStr.append(temp.toString());
                temp = temp.next;
            }
        }
        return hashTableStr.toString();
    }

    public ArrayList<String> getCollisionsForKey(String key) {
        return getCollisionsForKey(key, 1);
    }

    public ArrayList<String> getCollisionsForKey(String key, int quantity){
        /*
          Main idea:
          alphabet = {0, 1, 2}

          Step 1: "000"
          Step 2: "001"
          Step 3: "002"
          Step 4: "010"
          Step 5: "011"
           ...
          Step N: "222"

          All those keys will be hashed and checking if collides with the given one.
        * */

        final char[] alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        ArrayList<Integer> newKey = new ArrayList();
        ArrayList<String> foundKeys = new ArrayList();

        newKey.add(0);
        int collision = getHash(key);
        int current = newKey.size() -1;

        while (foundKeys.size() < quantity){
            //building current key
            String currentKey = "";
            for(int i = 0; i < newKey.size(); i++)
                currentKey += alphabet[newKey.get(i)];

            if(!currentKey.equals(key) && getHash(currentKey) == collision)
                foundKeys.add(currentKey);

            //increasing the current alphabet key
            newKey.set(current, newKey.get(current)+1);

            //overflow over the alphabet on current!
            if(newKey.get(current) == alphabet.length){
                int previous = current;
                do{
                    //increasing the previous to current alphabet key
                    previous--;
                    if(previous >= 0)  newKey.set(previous, newKey.get(previous) + 1);
                }
                while (previous >= 0 && newKey.get(previous) == alphabet.length);

                //cleaning
                for(int i = previous + 1; i < newKey.size(); i++)
                    newKey.set(i, 0);

                //increasing size on underflow over the key size
                if(previous < 0) newKey.add(0);

                current = newKey.size() -1;
            }
        }

        return  foundKeys;
    }

    static void log(String msg) {
        System.out.println(msg);
    }
}