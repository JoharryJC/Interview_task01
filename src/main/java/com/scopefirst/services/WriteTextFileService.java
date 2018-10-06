package com.scopefirst.services;

import com.scopefirst.domain.DataGridObject;

import java.util.List;

public interface WriteTextFileService {
    String writeTextFile (List<DataGridObject> listDataGridObjects);
}
