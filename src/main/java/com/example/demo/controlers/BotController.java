package com.example.demo.controlers;
import com.example.demo.dto.BotRequest;
import com.example.demo.dto.BotResponse;
import com.example.demo.dto.BotUpdateRequest;
import com.example.demo.mapper.BotMapper;
import com.example.demo.model.Bot;
import com.example.demo.model.Filter;
import com.example.demo.services.BotService;
import com.example.demo.services.FilterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/api/v1/bots")
public class BotController {
    @Autowired
    private FilterService filterService;
    @Autowired
    private BotService botService;

    @PostMapping
    public ResponseEntity<BotResponse> addBot(@Valid @RequestBody BotRequest botRequest) {
        try {
            Bot bot = BotMapper.toBot(botRequest);
            Bot createdBot = botService.createBot(bot);
            List<Filter> filters = BotMapper.toFilters(createdBot, botRequest.getFilters());
            for (Filter filter : filters) {
                filterService.addFilter(filter);
            }
            BotResponse response = new BotResponse(createdBot.getId(), createdBot.getName(), createdBot.getFilters(), "ACTIVE", createdBot.getCreatedAt());

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<BotResponse> updateBot(
            @PathVariable Long id,
            @Valid @RequestBody BotUpdateRequest request
    ) {
        Bot bot = botService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Бот не найден"));

        if (request.getName() != null &&
                botService.existsByNameAndUser(request.getName(), bot.getOwner().getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Имя бота уже используется");
        }

        botService.updateBot(bot, request);
        return ResponseEntity.ok(BotMapper.toResponse(bot));
    }

}