package com.cdy.mol.backend.service.user.detail.notification;

import org.codehaus.jettison.json.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface GetNotificationService {
    public JSONObject getList(Integer page);
}
