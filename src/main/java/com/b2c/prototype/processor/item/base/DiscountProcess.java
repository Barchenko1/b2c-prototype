package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;
import com.b2c.prototype.manager.item.IDiscountManager;
import com.b2c.prototype.processor.item.IDiscountProcess;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DiscountProcess implements IDiscountProcess {

    private final IDiscountManager discountManager;

    public DiscountProcess(IDiscountManager discountManager) {
        this.discountManager = discountManager;
    }

    @Override
    public void saveDiscount(Map<String, String> requestParams, DiscountDto discountDto) {
        discountManager.saveDiscount(discountDto);
    }

    @Override
    public void updateDiscount(Map<String, String> requestParams, DiscountDto discountDto) {
        String articularId = requestParams.get("articularId");
        String charSequenceCode = requestParams.get("charSequenceCode");
        if (articularId != null && charSequenceCode != null) {
            throw new RuntimeException("Only one of 'articularId' or 'charSequenceCode' can be provided");
        }
        if (articularId != null) {
            discountManager.updateItemDataDiscount(articularId, discountDto);
        }
        if (charSequenceCode != null) {
            discountManager.updateDiscount(charSequenceCode, discountDto);
        }
    }

    @Override
    public void changeDiscountStatus(DiscountStatusDto discountStatusDto) {
        discountManager.changeDiscountStatus(discountStatusDto);
    }

    @Override
    public void changeDiscountStatus(Map<String, String> requestParams, Map<String, Object> updates) {
        discountManager.changeDiscountStatus(null);
    }

    @Override
    public void deleteDiscount(Map<String, String> requestParams) {
        String charSequenceCode = requestParams.get("charSequenceCode");
        discountManager.deleteDiscount(charSequenceCode);
    }

    @Override
    public DiscountDto getDiscount(Map<String, String> requestParams) {
        String charSequenceCode = requestParams.get("charSequenceCode");
        return discountManager.getDiscount(charSequenceCode);
    }

    @Override
    public List<DiscountDto> getDiscounts(Map<String, String> requestParam) {
        return discountManager.getDiscounts();
    }
}
