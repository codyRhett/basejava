import ru.javawebinar.model.Resume;
import ru.javawebinar.model.Section;
import ru.javawebinar.model.SectionText;
import ru.javawebinar.model.SectionType;
import ru.javawebinar.storage.MapStorage;
import ru.javawebinar.storage.Storage;

public class ResumeTestData {

    protected static MapStorage storage = new MapStorage();

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID = "uuid";

    protected static final String NAME_1 = "sergey";
    protected static final String NAME_2 = "tanya";
    protected static final String NAME_3 = "jack";
    protected static final String NAME = "artem";

    public static void main(String[] args) {
;       storage.save(new Resume(UUID_1, NAME_1));
        storage.save(new Resume(UUID_2, NAME_2));
        storage.save(new Resume(UUID_3, NAME_3));

        Section position = new SectionText("Позиция");
        Section personal = new SectionText("Личные качества");

        storage.get(UUID_1).addSection(SectionType.valueOf("POSITION"), position);
        storage.get(UUID_1).addSection(SectionType.valueOf("PERSONAL"), personal);

        storage.save(new Resume(UUID_3, NAME_3));
    }
}
