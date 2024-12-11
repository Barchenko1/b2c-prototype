package com.b2c.prototype.service.embedded.bucket;

import com.b2c.prototype.modal.dto.request.ItemBucketDto;

public interface IBucketService {
    void addItemToBucket(ItemBucketDto itemBucketDto);
    void updateItemCountInBucket(ItemBucketDto itemBucketDto);
    void deleteItemFromBucket(ItemBucketDto itemBucketDto);
}
