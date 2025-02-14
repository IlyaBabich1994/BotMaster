package com.example.demo.controlers;
import com.example.demo.dto.BotRequest;
import com.example.demo.dto.BotResponse;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
}