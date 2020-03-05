package ex2;

public class Main {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable();

        // Put some key values.
        for(int i=0; i<30; i++) {
            final String key = String.valueOf(i);
            hashTable.put(key, key);
        }

        // Print the HashTable structure
        // Al hacer la extracción de clase del Main nos pide
        // que el .log haga el paquete privado.
        HashTable.log("****   HashTable  ***");
        HashTable.log(hashTable.toString());
        HashTable.log("\nValue for key(20) : " + hashTable.get("20") );

        // Hacemos refraccion de la clase Main, porque la clase en la que
        // esta no esta utilizando ningun recurso de este Main.
        // Por eso es mejor tener el Main en otro lugar, para separar lo que
        // es dar valores a nuestra hashTable (que es lo que hace este Main),
        // con ttodo lo que contiene la clase HashTable.
    }
}
