package com.b2c.prototype.transform.modal;

import com.b2c.prototype.modal.entity.item.ArticularGroup;
import com.b2c.prototype.modal.entity.item.DiscountGroup;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.modal.entity.store.StoreGeneralBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreArticularGroupTransform {
    private ArticularGroup articularGroup;
    private StoreGeneralBoard storeGeneralBoard;
    private Set<Store> stores;
}
