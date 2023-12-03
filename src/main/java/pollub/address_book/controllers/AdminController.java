package pollub.address_book.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pollub.address_book.models.ApplicationUser;
import pollub.address_book.services.User.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String helloAdmineController() {
        return "Admin level access";
    }

    @GetMapping("/users")
    public List<ApplicationUser> getAllUsers() {
        return userService.getAllUsers();
    }
}
