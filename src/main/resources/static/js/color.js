

COLOR = {
    initColorPicker: function() {
        var colorPicker = new iro.ColorPicker("#picker", {
            // Set the size of the color picker
            width: 320,
            // Set the initial color to pure red
            color: "#f00"
        });

        colorPicker.on('color:change', function(color) {
            COLOR.sendColor(color.rgb.r, color.rgb.g, color.rgb.b);
            COLOR.displayColors(color.rgb.r, color.rgb.g, color.rgb.b);
        });
    },

    sendColor: function(red, green, blue) {
        $.ajax({
            method: 'POST',
            url: '/color/' + red + '/' + green + '/' + blue,
            success: function(response) {
                console.log("success: " + response);
            }
        });
    },

    displayColors: function(red, green, blue) {
        $("#colors").html(
            "red = " + red +
            ", green = " + green +
            ", blue = " + blue
            );
    }
};




$(document).ready(function() {
    console.log("ready");
    COLOR.initColorPicker();
});