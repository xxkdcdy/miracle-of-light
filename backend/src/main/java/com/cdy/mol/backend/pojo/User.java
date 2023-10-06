package com.cdy.mol.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Data注解自动生成equals，tostring，getter，setter等
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // 使用int在Mybatis里会有warning
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String photo;
    private Integer flag;
    private Integer notification;
}

