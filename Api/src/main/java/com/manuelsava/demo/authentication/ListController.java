package com.manuelsava.demo.authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
public class ListController {

    @PostMapping("/list")
    public List<String> getListItems() {
        return List.of("1", "2", "3");
    }
}
