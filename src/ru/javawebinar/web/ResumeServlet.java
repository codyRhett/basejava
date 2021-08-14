package ru.javawebinar.web;

import ru.javawebinar.Config;
import ru.javawebinar.model.ContactsType;
import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.SqlStorage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    SqlStorage sqlStorage;

    @Override
    public void init() {
        sqlStorage = Config.get().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");

        response.getWriter().write("<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                "    <title>Список всех резюме</title>\n" +
                "</head>\n" +
                "<table border=\"1\" cellpadding=\"8\" cellspacing=\"0\">\n" +
                "  <tr>\n" +
                "    <th>FullName</th> \n" +
                "    <th>E-mail</th> \n" +
                "  </tr>\n" +
                "  <tr>\n");
        if (uuid != null) {
            Resume r = sqlStorage.get(uuid);
            response.getWriter().write(
                    "<tr>\n" +
                            "     <td><a href=\"resume?uuid=" + r.getUuid() + "\">" + r.getFullName() + "</a></td>\n" +
                            "     <td>" + r.getContact(ContactsType.MAIL) + "</td>\n" +
                            "</tr>\n");
        } else {
            for (Resume r : sqlStorage.getAllSorted()) {
                response.getWriter().write("<tr>\n" +
                        "     <td><a href=\"resume?uuid=" + r.getUuid() + "\">" + r.getFullName() + "</a></td>\n" +
                        "     <td>" + r.getContact(ContactsType.MAIL) + "</td>\n" +
                        "</tr>\n");
            }

            response.getWriter().write("</table>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>\n");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
