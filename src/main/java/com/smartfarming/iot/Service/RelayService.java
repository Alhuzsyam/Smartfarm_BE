package com.smartfarming.iot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartfarming.iot.Data.Model.Relay;
import com.smartfarming.iot.Repository.RelayRepository;

import jakarta.transaction.Transactional;

@Service
public class RelayService {

    @Autowired
    private RelayRepository relayRepository;

    public Relay getRelayByCode(String code) {
        return relayRepository.findByCode(code);
    }
    public Relay saveRelay(Relay relay) {
        return relayRepository.save(relay);
    }
    @Transactional
    public boolean updateRelayValByCode(String code, Boolean val) {
        int updatedRows = relayRepository.updateValByCode(code, val);
        return updatedRows > 0;
    }
}
