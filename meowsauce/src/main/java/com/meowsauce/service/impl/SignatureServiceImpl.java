package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meowsauce.entity.Signature;
import com.meowsauce.mapper.SignatureMapper;
import com.meowsauce.service.SignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SignatureServiceImpl extends ServiceImpl<SignatureMapper, Signature> implements SignatureService {
    @Autowired
    private SignatureMapper signatureMapper;
    //碎碎念页面获取签名信息
    @Override
    public List<Signature> getAllSignature() {
        List<Signature> signatures = signatureMapper.selectList(null);
        signatures.forEach(e->{
            Date signatureDate = e.getSignatureDate();
            DateFormat dateInstance = DateFormat.getDateInstance(DateFormat.LONG);
            DateFormat timeInstance = DateFormat.getTimeInstance(DateFormat.LONG);
            String dateTime = dateInstance.format(signatureDate) + timeInstance.format(signatureDate);
            e.setRealDate(dateTime);
        });
        return signatures;
    }
}
