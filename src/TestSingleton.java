import ru.javawebinar.model.SectionType;

public class TestSingleton {
    private static TestSingleton instance;// = new TestSingleton();
    public static TestSingleton getOurInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;

    }
    private TestSingleton() {

    }

    public static void main(String[] arg) {
        TestSingleton.getOurInstance().toString();
        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.name());

        SectionType type1 = SectionType.valueOf("PERSONAL");
        System.out.println(type1);

        for (SectionType type : SectionType.values()) {

            System.out.println(type.getTitle());
        }
    }

    public enum Singleton {
        INSTANCE
    }
}
