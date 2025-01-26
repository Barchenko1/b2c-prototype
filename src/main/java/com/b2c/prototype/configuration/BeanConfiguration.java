package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.address.ICountryDao;
import com.b2c.prototype.dao.address.base.BasicCountryDao;
import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.dao.item.base.BasicDiscountDao;
import com.b2c.prototype.dao.message.IMessageStatusDao;
import com.b2c.prototype.dao.message.IMessageTypeDao;
import com.b2c.prototype.dao.message.basic.BasicMessageStatusDao;
import com.b2c.prototype.dao.message.basic.BasicMessageTypeDao;
import com.b2c.prototype.dao.price.ICurrencyDao;
import com.b2c.prototype.dao.price.base.BasicCurrencyDao;
import com.b2c.prototype.dao.store.ICountTypeDao;
import com.b2c.prototype.dao.store.base.BasicCountTypeDao;
import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.dao.user.ICountryPhoneCodeDao;
import com.b2c.prototype.dao.user.base.BasicContactPhoneDao;
import com.b2c.prototype.dao.user.base.BasicCountryPhoneCodeDao;
import com.b2c.prototype.dao.user.base.BasicContactInfoDao;
import com.b2c.prototype.dao.item.base.BasicCategoryDao;
import com.b2c.prototype.dao.address.base.BasicAddressDao;
import com.b2c.prototype.dao.delivery.base.BasicDeliveryDao;
import com.b2c.prototype.dao.delivery.base.BasicDeliveryTypeDao;
import com.b2c.prototype.dao.payment.base.BasicPaymentMethodDao;
import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.dao.user.base.BasicUserProfileDao;
import com.b2c.prototype.dao.item.base.BasicBrandDao;
import com.b2c.prototype.dao.embedded.base.BasicBucketDao;
import com.b2c.prototype.dao.payment.base.BasicCreditCardDao;
import com.b2c.prototype.dao.option.base.BasicOptionItemDao;
import com.b2c.prototype.dao.option.base.BasicOptionGroupDao;
import com.b2c.prototype.dao.order.base.BasicOrderItemDataDao;
import com.b2c.prototype.dao.order.base.BasicOrderStatusDao;
import com.b2c.prototype.dao.payment.base.BasicPaymentDao;
import com.b2c.prototype.dao.post.base.BasicPostDao;
import com.b2c.prototype.dao.item.base.BasicItemDao;
import com.b2c.prototype.dao.item.base.BasicItemStatusDao;
import com.b2c.prototype.dao.item.base.BasicItemTypeDao;
import com.b2c.prototype.dao.rating.base.BasicRatingDao;
import com.b2c.prototype.dao.review.base.BasicReviewDao;
import com.b2c.prototype.dao.embedded.base.BasicWishListDao;
import com.b2c.prototype.dao.embedded.IBucketDao;
import com.b2c.prototype.dao.option.IOptionItemDao;
import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.b2c.prototype.dao.order.IOrderItemDataDao;
import com.b2c.prototype.dao.order.IOrderStatusDao;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.dao.item.IBrandDao;
import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.dao.rating.IRatingDao;
import com.b2c.prototype.dao.review.IReviewDao;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.b2c.prototype.dao.embedded.IWishListDao;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.query.QueryService;
import com.tm.core.configuration.manager.DatabaseConfigurationAnnotationClass;
import com.tm.core.configuration.manager.DatabaseType;
import com.tm.core.configuration.manager.DatabaseTypeConfiguration;
import com.tm.core.configuration.manager.ISessionFactoryManager;
import com.tm.core.configuration.manager.SessionFactoryManager;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.query.ISearchWrapper;
import com.tm.core.dao.query.SearchWrapper;
import com.tm.core.dao.transaction.ITransactionWrapper;
import com.tm.core.dao.transaction.TransactionWrapper;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.scanner.EntityScanner;
import com.tm.core.processor.finder.scanner.IEntityScanner;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class BeanConfiguration {

    @Value("${entity.package.path}")
    private String entityPackagePath;
    private static final String MAIN_WRITE_DATABASE_CONFIG = "hikari.hibernate.cfg.xml";
    private static final String CACHE_DATABASE_CONFIG = "embedded.hibernate.cfg.xml";
    private static final String MAIN_READ_DATABASE_CONFIG = "";

    public DatabaseTypeConfiguration getDatabaseTypeConfiguration() {
        DatabaseConfigurationAnnotationClass[] databaseConfigurationAnnotationClass =
                new DatabaseConfigurationAnnotationClass[] {
                        new DatabaseConfigurationAnnotationClass("hikari.hibernate.cfg.xml"),
                        new DatabaseConfigurationAnnotationClass("embedded.hibernate.cfg.xml")
                };

        return new DatabaseTypeConfiguration(DatabaseType.WRITE, databaseConfigurationAnnotationClass);
    }

    @Bean
    public SessionFactory sessionFactory() {
        ISessionFactoryManager sessionFactoryManager = SessionFactoryManager.getInstance(getDatabaseTypeConfiguration());
        return sessionFactoryManager.getSessionFactorySupplier(DatabaseType.WRITE, MAIN_WRITE_DATABASE_CONFIG).get();
    }

    @Bean
    public SessionFactory readSessionFactory() {
        ISessionFactoryManager sessionFactoryManager = SessionFactoryManager.getInstance(getDatabaseTypeConfiguration());
        return sessionFactoryManager.getSessionFactorySupplier(DatabaseType.READ, "").get();
    }

    @Bean
    public SessionFactory embededSessionFactory() {
        ISessionFactoryManager sessionFactoryManager = SessionFactoryManager.getInstance(getDatabaseTypeConfiguration());
        return sessionFactoryManager.getSessionFactorySupplier(DatabaseType.WRITE, CACHE_DATABASE_CONFIG).get();
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
    public IEntityIdentifierDao entityIdentifierDao() {
        return new EntityIdentifierDao(entityMappingManager());
    }

    @Bean
    public ITransactionWrapper transactionWrapper() {
        return new TransactionWrapper(sessionFactory());
    }

    @Bean
    public ISearchWrapper searchWrapper() {
        return new SearchWrapper(sessionFactory(), entityIdentifierDao());
    }

    @Bean
    public IQueryService queryService(ISearchWrapper searchWrapper) {
        return new QueryService(searchWrapper);
    }

    @Bean
    public ICountTypeDao countTypeDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicCountTypeDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public ICurrencyDao currencyDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicCurrencyDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IDiscountDao discountDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicDiscountDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IMessageTypeDao messageTypeDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicMessageTypeDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IMessageStatusDao messageStatusDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicMessageStatusDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IUserProfileDao appUserDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicUserProfileDao(sessionFactory, entityIdentifierDao);
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
    public IContactPhoneDao contactPhoneDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicContactPhoneDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public ICreditCardDao cardDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicCreditCardDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IOrderItemDataDao orderItemDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicOrderItemDataDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IOrderStatusDao orderStatusDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicOrderStatusDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public IItemTypeDao itemTypeDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicItemTypeDao(sessionFactory, entityIdentifierDao);
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
    public IContactInfoDao contactInfoDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicContactInfoDao(sessionFactory, entityIdentifierDao);
    }

    @Bean
    public ICountryPhoneCodeDao countryPhoneCodeDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        return new BasicCountryPhoneCodeDao(sessionFactory, entityIdentifierDao);
    }

}
