<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true"%>

<%@ attribute name="pageTitle" required="true" rtexprvalue="true"%>
<%@ attribute name="bodyCss" required="false" rtexprvalue="true"%>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template/"%>

<template:template pageTitle="${pageTitle}" bodyCss="${bodyCss}">

    <jsp:attribute name="header">
        Header
    </jsp:attribute>

    <jsp:attribute name="footer">
        Footer
    </jsp:attribute>

    <jsp:attribute name="javascript">
        <script type="text/javascript" src="/js/thejq.js"></script>
        <script type="text/javascript" src="/js/basicjs.js"></script>
        <script type="text/javascript" src="/js/color.js"></script>
    </jsp:attribute>
    <jsp:attribute name="stylesheet">
        <link rel="stylesheet" type="text/css" href="/style/style.css" />
    </jsp:attribute>

    <jsp:body>
        <jsp:doBody/>
    </jsp:body>

</template:template>
