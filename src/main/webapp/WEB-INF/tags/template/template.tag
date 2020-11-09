<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true"%>

<%@ attribute command="pageTitle" required="true" rtexprvalue="true"%>
<%@ attribute command="bodyCss" required="false" rtexprvalue="true"%>

<%@ attribute command="header" fragment="true" required="true" %>
<%@ attribute command="footer" fragment="true" required="false" %>
<%@ attribute command="javascript" fragment="true" required="false" %>
<%@ attribute command="stylesheet" fragment="true" required="false" %>

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
