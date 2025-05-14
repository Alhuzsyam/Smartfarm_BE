package com.smartfarming.iot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartfarming.iot.Data.Dto.Response;
import com.smartfarming.iot.Service.CustomUserDetailsService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private CustomUserDetailsService userService;

    // Endpoint untuk mengupdate apiKey
    @PutMapping("/updateApiKey/{username}")
    public Response<Object> updateApiKey(@PathVariable String username, @RequestParam String newApiKey) {
        Response<Object> res = new Response<>();

        try {
            boolean isUpdated = userService.updateApiKey(username, newApiKey);
            if (isUpdated) {
                res.setStatus(HttpStatus.OK.toString());
                res.setMessage("API Key updated successfully.");
                res.setPayload(null);
            } else {
                res.setStatus(HttpStatus.NOT_FOUND.toString());
                res.setMessage("User not found.");
                res.setPayload(null);
            }
        } catch (Exception e) {
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            res.setMessage("Failed to update API Key: " + e.getMessage());
            res.setPayload(null);
            e.printStackTrace();
        }

        return res;
    }
}