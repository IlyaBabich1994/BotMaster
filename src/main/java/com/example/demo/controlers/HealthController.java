package com.example.demo.controlers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/api/v1/health")
    public String health() {
        return "<!DOCTYPE html>" +
                "<html lang='ru'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>Сообщение</title>" +
                "<style>" +
                "body { display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; font-family: Arial, sans-serif; background-color: #f0f0f0; }" +
                "h1 { font-size: 24px; color: #333; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h1>Ты думал тут что-то будет?</h1>" +
                "</body>" +
                "</html>";
    }
}
