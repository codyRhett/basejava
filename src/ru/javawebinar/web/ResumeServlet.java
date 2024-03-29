package ru.javawebinar.web;

import ru.javawebinar.Config;
import ru.javawebinar.model.*;
import ru.javawebinar.storage.SqlStorage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
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
        Resume r;

        if (action == null) {
            request.setAttribute("resumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

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
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> organization = new ArrayList<>();
                    String[] str = request.getParameterValues(String.valueOf(type));
                    List<String> posList = new ArrayList<>();

                    if (str != null) {
                        for (String s : str) {
                            if (Objects.equals(s, "__")) {
                                // new Organization name found
                                addPositionsToOrgs(posList, organization);
                            } else {
                                posList.add(s);
                            }
                        }
                        addPositionsToOrgs(posList, organization);
                        OrganizationSection organizationSection = new OrganizationSection(organization);
                        r.addSection(type, organizationSection);
                    }
                    break;
                default:
                    throw new IllegalStateException("type " + type + " is illegal!");
            }
        }
    }

    private void addPositionsToOrgs(List<String> posList, List<Organization> organization) {
        List<Organization.Position> position = new ArrayList<>();
        for(int j = 2; j < posList.size(); j += 4) {
            if (!posList.get(j).equals("")) {
                // Position title exists
                String startDate = (!Objects.equals(posList.get(j + 1), "")) ? posList.get(j+1) : "2000-01-01";
                String endDate = (!Objects.equals(posList.get(j + 2), "")) ? posList.get(j+2) : "2000-01-01";
                position.add(new Organization.Position(posList.get(j), posList.get(j+3), LocalDate.parse(startDate), LocalDate.parse(endDate)));
            }
        }
        if (posList.size() != 0) {
            if (!posList.get(0).equals("")) {
                // Organization exists
                organization.add(new Organization(posList.get(0), posList.get(1), position));
            }
        }
        posList.clear();
    }
}
