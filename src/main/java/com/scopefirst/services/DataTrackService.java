package com.scopefirst.services;

import com.scopefirst.domain.DataTrack;

import java.util.Set;

public interface DataTrackService {

    Set<DataTrack> getDataTrackAll();

    DataTrack findById(Long l);

    void deleteAll();

    void deleteById(Long l);

    DataTrack saveDataTrack();
}
