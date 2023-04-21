package com.music.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.music.Repository.UserRepository;
import com.music.models.User;

import jakarta.servlet.http.HttpSession;
@Controller
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    UserController(UserRepository userRepository)
    {
        this.userRepository=userRepository;
    }
    @GetMapping("/Login")
    public String Login(){
        return "Login";
    }

    @GetMapping("/Register")
    public String Register(){
        return "Signup";
    }

    @PostMapping("/home")
    public String Home(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session){
       User u=userRepository.findByEmail(username);
         if(u!=null){
              if(u.getPassword().equals(password)){
                  session.setAttribute("userque", u.getQueue());
                  session.setAttribute("username", u.getUsername());
                  return "redirect:/";
              }
              else{
                return "Login";
              }
         }
         else{
             return "Login";
         }
    }
    


@PostMapping("/signup")
    public String Signup(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("username") String username){
        User u=new User();
        u.setEmail(email);
        u.setPassword(password);
        u.setUsername(username);
        u.setQueue(new ArrayList<String>());
        userRepository.save(u);
        return "Login";
    }

    // @GetMapping("/getUser")
    // public void getUser(){
    //     User u = userRepository.findByEmail("nshashankvarma");
    //     System.out.println("********************************************************************");
    //     System.out.println(u.toString(u));
    //     System.out.println("********************************************************************");      
    // }
}