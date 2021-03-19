import ru.javawebinar.storage.ArrayStorage;
import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.SortedArrayStorage;
import ru.javawebinar.storage.Storage;

import java.util.Arrays;

/**
 * Test for your ArrayStorage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    //static final Storage ARRAY_STORAGE = new ArrayStorage();
    static final SortedArrayStorage SORTED_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("r1");
        Resume r2 = new Resume();
        r2.setUuid("r2");
        Resume r3 = new Resume();
        r3.setUuid("r3");
        Resume r4 = new Resume();
        r4.setUuid("r4");
        Resume r5 = new Resume();
        r5.setUuid("r5");

        //ARRAY_STORAGE.save(r1);
        //ARRAY_STORAGE.save(r2);
        //ARRAY_STORAGE.save(r3);

        SORTED_STORAGE.save(r1);
        SORTED_STORAGE.save(r3);
        SORTED_STORAGE.save(r2);
        SORTED_STORAGE.save(r5);
        SORTED_STORAGE.save(r4);

        printAll();
        //SORTED_STORAGE.sortStorage();
        printAll();


        //System.out.println("Get r1: " + ARRAY_STORAGE.get(r1));
        //System.out.println("Size: " + ARRAY_STORAGE.size());

        //System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        //System.out.println("index of r2 " + Arrays.binarySearch(ARRAY_STORAGE.storage, 0, ARRAY_STORAGE.size(), r2));
        //printAll();
        //ARRAY_STORAGE.delete(r1);
        //printAll();
        //ARRAY_STORAGE.clear();
        //printAll();

        //System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : SORTED_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
