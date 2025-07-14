package com.b2c.prototype.dao;

import com.b2c.prototype.modal.constant.CommissionType;
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
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.query.IQueryService;
import com.tm.core.process.dao.query.QueryService;
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

public class SessionEntityFetcher implements ISessionEntityFetcher {

    private final IQueryService queryService;
    private final IParameterFactory parameterFactory;

    public SessionEntityFetcher(IQueryService queryService, IParameterFactory parameterFactory) {
        this.queryService = queryService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public Optional<UserDetails> fetchUserDetails(Session session, String value) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                UserDetails.class,
                "UserDetails.findByUserId",
                parameterFactory.createStringParameter(USER_ID, value));
    }

    @Override
    public PaymentMethod fetchPaymentMethod(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                PaymentMethod.class,
                "PaymentMethod.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    @Override
    public PaymentStatus fetchPaymentStatus(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                PaymentStatus.class,
                "PaymentStatus.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    public Currency fetchCurrency(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                Currency.class,
                "Currency.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    public CountryPhoneCode fetchCountryPhoneCode(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                CountryPhoneCode.class,
                "CountryPhoneCode.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    @Override
    public Country fetchCountry(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                Country.class,
                "Country.findByValue",
                parameterFactory.createStringParameter(VALUE, value)
        );
    }

    @Override
    public Optional<Discount> fetchDiscountOptional(Session session, String value) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                Discount.class,
                "Discount.currency",
                parameterFactory.createStringParameter(CHAR_SEQUENCE_CODE, value));
    }

    @Override
    public Optional<Discount> fetchOptionArticularDiscount(Session session, String value) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                Discount.class,
                "ArticularItem.findByDiscountCharSequenceCode",
                parameterFactory.createStringParameter(CHAR_SEQUENCE_CODE, value));
    }

    @Override
    public ArticularStatus fetchArticularStatus(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                ArticularStatus.class,
                "ArticularStatus.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    @Override
    public Optional<OptionGroup> fetchOptionGroup(Session session, String namedQuery, String value) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                OptionGroup.class,
                namedQuery,
                parameterFactory.createStringParameter(VALUE, value));
    }

    @Override
    public OrderStatus fetchOrderStatus(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                OrderStatus.class,
                "OrderStatus.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    @Override
    public ArticularItem fetchArticularItem(Session session, String articularId) {
        return queryService.getNamedQueryEntity(
                session,
                ArticularItem.class,
                "ArticularItem.full",
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId));
    }

    @Override
    public DeliveryType fetchDeliveryType(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                DeliveryType.class,
                "DeliveryType.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    @Override
    public TimeDurationOption fetchTimeDurationOption(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                TimeDurationOption.class,
                "TimeDurationOption.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    @Override
    public MessageStatus fetchMessageStatus(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                MessageStatus.class,
                "MessageStatus.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    @Override
    public MessageType fetchMessageType(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                MessageType.class,
                "MessageType.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    @Override
    public Optional<Category> fetchOptionalCategory(Session session, String value) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                Category.class,
                "Category.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    @Override
    public Optional<Brand> fetchOptionalBrand(Session session, String value) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                Brand.class,
                "Brand.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    @Override
    public Optional<ItemType> fetchOptionalItemType(Session session, String value) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                ItemType.class,
                "ItemType.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    @Override
    public Optional<CommissionValue> fetchCommission(Session session) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                CommissionValue.class,
                "CommissionValue.getCommissionList");
    }

    @Override
    public Optional<MinMaxCommission> fetchMinMaxCommission(Session session, CommissionType value) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                MinMaxCommission.class,
                "MinMaxCommission.findByCommissionType",
                parameterFactory.createEnumParameter("commissionType", value));
    }

    @Override
    public Optional<Post> fetchPost(Session session, String value) {
        if (value != null) {
            return queryService.getNamedQueryOptionalEntity(
                    session,
                    Post.class,
                    "Post.getPostByPostId",
                    parameterFactory.createStringParameter(POST_ID, value));
        }
        return Optional.empty();
    }

    @Override
    public List<ReviewComment> fetchReviewComments(Session session, String value) {
        if (value != null) {
            Optional<Review> optionalReview = queryService.getNamedQueryOptionalEntity(
                    session,
                    Review.class,
                    "Review.findByReviewId",
                    parameterFactory.createStringParameter(REVIEW_COMMENT_ID, value));
            if (optionalReview.isPresent()) {
                Review review = optionalReview.get();
                return review.getComments();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public ReviewStatus fetchReviewStatus(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                ReviewStatus.class,
                "ReviewStatus.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    @Override
    public Rating fetchRating(Session session, int value) {
        return queryService.getNamedQueryEntity(
                session,
                Rating.class,
                "Rating.findByValue",
                parameterFactory.createIntegerParameter(VALUE, value));
    }
}
