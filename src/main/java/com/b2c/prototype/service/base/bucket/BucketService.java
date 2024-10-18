package com.b2c.prototype.service.base.bucket;

import com.b2c.prototype.modal.dto.request.RequestItemBucketDto;
import com.b2c.prototype.dao.bucket.IBucketDao;
import com.b2c.prototype.modal.entity.bucket.Bucket;
import com.b2c.prototype.service.general.AbstractGeneralEntityService;
import com.tm.core.dao.general.IGeneralEntityDao;
import com.tm.core.modal.GeneralEntity;
import com.tm.core.processor.finder.parameter.Parameter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class BucketService extends AbstractGeneralEntityService implements IBucketService {

    private final IBucketDao bucketDao;

    public BucketService(IBucketDao bucketDao) {
        this.bucketDao = bucketDao;
    }

    @Override
    protected IGeneralEntityDao getEntityDao() {
        return this.bucketDao;
    }

    @Override
    public void addItemToBucket(RequestItemBucketDto itemBucketDto) {
        Bucket bucket = Bucket.builder()
//                .dateOfAdded(System.currentTimeMillis())
//                .item(null)
                .user(null)
                .build();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, bucket);
        super.saveEntity(generalEntity);
    }

    @Override
    public void updateItemCountInBucket(RequestItemBucketDto itemBucketDto) {
        Supplier<Bucket> bucketSupplier = () -> {
            Parameter[] parameters = parameterFactory.createParameterArray(
                    parameterFactory.createStringParameter("userId", itemBucketDto.getUserId()),
                    parameterFactory.createStringParameter("articularId", itemBucketDto.getArticularId())
            );

            return getEntityDao().getGeneralEntity(parameters);
        };
        super.updateEntity(bucketSupplier);
    }

    @Override
    public void deleteItemFromBucket(RequestItemBucketDto itemBucketDto) {
        Parameter[] parameters = parameterFactory.createParameterArray(
                parameterFactory.createStringParameter("userId", itemBucketDto.getUserId()),
                parameterFactory.createStringParameter("articularId", itemBucketDto.getArticularId())
        );
        super.deleteEntity(parameters);
    }
}
