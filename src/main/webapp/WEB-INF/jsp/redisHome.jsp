<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template/"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<template:main pageTitle="Hello there" bodyCss="homeBody">

    <c:choose>
        <c:when test="${not empty keys}">
            <c:forEach items="${keys}" var="key">
                ${key} <br/>
            </c:forEach>
        </c:when>
        <c:otherwise>
            otherwise
        </c:otherwise>
    </c:choose>

</template:main>
