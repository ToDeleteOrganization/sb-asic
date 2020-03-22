package app.controller;

import app.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.ExecutionException;

@Controller
public class BasicController {

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("test", true);
        return "home";
    }

    @GetMapping("/redis")
    public String keys(Model model) throws ExecutionException, InterruptedException {
        model.addAttribute("keys", redisService.findAllKeys().get());
        return "redisHome";
    }
}
