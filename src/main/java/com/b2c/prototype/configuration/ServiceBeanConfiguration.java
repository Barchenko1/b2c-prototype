package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.address.ICountryDao;
import com.b2c.prototype.dao.cashed.SingleValueMap;
import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.item.IBrandDao;
import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.dao.message.IMessageStatusDao;
import com.b2c.prototype.dao.message.IMessageTypeDao;
import com.b2c.prototype.dao.option.IOptionGroupDao;
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
import com.b2c.prototype.processor.AsyncProcessor;
import com.b2c.prototype.processor.IAsyncProcessor;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.function.TransformationFunctionService;
import com.b2c.prototype.service.processor.ConstantProcessorService;
import com.b2c.prototype.service.processor.IConstantProcessorService;
import com.b2c.prototype.service.manager.address.base.AddressManager;
import com.b2c.prototype.service.manager.address.base.CountryManager;
import com.b2c.prototype.service.manager.address.IAddressManager;
import com.b2c.prototype.service.manager.address.ICountryManager;
import com.b2c.prototype.service.help.calculate.IPriceCalculationService;
import com.b2c.prototype.service.help.calculate.PriceCalculationService;
import com.b2c.prototype.service.manager.delivery.IDeliveryTypeManager;
import com.b2c.prototype.service.manager.delivery.base.DeliveryTypeManager;
import com.b2c.prototype.service.manager.item.IBrandManager;
import com.b2c.prototype.service.manager.item.IDiscountManager;
import com.b2c.prototype.service.manager.item.IItemStatusManager;
import com.b2c.prototype.service.manager.item.IItemTypeManager;
import com.b2c.prototype.service.manager.item.base.BrandManager;
import com.b2c.prototype.service.manager.item.base.CategoryManager;
import com.b2c.prototype.service.manager.item.base.DiscountManager;
import com.b2c.prototype.service.manager.item.base.ItemStatusManager;
import com.b2c.prototype.service.manager.item.base.ItemTypeManager;
import com.b2c.prototype.service.manager.message.IMessageStatusManager;
import com.b2c.prototype.service.manager.message.IMessageTypeManager;
import com.b2c.prototype.service.manager.message.base.MessageStatusManager;
import com.b2c.prototype.service.manager.message.base.MessageTypeManager;
import com.b2c.prototype.service.manager.option.IOptionGroupManager;
import com.b2c.prototype.service.manager.option.base.OptionGroupManager;
import com.b2c.prototype.service.manager.order.IOrderItemDataManager;
import com.b2c.prototype.service.manager.order.IOrderStatusManager;
import com.b2c.prototype.service.manager.order.base.OrderItemDataManager;
import com.b2c.prototype.service.manager.order.base.OrderStatusManager;
import com.b2c.prototype.service.manager.payment.IPaymentMethodManager;
import com.b2c.prototype.service.manager.payment.base.PaymentMethodManager;
import com.b2c.prototype.service.manager.post.base.PostManager;
import com.b2c.prototype.service.manager.price.base.CurrencyManager;
import com.b2c.prototype.service.manager.price.ICurrencyManager;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.manager.rating.IRatingManager;
import com.b2c.prototype.service.manager.rating.base.RatingManager;
import com.b2c.prototype.service.manager.store.base.CountTypeManager;
import com.b2c.prototype.service.manager.store.ICountTypeManager;
import com.b2c.prototype.service.manager.userprofile.IContactInfoManager;
import com.b2c.prototype.service.manager.userprofile.ICountryPhoneCodeManager;
import com.b2c.prototype.service.manager.userprofile.IUserProfileManager;
import com.b2c.prototype.service.manager.userprofile.basic.CountryPhoneCodeManager;
import com.b2c.prototype.service.manager.userprofile.basic.UserProfileManager;
import com.b2c.prototype.service.embedded.bucket.BucketManager;
import com.b2c.prototype.service.embedded.bucket.IBucketManager;
import com.b2c.prototype.service.manager.payment.base.CreditCardManager;
import com.b2c.prototype.service.manager.payment.ICreditCardManager;
import com.b2c.prototype.service.manager.item.ICategoryManager;
import com.b2c.prototype.service.manager.delivery.base.DeliveryManager;
import com.b2c.prototype.service.manager.delivery.IDeliveryManager;
import com.b2c.prototype.service.manager.payment.IPaymentManager;
import com.b2c.prototype.service.manager.payment.base.PaymentManager;
import com.b2c.prototype.service.manager.post.IPostManager;
import com.b2c.prototype.service.manager.item.IItemManager;
import com.b2c.prototype.service.manager.item.base.ItemManager;
import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.b2c.prototype.service.manager.userprofile.basic.ContactInfoManager;
import com.b2c.prototype.service.embedded.wishlist.IWishListManager;
import com.b2c.prototype.service.embedded.wishlist.WishListManager;
import com.b2c.prototype.gateway.IRestClient;
import com.b2c.prototype.gateway.RestClient;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.b2c.prototype.service.supplier.SupplierService;
import com.tm.core.dao.query.ISearchWrapper;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.factory.ParameterFactory;
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
    public ISingleValueMap singleValueMap(Map<Class<?>, Map<?, ?>> classEntityMap,
                                          ISearchWrapper searchWrapper) {
        return new SingleValueMap(classEntityMap, searchWrapper);
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
    public IBrandManager brandService(IBrandDao brandDao,
                                      ITransformationFunctionService transformationFunctionService,
                                      ISingleValueMap singleValueMap) {
        return new BrandManager(parameterFactory(), brandDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public ICountTypeManager countTypeService(ICountTypeDao countTypeDao,
                                              ITransformationFunctionService transformationFunctionService,
                                              ISingleValueMap singleValueMap) {
        return new CountTypeManager(parameterFactory(), countTypeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public ICountryPhoneCodeManager countryPhoneCodeService(ICountryPhoneCodeDao countryPhoneCodeDao,
                                                            ITransformationFunctionService transformationFunctionService,
                                                            ISingleValueMap singleValueMap) {
        return new CountryPhoneCodeManager(parameterFactory(), countryPhoneCodeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public ICountryManager countryService(ICountryDao countryDao,
                                          ITransformationFunctionService transformationFunctionService,
                                          ISingleValueMap singleValueMap) {
        return new CountryManager(parameterFactory(), countryDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public ICurrencyManager currencyService(ICurrencyDao currencyDao,
                                            ITransformationFunctionService transformationFunctionService,
                                            ISingleValueMap singleValueMap) {
        return new CurrencyManager(parameterFactory(), currencyDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IDeliveryTypeManager deliveryTypeService(IDeliveryTypeDao deliveryTypeDao,
                                                    ITransformationFunctionService transformationFunctionService,
                                                    ISingleValueMap singleValueMap) {
        return new DeliveryTypeManager(parameterFactory(), deliveryTypeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IItemStatusManager itemStatusService(IItemStatusDao itemStatusDao,
                                                ITransformationFunctionService transformationFunctionService,
                                                ISingleValueMap singleValueMap) {
        return new ItemStatusManager(parameterFactory(), itemStatusDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IItemTypeManager itemTypeService(IItemTypeDao itemTypeDao,
                                            ITransformationFunctionService transformationFunctionService,
                                            ISingleValueMap singleValueMap) {
        return new ItemTypeManager(parameterFactory(), itemTypeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IMessageStatusManager messageStatusService(IMessageStatusDao messageStatusDao,
                                                      ITransformationFunctionService transformationFunctionService,
                                                      ISingleValueMap singleValueMap) {
        return new MessageStatusManager(parameterFactory(), messageStatusDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IMessageTypeManager messageTypeService(IMessageTypeDao messageTypeDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  ISingleValueMap singleValueMap) {
        return new MessageTypeManager(parameterFactory(), messageTypeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IOptionGroupManager optionGroupService(IOptionGroupDao optionGroupDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  ISingleValueMap singleValueMap) {
        return new OptionGroupManager(parameterFactory(), optionGroupDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IOrderStatusManager orderStatusService(IOrderStatusDao orderStatusDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  ISingleValueMap singleValueMap) {
        return new OrderStatusManager(parameterFactory(), orderStatusDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IPaymentMethodManager paymentMethodService(IPaymentMethodDao paymentMethodDao,
                                                      ITransformationFunctionService transformationFunctionService,
                                                      ISingleValueMap singleValueMap) {
        return new PaymentMethodManager(parameterFactory(), paymentMethodDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IRatingManager ratingService(IRatingDao ratingDao,
                                        ITransformationFunctionService transformationFunctionService,
                                        ISingleValueMap singleValueMap) {
        return new RatingManager(parameterFactory(), ratingDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IUserProfileManager userProfileService(IUserProfileDao userProfileDao,
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
    public IOrderItemDataManager orderItemDataManager(IOrderItemDataDao orderItemDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  ISupplierService supplierService) {
        return new OrderItemDataManager(orderItemDao, transformationFunctionService, supplierService);
    }

    @Bean
    public IWishListManager wishListManager(IWishListDao wishListDao) {
        return new WishListManager(wishListDao);
    }

    @Bean
    public ICategoryManager categoryManager(ICategoryDao categoryDao,
                                            ISingleValueMap singleValueMap) {
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

}
