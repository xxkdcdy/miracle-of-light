package com.cdy.mol.backend.service.predict.show.record.mark;

import java.util.Map;

public interface AddMRService {
    Map<String, String> add(String photo, String mark, String label);
}
