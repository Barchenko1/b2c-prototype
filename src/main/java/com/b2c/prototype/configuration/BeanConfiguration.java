package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.address.ICountryDao;
import com.b2c.prototype.dao.address.base.BasicCountryDao;
import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.dao.item.base.BasicDiscountDao;
import com.b2c.prototype.dao.item.base.BasicArticularItemDao;
import com.b2c.prototype.dao.item.base.BasicItemDataDao;
import com.b2c.prototype.dao.message.IMessageStatusDao;
import com.b2c.prototype.dao.message.IMessageTypeDao;
import com.b2c.prototype.dao.message.basic.BasicMessageStatusDao;
import com.b2c.prototype.dao.message.basic.BasicMessageTypeDao;
import com.b2c.prototype.dao.option.ITimeDurationOptionDao;
import com.b2c.prototype.dao.option.IZoneOptionDao;
import com.b2c.prototype.dao.option.base.BasicTimeDurationOptionDao;
import com.b2c.prototype.dao.option.base.BasicZoneOptionDao;
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
import com.b2c.prototype.dao.user.base.BasicUserDetailsDao;
import com.b2c.prototype.dao.item.base.BasicBrandDao;
import com.b2c.prototype.dao.payment.base.BasicCreditCardDao;
import com.b2c.prototype.dao.option.base.BasicOptionItemDao;
import com.b2c.prototype.dao.option.base.BasicOptionGroupDao;
import com.b2c.prototype.dao.order.base.BasicOrderArticularItemQuantityDao;
import com.b2c.prototype.dao.order.base.BasicOrderStatusDao;
import com.b2c.prototype.dao.payment.base.BasicPaymentDao;
import com.b2c.prototype.dao.post.base.BasicPostDao;
import com.b2c.prototype.dao.item.base.BasicItemDao;
import com.b2c.prototype.dao.item.base.BasicItemStatusDao;
import com.b2c.prototype.dao.item.base.BasicItemTypeDao;
import com.b2c.prototype.dao.rating.base.BasicRatingDao;
import com.b2c.prototype.dao.review.base.BasicReviewDao;
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
import com.b2c.prototype.dao.user.IUserDetailsDao;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.query.SearchService;
import com.tm.core.configuration.manager.DatabaseConfigurationAnnotationClass;
import com.tm.core.configuration.manager.DatabaseType;
import com.tm.core.configuration.manager.DatabaseTypeConfiguration;
import com.tm.core.configuration.manager.ISessionFactoryManager;
import com.tm.core.configuration.manager.SessionFactoryManager;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.dao.identifier.QueryService;
import com.tm.core.process.dao.query.FetchHandler;
import com.tm.core.process.dao.query.IFetchHandler;
import com.tm.core.process.dao.transaction.ITransactionHandler;
import com.tm.core.process.dao.transaction.TransactionHandler;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.scanner.EntityScanner;
import com.tm.core.finder.scanner.IEntityScanner;
import com.tm.core.process.manager.generic.GenericEntityOperationManager;
import com.tm.core.process.manager.generic.IGenericOperationManager;
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
    private static final String CACHE_DATABASE_CONFIG = "";
    private static final String MAIN_READ_DATABASE_CONFIG = "";

    public DatabaseTypeConfiguration getDatabaseTypeConfiguration() {
        DatabaseConfigurationAnnotationClass[] databaseConfigurationAnnotationClass =
                new DatabaseConfigurationAnnotationClass[] {
                        new DatabaseConfigurationAnnotationClass("hikari.hibernate.cfg.xml"),
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
    public IQueryService queryService() {
        return new QueryService(entityMappingManager());
    }

    @Bean
    public ITransactionHandler transactionHandler() {
        return new TransactionHandler(sessionFactory());
    }

    @Bean
    public IFetchHandler fetchHandler() {
        return new FetchHandler(sessionFactory(), queryService());
    }

    @Bean
    public ISearchService searchService(IFetchHandler fetchHandler) {
        return new SearchService(fetchHandler);
    }

    @Bean
    public IGenericOperationManager genericOperationManager(SessionFactory sessionFactory) {
        return new GenericEntityOperationManager(sessionFactory, entityPackagePath);
    }

    @Bean
    public ICountTypeDao countTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicCountTypeDao(sessionFactory, queryService);
    }

    @Bean
    public ICurrencyDao currencyDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicCurrencyDao(sessionFactory, queryService);
    }

    @Bean
    public IDiscountDao discountDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicDiscountDao(sessionFactory, queryService);
    }

    @Bean
    public IMessageTypeDao messageTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicMessageTypeDao(sessionFactory, queryService);
    }

    @Bean
    public IMessageStatusDao messageStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicMessageStatusDao(sessionFactory, queryService);
    }

    @Bean
    public IUserDetailsDao appUserDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicUserDetailsDao(sessionFactory, queryService);
    }

    @Bean
    public IPaymentDao paymentDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicPaymentDao(sessionFactory, queryService);
    }

    @Bean
    public IPaymentMethodDao paymentMethodDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicPaymentMethodDao(sessionFactory, queryService);
    }

    @Bean
    public IContactPhoneDao contactPhoneDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicContactPhoneDao(sessionFactory, queryService);
    }

    @Bean
    public ICreditCardDao cardDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicCreditCardDao(sessionFactory, queryService);
    }

    @Bean
    public IOrderItemDataDao orderItemDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicOrderArticularItemQuantityDao(sessionFactory, queryService);
    }

    @Bean
    public IOrderStatusDao orderStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicOrderStatusDao(sessionFactory, queryService);
    }

    @Bean
    public IItemTypeDao itemTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicItemTypeDao(sessionFactory, queryService);
    }

    @Bean
    public IBrandDao brandDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicBrandDao(sessionFactory, queryService);
    }

    @Bean
    public ICategoryDao categoryDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicCategoryDao(sessionFactory, queryService);
    }

    @Bean
    public IItemStatusDao itemStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicItemStatusDao(sessionFactory, queryService);
    }

    @Bean
    public IPostDao postDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicPostDao(sessionFactory, queryService);
    }

    @Bean
    public IOptionGroupDao optionGroupDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicOptionGroupDao(sessionFactory, queryService);
    }

    @Bean
    public IOptionItemDao optionItemDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicOptionItemDao(sessionFactory, queryService);
    }

    @Bean
    public ITimeDurationOptionDao timeDurationOptionDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicTimeDurationOptionDao(sessionFactory, queryService);
    }

    @Bean
    public IZoneOptionDao zoneOptionDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicZoneOptionDao(sessionFactory, queryService);
    }

    @Bean
    public IItemDao itemDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicItemDao(sessionFactory, queryService);
    }

    @Bean
    public IItemDataDao itemDataDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicItemDataDao(sessionFactory, queryService);
    }

    @Bean
    public IItemDataOptionDao itemDataOptionDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicArticularItemDao(sessionFactory, queryService);
    }

    @Bean
    public IReviewDao reviewDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicReviewDao(sessionFactory, queryService);
    }

    @Bean
    public IRatingDao ratingDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicRatingDao(sessionFactory, queryService);
    }

    @Bean
    public IDeliveryDao deliveryDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicDeliveryDao(sessionFactory, queryService);
    }

    @Bean
    public IAddressDao addressDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicAddressDao(sessionFactory, queryService);
    }

    @Bean
    public ICountryDao countryDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicCountryDao(sessionFactory, queryService);
    }

    @Bean
    public IDeliveryTypeDao deliveryTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicDeliveryTypeDao(sessionFactory, queryService);
    }

    @Bean
    public IContactInfoDao contactInfoDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicContactInfoDao(sessionFactory, queryService);
    }

    @Bean
    public ICountryPhoneCodeDao countryPhoneCodeDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicCountryPhoneCodeDao(sessionFactory, queryService);
    }

}
