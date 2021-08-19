<%--
  Created by IntelliJ IDEA.
  User: artem
  Date: 15.08.2021
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<html>
<<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.model.ContactsType" %>
<%@ page import="ru.javawebinar.model.SectionType" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullname" size=50 value="${resume.fullName}" required></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactsType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <h4>Секции:</h4>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <c:choose>
                    <c:when test="${type==SectionType.PERSONAL || type==SectionType.POSITION}">
                        <dd>
                            <input type="text" name="${type.name()}" size=30 value="${resume.getSection(type)}">
                        </dd>
                    </c:when>
                    <c:when test="${type==SectionType.ACHIEVEMENT || type==SectionType.QUALIFICATION}">
                        <dd>
                            <c:set var="section" value="${resume.getSection(type)}"/>
                            <c:if test="${section != null}">
                                <jsp:useBean id="section"
                                             type="ru.javawebinar.model.ListSection"/>
                                <br/>
                                <textarea name="${type.name()}" style="width:200px; height:110px;"><c:forEach var="sectionEntry" items="${section.items}">${sectionEntry}
                                    </c:forEach>
                                </textarea>
                            </c:if>
                            <c:if test="${section == null}">
                                <br/>
                                <textarea name="${type.name()}" style="width:200px; height:70px;"></textarea>
                            </c:if>
                        </dd>
                    </c:when>

                    <c:when test="${type==SectionType.EDUCATION || type==SectionType.EXPERIENCE}">
<%--                        <c:if test="${resume.getSection(type) != null}">--%>
                        <dd>
                            <c:set var="sectionOrg" value="${resume.getSection(type)}"/>
                                <jsp:useBean id="sectionOrg"
                                             type="ru.javawebinar.model.OrganizationSection"/>
                            <c:forEach var="organization" items="${sectionOrg.organizations}">
                                <jsp:useBean id="organization"
                                             type="ru.javawebinar.model.Organization"/>
                                <br/>
                                <p style="border: 3px solid #C1FF0A; background-color: #d5d5d1; padding: 10px;">
                                    Данные организации
                                    <br/>
                                    <input type="text" name="${type.name()}" size=30 value="${organization.homePage.name}" required>
                                    <input type="text" name="${type.name()}" size=30 value="${organization.homePage.url}"><td><a type="button" href="resume?uuid=${resume.uuid}&action=deleteOrganization&orgSec=${type.name()}&orgName=${organization.homePage.name}">Удалить организацию</a></td>
                                     <br/>
                                    <c:forEach var="position" items="${organization.positions}">
                                        <br/>
                                        Должность
                                        <br/>
                                        <input type="text" name="${organization.homePage.name}_${type.name()}" size=30 value="${position.title}" required>
                                        <input type="date" name="${organization.homePage.name}_${type.name()}" size=30 value="${position.startDate}" required>
                                        <input type="date" name="${organization.homePage.name}_${type.name()}" size=30 value="${position.endDate}" required><td><a type="button" href="resume?uuid=${resume.uuid}&action=deletePosition&orgSec=${type.name()}&orgName=${organization.homePage.name}&posTitle=${position.title}">Удалить Позицию</a></td>
                                        <br/>
                                        <textarea name="${organization.homePage.name}_${type.name()}" style="width:200px; height:110px;">${position.description}</textarea>
                                        <br/>
                                    </c:forEach>
                                    <td><a type="button" href="resume?uuid=${resume.uuid}&action=addPosition&orgSec=${type.name()}&orgName=${organization.homePage.name}">Добавить Позицию</a></td>
                                </p>
                            </c:forEach>
                            <td><a type="button" href="resume?uuid=${resume.uuid}&action=addOrganization&orgSec=${type.name()}">Добавить организацию</a></td>
                            <br/>
                        </dd>
<%--                        </c:if>--%>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>