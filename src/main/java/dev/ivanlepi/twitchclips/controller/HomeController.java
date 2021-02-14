package dev.ivanlepi.twitchclips.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import dev.ivanlepi.twitchclips.service.GamelistService;
import dev.ivanlepi.twitchclips.service.Twitch;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class HomeController {

    public static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    private GamelistService gamelistService;

    private Twitch twitch;

    @Autowired
    public HomeController(GamelistService gamelistService, Twitch twitch) {
        this.gamelistService = gamelistService;
        this.twitch = twitch;
    }

    // Testing the update function
    @GetMapping("/updatedb")
    public String clipshome(Model model) {
        model.addAttribute("clipsfeed", twitch.updateClips("32982"));
        return "home";
    }

    @GetMapping("/clips")
    public ResponseEntity<Map<String, Object>> findByGameId(@RequestParam(required = false) String game_id,
            @RequestParam(required = false) String broadcaster_id, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        try {
            Pageable paging = PageRequest.of(page, size);

            return new ResponseEntity<>(gamelistService.getResponse(game_id, broadcaster_id, paging), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
