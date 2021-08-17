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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h2>
    <h3>Контакты:</h3>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.model.ContactsType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
            <br/>
        </c:forEach>
    </p>
    <h3>Секции:</h3>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sectionsAll}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.model.SectionType, ru.javawebinar.model.AbstractSection>"/>
            <c:set var="secType" scope="session" value="${sectionEntry.key}"/>
            <b>${secType.toHtml()}</b>
            <c:choose>
                <c:when test="${secType==SectionType.PERSONAL || secType==SectionType.POSITION}">
                    ${sectionEntry.value}
                </c:when>
                <c:when test="${secType==SectionType.ACHIEVEMENT || secType==SectionType.QUALIFICATION}">
                    <c:set var="section" value="${sectionEntry.value}"/>
                    <jsp:useBean id="section"
                                 type="ru.javawebinar.model.ListSection"/>
                    <br/>
                    <c:set var="p" value="- "/>
                    <c:forEach var="sectionEntry" items="${section.items}">
                        ${p}${sectionEntry.toString()}
                        <br/>
                    </c:forEach>
                </c:when>

<%--                <c:when test="${secType==SectionType.EDUCATION || secType==SectionType.EXPERIENCE}">--%>
<%--                    <c:set var="sectionOrg" value="${sectionEntry.value}"/>--%>
<%--                    <jsp:useBean id="sectionOrg"--%>
<%--                                 type="ru.javawebinar.model.OrganizationSection"/>--%>
<%--                    <br/>--%>
<%--                    <c:forEach var="sectionEntry" items="${sectionOrg.organizations}">--%>
<%--                        ${sectionEntry.toString()}--%>
<%--                        <br/>--%>
<%--                    </c:forEach>--%>
<%--                </c:when>--%>

            </c:choose>
            <br/>
            <br/>
        </c:forEach>
    </p>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
