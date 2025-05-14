package com.smartfarming.iot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.smartfarming.iot.Data.Dto.Response;
import com.smartfarming.iot.Data.Model.Relay;
import com.smartfarming.iot.Service.RelayService;

@RestController
@RequestMapping("/api/relay")
public class RelayController {

    @Autowired
    private RelayService relayService;

    @GetMapping("/getByCode")
    public Response<Relay> getRelayByCode(@RequestParam String code) {
        Response<Relay> res = new Response<>();

        try {
            Relay relay = relayService.getRelayByCode(code);

            if (relay != null) {
                res.setStatus(HttpStatus.OK.toString());
                res.setMessage("Relay found");
                res.setPayload(relay);
            } else {
                res.setStatus(HttpStatus.NOT_FOUND.toString());
                res.setMessage("Relay not found");
                res.setPayload(null);
            }
        } catch (Exception e) {
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            res.setMessage("Error: " + e.getMessage());
            res.setPayload(null);
        }

        return res;
    }
    @PostMapping("/save")
    public Response<Relay> saveRelay(@RequestBody Relay relay) {
        Response<Relay> res = new Response<>();

        try {
            Relay savedRelay = relayService.saveRelay(relay);
            res.setStatus(HttpStatus.CREATED.toString());
            res.setMessage("Relay saved successfully");
            res.setPayload(savedRelay);
        } catch (Exception e) {
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            res.setMessage("Error: " + e.getMessage());
            res.setPayload(null);
        }
        System.out.println();

        return res;
    }
    @PutMapping("/updateValByCode")
    public Response<Object> updateRelayVal(@RequestParam String code, @RequestParam Boolean val) {
    Response<Object> res = new Response<>();

    try {
        boolean updated = relayService.updateRelayValByCode(code, val);
        if (updated) {
            res.setStatus(HttpStatus.OK.toString());
            res.setMessage("Relay value updated successfully");
            res.setPayload(null);
        } else {
            res.setStatus(HttpStatus.NOT_FOUND.toString());
            res.setMessage("Relay with specified code not found");
            res.setPayload(null);
        }
    } catch (Exception e) {
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        res.setMessage("Error: " + e.getMessage());
        res.setPayload(null);
    }

    return res;
}
}
