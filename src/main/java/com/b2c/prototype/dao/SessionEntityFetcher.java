package com.b2c.prototype.dao;

import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.CommissionValue;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.payment.PaymentStatus;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.review.Rating;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.review.ReviewComment;
import com.b2c.prototype.modal.entity.review.ReviewStatus;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.nimbusds.jose.util.Pair;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.CHAR_SEQUENCE_CODE;
import static com.b2c.prototype.util.Constant.POST_ID;
import static com.b2c.prototype.util.Constant.REVIEW_COMMENT_ID;
import static com.b2c.prototype.util.Constant.USER_ID;
import static com.b2c.prototype.util.Constant.VALUE;

public class SessionEntityFetcher {

    private final IGeneralEntityDao generalEntityDao;

    public SessionEntityFetcher(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    public Optional<UserDetails> fetchUserDetails(Session session, String value) {
        return generalEntityDao.findOptionEntity(
                "UserDetails.findByUserId",
                Pair.of(USER_ID, value));
    }

    public PaymentMethod fetchPaymentMethod(Session session, String value) {
        return generalEntityDao.findEntity(
                "PaymentMethod.findByValue",
                Pair.of(VALUE, value));
    }

    public PaymentStatus fetchPaymentStatus(Session session, String value) {
        return generalEntityDao.findEntity(
                "PaymentStatus.findByValue",
                Pair.of(VALUE, value));
    }

    public Currency fetchCurrency(Session session, String value) {
        return generalEntityDao.findEntity(
                "Currency.findByValue",
                Pair.of(VALUE, value));
    }

    public CountryPhoneCode fetchCountryPhoneCode(Session session, String value) {
        return generalEntityDao.findEntity(
                "CountryPhoneCode.findByValue",
                Pair.of(VALUE, value));
    }

    public Country fetchCountry(Session session, String value) {
        return generalEntityDao.findEntity(
                "Country.findByValue",
                Pair.of(VALUE, value));
    }

    public Optional<Discount> fetchDiscountOptional(Session session, String value) {
        return generalEntityDao.findOptionEntity(
                "Discount.currency",
                Pair.of(CHAR_SEQUENCE_CODE, value));
    }

    public Optional<Discount> fetchOptionArticularDiscount(Session session, String value) {
        return generalEntityDao.findOptionEntity(
                "ArticularItem.findByDiscountCharSequenceCode",
                Pair.of(CHAR_SEQUENCE_CODE, value));
    }

    public ArticularStatus fetchArticularStatus(Session session, String value) {
        return generalEntityDao.findEntity(
                "ArticularStatus.findByValue",
                Pair.of(VALUE, value));
    }

    public Optional<OptionGroup> fetchOptionGroup(Session session, String namedQuery, String value) {
        return generalEntityDao.findOptionEntity(
                namedQuery,
                Pair.of(VALUE, value));
    }

    public OrderStatus fetchOrderStatus(Session session, String value) {
        return generalEntityDao.findEntity(
                "OrderStatus.findByValue",
                Pair.of(VALUE, value));
    }

    public ArticularItem fetchArticularItem(Session session, String articularId) {
        return generalEntityDao.findEntity(
                "ArticularItem.full",
                Pair.of(ARTICULAR_ID, articularId));
    }

    public DeliveryType fetchDeliveryType(Session session, String value) {
        return generalEntityDao.findEntity(
                "DeliveryType.findByValue",
                Pair.of(VALUE, value));
    }

    public TimeDurationOption fetchTimeDurationOption(Session session, String value) {
        return generalEntityDao.findEntity(
                "TimeDurationOption.findByValue",
                Pair.of(VALUE, value));
    }

    public MessageStatus fetchMessageStatus(Session session, String value) {
        return generalEntityDao.findEntity(
                "MessageStatus.findByValue",
                Pair.of(VALUE, value));
    }

    public MessageType fetchMessageType(Session session, String value) {
        return generalEntityDao.findEntity(
                "MessageType.findByValue",
                Pair.of(VALUE, value));
    }

    public Optional<Category> fetchOptionalCategory(Session session, String value) {
        return generalEntityDao.findOptionEntity(
                "Category.findByValue",
                Pair.of(VALUE, value));
    }

    public Optional<Brand> fetchOptionalBrand(Session session, String value) {
        return generalEntityDao.findOptionEntity(
                "Brand.findByValue",
                Pair.of(VALUE, value));
    }

    public Optional<ItemType> fetchOptionalItemType(Session session, String value) {
        return generalEntityDao.findOptionEntity(
                "ItemType.findByValue",
                Pair.of(VALUE, value));
    }

    public Optional<CommissionValue> fetchCommission(Session session) {
        return generalEntityDao.findOptionEntity(
                "CommissionValue.getCommissionList", (Pair<String, ?>) null);
    }

    public Optional<MinMaxCommission> fetchMinMaxCommission(Session session) {
        return Optional.empty();
    }

    public Optional<Post> fetchPost(Session session, String value) {
        if (value != null) {
            return generalEntityDao.findOptionEntity(
                    "Post.getPostByPostId",
                    Pair.of(POST_ID, value));
        }
        return Optional.empty();
    }

    public List<ReviewComment> fetchReviewComments(Session session, String value) {
        if (value != null) {
            Optional<Review> optionalReview = generalEntityDao.findOptionEntity(
                    "Review.findByReviewId",
                    Pair.of(REVIEW_COMMENT_ID, value));
            if (optionalReview.isPresent()) {
                Review review = optionalReview.get();
                return review.getComments();
            }
        }
        return Collections.emptyList();
    }

    public ReviewStatus fetchReviewStatus(Session session, String value) {
        return generalEntityDao.findEntity(
                "ReviewStatus.findByValue",
                Pair.of(VALUE, value));
    }

    public Rating fetchRating(Session session, int value) {
        return generalEntityDao.findEntity(
                "Rating.findByValue",
                Pair.of(VALUE, value));
    }
}
