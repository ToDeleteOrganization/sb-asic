<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template/"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<template:main pageTitle="Hello there" bodyCss="homeBody">

    <%--TODO: add a separate js--%>
    <%--<jsp:attribute command="javascript">--%>
        <%--<script type="text/javascript" src="/js/arduino.js"></script>--%>
    <%--</jsp:attribute>--%>

    <%--Red Button--%>
    <%--<button id="redButton"--%>
            <%--data-command-command="red"--%>
            <%--data-command-state="on"--%>
            <%--data-command-url="/arduino/"--%>
    <%--style="width: 30%;height: 10%;">--%>
        <%--Off--%>
    <%--</button>--%>
    <%--</br>--%>

    <%--BlueButton--%>
    <%--<button id="blueButton"--%>
            <%--data-command-command="blue"--%>
            <%--data-command-state="on"--%>
            <%--data-command-url="/arduino/"--%>
            <%--style="width: 30%;height: 10%;">--%>
        <%--Off--%>
    <%--</button>--%>
    <%--</br>--%>

    <%-- RUNDA 2  de commnetarii--%>
    <%--MotorButton--%>
    <%--<button id="motor"--%>
            <%--data-command-command="motor"--%>
            <%--data-command-url="/arduino/"--%>
            <%--style="width: 30%;height: 10%;">--%>
        <%--0--%>
    <%--</button>--%>
    <%--<input type="text" value="0" id="speed" />--%>

    <%--</br></br></br>--%>

    <%--ServoButton--%>
    <%--<button id="servo"--%>
            <%--data-command-command="servo"--%>
            <%--data-command-url="/arduino/"--%>
            <%--style="width: 30%;height: 10%;">--%>
        <%--0--%>
    <%--</button>--%>
    <%--<input type="text" value="0" id="angle" />--%>


    Transistor Base command
    <button id="base"
            data-command-command="transistor"
            data-command-url="/arduino/"
            style="width: 30%;height: 10%;">
        0
    </button>
    <input type="text" value="0" id="baseValue" />


</template:main>
