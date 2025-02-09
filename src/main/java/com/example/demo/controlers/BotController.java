package com.example.demo.controlers;

import com.example.demo.dto.BotListResponse;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bots")
public class BotController {
    @Autowired
    private FilterService filterService;
    @Autowired
    private BotService botService;

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @PostMapping
    public ResponseEntity<BotResponse> addBot(@Valid @RequestBody BotRequest botRequest) {
        try {
            Bot bot = BotMapper.toBot(botRequest);
            Bot createdBot = botService.createBot(bot);
            List<Filter> filters = BotMapper.toFilters(createdBot, botRequest.getFilters());
            for (Filter filter : filters) {
                filterService.addFilter(filter);
            }
            BotResponse response = new BotResponse(createdBot.getId(), createdBot.getName(), createdBot.getFilters(), "ACTIVE", createdBot.getCreatedAt(), createdBot.getCategory());

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBot(@PathVariable Long id) {
        try {
            botService.deleteBotById(id);
            return ResponseEntity.ok("Bot successfully deleted");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bot not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBot(
            @PathVariable Long id,
            @Valid @RequestBody BotUpdateRequest updateRequest) {

        try {
            Bot existingBot = botService.findById(id);

            if (!existingBot.getName().equals(updateRequest.getName())
                    && botService.existsByName(updateRequest.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Bot name already exists");
            }

            existingBot.setName(updateRequest.getName());
            existingBot.setCategory(updateRequest.getCategory());
            existingBot.setWelcomeMessage(updateRequest.getWelcomeMessage());

            filterService.deleteAllByBotId(id);
            List<Filter> newFilters = BotMapper.toFilters(existingBot, updateRequest.getFilters());
            newFilters.forEach(filterService::addFilter);

            Bot updatedBot = botService.updateBot(existingBot);
            BotResponse response = BotMapper.toResponse(updatedBot);

            return ResponseEntity.ok(response);

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<BotListResponse>> getAllBots() {
        List<Bot> bots = botService.findAll();
        List<BotListResponse> response = bots.stream()
                .map(bot -> new BotListResponse(
                        bot.getId(),
                        bot.getName(),
                        bot.getStatus(),
                        bot.getCreatedAt()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}