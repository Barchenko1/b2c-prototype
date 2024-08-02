package com.b2c.prototype.service.client.bucket;

import com.b2c.prototype.modal.client.dto.request.RequestItemBucketDto;
import com.b2c.prototype.dao.bucket.IBucketDao;
import lombok.extern.slf4j.Slf4j;

import static com.b2c.prototype.util.Query.INSERT_INTO_BUCKET;
import static com.b2c.prototype.util.Query.DELETE_FROM_BUCKET;
import static com.b2c.prototype.util.Query.UPDATE_BUCKET;

@Slf4j
public class BucketService implements IBucketService {

    private final IBucketDao bucketDao;


    public BucketService(IBucketDao bucketDao) {
        this.bucketDao = bucketDao;
    }

    @Override
    public void addItemToBucket(RequestItemBucketDto itemBucketDto) {
        bucketDao.mutateEntityBySQLQueryWithParams(INSERT_INTO_BUCKET,
                itemBucketDto.getItemName(),
                "username1",
                itemBucketDto.getCount());
    }

    @Override
    public void updateItemCountInBucket(RequestItemBucketDto itemBucketDto) {
        bucketDao.mutateEntityBySQLQueryWithParams(UPDATE_BUCKET,
                itemBucketDto.getCount(),
                "username1",
                itemBucketDto.getItemName());
    }

    @Override
    public void deleteItemFromBucket(RequestItemBucketDto itemBucketDto) {
        bucketDao.mutateEntityBySQLQueryWithParams(DELETE_FROM_BUCKET,
                "username1",
                itemBucketDto.getItemName());
    }
}
