package app.controller;

import arduino.manager.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BasicController {

    @Autowired
    private BoardService arduinoService;

    @RequestMapping
    public String home(@RequestParam(value = "test", defaultValue = "false") boolean param, Model model) {
        model.addAttribute("test", param);
        return "home";
    }

    @RequestMapping(value = "/arduino-home")
    public String arduinoHome(Model model) {
        // TODO: add a state of the arduino, including the functional state (depending on actual alghorithm/circuit deployed on board)
//        model.addAttribute("arduinoState", rduinoState.getState());
        return "arduinoHome";
    }

    @ResponseBody
    @PostMapping("/arduino/{element}/{command}")
    public String elementStateCommandAction(@PathVariable("element") String element,
                        @PathVariable("command") String state) {
        arduinoService.executeCommand(element.concat(":").concat(state));
        return "ok";
    }

    @ResponseBody
    @PostMapping("/arduino/{command}")
    public String elementStateCommandAction(@PathVariable("command") String command) {
        arduinoService.executeCommand(command);
        return "ok";
    }

}
