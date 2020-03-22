<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template/"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<template:main pageTitle="Hello there" bodyCss="homeBody">
    <c:choose>
        <c:when test="${test}">
            Affirmative content
        </c:when>
        <c:otherwise>
            Negative content
        </c:otherwise>
    </c:choose>
</template:main>
