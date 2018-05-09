package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.Film;
import com.paridiso.cinema.entity.TV;
import com.paridiso.cinema.entity.Trailer;
import com.paridiso.cinema.persistence.TVRepository;
import com.paridiso.cinema.service.TVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.paridiso.cinema.service.UtilityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Qualifier(value = "TVServiceImpl")
public class TVServiceImpl implements TVService {
    @Autowired
    TVRepository tvRepository;

    @Override
    public Page<TV> getNewTVTonight(Pageable pageable){
        return tvRepository.findAllByIsNewTonightTrue(pageable);
    }

    @Override
    public Page<TV> getMostPopularTVOnCP(Pageable pageable){
        return tvRepository.findTop60ByOrderByRatingDesc(pageable);
    }
}
