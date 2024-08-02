package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.userinfo.IUserInfoDao;
import com.b2c.prototype.dao.bucket.IBucketDao;
import com.b2c.prototype.dao.delivery.IAddressDao;
import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.dao.payment.ICardDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.dao.wishlist.IWishListDao;
import com.b2c.prototype.modal.client.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.client.entity.item.Brand;
import com.b2c.prototype.modal.client.entity.item.ItemStatus;
import com.b2c.prototype.modal.client.entity.item.ItemType;
import com.b2c.prototype.modal.client.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.client.entity.item.Category;
import com.b2c.prototype.processor.IEntityStringMapWrapper;
import com.b2c.prototype.service.client.address.AddressService;
import com.b2c.prototype.service.client.address.IAddressService;
import com.b2c.prototype.service.client.appuser.AppUserService;
import com.b2c.prototype.service.client.appuser.IAppUserService;
import com.b2c.prototype.service.client.payment.IPaymentMethodService;
import com.b2c.prototype.service.client.payment.base.PaymentMethodService;
import com.b2c.prototype.service.client.userinfo.UserInfoService;
import com.b2c.prototype.service.client.userinfo.IUserInfoService;
import com.b2c.prototype.service.client.bucket.BucketService;
import com.b2c.prototype.service.client.bucket.IBucketService;
import com.b2c.prototype.service.client.card.CardService;
import com.b2c.prototype.service.client.card.ICardService;
import com.b2c.prototype.service.client.item.base.CategoryService;
import com.b2c.prototype.service.client.item.ICategoryService;
import com.b2c.prototype.service.client.delivery.base.DeliveryService;
import com.b2c.prototype.service.client.delivery.IDeliveryService;
import com.b2c.prototype.service.client.payment.IPaymentService;
import com.b2c.prototype.service.client.payment.base.PaymentService;
import com.b2c.prototype.service.client.post.IPostService;
import com.b2c.prototype.service.client.post.PostService;
import com.b2c.prototype.service.client.item.IItemService;
import com.b2c.prototype.service.client.item.base.ItemService;
import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.dao.user.IAppUserDao;
import com.b2c.prototype.service.client.wishlist.IWishListService;
import com.b2c.prototype.service.client.wishlist.WishListService;
import com.b2c.prototype.service.gateway.IRestClient;
import com.b2c.prototype.service.gateway.RestClient;
import com.tm.core.processor.ThreadLocalSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ServiceBeanConfiguration {

    // support service
    @Bean
    public IRestClient restClient() {
        return new RestClient();
    }

    @Bean
    public IAppUserService appUserService(IRestClient restClient,
                                          IAppUserDao appUserDao) {
        return new AppUserService(restClient, appUserDao);
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
                                    IEntityStringMapWrapper<Brand> brandIEntityStringMapWrapper,
                                    IEntityStringMapWrapper<ItemStatus> itemStatusIEntityStringMapWrapper,
                                    IEntityStringMapWrapper<ItemType> itemTypeIEntityStringMapWrapper,
                                    IEntityStringMapWrapper<Category> categoryIEntityStringMapWrapper,
                                    IItemDao itemDao,
                                    IDiscountDao discountDao) {
        return new ItemService(
                sessionManager,
                brandIEntityStringMapWrapper,
                itemStatusIEntityStringMapWrapper,
                itemTypeIEntityStringMapWrapper,
                categoryIEntityStringMapWrapper,
                itemDao,
                discountDao
        );
    }

    @Bean
    public ICardService cardService(ICardDao cardDao) {
        return new CardService(cardDao);
    }

    @Bean
    public IPaymentService paymentService(ThreadLocalSessionManager sessionManager,
                                          IPaymentDao paymentDao,
                                          ICardDao cardDao,
                                          IDiscountDao discountDao,
                                          IEntityStringMapWrapper<PaymentMethod> paymentMethodEntityMapWrapper) {
        return new PaymentService(
                sessionManager,
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
    public IDeliveryService deliveryService(IDeliveryDao deliveryDao,
                                            IAddressDao addressDao,
                                            Map<String, DeliveryType> deliveryTypeMap) {
        return new DeliveryService(deliveryDao, addressDao, deliveryTypeMap);
    }

    @Bean
    public IWishListService wishListService(IWishListDao wishListDao) {
        return new WishListService(wishListDao);
    }

    @Bean
    public ICategoryService categoryService(ICategoryDao categoryDao,
                                            IEntityStringMapWrapper<Category> categoryEntityMapWrapper) {
        return new CategoryService(categoryDao, categoryEntityMapWrapper);
    }

    @Bean
    public IPostService postService(IPostDao postDao) {
        return new PostService(postDao);
    }

}
