package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.modal.dto.payload.discount.DiscountGroupDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;
import com.b2c.prototype.manager.item.IDiscountGroupManager;
import com.b2c.prototype.processor.item.IDiscountGroupProcess;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DiscountGroupProcess implements IDiscountGroupProcess {

    private final IDiscountGroupManager discountManager;

    public DiscountGroupProcess(IDiscountGroupManager discountManager) {
        this.discountManager = discountManager;
    }

    @Override
    public void saveDiscountGroup(final Map<String, String> requestParams, DiscountGroupDto discountGroupDto) {
        String regionCode = requestParams.get("region");
        discountManager.saveDiscountGroup(regionCode, discountGroupDto);
    }

    @Override
    public void updateDiscountGroup(Map<String, String> requestParams, DiscountGroupDto discountGroupDto) {
        String articularId = requestParams.get("articularId");
        String regionCode = requestParams.get("region");
        String key = requestParams.get("key");
        if (articularId != null && key != null) {
            throw new RuntimeException("Only one of 'articularId' or 'charSequenceCode' can be provided");
        }
        if (articularId != null) {
            discountManager.updateArticularDiscount(articularId, discountGroupDto);
        }
        if (key != null) {
            discountManager.updateDiscountGroup(regionCode, key, discountGroupDto);
        }
    }

    @Override
    public void changeDiscountStatus(DiscountStatusDto discountStatusDto) {
        discountManager.changeDiscountStatus(discountStatusDto);
    }

    @Override
    public void removeDiscountGroup(Map<String, String> requestParams) {
        String regionCode = requestParams.get("region");
        String key = requestParams.get("key");
        discountManager.removeDiscountGroup(regionCode, key);
    }

    @Override
    public DiscountGroupDto getDiscountGroup(Map<String, String> requestParams) {
        String regionCode = requestParams.get("region");
        String key = requestParams.get("key");
        return discountManager.getDiscountGroup(regionCode, key);
    }

    @Override
    public List<DiscountGroupDto> getDiscountGroups(Map<String, String> requestParams) {
        String regionCode = requestParams.get("region");
        return discountManager.getDiscountGroups(regionCode);
    }
}
