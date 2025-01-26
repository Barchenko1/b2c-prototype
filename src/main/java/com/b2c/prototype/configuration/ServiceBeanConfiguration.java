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
import com.b2c.prototype.service.orchestrator.ConstantOrchestratorService;
import com.b2c.prototype.service.orchestrator.IConstantOrchestratorService;
import com.b2c.prototype.service.processor.address.base.AddressService;
import com.b2c.prototype.service.processor.address.base.CountryService;
import com.b2c.prototype.service.processor.address.IAddressService;
import com.b2c.prototype.service.processor.address.ICountryService;
import com.b2c.prototype.service.processor.calculate.IPriceCalculationService;
import com.b2c.prototype.service.processor.calculate.PriceCalculationService;
import com.b2c.prototype.service.processor.delivery.IDeliveryTypeService;
import com.b2c.prototype.service.processor.delivery.base.DeliveryTypeService;
import com.b2c.prototype.service.processor.item.IBrandService;
import com.b2c.prototype.service.processor.item.IDiscountService;
import com.b2c.prototype.service.processor.item.IItemStatusService;
import com.b2c.prototype.service.processor.item.IItemTypeService;
import com.b2c.prototype.service.processor.item.base.BrandService;
import com.b2c.prototype.service.processor.item.base.CategoryService;
import com.b2c.prototype.service.processor.item.base.DiscountService;
import com.b2c.prototype.service.processor.item.base.ItemStatusService;
import com.b2c.prototype.service.processor.item.base.ItemTypeService;
import com.b2c.prototype.service.processor.message.IMessageStatusService;
import com.b2c.prototype.service.processor.message.IMessageTypeService;
import com.b2c.prototype.service.processor.message.base.MessageStatusService;
import com.b2c.prototype.service.processor.message.base.MessageTypeService;
import com.b2c.prototype.service.processor.option.IOptionGroupService;
import com.b2c.prototype.service.processor.option.base.OptionGroupService;
import com.b2c.prototype.service.processor.order.IOrderItemDataService;
import com.b2c.prototype.service.processor.order.IOrderStatusService;
import com.b2c.prototype.service.processor.order.base.OrderItemDataService;
import com.b2c.prototype.service.processor.order.base.OrderStatusService;
import com.b2c.prototype.service.processor.payment.IPaymentMethodService;
import com.b2c.prototype.service.processor.payment.base.PaymentMethodService;
import com.b2c.prototype.service.processor.post.base.PostService;
import com.b2c.prototype.service.processor.price.base.CurrencyService;
import com.b2c.prototype.service.processor.price.ICurrencyService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.processor.rating.IRatingService;
import com.b2c.prototype.service.processor.rating.base.RatingService;
import com.b2c.prototype.service.processor.store.base.CountTypeService;
import com.b2c.prototype.service.processor.store.ICountTypeService;
import com.b2c.prototype.service.processor.userprofile.IContactInfoService;
import com.b2c.prototype.service.processor.userprofile.ICountryPhoneCodeService;
import com.b2c.prototype.service.processor.userprofile.IUserProfileService;
import com.b2c.prototype.service.processor.userprofile.basic.CountryPhoneCodeService;
import com.b2c.prototype.service.processor.userprofile.basic.UserProfileService;
import com.b2c.prototype.service.embedded.bucket.BucketService;
import com.b2c.prototype.service.embedded.bucket.IBucketService;
import com.b2c.prototype.service.processor.payment.base.CreditCardService;
import com.b2c.prototype.service.processor.payment.ICreditCardService;
import com.b2c.prototype.service.processor.item.ICategoryService;
import com.b2c.prototype.service.processor.delivery.base.DeliveryService;
import com.b2c.prototype.service.processor.delivery.IDeliveryService;
import com.b2c.prototype.service.processor.payment.IPaymentService;
import com.b2c.prototype.service.processor.payment.base.PaymentService;
import com.b2c.prototype.service.processor.post.IPostService;
import com.b2c.prototype.service.processor.item.IItemService;
import com.b2c.prototype.service.processor.item.base.ItemService;
import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.b2c.prototype.service.processor.userprofile.basic.ContactInfoService;
import com.b2c.prototype.service.embedded.wishlist.IWishListService;
import com.b2c.prototype.service.embedded.wishlist.WishListService;
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
    public IBrandService brandService(IBrandDao brandDao,
                                      ITransformationFunctionService transformationFunctionService,
                                      ISingleValueMap singleValueMap) {
        return new BrandService(parameterFactory(), brandDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public ICountTypeService countTypeService(ICountTypeDao countTypeDao,
                                              ITransformationFunctionService transformationFunctionService,
                                              ISingleValueMap singleValueMap) {
        return new CountTypeService(parameterFactory(), countTypeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public ICountryPhoneCodeService countryPhoneCodeService(ICountryPhoneCodeDao countryPhoneCodeDao,
                                                            ITransformationFunctionService transformationFunctionService,
                                                            ISingleValueMap singleValueMap) {
        return new CountryPhoneCodeService(parameterFactory(), countryPhoneCodeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public ICountryService countryService(ICountryDao countryDao,
                                          ITransformationFunctionService transformationFunctionService,
                                          ISingleValueMap singleValueMap) {
        return new CountryService(parameterFactory(), countryDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public ICurrencyService currencyService(ICurrencyDao currencyDao,
                                            ITransformationFunctionService transformationFunctionService,
                                            ISingleValueMap singleValueMap) {
        return new CurrencyService(parameterFactory(), currencyDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IDeliveryTypeService deliveryTypeService(IDeliveryTypeDao deliveryTypeDao,
                                                    ITransformationFunctionService transformationFunctionService,
                                                    ISingleValueMap singleValueMap) {
        return new DeliveryTypeService(parameterFactory(), deliveryTypeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IItemStatusService itemStatusService(IItemStatusDao itemStatusDao,
                                                ITransformationFunctionService transformationFunctionService,
                                                ISingleValueMap singleValueMap) {
        return new ItemStatusService(parameterFactory(), itemStatusDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IItemTypeService itemTypeService(IItemTypeDao itemTypeDao,
                                            ITransformationFunctionService transformationFunctionService,
                                            ISingleValueMap singleValueMap) {
        return new ItemTypeService(parameterFactory(), itemTypeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IMessageStatusService messageStatusService(IMessageStatusDao messageStatusDao,
                                                      ITransformationFunctionService transformationFunctionService,
                                                      ISingleValueMap singleValueMap) {
        return new MessageStatusService(parameterFactory(), messageStatusDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IMessageTypeService messageTypeService(IMessageTypeDao messageTypeDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  ISingleValueMap singleValueMap) {
        return new MessageTypeService(parameterFactory(), messageTypeDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IOptionGroupService optionGroupService(IOptionGroupDao optionGroupDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  ISingleValueMap singleValueMap) {
        return new OptionGroupService(parameterFactory(), optionGroupDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IOrderStatusService orderStatusService(IOrderStatusDao orderStatusDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  ISingleValueMap singleValueMap) {
        return new OrderStatusService(parameterFactory(), orderStatusDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IPaymentMethodService paymentMethodService(IPaymentMethodDao paymentMethodDao,
                                                      ITransformationFunctionService transformationFunctionService,
                                                      ISingleValueMap singleValueMap) {
        return new PaymentMethodService(parameterFactory(), paymentMethodDao, transformationFunctionService, singleValueMap);
    }

    @Bean
    public IRatingService ratingService(IRatingDao ratingDao,
                                        ITransformationFunctionService transformationFunctionService,
                                        ISingleValueMap singleValueMap) {
        return new RatingService(parameterFactory(), ratingDao, transformationFunctionService, singleValueMap);
    }


    @Bean
    public IUserProfileService userProfileService(IUserProfileDao userProfileDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  ISupplierService supplierService) {
        return new UserProfileService(userProfileDao, transformationFunctionService, supplierService);
    }

    @Bean
    public IBucketService bucketService(IBucketDao bucketDao) {
        return new BucketService(bucketDao);
    }

    @Bean
    public IContactInfoService contactInfoService(IContactInfoDao contactInfoDao,
                                                  IQueryService queryService,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  ISupplierService supplierService) {
        return new ContactInfoService(contactInfoDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IDiscountService discountService(IDiscountDao discountDao,
                                            IQueryService queryService,
                                            ITransformationFunctionService transformationFunctionService,
                                            ISupplierService supplierService) {
        return new DiscountService(discountDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IItemService itemService(IItemDao itemDao,
                                    IQueryService queryService,
                                    ITransformationFunctionService transformationFunctionService,
                                    ISupplierService supplierService) {
        return new ItemService(itemDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public ICreditCardService cardService(ICreditCardDao cardDao,
                                          IQueryService queryService,
                                          ITransformationFunctionService transformationFunctionService,
                                          ISupplierService supplierService) {
        return new CreditCardService(cardDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IPaymentService paymentService(IPaymentDao paymentDao,
                                          IQueryService queryService,
                                          ITransformationFunctionService transformationFunctionService,
                                          ISupplierService supplierService) {
        return new PaymentService(paymentDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IAddressService addressService(IAddressDao addressDao,
                                          IQueryService queryService,
                                          ITransformationFunctionService transformationFunctionService,
                                          ISupplierService supplierService) {
        return new AddressService(addressDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IDeliveryService deliveryService(IDeliveryDao deliveryDao,
                                            IQueryService queryService,
                                            ITransformationFunctionService transformationFunctionService,
                                            ISupplierService supplierService) {
        return new DeliveryService(deliveryDao, queryService, transformationFunctionService, supplierService);
    }

    @Bean
    public IOrderItemDataService orderItemService(IOrderItemDataDao orderItemDao,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  ISupplierService supplierService) {
        return new OrderItemDataService(orderItemDao, transformationFunctionService, supplierService);
    }

    @Bean
    public IWishListService wishListService(IWishListDao wishListDao) {
        return new WishListService(wishListDao);
    }

    @Bean
    public ICategoryService categoryService(ICategoryDao categoryDao,
                                            ISingleValueMap singleValueMap) {
        return new CategoryService(categoryDao, singleValueMap);
    }

    @Bean
    public IPostService postService(IPostDao postDao) {
        return new PostService(postDao);
    }

    @Bean
    public IConstantOrchestratorService constantOrchestratorService(
            IBrandService brandService,
            ICountTypeService countTypeService,
            ICountryPhoneCodeService countryPhoneCodeService,
            ICountryService countryService,
            ICurrencyService currencyService,
            IDeliveryTypeService deliveryTypeService,
            IItemStatusService itemStatusService,
            IItemTypeService itemTypeService,
            IMessageStatusService messageStatusService,
            IMessageTypeService messageTypeService,
            IOptionGroupService optionGroupService,
            IOrderStatusService orderStatusService,
            IPaymentMethodService paymentMethodService,
            IRatingService ratingService
    ) {
        return new ConstantOrchestratorService(
                brandService,
                countTypeService,
                countryPhoneCodeService,
                countryService,
                currencyService,
                deliveryTypeService,
                itemStatusService,
                itemTypeService,
                messageStatusService,
                messageTypeService,
                optionGroupService,
                orderStatusService,
                paymentMethodService,
                ratingService
        );
    }

}
