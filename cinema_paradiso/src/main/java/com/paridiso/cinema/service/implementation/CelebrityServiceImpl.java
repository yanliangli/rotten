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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MOVIE DOES NOT EXISTS");
        celebrityRepository.deleteById(celebrityId);
    }

    @Override
    public Optional<Celebrity>  updateCelebrity(Celebrity celebrity) {
        Celebrity celebrity1 = celebrityRepository.findById(celebrity.getCelebrityId())
                .orElseThrow(() -> new RuntimeException("CANNOT FIND Celebrity " +celebrity.getCelebrityId()));
        celebrity1.setBiography(celebrity.getBiography());
        celebrity1.setBirthCity(celebrity.getBirthCity());
        celebrity1.setBirthState(celebrity.getBirthState());
        celebrity1.setBirthCountry(celebrity.getBirthCountry());
        celebrity1.setBirthDate(celebrity.getBirthDate());
        celebrity1.setDirector(celebrity.isDirector());
        celebrity1.setFilmography(celebrity.getFilmography());
        celebrity1.setName(celebrity.getName());
        celebrity1.setPHOTO_LOCATION(celebrity.getPHOTO_LOCATION());
        celebrity1.setPhotos(celebrity.getPhotos());
        celebrity1.setProfileImage(celebrity.getProfileImage());
        celebrity1.setProfileImageName(celebrity1.getProfileImageName());

        return Optional.ofNullable(celebrityRepository.save(celebrity1));
    }

    @Override
    public Optional<Celebrity> addCelebrity(Celebrity celebrity) {
        if (celebrityRepository.findCelebrityByCelebrityId(celebrity.getCelebrityId()) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MOVIE EXISTS");
        return Optional.ofNullable(celebrityRepository.save(celebrity));
    }
}
