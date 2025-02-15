//package com.b2c.prototype.configuration.transform;
//
//import com.b2c.prototype.modal.dto.payload.InitDiscountDto;
//import com.b2c.prototype.modal.dto.payload.OptionGroupDto;
//import com.b2c.prototype.modal.dto.payload.OptionGroupOptionItemSetDto;
//import com.b2c.prototype.modal.dto.payload.OptionItemDto;
//import com.b2c.prototype.modal.dto.payload.SingleOptionItemDto;
//import com.b2c.prototype.modal.entity.item.ArticularItem;
//import com.b2c.prototype.modal.entity.item.ArticularStatus;
//
//import com.b2c.prototype.modal.base.AbstractConstantEntity;
//import com.b2c.prototype.modal.constant.MessageStatusEnum;
//import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
//import com.b2c.prototype.modal.dto.payload.AddressDto;
//import com.b2c.prototype.modal.dto.payload.BeneficiaryDto;
//import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
//import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;
//import com.b2c.prototype.modal.dto.payload.CountryDto;
//import com.b2c.prototype.modal.dto.payload.CreditCardDto;
//import com.b2c.prototype.modal.dto.payload.DeliveryDto;
//import com.b2c.prototype.modal.dto.payload.DiscountDto;
//import com.b2c.prototype.modal.dto.payload.ItemDataDto;
//import com.b2c.prototype.modal.dto.payload.MessageDto;
//import com.b2c.prototype.modal.dto.payload.PriceDto;
//import com.b2c.prototype.modal.dto.payload.RegistrationUserProfileDto;
//import com.b2c.prototype.modal.dto.payload.ReviewDto;
//import com.b2c.prototype.modal.dto.payload.UserProfileDto;
//import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
//import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
//import com.b2c.prototype.modal.dto.response.ResponseMessageOverviewDto;
//import com.b2c.prototype.modal.dto.response.ResponseMessagePayloadDto;
//import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
//import com.b2c.prototype.modal.dto.response.ResponseReviewDto;
//import com.b2c.prototype.modal.entity.address.Address;
//import com.b2c.prototype.modal.entity.address.Country;
//import com.b2c.prototype.modal.entity.delivery.Delivery;
//import com.b2c.prototype.modal.entity.delivery.DeliveryType;
//import com.b2c.prototype.modal.entity.item.Brand;
//import com.b2c.prototype.modal.entity.item.Category;
//import com.b2c.prototype.modal.entity.item.Discount;
//import com.b2c.prototype.modal.entity.item.ItemData;
//import com.b2c.prototype.modal.entity.item.ItemType;
//import com.b2c.prototype.modal.entity.item.Rating;
//import com.b2c.prototype.modal.entity.message.Message;
//import com.b2c.prototype.modal.entity.message.MessageStatus;
//import com.b2c.prototype.modal.entity.message.MessageType;
//import com.b2c.prototype.modal.entity.option.OptionGroup;
//import com.b2c.prototype.modal.entity.option.OptionItem;
//import com.b2c.prototype.modal.entity.order.OrderArticularItem;
//import com.b2c.prototype.modal.entity.order.OrderStatus;
//import com.b2c.prototype.modal.entity.payment.CreditCard;
//import com.b2c.prototype.modal.entity.payment.PaymentMethod;
//import com.b2c.prototype.modal.entity.price.Currency;
//import com.b2c.prototype.modal.entity.price.Price;
//import com.b2c.prototype.modal.entity.review.Review;
//import com.b2c.prototype.modal.entity.store.CountType;
//import com.b2c.prototype.modal.entity.order.Beneficiary;
//import com.b2c.prototype.modal.entity.user.ContactInfo;
//import com.b2c.prototype.modal.entity.user.ContactPhone;
//import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
//import com.b2c.prototype.modal.entity.user.UserProfile;
//import com.b2c.prototype.service.function.ITransformationFunctionService;
//import com.b2c.prototype.service.help.calculate.IPriceCalculationService;
//import com.b2c.prototype.service.scope.IConstantContext;
//import com.b2c.prototype.util.CardUtil;
//import com.tm.core.process.dao.identifier.IQueryService;
//import com.tm.core.finder.factory.IParameterFactory;
//import com.tm.core.finder.parameter.Parameter;
//import org.hibernate.Session;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.function.Function;
//import java.util.function.Supplier;
//import java.util.stream.Collectors;
//
//import static com.b2c.prototype.util.Constant.VALUE;
//import static com.b2c.prototype.util.Util.getCurrentTimeMillis;
//import static com.b2c.prototype.util.Util.getUUID;
//
//@Configuration
//public class TransformationEntityConfigurationOld {
//
//    private final IQueryService queryService;
//    private final IConstantContext constantContext;
//    private final IParameterFactory parameterFactory;
//    private final IPriceCalculationService priceCalculationService;
//
//    public TransformationEntityConfigurationOld(ITransformationFunctionService transformationFunctionService,
//                                             IQueryService queryService,
//                                             IConstantContext constantContext,
//                                             IParameterFactory parameterFactory,
//                                             IPriceCalculationService priceCalculationService) {
//        this.queryService = queryService;
//        this.constantContext = constantContext;
//        this.parameterFactory = parameterFactory;
//        this.priceCalculationService = priceCalculationService;
//        addConstantEntityTransformationFunctions(transformationFunctionService, Brand.class, Brand::new);
//        addConstantEntityTransformationFunctions(transformationFunctionService, CountType.class, CountType::new);
//        addConstantEntityTransformationFunctions(transformationFunctionService, CountryPhoneCode.class, CountryPhoneCode::new);
//        addConstantEntityTransformationFunctions(transformationFunctionService, Currency.class, Currency::new);
//        addConstantEntityTransformationFunctions(transformationFunctionService, DeliveryType.class, DeliveryType::new);
//        addConstantEntityTransformationFunctions(transformationFunctionService, ItemType.class, ItemType::new);
//        addConstantEntityTransformationFunctions(transformationFunctionService, ArticularStatus.class, ArticularStatus::new);
//        addConstantEntityTransformationFunctions(transformationFunctionService, MessageStatus.class, MessageStatus::new);
//        addConstantEntityTransformationFunctions(transformationFunctionService, MessageType.class, MessageType::new);
//        addConstantEntityTransformationFunctions(transformationFunctionService, OptionGroup.class, OptionGroup::new);
//        addConstantEntityTransformationFunctions(transformationFunctionService, OrderStatus.class, OrderStatus::new);
//        addConstantEntityTransformationFunctions(transformationFunctionService, PaymentMethod.class, PaymentMethod::new);
//
//        transformationFunctionService.addTransformationFunction(CountryDto.class, Country.class, mapCountryDtoCountryFunction());
//        transformationFunctionService.addTransformationFunction(Country.class, CountryDto.class, mapCountryEntityCountryDtoFunction());
//
//        transformationFunctionService.addTransformationFunction(NumberConstantPayloadDto.class, Rating.class, mapOneIntegerFieldEntityDtoRatingFunction());
//
//        transformationFunctionService.addTransformationFunction(Address.class, AddressDto.class, mapAddressToAddressDtoFunction());
//        transformationFunctionService.addTransformationFunction(AddressDto.class, Address.class, mapAddressDtoToAddressFunction());
//        transformationFunctionService.addTransformationFunction(OrderArticularItem.class, AddressDto.class, mapOrderItemToAddressDtoFunction());
//        transformationFunctionService.addTransformationFunction(UserProfile.class, AddressDto.class, mapUserProfileToAddressDtoFunction());
//
//        transformationFunctionService.addTransformationFunction(CreditCardDto.class, CreditCard.class, mapCreditCardDtoToCreditCardFunction());
//        transformationFunctionService.addTransformationFunction(CreditCard.class, ResponseCreditCardDto.class, mapCreditCardToResponseCardDtoFunction());
//
//        transformationFunctionService.addTransformationFunction(MessageDto.class, Message.class, mapMessageDtoToMessageFunction());
//        transformationFunctionService.addTransformationFunction(Message.class, ResponseMessageOverviewDto.class, mapMessageToResponseMessageOverviewDtoFunction());
//        transformationFunctionService.addTransformationFunction(Message.class, ResponseMessagePayloadDto.class, mapMessageToResponseMessagePayloadDtoFunction());
//
//        transformationFunctionService.addTransformationFunction(ContactPhone.class, ContactPhoneDto.class, mapContactPhoneToContactPhoneDtoFunction());
//        transformationFunctionService.addTransformationFunction(ContactPhoneDto.class, ContactPhone.class, mapContactPhoneDtoToContactPhoneFunction());
//        transformationFunctionService.addTransformationFunction(UserProfile.class, ContactPhoneDto.class, mapUserProfileToContactPhoneDtoFunction());
//        transformationFunctionService.addTransformationFunction(OrderArticularItem.class, ContactPhoneDto.class, mapOrderItemToContactPhoneDtoFunction());
//        transformationFunctionService.addTransformationFunction(BeneficiaryDto.class, Beneficiary.class, "list", mapContactInfoDtoToContactInfoFunction());
//
//        transformationFunctionService.addTransformationFunction(RegistrationUserProfileDto.class, UserProfile.class, mapRegistrationUserProfileDtoToUserProfileFunction());
//        transformationFunctionService.addTransformationFunction(UserProfile.class, UserProfileDto.class, mapUserProfileToUserProfileDtoFunction());
//
//        transformationFunctionService.addTransformationFunction(ReviewDto.class, Review.class, mapReviewDtoToReviewFunction());
//        transformationFunctionService.addTransformationFunction(Review.class, ResponseReviewDto.class, mapReviewToResponseReviewDtoFunction());
//
//        transformationFunctionService.addTransformationFunction(PriceDto.class, Price.class, mapPriceDtoToPriceFunction());
//        transformationFunctionService.addTransformationFunction(Price.class, PriceDto.class, mapPriceToPriceDtoFunction());
//        transformationFunctionService.addTransformationFunction(OrderArticularItem.class, PriceDto.class, "fullPrice", mapOrderItemToFullPriceDtoFunction());
//        transformationFunctionService.addTransformationFunction(OrderArticularItem.class, PriceDto.class, "totalPrice", mapOrderItemToTotalPriceDtoFunction());
//        transformationFunctionService.addTransformationFunction(ArticularItem.class, PriceDto.class, "fullPrice", mapItemDataOptionToFullPriceDtoFunction());
//        transformationFunctionService.addTransformationFunction(ArticularItem.class, PriceDto.class, "totalPrice", mapItemDataOptionToTotalPriceDtoFunction());
//
//        loadOptionItemFunctions(transformationFunctionService);
//        loadItemDataFunctions(transformationFunctionService);
//
//        transformationFunctionService.addTransformationFunction(DiscountDto.class, Discount.class, mapDiscountDtoToDiscountFunction());
//        transformationFunctionService.addTransformationFunction(Discount.class, DiscountDto.class, mapDiscountToDiscountDtoFunction());
//        transformationFunctionService.addTransformationFunction(ArticularItem.class, DiscountDto.class, mapItemDataOptionListToDiscountDtoFunction());
//        transformationFunctionService.addTransformationFunction(ArticularItem.class, DiscountDto.class, "list", mapItemDataOptionListToDiscountDtoListFunction());
//
//
//        transformationFunctionService.addTransformationFunction(OrderArticularItem.class, Beneficiary.class, "list", mapOrderItemToContactInfoListFunction());
//    }
//
//    private void loadItemDataFunctions(ITransformationFunctionService transformationFunctionService) {
//        transformationFunctionService.addTransformationFunction(ItemDataDto.class, ItemData.class, mapItemDataDtoToItemDataFunction());
//    }
//
//    private void loadOptionItemFunctions(ITransformationFunctionService transformationFunctionService) {
//        transformationFunctionService.addTransformationFunction(OptionGroupOptionItemSetDto.class, OptionGroup.class, mapOptionItemDtoToOptionGroup());
//        transformationFunctionService.addTransformationFunction(OptionGroupOptionItemSetDto.class, OptionGroup.class, "set", mapOptionGroupOptionItemSetDtoListToOptionGroupSet());
//        transformationFunctionService.addTransformationFunction(ArticularItem.class, OptionItem.class, mapItemDataOptionToOptionItemFunction());
//        transformationFunctionService.addTransformationFunction(OptionGroup.class, OptionGroupOptionItemSetDto.class, mapOptionGroupToOptionItemDto());
//        transformationFunctionService.addTransformationFunction(ArticularItem.class, OptionGroupOptionItemSetDto.class, "list", mapArticularItemToOptionItemDtoList());
//
//        transformationFunctionService.addTransformationFunction(SingleOptionItemDto.class, OptionGroup.class, mapSingleOptionItemDtoToOptionGroup());
//    }
//
//    private <T extends AbstractConstantEntity> Function<ConstantPayloadDto, T> mapConstantEntityPayloadDtoToConstantEntityFunction(Supplier<T> entitySupplier) {
//        return constantPayloadDto -> {
//            T entity = entitySupplier.get();
//            entity.setLabel(constantPayloadDto.getLabel());
//            entity.setValue(constantPayloadDto.getValue());
//            return entity;
//        };
//    }
//
//    private <T extends AbstractConstantEntity> Function<T, ConstantPayloadDto> mapConstantEntityToConstantEntityPayloadDtoFunction() {
//        return abstractConstantEntity -> ConstantPayloadDto.builder()
//                .label(abstractConstantEntity.getLabel())
//                .value(abstractConstantEntity.getValue())
//                .build();
//    }
//
//    private <T extends AbstractConstantEntity> void addConstantEntityTransformationFunctions(ITransformationFunctionService transformationFunctionService,
//                                                                                             Class<T> entityClass,
//                                                                                             Supplier<T> entitySupplier) {
//        transformationFunctionService.addTransformationFunction(ConstantPayloadDto.class, entityClass, mapConstantEntityPayloadDtoToConstantEntityFunction(entitySupplier));
//        transformationFunctionService.addTransformationFunction(entityClass, ConstantPayloadDto.class, mapConstantEntityToConstantEntityPayloadDtoFunction());
//    }
//
//    private Function<NumberConstantPayloadDto, Rating> mapOneIntegerFieldEntityDtoRatingFunction() {
//        return constantIntegerEntityPayloadDto -> Rating.builder()
//                .value((Integer) constantIntegerEntityPayloadDto.getValue())
//                .build();
//    }
//
//    private Function<Country, CountryDto> mapCountryEntityCountryDtoFunction() {
//        return entity -> CountryDto.builder()
//                .label(entity.getLabel())
//                .value(entity.getValue())
//                .flagImagePath(entity.getFlagImagePath())
//                .build();
//    }
//
//    private Function<CountryDto, Country> mapCountryDtoCountryFunction() {
//        return countryDto -> Country.builder()
//                .label(countryDto.getLabel())
//                .value(countryDto.getValue())
//                .flagImagePath(countryDto.getFlagImagePath())
//                .build();
//    }
//
//    private Function<Currency, ConstantPayloadDto> mapResponseOneFieldEntityDtoCurrencyFunction() {
//        return entity -> ConstantPayloadDto.builder()
//                .label(entity.getLabel())
//                .value(entity.getValue())
//                .build();
//    }
//
//    private Function<AddressDto, Address> mapAddressDtoToAddressFunction(Session session) {
//        return addressDto -> Address.builder()
//                .country(constantContext.getEntity(
//                        session,
//                        Country.class,
//                        "value",
//                        addressDto.getCountry()))
//                .city(addressDto.getCity())
//                .street(addressDto.getStreet())
//                .street2(addressDto.getStreet2())
//                .buildingNumber(addressDto.getBuildingNumber())
//                .florNumber(addressDto.getFlorNumber())
//                .apartmentNumber(addressDto.getApartmentNumber())
//                .zipCode(addressDto.getZipCode())
//                .build();
//    }
//
//    private Function<Address, AddressDto> mapAddressToAddressDtoFunction() {
//        return address -> AddressDto.builder()
//                .country(address.getCountry().getValue())
//                .city(address.getCity())
//                .street(address.getStreet())
//                .street2(address.getStreet2())
//                .buildingNumber(address.getBuildingNumber())
//                .florNumber(address.getFlorNumber())
//                .apartmentNumber(address.getApartmentNumber())
//                .zipCode(address.getZipCode())
//                .build();
//    }
//
//    private Function<UserProfile, AddressDto> mapUserProfileToAddressDtoFunction() {
//        return userProfile -> {
//            Address address = userProfile.getAddress();
//            return mapAddressToAddressDtoFunction().apply(address);
//        };
//    }
//
//    private Function<OrderArticularItem, AddressDto> mapOrderItemToAddressDtoFunction() {
//        return orderItem -> {
//            Address address = orderItem.getDelivery().getAddress();
//            return mapAddressToAddressDtoFunction().apply(address);
//        };
//    }
//
//    private Function<CreditCardDto, CreditCard> mapCreditCardDtoToCreditCardFunction() {
//        return creditCardDto -> CreditCard.builder()
//                .cardNumber(creditCardDto.getCardNumber())
//                .dateOfExpire(creditCardDto.getDateOfExpire())
//                .isActive(CardUtil.isCardActive(creditCardDto.getDateOfExpire()))
//                .cvv(creditCardDto.getCvv())
//                .ownerName(creditCardDto.getOwnerName())
//                .ownerSecondName(creditCardDto.getOwnerSecondName())
//                .build();
//    }
//
//    private Function<CreditCard, ResponseCreditCardDto> mapCreditCardToResponseCardDtoFunction() {
//        return creditCard -> ResponseCreditCardDto.builder()
//                .cardNumber(creditCard.getCardNumber())
//                .dateOfExpire(creditCard.getDateOfExpire())
//                .isActive(CardUtil.isCardActive(creditCard.getDateOfExpire()))
//                .ownerName(creditCard.getOwnerName())
//                .ownerSecondName(creditCard.getOwnerSecondName())
//                .build();
//    }
//
//    private Function<MessageDto, Message> mapMessageDtoToMessageFunction() {
//        return messageDto -> {
//            MessageStatus messageStatus = constantContext.getEntity(
//                    MessageStatus.class,
//                    "value",
//                    MessageStatusEnum.UNREAD.getValue());
//            MessageType messageType = constantContext.getEntity(
//                    MessageType.class,
//                    "value",
//                    "value");
//
//            return Message.builder()
//                    .sender(messageDto.getSender())
//                    .title(messageDto.getTitle())
//                    .message(messageDto.getMessage())
//                    .messageUniqNumber(getUUID())
//                    .dateOfSend(getCurrentTimeMillis())
//                    .receivers(messageDto.getReceivers())
//                    .subscribe("subscribe")
//                    .type(messageType)
//                    .status(messageStatus)
//                    .build();
//        };
//    }
//
//    private Function<Message, ResponseMessageOverviewDto> mapMessageToResponseMessageOverviewDtoFunction() {
//        return message -> ResponseMessageOverviewDto.builder()
//                .sender(message.getSender())
//                .title(message.getTitle())
//                .dateOfSend(message.getDateOfSend())
//                .receivers(message.getReceivers())
//                .subscribe(message.getSubscribe())
//                .type(message.getType().getValue())
//                .status(message.getStatus().getValue())
//                .build();
//    }
//
//    private Function<Message, ResponseMessagePayloadDto> mapMessageToResponseMessagePayloadDtoFunction() {
//        return message -> ResponseMessagePayloadDto.builder()
//                .message(message.getMessage())
//                .build();
//    }
//
//    private Function<ContactPhoneDto, ContactPhone> mapContactPhoneDtoToContactPhoneFunction() {
//        return contactPhoneDto -> {
//            CountryPhoneCode countryPhoneCode = constantContext.getEntity(
//                    CountryPhoneCode.class,
//                    "code",
//                    contactPhoneDto.getCountryPhoneCode());
//            return ContactPhone.builder()
//                    .phoneNumber(contactPhoneDto.getPhoneNumber())
//                    .countryPhoneCode(countryPhoneCode)
//                    .build();
//        };
//    }
//
//    private Function<ContactPhone, ContactPhoneDto> mapContactPhoneToContactPhoneDtoFunction() {
//        return contactPhone -> ContactPhoneDto.builder()
//                .phoneNumber(contactPhone.getPhoneNumber())
//                .countryPhoneCode(contactPhone.getCountryPhoneCode().getValue())
//                .build();
//    }
//
//    private Function<UserProfile, ContactPhoneDto> mapUserProfileToContactPhoneDtoFunction() {
//        return userProfile -> {
//            ContactPhone contactPhone = userProfile.getContactInfo().getContactPhone();
//            return mapContactPhoneToContactPhoneDtoFunction().apply(contactPhone);
//        };
//    }
//
//    private Function<OrderArticularItem, List<ContactPhoneDto>> mapOrderItemToContactPhoneDtoFunction() {
//        return orderItem -> orderItem.getBeneficiaries().stream()
//                .map(beneficiary -> mapContactPhoneToContactPhoneDtoFunction().apply(beneficiary.getContactPhone()))
//                .toList();
//    }
//
//    private Function<BeneficiaryDto, Beneficiary> mapContactInfoDtoToContactInfoFunction() {
//        return beneficiaryDto -> {
//            CountryPhoneCode countryPhoneCode = constantContext.getEntity(
//                    CountryPhoneCode.class,
//                    "code",
//                    beneficiaryDto.getContactPhone().getCountryPhoneCode());
//            ContactPhone contactPhone = ContactPhone.builder()
//                    .phoneNumber(beneficiaryDto.getContactPhone().getPhoneNumber())
//                    .countryPhoneCode(countryPhoneCode)
//                    .build();
//            return Beneficiary.builder()
//                    .firstName(beneficiaryDto.getFirstName())
//                    .lastName(beneficiaryDto.getLastName())
//                    .contactPhone(contactPhone)
//                    .build();
//        };
//    }
//
//    Function<OrderArticularItem, List<Beneficiary>> mapOrderItemToContactInfoListFunction() {
//        return OrderArticularItem::getBeneficiaries;
//    }
//
//    Function<UserProfile, ContactInfoDto> mapUserProfileToContactInfoDtoFunction() {
//        return userProfile -> {
//            ContactInfo contactInfo = userProfile.getContactInfo();
//            ContactPhone contactPhone = contactInfo.getContactPhone();
//            return ContactInfoDto.builder()
//                    .firstName(contactInfo.getFirstName())
//                    .lastName(contactInfo.getLastName())
//                    .contactPhone(ContactPhoneDto.builder()
//                            .phoneNumber(contactPhone.getPhoneNumber())
//                            .countryPhoneCode(contactPhone.getCountryPhoneCode().getValue())
//                            .build())
//                    .build();
//        };
//    }
//
//    private Function<RegistrationUserProfileDto, UserProfile> mapRegistrationUserProfileDtoToUserProfileFunction() {
//        return registrationUserProfileDto -> UserProfile.builder()
//                .username(registrationUserProfileDto.getUsername())
//                .email(registrationUserProfileDto.getEmail())
//                .dateOfCreate(getCurrentTimeMillis())
//                .build();
//    }
//
//    private Function<UserProfile, UserProfileDto> mapUserProfileToUserProfileDtoFunction() {
//        return userProfile -> {
//            ContactInfoDto contactInfoDto = mapUserProfileToContactInfoDtoFunction().apply(userProfile);
//            AddressDto addressDto = mapUserProfileToAddressDtoFunction().apply(userProfile);
//            List<ResponseCreditCardDto> responseCreditCardDtoList = userProfile.getCreditCardList().stream()
//                    .map(c -> mapCreditCardToResponseCardDtoFunction().apply(c))
//                    .toList();
//            return UserProfileDto.builder()
//                    .email(userProfile.getEmail())
//                    .contactInfo(contactInfoDto)
//                    .creditCards(responseCreditCardDtoList)
//                    .addressDto(addressDto)
//                    .build();
//        };
//    }
//
//    private Function<ReviewDto, Review> mapReviewDtoToReviewFunction() {
//        return reviewDto -> Review.builder()
//                .title(reviewDto.getTitle())
//                .message(reviewDto.getMessage())
//                .dateOfCreate(getCurrentTimeMillis())
//                .build();
//    }
//
//    private Function<Review, ResponseReviewDto> mapReviewToResponseReviewDtoFunction() {
//        return review -> ResponseReviewDto.builder()
//                .title(review.getTitle())
//                .message(review.getMessage())
//                .ratingValue(review.getRating().getValue())
//                .dateOfCreate(new Date(getCurrentTimeMillis()))
//                .build();
//    }
//
//    private Function<PriceDto, Price> mapPriceDtoToPriceFunction() {
//        return priceDto -> {
//            Currency currency = constantContext.getEntity(
//                    Currency.class,
//                    "value",
//                    priceDto.getCurrency());
//            return Price.builder()
//                    .amount(priceDto.getAmount())
//                    .currency(currency)
//                    .build();
//        };
//    }
//
//    private Function<OrderArticularItem, PriceDto> mapOrderItemToFullPriceDtoFunction() {
//        return orderItem -> {
//            Price fullPrice = orderItem.getPayment().getFullPrice();
//            return PriceDto.builder()
//                    .amount(fullPrice.getAmount())
//                    .currency(fullPrice.getCurrency().getValue())
//                    .build();
//        };
//    }
//
//    private Function<OrderArticularItem, PriceDto> mapOrderItemToTotalPriceDtoFunction() {
//        return orderItem -> {
//            Price fullPrice = orderItem.getPayment().getTotalPrice();
//            return PriceDto.builder()
//                    .amount(fullPrice.getAmount())
//                    .currency(fullPrice.getCurrency().getValue())
//                    .build();
//        };
//    }
//
//    private Function<ArticularItem, PriceDto> mapItemDataOptionToFullPriceDtoFunction() {
//        return itemDataOption -> {
//            Price fullPrice = itemDataOption.getFullPrice();
//            return PriceDto.builder()
//                    .amount(fullPrice.getAmount())
//                    .currency(fullPrice.getCurrency().getValue())
//                    .build();
//        };
//    }
//
//    private Function<ArticularItem, PriceDto> mapItemDataOptionToTotalPriceDtoFunction() {
//        return itemDataOption -> {
//            Price fullPrice = itemDataOption.getTotalPrice();
//            return PriceDto.builder()
//                    .amount(fullPrice.getAmount())
//                    .currency(fullPrice.getCurrency().getValue())
//                    .build();
//        };
//    }
//
//    private Function<Price, PriceDto> mapPriceToPriceDtoFunction() {
//        return price -> PriceDto.builder()
//                .amount(price.getAmount())
//                .currency(price.getCurrency().getValue())
//                .build();
//    }
//
//    private Function<ArticularItem, Price> mapItemDataOptionToFullPriceFunction() {
//        return ArticularItem::getFullPrice;
//    }
//
//    private Function<ArticularItem, Price> mapItemDataOptionToTotalPriceFunction() {
//        return ArticularItem::getTotalPrice;
//    }
//
//    private Function<OrderArticularItem, Price> mapOrderItemToFullPriceFunction() {
//        return orderItem -> orderItem.getPayment().getFullPrice();
//    }
//
//    private Function<OrderArticularItem, Price> mapOrderItemToTotalPriceFunction() {
//        return orderArticularItem -> orderArticularItem.getPayment().getTotalPrice();
//    }
//
//    Function<ArticularItem, Set<OptionItem>> mapItemDataOptionToOptionItemFunction() {
//        return ArticularItem::getOptionItems;
//    }
//
//    Function<Set<OptionGroupOptionItemSetDto>, Set<OptionGroup>> mapOptionGroupOptionItemSetDtoListToOptionGroupSet() {
//        return optionItemDtoList -> optionItemDtoList.stream()
//                .map(optionItemDto ->
//                        constantContext.getOptionalEntityGraph(OptionGroup.class,
//                                "optionGroup.withOptionItemsAndArticularItems", VALUE, optionItemDto.getOptionGroup().getValue()
////                        constantContext.getOptionalEntityNamedQuery(OptionGroup.class,
////                                "optionGroup.withOptionItemsAndArticularItems", VALUE, optionItemDto.getOptionGroup().getValue()
//                ).map(existingOG -> {
//                    optionItemDto.getOptionItems().stream()
//                            .filter(oi -> existingOG.getOptionItems().stream()
//                                    .noneMatch(v -> v.getValue().equals(oi.getValue())))
//                            .forEach(newOption -> existingOG.addOptionItem(
//                                    OptionItem.builder()
//                                            .label(newOption.getLabel())
//                                            .value(newOption.getValue())
//                                            .build()
//                            ));
//
//                    return existingOG;
//                }).orElseGet(() -> {
//                    OptionGroup newOG = OptionGroup.builder()
//                            .label(optionItemDto.getOptionGroup().getLabel())
//                            .value(optionItemDto.getOptionGroup().getValue())
//                            .build();
//
//                    optionItemDto.getOptionItems().stream()
//                            .map(item -> OptionItem.builder()
//                                    .label(item.getLabel())
//                                    .value(item.getValue())
//                                    .build())
//                            .forEach(newOG::addOptionItem);
//
//                    return newOG;
//                }))
//                .collect(Collectors.toSet());
//    }
//
//    Function<OptionGroupOptionItemSetDto, OptionGroup> mapOptionItemDtoToOptionGroup() {
//        return optionItemDto -> {
//            Optional<OptionGroup> optionalResult = constantContext.getOptionalEntityGraph(
//                    OptionGroup.class, "optionGroup.withOptionItems", VALUE, optionItemDto.getOptionGroup().getValue());
//            return optionalResult.map(existingOG -> {
//                optionItemDto.getOptionItems().stream()
//                        .filter(oi -> existingOG.getOptionItems().stream()
//                                .noneMatch(v -> v.getValue().equals(oi.getValue())))
//                        .forEach(newOption -> existingOG.getOptionItems().add(
//                                OptionItem.builder()
//                                        .label(newOption.getLabel())
//                                        .value(newOption.getValue())
//                                        .build()
//                        ));
//
//                return existingOG;
//            }).orElseGet(() -> {
//                OptionGroup newOG = OptionGroup.builder()
//                        .label(optionItemDto.getOptionGroup().getLabel())
//                        .value(optionItemDto.getOptionGroup().getValue())
//                        .build();
//
//                optionItemDto.getOptionItems().stream()
//                        .map(item -> OptionItem.builder()
//                                .label(item.getLabel())
//                                .value(item.getValue())
//                                .build())
//                        .forEach(newOG::addOptionItem);
//
//                return newOG;
//            });
//        };
//    };
//
//    Function<OptionGroup, OptionGroupOptionItemSetDto> mapOptionGroupToOptionItemDto() {
//        return optionGroup -> {
//            OptionGroupDto optionGroupDto = OptionGroupDto.builder()
//                    .label(optionGroup.getLabel())
//                    .value(optionGroup.getValue())
//                    .build();
//
//            Set<OptionItemDto> constantPayloadDtoList = optionGroup.getOptionItems().stream()
//                    .map(item -> OptionItemDto.builder()
//                            .label(item.getLabel())
//                            .value(item.getValue())
//                            .build())
//                    .collect(Collectors.toSet());
//
//
//            return OptionGroupOptionItemSetDto.builder()
//                    .optionGroup(optionGroupDto)
//                    .optionItems(constantPayloadDtoList)
//                    .build();
//        };
//    }
//
//    private Function<ArticularItem, List<OptionGroupOptionItemSetDto>> mapArticularItemToOptionItemDtoList() {
//        return articularItem -> articularItem.getOptionItems().stream()
//                .map(optionItem -> OptionGroupOptionItemSetDto.builder()
//                        .optionGroup(OptionGroupDto.builder()
//                                .label(optionItem.getOptionGroup().getLabel())
//                                .value(optionItem.getOptionGroup().getValue())
//                                .build())
//                        .optionItems(Set.of(OptionItemDto.builder()
//                                .label(optionItem.getLabel())
//                                .value(optionItem.getValue())
//                                .build()))
//                        .build())
//                .toList();
//    }
//
//    private Function<SingleOptionItemDto, OptionGroup> mapSingleOptionItemDtoToOptionGroup() {
//        return singleOptionItemDto -> {
//            OptionGroup og = OptionGroup.builder()
//                    .label(singleOptionItemDto.getOptionGroup().getLabel())
//                    .value(singleOptionItemDto.getOptionGroup().getValue())
//                    .build();
//            og.addOptionItem(OptionItem.builder()
//                    .value(singleOptionItemDto.getOptionItem().getValue())
//                    .label(singleOptionItemDto.getOptionItem().getLabel())
//                    .build());
//            return og;
//        };
//    }
//
//    private Function<InitDiscountDto, Discount> mapInitDiscountDtoToDiscount() {
//        return initDiscountDto -> {
//            Currency currency = constantContext.getEntity(
//                    Currency.class,
//                    "value",
//                    initDiscountDto.getCurrency());
////            Currency currency = queryService.getEntity(null, );
//            return Discount.builder()
//                    .currency(currency)
//                    .amount(initDiscountDto.getAmount())
//                    .charSequenceCode(initDiscountDto.getCharSequenceCode())
//                    .isActive(initDiscountDto.getIsActive())
//                    .isPercent(currency == null)
//                    .build();
//        };
//    }
//
//    private Function<ItemDataDto, ItemData> mapItemDataDtoToItemDataFunction(Session session) {
//        return itemDataDto -> {
//            Optional<Category> optionalCategory = queryService.getOptionalEntity(session, Category.class, new Parameter("name", itemDataDto.getCategory()));
//            Optional<Brand> optionalBrand = queryService.getOptionalEntity(session, Brand.class, new Parameter(VALUE, itemDataDto.getBrand()));
//            Optional<ItemType> optionalItemType = queryService.getOptionalEntity(session, ItemType.class, new Parameter(VALUE, itemDataDto.getItemType()));
//
//            Set<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDtoList = itemDataDto.getArticularItemSet().stream()
//                    .flatMap(articularItem -> articularItem.getOptions().stream())
//                    .collect(Collectors.groupingBy(
//                            SingleOptionItemDto::getOptionGroup,
//                            Collectors.mapping(
//                                    SingleOptionItemDto::getOptionItem,
//                                    Collectors.toSet()
//                            )
//                    ))
//                    .entrySet().stream()
//                    .map(entry -> new OptionGroupOptionItemSetDto(
//                            new OptionGroupDto(entry.getKey().getValue(), entry.getKey().getLabel()), // Convert OptionGroup to OptionGroupDto
//                            entry.getValue().stream()
//                                    .map(optionItem -> new OptionItemDto(optionItem.getValue(), optionItem.getLabel())) // Convert OptionItem to OptionItemDto
//                                    .collect(Collectors.toSet())
//                    ))
//                    .collect(Collectors.toSet());
//
//            Set<OptionGroup> optionGroups = mapOptionGroupOptionItemSetDtoListToOptionGroupSet(session).apply(optionGroupOptionItemSetDtoList);
//            Set<OptionItem> optionItems = optionGroups.stream()
//                    .flatMap(og -> og.getOptionItems().stream())
//                    .collect(Collectors.toSet());
//            Set<ArticularItem> articularItemList = itemDataDto.getArticularItemSet().stream()
//                    .map(articularItemDto -> {
//                        Discount discount = (articularItemDto != null && articularItemDto.getDiscount() != null)
//                                ? mapInitDiscountDtoToDiscount(session).apply(articularItemDto.getDiscount())
//                                : queryService.getEntity(session, Discount.class, new Parameter(VALUE, articularItemDto.getDiscount().getCharSequenceCode()));
//                        ArticularStatus articularStatus = queryService.getEntity(session, ArticularStatus.class, new Parameter(VALUE, articularItemDto.getStatus()));
//
//                        ArticularItem articularItem = ArticularItem.builder()
//                                .dateOfCreate(getCurrentTimeMillis())
//                                .articularId(getUUID())
//                                .productName(articularItemDto.getProductName())
//                                .status(articularStatus)
//                                .fullPrice(mapPriceDtoToPriceFunction(session).apply(articularItemDto.getFullPrice()))
//                                .totalPrice(mapPriceDtoToPriceFunction(session).apply(articularItemDto.getTotalPrice()))
//                                .discount(discount)
//                                .build();
//
//                        articularItemDto.getOptions().stream()
//                                .flatMap(soi -> optionItems.stream()
//                                        .filter(optionItem -> optionItem.getValue().equals(soi.getOptionItem().getValue()))
//                                )
//                                .forEach(articularItem::addOptionItem);
//
//                        return articularItem;
//                    })
//                    .collect(Collectors.toSet());
//
//            return ItemData.builder()
//                    .itemId(getUUID())
//                    .description(itemDataDto.getDescription())
//                    .category(optionalCategory.orElse(null))
//                    .brand(optionalBrand.orElse(null))
//                    .itemType(optionalItemType.orElse(null))
//                    .articularItemSet(articularItemList)
//                    .build();
//        };
//    }
//
//    private Function<InitDiscountDto, Discount> mapInitDiscountDtoToDiscount(Session session) {
//        return initDiscountDto -> {
//            Currency currency = queryService.getEntity(
//                    session, Currency.class, new Parameter(VALUE, initDiscountDto.getCurrency()));
//            return Discount.builder()
//                    .currency(currency)
//                    .amount(initDiscountDto.getAmount())
//                    .charSequenceCode(initDiscountDto.getCharSequenceCode())
//                    .isActive(initDiscountDto.getIsActive())
//                    .isPercent(currency == null)
//                    .build();
//        };
//    }
//
//    private Function<PriceDto, Price> mapPriceDtoToPriceFunction(Session session) {
//        return priceDto -> {
//            Currency currency = queryService.getEntity(
//                    session, Currency.class, new Parameter(VALUE, priceDto.getCurrency()));
//            return Price.builder()
//                    .amount(priceDto.getAmount())
//                    .currency(currency)
//                    .build();
//        };
//    }
//
//    private Function<Set<OptionGroupOptionItemSetDto>, Set<OptionGroup>> mapOptionGroupOptionItemSetDtoListToOptionGroupSet() {
//        Session session;
//        return optionItemDtoList -> optionItemDtoList.stream()
//                .map(optionItemDto -> queryService.getOptionalEntityGraph(
//                                        session,
//                                        OptionGroup.class,
//                                    "optionGroup.withOptionItemsAndArticularItems",
//                                        new Parameter(VALUE, optionItemDto.getOptionGroup().getValue())
//                                )
//                                .map(existingOG -> {
//                                    optionItemDto.getOptionItems().stream()
//                                            .filter(oi -> existingOG.getOptionItems().stream()
//                                                    .noneMatch(v -> v.getValue().equals(oi.getValue())))
//                                            .forEach(newOption -> existingOG.addOptionItem(
//                                                    OptionItem.builder()
//                                                            .label(newOption.getLabel())
//                                                            .value(newOption.getValue())
//                                                            .build()
//                                            ));
//
//                                    return existingOG;
//                                })
//                                .orElseGet(() -> {
//                                    OptionGroup newOG = OptionGroup.builder()
//                                            .label(optionItemDto.getOptionGroup().getLabel())
//                                            .value(optionItemDto.getOptionGroup().getValue())
//                                            .build();
//
//                                    optionItemDto.getOptionItems().stream()
//                                            .map(item -> OptionItem.builder()
//                                                    .label(item.getLabel())
//                                                    .value(item.getValue())
//                                                    .build())
//                                            .forEach(newOG::addOptionItem);
//
//                                    return newOG;
//                                })
//                )
//                .collect(Collectors.toSet());
//    }
//
//    private Function<DiscountDto, Discount> mapDiscountDtoToDiscountFunction() {
//        return discountDto -> {
//            Currency currency = constantContext.getEntity(
//                    Currency.class,
//                    "value",
//                    discountDto.getCurrency());
//            return Discount.builder()
//                    .currency(currency)
//                    .amount(discountDto.getAmount())
//                    .charSequenceCode(discountDto.getCharSequenceCode())
//                    .isActive(discountDto.getIsActive())
//                    .isPercent(currency == null)
//                    .build();
//        };
//    }
//
//    private Function<Discount, DiscountDto> mapDiscountToDiscountDtoFunction() {
//        return discount -> DiscountDto.builder()
//                .currency(discount.getCurrency().getValue())
//                .amount(discount.getAmount())
//                .charSequenceCode(discount.getCharSequenceCode())
//                .isActive(discount.isActive())
//                .build();
//    }
//
//    private Function<List<ArticularItem>, DiscountDto> mapItemDataOptionListToDiscountDtoFunction() {
//        return itemDataOptionList -> {
//            Discount discount = itemDataOptionList.get(0).getDiscount();
//            return DiscountDto.builder()
//                    .charSequenceCode(discount.getCharSequenceCode())
//                    .amount(discount.getAmount())
//                    .isActive(discount.isActive())
//                    .currency(discount.getCurrency() != null ? discount.getCurrency().getValue() : null)
//                    .articularIdSet(
//                            itemDataOptionList.stream()
//                                    .map(ArticularItem::getArticularId)
//                                    .collect(Collectors.toSet())
//                    )
//                    .build();
//        };
//    }
//
//    private Function<List<ArticularItem>, List<DiscountDto>> mapItemDataOptionListToDiscountDtoListFunction() {
//        return itemDataOptionList -> itemDataOptionList.stream()
//                .collect(Collectors.groupingBy(ArticularItem::getDiscount))
//                .entrySet().stream()
//                .map(entry -> {
//                    Discount discount = entry.getKey();
//                    Set<String> articularIdSet = entry.getValue().stream()
//                            .map(ArticularItem::getArticularId)
//                            .collect(Collectors.toSet());
//
//                    return DiscountDto.builder()
//                            .charSequenceCode(discount.getCharSequenceCode())
//                            .amount(discount.getAmount())
//                            .isActive(discount.isActive())
//                            .currency(discount.getCurrency() != null ? discount.getCurrency().getValue() : null)
//                            .articularIdSet(articularIdSet)
//                            .build();
//                })
//                .collect(Collectors.toList());
//    }
//
//    private Function<ItemDataDto, ItemData> mapItemDataDtoToItemDataFunction() {
//        return itemDataDto -> {
//            Category category = constantContext.getEntity(Category.class, "name", itemDataDto.getCategory());
//            Brand brand = constantContext.getEntity(Brand.class, "value", itemDataDto.getBrand());
//            ItemType itemType = constantContext.getEntity(ItemType.class, "value", itemDataDto.getItemType());
//
//            Set<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDtoList = itemDataDto.getArticularItemSet().stream()
//                    .flatMap(articularItem -> articularItem.getOptions().stream())
//                    .collect(Collectors.groupingBy(
//                            SingleOptionItemDto::getOptionGroup,
//                            Collectors.mapping(
//                                    SingleOptionItemDto::getOptionItem,
//                                    Collectors.toSet()
//                            )
//                    ))
//                    .entrySet().stream()
//                    .map(entry -> new OptionGroupOptionItemSetDto(
//                            new OptionGroupDto(entry.getKey().getValue(), entry.getKey().getLabel()),
//                            entry.getValue().stream()
//                                    .map(optionItem -> new OptionItemDto(optionItem.getValue(), optionItem.getLabel())) // Convert OptionItem to OptionItemDto
//                                    .collect(Collectors.toSet())
//                    ))
//                    .collect(Collectors.toSet());
//
//            Set<OptionGroup> optionGroups = mapOptionGroupOptionItemSetDtoListToOptionGroupSet().apply(optionGroupOptionItemSetDtoList);
//            Set<OptionItem> optionItems = optionGroups.stream()
//                    .flatMap(og -> og.getOptionItems().stream())
//                    .collect(Collectors.toSet());
//            Set<ArticularItem> articularItemList = itemDataDto.getArticularItemSet().stream()
//                    .map(articularItemDto -> {
//                        Discount discount = (articularItemDto != null && articularItemDto.getDiscount() != null)
//                                ? mapInitDiscountDtoToDiscount().apply(articularItemDto.getDiscount())
//                                : constantContext.getEntity(Discount.class, "charSequenceCode", articularItemDto.getDiscount().getCharSequenceCode());
//                        ArticularStatus articularStatus = constantContext.getEntity(ArticularStatus.class, "value", articularItemDto.getStatus());
//
//                        ArticularItem articularItem = ArticularItem.builder()
//                                .dateOfCreate(getCurrentTimeMillis())
//                                .articularId(getUUID())
//                                .productName(articularItemDto.getProductName())
//                                .status(articularStatus)
//                                .fullPrice(mapPriceDtoToPriceFunction().apply(articularItemDto.getFullPrice()))
//                                .totalPrice(mapPriceDtoToPriceFunction().apply(articularItemDto.getTotalPrice()))
//                                .discount(discount)
//                                .build();
//
//                        articularItemDto.getOptions().stream()
//                                .flatMap(soi -> optionItems.stream()
//                                        .filter(optionItem -> optionItem.getValue().equals(soi.getOptionItem().getValue()))
//                                )
//                                .forEach(articularItem::addOptionItem);
//
//                        return articularItem;
//                    })
//                    .collect(Collectors.toSet());
//
//            return ItemData.builder()
//                    .itemId(getUUID())
//                    .description(itemDataDto.getDescription())
//                    .category(category)
//                    .brand(brand)
//                    .itemType(itemType)
//                    .articularItemSet(articularItemList)
//                    .build();
//        };
//    }
//
//
//    private Function<ItemData, ResponseItemDataDto> mapItemDataToItemDataDtoFunction() {
//        return itemData -> ResponseItemDataDto.builder()
//                .brandName(itemData.getBrand().getValue())
//                .categoryName(itemData.getCategory().getName())
//                .itemTypeName(itemData.getItemType().getValue())
//                .description(itemData.getDescription())
//                .build();
//    }
//
//    private Function<DeliveryDto, Delivery> mapDeliveryDtoToDeliveryFunction() {
//        return deliveryDto -> {
//            Address address = mapAddressDtoToAddressFunction().apply(deliveryDto.getDeliveryAddress());
//            DeliveryType deliveryType = constantContext.getEntity(
//                    DeliveryType.class,
//                    "value",
//                    deliveryDto.getDeliveryType());
//
//            return Delivery.builder()
//                    .address(address)
//                    .deliveryType(deliveryType)
//                    .build();
//        };
//    }
//
//    private Function<Delivery, DeliveryDto> mapDeliveryToDeliveryDtoFunction() {
//        return delivery -> {
//            AddressDto addressDto = mapAddressToAddressDtoFunction().apply(delivery.getAddress());
//
//            return DeliveryDto.builder()
//                    .deliveryAddress(addressDto)
//                    .deliveryType(delivery.getDeliveryType().getValue())
//                    .build();
//        };
//    }
//
//
//
//}
