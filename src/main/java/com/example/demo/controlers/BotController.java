package com.example.demo.controlers;

import com.example.demo.dto.BotRequest;
import com.example.demo.dto.BotResponse;
import com.example.demo.dto.FilterRequest;
import com.example.demo.model.Bot;
import com.example.demo.model.Filter;
import com.example.demo.repository.BotRepository;
import com.example.demo.repository.FilterRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/bots")
public class BotController {

    @Autowired
    private BotRepository botRepository;

    @Autowired
    private FilterRepository filterRepository;

    @PostMapping
    public ResponseEntity<BotResponse> addBot(@Valid @RequestBody BotRequest botRequest) {
        try {
            if (botRepository.existsByName(botRequest.getName())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            Bot bot = Bot.builder()
                    .name(botRequest.getName())
                    .token(botRequest.getToken())
                    .welcomeMessage(botRequest.getWelcomeMessage())
                    .createdAt(new Date())
                    .status("ACTIVE")
                    .build();

            botRepository.save(bot);

            if (botRequest.getFilters() != null) {
                for (FilterRequest filterRequest : botRequest.getFilters()) {
                    Filter filter = Filter.builder()
                            .pattern(filterRequest.getPattern())
                            .action(Collections.singletonList(filterRequest.getAction()))
                            .bot(bot)
                            .build();
                    filterRepository.save(filter);
                }
            }


            BotResponse response = new BotResponse(bot.getId(), bot.getName(), bot.getFilters(), "ACTIVE", bot.getCreatedAt());

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}