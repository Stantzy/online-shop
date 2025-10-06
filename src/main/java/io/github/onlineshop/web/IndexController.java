package io.github.onlineshop.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    private static final Logger log =
            LoggerFactory.getLogger(IndexController.class);

    @GetMapping("")
    public String getIndex() {
        log.info("Called method getIndex");
        return "Index page";
    }
}
