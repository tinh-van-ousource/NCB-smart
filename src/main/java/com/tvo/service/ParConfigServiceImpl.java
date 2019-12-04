package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.ParConfigUpdateCreditCardNumberReqDto;
import com.tvo.controllerDto.ParConfigUpdateOtherParamReqDto;
import com.tvo.controllerDto.ParConfigUpdateReissueCardReasonReqDto;
import com.tvo.dao.ParConfigMultiIdRepo;
import com.tvo.dto.ParConfigResDto;
import com.tvo.model.ParConfigMultiIdEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ParConfigServiceImpl implements ParConfigService {

    private final ParConfigMultiIdRepo parConfigMultiIdRepo;

    public ParConfigServiceImpl(ParConfigMultiIdRepo parConfigMultiIdRepo) {
        this.parConfigMultiIdRepo = parConfigMultiIdRepo;
    }

    @Override
    public ParConfigResDto createCreditCardNumber(ParConfigUpdateCreditCardNumberReqDto req) {
        ParConfigMultiIdEntity entityNew = new ParConfigMultiIdEntity();
        entityNew.getKey().setParam("creditcardnumber");
        entityNew.getKey().setCode(req.getValue());
        entityNew.setValue(req.getValue());
        entityNew.setNote("Số lượng thẻ tối đa 1 KH được mở");

        return ModelMapperUtils.map(parConfigMultiIdRepo.save(entityNew), ParConfigResDto.class);
    }

    @Override
    public Boolean checkDuplicateCreditCardNumber(String value) {
        ParConfigMultiIdEntity entity = parConfigMultiIdRepo.findByParamAndCode("creditcardnumber", value);
        return entity != null;
    }

    @Override
    public List<ParConfigResDto> getCreditCardNumber() {
        List<ParConfigMultiIdEntity> parConfigMultiIdEntityList = parConfigMultiIdRepo.findByParam("creditcardnumber");
        return ModelMapperUtils.mapAll(parConfigMultiIdEntityList, ParConfigResDto.class);
    }

    @Override
    @Transactional
    public ParConfigResDto updateCreditCardNumber(String oldValue, String newValue) {
        ParConfigMultiIdEntity oldEntity = parConfigMultiIdRepo.findByParamAndCode("creditcardnumber", oldValue);
        if (oldEntity != null) {
            ParConfigMultiIdEntity newEntity = ModelMapperUtils.map(oldEntity, new ParConfigMultiIdEntity());
            newEntity.setValue(newValue);
            newEntity.getKey().setCode(newValue);
            parConfigMultiIdRepo.delete(oldEntity);
            return ModelMapperUtils.map(parConfigMultiIdRepo.save(newEntity), ParConfigResDto.class);
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean deleteCreditCardNumber(String value) {
        ParConfigMultiIdEntity oldEntity = parConfigMultiIdRepo.findByParamAndCode("creditcardnumber", value);
        if (oldEntity != null) {
            parConfigMultiIdRepo.delete(oldEntity);
            return true;
        }
        return false;
    }


    @Override
    public ParConfigResDto updateReissueCardReason(ParConfigUpdateReissueCardReasonReqDto req) {
        ParConfigMultiIdEntity entityNew = new ParConfigMultiIdEntity();
        entityNew.getKey().setParam("reissue_card_reason");
        entityNew.getKey().setCode(req.getCode());
        entityNew.setValue(req.getValue());
        entityNew.setNote("Lý do phát hành lại thẻ");

        return ModelMapperUtils.map(parConfigMultiIdRepo.save(entityNew), ParConfigResDto.class);
    }

    @Override
    public List<ParConfigResDto> getReissueCardReason() {
        List<ParConfigMultiIdEntity> parConfigMultiIdEntityList = parConfigMultiIdRepo.findByParam("reissue_card_reason");
        return ModelMapperUtils.mapAll(parConfigMultiIdEntityList, ParConfigResDto.class);
    }

    @Override
    public Boolean checkDuplicateReissueCardReason(String code) {
        ParConfigMultiIdEntity entity = parConfigMultiIdRepo.findByParamAndCode("reissue_card_reason", code);
        return entity != null;
    }

    @Override
    @Transactional
    public Boolean deleteReissueCardReason(String code) {
        ParConfigMultiIdEntity oldEntity = parConfigMultiIdRepo.findByParamAndCode("reissue_card_reason", code);
        if (oldEntity != null) {
            parConfigMultiIdRepo.delete(oldEntity);
            return true;
        }
        return false;
    }


    @Override
    public ParConfigResDto updateOtherParam(ParConfigUpdateOtherParamReqDto req) {
        ParConfigMultiIdEntity entityNew = new ParConfigMultiIdEntity();
        entityNew.getKey().setParam(req.getParam());
        entityNew.getKey().setCode(req.getCode());
        entityNew.setValue(req.getValue());
        entityNew.setNote(req.getNote());

        return ModelMapperUtils.map(parConfigMultiIdRepo.save(entityNew), ParConfigResDto.class);
    }

    @Override
    public List<ParConfigResDto> getOtherParam() {
        return ModelMapperUtils.mapAll(
                parConfigMultiIdRepo.findOtherParam(Arrays.asList("creditcardnumber", "reissue_card_reason")),
                ParConfigResDto.class);
    }

    @Override
    public Boolean checkDuplicateOtherParam(String param, String code) {
        ParConfigMultiIdEntity entity = parConfigMultiIdRepo.findByParamAndCode(param, code);
        return entity != null;
    }

    @Override
    public Boolean deleteOtherParam(String param, String code) {
        ParConfigMultiIdEntity oldEntity = parConfigMultiIdRepo.findByParamAndCode(param, code);
        if (oldEntity != null) {
            parConfigMultiIdRepo.delete(oldEntity);
            return true;
        }
        return false;
    }

}
