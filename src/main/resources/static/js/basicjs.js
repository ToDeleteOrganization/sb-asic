
$(document).ready(function() {
    console.log("ready");

    if ($("#redButton")) {
        $("#redButton").on('click', function(event) {
            treatLed($(this));
        });
    }

    if ($("#blueButton")) {
        $("#blueButton").on('click', function(event) {
            treatLed($(this));
        });
    }

    if ($("#motor")) {
        $("#motor").on('click', function(event) {
            var url = $(this).data('command-url');
            var commandName = $(this).data('command-name');

            var speed = $("#speed").val();
            $.ajax({
                method: 'POST',
                url: url + commandName + '/' + speed,
                success: function(response) {
                    console.log("success: " + response);
                }
            });
        });
    }

    if ($("#servo")) {
        $("#servo").on('click', function(event) {
            var url = $(this).data('command-url');
            var commandName = $(this).data('command-name');

            var angle = $("#angle").val();
            $.ajax({
                method: 'POST',
                url: url + commandName + '/' + angle,
                success: function(response) {
                    console.log("success: " + response);
                }
            });
        });
    }

    if ($("#base")) {
        $("#base").on('click', function() {
            var url = $(this).data('command-url');

            $.ajax({
                method: 'POST',
                url: url + '/' + $("#baseValue").val(),
                success: function(response) {
                    console.log("success: " + response);
                }
            });
        });
    }

    function treatLed($button) {
        var url = $button.data('command-url');
        var commandName = $button.data('command-name');
        var state = $button.data('command-state');

        var newCommandName = getState(state);
        $.ajax({
            method: 'POST',
            url: url + commandName + '/' + newCommandName,
            success: function(response) {
                console.log("success: " + response);
                $button.data('command-state', getState(newCommandName));
                $button.html(getState(newCommandName));
            }
        });

    }

    function getState(state) {
        var newCommandName;

        if (state == 'on') {
            newCommandName = '1';

        } else if (state == 'off') {
            newCommandName = '0';

        } else if (state =='0') {
            newCommandName = 'on';

        } else if (state == '1') {
            newCommandName = 'off';
        }
        return newCommandName;
    }

});