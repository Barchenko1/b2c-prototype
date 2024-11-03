package com.b2c.prototype.service.base.wishlist;

import com.b2c.prototype.dao.embedded.IWishListDao;
import com.b2c.prototype.modal.dto.request.RequestWishListDto;
import com.b2c.prototype.modal.embedded.wishlist.Wishlist;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.modal.GeneralEntity;
import com.tm.core.processor.finder.parameter.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WishListService extends AbstractSingleEntityService implements IWishListService {
    private final ISingleEntityDao wishListDao;

    public WishListService(IWishListDao wishListDao) {
        this.wishListDao = wishListDao;
    }

    @Override
    protected ISingleEntityDao getEntityDao() {
        return this.wishListDao;
    }

    @Override
    public void addToWishList(RequestWishListDto requestItemBucketDto) {
        Wishlist wishlist = Wishlist.builder()
                .dateOfUpdate(System.currentTimeMillis())
//                .userProfile(null)
//                .item(null)
                .build();
        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, wishlist);
        super.saveEntity(generalEntity);
    }

    @Override
    public void deleteFromWishList(RequestWishListDto requestItemBucketDto) {
        Parameter[] parameter = parameterFactory.createParameterArray(
                parameterFactory.createStringParameter("username", requestItemBucketDto.getUserId()),
                parameterFactory.createStringParameter("articularId", requestItemBucketDto.getArticularId())
        );

        super.deleteEntity(parameter);
    }
}
