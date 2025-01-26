package com.b2c.prototype.service.embedded.bucket;

import com.b2c.prototype.modal.dto.payload.ItemBucketDto;

public interface IBucketManager {
    void addItemToBucket(ItemBucketDto itemBucketDto);
    void updateItemCountInBucket(ItemBucketDto itemBucketDto);
    void deleteItemFromBucket(ItemBucketDto itemBucketDto);
}
