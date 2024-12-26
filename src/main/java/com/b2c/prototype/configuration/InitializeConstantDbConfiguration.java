package com.b2c.prototype.configuration;

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
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.ItemStatus;
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
import com.tm.core.dao.transaction.ITransactionWrapper;
import jakarta.annotation.PostConstruct;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static com.b2c.prototype.util.CategoryUtil.extractTransitiveSelfYaml;
import static com.b2c.prototype.util.CategoryUtil.findNonExistingCategories;
import static com.b2c.prototype.util.CategoryUtil.findNonExistingCategoriesMap;

@Configuration
public class InitializeConstantDbConfiguration {

    private final LocalizationInterpreter localizationInterpreter;
    private final Set<Locale> availableLocales;
    private final EnumConfiguration enumConfiguration;
    private final ITransactionWrapper transactionWrapper;
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
                                             EnumConfiguration enumConfiguration, ITransactionWrapper transactionWrapper,
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
                                             IRatingDao ratingDao, ICategoryDao categoryDao) {
        this.localizationInterpreter = localizationInterpreter;
        this.availableLocales = availableLocales;
        this.enumConfiguration = enumConfiguration;
        this.transactionWrapper = transactionWrapper;
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

        initializeCategory(defaultLocale);

    }

    private void initializeCountTypes(Locale defaultLocale) {
        Set<String> countTypeNames = enumConfiguration.getCountTypes();
        List<CountType> existingCountTypes = countTypeDao.getEntityList();

        countTypeNames.stream()
                .map(name -> localizationInterpreter.interpret("count.type", name.toLowerCase(), defaultLocale))
                .filter(localizedName -> existingCountTypes.stream()
                        .noneMatch(existing -> existing.getValue().equals(localizedName)))
                .map(countType -> CountType.builder()
                        .value(countType)
                        .build())
                .forEach(countTypeDao::persistEntity);
    }

    private void initializeCountryPhoneCode(Locale defaultLocale) {
        Set<String> countryPhoneCodes = enumConfiguration.getCountryPhoneCodes();
        List<CountryPhoneCode> existingCountryPhoneCodes = countryPhoneCodeDao.getEntityList();

        countryPhoneCodes.stream()
                .map(name -> localizationInterpreter.interpret("contact.phone", name.toLowerCase(), defaultLocale))
                .filter(localizedName -> existingCountryPhoneCodes.stream()
                        .noneMatch(existing -> existing.getCode().equals(localizedName)))
                .map(countryPhoneCodeName -> CountryPhoneCode.builder()
                        .code(countryPhoneCodeName)
                        .build())
                .forEach(countryPhoneCodeDao::persistEntity);
    }

    private void initializeCountries(Locale defaultLocale) {
        Set<String> countryNames = enumConfiguration.getCountries();
        List<Country> existingCountries = countryDao.getEntityList();

        countryNames.stream()
                .map(name -> localizationInterpreter.interpret("country", name.toLowerCase(), defaultLocale))
                .filter(localizedName -> existingCountries.stream()
                        .noneMatch(existing -> existing.getValue().equals(localizedName)))
                .map(countryName -> Country.builder()
                        .value(countryName)
                        .build())
                .forEach(countryDao::persistEntity);
    }

    private void initializeCurrencies(Locale defaultLocale) {
        Set<String> currencyNames = enumConfiguration.getCurrencies();
        List<Currency> existingCurrencies = currencyDao.getEntityList();

        currencyNames.stream()
                .map(name -> localizationInterpreter.interpret("currency", name.toLowerCase(), defaultLocale))
                .filter(localizedName -> existingCurrencies.stream()
                        .noneMatch(existing -> existing.getValue().equals(localizedName)))
                .map(currency -> Currency.builder()
                        .value(currency)
                        .build())
                .forEach(currencyDao::persistEntity);
    }

    private void initializeDeliveryTypeEntities(Locale defaultLocale) {
        Set<String> deliveryTypeList = enumConfiguration.getDeliveryTypes();
        List<DeliveryType> existingDeliveryType = deliveryTypeDao.getEntityList();

        if (deliveryTypeList != null) {
            deliveryTypeList.stream()
                    .map(deliveryTypeName -> localizationInterpreter.interpret("delivery.type", deliveryTypeName.toLowerCase(), defaultLocale))
                    .filter(localizedName ->
                            existingDeliveryType.stream()
                                    .noneMatch(deliveryType -> deliveryType.getValue().equals(localizedName)))
                    .map(deliveryTypeName -> DeliveryType.builder()
                            .value(deliveryTypeName)
                            .build())
                    .forEach(deliveryTypeDao::persistEntity);
        }
    }

    private void initializePaymentMethodEntities(Locale defaultLocale) {
        Set<String> paymentMethodList = enumConfiguration.getPaymentMethods();
        List<PaymentMethod> existingPaymentMethod = paymentMethodDao.getEntityList();

        if (paymentMethodList != null) {
            paymentMethodList.stream()
                    .map(paymentMethodName -> localizationInterpreter.interpret("payment.method", paymentMethodName.toLowerCase(), defaultLocale))
                    .filter(localizedName -> existingPaymentMethod.stream()
                                    .noneMatch(paymentMethod -> paymentMethod.getValue().equals(localizedName)))
                    .map(paymentMethodName -> PaymentMethod.builder()
                            .value(paymentMethodName)
                            .build())
                    .forEach(paymentMethodDao::persistEntity);
        }
    }

    private void initializeOrderStatusEntities(Locale defaultLocale) {
        Set<String> orderStatusList = enumConfiguration.getOrderStatuses();
        List<OrderStatus> existingOrderStatus = orderStatusDao.getEntityList();

        if (orderStatusList != null) {
            orderStatusList.stream()
                    .map(orderStatusName -> localizationInterpreter.interpret("item.status", orderStatusName.toLowerCase(), defaultLocale))
                    .filter(localizedName ->
                            existingOrderStatus.stream()
                                    .noneMatch(orderStatus -> orderStatus.getValue().equals(localizedName)))
                    .map(orderStatusName -> OrderStatus.builder()
                            .value(orderStatusName)
                            .build())
                    .forEach(orderStatusDao::persistEntity);
        }
    }

    private void initializeBrandEntities(Locale defaultLocale) {
        Set<String> brandSet = enumConfiguration.getBrands();
        List<Brand> existingBrands = brandDao.getEntityList();

        if (brandSet != null) {
            brandSet.stream()
                    .map(brandName -> localizationInterpreter.interpret("brand", brandName.toLowerCase(), defaultLocale))
                    .filter(localizedName ->
                            existingBrands.stream()
                                    .noneMatch(brand -> brand.getValue().equals(localizedName)))
                    .map(brandName -> Brand.builder()
                            .value(brandName)
                            .build())
                    .forEach(brandDao::persistEntity);
        }
    }

    private void initializeItemStatusEntities(Locale defaultLocale) {
        Set<String> itemStatusList = enumConfiguration.getItemStatuses();
        List<ItemStatus> existItemStatus = itemStatusDao.getEntityList();

        if (itemStatusList != null) {
            itemStatusList.stream()
                    .map(itemStatusName -> localizationInterpreter.interpret("item.status", itemStatusName.toLowerCase(), defaultLocale))
                    .filter(localizedName ->
                            existItemStatus.stream()
                                    .noneMatch(itemStatus -> itemStatus.getValue().equals(localizedName)))
                    .map(itemStatusName -> ItemStatus.builder()
                            .value(itemStatusName)
                            .build())
                    .forEach(itemStatusDao::persistEntity);
        }
    }

    private void initializeItemTypesEntities(Locale defaultLocale) {
        Set<String> itemTypeList = enumConfiguration.getItemTypes();
        List<ItemType> existItemTypes = itemTypeDao.getEntityList();

        if (itemTypeList != null) {
            itemTypeList.stream()
                    .map(itemTypeName -> localizationInterpreter.interpret("item.type", itemTypeName.toLowerCase(), defaultLocale))
                    .filter(localizedName ->
                            existItemTypes.stream()
                                    .noneMatch(itemType -> itemType.getValue().equals(localizedName)))
                    .map(itemTypeName -> ItemType.builder()
                            .value(itemTypeName)
                            .build())
                    .forEach(itemTypeDao::persistEntity);
        }
    }

    private void initializeMessageStatuses(Locale locale) {
        Set<String> messageStatusNames = enumConfiguration.getMessageStatuses();
        List<MessageStatus> existingMessageStatuses = messageStatusDao.getEntityList();

        messageStatusNames.stream()
                .map(name -> localizationInterpreter.interpret("message.status", name.toLowerCase(), locale))
                .filter(localizedName -> existingMessageStatuses.stream()
                        .noneMatch(existing -> existing.getValue().equals(localizedName)))
                .map(messageStatusValue -> MessageStatus.builder()
                        .value(messageStatusValue)
                        .build())
                .forEach(messageStatusDao::persistEntity);
    }

    private void initializeMessageTypes(Locale locale) {
        Set<String> messageTypeNames = enumConfiguration.getMessageTypes();
        List<MessageType> existingMessageTypes = messageTypeDao.getEntityList();

        messageTypeNames.stream()
                .map(name -> localizationInterpreter.interpret("message.type", name.toLowerCase(), locale))
                .filter(localizedName -> existingMessageTypes.stream()
                        .noneMatch(existing -> existing.getValue().equals(localizedName)))
                .map(messageTypeValue -> MessageType.builder()
                        .value(messageTypeValue)
                        .build())
                .forEach(messageTypeDao::persistEntity);
    }

    private void initializeOptionGroupEntities(Locale defaultLocale) {
        Set<String> optionGroupList = enumConfiguration.getItemTypes();
        List<ItemType> existOptionGroup = optionGroupDao.getEntityList();

        if (optionGroupList != null) {
            optionGroupList.stream()
                    .map(optionGroupName -> localizationInterpreter.interpret("option.group", optionGroupName.toLowerCase(), defaultLocale))
                    .filter(localizedName ->
                            existOptionGroup.stream()
                                    .noneMatch(optionGroup -> optionGroup.getValue().equals(localizedName)))
                    .map(optionGroupName -> OptionGroup.builder()
                            .value(optionGroupName)
                            .build())
                    .forEach(optionGroupDao::persistEntity);
        }
    }

    private void initializeRatingEntities() {
        Set<Integer> ratingList = enumConfiguration.getRatings();
        List<Rating> existRatingList = ratingDao.getEntityList();

        if (ratingList != null) {
            ratingList.stream()
                    .filter(rating ->
                            existRatingList.stream()
                                    .noneMatch(existRating -> existRating.getValue() == rating))
                    .map(rating -> Rating.builder()
                            .value(rating)
                            .build())
                    .forEach(ratingDao::persistEntity);
        }
    }

    private void initializeCategory(Locale defaultLocale) {
        Set<TransitiveSelfYaml> categories = enumConfiguration.getCategories();
        List<Category> existCategoryList = categoryDao.getTransitiveSelfEntityList();
        List<TransitiveSelfYaml> configYamlList = extractTransitiveSelfYaml(categories);
        List<TransitiveSelfYaml> nonExistingCategories = findNonExistingCategories(configYamlList, existCategoryList);
        Map<Category, List<TransitiveSelfYaml>> nonExistingCategoriesMap = findNonExistingCategoriesMap(categories, existCategoryList);
        if (!nonExistingCategoriesMap.isEmpty()) {
            buildNewCategories(nonExistingCategoriesMap);
        } else {
            categories.stream()
                    .map(CategoryUtil::buildCategory)
                    .forEach(categoryDao::saveEntityTree);
        }
    }

    private void buildNewCategories(Map<Category, List<TransitiveSelfYaml>> nonExistingCategoriesMap) {
        nonExistingCategoriesMap.forEach((key, value) -> {
            List<Category> newCategories = value.stream()
                    .map(CategoryUtil::buildCategory)
                    .toList();
            newCategories.forEach(newCategory -> {
                key.addChildTransitiveEntity(newCategory);
                Consumer<Session> consumer = session -> {
                    session.persist(newCategory);
                };
                //not work
//                categoryDao.saveEntityTree(newCategory);
                transactionWrapper.executeConsumer(consumer);
            });
        });
    };

