package com.b2c.prototype.processor.discount;

import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.DiscountStatusDto;
import com.b2c.prototype.manager.item.IDiscountManager;

import java.util.List;
import java.util.Optional;

public class DiscountProcess implements IDiscountProcess {

    private final IDiscountManager discountManager;

    public DiscountProcess(IDiscountManager discountManager) {
        this.discountManager = discountManager;
    }


    @Override
    public void saveDiscount(DiscountDto discountDto) {
        discountManager.saveDiscount(discountDto);
    }

    @Override
    public void updateItemDataDiscount(String articularId, DiscountDto discountDto) {
        discountManager.updateItemDataDiscount(articularId, discountDto);
    }

    @Override
    public void updateDiscount(String charSequenceCode, DiscountDto discountDto) {
        discountManager.updateDiscount(charSequenceCode, discountDto);
    }

    @Override
    public void changeDiscountStatus(DiscountStatusDto discountStatusDto) {
        discountManager.changeDiscountStatus(discountStatusDto);
    }

    @Override
    public void deleteDiscount(String charSequenceCode) {
        discountManager.deleteDiscount(charSequenceCode);
    }

    @Override
    public DiscountDto getDiscount(String charSequenceCode) {
        return discountManager.getDiscount(charSequenceCode);
    }

    @Override
    public Optional<DiscountDto> getOptionalDiscount(String charSequenceCode) {
        return discountManager.getOptionalDiscount(charSequenceCode);
    }

    @Override
    public List<DiscountDto> getDiscounts() {
        return discountManager.getDiscounts();
    }
}
