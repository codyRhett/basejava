import ru.javawebinar.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("uuid");
        Method method = r.getClass().getDeclaredMethod("toString");
        String str = (String) method.invoke(r);
        System.out.println(str);
    }
}
