package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.dao.address.ICountryDao;
import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.item.IBrandDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.dao.message.IMessageStatusDao;
import com.b2c.prototype.dao.message.IMessageTypeDao;
import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.b2c.prototype.dao.option.IOptionItemDao;
import com.b2c.prototype.dao.order.IOrderItemDataDao;
import com.b2c.prototype.dao.order.IOrderStatusDao;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.dao.price.ICurrencyDao;
import com.b2c.prototype.dao.rating.IRatingDao;
import com.b2c.prototype.dao.store.ICountTypeDao;
import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.dao.user.ICountryPhoneCodeDao;
import com.b2c.prototype.dao.user.IUserDetailsDao;
import com.b2c.prototype.gateway.IRestClient;
import com.b2c.prototype.gateway.RestClient;
import com.b2c.prototype.manager.address.IAddressManager;
import com.b2c.prototype.manager.address.ICountryManager;
import com.b2c.prototype.manager.address.base.AddressManager;
import com.b2c.prototype.manager.address.base.CountryManager;
import com.b2c.prototype.manager.delivery.IDeliveryManager;
import com.b2c.prototype.manager.delivery.IDeliveryTypeManager;
import com.b2c.prototype.manager.delivery.base.DeliveryManager;
import com.b2c.prototype.manager.delivery.base.DeliveryTypeManager;
import com.b2c.prototype.manager.item.IArticularItemManager;
import com.b2c.prototype.manager.item.IBrandManager;
import com.b2c.prototype.manager.item.ICategoryManager;
import com.b2c.prototype.manager.item.IDiscountManager;
import com.b2c.prototype.manager.item.IItemDataManager;
import com.b2c.prototype.manager.item.IItemManager;
import com.b2c.prototype.manager.item.IItemStatusManager;
import com.b2c.prototype.manager.item.IItemTypeManager;
import com.b2c.prototype.manager.item.base.ArticularItemManager;
import com.b2c.prototype.manager.item.base.BrandManager;
import com.b2c.prototype.manager.item.base.CategoryManager;
import com.b2c.prototype.manager.item.base.DiscountManager;
import com.b2c.prototype.manager.item.base.ItemDataManager;
import com.b2c.prototype.manager.item.base.ItemManager;
import com.b2c.prototype.manager.item.base.ItemStatusManager;
import com.b2c.prototype.manager.item.base.ItemTypeManager;
import com.b2c.prototype.manager.message.IMessageStatusManager;
import com.b2c.prototype.manager.message.IMessageTypeManager;
import com.b2c.prototype.manager.message.base.MessageStatusManager;
import com.b2c.prototype.manager.message.base.MessageTypeManager;
import com.b2c.prototype.manager.option.IOptionGroupManager;
import com.b2c.prototype.manager.option.IOptionItemManager;
import com.b2c.prototype.manager.option.base.OptionGroupManager;
import com.b2c.prototype.manager.option.base.OptionItemManager;
import com.b2c.prototype.manager.order.IOrderArticularItemQuantityManager;
import com.b2c.prototype.manager.order.IOrderStatusManager;
import com.b2c.prototype.manager.order.base.OrderArticularItemQuantityManager;
import com.b2c.prototype.manager.order.base.OrderStatusManager;
import com.b2c.prototype.manager.payment.ICreditCardManager;
import com.b2c.prototype.manager.payment.IPaymentManager;
import com.b2c.prototype.manager.payment.IPaymentMethodManager;
import com.b2c.prototype.manager.payment.base.CreditCardManager;
import com.b2c.prototype.manager.payment.base.PaymentManager;
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
import com.b2c.prototype.processor.constant.ConstantProcessorService;
import com.b2c.prototype.processor.constant.IConstantProcessorService;
import com.b2c.prototype.processor.discount.DiscountProcess;
import com.b2c.prototype.processor.discount.IDiscountProcess;
import com.b2c.prototype.processor.item.ArticularItemProcessor;
import com.b2c.prototype.processor.item.IArticularItemProcessor;
import com.b2c.prototype.processor.item.IItemDataProcessor;
import com.b2c.prototype.processor.item.ItemDataProcessor;
import com.b2c.prototype.processor.option.IOptionItemProcessor;
import com.b2c.prototype.processor.option.OptionItemProcessor;
import com.b2c.prototype.processor.order.IOrderProcessor;
import com.b2c.prototype.processor.order.OrderProcessor;
import com.b2c.prototype.processor.user.IUserDetailsProcess;
import com.b2c.prototype.processor.user.UserDetailsProcess;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.function.TransformationFunctionService;
import com.b2c.prototype.service.help.calculate.IPriceCalculationService;
import com.b2c.prototype.service.help.calculate.PriceCalculationService;
import com.b2c.prototype.service.parallel.AsyncProcessor;
import com.b2c.prototype.service.parallel.IAsyncProcessor;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.b2c.prototype.service.supplier.SupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.factory.ParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;
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
    public ISupplierService supplierService(IParameterFactory parameterFactory,
                                            ISearchService searchService,
                                            ITransformationFunctionService transformationFunctionService) {
        return new SupplierService(parameterFactory, searchService, transformationFunctionService);
    }

    @Bean
    public IPriceCalculationService priceCalculationService() {
        return new PriceCalculationService();
    }

    // app service

    @Bean
    public IBrandManager brandManager(IBrandDao brandDao,
                                      ITransformationFunctionService transformationFunctionService) {
        return new BrandManager(parameterFactory(), brandDao, transformationFunctionService);
    }

    @Bean
    public ICountTypeManager countTypeManager(ICountTypeDao countTypeDao,
                                              ITransformationFunctionService transformationFunctionService) {
        return new CountTypeManager(parameterFactory(), countTypeDao, transformationFunctionService);
    }

    @Bean
    public ICountryPhoneCodeManager countryPhoneCodeManager(ICountryPhoneCodeDao countryPhoneCodeDao,
                                                            ITransformationFunctionService transformationFunctionService) {
        return new CountryPhoneCodeManager(parameterFactory(), countryPhoneCodeDao, transformationFunctionService);
    }

    @Bean
    public ICountryManager countryManager(ICountryDao countryDao,
                                          ITransformationFunctionService transformationFunctionService) {
        return new CountryManager(parameterFactory(), countryDao, transformationFunctionService);
    }

    @Bean
    public ICurrencyManager currencyManager(ICurrencyDao currencyDao,
                                            ITransformationFunctionService transformationFunctionService) {
        return new CurrencyManager(parameterFactory(), currencyDao, transformationFunctionService);
    }

    @Bean
    public IDeliveryTypeManager deliveryTypeManager(IDeliveryTypeDao deliveryTypeDao,
                                                    ITransformationFunctionService transformationFunctionService) {
        return new DeliveryTypeManager(parameterFactory(), deliveryTypeDao, transformationFunctionService);
    }

    @Bean
    public IItemStatusManager itemStatusManager(IItemStatusDao itemStatusDao,
                                                ITransformationFunctionService transformationFunctionService) {
        return new ItemStatusManager(parameterFactory(), itemStatusDao, transformationFunctionService);
    }

    @Bean
    public IItemTypeManager itemTypeManager(IItemTypeDao itemTypeDao,
                                            ITransformationFunctionService transformationFunctionService) {
        return new ItemTypeManager(parameterFactory(), itemTypeDao, transformationFunctionService);
    }

    @Bean
    public IMessageStatusManager messageStatusManager(IMessageStatusDao messageStatusDao,
                                                      ITransformationFunctionService transformationFunctionService) {
        return new MessageStatusManager(parameterFactory(), messageStatusDao, transformationFunctionService);
    }

    @Bean
    public IMessageTypeManager messageTypeManager(IMessageTypeDao messageTypeDao,
                                                  ITransformationFunctionService transformationFunctionService) {
        return new MessageTypeManager(parameterFactory(), messageTypeDao, transformationFunctionService);
    }

    @Bean
    public IOptionGroupManager optionGroupManager(IOptionGroupDao optionGroupDao,
                                                  ITransformationFunctionService transformationFunctionService) {
        return new OptionGroupManager(parameterFactory(), optionGroupDao, transformationFunctionService);
    }

    @Bean
    public IOrderStatusManager orderStatusManager(IOrderStatusDao orderStatusDao,
                                                  ITransformationFunctionService transformationFunctionService) {
        return new OrderStatusManager(parameterFactory(), orderStatusDao, transformationFunctionService);
    }

    @Bean
    public IPaymentMethodManager paymentMethodManager(IPaymentMethodDao paymentMethodDao,
                                                      ITransformationFunctionService transformationFunctionService) {
        return new PaymentMethodManager(parameterFactory(), paymentMethodDao, transformationFunctionService);
    }

    @Bean
    public IRatingManager ratingManager(IRatingDao ratingDao,
                                        ITransformationFunctionService transformationFunctionService) {
        return new RatingManager(parameterFactory(), ratingDao, transformationFunctionService);
    }

    @Bean
    public IUserDetailsManager userDetailsManager(IUserDetailsDao userDetailsDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  IQueryService queryService,
                                                  IParameterFactory parameterFactory) {
        return new UserDetailsManager(userDetailsDao, transformationFunctionService, queryService, parameterFactory);
    }

    @Bean
    public IContactInfoManager contactInfoManager(IContactInfoDao contactInfoDao,
                                                  ISearchService searchService,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  ISupplierService supplierService,
                                                  IParameterFactory parameterFactory) {
        return new ContactInfoManager(contactInfoDao, searchService, transformationFunctionService, supplierService, parameterFactory);
    }

    @Bean
    public IDiscountManager discountManager(IDiscountDao discountDao,
                                            ISearchService searchService,
                                            IQueryService queryService,
                                            ITransformationFunctionService transformationFunctionService,
                                            ISupplierService supplierService,
                                            IParameterFactory parameterFactory) {
        return new DiscountManager(discountDao, searchService, queryService, transformationFunctionService, supplierService, parameterFactory);
    }

    @Bean
    public IItemManager itemManager(IItemDao itemDao,
                                    ISearchService searchService,
                                    ITransformationFunctionService transformationFunctionService,
                                    ISupplierService supplierService,
                                    IParameterFactory parameterFactory) {
        return new ItemManager(itemDao, searchService, transformationFunctionService, supplierService, parameterFactory);
    }

    @Bean
    public IItemDataManager itemDataManager(IItemDataDao itemDataDao,
                                            ITransformationFunctionService transformationFunctionService,
                                            ISupplierService supplierService,
                                            IParameterFactory parameterFactory) {
        return new ItemDataManager(itemDataDao, transformationFunctionService, supplierService, parameterFactory);
    }

    @Bean
    public IArticularItemManager itemDataOptionManager(IItemDataOptionDao itemDataOptionDao,
                                                       ISearchService searchService,
                                                       ITransformationFunctionService transformationFunctionService,
                                                       ISupplierService supplierService,
                                                       IParameterFactory parameterFactory) {
        return new ArticularItemManager(itemDataOptionDao, searchService, transformationFunctionService, supplierService, parameterFactory);
    }

    @Bean
    public IOptionItemManager optionItemManager(IOptionItemDao optionItemDao,
                                                ISearchService searchService,
                                                ITransformationFunctionService transformationFunctionService,
                                                ISupplierService supplierService,
                                                IParameterFactory parameterFactory) {
        return new OptionItemManager(optionItemDao, searchService, transformationFunctionService, supplierService, parameterFactory);
    }

    @Bean
    public ICreditCardManager creditCardManager(ICreditCardDao cardDao,
                                          ISearchService searchService,
                                          ITransformationFunctionService transformationFunctionService,
                                          ISupplierService supplierService,
                                          IParameterFactory parameterFactory) {
        return new CreditCardManager(cardDao, searchService, transformationFunctionService, supplierService, parameterFactory);
    }

    @Bean
    public IPaymentManager paymentManager(IPaymentDao paymentDao,
                                          ISearchService searchService,
                                          ITransformationFunctionService transformationFunctionService,
                                          ISupplierService supplierService,
                                          IParameterFactory parameterFactory) {
        return new PaymentManager(paymentDao, searchService, transformationFunctionService, supplierService, parameterFactory);
    }

    @Bean
    public IAddressManager addressManager(IAddressDao addressDao,
                                          ISearchService searchService,
                                          ITransformationFunctionService transformationFunctionService,
                                          ISupplierService supplierService,
                                          IParameterFactory parameterFactory) {
        return new AddressManager(addressDao, searchService, transformationFunctionService, supplierService, parameterFactory);
    }

    @Bean
    public IDeliveryManager deliveryManager(IDeliveryDao deliveryDao,
                                            ISearchService searchService,
                                            ITransformationFunctionService transformationFunctionService,
                                            ISupplierService supplierService,
                                            IParameterFactory parameterFactory) {
        return new DeliveryManager(deliveryDao, searchService, transformationFunctionService, supplierService, parameterFactory);
    }

    @Bean
    public IOrderArticularItemQuantityManager orderItemDataManager(IOrderItemDataDao orderItemDao,
                                                                   ITransformationFunctionService transformationFunctionService,
                                                                   ISupplierService supplierService,
                                                                   IParameterFactory parameterFactory) {
        return new OrderArticularItemQuantityManager(orderItemDao, transformationFunctionService, supplierService, parameterFactory);
    }

    @Bean
    public ICategoryManager categoryManager(ICategoryDao categoryDao,
                                            ITransformationFunctionService transformationFunctionService,
                                            ISupplierService supplierService,
                                            IParameterFactory parameterFactory) {
        return new CategoryManager(categoryDao, transformationFunctionService, supplierService, parameterFactory);
    }

    @Bean
    public IPostManager postManager(IPostDao postDao,
                                    ITransformationFunctionService transformationFunctionService,
                                    ISupplierService supplierService,
                                    IParameterFactory parameterFactory) {
        return new PostManager(postDao, transformationFunctionService, supplierService, parameterFactory);
    }

    @Bean
    public IConstantProcessorService constantOrchestratorService(
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


    //parallel

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
    public IOrderProcessor orderProcessor(IOrderArticularItemQuantityManager orderItemDataOptionManager) {
        return new OrderProcessor(orderItemDataOptionManager);
    }

    @Bean
    public IUserDetailsProcess userDetailsProcessor(IUserDetailsManager userDetailsManager) {
        return new UserDetailsProcess(userDetailsManager);
    }
}
