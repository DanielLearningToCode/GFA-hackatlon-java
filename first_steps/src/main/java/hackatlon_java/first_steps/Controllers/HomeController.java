package hackatlon_java.first_steps.Controllers;

import hackatlon_java.first_steps.DTOs.CreateUserDTO;
import hackatlon_java.first_steps.DTOs.LoginRequest;
import hackatlon_java.first_steps.Services.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import hackatlon_java.first_steps.DTOs.QuestionDTO;
import hackatlon_java.first_steps.Services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("")
public class HomeController {

    private AuthenticationManager authenticationManager;
    private MasterService masterService;
    private UserDetailsService userDetailsService;
    private final QuestionService questionService;
    private ArrayList<QuestionDTO> q;


    @Autowired
    public HomeController(AuthenticationManager authenticate, MasterService masterService, @Qualifier("custom") UserDetailsService userDetailsService, QuestionService questionService1, QuestionService questionService) {
        this.authenticationManager = authenticate;
        this.masterService = masterService;
        this.userDetailsService = userDetailsService;
        this.questionService = questionService1;
        q=questionService.getQuestion();
    }

    @GetMapping("index")
    public String home() {
        return "Index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.
                    getUsername(), loginRequest.getPassword()));
        } catch (InternalAuthenticationServiceException | BadCredentialsException e) {
            return "redirect:login";
        }
        //final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        return "redirect:Index" ;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity createUser(@Valid @ModelAttribute CreateUserDTO userDTO) {
        masterService.createUser(userDTO);
        return new ResponseEntity("User created", HttpStatus.OK);
    }

    @GetMapping("/quiz/{id}")
    public String getQuiz(@PathVariable Integer id,Model m) {
        if (id == null){
            id =0;
        }
        m.addAttribute("quizz",q.get((int)id));
        return "quiz";
    }

    @PostMapping("/quiz/{id}")
    public RedirectView getQuiz(@RequestParam Integer id,Model m, int point){
        id ++;
        return new RedirectView("quiz/{id}");
    }
}
