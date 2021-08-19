package ru.javawebinar.web;

import ru.javawebinar.Config;
import ru.javawebinar.model.*;
import ru.javawebinar.storage.SqlStorage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
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
        Map<String, String[]> map = request.getParameterMap();

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
                OrganizationSection organizationSection1;
                if (Objects.equals(orgSec, String.valueOf(SectionType.EXPERIENCE))) {
                    organizationSection1 = (OrganizationSection)r.getSection(SectionType.EXPERIENCE);
                } else {
                    organizationSection1 = (OrganizationSection)r.getSection(SectionType.EDUCATION);
                }

                organizationSection1.getOrganizations().add(new Organization("name", "url", new Organization.Position(1991, Month.JANUARY,1992, Month.JANUARY, "title", "decription")));
                sqlStorage.update(r);
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
            case "addPosition":
                orgName = request.getParameter("orgName");
                orgSec = request.getParameter("orgSec");
                r = sqlStorage.get(uuid);
                if (orgName != null) {
                    OrganizationSection organizationSection;
                    if (Objects.equals(orgSec, String.valueOf(SectionType.EXPERIENCE))) {
                        organizationSection = (OrganizationSection)r.getSection(SectionType.EXPERIENCE);
                    } else {
                        organizationSection = (OrganizationSection)r.getSection(SectionType.EDUCATION);
                    }

                    Organization.Position position = new Organization.Position();
                    position.setTitle("Введите название позиции");
                    position.setDescription("Введите описание позиции");
                    List<Organization> organizations = organizationSection.getOrganizations();

                    for(Organization o : organizations) {
                        if (o.getHomePage().getName().equals(orgName)) {
                            o.getPositions().add(position);
                        }
                    }
                } else {

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
                    OrganizationSection organizationSection;
                    if (Objects.equals(orgSec, String.valueOf(SectionType.EXPERIENCE))) {
                        organizationSection = (OrganizationSection)r.getSection(SectionType.EXPERIENCE);
                    } else {
                        organizationSection = (OrganizationSection)r.getSection(SectionType.EDUCATION);
                    }

                    List<Organization> organizations = organizationSection.getOrganizations();


                    for (Iterator<Organization> iterator = organizations.iterator(); iterator.hasNext(); ) {
                        Organization value = iterator.next();
                        if (value.getHomePage().getName().equals(orgName)) {
                            for (Iterator<Organization.Position> positionIterator = value.getPositions().iterator(); positionIterator.hasNext(); ) {
                                Organization.Position valuePosition= positionIterator.next();
                                if (valuePosition.getTitle().equals(posTitle)) {
                                    positionIterator.remove();
                                }
                            }
                        }
                    }
                } else {

                }
                sqlStorage.update(r);
                break;
            case "deleteOrganization":
                orgSec = request.getParameter("orgSec");
                orgName = request.getParameter("orgName");
                r = sqlStorage.get(uuid);
                OrganizationSection organizationSection2;
                if (Objects.equals(orgSec, String.valueOf(SectionType.EXPERIENCE))) {
                    organizationSection2 = (OrganizationSection)r.getSection(SectionType.EXPERIENCE);
                } else {
                    organizationSection2 = (OrganizationSection)r.getSection(SectionType.EDUCATION);
                }

                List<Organization> orgList = organizationSection2.getOrganizations();
                for (Iterator<Organization> iterator = orgList.iterator(); iterator.hasNext(); ) {
                    Organization value = iterator.next();
                    if (value.getHomePage().getName().equals(orgName)) {
                        iterator.remove();
                    }
                }

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
        Map<String, String[]> map = request.getParameterMap();
        String position = request.getParameter("position");
        if (position != null) {
            // add Position to Organization
            int t = 0;
            response.sendRedirect("resume");
        } else {
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
                    String[] str = request.getParameterValues(type.getTitle());
//                    if (value != null) {
//                        String
//                    }
                    Map<String, String[]> map = request.getParameterMap();
                    int t = 0;
                    break;
                default:
                    //throw new IllegalStateException("type " + type + " is illegal!");
            }
        }
    }
}
