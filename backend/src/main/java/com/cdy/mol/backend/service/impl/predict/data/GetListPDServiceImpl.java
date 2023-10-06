package com.cdy.mol.backend.service.impl.predict.data;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdy.mol.backend.mapper.InsectMapper;
import com.cdy.mol.backend.pojo.Insect;
import com.cdy.mol.backend.service.predict.data.GetListPDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListPDServiceImpl implements GetListPDService {
    @Autowired
    private InsectMapper insectMapper;
    @Override
    public JSONObject getList(Integer page) {
        IPage<Insect> recordIpage = new Page<>(page, 4);
        List<Insect> records = insectMapper.selectPage(recordIpage, null).getRecords();
        JSONObject resp = new JSONObject();
        resp.put("records", records);
        resp.put("records_count", insectMapper.selectCount(null));
        return resp;
    }
}
