package com.meowsauce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章编辑功能中标签的展示以及保存
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelDTO {
    private Integer id;
    private String[] label;
}
