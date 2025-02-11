package org.joelson.turf.scorecalc.imprt.service;

import org.joelson.turf.scorecalc.imprt.model.UserImport;
import org.joelson.turf.scorecalc.imprt.model.UserImportRepository;
import org.joelson.turf.turfgame.apiv5.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserImportService {

    @Autowired
    UserImportRepository userImportRepository;

    public UserImport getOrCreate(User turfUser) {
        UserImport user = userImportRepository.findById((long) turfUser.getId()).orElse(null);
        if (user == null) {
            return userImportRepository.save(new UserImport((long) turfUser.getId()));
        }
        return user;
    }
}
