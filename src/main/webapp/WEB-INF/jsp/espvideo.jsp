<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hello ggg</title>
    <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/main.css" rel="stylesheet">
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <script src="/js/app.js"></script>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
    enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<div id="main-content" class="container">
    <div class="row">
        <div class="col-md-6">
            <form class="form-inline">
                <div class="form-group">
                    <label for="textcommand">What is your command?</label>
                    <input type="text" id="textcommand" class="form-control" placeholder="Your command here...">
                </div>
                <button id="send" class="btn btn-default" type="submit">Send</button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table id="conversation" class="table table-striped">
                <thead>
                <tr>
                    <th>Greetings</th>
                </tr>
                </thead>
                <tbody id="greetings">
                </tbody>
            </table>
        </div>
    </div>
    <c:forEach items="${boards}" var="board">
        <button id="camera" data-id="${board}" data-active="false" class="btn btn-default camera">${board}</button>
        <button id="left" data-command="left" data-board="${board}" class="btn btn-default board">&lt;&lt;</button>
        <button id="right" data-command="right" data-board="${board}" class="btn btn-default board">&gt;&gt;</button>
    </c:forEach>

    <div>
        <img id="video" width="850" height="650" src="" />
    </div>
</p>
</body>
</html>