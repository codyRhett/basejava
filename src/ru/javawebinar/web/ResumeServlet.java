package ru.javawebinar.web;

import ru.javawebinar.Config;
import ru.javawebinar.model.*;
import ru.javawebinar.storage.SqlStorage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
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
        String orgName, orgSec, posTitle;

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
            case "addOrganization":
                orgSec = request.getParameter("orgSec");
                r = sqlStorage.get(uuid);

                ((OrganizationSection)r.getSection(SectionType.valueOf(orgSec))).getOrganizations().add(new Organization("Название организации",
                        "Сайт организации",
                        new Organization.Position(1991,
                                Month.JANUARY,1992, Month.JANUARY, "Введите название должности", "Введите описание обязанностей")));

                sqlStorage.update(r);
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
            case "add":
                OrganizationSection organizationSection = new OrganizationSection(new Organization("Название организации", "Сайт организации",
                        new Organization.Position("Введите название должности",
                                "Введите описание обязанностей",
                                LocalDate.of(1,1,1),
                                LocalDate.of(1,1,1))));
                r = new Resume(UUID.randomUUID().toString(), "name");
                r.addSection(SectionType.EXPERIENCE, organizationSection);
                r.addSection(SectionType.EDUCATION, organizationSection);
                sqlStorage.save(r);
                break;
            case "addPosition":
                orgName = request.getParameter("orgName");
                orgSec = request.getParameter("orgSec");
                r = sqlStorage.get(uuid);
                if (orgName != null) {
                    Organization.Position position = new Organization.Position();
                    position.setTitle("Введите название должности");
                    position.setDescription("Введите описание обязанностей");
                    List<Organization> organizations = ((OrganizationSection)r.getSection(SectionType.valueOf(orgSec))).getOrganizations();

                    for(Organization o : organizations) {
                        if (o.getHomePage().getName().equals(orgName)) {
                            o.getPositions().add(position);
                        }
                    }
                }
                sqlStorage.update(r);

                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
            case "deletePosition":
                orgName = request.getParameter("orgName");
                orgSec = request.getParameter("orgSec");
                posTitle = request.getParameter("posTitle");
                r = sqlStorage.get(uuid);
                if (orgName != null) {
                    List<Organization> organizations = ((OrganizationSection)r.getSection(SectionType.valueOf(orgSec))).getOrganizations();

                    for (Organization value : organizations) {
                        if (value.getHomePage().getName().equals(orgName)) {
                            value.getPositions().removeIf(valuePosition -> valuePosition.getTitle().equals(posTitle));
                        }
                    }
                }
                sqlStorage.update(r);
                break;
            case "deleteOrganization":
                orgSec = request.getParameter("orgSec");
                orgName = request.getParameter("orgName");
                r = sqlStorage.get(uuid);
                List<Organization> orgList = ((OrganizationSection)r.getSection(SectionType.valueOf(orgSec))).getOrganizations();
                orgList.removeIf(value -> value.getHomePage().getName().equals(orgName));

                sqlStorage.update(r);
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
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

                    String[] org = request.getParameterValues(String.valueOf(type));

                    List<Organization> organization = new ArrayList<>();
                    List<Organization.Position> position = new ArrayList<>();
                    if (org != null) {
                        for(int i = 0; i < org.length/2; i+=2) {
                            String[] pos = request.getParameterValues(org[i] + "_" + String.valueOf(type));
                            if (pos != null) {
                                for(int j = 0; j < pos.length; j+=4) {
                                    position.add(new Organization.Position(pos[j], pos[j+3], LocalDate.parse(pos[j+1]), LocalDate.parse(pos[j+2])));
                                }
                            }
                            organization.add(new Organization(org[i], org[i+1], position));
                        }
                    }

                    OrganizationSection organizationSection = new OrganizationSection(organization);
                    r.addSection(type, organizationSection);
                    break;
                default:
                    throw new IllegalStateException("type " + type + " is illegal!");
            }
        }
    }
}
