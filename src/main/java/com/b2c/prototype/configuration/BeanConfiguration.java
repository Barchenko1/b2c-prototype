package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.address.BasicCountryDao;
import com.b2c.prototype.dao.item.BasicAvailabilityStatusDao;
import com.b2c.prototype.dao.item.BasicDiscountDao;
import com.b2c.prototype.dao.item.BasicArticularItemDao;
import com.b2c.prototype.dao.item.BasicItemDataDao;
import com.b2c.prototype.dao.message.BasicMessageDao;
import com.b2c.prototype.dao.message.BasicMessageBoxDao;
import com.b2c.prototype.dao.message.BasicMessageStatusDao;
import com.b2c.prototype.dao.message.BasicMessageTypeDao;
import com.b2c.prototype.dao.option.BasicTimeDurationOptionDao;
import com.b2c.prototype.dao.option.BasicZoneOptionDao;
import com.b2c.prototype.dao.payment.BasicMinMaxCommissionDao;
import com.b2c.prototype.dao.payment.CurrencyCoefficientDao;
import com.b2c.prototype.dao.price.BasicCurrencyDao;
import com.b2c.prototype.dao.store.BasicCountTypeDao;
import com.b2c.prototype.dao.store.BasicStoreDao;
import com.b2c.prototype.dao.user.BasicContactPhoneDao;
import com.b2c.prototype.dao.user.BasicCountryPhoneCodeDao;
import com.b2c.prototype.dao.user.BasicContactInfoDao;
import com.b2c.prototype.dao.item.BasicCategoryDao;
import com.b2c.prototype.dao.address.BasicAddressDao;
import com.b2c.prototype.dao.delivery.BasicDeliveryDao;
import com.b2c.prototype.dao.delivery.BasicDeliveryTypeDao;
import com.b2c.prototype.dao.payment.BasicPaymentMethodDao;
import com.b2c.prototype.dao.user.BasicDeviceDao;
import com.b2c.prototype.dao.user.BasicUserDetailsDao;
import com.b2c.prototype.dao.item.BasicBrandDao;
import com.b2c.prototype.dao.payment.BasicCreditCardDao;
import com.b2c.prototype.dao.option.BasicOptionItemDao;
import com.b2c.prototype.dao.option.BasicOptionGroupDao;
import com.b2c.prototype.dao.order.BasicCustomerOrderDao;
import com.b2c.prototype.dao.order.BasicOrderStatusDao;
import com.b2c.prototype.dao.payment.BasicPaymentDao;
import com.b2c.prototype.dao.post.BasicPostDao;
import com.b2c.prototype.dao.item.BasicItemDao;
import com.b2c.prototype.dao.item.BasicItemStatusDao;
import com.b2c.prototype.dao.item.BasicItemTypeDao;
import com.b2c.prototype.dao.rating.BasicRatingDao;
import com.b2c.prototype.dao.review.BasicReviewDao;
import com.tm.core.configuration.dbType.DatabaseConfigurationAnnotationClass;
import com.tm.core.configuration.dbType.DatabaseType;
import com.tm.core.configuration.dbType.DatabaseTypeConfiguration;
import com.tm.core.configuration.entityManager.EntityManagerFactoryManager;
import com.tm.core.configuration.entityManager.IEntityManagerFactoryManager;
import com.tm.core.configuration.session.ISessionFactoryManager;
import com.tm.core.configuration.session.SessionFactoryManager;
import com.tm.core.process.dao.IFetchHandler;
import com.tm.core.process.dao.common.ITransactionEntityDao;
import com.tm.core.process.dao.fetch.SessionFetchHandler;
import com.tm.core.process.dao.generic.IGenericDao;
import com.tm.core.process.dao.generic.session.GenericSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import com.tm.core.process.dao.query.QueryService;
import com.tm.core.process.dao.transaction.ITransactionHandler;
import com.tm.core.process.dao.transaction.SessionTransactionHandler;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.scanner.EntityScanner;
import com.tm.core.finder.scanner.IEntityScanner;
import com.tm.core.process.manager.generic.operator.GenericOperationManager;
import com.tm.core.process.manager.generic.IGenericOperationManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public EntityManager entityManager() {
        IEntityManagerFactoryManager entityManagerFactoryManager = EntityManagerFactoryManager.getInstance(getDatabaseTypeConfiguration());
        EntityManagerFactory entityManagerFactory =
                entityManagerFactoryManager.getEntityManagerFactory(DatabaseType.WRITE, MAIN_WRITE_DATABASE_CONFIG);
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public SessionFactory sessionFactory() {
        ISessionFactoryManager sessionFactoryManager = SessionFactoryManager.getInstance(getDatabaseTypeConfiguration());
        return sessionFactoryManager.getSessionFactory(DatabaseType.WRITE, MAIN_WRITE_DATABASE_CONFIG);
    }

    @Bean
    public SessionFactory readSessionFactory() {
        ISessionFactoryManager sessionFactoryManager = SessionFactoryManager.getInstance(getDatabaseTypeConfiguration());
        return sessionFactoryManager.getSessionFactory(DatabaseType.READ, MAIN_READ_DATABASE_CONFIG);
    }

    @Bean
    public SessionFactory embededSessionFactory() {
        ISessionFactoryManager sessionFactoryManager = SessionFactoryManager.getInstance(getDatabaseTypeConfiguration());
        return sessionFactoryManager.getSessionFactory(DatabaseType.WRITE, CACHE_DATABASE_CONFIG);
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
        return new SessionTransactionHandler(sessionFactory());
    }

    @Bean
    public IFetchHandler fetchHandler() {
        return new SessionFetchHandler(sessionFactory(), queryService());
    }

    @Bean
    public IGenericDao genericDao(SessionFactory sessionFactory) {
        return new GenericSessionFactoryDao(sessionFactory, entityPackagePath);
    }

    @Bean
    public IGenericOperationManager genericOperationManager(IGenericDao genericDao) {
        return new GenericOperationManager(genericDao);
    }

    @Bean
    public ITransactionEntityDao countTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicCountTypeDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao currencyDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicCurrencyDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao discountDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicDiscountDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao messageTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicMessageTypeDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao messageStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicMessageStatusDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao messageDao(SessionFactory sessionFactory,
                                            IQueryService queryService) {
        return new BasicMessageDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao messageBoxDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicMessageBoxDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao appUserDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicUserDetailsDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao deviceDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicDeviceDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao appCommissionDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicMinMaxCommissionDao(sessionFactory, queryService);
    }

//    @Bean
//    public IBuyerCommissionDao buyerCommissionDao(SessionFactory sessionFactory, IQueryService queryService) {
//        return new BasicBuyerCommissionDao(sessionFactory, queryService);
//    }

    @Bean
    public ITransactionEntityDao paymentDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicPaymentDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao paymentMethodDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicPaymentMethodDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao contactPhoneDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicContactPhoneDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao cardDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicCreditCardDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao orderItemDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicCustomerOrderDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao orderStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicOrderStatusDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao itemTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicItemTypeDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao availabilityStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicAvailabilityStatusDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao brandDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicBrandDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao categoryDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicCategoryDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao itemStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicItemStatusDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao postDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicPostDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao optionGroupDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicOptionGroupDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao optionItemDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicOptionItemDao(sessionFactory, queryService);
    }

//    @Bean
//    public ITransactionEntityDao optionItemCostDao(SessionFactory sessionFactory, IQueryService queryService) {
//        return new BasicOptionItemCostDao(sessionFactory, queryService);
//    }

    @Bean
    public ITransactionEntityDao timeDurationOptionDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicTimeDurationOptionDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao zoneOptionDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicZoneOptionDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao itemDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicItemDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao itemDataDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicItemDataDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao itemDataOptionDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicArticularItemDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao reviewDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicReviewDao(sessionFactory, queryService);
    }

    @Bean
    @Qualifier(value = "ratingDao")
    public ITransactionEntityDao ratingDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicRatingDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao deliveryDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicDeliveryDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao addressDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicAddressDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao countryDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicCountryDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao deliveryTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicDeliveryTypeDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao contactInfoDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicContactInfoDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao countryPhoneCodeDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicCountryPhoneCodeDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao storeDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new BasicStoreDao(sessionFactory, queryService);
    }

    @Bean
    public ITransactionEntityDao currencyCoefficientDao(SessionFactory sessionFactory, IQueryService queryService) {
        return new CurrencyCoefficientDao(sessionFactory, queryService);
    }
}
