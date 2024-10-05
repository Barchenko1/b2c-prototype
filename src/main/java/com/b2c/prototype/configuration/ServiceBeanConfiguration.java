package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.dao.order.IOrderItemDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.user.IUserInfoDao;
import com.b2c.prototype.dao.bucket.IBucketDao;
import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.dao.payment.ICardDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.dao.wishlist.IWishListDao;
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
import com.b2c.prototype.service.base.appuser.AppUserService;
import com.b2c.prototype.service.base.appuser.IAppUserService;
import com.b2c.prototype.service.base.order.IOrderItemService;
import com.b2c.prototype.service.base.order.base.OrderItemService;
import com.b2c.prototype.service.base.payment.IPaymentMethodService;
import com.b2c.prototype.service.base.payment.base.PaymentMethodService;
import com.b2c.prototype.service.base.post.PostService;
import com.b2c.prototype.service.base.userinfo.IUserInfoService;
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
import com.b2c.prototype.dao.user.IAppUserDao;
import com.b2c.prototype.service.base.userinfo.UserInfoService;
import com.b2c.prototype.service.base.wishlist.IWishListService;
import com.b2c.prototype.service.base.wishlist.WishListService;
import com.b2c.prototype.gateway.IRestClient;
import com.b2c.prototype.gateway.RestClient;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.factory.ParameterFactory;
import com.tm.core.processor.finder.scanner.EntityScanner;
import com.tm.core.processor.finder.scanner.IEntityScanner;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class ServiceBeanConfiguration {

    @Value("${thread.pool.size}")
    private int threadCount;

    @Value("${entity.package.path}")
    private String entityPackagePath;

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
    public ThreadLocalSessionManager sessionManager(SessionFactory sessionFactory) {
        return new ThreadLocalSessionManager(sessionFactory);
    }

    @Bean
    public IParameterFactory parameterFactory() {
        return new ParameterFactory();
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
    public IEntityIdentifierDao entityIdentifierDao(SessionFactory sessionFactory) {
        return new EntityIdentifierDao(sessionManager(sessionFactory), entityMappingManager());
    }

    // app service

    @Bean
    public IAppUserService appUserService(IRestClient restClient,
                                          IAppUserDao appUserDao,
                                          IEntityIdentifierDao entityIdentifierDao) {
        return new AppUserService(restClient, appUserDao, entityIdentifierDao);
    }

    @Bean
    public IBucketService bucketService(IBucketDao bucketDao) {
        return new BucketService(bucketDao);
    }

    @Bean
    public IUserInfoService userInfoService(IUserInfoDao userInfoDao) {
        return new UserInfoService(userInfoDao);
    }

    @Bean
    public IItemService itemService(ThreadLocalSessionManager sessionManager,
                                    IAsyncProcessor asyncProcessor,
                                    IEntityStringMapWrapper<Brand> brandMapWrapper,
                                    IEntityStringMapWrapper<ItemStatus> itemStatusMapWrapper,
                                    IEntityStringMapWrapper<ItemType> itemTypeMapWrapper,
                                    IEntityStringMapWrapper<Category> categoryMapWrapper,
                                    IEntityStringMapWrapper<OptionGroup> optionGroupMapWrapper,
                                    IItemDao itemDao,
                                    IDiscountDao discountDao) {
        return new ItemService(
                sessionManager,
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
    public ICardService cardService(ICardDao cardDao) {
        return new CreditCardService(cardDao);
    }

    @Bean
    public IPaymentService paymentService(ThreadLocalSessionManager sessionManager,
                                          IAsyncProcessor asyncProcessor,
                                          IPaymentDao paymentDao,
                                          ICardDao cardDao,
                                          IDiscountDao discountDao,
                                          IEntityStringMapWrapper<PaymentMethod> paymentMethodEntityMapWrapper) {
        return new PaymentService(
                sessionManager,
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
    public IDeliveryService deliveryService(ThreadLocalSessionManager sessionManager,
                                            IAsyncProcessor asyncProcessor,
                                            IDeliveryDao deliveryDao,
                                            IEntityStringMapWrapper<DeliveryType> deliveryTypeEntityMapWrapper) {
        return new DeliveryService(sessionManager,
                asyncProcessor,
                deliveryDao,
                deliveryTypeEntityMapWrapper);
    }

    @Bean
    public IOrderItemService orderItemService(ThreadLocalSessionManager sessionManager,
                                              IAsyncProcessor asyncProcessor,
                                              IOrderItemDao orderItemDao,
                                              IItemDao itemDao,
                                              IUserInfoDao userInfoDao,
                                              IEntityStringMapWrapper<DeliveryType> deliveryTypeMapWrapper,
                                              IEntityStringMapWrapper<PaymentMethod> paymentMethodMapWrapper,
                                              IEntityStringMapWrapper<OrderStatus> orderStatusMapWrapper) {
        return new OrderItemService(
                sessionManager,
                asyncProcessor,
                orderItemDao,
                itemDao,
                userInfoDao,
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
