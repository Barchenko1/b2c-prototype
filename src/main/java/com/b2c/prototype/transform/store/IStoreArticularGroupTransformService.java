package com.b2c.prototype.transform.store;

import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.StoreDiscount;
import com.b2c.prototype.modal.dto.payload.item.StoreDiscountGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemCostGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemGroupTransfer;
import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularItemRequestDto;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.DiscountGroup;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.tenant.Tenant;
import com.b2c.prototype.transform.transform.StoreArticularGroupTransform;

import java.util.Map;

public interface IStoreArticularGroupTransformService {

    Discount mapStoreDiscountToDiscount(StoreDiscount storeDiscount);
    DiscountGroup mapStoreDiscountGroupDtoToDiscountGroup(Tenant tenant, StoreDiscountGroup storeDiscountGroup);
    OptionGroup mapStoreOptionItemGroupDtoToOptionGroup(Tenant tenant, StoreOptionItemGroup storeOptionItemGroup);
    StoreOptionItemGroupTransfer mapStoreOptionItemGroupDtoToOptionItemSet(Tenant tenant, Map.Entry<String, StoreOptionItemGroup> storeOptionItemGroup);
    StoreOptionItemGroupTransfer mapStoreOptionItemCostGroupDtoToOptionItemSet(Tenant tenant, Map.Entry<String, StoreOptionItemCostGroup> storeOptionItemCostGroup);

    OptionGroup mapStoreOptionItemCostGroupDtoToOptionGroup(Tenant tenant, StoreOptionItemCostGroup storeOptionItemCostGroup);

    StoreArticularGroupTransform mapStoreArticularGroupRequestDtoToStoreArticularGroupTransform(StoreArticularGroupRequestDto storeArticularGroupRequestDto);
    StoreArticularGroupTransform mapStoreArticularItemRequestDtoToStoreArticularGroupTransform(StoreArticularItemRequestDto storeArticularItemRequestDto);

}
