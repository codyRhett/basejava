package ru.javawebinar.web;

import ru.javawebinar.Config;
import ru.javawebinar.model.*;
import ru.javawebinar.storage.SqlStorage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class ResumeServlet extends HttpServlet {
    SqlStorage sqlStorage;

    @Override
    public void init() {
        sqlStorage = Config.get().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                sqlStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = sqlStorage.get(uuid);
                break;
            case "add":
                r = new Resume();
                break;
            default:
                throw new IllegalStateException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                        ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                )
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullname = request.getParameter("fullname");

        Resume r;
        if (Objects.equals(uuid, "")) {
            // To create new Resume
           uuid = UUID.randomUUID().toString();
           r = new Resume(uuid, fullname);
           addContentsToResume(r, request);
           sqlStorage.save(r);
        } else {
            // to Update old Resume
            r = sqlStorage.get(uuid);
            r.setFullName(fullname);
            addContentsToResume(r, request);
            sqlStorage.update(r);
        }
        response.sendRedirect("resume");
    }

    private void addContentsToResume(Resume r, HttpServletRequest request) {
        for(ContactsType type : ContactsType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for(SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            switch (type) {
                case PERSONAL:
                case POSITION:
                    if (value != null && value.trim().length() != 0) {
                        r.addSection(type, new TextSection(value));
                    } else {
                        r.getSectionsAll().remove(type);
                    }
                    break;
                case QUALIFICATION:
                case ACHIEVEMENT:
                    String[] strArray = value.trim().split("\r\n");
                    List<String> list = new ArrayList<>();
                    for(String s : strArray) {
                        if (!s.trim().equals("")) {
                            list.add(s);
                        }
                    }
                    if (list.isEmpty()) {
                        r.getSectionsAll().remove(type);
                    } else {
                        r.addSection(type, new ListSection(list));
                    }
                    break;
                default:
                    //throw new IllegalStateException("type " + type + " is illegal!");
            }
        }
    }
}
