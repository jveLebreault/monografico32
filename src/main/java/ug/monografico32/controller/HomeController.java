/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Administrador
 */
@Controller
@RequestMapping( path = {"/", "/home"})
public class HomeController {
    
    @RequestMapping( method = RequestMethod.GET)
    public String home(){
        return "home";
    }
    
}
