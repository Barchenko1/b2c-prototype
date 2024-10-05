package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.item.IBrandDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.b2c.prototype.dao.order.IOrderStatusDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.rating.IRatingDao;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Configuration
public class InitializeConstantDbConfiguration {

    private final LocalizationInterpreter localizationInterpreter;
    private final Set<Locale> availableLocales;
    private final EnumConfiguration enumConfiguration;
    private final IDeliveryTypeDao deliveryTypeDao;
    private final IPaymentMethodDao paymentMethodDao;
    private final IOrderStatusDao orderStatusDao;
    private final IBrandDao brandDao;
    private final IItemStatusDao itemStatusDao;
    private final IItemTypeDao itemTypeDao;
    private final ICategoryDao categoryDao;
    private final IRatingDao ratingDao;
    private final IOptionGroupDao optionGroupDao;

    public InitializeConstantDbConfiguration(LocalizationInterpreter localizationInterpreter,
                                             Set<Locale> availableLocales,
                                             EnumConfiguration enumConfiguration,
                                             IDeliveryTypeDao deliveryTypeDao,
                                             IPaymentMethodDao paymentMethodDao,
                                             IOrderStatusDao orderStatusDao,
                                             IBrandDao brandDao,
                                             IItemStatusDao itemStatusDao,
                                             IItemTypeDao itemTypeDao,
                                             ICategoryDao categoryDao,
                                             IRatingDao ratingDao,
                                             IOptionGroupDao optionGroupDao) {
        this.localizationInterpreter = localizationInterpreter;
        this.availableLocales = availableLocales;
        this.enumConfiguration = enumConfiguration;
        this.deliveryTypeDao = deliveryTypeDao;
        this.paymentMethodDao = paymentMethodDao;
        this.orderStatusDao = orderStatusDao;
        this.brandDao = brandDao;
        this.itemStatusDao = itemStatusDao;
        this.itemTypeDao = itemTypeDao;
        this.categoryDao = categoryDao;
        this.ratingDao = ratingDao;
        this.optionGroupDao = optionGroupDao;
    }

    @PostConstruct
    public void initializeBrands() {
        Locale defaultLocale = availableLocales.stream()
                .filter(availableLocale -> availableLocale.equals(Locale.ENGLISH))
                .findFirst()
                .orElse(Locale.ENGLISH);

        setupDeliveryTypeEntities(defaultLocale);
        setupPaymentMethodEntities(defaultLocale);
        setupOrderStatusEntities(defaultLocale);

        setupBrandEntities(defaultLocale);
        setupItemStatusEntities(defaultLocale);
        setupItemTypesEntities(defaultLocale);



        setupOptionGroupEntities(defaultLocale);

    }

    private void setupDeliveryTypeEntities(Locale defaultLocale) {
        List<String> deliveryTypeList = enumConfiguration.getDeliveryTypes();
        List<DeliveryType> existingDeliveryType = deliveryTypeDao.getEntityList();

        if (deliveryTypeList != null) {
            deliveryTypeList.stream()
                    .map(deliveryTypeName -> localizationInterpreter.interpret("delivery.type", deliveryTypeName.toLowerCase(), defaultLocale))
                    .filter(localizedName ->
                            existingDeliveryType.stream()
                                    .noneMatch(deliveryType -> deliveryType.getName().equals(localizedName)))
                    .map(deliveryTypeName -> DeliveryType.builder()
                            .name(deliveryTypeName)
                            .build())
                    .forEach(deliveryTypeDao::saveEntity);
        }
    }

    private void setupPaymentMethodEntities(Locale defaultLocale) {
        List<String> paymentMethodList = enumConfiguration.getPaymentMethods();
        List<PaymentMethod> existingPaymentMethod = paymentMethodDao.getEntityList();

        if (paymentMethodList != null) {
            paymentMethodList.stream()
                    .map(paymentMethodName -> localizationInterpreter.interpret("payment.method", paymentMethodName.toLowerCase(), defaultLocale))
                    .filter(localizedName -> existingPaymentMethod.stream()
                                    .noneMatch(paymentMethod -> paymentMethod.getMethod().equals(localizedName)))
                    .map(paymentMethodName -> PaymentMethod.builder()
                            .method(paymentMethodName)
                            .build())
                    .forEach(paymentMethodDao::saveEntity);
        }
    }

