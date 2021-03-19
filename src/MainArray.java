import ru.javawebinar.storage.ArrayStorage;
import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.SortedArrayStorage;
import ru.javawebinar.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Interactive test for ArrayStorage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private final static SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume resume;
        while (true) {
            System.out.print("Введите одну из команд - (list | save uuid | delete uuid | get uuid | clear | update | sort | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 2) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            if (params.length == 2) {
                uuid = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    resume = new Resume();
                    resume.setUuid(uuid);
                    ARRAY_STORAGE.save(resume);
                    printAll();
                    break;
                case "delete":
                    resume = new Resume();
                    resume.setUuid(uuid);
                    ARRAY_STORAGE.delete(resume);
                    printAll();
                    break;
                case "get":
                    resume = new Resume();
                    resume.setUuid(uuid);
                    System.out.println(ARRAY_STORAGE.get(resume));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                case "update":
                    resume = new Resume();
                    resume.setUuid(uuid);
                    ARRAY_STORAGE.update(resume);
                    break;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        Resume[] all = ARRAY_STORAGE.getAll();
        System.out.println("----------------------------");
        if (all.length == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}