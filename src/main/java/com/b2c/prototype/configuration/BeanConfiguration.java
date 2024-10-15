package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.address.ICountryDao;
import com.b2c.prototype.dao.address.base.BasicCountryDao;
import com.b2c.prototype.dao.user.ICountryPhoneCodeDao;
import com.b2c.prototype.dao.user.base.BasicCountryPhoneCodeDao;
import com.b2c.prototype.dao.user.base.BasicUserInfoDao;
import com.b2c.prototype.dao.item.base.BasicCategoryDao;
import com.b2c.prototype.dao.address.base.BasicAddressDao;
import com.b2c.prototype.dao.delivery.base.BasicDeliveryDao;
import com.b2c.prototype.dao.delivery.base.BasicDeliveryTypeDao;
import com.b2c.prototype.dao.payment.base.BasicPaymentMethodDao;
import com.b2c.prototype.dao.user.IUserInfoDao;
import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.dao.user.base.BasicAppUserDao;
import com.b2c.prototype.dao.item.base.BasicBrandDao;
import com.b2c.prototype.dao.bucket.base.BasicBucketDao;
import com.b2c.prototype.dao.payment.base.BasicCardDao;
import com.b2c.prototype.dao.item.base.BasicCurrencyDiscountDao;
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
import com.b2c.prototype.dao.item.ICurrencyDiscountDao;
import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.dao.rating.IRatingDao;
import com.b2c.prototype.dao.review.IReviewDao;
import com.b2c.prototype.dao.user.IAppUserDao;
import com.b2c.prototype.dao.wishlist.IWishListDao;
import com.tm.core.configuration.manager.DatabaseType;
import com.tm.core.configuration.manager.ISessionFactoryManager;
import com.tm.core.configuration.manager.SessionFactoryManager;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.query.ISearchWrapper;
import com.tm.core.dao.query.SearchWrapper;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.scanner.EntityScanner;
import com.tm.core.processor.finder.scanner.IEntityScanner;
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class BeanConfiguration {

    @Value("${entity.package.path}")
    private String entityPackagePath;

    @Bean
    public SessionFactory sessionFactory() {
        ISessionFactoryManager sessionFactoryManager =
                SessionFactoryManager.getInstance("hikari.hibernate.cfg.xml");
        return sessionFactoryManager.getSessionFactorySupplier(DatabaseType.WRITE).get();
    }

    @Bean
    public IThreadLocalSessionManager sessionManager(SessionFactory sessionFactory) {
        return new ThreadLocalSessionManager(sessionFactory);
    }

    @Bean
    public IEntityMappingManager entityMappingManager() {
        return new EntityMappingManager();
    }

    @Bean
    @DependsOn({"entityMappingManager"})
    public IEntityScanner entityScanner() {
        return new EntityScanner(entityMappingManager(), entityPackagePath);
    }

    @Bean
    @DependsOn({"entityScanner"})
    public IEntityIdentifierDao entityIdentifierDao(SessionFactory sessionFactory) {
        return new EntityIdentifierDao(sessionManager(sessionFactory), entityMappingManager());
    }

    @Bean
    public ISearchWrapper searchWrapper() {
        return new SearchWrapper(sessionManager(sessionFactory()), entityIdentifierDao(sessionFactory()));
    }

    @Bean
    public IAppUserDao appUserDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicAppUserDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IPaymentDao paymentDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicPaymentDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IPaymentMethodDao paymentMethodDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicPaymentMethodDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public ICardDao cardDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicCardDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IOrderItemDao orderItemDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicOrderItemDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IOrderStatusDao orderStatusDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicOrderStatusDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IOrderHistoryDao orderHistoryDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicOrderHistoryDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IItemTypeDao itemTypeDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicItemTypeDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public ICurrencyDiscountDao discountDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicCurrencyDiscountDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IBrandDao brandDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicBrandDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public ICategoryDao categoryDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicCategoryDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IItemStatusDao itemStatusDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicItemStatusDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IPostDao postDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicPostDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IOptionGroupDao optionGroupDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicOptionGroupDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IOptionItemDao optionItemDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicOptionItemDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IBucketDao bucketDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicBucketDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IWishListDao wishListDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicWishListDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IItemDao itemDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicItemDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IReviewDao reviewDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicReviewDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IRatingDao ratingDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicRatingDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IDeliveryDao deliveryDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicDeliveryDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IAddressDao addressDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicAddressDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public ICountryDao countryDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicCountryDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IDeliveryTypeDao deliveryTypeDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicDeliveryTypeDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IUserInfoDao userInfoDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicUserInfoDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public ICountryPhoneCodeDao countryPhoneCodeDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicCountryPhoneCodeDao(sessionFactory, entityIdentifierDao);
    }

}
