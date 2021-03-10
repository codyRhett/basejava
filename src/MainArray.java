import com.webapp.storage.ArrayStorage;
import com.webapp.model.Resume;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Interactive test for ArrayStorage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private final static ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume resume;
        while (true) {
            System.out.print("Введите одну из команд - (list | save uuid | delete uuid | get uuid | clear | update | exit): ");
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
                    if (!ARRAY_STORAGE.delete(resume)) {
                        System.out.println("Резюме " + uuid + " отсутствует");
                    }
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
                    if (!ARRAY_STORAGE.update(resume)) {
                        System.out.println("Резюме " + uuid + " отсутствует");
                    }
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