package com.b2c.prototype.service.embedded.bucket;

import com.b2c.prototype.modal.dto.request.RequestItemBucketDto;

public interface IBucketService {
    void addItemToBucket(RequestItemBucketDto itemBucketDto);
    void updateItemCountInBucket(RequestItemBucketDto itemBucketDto);
    void deleteItemFromBucket(RequestItemBucketDto itemBucketDto);
}
