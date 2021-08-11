package ru.javawebinar.web;

import ru.javawebinar.Config;
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

        response.getWriter().write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h2>RESUMES</h2>\n" +
                "\n" +
                "<table style=\"width:40%\">\n" +
                "  <tr>\n" +
                "    <th>UUID</th>\n" +
                "    <th>FullName</th> \n" +
                "  </tr>\n" +
                "  <tr>\n");
        if (uuid != null) {
            Resume r = sqlStorage.get(uuid);
            response.getWriter().write(
                    "    <td>" + r.getUuid() + "</td>\n" +
                    "    <td>" + r.getFullName() + "</td>\n" +
                    "  </tr>\n" +
                    "</table>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>\n");
        } else {
            for (Resume r : sqlStorage.getAllSorted()) {
                response.getWriter().write("<tr>\n" +
                        "    <td>" + r.getUuid() + "</td>\n" +
                        "    <td>" + r.getFullName() + "</td>\n" +
                        "  </tr>\n");
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
