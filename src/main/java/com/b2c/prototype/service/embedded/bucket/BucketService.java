package com.b2c.prototype.service.embedded.bucket;

import com.b2c.prototype.modal.dto.request.ItemBucketDto;
import com.b2c.prototype.dao.embedded.IBucketDao;
import com.b2c.prototype.modal.embedded.bucket.Bucket;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class BucketService implements IBucketService {

    private final IBucketDao bucketDao;

    public BucketService(IBucketDao bucketDao) {
        this.bucketDao = bucketDao;
    }

    @Override
    public void addItemToBucket(ItemBucketDto itemBucketDto) {
        Bucket bucket = Bucket.builder()
//                .dateOfAdded(System.currentTimeMillis())
//                .item(null)
//                .userProfile(null)
                .build();

//        super.saveEntity(generalEntity);
    }

    @Override
    public void updateItemCountInBucket(ItemBucketDto itemBucketDto) {
//        Supplier<Bucket> bucketSupplier = () -> {
//            Parameter[] parameters = parameterFactory.createParameterArray(
//                    parameterFactory.createStringParameter("userId", itemBucketDto.getUserId()),
//                    parameterFactory.createStringParameter("articularId", itemBucketDto.getArticularId())
//            );

//            return getEntityDao().getEntity(parameters);
//        };
//        super.updateEntity(bucketSupplier);
    }

    @Override
    public void deleteItemFromBucket(ItemBucketDto itemBucketDto) {
//        Parameter[] parameters = parameterFactory.createParameterArray(
//                parameterFactory.createStringParameter("userId", itemBucketDto.getUserId()),
//                parameterFactory.createStringParameter("articularId", itemBucketDto.getArticularId())
//        );
//        super.deleteEntityByParameter(parameters);
    }
}
