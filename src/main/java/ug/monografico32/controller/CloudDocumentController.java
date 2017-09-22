/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.controller;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ug.monografico32.model.AmazonS3Document;
import ug.monografico32.model.aws.URLSignerWrapper;

@Controller
@RequestMapping("/documents")
public class CloudDocumentController {
    
    @Autowired
    private URLSignerWrapper urlSigner;
    
    public CloudDocumentController(URLSignerWrapper urlSigner){
        this.urlSigner = urlSigner;
    }
    
    //NOTE: a redirect to the external url should be in place
    //NOTE: look how to pass model objects between redirects
    @GetMapping("/{key}")
    public String getDocumentUrl(@PathVariable String key) throws IOException, InvalidKeySpecException{
        String url = urlSigner.getURLFor(key);
        System.out.println(url);
        return "redirect:"+url;
    }
    
}
