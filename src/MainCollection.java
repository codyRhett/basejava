import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.ListStorage;
import ru.javawebinar.storage.SortedArrayStorage;

import java.util.*;

public class MainCollection {
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    private final static ListStorage LIST_STORAGE = new ListStorage();

    public static void main(String[] args) {

        LIST_STORAGE.save(RESUME_1);
        LIST_STORAGE.save(RESUME_2);
        LIST_STORAGE.save(RESUME_3);
        System.out.println(LIST_STORAGE.get(UUID_1));

//        Collection<Resume> collection = new ArrayList<>();
//        collection.add(RESUME_1);
//        collection.add(RESUME_2);
//        collection.add(RESUME_3);
//        collection.add(RESUME_4);


//        for(Resume r : collection) {
//            System.out.println(r);
//            if (Objects.equals(r.getUuid(), UUID_1)) {
////                collection.remove(r);
//            }
//        }
//
//        Iterator<Resume> iterator = collection.iterator();
//        while(iterator.hasNext()) {
//            Resume r = iterator.next();
//            System.out.println(r);
//            if (Objects.equals(r.getUuid(), UUID_1)) {
//                iterator.remove();
//            }
//        }
//        System.out.println(collection.toString());
//
//        Map<String, Resume> map = new HashMap<>();
//        map.put(UUID_1, RESUME_1);
//        map.put(UUID_2, RESUME_2);
//        map.put(UUID_3, RESUME_3);
//
//        for (String uuid : map.keySet()) {
//            System.out.println(map.get(uuid));
//        }
//
//        for(Map.Entry<String, Resume> entry : map.entrySet()) {
//            System.out.println(entry.getValue());
//        }

    }
}
