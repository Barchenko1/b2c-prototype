package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.basic.BasicUserInfoDao;
import com.b2c.prototype.dao.basic.BasicCategoryDao;
import com.b2c.prototype.dao.basic.BasicAddressDao;
import com.b2c.prototype.dao.basic.BasicDeliveryDao;
import com.b2c.prototype.dao.basic.BasicDeliveryTypeDao;
import com.b2c.prototype.dao.basic.BasicPaymentMethodDao;
import com.b2c.prototype.dao.userinfo.IUserInfoDao;
import com.b2c.prototype.dao.delivery.IAddressDao;
import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.tm.core.configuration.ConfigDbType;
import com.tm.core.configuration.factory.ConfigurationSessionFactory;
import com.tm.core.configuration.factory.IConfigurationSessionFactory;
import com.b2c.prototype.dao.basic.BasicAppUserDao;
import com.b2c.prototype.dao.basic.BasicBrandDao;
import com.b2c.prototype.dao.basic.BasicBucketDao;
import com.b2c.prototype.dao.basic.BasicCardDao;
import com.b2c.prototype.dao.basic.BasicDiscountDao;
import com.b2c.prototype.dao.basic.BasicOptionItemDao;
import com.b2c.prototype.dao.basic.BasicOptionGroupDao;
import com.b2c.prototype.dao.basic.BasicOrderHistoryDao;
import com.b2c.prototype.dao.basic.BasicOrderItemDao;
import com.b2c.prototype.dao.basic.BasicOrderStatusDao;
import com.b2c.prototype.dao.basic.BasicPaymentDao;
import com.b2c.prototype.dao.basic.BasicPostDao;
import com.b2c.prototype.dao.basic.BasicItemDao;
import com.b2c.prototype.dao.basic.BasicItemStatusDao;
import com.b2c.prototype.dao.basic.BasicItemTypeDao;
import com.b2c.prototype.dao.basic.BasicRatingDao;
import com.b2c.prototype.dao.basic.BasicReviewDao;
import com.b2c.prototype.dao.basic.BasicWishListDao;
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
import com.tm.core.transaction.BasicTransactionManager;
import com.tm.core.transaction.ITransactionManager;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BeanConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        IConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
                ConfigDbType.XML
        );
        return configurationSessionFactory.getSessionFactory();
    }

//    @Bean
//    public SessionFactory tenantSessionFactory() {
//        IConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
//                ConfigDbType.XML
//        );
//        return configurationSessionFactory.getSessionFactory();
//    }

    @Bean
    public ThreadLocalSessionManager sessionManager() {
        return new ThreadLocalSessionManager(sessionFactory());
    }

    @Bean
    @Primary
    @Qualifier("clientTransactionManager")
    public ITransactionManager clientTransactionManager(SessionFactory sessionFactory) {
        return new BasicTransactionManager(
                sessionFactory
        );
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
