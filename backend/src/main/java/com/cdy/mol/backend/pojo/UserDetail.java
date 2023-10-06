package com.cdy.mol.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String nickname;
    private String address;
    private String description;
    private String organization;
    private String email;
    private String name;
    private String identification;
    private Integer scoreTotal;
    private Integer scoreAvailable;
}
