package com.cdy.mol.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Insect {
    @TableId(type = IdType.AUTO)
    private Integer insectId;
    private String insectOrder;
    private String family;
    private String familyCode;
    private String genus;
    private String genusCode;
    private String name;
    private String nameCode;
    private String nameLatin;
    private String environment;
    private String region;
    private String suggest;
    private String url;
}
