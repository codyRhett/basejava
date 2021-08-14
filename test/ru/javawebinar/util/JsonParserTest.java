package ru.javawebinar.util;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.model.AbstractSection;
import ru.javawebinar.model.Resume;
import ru.javawebinar.model.TextSection;

import static ru.javawebinar.storage.TestData.R1;

public class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.write(R1);
        System.out.println(json);
        Resume r2 = JsonParser.read(json, Resume.class);
        Assert.assertEquals(R1, r2);
    }

    @Test
    public void write() {
        AbstractSection section1 = new TextSection("person_1");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json,AbstractSection.class);
        Assert.assertEquals(section1, section2);
    }
}