package ru.javawebinar.storage;

import ru.javawebinar.ResumeTestData;
import ru.javawebinar.model.Resume;

import java.util.UUID;

public class TestData {
    public static UUID uuid_1 = UUID.randomUUID();
    public static UUID uuid_2 = UUID.randomUUID();
    public static UUID uuid_3 = UUID.randomUUID();
    public static UUID uuid_4 = UUID.randomUUID();

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;

    static {
        R1 = new Resume(uuid_1.toString(), "sergey");
        //R1 = ResumeTestData.createResume(uuid_1.toString(), "sergey");
        R2 = ResumeTestData.createResume(uuid_2.toString(), "tanya");
        R3 = ResumeTestData.createResume(uuid_3.toString(), "jack");
        R4 = ResumeTestData.createResume(uuid_4.toString(), "Name");
    }
}