    private void setupOrderStatusEntities(Locale defaultLocale) {
        List<String> orderStatusList = enumConfiguration.getOrderStatuses();
        List<OrderStatus> existingOrderStatus = orderStatusDao.getEntityList();

        if (orderStatusList != null) {
            orderStatusList.stream()
                    .map(orderStatusName -> localizationInterpreter.interpret("item.status", orderStatusName.toLowerCase(), defaultLocale))
                    .filter(localizedName ->
                            existingOrderStatus.stream()
                                    .noneMatch(orderStatus -> orderStatus.getName().equals(localizedName)))
                    .map(orderStatusName -> OrderStatus.builder()
                            .name(orderStatusName)
                            .build())
                    .forEach(orderStatusDao::saveEntity);
        }
    }

    private void setupBrandEntities(Locale defaultLocale) {
        List<String> brandList = enumConfiguration.getBrands();
        List<Brand> existingBrands = brandDao.getEntityList();

        if (brandList != null) {
            brandList.stream()
                    .map(brandName -> localizationInterpreter.interpret("brand", brandName.toLowerCase(), defaultLocale))
                    .filter(localizedName ->
                            existingBrands.stream()
                                    .noneMatch(brand -> brand.getName().equals(localizedName)))
                    .map(brandName -> Brand.builder()
                            .name(brandName)
                            .build())
                    .forEach(brandDao::saveEntity);
        }
    }

    private void setupItemStatusEntities(Locale defaultLocale) {
        List<String> itemStatusList = enumConfiguration.getItemStatuses();
        List<ItemStatus> existItemStatus = itemStatusDao.getEntityList();

        if (itemStatusList != null) {
            itemStatusList.stream()
                    .map(itemStatusName -> localizationInterpreter.interpret("item.status", itemStatusName.toLowerCase(), defaultLocale))
                    .filter(localizedName ->
                            existItemStatus.stream()
                                    .noneMatch(itemStatus -> itemStatus.getName().equals(localizedName)))
                    .map(itemStatusName -> ItemStatus.builder()
                            .name(itemStatusName)
                            .build())
                    .forEach(itemStatusDao::saveEntity);
        }
    }

    private void setupItemTypesEntities(Locale defaultLocale) {
        List<String> itemTypeList = enumConfiguration.getItemTypes();
        List<ItemType> existItemTypes = itemTypeDao.getEntityList();

        if (itemTypeList != null) {
            itemTypeList.stream()
                    .map(itemTypeName -> localizationInterpreter.interpret("item.type", itemTypeName.toLowerCase(), defaultLocale))
                    .filter(localizedName ->
                            existItemTypes.stream()
                                    .noneMatch(itemType -> itemType.getName().equals(localizedName)))
                    .map(itemTypeName -> ItemType.builder()
                            .name(itemTypeName)
                            .build())
                    .forEach(itemTypeDao::saveEntity);
        }
    }

    private void setupOptionGroupEntities(Locale defaultLocale) {
        List<String> optionGroupList = enumConfiguration.getItemTypes();
        List<ItemType> existOptionGroup = optionGroupDao.getEntityList();

        if (optionGroupList != null) {
            optionGroupList.stream()
                    .map(optionGroupName -> localizationInterpreter.interpret("option.group", optionGroupName.toLowerCase(), defaultLocale))
                    .filter(localizedName ->
                            existOptionGroup.stream()
                                    .noneMatch(optionGroup -> optionGroup.getName().equals(localizedName)))
                    .map(optionGroupName -> OptionGroup.builder()
                            .name(optionGroupName)
                            .build())
                    .forEach(optionGroupDao::saveEntity);
        }
    }


}
