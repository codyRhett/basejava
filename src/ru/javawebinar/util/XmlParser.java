package ru.javawebinar.util;


import javax.xml.bind.Marshaller;

public class XmlParser {
    private final Marshaller marshaller;

    public XmlParser(Marshaller marshaller) {
        this.marshaller = marshaller;
    }
}
