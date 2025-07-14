package com.b2c.prototype.configuration;

import com.b2c.prototype.configuration.modal.ApplicationProperty;
import com.b2c.prototype.configuration.modal.TransitiveSelfYaml;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.review.Rating;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.tm.core.process.dao.common.IEntityDao;
import com.tm.core.process.dao.common.ITransactionEntityDao;
import com.tm.core.process.dao.transaction.ITransactionHandler;
import jakarta.annotation.PostConstruct;
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
    private final IEntityDao brandDao;
    private final IEntityDao countTypeDao;
    private final IEntityDao countryPhoneCodeDao;
    private final ITransactionEntityDao countryDao;
    private final ITransactionEntityDao currencyDao;
    private final ITransactionEntityDao deliveryTypeDao;
    private final ITransactionEntityDao itemStatusDao;
    private final ITransactionEntityDao itemTypeDao;
    private final ITransactionEntityDao messageStatusDao;
    private final ITransactionEntityDao messageTypeDao;
    private final ITransactionEntityDao optionGroupDao;
    private final ITransactionEntityDao orderStatusDao;
    private final ITransactionEntityDao paymentMethodDao;
    private final ITransactionEntityDao ratingDao;

    private final ITransactionEntityDao categoryDao;

    public InitializeConstantDbConfiguration(LocalizationInterpreter localizationInterpreter,
                                             Set<Locale> availableLocales,
                                             ApplicationPropertyConfiguration applicationPropertyConfiguration,
                                             ITransactionHandler transactionHandler,
                                             ITransactionEntityDao brandDao,
                                             ITransactionEntityDao countTypeDao,
                                             ITransactionEntityDao countryPhoneCodeDao,
                                             ITransactionEntityDao countryDao,
                                             ITransactionEntityDao currencyDao,
                                             ITransactionEntityDao deliveryTypeDao,
                                             ITransactionEntityDao itemStatusDao,
                                             ITransactionEntityDao itemTypeDao,
                                             ITransactionEntityDao messageStatusDao,
                                             ITransactionEntityDao messageTypeDao,
                                             ITransactionEntityDao optionGroupDao,
                                             ITransactionEntityDao orderStatusDao,
                                             ITransactionEntityDao paymentMethodDao,
                                             ITransactionEntityDao ratingDao,
                                             ITransactionEntityDao categoryDao) {
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
        List<CountType> existingCountTypes = countTypeDao.getNamedQueryEntityListClose("CountType.all");

        if (countTypeNames != null) {
            countTypeNames.stream()
                    .filter(ap -> existingCountTypes.stream()
                            .noneMatch(countType -> countType.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> CountType.builder()
                            .label(localizationInterpreter.interpret("country_phone_code", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
        }
    }

    private void initializeCountryPhoneCode(Locale defaultLocale) {
        Set<ApplicationProperty> countryPhoneCodes = applicationPropertyConfiguration.getCountryPhoneCodes();
        List<CountryPhoneCode> existingCountryPhoneCodes = countryPhoneCodeDao.getNamedQueryEntityListClose("CountryPhoneCode.all");

        if (countryPhoneCodes != null) {
            countryPhoneCodes.stream()
                    .filter(ap -> existingCountryPhoneCodes.stream()
                            .noneMatch(countryPhoneCode -> countryPhoneCode.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> CountryPhoneCode.builder()
                            .label(localizationInterpreter.interpret("country_phone_code", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
        }
    }

    private void initializeCountries(Locale defaultLocale) {
        Set<ApplicationProperty> countrySet = applicationPropertyConfiguration.getCountries();
        List<Country> existingCountries = countryDao.getNamedQueryEntityListClose("Country.all");

        if (countrySet != null) {
            countrySet.stream()
                    .filter(ap -> existingCountries.stream()
                            .noneMatch(country -> country.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> Country.builder()
                            .label(localizationInterpreter.interpret("country", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
        }
    }

    private void initializeCurrencies(Locale defaultLocale) {
        Set<ApplicationProperty> currencySet = applicationPropertyConfiguration.getCurrencies();
        List<Currency> existingCurrencies = currencyDao.getNamedQueryEntityListClose("Currency.all");

        if (currencySet != null) {
            currencySet.stream()
                    .filter(ap -> existingCurrencies.stream()
                            .noneMatch(currency -> currency.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> Currency.builder()
                            .label(localizationInterpreter.interpret("currency", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
        }
    }

    private void initializeDeliveryTypeEntities(Locale defaultLocale) {
        Set<ApplicationProperty> deliveryTypeSet = applicationPropertyConfiguration.getDeliveryTypes();
        List<DeliveryType> existingDeliveryTypeList = deliveryTypeDao.getNamedQueryEntityListClose("DeliveryType.all");

        if (deliveryTypeSet != null) {
            deliveryTypeSet.stream()
                    .filter(ap -> existingDeliveryTypeList.stream()
                            .noneMatch(deliveryType -> deliveryType.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> DeliveryType.builder()
                            .label(localizationInterpreter.interpret("delivery.type", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
        }
    }

    private void initializePaymentMethodEntities(Locale defaultLocale) {
        Set<ApplicationProperty> paymentMethodSet = applicationPropertyConfiguration.getPaymentMethods();
        List<PaymentMethod> existingPaymentMethodList = paymentMethodDao.getNamedQueryEntityListClose("PaymentMethod.all");

        if (paymentMethodSet != null) {
            paymentMethodSet.stream()
                    .filter(ap -> existingPaymentMethodList.stream()
                            .noneMatch(orderStatus -> orderStatus.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> OrderStatus.builder()
                            .label(localizationInterpreter.interpret("payment.method", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
        }
    }

    private void initializeOrderStatusEntities(Locale defaultLocale) {
        Set<ApplicationProperty> applicationPropertySet = applicationPropertyConfiguration.getOrderStatuses();
        List<OrderStatus> existingOrderStatus = orderStatusDao.getNamedQueryEntityListClose("OrderStatus.all");

        if (applicationPropertySet != null) {
            applicationPropertySet.stream()
                    .filter(ap -> existingOrderStatus.stream()
                            .noneMatch(orderStatus -> orderStatus.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> OrderStatus.builder()
                            .label(localizationInterpreter.interpret("order.status", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
        }
    }

    private void initializeBrandEntities(Locale defaultLocale) {
        Set<ApplicationProperty> brandSet = applicationPropertyConfiguration.getBrands();
        List<Brand> existingBrands = brandDao.getNamedQueryEntityListClose("Brand.all");

        if (brandSet != null) {
            brandSet.stream()
                    .filter(ap -> existingBrands.stream()
                            .noneMatch(brand -> brand.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> Brand.builder()
                            .label(localizationInterpreter.interpret("brand", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
        }
    }

    private void initializeItemStatusEntities(Locale defaultLocale) {
        Set<ApplicationProperty> itemStatuses = applicationPropertyConfiguration.getItemStatuses();
        List<ArticularStatus> existArticularStatuses = itemStatusDao.getNamedQueryEntityListClose("ArticularStatus.all");

        if (itemStatuses != null) {
            itemStatuses.stream()
                    .filter(ap -> existArticularStatuses.stream()
                            .noneMatch(itemStatus -> itemStatus.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> ArticularStatus.builder()
                            .label(localizationInterpreter.interpret("item.status", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
        }
    }

    private void initializeItemTypesEntities(Locale defaultLocale) {
        Set<ApplicationProperty> itemTypeSet = applicationPropertyConfiguration.getItemTypes();
        List<ItemType> existItemTypes = itemTypeDao.getNamedQueryEntityListClose("ItemType.all");

        if (itemTypeSet != null) {
            itemTypeSet.stream()
                    .filter(ap -> existItemTypes.stream()
                            .noneMatch(itemStatus -> itemStatus.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> ArticularStatus.builder()
                            .label(localizationInterpreter.interpret("item.type", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
        }
    }

    private void initializeMessageStatuses(Locale defaultLocale) {
        Set<ApplicationProperty> messageStatuses = applicationPropertyConfiguration.getMessageStatuses();
        List<MessageStatus> existingMessageStatuses = messageStatusDao.getNamedQueryEntityListClose("MessageStatus.all");

        if (messageStatuses != null) {
            messageStatuses.stream()
                    .filter(ap -> existingMessageStatuses.stream()
                            .noneMatch(messageStatus -> messageStatus.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> ArticularStatus.builder()
                            .label(localizationInterpreter.interpret("message.status", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
        }
    }

    private void initializeMessageTypes(Locale defaultLocale) {
        Set<ApplicationProperty> messageTypes = applicationPropertyConfiguration.getMessageTypes();
        List<MessageType> existingMessageTypes = messageTypeDao.getNamedQueryEntityListClose("MessageType.all");

        if (messageTypes != null) {
            messageTypes.stream()
                    .filter(ap -> existingMessageTypes.stream()
                            .noneMatch(messageStatus -> messageStatus.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> ArticularStatus.builder()
                            .label(localizationInterpreter.interpret("message.type", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
        }
    }

    private void initializeOptionGroupEntities(Locale defaultLocale) {
        Set<ApplicationProperty> optionGroupSet = applicationPropertyConfiguration.getOptionGroups();
        List<OptionGroup> existOptionGroupList = optionGroupDao.getNamedQueryEntityListClose("OptionGroup.all");

        if (optionGroupSet != null) {
            optionGroupSet.stream()
                    .filter(ap -> existOptionGroupList.stream()
                            .noneMatch(og -> og.getValue().equals(ap.getValue().toLowerCase())))
                    .map(ap -> ArticularStatus.builder()
                            .label(localizationInterpreter.interpret("option.group", ap.getValue().toLowerCase(), defaultLocale))
                            .value(ap.getValue())
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
        }
    }

    private void initializeRatingEntities() {
        Set<Integer> ratingList = applicationPropertyConfiguration.getRatings();
        List<Rating> existRatingList = ratingDao.getNamedQueryEntityListClose("Rating.all");

        if (ratingList != null) {
            ratingList.stream()
                    .filter(rating ->
                            existRatingList.stream()
                                    .noneMatch(existRating -> existRating.getValue().equals(rating)))
                    .map(rating -> Rating.builder()
                            .value(rating)
                            .build())
                    .forEach(entity -> {
                        transactionHandler.executeConsumer(entityManager -> {
                            entityManager.persist(entity);
                        });
                    });
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
