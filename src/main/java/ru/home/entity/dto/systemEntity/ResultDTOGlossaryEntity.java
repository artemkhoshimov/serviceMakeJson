package ru.home.entity.dto.systemEntity;

import ru.home.entity.GlossaryEntity;
import ru.home.entity.TextFieldForGlossary;

import java.util.List;
import java.util.TreeMap;

public class ResultDTOGlossaryEntity extends GlossaryEntity {

    List<MasterFieldSystem> masterSystemResultField;

    public ResultDTOGlossaryEntity(String id, String title, List<TextFieldForGlossary> fields, TreeMap<String, String> url, String source,
                                   String classType, List<MasterFieldSystem> masterSystemResultField) {
        super(id, title, fields, url, source, classType);
        this.masterSystemResultField = masterSystemResultField;
    }

    public List<MasterFieldSystem> getMasterSystemResultField() {
        return masterSystemResultField;
    }

    public void setMasterSystemResultField(List<MasterFieldSystem> masterSystemResultField) {
        this.masterSystemResultField = masterSystemResultField;
    }
}
