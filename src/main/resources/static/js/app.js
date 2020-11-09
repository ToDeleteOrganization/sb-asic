var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect('marius', 'cocoi', function (frame) { // TODO: test BE for entered user
        setConnected(true);
        console.log('Connected: ' + frame);

        // // home monitoring subscription: TODO
        // stompClient.subscribe('/monitoring', function (image) {
        // });
    });
}

function subscribeToCameraButton(button) {
    stompClient.subscribe('/camera/video', function (image) {
        $("#video").attr('src', "data:image/jpg;base64," + JSON.parse(image.body).content);
    }, {boardId: $(button).data("id")});
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    var command = JSON.stringify({"command": $("#textcommand").val(), "boardId": "esp32_1"})
    stompClient.send("/app/camera/video", {}, command);
    // stompClient.send("/app/monitoring", {}, command);
    // home monitoring subscription: TODO
    // stompClient.subscribe('/monitoring', function (image) {
    //     console.log("monitoring subscription - TODO");
    // });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function bindCommands() {
   $( ".board" ).click(function(ev) {
       var command = JSON.stringify({"command": $(this).data("command"), "boardId" : $(this).data("board")});
       stompClient.send("/app/camera/video", {}, command);
   });
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    connect();
    bindCommands();
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( ".camera" ).click(function(ev) { subscribeToCameraButton($(this)); });
});