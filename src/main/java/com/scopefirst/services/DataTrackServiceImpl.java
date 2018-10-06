package com.scopefirst.services;

import com.scopefirst.domain.DataTrack;
import com.scopefirst.repositories.DataTrackRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DataTrackServiceImpl implements DataTrackService {

    private final DataTrackRepository dataTrackRepository;

    public DataTrackServiceImpl(DataTrackRepository dataTrackRepository) {
        this.dataTrackRepository = dataTrackRepository;
    }

    @Override
    public Set<DataTrack> getDataTrackAll() {
        Set<DataTrack> dataTrackSet = new HashSet<>();
        dataTrackRepository.findAll().iterator().forEachRemaining(dataTrackSet::add);
        System.out.println("count of record dataTrack = " + dataTrackSet.size());
        return dataTrackSet;
    }

    @Override
    public DataTrack findById(Long l) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(Long l) {
        dataTrackRepository.deleteById(l);
    }

    @Override
    public DataTrack saveDataTrack() {
        DataTrack dataTrack = new DataTrack();
        java.util.Date fech = new java.util.Date();
        dataTrack.setDateCreated(fech);
        return dataTrackRepository.save(dataTrack);
    }
}
