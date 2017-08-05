/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Administrador
 */
@Controller
public class HomeController {

    @RequestMapping( path = {"/", "/home"}, method = RequestMethod.GET)
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }

   @GetMapping(value = "/login", params = "error")
    public String loginError(@RequestParam("error") String error, Model model){
        model.addAttribute("error", true);
        return "login";
    }

    @GetMapping(value = "/login", params = "logout")
    public String logoutSuccess(@RequestParam("logout") String error, Model model){
        model.addAttribute("logout", true);
        return "login";
    }

}
