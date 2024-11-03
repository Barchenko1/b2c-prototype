package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.dao.order.IOrderItemDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.dao.embedded.IBucketDao;
import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.dao.item.ICurrencyDiscountDao;
import com.b2c.prototype.dao.embedded.IWishListDao;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.dao.cashed.IEntityStringMapWrapper;
import com.b2c.prototype.processor.AsyncProcessor;
import com.b2c.prototype.processor.IAsyncProcessor;
import com.b2c.prototype.service.base.address.AddressService;
import com.b2c.prototype.service.base.address.IAddressService;
import com.b2c.prototype.service.base.order.IOrderItemService;
import com.b2c.prototype.service.base.order.base.OrderItemService;
import com.b2c.prototype.service.base.payment.IPaymentMethodService;
import com.b2c.prototype.service.base.payment.base.PaymentMethodService;
import com.b2c.prototype.service.base.post.PostService;
import com.b2c.prototype.service.base.contactinfo.IContactInfoService;
import com.b2c.prototype.service.base.bucket.BucketService;
import com.b2c.prototype.service.base.bucket.IBucketService;
import com.b2c.prototype.service.base.card.CreditCardService;
import com.b2c.prototype.service.base.card.ICardService;
import com.b2c.prototype.service.base.item.base.CategorySingleEntityService;
import com.b2c.prototype.service.base.item.ICategoryService;
import com.b2c.prototype.service.base.delivery.base.DeliveryService;
import com.b2c.prototype.service.base.delivery.IDeliveryService;
import com.b2c.prototype.service.base.payment.IPaymentService;
import com.b2c.prototype.service.base.payment.base.PaymentService;
import com.b2c.prototype.service.base.post.IPostService;
import com.b2c.prototype.service.base.item.IItemService;
import com.b2c.prototype.service.base.item.base.ItemService;
import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.b2c.prototype.service.base.contactinfo.ContactInfoService;
import com.b2c.prototype.service.base.userprofile.IUserProfileService;
import com.b2c.prototype.service.base.userprofile.UserProfileService;
import com.b2c.prototype.service.base.wishlist.IWishListService;
import com.b2c.prototype.service.base.wishlist.WishListService;
import com.b2c.prototype.gateway.IRestClient;
import com.b2c.prototype.gateway.RestClient;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.factory.ParameterFactory;
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

    // app service

    @Bean
    public IUserProfileService appUserService(IRestClient restClient,
                                              IUserProfileDao appUserDao,
                                              IEntityIdentifierDao entityIdentifierDao) {
        return new UserProfileService(restClient, appUserDao, entityIdentifierDao);
    }

    @Bean
    public IBucketService bucketService(IBucketDao bucketDao) {
        return new BucketService(bucketDao);
    }

    @Bean
    public IContactInfoService contactInfoService(IContactInfoDao contactInfoDao) {
        return new ContactInfoService(contactInfoDao);
    }

    @Bean
    public IItemService itemService(IAsyncProcessor asyncProcessor,
                                    IEntityStringMapWrapper<Brand> brandMapWrapper,
                                    IEntityStringMapWrapper<ItemStatus> itemStatusMapWrapper,
                                    IEntityStringMapWrapper<ItemType> itemTypeMapWrapper,
                                    IEntityStringMapWrapper<Category> categoryMapWrapper,
                                    IEntityStringMapWrapper<OptionGroup> optionGroupMapWrapper,
                                    IItemDao itemDao,
                                    ICurrencyDiscountDao discountDao) {
        return new ItemService(
                asyncProcessor,
                brandMapWrapper,
                itemStatusMapWrapper,
                itemTypeMapWrapper,
                categoryMapWrapper,
                optionGroupMapWrapper,
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
                                          IEntityStringMapWrapper<PaymentMethod> paymentMethodEntityMapWrapper) {
        return new PaymentService(
                asyncProcessor,
                paymentDao,
                cardDao,
                discountDao,
                paymentMethodEntityMapWrapper
        );
    }

    @Bean
    public IPaymentMethodService paymentMethodService(IPaymentMethodDao paymentMethodDao,
                                                IEntityStringMapWrapper<PaymentMethod> paymentMethodEntityMapWrapper) {
        return new PaymentMethodService(paymentMethodDao, paymentMethodEntityMapWrapper);
    }

    @Bean
    public IAddressService addressService(IAddressDao addressDao) {
        return new AddressService(addressDao);
    }

    @Bean
    public IDeliveryService deliveryService(IAsyncProcessor asyncProcessor,
                                            IDeliveryDao deliveryDao,
                                            IEntityStringMapWrapper<DeliveryType> deliveryTypeEntityMapWrapper) {
        return new DeliveryService(
                asyncProcessor,
                deliveryDao,
                deliveryTypeEntityMapWrapper
        );
    }

    @Bean
    public IOrderItemService orderItemService(IAsyncProcessor asyncProcessor,
                                              IOrderItemDao orderItemDao,
                                              IItemDao itemDao,
                                              IContactInfoDao contactInfoDao,
                                              IEntityStringMapWrapper<DeliveryType> deliveryTypeMapWrapper,
                                              IEntityStringMapWrapper<PaymentMethod> paymentMethodMapWrapper,
                                              IEntityStringMapWrapper<OrderStatus> orderStatusMapWrapper) {
        return new OrderItemService(
                asyncProcessor,
                orderItemDao,
                itemDao,
                contactInfoDao,
                deliveryTypeMapWrapper,
                paymentMethodMapWrapper,
                orderStatusMapWrapper
        );
    }

    @Bean
    public IWishListService wishListService(IWishListDao wishListDao) {
        return new WishListService(wishListDao);
    }

    @Bean
    public ICategoryService categoryService(ICategoryDao categoryDao,
                                            IEntityStringMapWrapper<Category> categoryEntityMapWrapper) {
        return new CategorySingleEntityService(categoryDao, categoryEntityMapWrapper);
    }

    @Bean
    public IPostService postService(IPostDao postDao) {
        return new PostService(postDao);
    }

}
