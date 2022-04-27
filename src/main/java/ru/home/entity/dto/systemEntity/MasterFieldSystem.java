package ru.home.entity.dto.systemEntity;

import java.util.List;

public class MasterFieldSystem {

    Integer masterSystemRelTypeId;
    List<NameIdRisEntity> systemNameAndIdRis;

    public MasterFieldSystem(Integer masterSystemRelTypeId, List<NameIdRisEntity> systemNameAndIdRis) {
        this.masterSystemRelTypeId = masterSystemRelTypeId;
        this.systemNameAndIdRis = systemNameAndIdRis;
    }

    public Integer getMasterSystemRelTypeId() {
        return masterSystemRelTypeId;
    }

    public List<NameIdRisEntity> getSystemNameAndIdRis() {
        return systemNameAndIdRis;
    }
}
