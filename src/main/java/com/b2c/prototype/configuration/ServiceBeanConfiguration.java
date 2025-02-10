package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.address.ICountryDao;
import com.b2c.prototype.service.scope.ConstantsScope;
import com.b2c.prototype.service.scope.IConstantsScope;
import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.item.IBrandDao;
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
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.price.ICurrencyDao;
import com.b2c.prototype.dao.rating.IRatingDao;
import com.b2c.prototype.dao.store.ICountTypeDao;
import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.dao.embedded.IBucketDao;
import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.dao.embedded.IWishListDao;
import com.b2c.prototype.dao.user.ICountryPhoneCodeDao;
import com.b2c.prototype.manager.option.IOptionItemManager;
import com.b2c.prototype.manager.option.base.OptionItemManager;
import com.b2c.prototype.service.parallel.AsyncProcessor;
import com.b2c.prototype.service.parallel.IAsyncProcessor;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.function.TransformationFunctionService;
import com.b2c.prototype.manager.item.IItemDataManager;
import com.b2c.prototype.manager.item.IArticularItemManager;
import com.b2c.prototype.manager.item.base.ItemDataManager;
import com.b2c.prototype.manager.item.base.ArticularArticularItemManager;
import com.b2c.prototype.processor.constant.ConstantProcessorService;
import com.b2c.prototype.processor.constant.IConstantProcessorService;
import com.b2c.prototype.manager.address.base.AddressManager;
import com.b2c.prototype.manager.address.base.CountryManager;
import com.b2c.prototype.manager.address.IAddressManager;
import com.b2c.prototype.manager.address.ICountryManager;
import com.b2c.prototype.service.help.calculate.IPriceCalculationService;
import com.b2c.prototype.service.help.calculate.PriceCalculationService;
import com.b2c.prototype.manager.delivery.IDeliveryTypeManager;
import com.b2c.prototype.manager.delivery.base.DeliveryTypeManager;
import com.b2c.prototype.manager.item.IBrandManager;
import com.b2c.prototype.manager.item.IDiscountManager;
import com.b2c.prototype.manager.item.IItemStatusManager;
import com.b2c.prototype.manager.item.IItemTypeManager;
import com.b2c.prototype.manager.item.base.BrandManager;
import com.b2c.prototype.manager.item.base.CategoryManager;
import com.b2c.prototype.manager.item.base.DiscountManager;
import com.b2c.prototype.manager.item.base.ItemStatusManager;
import com.b2c.prototype.manager.item.base.ItemTypeManager;
import com.b2c.prototype.manager.message.IMessageStatusManager;
import com.b2c.prototype.manager.message.IMessageTypeManager;
import com.b2c.prototype.manager.message.base.MessageStatusManager;
import com.b2c.prototype.manager.message.base.MessageTypeManager;
import com.b2c.prototype.manager.option.IOptionGroupManager;
import com.b2c.prototype.manager.option.base.OptionGroupManager;
import com.b2c.prototype.manager.order.IOrderItemDataOptionManager;
import com.b2c.prototype.manager.order.IOrderStatusManager;
import com.b2c.prototype.manager.order.base.OrderItemDataOptionManager;
import com.b2c.prototype.manager.order.base.OrderStatusManager;
import com.b2c.prototype.manager.payment.IPaymentMethodManager;
import com.b2c.prototype.manager.payment.base.PaymentMethodManager;
import com.b2c.prototype.manager.post.base.PostManager;
import com.b2c.prototype.manager.price.base.CurrencyManager;
import com.b2c.prototype.manager.price.ICurrencyManager;
import com.b2c.prototype.processor.discount.DiscountProcess;
import com.b2c.prototype.processor.discount.IDiscountProcess;
import com.b2c.prototype.processor.item.IArticularItemProcessor;
import com.b2c.prototype.processor.item.IItemDataProcessor;
import com.b2c.prototype.processor.option.IOptionItemProcessor;
import com.b2c.prototype.processor.option.OptionItemProcessor;
import com.b2c.prototype.processor.order.IOrderProcessor;
import com.b2c.prototype.processor.item.ArticularItemProcessor;
import com.b2c.prototype.processor.item.ItemDataProcessor;
import com.b2c.prototype.processor.order.OrderProcessor;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.manager.rating.IRatingManager;
import com.b2c.prototype.manager.rating.base.RatingManager;
import com.b2c.prototype.manager.store.base.CountTypeManager;
import com.b2c.prototype.manager.store.ICountTypeManager;
import com.b2c.prototype.manager.userprofile.IContactInfoManager;
import com.b2c.prototype.manager.userprofile.ICountryPhoneCodeManager;
import com.b2c.prototype.manager.userprofile.IUserProfileManager;
import com.b2c.prototype.manager.userprofile.basic.CountryPhoneCodeManager;
import com.b2c.prototype.manager.userprofile.basic.UserProfileManager;
import com.b2c.prototype.service.embedded.bucket.BucketManager;
import com.b2c.prototype.service.embedded.bucket.IBucketManager;
import com.b2c.prototype.manager.payment.base.CreditCardManager;
import com.b2c.prototype.manager.payment.ICreditCardManager;
import com.b2c.prototype.manager.item.ICategoryManager;
import com.b2c.prototype.manager.delivery.base.DeliveryManager;
import com.b2c.prototype.manager.delivery.IDeliveryManager;
import com.b2c.prototype.manager.payment.IPaymentManager;
import com.b2c.prototype.manager.payment.base.PaymentManager;
import com.b2c.prototype.manager.post.IPostManager;
import com.b2c.prototype.manager.item.IItemManager;
import com.b2c.prototype.manager.item.base.ItemManager;
import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.b2c.prototype.manager.userprofile.basic.ContactInfoManager;
import com.b2c.prototype.service.embedded.wishlist.IWishListManager;
import com.b2c.prototype.service.embedded.wishlist.WishListManager;
import com.b2c.prototype.gateway.IRestClient;
import com.b2c.prototype.gateway.RestClient;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.b2c.prototype.service.supplier.SupplierService;
import com.tm.core.dao.query.ISearchHandler;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.factory.ParameterFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

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
    public IConstantsScope singleValueMap(Map<Class<?>, Map<?, ?>> classEntityMap,
                                          ISearchHandler searchHandler) {
        return new ConstantsScope(classEntityMap, searchHandler);
    }

    @Bean
    public ITransformationFunctionService transformationFunctionService() {
        return new TransformationFunctionService();
    }

    @Bean
    public ISupplierService supplierService(IParameterFactory parameterFactory,
                                            IQueryService queryService,
                                            ITransformationFunctionService transformationFunctionService) {
        return new SupplierService(parameterFactory, queryService, transformationFunctionService);
    }

    @Bean
    public IPriceCalculationService priceCalculationService() {
        return new PriceCalculationService();
    }

    // app service

    @Bean
    public IBrandManager brandManager(IBrandDao brandDao,
                                      ITransformationFunctionService transformationFunctionService,
                                      IConstantsScope singleValueMap) {
        return new BrandManager(parameterFactory(), brandDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public ICountTypeManager countTypeManager(ICountTypeDao countTypeDao,
                                              ITransformationFunctionService transformationFunctionService,
                                              IConstantsScope singleValueMap) {
        return new CountTypeManager(parameterFactory(), countTypeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public ICountryPhoneCodeManager countryPhoneCodeManager(ICountryPhoneCodeDao countryPhoneCodeDao,
                                                            ITransformationFunctionService transformationFunctionService,
                                                            IConstantsScope singleValueMap) {
        return new CountryPhoneCodeManager(parameterFactory(), countryPhoneCodeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public ICountryManager countryManager(ICountryDao countryDao,
                                          ITransformationFunctionService transformationFunctionService,
                                          IConstantsScope singleValueMap) {
        return new CountryManager(parameterFactory(), countryDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public ICurrencyManager currencyManager(ICurrencyDao currencyDao,
                                            ITransformationFunctionService transformationFunctionService,
                                            IConstantsScope singleValueMap) {
        return new CurrencyManager(parameterFactory(), currencyDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IDeliveryTypeManager deliveryTypeManager(IDeliveryTypeDao deliveryTypeDao,
                                                    ITransformationFunctionService transformationFunctionService,
                                                    IConstantsScope singleValueMap) {
        return new DeliveryTypeManager(parameterFactory(), deliveryTypeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IItemStatusManager itemStatusManager(IItemStatusDao itemStatusDao,
                                                ITransformationFunctionService transformationFunctionService,
                                                IConstantsScope singleValueMap) {
        return new ItemStatusManager(parameterFactory(), itemStatusDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IItemTypeManager itemTypeManager(IItemTypeDao itemTypeDao,
                                            ITransformationFunctionService transformationFunctionService,
                                            IConstantsScope singleValueMap) {
        return new ItemTypeManager(parameterFactory(), itemTypeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IMessageStatusManager messageStatusManager(IMessageStatusDao messageStatusDao,
                                                      ITransformationFunctionService transformationFunctionService,
                                                      IConstantsScope constantsScope) {
        return new MessageStatusManager(parameterFactory(), messageStatusDao, transformationFunctionService, constantsScope);
    }

    @Bean
    public IMessageTypeManager messageTypeManager(IMessageTypeDao messageTypeDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  IConstantsScope singleValueMap) {
        return new MessageTypeManager(parameterFactory(), messageTypeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IOptionGroupManager optionGroupManager(IOptionGroupDao optionGroupDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  IConstantsScope singleValueMap) {
        return new OptionGroupManager(parameterFactory(), optionGroupDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IOrderStatusManager orderStatusManager(IOrderStatusDao orderStatusDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  IConstantsScope constantsScope) {
        return new OrderStatusManager(parameterFactory(), orderStatusDao, transformationFunctionService, constantsScope);
    }

    @Bean
    public IPaymentMethodManager paymentMethodManager(IPaymentMethodDao paymentMethodDao,
                                                      ITransformationFunctionService transformationFunctionService,
                                                      IConstantsScope singleValueMap) {
        return new PaymentMethodManager(parameterFactory(), paymentMethodDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IRatingManager ratingManager(IRatingDao ratingDao,
                                        ITransformationFunctionService transformationFunctionService,
                                        IConstantsScope singleValueMap) {
        return new RatingManager(parameterFactory(), ratingDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IUserProfileManager userProfileManager(IUserProfileDao userProfileDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  ISupplierService supplierService) {
        return new UserProfileManager(userProfileDao, transformationFunctionService, supplierService);
    }

    @Bean
    public IBucketManager bucketManager(IBucketDao bucketDao) {
        return new BucketManager(bucketDao);
    }

    @Bean
    public IContactInfoManager contactInfoManager(IContactInfoDao contactInfoDao,
                                                  IQueryService queryService,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  ISupplierService supplierService) {
        return new ContactInfoManager(contactInfoDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IDiscountManager discountManager(IDiscountDao discountDao,
                                            IQueryService queryService,
                                            ITransformationFunctionService transformationFunctionService,
                                            ISupplierService supplierService) {
        return new DiscountManager(discountDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IItemManager itemManager(IItemDao itemDao,
                                    IQueryService queryService,
                                    ITransformationFunctionService transformationFunctionService,
                                    ISupplierService supplierService) {
        return new ItemManager(itemDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IItemDataManager itemDataManager(IItemDataDao itemDataDao,
                                            IQueryService queryService,
                                            ITransformationFunctionService transformationFunctionService,
                                            ISupplierService supplierService) {
        return new ItemDataManager(itemDataDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IArticularItemManager itemDataOptionManager(IItemDataOptionDao itemDataOptionDao,
                                                       IQueryService queryService,
                                                       ITransformationFunctionService transformationFunctionService,
                                                       ISupplierService supplierService) {
        return new ArticularArticularItemManager(itemDataOptionDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IOptionItemManager optionItemManager(IOptionItemDao optionItemDao,
                                                IQueryService queryService,
                                                ITransformationFunctionService transformationFunctionService,
                                                ISupplierService supplierService) {
        return new OptionItemManager(optionItemDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public ICreditCardManager creditCardManager(ICreditCardDao cardDao,
                                          IQueryService queryService,
                                          ITransformationFunctionService transformationFunctionService,
                                          ISupplierService supplierService) {
        return new CreditCardManager(cardDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IPaymentManager paymentManager(IPaymentDao paymentDao,
                                          IQueryService queryService,
                                          ITransformationFunctionService transformationFunctionService,
                                          ISupplierService supplierService) {
        return new PaymentManager(paymentDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IAddressManager addressManager(IAddressDao addressDao,
                                          IQueryService queryService,
                                          ITransformationFunctionService transformationFunctionService,
                                          ISupplierService supplierService) {
        return new AddressManager(addressDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IDeliveryManager deliveryManager(IDeliveryDao deliveryDao,
                                            IQueryService queryService,
                                            ITransformationFunctionService transformationFunctionService,
                                            ISupplierService supplierService) {
        return new DeliveryManager(deliveryDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IOrderItemDataOptionManager orderItemDataManager(IOrderItemDataDao orderItemDao,
                                                            ITransformationFunctionService transformationFunctionService,
                                                            ISupplierService supplierService) {
        return new OrderItemDataOptionManager(orderItemDao, transformationFunctionService, supplierService);
    }

    @Bean
    public IWishListManager wishListManager(IWishListDao wishListDao) {
        return new WishListManager(wishListDao);
    }

    @Bean
    public ICategoryManager categoryManager(ICategoryDao categoryDao,
                                            IConstantsScope singleValueMap) {
        return new CategoryManager(categoryDao, singleValueMap);
    }

    @Bean
    public IPostManager postManager(IPostDao postDao) {
        return new PostManager(postDao);
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
    public IOrderProcessor orderProcessor(IOrderItemDataOptionManager orderItemDataOptionManager) {
        return new OrderProcessor(orderItemDataOptionManager);
    }
}
