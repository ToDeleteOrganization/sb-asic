package app.controller;

import app.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutionException;

// TODO: implement
//@Controller("/redis")
public class RedisBasicController {

    @Autowired
    private RedisService redisService;

    @GetMapping
    public String keys(Model model) throws ExecutionException, InterruptedException {
        model.addAttribute("keys", redisService.findAllKeys().get());
        return "redisHome";
    }
}
