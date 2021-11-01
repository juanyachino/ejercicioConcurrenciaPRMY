import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMemoryStoreImpl implements ConcurrentMemoryStore{
    private final Map<String,Item> itemStore = new ConcurrentHashMap<>();

    @Override
    public void store(String key, Item item) throws IllegalArgumentException {
        if (itemStore.containsKey(key)) throw new IllegalArgumentException();
        itemStore.put(key,item);
    }

    @Override
    public void update(String key, int value1, int value2) {
        Item item = itemStore.get(key);
        item.setValue1(value1);
        item.setValue2(value2);
        itemStore.put(key,item);
    }

    @Override
    public Iterator<Item> valueIterator() {
        return itemStore.values().iterator();
    }

    @Override
    public void remove(String key) {
        itemStore.remove(key);
    }
}
