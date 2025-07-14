package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.ISessionEntityFetcher;
import com.b2c.prototype.dao.SessionEntityFetcher;
import com.b2c.prototype.gateway.IRestClient;
import com.b2c.prototype.gateway.RestClient;
import com.b2c.prototype.manager.address.IUserAddressManager;
import com.b2c.prototype.manager.address.ICountryManager;
import com.b2c.prototype.manager.address.base.UserAddressManager;
import com.b2c.prototype.manager.address.base.CountryManager;
import com.b2c.prototype.manager.delivery.IDeliveryTypeManager;
import com.b2c.prototype.manager.delivery.base.DeliveryTypeManager;
import com.b2c.prototype.manager.item.IArticularItemManager;
import com.b2c.prototype.manager.item.IAvailabilityStatusManager;
import com.b2c.prototype.manager.item.IBrandManager;
import com.b2c.prototype.manager.item.ICategoryManager;
import com.b2c.prototype.manager.item.IDiscountManager;
import com.b2c.prototype.manager.item.IItemDataManager;
import com.b2c.prototype.manager.item.IItemStatusManager;
import com.b2c.prototype.manager.item.IItemTypeManager;
import com.b2c.prototype.manager.item.base.ArticularItemManager;
import com.b2c.prototype.manager.item.base.AvailabilityStatusManager;
import com.b2c.prototype.manager.item.base.BrandManager;
import com.b2c.prototype.manager.item.base.CategoryManager;
import com.b2c.prototype.manager.item.base.DiscountManager;
import com.b2c.prototype.manager.item.base.ItemDataManager;
import com.b2c.prototype.manager.item.base.ArticularStatusManager;
import com.b2c.prototype.manager.item.base.ItemTypeManager;
import com.b2c.prototype.manager.message.IMessageManager;
import com.b2c.prototype.manager.message.IMessageStatusManager;
import com.b2c.prototype.manager.message.IMessageTypeManager;
import com.b2c.prototype.manager.message.base.MessageManager;
import com.b2c.prototype.manager.message.base.MessageStatusManager;
import com.b2c.prototype.manager.message.base.MessageTypeManager;
import com.b2c.prototype.manager.option.IOptionGroupManager;
import com.b2c.prototype.manager.option.IOptionItemManager;
import com.b2c.prototype.manager.option.ITimeDurationOptionManager;
import com.b2c.prototype.manager.option.IZoneOptionManager;
import com.b2c.prototype.manager.option.base.OptionGroupManager;
import com.b2c.prototype.manager.option.base.OptionItemManager;
import com.b2c.prototype.manager.option.base.TimeDurationOptionManager;
import com.b2c.prototype.manager.option.base.ZoneOptionManager;
import com.b2c.prototype.manager.order.ICustomerSingleDeliveryOrderManager;
import com.b2c.prototype.manager.order.IOrderStatusManager;
import com.b2c.prototype.manager.order.base.CustomerSingleDeliveryOrderManager;
import com.b2c.prototype.manager.order.base.OrderStatusManager;
import com.b2c.prototype.manager.payment.ICommissionManager;
import com.b2c.prototype.manager.payment.ICurrencyCoefficientManager;
import com.b2c.prototype.manager.payment.base.CommissionManager;
import com.b2c.prototype.manager.payment.base.CurrencyCoefficientManager;
import com.b2c.prototype.manager.review.IReviewManager;
import com.b2c.prototype.manager.review.base.ReviewManager;
import com.b2c.prototype.manager.store.IStoreAddressManager;
import com.b2c.prototype.manager.store.IStoreManager;
import com.b2c.prototype.manager.store.base.StoreAddressManager;
import com.b2c.prototype.manager.store.base.StoreManager;
import com.b2c.prototype.manager.userdetails.DeviceManager;
import com.b2c.prototype.manager.userdetails.IDeviceManager;
import com.b2c.prototype.manager.userdetails.IUserCreditCardManager;
import com.b2c.prototype.manager.payment.IPaymentMethodManager;
import com.b2c.prototype.manager.userdetails.basic.UserCreditCardManager;
import com.b2c.prototype.manager.payment.base.PaymentMethodManager;
import com.b2c.prototype.manager.post.IPostManager;
import com.b2c.prototype.manager.post.base.PostManager;
import com.b2c.prototype.manager.price.ICurrencyManager;
import com.b2c.prototype.manager.price.base.CurrencyManager;
import com.b2c.prototype.manager.rating.IRatingManager;
import com.b2c.prototype.manager.rating.base.RatingManager;
import com.b2c.prototype.manager.store.ICountTypeManager;
import com.b2c.prototype.manager.store.base.CountTypeManager;
import com.b2c.prototype.manager.userdetails.IContactInfoManager;
import com.b2c.prototype.manager.userdetails.ICountryPhoneCodeManager;
import com.b2c.prototype.manager.userdetails.IUserDetailsManager;
import com.b2c.prototype.manager.userdetails.basic.ContactInfoManager;
import com.b2c.prototype.manager.userdetails.basic.CountryPhoneCodeManager;
import com.b2c.prototype.manager.userdetails.basic.UserDetailsManager;
import com.b2c.prototype.processor.address.UserAddressProcess;
import com.b2c.prototype.processor.address.IUserAddressProcess;
import com.b2c.prototype.processor.commission.ICommissionProcess;
import com.b2c.prototype.processor.commission.CommissionProcess;
import com.b2c.prototype.processor.constant.ConstantProcessorService;
import com.b2c.prototype.processor.constant.IConstantProcessorService;
import com.b2c.prototype.processor.creditcard.IUserCreditCardProcess;
import com.b2c.prototype.processor.creditcard.UserCreditCardProcess;
import com.b2c.prototype.processor.discount.DiscountProcess;
import com.b2c.prototype.processor.discount.IDiscountProcess;
import com.b2c.prototype.processor.item.ArticularItemProcessor;
import com.b2c.prototype.processor.item.CategoryProcess;
import com.b2c.prototype.processor.item.IArticularItemProcessor;
import com.b2c.prototype.processor.item.ICategoryProcess;
import com.b2c.prototype.processor.item.IItemDataProcessor;
import com.b2c.prototype.processor.item.IPostProcess;
import com.b2c.prototype.processor.item.IReviewProcessor;
import com.b2c.prototype.processor.item.ItemDataProcessor;
import com.b2c.prototype.processor.item.PostProcess;
import com.b2c.prototype.processor.item.ReviewProcessor;
import com.b2c.prototype.processor.option.IOptionItemProcessor;
import com.b2c.prototype.processor.option.ITimeDurationOptionProcess;
import com.b2c.prototype.processor.option.IZoneOptionProcess;
import com.b2c.prototype.processor.option.OptionItemProcessor;
import com.b2c.prototype.processor.option.TimeDurationOptionProcess;
import com.b2c.prototype.processor.option.ZoneOptionProcess;
import com.b2c.prototype.processor.order.ICustomerOrderProcessor;
import com.b2c.prototype.processor.order.CustomerOrderProcessor;
import com.b2c.prototype.processor.payment.CurrencyCoefficientProcessor;
import com.b2c.prototype.processor.payment.ICurrencyCoefficientProcessor;
import com.b2c.prototype.processor.store.IStoreAddressProcess;
import com.b2c.prototype.processor.store.IStoreProcess;
import com.b2c.prototype.processor.store.StoreAddressProcess;
import com.b2c.prototype.processor.store.StoreProcess;
import com.b2c.prototype.processor.user.ContactInfoProcess;
import com.b2c.prototype.processor.user.DeviceProcess;
import com.b2c.prototype.processor.user.IContactInfoProcess;
import com.b2c.prototype.processor.user.IDeviceProcess;
import com.b2c.prototype.processor.user.IMessageProcess;
import com.b2c.prototype.processor.user.IUserDetailsProcess;
import com.b2c.prototype.processor.user.MessageProcess;
import com.b2c.prototype.processor.user.UserDetailsProcess;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.transform.function.TransformationFunctionService;
import com.b2c.prototype.transform.help.calculate.IPriceCalculationService;
import com.b2c.prototype.transform.help.calculate.PriceCalculationService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.factory.ParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;
import com.tm.core.process.dao.query.IQueryService;
import com.tm.core.process.dao.IFetchHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfiguration {

    @Value("${thread.pool.size}")
    private int threadCount;

    // support service
    @Bean
    public IRestClient restClient() {
        return new RestClient();
    }

    @Bean
    public IParameterFactory parameterFactory() {
        return new ParameterFactory();
    }

    @Bean
    public ITransformationFunctionService transformationFunctionService() {
        return new TransformationFunctionService();
    }

    @Bean
    public IPriceCalculationService priceCalculationService() {
        return new PriceCalculationService();
    }

    @Bean
    public ISessionEntityFetcher sessionEntityFetcher(IQueryService queryService, IParameterFactory parameterFactory) {
        return new SessionEntityFetcher(queryService, parameterFactory);
    }

    // app service

    @Bean
    public IAvailabilityStatusManager availabilityStatusManager(ITransactionEntityDao availabilityStatusDao,
                                                                IParameterFactory parameterFactory,
                                                                ITransformationFunctionService transformationFunctionService) {
        return new AvailabilityStatusManager(parameterFactory, availabilityStatusDao, transformationFunctionService);
    }

    @Bean
    public IBrandManager brandManager(ITransactionEntityDao brandDao,
                                      IParameterFactory parameterFactory,
                                      ITransformationFunctionService transformationFunctionService) {
        return new BrandManager(parameterFactory, brandDao, transformationFunctionService);
    }

    @Bean
    @Qualifier("countTypeDao")
    public ICountTypeManager countTypeManager(ITransactionEntityDao countTypeDao,
                                              IParameterFactory parameterFactory,
                                              ITransformationFunctionService transformationFunctionService) {
        return new CountTypeManager(parameterFactory, countTypeDao, transformationFunctionService);
    }

    @Bean
    public ICountryPhoneCodeManager countryPhoneCodeManager(ITransactionEntityDao countryPhoneCodeDao,
                                                            IParameterFactory parameterFactory,
                                                            ITransformationFunctionService transformationFunctionService) {
        return new CountryPhoneCodeManager(parameterFactory, countryPhoneCodeDao, transformationFunctionService);
    }

    @Bean
    public ICountryManager countryManager(ITransactionEntityDao countryDao,
                                          IParameterFactory parameterFactory,
                                          ITransformationFunctionService transformationFunctionService) {
        return new CountryManager(parameterFactory, countryDao, transformationFunctionService);
    }

    @Bean
    @Qualifier("currencyDao")
    public ICurrencyManager currencyManager(ITransactionEntityDao currencyDao,
                                            IParameterFactory parameterFactory,
                                            ITransformationFunctionService transformationFunctionService) {
        return new CurrencyManager(parameterFactory, currencyDao, transformationFunctionService);
    }

    @Bean
    @Qualifier("deliveryTypeDao")
    public IDeliveryTypeManager deliveryTypeManager(ITransactionEntityDao deliveryTypeDao,
                                                    IParameterFactory parameterFactory,
                                                    ITransformationFunctionService transformationFunctionService) {
        return new DeliveryTypeManager(parameterFactory, deliveryTypeDao, transformationFunctionService);
    }

    @Bean
    @Qualifier("itemStatusDao")
    public IItemStatusManager itemStatusManager(ITransactionEntityDao itemStatusDao,
                                                IParameterFactory parameterFactory,
                                                ITransformationFunctionService transformationFunctionService) {
        return new ArticularStatusManager(parameterFactory, itemStatusDao, transformationFunctionService);
    }

    @Bean
    @Qualifier("itemTypeDao")
    public IItemTypeManager itemTypeManager(ITransactionEntityDao itemTypeDao,
                                            IParameterFactory parameterFactory,
                                            ITransformationFunctionService transformationFunctionService) {
        return new ItemTypeManager(parameterFactory, itemTypeDao, transformationFunctionService);
    }

    @Bean
    public IMessageStatusManager messageStatusManager(ITransactionEntityDao messageStatusDao,
                                                      IParameterFactory parameterFactory,
                                                      ITransformationFunctionService transformationFunctionService) {
        return new MessageStatusManager(parameterFactory, messageStatusDao, transformationFunctionService);
    }

    @Bean
    @Qualifier("messageTypeDao")
    public IMessageTypeManager messageTypeManager(ITransactionEntityDao messageTypeDao,
                                                  IParameterFactory parameterFactory,
                                                  ITransformationFunctionService transformationFunctionService) {
        return new MessageTypeManager(parameterFactory, messageTypeDao, transformationFunctionService);
    }

    @Bean
    public IMessageManager messageManager(ITransactionEntityDao messageBoxDao,
                                          IFetchHandler fetchHandler,
                                          IQueryService queryService,
                                          ITransformationFunctionService transformationFunctionService,
                                          IParameterFactory parameterFactory) {
        return new MessageManager(messageBoxDao, fetchHandler, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    @Qualifier("optionGroupDao")
    public IOptionGroupManager optionGroupManager(ITransactionEntityDao optionGroupDao,
                                                  IParameterFactory parameterFactory,
                                                  ITransformationFunctionService transformationFunctionService) {
        return new OptionGroupManager(parameterFactory, optionGroupDao, transformationFunctionService);
    }

    @Bean
    public IOrderStatusManager orderStatusManager(ITransactionEntityDao orderStatusDao,
                                                  IParameterFactory parameterFactory,
                                                  ITransformationFunctionService transformationFunctionService) {
        return new OrderStatusManager(parameterFactory, orderStatusDao, transformationFunctionService);
    }

    @Bean
    public IPaymentMethodManager paymentMethodManager(ITransactionEntityDao paymentMethodDao,
                                                      IParameterFactory parameterFactory,
                                                      ITransformationFunctionService transformationFunctionService) {
        return new PaymentMethodManager(parameterFactory, paymentMethodDao, transformationFunctionService);
    }

    @Bean
    public IRatingManager ratingManager(ITransactionEntityDao ratingDao,
                                        IParameterFactory parameterFactory,
                                        ITransformationFunctionService transformationFunctionService) {
        return new RatingManager(parameterFactory, ratingDao, transformationFunctionService);
    }

    @Bean
    public IUserDetailsManager userDetailsManager(ITransactionEntityDao appUserDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  IFetchHandler fetchHandler,
                                                  IParameterFactory parameterFactory) {
        return new UserDetailsManager(appUserDao, transformationFunctionService, fetchHandler, parameterFactory);
    }

    @Bean
    public IDeviceManager deviceManager(ITransactionEntityDao deviceDao,
                                        IFetchHandler fetchHandler,
                                        ITransformationFunctionService transformationFunctionService,
                                        IParameterFactory parameterFactory) {
        return new DeviceManager(deviceDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IContactInfoManager contactInfoManager(ITransactionEntityDao contactInfoDao,
                                                  IFetchHandler fetchHandler,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  IParameterFactory parameterFactory) {
        return new ContactInfoManager(contactInfoDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IDiscountManager discountManager(ITransactionEntityDao discountDao,
                                            IFetchHandler fetchHandler,
                                            ITransformationFunctionService transformationFunctionService,
                                            IParameterFactory parameterFactory) {
        return new DiscountManager(discountDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IItemDataManager itemDataManager(ITransactionEntityDao itemDataDao,
                                            ITransformationFunctionService transformationFunctionService,
                                            IParameterFactory parameterFactory) {
        return new ItemDataManager(itemDataDao, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IArticularItemManager itemDataOptionManager(ITransactionEntityDao itemDataOptionDao,
                                                       IFetchHandler fetchHandler,
                                                       ITransformationFunctionService transformationFunctionService,
                                                       IParameterFactory parameterFactory) {
        return new ArticularItemManager(itemDataOptionDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IOptionItemManager optionItemManager(ITransactionEntityDao optionItemDao,
                                                IFetchHandler fetchHandler,
                                                ITransformationFunctionService transformationFunctionService,
                                                IParameterFactory parameterFactory) {
        return new OptionItemManager(optionItemDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

//    @Bean
//    public IOptionItemCostManager optionItemCostManager(ITransactionEntityDao optionItemCostDao,
//                                                        IQueryService queryService,
//                                                        ITransformationFunctionService transformationFunctionService,
//                                                        IParameterFactory parameterFactory) {
//        return new OptionItemCostManager(optionItemCostDao, queryService, transformationFunctionService, parameterFactory);
//    }

    @Bean
    public ITimeDurationOptionManager timeDurationOptionManager(ITransactionEntityDao timeDurationOptionDao,
                                                                ITransformationFunctionService transformationFunctionService,
                                                                IParameterFactory parameterFactory) {
        return new TimeDurationOptionManager(timeDurationOptionDao, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IZoneOptionManager zoneOptionManager(ITransactionEntityDao zoneOptionDao,
                                                ITransformationFunctionService transformationFunctionService,
                                                IParameterFactory parameterFactory) {
        return new ZoneOptionManager(zoneOptionDao, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IUserCreditCardManager creditCardManager(ITransactionEntityDao cardDao,
                                                    IQueryService queryService,
                                                    IFetchHandler fetchHandler,
                                                    ITransformationFunctionService transformationFunctionService,
                                                    IParameterFactory parameterFactory) {
        return new UserCreditCardManager(cardDao, queryService, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public ICommissionManager commissionManager(ITransactionEntityDao appCommissionDao,
                                                IQueryService queryService,
                                                ITransformationFunctionService transformationFunctionService,
                                                IParameterFactory parameterFactory,
                                                IPriceCalculationService priceCalculationService) {
        return new CommissionManager(appCommissionDao, queryService, transformationFunctionService, parameterFactory, priceCalculationService);
    }

    @Bean
    public IUserAddressManager userAddressManager(ITransactionEntityDao addressDao,
                                              IFetchHandler fetchHandler,
                                              ITransformationFunctionService transformationFunctionService,
                                              IParameterFactory parameterFactory) {
        return new UserAddressManager(addressDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public ICustomerSingleDeliveryOrderManager customerOrderManager(ITransactionEntityDao orderItemDao,
                                                                    ISessionEntityFetcher sessionEntityFetcher,
                                                                    ITransformationFunctionService transformationFunctionService,
                                                                    IParameterFactory parameterFactory,
                                                                    IPriceCalculationService priceCalculationService) {
        return new CustomerSingleDeliveryOrderManager(orderItemDao, sessionEntityFetcher, transformationFunctionService, parameterFactory, priceCalculationService);
    }

    @Bean
    public ICategoryManager categoryManager(ITransactionEntityDao categoryDao,
                                            IQueryService queryService,
                                            ITransformationFunctionService transformationFunctionService,
                                            IParameterFactory parameterFactory) {
        return new CategoryManager(categoryDao, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IPostManager postManager(ITransactionEntityDao postDao,
                                    IQueryService queryService,
                                    ITransformationFunctionService transformationFunctionService,
                                    IParameterFactory parameterFactory) {
        return new PostManager(postDao, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IReviewManager reviewManager(ITransactionEntityDao reviewDao,
                                        IQueryService queryService,
                                        IFetchHandler fetchHandler,
                                        ITransformationFunctionService transformationFunctionService,
                                        IParameterFactory parameterFactory) {
        return new ReviewManager(reviewDao, queryService, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IStoreManager storeManager(ITransactionEntityDao storeDao,
                                      IQueryService queryService,
                                      ITransformationFunctionService transformationFunctionService,
                                      IParameterFactory parameterFactory) {
        return new StoreManager(storeDao, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IStoreAddressManager storeAddressManager(ITransactionEntityDao addressDao,
                                                    IQueryService queryService,
                                                    ITransformationFunctionService transformationFunctionService,
                                                    IParameterFactory parameterFactory) {
        return new StoreAddressManager(addressDao, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public ICurrencyCoefficientManager currencyCoefficientManager(ITransactionEntityDao currencyCoefficientDao,
                                                                  IQueryService queryService,
                                                                  IFetchHandler fetchHandler,
                                                                  ITransformationFunctionService transformationFunctionService,
                                                                  IParameterFactory parameterFactory) {
        return new CurrencyCoefficientManager(currencyCoefficientDao, queryService, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IConstantProcessorService constantOrchestratorService(
            IAvailabilityStatusManager availabilityStatusManager,
            IBrandManager brandManager,
            ICountTypeManager countTypeManager,
            ICountryPhoneCodeManager countryPhoneCodeManager,
            ICountryManager countryManager,
            ICurrencyManager currencyManager,
            IDeliveryTypeManager deliveryTypeManager,
            IItemStatusManager itemStatusManager,
            IItemTypeManager itemTypeManager,
            IMessageStatusManager messageStatusManager,
            IMessageTypeManager messageTypeManager,
            IOptionGroupManager optionGroupManager,
            IOrderStatusManager orderStatusManager,
            IPaymentMethodManager paymentMethodManager,
            IRatingManager ratingManager
    ) {
        return new ConstantProcessorService(
                availabilityStatusManager,
                brandManager,
                countTypeManager,
                countryPhoneCodeManager,
                countryManager,
                currencyManager,
                deliveryTypeManager,
                itemStatusManager,
                itemTypeManager,
                messageStatusManager,
                messageTypeManager,
                optionGroupManager,
                orderStatusManager,
                paymentMethodManager,
                ratingManager
        );
    }


    //process

    @Bean
    public ICategoryProcess categoryProcess(ICategoryManager categoryManager) {
        return new CategoryProcess(categoryManager);
    }

    @Bean
    public IDiscountProcess discountProcess(IDiscountManager discountManager) {
        return new DiscountProcess(discountManager);
    }

    @Bean
    public IItemDataProcessor itemDataProcessor(IItemDataManager itemDataManager) {
        return new ItemDataProcessor(itemDataManager);
    }

    @Bean
    public IArticularItemProcessor itemDataOptionProcessor(IArticularItemManager itemDataOptionManager) {
        return new ArticularItemProcessor(itemDataOptionManager);
    }

    @Bean
    public IOptionItemProcessor optionItemProcessor(IOptionItemManager optionItemManager) {
        return new OptionItemProcessor(optionItemManager);
    }

    @Bean
    public ITimeDurationOptionProcess timeDurationOptionProcess(ITimeDurationOptionManager timeDurationOptionManager) {
        return new TimeDurationOptionProcess(timeDurationOptionManager);
    }

    @Bean
    public IZoneOptionProcess zoneOptionProcess(IZoneOptionManager zoneOptionManager) {
        return new ZoneOptionProcess(zoneOptionManager);
    }

    @Bean
    public ICustomerOrderProcessor customerOrderProcessor(ICustomerSingleDeliveryOrderManager orderItemDataOptionManager) {
        return new CustomerOrderProcessor(orderItemDataOptionManager);
    }

    @Bean
    public IUserDetailsProcess userDetailsProcessor(IUserDetailsManager userDetailsManager) {
        return new UserDetailsProcess(userDetailsManager);
    }

    @Bean
    public IContactInfoProcess contactInfoProcessor(IContactInfoManager contactInfoManager) {
        return new ContactInfoProcess(contactInfoManager);
    }

    @Bean
    public IUserAddressProcess userAddressProcess(IUserAddressManager userAddressManager) {
        return new UserAddressProcess(userAddressManager);
    }

    @Bean
    public IUserCreditCardProcess userCreditCardProcess(IUserCreditCardManager creditCardManager) {
        return new UserCreditCardProcess(creditCardManager);
    }

    @Bean
    public IDeviceProcess deviceProcess(IDeviceManager deviceManager) {
        return new DeviceProcess(deviceManager);
    }

    @Bean
    public ICommissionProcess commissionProcess(ICommissionManager commissionManager) {
        return new CommissionProcess(commissionManager);
    }

    @Bean
    public IStoreProcess storeProcess(IStoreManager storeManager) {
        return new StoreProcess(storeManager);
    }

    @Bean
    public IStoreAddressProcess storeAddressProcess(IStoreAddressManager storeAddressManager) {
        return new StoreAddressProcess(storeAddressManager);
    }

    @Bean
    public IMessageProcess messageProcess(IMessageManager messageManager) {
        return new MessageProcess(messageManager);
    }

    @Bean
    public IPostProcess postProcess(IPostManager postManager) {
        return new PostProcess(postManager);
    }

    @Bean
    public IReviewProcessor reviewProcessor(IReviewManager reviewManager) {
        return new ReviewProcessor(reviewManager);
    }

    @Bean
    public ICurrencyCoefficientProcessor currencyCoefficientProcessor(ICurrencyCoefficientManager currencyCoefficientManager) {
        return new CurrencyCoefficientProcessor(currencyCoefficientManager);
    }
}
