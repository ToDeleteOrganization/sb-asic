<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true"%>

<%@ attribute name="pageTitle" required="true" rtexprvalue="true"%>
<%@ attribute name="bodyCss" required="false" rtexprvalue="true"%>

<%@ attribute name="header" fragment="true" required="true" %>
<%@ attribute name="footer" fragment="true" required="false" %>
<%@ attribute name="javascript" fragment="true" required="false" %>
<%@ attribute name="stylesheet" fragment="true" required="false" %>

<html>
    <head>
        <title>${pageTitle}</title>
        <jsp:invoke fragment="javascript"/>
        <jsp:invoke fragment="stylesheet"/>
    </head>

    <body>
        <div class="header">
            <jsp:invoke fragment="header"/>
        </div>

        <div class="${not empty bodyCss ? bodyCss : 'body'}">
            <jsp:doBody/>
        </div>

        <div class="footer">
            <jsp:invoke fragment="footer"/>
        </div>
    </body>

</html>
