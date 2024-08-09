package com.project.team5.car_bike_rental.controller;

import com.project.team5.car_bike_rental.model.UserProfile;
import com.project.team5.car_bike_rental.model.Voucher;
import com.project.team5.car_bike_rental.service.UserProfileService;
import com.project.team5.car_bike_rental.service.UserService;
import com.project.team5.car_bike_rental.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private UserService userService;
    @Autowired
    private VoucherService voucherService;
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @GetMapping("/profile")
    public String viewProfile(@AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            logger.error("User is not authenticated");
            return "redirect:/login";
        }

        String username = user.getUsername();
        logger.info("Fetching profile for username: " + username);

        UserProfile userProfile = userProfileService.getUserProfileByUsername(username);
        if (userProfile == null) {
            logger.error("User profile not found for username: " + username);
            return "redirect:/profile/edit";
        }
        com.project.team5.car_bike_rental.model.User userDB = userService.findByUsername(username);
        if (userDB  == null) {
            logger.error("User with username \"" + username + "\" not found.");
            return "redirect:/login";
        }
        logger.info("User roles: " + user.getAuthorities());
        // Fetch vouchers associated with the user
        List<Voucher> vouchers = voucherService.getVouchersByUserId(userDB.getId());
        model.addAttribute("vouchers", vouchers);

        model.addAttribute("userProfile", userProfile);
        return "profile/view";
    }

    @GetMapping("/profile/edit")
    public String editProfile(@AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            logger.error("User is not authenticated");
            return "redirect:/login";
        }

        String username = user.getUsername();
        logger.info("Editing profile for username: " + username);

        UserProfile userProfile = userProfileService.getUserProfileByUsername(username);
        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUsername(username);
            logger.info("Creating new profile for username: " + username);
        }

        model.addAttribute("userProfile", userProfile);
        return "profile/edit";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@AuthenticationPrincipal User user, UserProfile userProfile) {
        if (user == null) {
            logger.error("User is not authenticated");
            return "redirect:/login";
        }

        String username = user.getUsername();
        logger.info("Updating profile for username: " + username);

        UserProfile existingProfile = userProfileService.getUserProfileByUsername(username);
        if (existingProfile != null) {
            userProfile.setId(existingProfile.getId());
        }

        userProfileService.saveUserProfile(userProfile);
        return "redirect:/profile";
    }
}
