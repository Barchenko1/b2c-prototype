package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.user.base.BasicUserInfoDao;
import com.b2c.prototype.dao.item.base.BasicCategoryDao;
import com.b2c.prototype.dao.delivery.base.BasicAddressDao;
import com.b2c.prototype.dao.delivery.base.BasicDeliveryDao;
import com.b2c.prototype.dao.delivery.base.BasicDeliveryTypeDao;
import com.b2c.prototype.dao.payment.base.BasicPaymentMethodDao;
import com.b2c.prototype.dao.user.IUserInfoDao;
import com.b2c.prototype.dao.delivery.IAddressDao;
import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.processor.AsyncProcessor;
import com.b2c.prototype.processor.IAsyncProcessor;
import com.tm.core.configuration.ConfigDbType;
import com.tm.core.configuration.factory.ConfigurationSessionFactory;
import com.tm.core.configuration.factory.IConfigurationSessionFactory;
import com.b2c.prototype.dao.user.base.BasicAppUserDao;
import com.b2c.prototype.dao.item.base.BasicBrandDao;
import com.b2c.prototype.dao.bucket.base.BasicBucketDao;
import com.b2c.prototype.dao.payment.base.BasicCardDao;
import com.b2c.prototype.dao.item.base.BasicDiscountDao;
import com.b2c.prototype.dao.option.base.BasicOptionItemDao;
import com.b2c.prototype.dao.option.base.BasicOptionGroupDao;
import com.b2c.prototype.dao.order.base.BasicOrderHistoryDao;
import com.b2c.prototype.dao.order.base.BasicOrderItemDao;
import com.b2c.prototype.dao.order.base.BasicOrderStatusDao;
import com.b2c.prototype.dao.payment.base.BasicPaymentDao;
import com.b2c.prototype.dao.post.base.BasicPostDao;
import com.b2c.prototype.dao.item.base.BasicItemDao;
import com.b2c.prototype.dao.item.base.BasicItemStatusDao;
import com.b2c.prototype.dao.item.base.BasicItemTypeDao;
import com.b2c.prototype.dao.rating.base.BasicRatingDao;
import com.b2c.prototype.dao.review.base.BasicReviewDao;
import com.b2c.prototype.dao.wishlist.base.BasicWishListDao;
import com.b2c.prototype.dao.bucket.IBucketDao;
import com.b2c.prototype.dao.option.IOptionItemDao;
import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.b2c.prototype.dao.order.IOrderHistoryDao;
import com.b2c.prototype.dao.order.IOrderItemDao;
import com.b2c.prototype.dao.order.IOrderStatusDao;
import com.b2c.prototype.dao.payment.ICardDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.dao.item.IBrandDao;
import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.dao.rating.IRatingDao;
import com.b2c.prototype.dao.review.IReviewDao;
import com.b2c.prototype.dao.user.IAppUserDao;
import com.b2c.prototype.dao.wishlist.IWishListDao;
import com.tm.core.processor.ThreadLocalSessionManager;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Value("${thread.pool.size}")
    private int threadCount;

    @Bean
    public SessionFactory sessionFactory() {
        IConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
                ConfigDbType.XML
        );
        return configurationSessionFactory.getSessionFactory();
    }

    @Bean
    public IAsyncProcessor asyncProcessor() {
        return new AsyncProcessor(threadCount);
    }

    @Bean
    public ThreadLocalSessionManager sessionManager() {
        return new ThreadLocalSessionManager(sessionFactory());
    }

    @Bean
    public IAppUserDao appUserDao(SessionFactory sessionFactory) {
        return new BasicAppUserDao(sessionFactory);
    }

    @Bean
    public IPaymentDao paymentDao(SessionFactory sessionFactory) {
        return new BasicPaymentDao(sessionFactory);
    }

    @Bean
    public IPaymentMethodDao paymentMethodDao(SessionFactory sessionFactory) {
        return new BasicPaymentMethodDao(sessionFactory);
    }

    @Bean
    public ICardDao cardDao(SessionFactory sessionFactory) {
        return new BasicCardDao(sessionFactory);
    }

    @Bean
    public IOrderItemDao orderItemDao(SessionFactory sessionFactory) {
        return new BasicOrderItemDao(sessionFactory);
    }

    @Bean
    public IOrderStatusDao orderStatusDao(SessionFactory sessionFactory) {
        return new BasicOrderStatusDao(sessionFactory);
    }

    @Bean
    public IOrderHistoryDao orderHistoryDao(SessionFactory sessionFactory) {
        return new BasicOrderHistoryDao(sessionFactory);
    }

    @Bean
    public IItemTypeDao itemTypeDao(SessionFactory sessionFactory) {
        return new BasicItemTypeDao(sessionFactory);
    }

    @Bean
    public IDiscountDao discountDao(SessionFactory sessionFactory) {
        return new BasicDiscountDao(sessionFactory);
    }

    @Bean
    public IBrandDao brandDao(SessionFactory sessionFactory) {
        return new BasicBrandDao(sessionFactory);
    }

    @Bean
    public ICategoryDao categoryDao(SessionFactory sessionFactory) {
        return new BasicCategoryDao(sessionFactory);
    }

    @Bean
    public IItemStatusDao itemStatusDao(SessionFactory sessionFactory) {
        return new BasicItemStatusDao(sessionFactory);
    }

    @Bean
    public IPostDao postDao(SessionFactory sessionFactory) {
        return new BasicPostDao(sessionFactory);
    }

    @Bean
    public IOptionGroupDao optionGroupDao(SessionFactory sessionFactory) {
        return new BasicOptionGroupDao(sessionFactory);
    }

    @Bean
    public IOptionItemDao optionDao(SessionFactory sessionFactory) {
        return new BasicOptionItemDao(sessionFactory);
    }

    @Bean
    public IBucketDao bucketDao(SessionFactory sessionFactory) {
        return new BasicBucketDao(sessionFactory);
    }

    @Bean
    public IWishListDao wishListDao(SessionFactory sessionFactory) {
        return new BasicWishListDao(sessionFactory);
    }

    @Bean
    public IItemDao itemDao(SessionFactory sessionFactory) {
        return new BasicItemDao(sessionFactory);
    }

    @Bean
    public IReviewDao reviewDao(SessionFactory sessionFactory) {
        return new BasicReviewDao(sessionFactory);
    }

    @Bean
    public IRatingDao ratingDao(SessionFactory sessionFactory) {
        return new BasicRatingDao(sessionFactory);
    }

    @Bean
    public IDeliveryDao deliveryDao(SessionFactory sessionFactory) {
        return new BasicDeliveryDao(sessionFactory);
    }

    @Bean
    public IAddressDao addressDao(SessionFactory sessionFactory) {
        return new BasicAddressDao(sessionFactory);
    }

    @Bean
    public IDeliveryTypeDao deliveryTypeDao(SessionFactory sessionFactory) {
        return new BasicDeliveryTypeDao(sessionFactory);
    }

    @Bean
    public IUserInfoDao userInfoDao(SessionFactory sessionFactory) {
        return new BasicUserInfoDao(sessionFactory);
    }


}
