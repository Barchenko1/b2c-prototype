package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.address.ICountryDao;
import com.b2c.prototype.dao.cashed.EntityCachedMap;
import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.item.IBrandDao;
import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.dao.message.IMessageStatusDao;
import com.b2c.prototype.dao.message.IMessageTypeDao;
import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.b2c.prototype.dao.order.IOrderItemDao;
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
import com.b2c.prototype.dao.item.ICurrencyDiscountDao;
import com.b2c.prototype.dao.embedded.IWishListDao;
import com.b2c.prototype.dao.user.ICountryPhoneCodeDao;
import com.b2c.prototype.processor.AsyncProcessor;
import com.b2c.prototype.processor.IAsyncProcessor;
import com.b2c.prototype.service.processor.address.base.AddressService;
import com.b2c.prototype.service.processor.address.base.CountryService;
import com.b2c.prototype.service.processor.address.IAddressService;
import com.b2c.prototype.service.processor.address.ICountryService;
import com.b2c.prototype.service.processor.delivery.IDeliveryTypeService;
import com.b2c.prototype.service.processor.delivery.base.DeliveryTypeService;
import com.b2c.prototype.service.processor.item.IBrandService;
import com.b2c.prototype.service.processor.item.IItemStatusService;
import com.b2c.prototype.service.processor.item.IItemTypeService;
import com.b2c.prototype.service.processor.item.base.BrandService;
import com.b2c.prototype.service.processor.item.base.CategoryService;
import com.b2c.prototype.service.processor.item.base.ItemStatusService;
import com.b2c.prototype.service.processor.item.base.ItemTypeService;
import com.b2c.prototype.service.processor.message.IMessageStatusService;
import com.b2c.prototype.service.processor.message.IMessageTypeService;
import com.b2c.prototype.service.processor.message.base.MessageStatusService;
import com.b2c.prototype.service.processor.message.base.MessageTypeService;
import com.b2c.prototype.service.processor.option.IOptionGroupService;
import com.b2c.prototype.service.processor.option.base.OptionGroupService;
import com.b2c.prototype.service.processor.order.IOrderItemService;
import com.b2c.prototype.service.processor.order.IOrderStatusService;
import com.b2c.prototype.service.processor.order.base.OrderItemService;
import com.b2c.prototype.service.processor.order.base.OrderStatusService;
import com.b2c.prototype.service.processor.payment.IPaymentMethodService;
import com.b2c.prototype.service.processor.payment.base.PaymentMethodService;
import com.b2c.prototype.service.processor.post.base.PostService;
import com.b2c.prototype.service.processor.price.base.CurrencyService;
import com.b2c.prototype.service.processor.price.ICurrencyService;
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
import com.b2c.prototype.service.processor.payment.ICardService;
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
import com.tm.core.dao.factory.GeneralEntityFactory;
import com.tm.core.dao.factory.IGeneralEntityFactory;
import com.tm.core.dao.query.ISearchWrapper;
import com.tm.core.dao.transaction.ITransactionWrapper;
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
    public IGeneralEntityFactory generalEntityFactory() {
        return new GeneralEntityFactory();
    }

    @Bean
    public IEntityCachedMap entityCachedMap(Map<Class<?>, Map<?, ?>> classEntityMap,
                                            ISearchWrapper searchWrapper) {
        return new EntityCachedMap(classEntityMap, searchWrapper);
    }

    // app service

    @Bean
    public IBrandService brandService(IBrandDao brandDao,
                                      IEntityCachedMap entityCachedMap) {
        return new BrandService(parameterFactory(), brandDao, entityCachedMap);
    }

    @Bean
    public ICountTypeService countTypeService(ICountTypeDao countTypeDao,
                                              IEntityCachedMap entityCachedMap) {
        return new CountTypeService(parameterFactory(), countTypeDao, entityCachedMap);
    }

    @Bean
    public ICountryPhoneCodeService countryPhoneCodeService(ICountryPhoneCodeDao countryPhoneCodeDao,
                                                            IEntityCachedMap entityCachedMap) {
        return new CountryPhoneCodeService(parameterFactory(), countryPhoneCodeDao, entityCachedMap);
    }

    @Bean
    public ICountryService countryService(ICountryDao countryDao,
                                          IEntityCachedMap entityCachedMap) {
        return new CountryService(parameterFactory(), countryDao, entityCachedMap);
    }

    @Bean
    public ICurrencyService currencyService(ICurrencyDao currencyDao,
                                            IEntityCachedMap entityCachedMap) {
        return new CurrencyService(parameterFactory(), currencyDao, entityCachedMap);
    }

    @Bean
    public IDeliveryTypeService deliveryTypeService(IDeliveryTypeDao deliveryTypeDao,
                                                    IEntityCachedMap entityCachedMap) {
        return new DeliveryTypeService(parameterFactory(), deliveryTypeDao, entityCachedMap);
    }

    @Bean
    public IItemStatusService itemStatusService(IItemStatusDao itemStatusDao,
                                                IEntityCachedMap entityCachedMap) {
        return new ItemStatusService(parameterFactory(), itemStatusDao, entityCachedMap);
    }

    @Bean
    public IItemTypeService itemTypeService(IItemTypeDao itemTypeDao, IEntityCachedMap entityCachedMap) {
        return new ItemTypeService(parameterFactory(), itemTypeDao, entityCachedMap);
    }

    @Bean
    public IMessageStatusService messageStatusService(IMessageStatusDao messageStatusDao,
                                                      IEntityCachedMap entityCachedMap) {
        return new MessageStatusService(parameterFactory(), messageStatusDao, entityCachedMap);
    }

    @Bean
    public IMessageTypeService messageTypeService(IMessageTypeDao messageTypeDao,
                                                  IEntityCachedMap entityCachedMap) {
        return new MessageTypeService(parameterFactory(), messageTypeDao, entityCachedMap);
    }

    @Bean
    public IOptionGroupService optionGroupService(IOptionGroupDao optionGroupDao,
                                                  IEntityCachedMap entityCachedMap) {
        return new OptionGroupService(parameterFactory(), optionGroupDao, entityCachedMap);
    }

    @Bean
    public IOrderStatusService orderStatusService(IOrderStatusDao orderStatusDao, IEntityCachedMap entityCachedMap) {
        return new OrderStatusService(parameterFactory(), orderStatusDao, entityCachedMap);
    }

    @Bean
    public IPaymentMethodService paymentMethodService(IPaymentMethodDao paymentMethodDao, IEntityCachedMap entityCachedMap) {
        return new PaymentMethodService(parameterFactory(), paymentMethodDao, entityCachedMap);
    }

    @Bean
    public IRatingService ratingService(IRatingDao ratingDao, IEntityCachedMap entityCachedMap) {
        return new RatingService(parameterFactory(), ratingDao, entityCachedMap);
    }


    @Bean
    public IUserProfileService userProfileService(IParameterFactory parameterFactory,
                                              IGeneralEntityFactory generalEntityFactory,
                                              IUserProfileDao userProfileDao) {
        return new UserProfileService(parameterFactory, generalEntityFactory, userProfileDao);
    }

    @Bean
    public IBucketService bucketService(IBucketDao bucketDao) {
        return new BucketService(bucketDao);
    }

    @Bean
    public IContactInfoService contactInfoService(IParameterFactory parameterFactory,
                                                  IGeneralEntityFactory generalEntityFactory,
                                                  IContactInfoDao contactInfoDao,
                                                  IUserProfileDao userProfileDao,
                                                  IOrderItemDao orderItemDao,
                                                  IEntityCachedMap entityCachedMap) {
        return new ContactInfoService(parameterFactory, generalEntityFactory, contactInfoDao, userProfileDao, orderItemDao, entityCachedMap);
    }

    @Bean
    public IItemService itemService(IAsyncProcessor asyncProcessor,
                                    IItemDao itemDao,
                                    ICurrencyDiscountDao discountDao) {
        return new ItemService(
                asyncProcessor,
                itemDao,
                discountDao
        );
    }

    @Bean
    public ICardService cardService(ICreditCardDao cardDao) {
        return new CreditCardService(cardDao);
    }

    @Bean
    public IPaymentService paymentService(IAsyncProcessor asyncProcessor,
                                          IPaymentDao paymentDao,
                                          ICreditCardDao cardDao,
                                          ICurrencyDiscountDao discountDao,
                                          IEntityCachedMap entityCachedMap) {
        return new PaymentService(
                asyncProcessor,
                paymentDao,
                cardDao,
                discountDao,
                entityCachedMap
        );
    }

    @Bean
    public IAddressService addressService(IParameterFactory parameterFactory,
                                          ITransactionWrapper transactionWrapper,
                                          ISearchWrapper searchWrapper,
                                          IAddressDao addressDao,
                                          IEntityCachedMap entityCachedMap) {
        return new AddressService(parameterFactory, transactionWrapper, searchWrapper, addressDao, entityCachedMap);
    }

    @Bean
    public IDeliveryService deliveryService(IAsyncProcessor asyncProcessor,
                                            IDeliveryDao deliveryDao,
                                            IEntityCachedMap entityCachedMap) {
        return new DeliveryService(
                asyncProcessor,
                deliveryDao,
                entityCachedMap
        );
    }

    @Bean
    public IOrderItemService orderItemService(IAsyncProcessor asyncProcessor,
                                              IOrderItemDao orderItemDao,
                                              IItemDao itemDao,
                                              IContactInfoDao contactInfoDao,
                                              IEntityCachedMap entityCachedMap) {
        return new OrderItemService(
                asyncProcessor,
                orderItemDao,
                itemDao,
                contactInfoDao,
                entityCachedMap
        );
    }

    @Bean
    public IWishListService wishListService(IWishListDao wishListDao) {
        return new WishListService(wishListDao);
    }

    @Bean
    public ICategoryService categoryService(ICategoryDao categoryDao,
                                            IEntityCachedMap entityCachedMap) {
        return new CategoryService(categoryDao, entityCachedMap);
    }

    @Bean
    public IPostService postService(IPostDao postDao) {
        return new PostService(postDao);
    }

}
