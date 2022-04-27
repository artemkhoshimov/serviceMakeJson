package ru.home.entity.dto.manyFilterResultEntity;

import ru.home.entity.StreamOwnerProjectEntity;
import ru.home.entity.SystemEntity;
import ru.home.entity.dto.ownerStreamEntity.TextField;

import java.util.List;

public class ResultEntityDTOForManyFilter {

    String id;
    String title;
    List<TextField> fields;
    String url;
    String source;
    String classType;
    String ownerStreamName;
    List<SystemEntity> systemEntityList;
    List<StreamOwnerProjectEntity> streamOwnerProjectEntityList;




}
