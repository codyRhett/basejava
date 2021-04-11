import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.ListStorage;
import ru.javawebinar.storage.MapStorage;

public class MainCollection {
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);

    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public static void main(String[] args) {
        MapStorage mStorage = new MapStorage();

        mStorage.save(RESUME_1);
        mStorage.save(RESUME_2);
        mStorage.save(RESUME_3);

        mStorage.save(RESUME_1);

        System.out.println(mStorage.get(UUID_1));
        System.out.println(mStorage.get(UUID_2));
        System.out.println(mStorage.get(UUID_3));


//        ListStorage rStorage = new ListStorage();
//
//        rStorage.save(RESUME_1);
//        rStorage.save(RESUME_2);
//        rStorage.save(RESUME_3);
//        System.out.println("----------------------------");
//        System.out.println(rStorage.get(UUID_1));
//        System.out.println(rStorage.get(UUID_2));
//        System.out.println(rStorage.get(UUID_3));
//        System.out.println("----------------------------");
//        rStorage.update(RESUME_3);
//        System.out.println("----------------------------");
//        System.out.println(rStorage.get(UUID_1));
//        System.out.println(rStorage.get(UUID_2));
//        System.out.println(rStorage.get(UUID_3));
//        System.out.println("----------------------------");
//        Resume[] all = rStorage.getAll();
//
//        System.out.println("----------------------------");
//        if (all.length == 0) {
//            System.out.println("Empty");
//        } else {
//            for (Resume r : all) {
//                System.out.println(r);
//            }
//        }
//        System.out.println("----------------------------");
//        rStorage.delete(RESUME_1);


    }
}
