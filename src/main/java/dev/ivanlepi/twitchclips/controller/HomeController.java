package dev.ivanlepi.twitchclips.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import dev.ivanlepi.twitchclips.service.GamelistService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@CrossOrigin(origins = "http://localhost:3000") // TODO change to different port once we have a client
public class HomeController {

    public static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    private GamelistService gamelistService;

    @Autowired
    public HomeController(GamelistService gamelistService) {
        this.gamelistService = gamelistService;
    }

    @GetMapping("/clips")
    public ResponseEntity<Map<String, Object>> findByGameId(@RequestParam(required = false) String game_id,
            @RequestParam(required = false) String broadcaster_id, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "default") String sort){

        try {
            Pageable paging = PageRequest.of(page, size);

            return new ResponseEntity<>(gamelistService.getResponse(game_id, broadcaster_id, paging, sort), HttpStatus.OK);

        } catch (Exception e) {
            if(e.getMessage().matches("NO_CONTENT")){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
        }
    }

}
