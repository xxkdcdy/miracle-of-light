package com.cdy.mol.backend.service.impl.version;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdy.mol.backend.mapper.VersionMapper;
import com.cdy.mol.backend.pojo.Version;
import com.cdy.mol.backend.service.version.LatestVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LatestVersionServiceImpl implements LatestVersionService {
    @Autowired
    private VersionMapper versionMapper;
    @Override
    public JSONObject latestVersion() {
        QueryWrapper<Version> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("max(id) as id");   // 查找id字段的最大值
        Version version = versionMapper.selectOne(queryWrapper);
        JSONObject resp = new JSONObject();
        if (null == version) {
            resp.put("error_message", "查询出错！");
            return resp;
        }
        resp.put("version_message", versionMapper.selectById(version.getId()));
        resp.put("error_message", "success");
        return resp;
    }
}
