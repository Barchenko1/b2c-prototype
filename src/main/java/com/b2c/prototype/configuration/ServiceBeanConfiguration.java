package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.dao.address.ICountryDao;
import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.item.IAvailabilityStatusDao;
import com.b2c.prototype.dao.item.IBrandDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.dao.message.IMessageBoxDao;
import com.b2c.prototype.dao.message.IMessageStatusDao;
import com.b2c.prototype.dao.message.IMessageTypeDao;
import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.b2c.prototype.dao.option.IOptionItemCostDao;
import com.b2c.prototype.dao.option.IOptionItemDao;
import com.b2c.prototype.dao.option.ITimeDurationOptionDao;
import com.b2c.prototype.dao.option.IZoneOptionDao;
import com.b2c.prototype.dao.order.ICustomerOrderDao;
import com.b2c.prototype.dao.order.IOrderStatusDao;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.payment.IMinMaxCommissionDao;
import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.dao.price.ICurrencyDao;
import com.b2c.prototype.dao.rating.IRatingDao;
import com.b2c.prototype.dao.review.IReviewDao;
import com.b2c.prototype.dao.store.ICountTypeDao;
import com.b2c.prototype.dao.store.IStoreDao;
import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.dao.user.ICountryPhoneCodeDao;
import com.b2c.prototype.dao.user.IDeviceDao;
import com.b2c.prototype.dao.user.IUserDetailsDao;
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
import com.b2c.prototype.manager.option.IOptionItemCostManager;
import com.b2c.prototype.manager.option.IOptionItemManager;
import com.b2c.prototype.manager.option.ITimeDurationOptionManager;
import com.b2c.prototype.manager.option.IZoneOptionManager;
import com.b2c.prototype.manager.option.base.OptionGroupManager;
import com.b2c.prototype.manager.option.base.OptionItemCostManager;
import com.b2c.prototype.manager.option.base.OptionItemManager;
import com.b2c.prototype.manager.option.base.TimeDurationOptionManager;
import com.b2c.prototype.manager.option.base.ZoneOptionManager;
import com.b2c.prototype.manager.order.ICustomerSingleDeliveryOrderManager;
import com.b2c.prototype.manager.order.IOrderStatusManager;
import com.b2c.prototype.manager.order.base.CustomerSingleDeliveryOrderManager;
import com.b2c.prototype.manager.order.base.OrderStatusManager;
import com.b2c.prototype.manager.payment.ICommissionManager;
import com.b2c.prototype.manager.payment.base.CommissionManager;
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
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.function.TransformationFunctionService;
import com.b2c.prototype.service.help.calculate.IPriceCalculationService;
import com.b2c.prototype.service.help.calculate.PriceCalculationService;
import com.b2c.prototype.service.parallel.AsyncProcessor;
import com.b2c.prototype.service.parallel.IAsyncProcessor;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.factory.ParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.dao.query.IFetchHandler;
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
    public IAsyncProcessor asyncProcessor() {
        return new AsyncProcessor(threadCount);
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

    // app service

    @Bean
    public IAvailabilityStatusManager availabilityStatusManager(IAvailabilityStatusDao availabilityStatusDao,
                                                                IParameterFactory parameterFactory,
                                                                ITransformationFunctionService transformationFunctionService) {
        return new AvailabilityStatusManager(parameterFactory, availabilityStatusDao, transformationFunctionService);
    }

    @Bean
    public IBrandManager brandManager(IBrandDao brandDao,
                                      IParameterFactory parameterFactory,
                                      ITransformationFunctionService transformationFunctionService) {
        return new BrandManager(parameterFactory, brandDao, transformationFunctionService);
    }

    @Bean
    public ICountTypeManager countTypeManager(ICountTypeDao countTypeDao,
                                              IParameterFactory parameterFactory,
                                              ITransformationFunctionService transformationFunctionService) {
        return new CountTypeManager(parameterFactory, countTypeDao, transformationFunctionService);
    }

    @Bean
    public ICountryPhoneCodeManager countryPhoneCodeManager(ICountryPhoneCodeDao countryPhoneCodeDao,
                                                            IParameterFactory parameterFactory,
                                                            ITransformationFunctionService transformationFunctionService) {
        return new CountryPhoneCodeManager(parameterFactory, countryPhoneCodeDao, transformationFunctionService);
    }

    @Bean
    public ICountryManager countryManager(ICountryDao countryDao,
                                          IParameterFactory parameterFactory,
                                          ITransformationFunctionService transformationFunctionService) {
        return new CountryManager(parameterFactory, countryDao, transformationFunctionService);
    }

    @Bean
    public ICurrencyManager currencyManager(ICurrencyDao currencyDao,
                                            IParameterFactory parameterFactory,
                                            ITransformationFunctionService transformationFunctionService) {
        return new CurrencyManager(parameterFactory, currencyDao, transformationFunctionService);
    }

    @Bean
    public IDeliveryTypeManager deliveryTypeManager(IDeliveryTypeDao deliveryTypeDao,
                                                    IParameterFactory parameterFactory,
                                                    ITransformationFunctionService transformationFunctionService) {
        return new DeliveryTypeManager(parameterFactory, deliveryTypeDao, transformationFunctionService);
    }

    @Bean
    public IItemStatusManager itemStatusManager(IItemStatusDao itemStatusDao,
                                                IParameterFactory parameterFactory,
                                                ITransformationFunctionService transformationFunctionService) {
        return new ArticularStatusManager(parameterFactory, itemStatusDao, transformationFunctionService);
    }

    @Bean
    public IItemTypeManager itemTypeManager(IItemTypeDao itemTypeDao,
                                            IParameterFactory parameterFactory,
                                            ITransformationFunctionService transformationFunctionService) {
        return new ItemTypeManager(parameterFactory, itemTypeDao, transformationFunctionService);
    }

    @Bean
    public IMessageStatusManager messageStatusManager(IMessageStatusDao messageStatusDao,
                                                      IParameterFactory parameterFactory,
                                                      ITransformationFunctionService transformationFunctionService) {
        return new MessageStatusManager(parameterFactory, messageStatusDao, transformationFunctionService);
    }

    @Bean
    public IMessageTypeManager messageTypeManager(IMessageTypeDao messageTypeDao,
                                                  IParameterFactory parameterFactory,
                                                  ITransformationFunctionService transformationFunctionService) {
        return new MessageTypeManager(parameterFactory, messageTypeDao, transformationFunctionService);
    }

    @Bean
    public IMessageManager messageManager(IMessageBoxDao messageBoxDao,
                                          IFetchHandler fetchHandler,
                                          IQueryService queryService,
                                          ITransformationFunctionService transformationFunctionService,
                                          IParameterFactory parameterFactory) {
        return new MessageManager(messageBoxDao, fetchHandler, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IOptionGroupManager optionGroupManager(IOptionGroupDao optionGroupDao,
                                                  IParameterFactory parameterFactory,
                                                  ITransformationFunctionService transformationFunctionService) {
        return new OptionGroupManager(parameterFactory, optionGroupDao, transformationFunctionService);
    }

    @Bean
    public IOrderStatusManager orderStatusManager(IOrderStatusDao orderStatusDao,
                                                  IParameterFactory parameterFactory,
                                                  ITransformationFunctionService transformationFunctionService) {
        return new OrderStatusManager(parameterFactory, orderStatusDao, transformationFunctionService);
    }

    @Bean
    public IPaymentMethodManager paymentMethodManager(IPaymentMethodDao paymentMethodDao,
                                                      IParameterFactory parameterFactory,
                                                      ITransformationFunctionService transformationFunctionService) {
        return new PaymentMethodManager(parameterFactory, paymentMethodDao, transformationFunctionService);
    }

    @Bean
    public IRatingManager ratingManager(IRatingDao ratingDao,
                                        IParameterFactory parameterFactory,
                                        ITransformationFunctionService transformationFunctionService) {
        return new RatingManager(parameterFactory, ratingDao, transformationFunctionService);
    }

    @Bean
    public IUserDetailsManager userDetailsManager(IUserDetailsDao userDetailsDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  IFetchHandler fetchHandler,
                                                  IParameterFactory parameterFactory) {
        return new UserDetailsManager(userDetailsDao, transformationFunctionService, fetchHandler, parameterFactory);
    }

    @Bean
    public IDeviceManager deviceManager(IDeviceDao deviceDao,
                                        IFetchHandler fetchHandler,
                                        ITransformationFunctionService transformationFunctionService,
                                        IParameterFactory parameterFactory) {
        return new DeviceManager(deviceDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IContactInfoManager contactInfoManager(IContactInfoDao contactInfoDao,
                                                  IFetchHandler fetchHandler,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  IParameterFactory parameterFactory) {
        return new ContactInfoManager(contactInfoDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IDiscountManager discountManager(IDiscountDao discountDao,
                                            IFetchHandler fetchHandler,
                                            ITransformationFunctionService transformationFunctionService,
                                            IParameterFactory parameterFactory) {
        return new DiscountManager(discountDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IItemDataManager itemDataManager(IItemDataDao itemDataDao,
                                            ITransformationFunctionService transformationFunctionService,
                                            IParameterFactory parameterFactory) {
        return new ItemDataManager(itemDataDao, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IArticularItemManager itemDataOptionManager(IItemDataOptionDao itemDataOptionDao,
                                                       IFetchHandler fetchHandler,
                                                       ITransformationFunctionService transformationFunctionService,
                                                       IParameterFactory parameterFactory) {
        return new ArticularItemManager(itemDataOptionDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IOptionItemManager optionItemManager(IOptionItemDao optionItemDao,
                                                IFetchHandler fetchHandler,
                                                ITransformationFunctionService transformationFunctionService,
                                                IParameterFactory parameterFactory) {
        return new OptionItemManager(optionItemDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IOptionItemCostManager optionItemCostManager(IOptionItemCostDao optionItemCostDao,
                                                        IQueryService queryService,
                                                        ITransformationFunctionService transformationFunctionService,
                                                        IParameterFactory parameterFactory) {
        return new OptionItemCostManager(optionItemCostDao, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public ITimeDurationOptionManager timeDurationOptionManager(ITimeDurationOptionDao timeDurationOptionDao,
                                                                ITransformationFunctionService transformationFunctionService,
                                                                IParameterFactory parameterFactory) {
        return new TimeDurationOptionManager(timeDurationOptionDao, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IZoneOptionManager zoneOptionManager(IZoneOptionDao zoneOptionDao,
                                                ITransformationFunctionService transformationFunctionService,
                                                IParameterFactory parameterFactory) {
        return new ZoneOptionManager(zoneOptionDao, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IUserCreditCardManager creditCardManager(ICreditCardDao cardDao,
                                                    IQueryService queryService,
                                                    IFetchHandler fetchHandler,
                                                    ITransformationFunctionService transformationFunctionService,
                                                    IParameterFactory parameterFactory) {
        return new UserCreditCardManager(cardDao, queryService, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public ICommissionManager commissionManager(IMinMaxCommissionDao minMaxCommissionDao,
                                                IQueryService queryService,
                                                ITransformationFunctionService transformationFunctionService,
                                                IParameterFactory parameterFactory) {
        return new CommissionManager(minMaxCommissionDao, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IUserAddressManager userAddressManager(IAddressDao addressDao,
                                              IFetchHandler fetchHandler,
                                              ITransformationFunctionService transformationFunctionService,
                                              IParameterFactory parameterFactory) {
        return new UserAddressManager(addressDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public ICustomerSingleDeliveryOrderManager customerOrderManager(ICustomerOrderDao orderItemDao,
                                                                    IQueryService queryService,
                                                                    IFetchHandler fetchHandler,
                                                                    ITransformationFunctionService transformationFunctionService,
                                                                    IParameterFactory parameterFactory) {
        return new CustomerSingleDeliveryOrderManager(orderItemDao, queryService, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public ICategoryManager categoryManager(ICategoryDao categoryDao,
                                            IQueryService queryService,
                                            ITransformationFunctionService transformationFunctionService,
                                            IParameterFactory parameterFactory) {
        return new CategoryManager(categoryDao, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IPostManager postManager(IPostDao postDao,
                                    IQueryService queryService,
                                    ITransformationFunctionService transformationFunctionService,
                                    IParameterFactory parameterFactory) {
        return new PostManager(postDao, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IReviewManager reviewManager(IReviewDao reviewDao,
                                        IQueryService queryService,
                                        IFetchHandler fetchHandler,
                                        ITransformationFunctionService transformationFunctionService,
                                        IParameterFactory parameterFactory) {
        return new ReviewManager(reviewDao, queryService, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IStoreManager storeManager(IStoreDao storeDao,
                                      IQueryService queryService,
                                      ITransformationFunctionService transformationFunctionService,
                                      IParameterFactory parameterFactory) {
        return new StoreManager(storeDao, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IStoreAddressManager storeAddressManager(IAddressDao addressDao,
                                                    IQueryService queryService,
                                                    ITransformationFunctionService transformationFunctionService,
                                                    IParameterFactory parameterFactory) {
        return new StoreAddressManager(addressDao, queryService, transformationFunctionService, parameterFactory);
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


}
