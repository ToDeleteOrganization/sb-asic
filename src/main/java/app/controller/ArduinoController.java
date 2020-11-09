package app.controller;

import arduino.manager.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("/command")
public class ArduinoController {

    @Autowired
    private BoardService arduinoService;

    @RequestMapping(value = "/color")
    public String color() {
        return "colorPallete";
    }

    @PostMapping("/color/{red}/{green}/{blue}")
    public String setColor(@PathVariable("red") String red,
                           @PathVariable("green") String green,
                           @PathVariable("blue") String blue) {
        arduinoService.executeCommand(String.join(",", red, green, blue) + ",");
        return "ok";
    }

}
