package com.b2c.prototype.transform.store;

import com.b2c.prototype.modal.dto.payload.item.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.StoreDiscount;
import com.b2c.prototype.modal.dto.payload.item.StoreDiscountGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemCostGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemGroupTransfer;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.DiscountGroup;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.region.Region;
import com.b2c.prototype.transform.modal.StoreArticularGroupTransform;

import java.util.Map;

public interface IStoreArticularGroupTransformService {

    Discount mapStoreDiscountToDiscount(StoreDiscount storeDiscount);
    DiscountGroup mapStoreDiscountGroupDtoToDiscountGroup(Region region, StoreDiscountGroup storeDiscountGroup);
    OptionGroup mapStoreOptionItemGroupDtoToOptionGroup(Region region, StoreOptionItemGroup storeOptionItemGroup);
    StoreOptionItemGroupTransfer mapStoreOptionItemGroupDtoToOptionItemSet(Region region, Map.Entry<String, StoreOptionItemGroup> storeOptionItemGroup);
    StoreOptionItemGroupTransfer mapStoreOptionItemCostGroupDtoToOptionItemSet(Region region, Map.Entry<String, StoreOptionItemCostGroup> storeOptionItemCostGroup);

    OptionGroup mapStoreOptionItemCostGroupDtoToOptionGroup(Region region, StoreOptionItemCostGroup storeOptionItemCostGroup);

    StoreArticularGroupTransform mapStoreArticularGroupRequestDtoToStoreArticularGroupTransform(StoreArticularGroupRequestDto storeArticularGroupRequestDto);
}
