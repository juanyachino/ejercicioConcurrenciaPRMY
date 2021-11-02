import java.util.Iterator;

public class Main {
    public static void main(String[] args){
        ConcurrentMemoryStoreImpl concurrentMemoryStore = new ConcurrentMemoryStoreImpl();
        ThreadLocal<Item> itemLocal = new ThreadLocal<>();
        Item item1 = new Item();
        item1.setValue1(2000);
        item1.setValue2(4000);
        itemLocal.set(item1);
        Item item2 = new Item();
        item2.setValue1(100000);
        item2.setValue2(200000);
        Item item3 = new Item();
        item3.setValue1(8);
        item3.setValue2(16);
        Item item4 = new Item();
        item4.setValue1(16);
        item4.setValue2(32);
        Item item5 = new Item();
        item5.setValue1(32);
        item5.setValue2(64);

        Item theItem = itemLocal.get();
        concurrentMemoryStore.store("item1",theItem);

        System.out.println(Thread.currentThread().getName());
        new Thread("creador"){
                public void run(){
                    System.out.println("Thread: " + getName() + " running");
                    itemLocal.set(item2);
                    Item theItem = itemLocal.get();
                    concurrentMemoryStore.store("item1",theItem);
                    //concurrentMemoryStore.store("item2",item2);
                    concurrentMemoryStore.store("item3",item3);
                }
            }.start();
        new Thread("actualizando"){
            public void run(){
                System.out.println("Thread: " + getName() + " running");
                //concurrentMemoryStore.update("item1",400,7800);
            }
        }.start();
        new Thread("recorriendo"){
            public void run(){
                System.out.println("Thread: " + getName() + " running");
                for (Iterator<Item> it = concurrentMemoryStore.valueIterator(); it.hasNext(); ) {
                    Item item = it.next();
                    System.out.println(item);
                }
            }
        }.start();
        new Thread("borrando y guardando"){
            public void run(){
                System.out.println("Thread: " + getName() + " running");
                concurrentMemoryStore.remove("item1");
                concurrentMemoryStore.store("item4",item4);
            }
        }.start();
        new Thread("recorriendo 2"){
            public void run(){
                System.out.println("Thread: " + getName() + " running");
                for (Iterator<Item> it = concurrentMemoryStore.valueIterator(); it.hasNext(); ) {
                    Item item = it.next();
                    System.out.println(item);
                }
            }
        }.start();
        new Thread("recorriendo 3 y guardando 2"){
            public void run(){
                System.out.println("Thread: " + getName() + " running");
                for (Iterator<Item> it = concurrentMemoryStore.valueIterator(); it.hasNext(); ) {
                    Item item = it.next();
                    System.out.println(item);

                }
                concurrentMemoryStore.store("item5",item5);
            }
        }.start();
        ThreadLocal<NonConcurrentMemoryStoreImpl> localMemoryStore = new ThreadLocal<>();
        new Thread("using not thread-safe store class "){
            public void run(){
                System.out.println("Thread: " + getName() + " running");
                localMemoryStore.set(new NonConcurrentMemoryStoreImpl());
                localMemoryStore.get().store("key",new Item());
                for (Iterator<Item> it = localMemoryStore.get().valueIterator(); it.hasNext(); ) {
                    Item item = it.next();
                    System.out.println(item);

                }
                localMemoryStore.get().remove("key");
            }
        }.start();
        new Thread(" 2 using not thread-safe store class "){
            public void run(){
                System.out.println("Thread: " + getName() + " running");
                localMemoryStore.set(new NonConcurrentMemoryStoreImpl());
                localMemoryStore.get().store("key",new Item());
                for (Iterator<Item> it = localMemoryStore.get().valueIterator(); it.hasNext(); ) {
                    Item item = it.next();
                    System.out.println(item);

                }
                localMemoryStore.get().remove("key");
            }
        }.start();
    }
}
