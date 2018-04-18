package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.Celebrity;
import com.paridiso.cinema.persistence.CelebrityRepository;
import com.paridiso.cinema.service.CelebrityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier(value = "CelebrityServiceImpl")
public class CelebrityServiceImpl implements CelebrityService {

    @Autowired
    CelebrityRepository celebrityRepository;

    @Override
    public List<Celebrity> getCelebrities() {
        return celebrityRepository.findAll();
    }

    @Override
    public Celebrity getCelebrity(String celebrityId) {
        return celebrityRepository.findCelebrityByCelebrityId(celebrityId);
    }

    @Override
    public void deleteCelebrity(String celebrityId) {
        if (celebrityRepository.findCelebrityByCelebrityId(celebrityId) == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Celebrity DOES NOT EXISTS");
        celebrityRepository.deleteById(celebrityId);
    }

    @Override
    public Optional<Celebrity>  updateCelebrity(Celebrity celebrity) {
        if (celebrityRepository.findCelebrityByCelebrityId(celebrity.getCelebrityId()) == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Celebrity DOES NOT EXIST");
        return Optional.ofNullable(celebrityRepository.save(celebrity));
    }

    @Override
    public Optional<Celebrity> addCelebrity(Celebrity celebrity) {
        if (celebrityRepository.findCelebrityByCelebrityId(celebrity.getCelebrityId()) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Celebrity already EXISTS");
        return Optional.ofNullable(celebrityRepository.save(celebrity));
    }
}
