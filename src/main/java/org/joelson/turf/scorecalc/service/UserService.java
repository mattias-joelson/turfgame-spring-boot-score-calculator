package org.joelson.turf.scorecalc.service;

import org.joelson.turf.scorecalc.model.User;
import org.joelson.turf.scorecalc.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getOrCreate(org.joelson.turf.turfgame.apiv5.User turfUser) {
        User user = userRepository.findById(turfUser.getId()).orElse(null);
        if (user == null) {
            return userRepository.save(new User(turfUser.getId()));
        }
        return user;
    }
}
