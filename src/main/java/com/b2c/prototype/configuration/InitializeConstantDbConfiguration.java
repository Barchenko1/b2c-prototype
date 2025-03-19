package com.b2c.prototype.configuration;

import com.b2c.prototype.configuration.modal.ApplicationProperty;
import com.b2c.prototype.configuration.modal.TransitiveSelfYaml;
import com.b2c.prototype.dao.address.ICountryDao;
import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.item.IBrandDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.dao.message.IMessageStatusDao;
import com.b2c.prototype.dao.message.IMessageTypeDao;
import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.b2c.prototype.dao.order.IOrderStatusDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.price.ICurrencyDao;
import com.b2c.prototype.dao.rating.IRatingDao;
import com.b2c.prototype.dao.store.ICountTypeDao;
import com.b2c.prototype.dao.user.ICountryPhoneCodeDao;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.util.CategoryUtil;
import com.tm.core.process.dao.transaction.ITransactionHandler;
import jakarta.annotation.PostConstruct;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Configuration
public class InitializeConstantDbConfiguration {

    private final LocalizationInterpreter localizationInterpreter;
    private final Set<Locale> availableLocales;
    private final ApplicationPropertyConfiguration applicationPropertyConfiguration;
    private final ITransactionHandler transactionHandler;
    private final IBrandDao brandDao;
    private final ICountTypeDao countTypeDao;
    private final ICountryPhoneCodeDao countryPhoneCodeDao;
    private final ICountryDao countryDao;
    private final ICurrencyDao currencyDao;
    private final IDeliveryTypeDao deliveryTypeDao;
    private final IItemStatusDao itemStatusDao;
    private final IItemTypeDao itemTypeDao;
    private final IMessageStatusDao messageStatusDao;
    private final IMessageTypeDao messageTypeDao;
    private final IOptionGroupDao optionGroupDao;
    private final IOrderStatusDao orderStatusDao;
    private final IPaymentMethodDao paymentMethodDao;
    private final IRatingDao ratingDao;

    private final ICategoryDao categoryDao;

    public InitializeConstantDbConfiguration(LocalizationInterpreter localizationInterpreter,
                                             Set<Locale> availableLocales,
                                             ApplicationPropertyConfiguration applicationPropertyConfiguration,
                                             ITransactionHandler transactionHandler,
                                             IBrandDao brandDao,
                                             ICountTypeDao countTypeDao,
                                             ICountryPhoneCodeDao countryPhoneCodeDao,
                                             ICountryDao countryDao,
                                             ICurrencyDao currencyDao,
                                             IDeliveryTypeDao deliveryTypeDao,
                                             IItemStatusDao itemStatusDao,
                                             IItemTypeDao itemTypeDao,
                                             IMessageStatusDao messageStatusDao,
                                             IMessageTypeDao messageTypeDao,
                                             IOptionGroupDao optionGroupDao,
                                             IOrderStatusDao orderStatusDao,
                                             IPaymentMethodDao paymentMethodDao,
                                             IRatingDao ratingDao,
                                             ICategoryDao categoryDao) {
        this.localizationInterpreter = localizationInterpreter;
        this.availableLocales = availableLocales;
        this.applicationPropertyConfiguration = applicationPropertyConfiguration;
        this.transactionHandler = transactionHandler;
        this.brandDao = brandDao;
        this.countTypeDao = countTypeDao;
        this.countryPhoneCodeDao = countryPhoneCodeDao;
        this.countryDao = countryDao;
        this.currencyDao = currencyDao;
        this.deliveryTypeDao = deliveryTypeDao;
        this.itemStatusDao = itemStatusDao;
        this.itemTypeDao = itemTypeDao;
        this.messageStatusDao = messageStatusDao;
        this.messageTypeDao = messageTypeDao;
        this.optionGroupDao = optionGroupDao;
        this.orderStatusDao = orderStatusDao;
        this.paymentMethodDao = paymentMethodDao;
        this.ratingDao = ratingDao;
        this.categoryDao = categoryDao;
    }


    @PostConstruct
    public void initializeBrands() {
        Locale defaultLocale = availableLocales.stream()
                .filter(availableLocale -> availableLocale.equals(Locale.ENGLISH))
                .findFirst()
                .orElse(Locale.ENGLISH);

        initializeBrandEntities(defaultLocale);
        initializeCountTypes(defaultLocale);
        initializeCountryPhoneCode(defaultLocale);
        initializeCountries(defaultLocale);
        initializeCurrencies(defaultLocale);

        initializeDeliveryTypeEntities(defaultLocale);
        initializePaymentMethodEntities(defaultLocale);
        initializeOrderStatusEntities(defaultLocale);
        initializeItemStatusEntities(defaultLocale);
        initializeItemTypesEntities(defaultLocale);

        initializeMessageStatuses(defaultLocale);
        initializeMessageTypes(defaultLocale);

        initializeOptionGroupEntities(defaultLocale);
        initializeRatingEntities();

//        initializeCategory(defaultLocale);

    }

