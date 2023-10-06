package com.cdy.mol.backend.service.impl.predict.data;

import com.alibaba.fastjson.JSONObject;
import com.cdy.mol.backend.mapper.InsectMapper;
import com.cdy.mol.backend.pojo.Insect;
import com.cdy.mol.backend.service.predict.data.GetAllPDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllPDServiceImpl implements GetAllPDService {
    @Autowired
    private InsectMapper insectMapper;
    @Override
    public JSONObject getAll() {
        JSONObject resp = new JSONObject();
        List<Insect> records = insectMapper.selectList(null);
        resp.put("records", records);
        return resp;
    }
}
