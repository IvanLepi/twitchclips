package dev.ivanlepi.twitchclips.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import dev.ivanlepi.twitchclips.service.GamelistService;
import dev.ivanlepi.twitchclips.service.Twitch;

@Controller
public class HomeController {
    
    private GamelistService gamelistService;

    // private Twitch twitch;

    // @Autowired
    // public HomeController(Twitch twitch){
    //     this.twitch =twitch;
    // }

    @Autowired
    public HomeController(GamelistService gamelistService){
        this.gamelistService = gamelistService;
    }

    // @GetMapping("/")
    // public String home(Model model) {
    //     model.addAttribute("feed", gamelistService.getGames());
    //     return "home";
    // }

    // @GetMapping("/")
    // public String clipshome(Model model) {
    //     model.addAttribute("clipsfeed", twitch.updateClips("509658"));
    //     return "home";
    // }

    @GetMapping("/")
    public String clipshome(Model model) {
        model.addAttribute("clipsfeed", gamelistService.getBroadcasterClips("71190292"));
        return "clipshome";
    }

}