    private void initializeCountTypes(Locale defaultLocale) {
        Set<ApplicationProperty> countTypeNames = applicationPropertyConfiguration.getCountTypes();
        List<CountType> existingCountTypes = countTypeDao.getNamedQueryEntityList("CountType.all");

        if (countTypeNames != null) {
            countTypeNames.stream()
                    .filter(ap -> existingCountTypes.stream()
                            .noneMatch(countType -> countType.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> CountType.builder()
                            .label(localizationInterpreter.interpret("country_phone_code", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(countTypeDao::persistEntity);
        }
    }

    private void initializeCountryPhoneCode(Locale defaultLocale) {
        Set<ApplicationProperty> countryPhoneCodes = applicationPropertyConfiguration.getCountryPhoneCodes();
        List<CountryPhoneCode> existingCountryPhoneCodes = countryPhoneCodeDao.getNamedQueryEntityList("CountryPhoneCode.all");

        if (countryPhoneCodes != null) {
            countryPhoneCodes.stream()
                    .filter(ap -> existingCountryPhoneCodes.stream()
                            .noneMatch(countryPhoneCode -> countryPhoneCode.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> CountryPhoneCode.builder()
                            .label(localizationInterpreter.interpret("country_phone_code", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(countryPhoneCodeDao::persistEntity);
        }
    }

    private void initializeCountries(Locale defaultLocale) {
        Set<ApplicationProperty> countrySet = applicationPropertyConfiguration.getCountries();
        List<Country> existingCountries = countryDao.getNamedQueryEntityList("Country.all");

        if (countrySet != null) {
            countrySet.stream()
                    .filter(ap -> existingCountries.stream()
                            .noneMatch(country -> country.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> Country.builder()
                            .label(localizationInterpreter.interpret("country", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(countryDao::persistEntity);
        }
    }

    private void initializeCurrencies(Locale defaultLocale) {
        Set<ApplicationProperty> currencySet = applicationPropertyConfiguration.getCurrencies();
        List<Currency> existingCurrencies = currencyDao.getNamedQueryEntityList("Currency.all");

        if (currencySet != null) {
            currencySet.stream()
                    .filter(ap -> existingCurrencies.stream()
                            .noneMatch(currency -> currency.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> Currency.builder()
                            .label(localizationInterpreter.interpret("currency", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(currencyDao::persistEntity);
        }
    }

    private void initializeDeliveryTypeEntities(Locale defaultLocale) {
        Set<ApplicationProperty> deliveryTypeSet = applicationPropertyConfiguration.getDeliveryTypes();
        List<DeliveryType> existingDeliveryTypeList = deliveryTypeDao.getNamedQueryEntityList("DeliveryType.all");

        if (deliveryTypeSet != null) {
            deliveryTypeSet.stream()
                    .filter(ap -> existingDeliveryTypeList.stream()
                            .noneMatch(deliveryType -> deliveryType.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> DeliveryType.builder()
                            .label(localizationInterpreter.interpret("delivery.type", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(deliveryTypeDao::persistEntity);
        }
    }

    private void initializePaymentMethodEntities(Locale defaultLocale) {
        Set<ApplicationProperty> paymentMethodSet = applicationPropertyConfiguration.getPaymentMethods();
        List<PaymentMethod> existingPaymentMethodList = paymentMethodDao.getNamedQueryEntityList("PaymentMethod.all");

        if (paymentMethodSet != null) {
            paymentMethodSet.stream()
                    .filter(ap -> existingPaymentMethodList.stream()
                            .noneMatch(orderStatus -> orderStatus.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> OrderStatus.builder()
                            .label(localizationInterpreter.interpret("payment.method", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(paymentMethodDao::persistEntity);
        }
    }

    private void initializeOrderStatusEntities(Locale defaultLocale) {
        Set<ApplicationProperty> applicationPropertySet = applicationPropertyConfiguration.getOrderStatuses();
        List<OrderStatus> existingOrderStatus = orderStatusDao.getNamedQueryEntityList("OrderStatus.all");

        if (applicationPropertySet != null) {
            applicationPropertySet.stream()
                    .filter(ap -> existingOrderStatus.stream()
                            .noneMatch(orderStatus -> orderStatus.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> OrderStatus.builder()
                            .label(localizationInterpreter.interpret("order.status", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(orderStatusDao::persistEntity);
        }
    }

    private void initializeBrandEntities(Locale defaultLocale) {
        Set<ApplicationProperty> brandSet = applicationPropertyConfiguration.getBrands();
        List<Brand> existingBrands = brandDao.getNamedQueryEntityList("Brand.all");

        if (brandSet != null) {
            brandSet.stream()
                    .filter(ap -> existingBrands.stream()
                            .noneMatch(brand -> brand.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> Brand.builder()
                            .label(localizationInterpreter.interpret("brand", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(brandDao::persistEntity);
        }
    }

    private void initializeItemStatusEntities(Locale defaultLocale) {
        Set<ApplicationProperty> itemStatuses = applicationPropertyConfiguration.getItemStatuses();
        List<ArticularStatus> existArticularStatuses = itemStatusDao.getNamedQueryEntityList("ArticularStatus.all");

        if (itemStatuses != null) {
            itemStatuses.stream()
                    .filter(ap -> existArticularStatuses.stream()
                            .noneMatch(itemStatus -> itemStatus.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> ArticularStatus.builder()
                            .label(localizationInterpreter.interpret("item.status", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(itemStatusDao::persistEntity);
        }
    }

    private void initializeItemTypesEntities(Locale defaultLocale) {
        Set<ApplicationProperty> itemTypeSet = applicationPropertyConfiguration.getItemTypes();
        List<ItemType> existItemTypes = itemTypeDao.getNamedQueryEntityList("ItemType.all");

        if (itemTypeSet != null) {
            itemTypeSet.stream()
                    .filter(ap -> existItemTypes.stream()
                            .noneMatch(itemStatus -> itemStatus.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> ArticularStatus.builder()
                            .label(localizationInterpreter.interpret("item.type", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(itemTypeDao::persistEntity);
        }
    }

    private void initializeMessageStatuses(Locale defaultLocale) {
        Set<ApplicationProperty> messageStatuses = applicationPropertyConfiguration.getMessageStatuses();
        List<MessageStatus> existingMessageStatuses = messageStatusDao.getNamedQueryEntityList("MessageStatus.all");

        if (messageStatuses != null) {
            messageStatuses.stream()
                    .filter(ap -> existingMessageStatuses.stream()
                            .noneMatch(messageStatus -> messageStatus.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> ArticularStatus.builder()
                            .label(localizationInterpreter.interpret("message.status", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(messageStatusDao::persistEntity);
        }
    }

    private void initializeMessageTypes(Locale defaultLocale) {
        Set<ApplicationProperty> messageTypes = applicationPropertyConfiguration.getMessageTypes();
        List<MessageType> existingMessageTypes = messageTypeDao.getNamedQueryEntityList("MessageType.all");

        if (messageTypes != null) {
            messageTypes.stream()
                    .filter(ap -> existingMessageTypes.stream()
                            .noneMatch(messageStatus -> messageStatus.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> ArticularStatus.builder()
                            .label(localizationInterpreter.interpret("message.type", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(messageStatusDao::persistEntity);
        }
    }

    private void initializeOptionGroupEntities(Locale defaultLocale) {
        Set<ApplicationProperty> optionGroupSet = applicationPropertyConfiguration.getOptionGroups();
        List<OptionGroup> existOptionGroupList = optionGroupDao.getNamedQueryEntityList("OptionGroup.all");

        if (optionGroupSet != null) {
            optionGroupSet.stream()
                    .filter(ap -> existOptionGroupList.stream()
                            .noneMatch(og -> og.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> ArticularStatus.builder()
                            .label(localizationInterpreter.interpret("option.group", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(messageStatusDao::persistEntity);
        }
    }

    private void initializeRatingEntities() {
        Set<Integer> ratingList = applicationPropertyConfiguration.getRatings();
        List<Rating> existRatingList = ratingDao.getNamedQueryEntityList("Rating.all");

        if (ratingList != null) {
            ratingList.stream()
                    .filter(rating ->
                            existRatingList.stream()
                                    .noneMatch(existRating -> existRating.getValue().equals(rating)))
                    .map(rating -> Rating.builder()
                            .value(rating)
                            .build())
                    .forEach(ratingDao::persistEntity);
        }
    }

    private void initializeCategory(Locale defaultLocale) {
        Set<TransitiveSelfYaml> categories = applicationPropertyConfiguration.getCategories();
//        List<Category> existCategoryList = categoryDao.getTransitiveSelfEntityList();
//        List<TransitiveSelfYaml> configYamlList = extractTransitiveSelfYaml(categories);
//        List<TransitiveSelfYaml> nonExistingCategories = findNonExistingCategories(configYamlList, existCategoryList);
//        Map<Category, List<TransitiveSelfYaml>> nonExistingCategoriesMap = findNonExistingCategoriesMap(categories, existCategoryList);
//        if (!nonExistingCategoriesMap.isEmpty()) {
//            buildNewCategories(nonExistingCategoriesMap);
//        } else {
//            categories.stream()
//                    .map(CategoryUtil::buildCategory);
////                    .forEach(categoryDao::saveEntityTree);
//        }
    }

    private void buildNewCategories(Map<Category, List<TransitiveSelfYaml>> nonExistingCategoriesMap) {
        nonExistingCategoriesMap.forEach((key, value) -> {
//            List<Category> newCategories = value.stream()
//                    .map(CategoryUtil::buildCategory)
//                    .toList();
//            newCategories.forEach(newCategory -> {
//                key.addChildEntity(newCategory);
//                Consumer<Session> consumer = session -> {
//                    session.persist(newCategory);
//                };
//                //not work
////                categoryDao.saveEntityTree(newCategory);
//                transactionHandler.executeConsumer(consumer);
//            });
        });
    }


}
