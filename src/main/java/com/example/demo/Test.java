package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/")
public class Test {
    @GetMapping
    public String Health() {
        Logger logger = LogManager.getLogger("CONSOLE_JSON_APPENDER");
        logger.info("Info message");
        logger.error("Error message");

        Logger logger2 = LogManager.getLogger("CONSOLE_JSON_APPENDER2");
        logger2.info("Info message2");
        logger2.error("Error message2");
        return "Health Checks!";
    }
}