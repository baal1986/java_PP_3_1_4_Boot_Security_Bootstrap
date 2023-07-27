package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/users")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("userInfo", SecurityContextHolder.getContext().getAuthentication());
        return "admin/read";
    }

    @GetMapping("/new")
    public String getUserCreateForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("userInfo", SecurityContextHolder.getContext().getAuthentication());
        return "admin/create";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String getUserEditForm(@PathVariable("id") Long id, Model model) {
        User userById = userService.findById(id);
        model.addAttribute("user", userById);
        model.addAttribute("roles", roleService.getRoles());
        return "admin/read";
    }

    @PutMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}