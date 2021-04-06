import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.ListStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        ListStorage rStorage = new ListStorage();

        rStorage.save(RESUME_1);
        rStorage.save(RESUME_2);
        rStorage.save(RESUME_3);ч

        System.out.println(rStorage.get(UUID_1));
        System.out.println(rStorage.get(UUID_2));
        System.out.println(rStorage.get(UUID_3));

        rStorage.update(RESUME_3);

        System.out.println(rStorage.get(UUID_1));
        System.out.println(rStorage.get(UUID_2));
        System.out.println(rStorage.get(UUID_3));

    }
}
