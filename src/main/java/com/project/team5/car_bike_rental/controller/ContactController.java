package com.project.team5.car_bike_rental.controller;

import okhttp3.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class ContactController {

    private final String MAILTRAP_API_KEY = "06cbc515d6b294a9fd329dc11a777a86";
    private final String MAILTRAP_URL = "https://sandbox.api.mailtrap.io/api/send/3067458";
    private final String FROM_EMAIL = "mailtrap@example.com";
    private final String FROM_NAME = "Mailtrap Test";

    @GetMapping("/contact")
    public String showContactForm() {
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("subject") String subject,
        @RequestParam("message") String message,
        Model model) {
        
        try {
            sendEmail(name, email, subject, message);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/contact?error=true";
        }

        return "redirect:/contact?success=true";
    }

    @SuppressWarnings("deprecation")
    private void sendEmail(String name, String email, String subject, String message) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = String.format("{\"from\":{\"email\":\"%s\",\"name\":\"%s\"},\"to\":[{\"email\":\"%s\"}],\"subject\":\"%s\",\"text\":\"%s\",\"category\":\"Contact Form\"}",
                FROM_EMAIL, FROM_NAME, email, subject, message);
        RequestBody body = RequestBody.create(mediaType, jsonBody);
        Request request = new Request.Builder()
            .url(MAILTRAP_URL)
            .method("POST", body)
            .addHeader("Authorization", "Bearer " + MAILTRAP_API_KEY)
            .addHeader("Content-Type", "application/json")
            .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
    }
}
