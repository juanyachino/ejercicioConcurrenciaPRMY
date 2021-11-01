import java.util.Iterator;

public interface ConcurrentMemoryStore {
    /**
     * Almacena un Item asociado a una clave key
     * @throws IllegalArgumentException Si ya existe un valor
     * asociado a la clave
     */
    void store(String key, Item item) throws IllegalArgumentException;
    /**
     * Actualiza los valores del Item asociado a key.
     * La instancia de Item que queda almacenada no debe cambiar.
     **/
    void update(String key, int value1, int value2);
    /**
     * Retorna un iterador sobre los Items contenidos
     */
    Iterator<Item> valueIterator();
    /**
     5
     * Borra el Item con clave key
     */
    void remove(String key);
}

