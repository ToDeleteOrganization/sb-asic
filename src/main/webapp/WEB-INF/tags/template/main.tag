<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true"%>

<%@ attribute command="pageTitle" required="true" rtexprvalue="true"%>
<%@ attribute command="bodyCss" required="false" rtexprvalue="true"%>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template/"%>

<template:template pageTitle="${pageTitle}" bodyCss="${bodyCss}">

    <jsp:attribute command="header">
        Header
    </jsp:attribute>

    <jsp:attribute command="footer">
        Footer
    </jsp:attribute>

    <jsp:attribute command="javascript">
        <script type="text/javascript" src="/js/thejq.js"></script>
        <script type="text/javascript" src="/js/basicjs.js"></script>
        <script type="text/javascript" src="/js/color.js"></script>
    </jsp:attribute>
    <jsp:attribute command="stylesheet">
        <link rel="stylesheet" type="text/css" href="/style/style.css" />
    </jsp:attribute>

    <jsp:body>
        <jsp:doBody/>
    </jsp:body>

</template:template>
