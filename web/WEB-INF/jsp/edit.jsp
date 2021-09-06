<%--
  Created by IntelliJ IDEA.
  User: artem
  Date: 15.08.2021
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                                </c:forEach></textarea>
                            </c:if>
                            <c:if test="${section == null}">
                                <br/>
                                <textarea name="${type.name()}" style="width:200px; height:70px;"></textarea>
                            </c:if>
                        </dd>
                    </c:when>

                    <c:when test="${type==SectionType.EDUCATION || type==SectionType.EXPERIENCE}">
                        <dd>
                            <c:if test="${resume.getSection(type) != null}">
                                <c:set var="sectionOrg" value="${resume.getSection(type)}"/>
                                <jsp:useBean id="sectionOrg"
                                             type="ru.javawebinar.model.OrganizationSection"/>
                                <c:forEach var="organization" items="${sectionOrg.organizations}">
                                    <jsp:useBean id="organization"
                                                 type="ru.javawebinar.model.Organization"/>
                                    <p style="border: 3px solid #C1FF0A; background-color: #d5d5d1; padding: 10px;">
                                        Данные организации
                                        <input type="text" name="${type.name()}" size=30 value="__" hidden>
                                        <br/>
                                        <input type="text" name="${type.name()}" size=30 value="${organization.homePage.name}">
                                        <input type="text" name="${type.name()}" size=30 value="${organization.homePage.url}">
                                        <br/>
                                        <c:forEach var="position" items="${organization.positions}">
                                            <jsp:useBean id="position"
                                                         type="ru.javawebinar.model.Organization.Position"/>
                                            <br/>
                                            Должность
                                            <br/>
                                            <input type="text" name="${type.name()}" size=30 value="${position.title}">
                                            <br/>
                                            <input type="date" name="${type.name()}" size=30 value="${position.startDate}">
                                            <input type="date" name="${type.name()}" size=30 value="${position.endDate}">
                                            <br/>
                                            <textarea name="${type.name()}" style="width:200px; height:110px;">${position.description}</textarea>
                                            <br/>
                                        </c:forEach>
                                        <br/>
                                        Должность
                                        <br/>
                                        <input type="text" name="${type.name()}" size=30 value="">
                                        <br/>
                                        <input type="date" name="${type.name()}" size=30 value="">
                                        <input type="date" name="${type.name()}" size=30 value="">
                                        <br/>
                                        <textarea name="${type.name()}" style="width:200px; height:110px;"></textarea>
                                        <br/>
                                    </p>
                                </c:forEach>
                                <p style="border: 3px solid #C1FF0A; background-color: #d5d5d1; padding: 10px;">
                                    Данные организации
                                    <input type="text" name="${type.name()}" size=30 value="__" hidden>
                                    <br/>
                                    <input type="text" name="${type.name()}" size=30 value="">
                                    <input type="text" name="${type.name()}" size=30 value="">
                                    <br/>
                                    <br/>
                                    Должность
                                    <br/>
                                    <input type="text" name="${type.name()}" size=30 value="">
                                    <br/>
                                    <input type="date" name="${type.name()}" size=30 value="">
                                    <input type="date" name="${type.name()}" size=30 value="">
                                    <br/>
                                    <textarea name="${type.name()}" style="width:200px; height:110px;"></textarea>
                                    <br/>
                                </p>
                            </c:if>

                            <c:if test="${resume.getSection(type) == null}">
                                <p style="border: 3px solid #C1FF0A; background-color: #d5d5d1; padding: 10px;">
                                    Данные организации
                                    <input type="text" name="${type.name()}" size=30 value="__" hidden>
                                    <br/>
                                    <input type="text" name="${type.name()}" size=30 value="">
                                    <input type="text" name="${type.name()}" size=30 value="">
                                    <br/>
                                    <br/>
                                    Должность
                                    <br/>
                                    <input type="text" name="${type.name()}" size=30 value="">
                                    <br/>
                                    <input type="date" name="${type.name()}" size=30 value="">
                                    <input type="date" name="${type.name()}" size=30 value="">
                                    <br/>
                                    <textarea name="${type.name()}" style="width:200px; height:110px;"></textarea>
                                    <br/>
                                </p>
                            </c:if>

                        </dd>
                        <br/>
                        <br/>
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