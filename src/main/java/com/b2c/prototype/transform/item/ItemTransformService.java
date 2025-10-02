package com.b2c.prototype.transform.item;

import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;
import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.store.ResponseStoreDto;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.store.Store;
import org.springframework.stereotype.Service;

@Service
public class ItemTransformService implements IItemTransformService {

    @Override
    public Post mapPostDtoToPost(PostDto postDto) {
        return null;
    }

    @Override
    public PostDto mapPostToPostDto(Post post) {
        return null;
    }

    @Override
    public Store mapStoreDtoToStore(StoreDto storeDto) {
        return null;
    }

    @Override
    public ResponseStoreDto mapStoreToResponseStoreDto(Store store) {
        return null;
    }

    @Override
    public MetaData mapMetaDataDtoToMetaDataDto(MetaDataDto metaDataDto) {
        return null;
    }

    @Override
    public ResponseMetaDataDto mapMetaDataToResponseMetaDataDto(MetaData metaData) {
        return null;
    }

    @Override
    public ArticularItem mapArticularItemDtoToArticularItem(ArticularItemDto articularItemDto) {
        return null;
    }

    @Override
    public ResponseArticularItemDto mapArticularItemToResponseArticularItem(ArticularItem articularItem) {
        return null;
    }


    @Override
    public Review mapReviewDtoToReview(ReviewDto reviewDto) {
        return null;
    }

    @Override
    public ResponseReviewDto mapReviewToResponseReviewDto(Review review) {
        return null;
    }

}
