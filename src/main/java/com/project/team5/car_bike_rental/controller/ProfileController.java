package com.project.team5.car_bike_rental.controller;

import com.project.team5.car_bike_rental.model.UserProfile;
import com.project.team5.car_bike_rental.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @GetMapping("/profile")
    public String viewProfile(@AuthenticationPrincipal User user, Model model) {
        UserProfile userProfile = userProfileRepository.findByUsername(user.getUsername());
        if (userProfile == null) {
            // Handle the case where user profile is not found, maybe redirect to an error page or create a new profile
            return "redirect:/profile/edit";
        }
        model.addAttribute("userProfile", userProfile);
        return "profile/view";
    }

    @GetMapping("/profile/edit")
    public String editProfile(@AuthenticationPrincipal User user, Model model) {
        UserProfile userProfile = userProfileRepository.findByUsername(user.getUsername());
        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUsername(user.getUsername());
        }
        model.addAttribute("userProfile", userProfile);
        return "profile/edit";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@AuthenticationPrincipal User user, UserProfile userProfile) {
        UserProfile existingProfile = userProfileRepository.findByUsername(user.getUsername());
        if (existingProfile != null) {
            userProfile.setId(existingProfile.getId());
        }
        userProfileRepository.save(userProfile);
        return "redirect:/profile";
    }
}