//    private Category buildCategory(TransitiveSelfYaml transitiveSelfYaml) {
//        Category category = Category.builder()
//                .name(transitiveSelfYaml.getName())
//                .build();
//
//        if (transitiveSelfYaml.getSub() != null) {
//            for (TransitiveSelfYaml sub : transitiveSelfYaml.getSub()) {
//                Category childCategory = buildCategory(sub);
//                category.addChildTransitiveEntity(childCategory);
//            }
//        }
//
//        return category;
//    }
//
//    public List<TransitiveSelfYaml> extractTransitiveSelfYaml(Set<TransitiveSelfYaml> categories) {
//        List<TransitiveSelfYaml> result = new ArrayList<>();
//        for (TransitiveSelfYaml category : categories) {
//            extractNamesRecursive(category, result);
//        }
//        return result;
//    }
//
//    private void extractNamesRecursive(TransitiveSelfYaml category, List<TransitiveSelfYaml> transitiveSelfYamlList) {
//        transitiveSelfYamlList.add(category);
//
//        if (category.getSub() != null && !category.getSub().isEmpty()) {
//            for (TransitiveSelfYaml subCategory : category.getSub()) {
//                extractNamesRecursive(subCategory, transitiveSelfYamlList);
//            }
//        }
//    }
//
//    public List<TransitiveSelfYaml> findNonExistingCategories(List<TransitiveSelfYaml> configYamlList, List<Category> existCategoryList) {
//        Set<String> existingCategoryNames = existCategoryList.stream()
//                .map(Category::getRootField)
//                .collect(Collectors.toSet());
//
//        return configYamlList.stream()
//                .filter(configYaml -> !existingCategoryNames.contains(configYaml.getName()))
//                .collect(Collectors.toList());
//    }
//
//    public Map<Category, List<TransitiveSelfYaml>> findNonExistingCategoriesMap(
//            Set<TransitiveSelfYaml> configYamlCategories,
//            List<Category> existCategoryList
//    ) {
//        Map<Category, List<TransitiveSelfYaml>> resultMap = new HashMap<>();
//
//        Map<String, Category> existingCategoriesByName = existCategoryList.stream()
//                .collect(Collectors.toMap(Category::getRootField, category -> category));
//
//        for (TransitiveSelfYaml yamlCategory : configYamlCategories) {
//            findNewSubcategories(yamlCategory, existingCategoriesByName, resultMap);
//        }
//
//        return resultMap;
//    }
//
//    private void findNewSubcategories(
//            TransitiveSelfYaml yamlCategory,
//            Map<String, Category> existingCategoriesByName,
//            Map<Category, List<TransitiveSelfYaml>> resultMap
//    ) {
//        Category existingParentCategory = existingCategoriesByName.get(yamlCategory.getName());
//
//        if (existingParentCategory != null) {
//            List<TransitiveSelfYaml> newSubcategories;
//            if (yamlCategory.getSub() != null && !yamlCategory.getSub().isEmpty()) {
//                newSubcategories = yamlCategory.getSub().stream()
//                        .filter(subName -> existingParentCategory.getChildNodeList().stream()
//                                .noneMatch(child -> child.getName().equals(subName.getName())))
//                        .collect(Collectors.toList());
//
//                if (!newSubcategories.isEmpty()) {
//                    resultMap.put(existingParentCategory, newSubcategories);
//                }
//
//                for (TransitiveSelfYaml subYaml : yamlCategory.getSub()) {
//                    findNewSubcategories(subYaml, existingCategoriesByName, resultMap);
//                }
//            }
//        }
//    }


}
