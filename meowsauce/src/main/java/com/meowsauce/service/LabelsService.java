package com.meowsauce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meowsauce.entity.Labels;

import java.util.Collection;
import java.util.List;

public interface LabelsService extends IService<Labels> {

    List<Labels> getAllLabel();

    String[] getAllLabelByAidToArray(int aid);

    void saveLabelsByAId(int id, String[] label);
}
