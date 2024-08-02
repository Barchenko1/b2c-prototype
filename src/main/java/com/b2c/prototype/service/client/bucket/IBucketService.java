package com.b2c.prototype.service.client.bucket;

import com.b2c.prototype.modal.client.dto.request.RequestItemBucketDto;

public interface IBucketService {
    void addItemToBucket(RequestItemBucketDto itemBucketDto);
    void updateItemCountInBucket(RequestItemBucketDto itemBucketDto);
    void deleteItemFromBucket(RequestItemBucketDto itemBucketDto);
}
