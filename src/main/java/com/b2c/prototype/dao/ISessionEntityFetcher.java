package com.b2c.prototype.dao;

import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.*;
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
import com.b2c.prototype.modal.entity.review.ReviewComment;
import com.b2c.prototype.modal.entity.review.ReviewStatus;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserDetails;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public interface ISessionEntityFetcher {

    Optional<UserDetails> fetchUserDetails(Session session, String value);
    PaymentMethod fetchPaymentMethod(Session session, String value);
    PaymentStatus fetchPaymentStatus(Session session, String value);
    Currency fetchCurrency(Session session, String value);
    CountryPhoneCode fetchCountryPhoneCode(Session session, String value);
    Country fetchCountry(Session session, String value);
    Optional<Discount> fetchDiscountOptional(Session session, String value);
    Optional<Discount> fetchOptionArticularDiscount(Session session, String value);
    ArticularStatus fetchArticularStatus(Session session, String value);
    Optional<OptionGroup> fetchOptionGroup(Session session, String namedQuery, String value);
    OrderStatus fetchOrderStatus(Session session, String value);
    ArticularItem fetchArticularItem(Session session, String articularId);
    DeliveryType fetchDeliveryType(Session session, String value);
    TimeDurationOption fetchTimeDurationOption(Session session, String value);
    MessageStatus fetchMessageStatus(Session session, String value);
    MessageType fetchMessageType(Session session, String value);
    Optional<Category> fetchOptionalCategory(Session session, String value);
    Optional<Brand> fetchOptionalBrand(Session session, String value);
    Optional<ItemType> fetchOptionalItemType(Session session, String value);
    Optional<CommissionValue> fetchCommission(Session session);
    Optional<MinMaxCommission> fetchMinMaxCommission(Session session);
    Optional<Post> fetchPost(Session session, String value);
    List<ReviewComment> fetchReviewComments(Session session, String value);
    ReviewStatus fetchReviewStatus(Session session, String value);
    Rating fetchRating(Session session, int value);
}
