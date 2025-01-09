package com.b2c.prototype.configuration.transform;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.base.AbstractConstantEntity;
import com.b2c.prototype.modal.constant.MessageStatusEnum;
import com.b2c.prototype.modal.dto.common.ConstantNumberEntityPayloadDto;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.payload.BeneficiaryDto;
import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;
import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.DeliveryDto;
import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.MessageDto;
import com.b2c.prototype.modal.dto.payload.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.payload.RegistrationUserProfileDto;
import com.b2c.prototype.modal.dto.payload.ReviewDto;
import com.b2c.prototype.modal.dto.payload.UserProfileDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.response.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.payload.ConstantEntityPayloadDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.modal.entity.order.Beneficiary;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.calculate.IPriceCalculationService;
import com.b2c.prototype.util.CardUtil;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.UniqueIdUtil.getUUID;

@Configuration
public class TransformationEntityConfiguration {

    private final ISingleValueMap singleValueMap;
    private final IPriceCalculationService priceCalculationService;

    public TransformationEntityConfiguration(ITransformationFunctionService transformationFunctionService,
                                             ISingleValueMap singleValueMap,
                                             IPriceCalculationService priceCalculationService) {
        this.singleValueMap = singleValueMap;
        this.priceCalculationService = priceCalculationService;
        addConstantEntityTransformationFunctions(transformationFunctionService, Brand.class, Brand::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, CountType.class, CountType::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, CountryPhoneCode.class, CountryPhoneCode::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, Country.class, Country::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, Currency.class, Currency::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, DeliveryType.class, DeliveryType::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, ItemType.class, ItemType::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, ItemStatus.class, ItemStatus::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, MessageStatus.class, MessageStatus::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, MessageType.class, MessageType::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, OptionGroup.class, OptionGroup::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, OrderStatus.class, OrderStatus::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, PaymentMethod.class, PaymentMethod::new);

        transformationFunctionService.addTransformationFunction(ConstantNumberEntityPayloadDto.class, Rating.class,  mapOneIntegerFieldEntityDtoRatingFunction());

        transformationFunctionService.addTransformationFunction(Address.class, AddressDto.class, mapAddressToAddressDtoFunction());
        transformationFunctionService.addTransformationFunction(AddressDto.class, Address.class, mapAddressDtoToAddressFunction());
        transformationFunctionService.addTransformationFunction(OrderItemData.class, AddressDto.class, mapOrderItemToAddressDtoFunction());
        transformationFunctionService.addTransformationFunction(UserProfile.class, AddressDto.class, mapUserProfileToAddressDtoFunction());

        transformationFunctionService.addTransformationFunction(CreditCardDto.class, CreditCard.class, mapCreditCardDtoToCreditCardFunction());
        transformationFunctionService.addTransformationFunction(CreditCard.class, ResponseCreditCardDto.class, mapCreditCardToResponseCardDtoFunction());

        transformationFunctionService.addTransformationFunction(MessageDto.class, Message.class, mapMessageDtoToMessageFunction());
        transformationFunctionService.addTransformationFunction(Message.class, ResponseMessageOverviewDto.class, mapMessageToResponseMessageOverviewDtoFunction());
        transformationFunctionService.addTransformationFunction(Message.class, ResponseMessagePayloadDto.class, mapMessageToResponseMessagePayloadDtoFunction());

        transformationFunctionService.addTransformationFunction(ContactPhone.class, ContactPhoneDto.class, mapContactPhoneToContactPhoneDtoFunction());
        transformationFunctionService.addTransformationFunction(ContactPhoneDto.class, ContactPhone.class, mapContactPhoneDtoToContactPhoneFunction());
        transformationFunctionService.addTransformationFunction(UserProfile.class, ContactPhoneDto.class, mapUserProfileToContactPhoneDtoFunction());
        transformationFunctionService.addTransformationFunction(OrderItemData.class, ContactPhoneDto.class, mapOrderItemToContactPhoneDtoFunction());
        transformationFunctionService.addTransformationFunction(BeneficiaryDto.class, Beneficiary.class, "list", mapContactInfoDtoToContactInfoFunction());

        transformationFunctionService.addTransformationFunction(RegistrationUserProfileDto.class, UserProfile.class, mapRegistrationUserProfileDtoToUserProfileFunction());
        transformationFunctionService.addTransformationFunction(UserProfile.class, UserProfileDto.class, mapUserProfileToUserProfileDtoFunction());

        transformationFunctionService.addTransformationFunction(ReviewDto.class, Review.class, mapReviewDtoToReviewFunction());
        transformationFunctionService.addTransformationFunction(Review.class, ResponseReviewDto.class, mapReviewToResponseReviewDtoFunction());

        transformationFunctionService.addTransformationFunction(PriceDto.class, Price.class, mapPriceDtoToPriceFunction());
        transformationFunctionService.addTransformationFunction(Price.class, PriceDto.class, mapPriceToPriceDtoFunction());
        transformationFunctionService.addTransformationFunction(OrderItemData.class, PriceDto.class, "fullPrice", mapOrderItemToFullPriceDtoFunction());
        transformationFunctionService.addTransformationFunction(OrderItemData.class, PriceDto.class, "totalPrice", mapOrderItemToTotalPriceDtoFunction());
        transformationFunctionService.addTransformationFunction(ItemDataOption.class, PriceDto.class, "fullPrice", mapItemDataOptionToFullPriceDtoFunction());
        transformationFunctionService.addTransformationFunction(ItemDataOption.class, PriceDto.class, "totalPrice", mapItemDataOptionToTotalPriceDtoFunction());

        transformationFunctionService.addTransformationFunction(OptionItemDto.class, OptionItem.class, "set", mapOptionItemDtoToOptionItemSet());
        transformationFunctionService.addTransformationFunction(OptionItem.class, OptionItemDto.class, mapOptionItemSetToOptionItemDto());

        transformationFunctionService.addTransformationFunction(OrderItemData.class, Beneficiary.class, "list", mapOrderItemToContactInfoListFunction());
    }

    private <T extends AbstractConstantEntity> Function<ConstantEntityPayloadDto, T> mapConstantEntityPayloadDtoToConstantEntityFunction(Supplier<T> entitySupplier) {
        return constantEntityPayloadDto -> {
            T entity = entitySupplier.get();
            entity.setLabel(constantEntityPayloadDto.getLabel());
            entity.setValue(constantEntityPayloadDto.getValue());
            return entity;
        };
    }

    private <T extends AbstractConstantEntity> Function<OneFieldEntityDto, T> mapOneFieldEntityDtoToConstantEntityFunction(Supplier<T> entitySupplier) {
        return oneFieldEntityDto -> {
            T entity = entitySupplier.get();
            entity.setValue(oneFieldEntityDto.getValue());
            return entity;
        };
    }

    private <T extends AbstractConstantEntity> Function<T, ConstantEntityPayloadDto> mapConstantEntityToConstantEntityPayloadDtoFunction() {
        return abstractConstantEntity -> ConstantEntityPayloadDto.builder()
                .label(abstractConstantEntity.getLabel())
                .value(abstractConstantEntity.getValue())
                .build();
    }

    private <T extends AbstractConstantEntity> void addConstantEntityTransformationFunctions(ITransformationFunctionService transformationFunctionService,
                                                                                             Class<T> entityClass,
                                                                                             Supplier<T> entitySupplier) {
        transformationFunctionService.addTransformationFunction(ConstantEntityPayloadDto.class, entityClass, mapConstantEntityPayloadDtoToConstantEntityFunction(entitySupplier));
        transformationFunctionService.addTransformationFunction(entityClass, ConstantEntityPayloadDto.class, mapConstantEntityToConstantEntityPayloadDtoFunction());
    }



    private Function<ConstantEntityPayloadDto, Brand> mapConstantEntityPayloadDtoBrandFunction() {
        return constantEntityPayloadDto -> Brand.builder()
                .label(constantEntityPayloadDto.getLabel())
                .value(constantEntityPayloadDto.getValue())
                .build();
    }

    private Function<ConstantEntityPayloadDto, CountType> mapConstantEntityPayloadDtoCountTypeFunction() {
        return constantEntityPayloadDto -> CountType.builder()
                .label(constantEntityPayloadDto.getLabel())
                .value(constantEntityPayloadDto.getValue())
                .build();
    }

    private Function<ConstantEntityPayloadDto, CountryPhoneCode> mapConstantEntityPayloadDtoCountryPhoneCodeFunction() {
        return constantEntityPayloadDto -> CountryPhoneCode.builder()
                .label(constantEntityPayloadDto.getLabel())
                .value(constantEntityPayloadDto.getValue())
                .build();
    }

    private Function<ConstantEntityPayloadDto, Country> mapConstantEntityPayloadDtoCountryFunction() {
        return constantEntityPayloadDto -> Country.builder()
                .label(constantEntityPayloadDto.getLabel())
                .value(constantEntityPayloadDto.getValue())
                .build();
    }

    private Function<ConstantEntityPayloadDto, Currency> mapConstantEntityPayloadDtoCurrencyFunction() {
        return constantEntityPayloadDto -> Currency.builder()
                .label(constantEntityPayloadDto.getLabel())
                .value(constantEntityPayloadDto.getValue())
                .build();
    }

    private Function<ConstantEntityPayloadDto, DeliveryType> mapOneFieldEntityDtoDeliveryTypeFunction() {
        return constantEntityPayloadDto -> DeliveryType.builder()
                .label(constantEntityPayloadDto.getLabel())
                .value(constantEntityPayloadDto.getValue())
                .build();
    }

    private Function<ConstantEntityPayloadDto, ItemStatus> mapOneFieldEntityDtoItemStatusFunction() {
        return constantEntityPayloadDto -> ItemStatus.builder()
                .label(constantEntityPayloadDto.getLabel())
                .value(constantEntityPayloadDto.getValue())
                .build();
    }

    private Function<ConstantEntityPayloadDto, ItemType> mapOneFieldEntityDtoItemTypeFunction() {
        return constantEntityPayloadDto -> ItemType.builder()
                .label(constantEntityPayloadDto.getLabel())
                .value(constantEntityPayloadDto.getValue())
                .build();
    }

    private Function<ConstantEntityPayloadDto, MessageStatus> mapOneFieldEntityDtoMessageStatusFunction() {
        return constantEntityPayloadDto -> MessageStatus.builder()
                .label(constantEntityPayloadDto.getLabel())
                .value(constantEntityPayloadDto.getValue())
                .build();
    }

    private Function<ConstantEntityPayloadDto, MessageType> mapOneFieldEntityDtoMessageTypeFunction() {
        return constantEntityPayloadDto -> MessageType.builder()
                .label(constantEntityPayloadDto.getLabel())
                .value(constantEntityPayloadDto.getValue())
                .build();
    }

    private Function<ConstantEntityPayloadDto, OptionGroup> mapOneFieldEntityDtoOptionGroupFunction() {
        return constantEntityPayloadDto -> OptionGroup.builder()
                .label(constantEntityPayloadDto.getLabel())
                .value(constantEntityPayloadDto.getValue())
                .build();
    }

    private Function<ConstantEntityPayloadDto, OrderStatus> mapOneFieldEntityDtoOrderStatusFunction() {
        return constantEntityPayloadDto -> OrderStatus.builder()
                .label(constantEntityPayloadDto.getLabel())
                .value(constantEntityPayloadDto.getValue())
                .build();
    }

    private Function<ConstantEntityPayloadDto, PaymentMethod> mapOneFieldEntityDtoPaymentMethodFunction() {
        return constantEntityPayloadDto -> PaymentMethod.builder()
                .label(constantEntityPayloadDto.getLabel())
                .value(constantEntityPayloadDto.getValue())
                .build();
    }

    private Function<ConstantNumberEntityPayloadDto, Rating> mapOneIntegerFieldEntityDtoRatingFunction() {
        return constantIntegerEntityPayloadDto -> Rating.builder()
                .value((Integer) constantIntegerEntityPayloadDto.getValue())
                .build();
    }

    private Function<Brand, ConstantEntityPayloadDto> mapResponseOneFieldEntityDtoBrandFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<CountType, ConstantEntityPayloadDto> mapResponseOneFieldEntityDtoCountTypeFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<CountryPhoneCode, ConstantEntityPayloadDto> mapResponseOneFieldEntityDtoCountryPhoneCodeFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<Country, ConstantEntityPayloadDto> mapResponseOneFieldEntityDtoCountryFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<Currency, ConstantEntityPayloadDto> mapResponseOneFieldEntityDtoCurrencyFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<DeliveryType, ConstantEntityPayloadDto> mapResponseOneFieldEntityDtoDeliveryTypeFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<ItemStatus, ConstantEntityPayloadDto> mapResponseOneFieldEntityDtoItemStatusFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<MessageStatus, ConstantEntityPayloadDto> mapResponseOneFieldEntityDtoMessageStatusFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<ItemType, ConstantEntityPayloadDto> mapResponseOneFieldEntityDtoItemTypeFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<MessageStatus, ConstantEntityPayloadDto> getResponseOneFieldEntityDtoMessageStatusFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<MessageType, ConstantEntityPayloadDto> mapResponseOneFieldEntityDtoMessageTypeFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<OptionGroup, ConstantEntityPayloadDto> mapResponseOneFieldEntityDtoOptionGroupFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<OrderStatus, ConstantEntityPayloadDto> mapResponseOneFieldEntityDtoOrderStatusFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<PaymentMethod, ConstantEntityPayloadDto> mapResponseOneFieldEntityDtoPaymentMethodFunction() {
        return entity -> ConstantEntityPayloadDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .build();
    }

    private Function<AddressDto, Address> mapAddressDtoToAddressFunction() {
        return addressDto -> Address.builder()
                .country(singleValueMap.getEntity(
                        Country.class,
                        "value",
                        addressDto.getCountry()))
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .street2(addressDto.getStreet2())
                .buildingNumber(addressDto.getBuildingNumber())
                .florNumber(addressDto.getFlorNumber())
                .apartmentNumber(addressDto.getApartmentNumber())
                .zipCode(addressDto.getZipCode())
                .build();
    }

    private Function<Address, AddressDto> mapAddressToAddressDtoFunction() {
        return address -> AddressDto.builder()
                .country(address.getCountry().getValue())
                .city(address.getCity())
                .street(address.getStreet())
                .street2(address.getStreet2())
                .buildingNumber(address.getBuildingNumber())
                .florNumber(address.getFlorNumber())
                .apartmentNumber(address.getApartmentNumber())
                .zipCode(address.getZipCode())
                .build();
    }

    private Function<UserProfile, AddressDto> mapUserProfileToAddressDtoFunction() {
        return userProfile -> {
            Address address = userProfile.getAddress();
            return mapAddressToAddressDtoFunction().apply(address);
        };
    }

    private Function<OrderItemData, AddressDto> mapOrderItemToAddressDtoFunction() {
        return orderItem -> {
            Address address = orderItem.getDelivery().getAddress();
            return mapAddressToAddressDtoFunction().apply(address);
        };
    }

    private Function<CreditCardDto, CreditCard> mapCreditCardDtoToCreditCardFunction() {
        return creditCardDto -> CreditCard.builder()
                .cardNumber(creditCardDto.getCardNumber())
                .dateOfExpire(creditCardDto.getDateOfExpire())
                .isActive(CardUtil.isCardActive(creditCardDto.getDateOfExpire()))
                .cvv(creditCardDto.getCvv())
                .ownerName(creditCardDto.getOwnerName())
                .ownerSecondName(creditCardDto.getOwnerSecondName())
                .build();
    }

    private Function<CreditCard, ResponseCreditCardDto> mapCreditCardToResponseCardDtoFunction() {
        return creditCard -> ResponseCreditCardDto.builder()
                .cardNumber(creditCard.getCardNumber())
                .dateOfExpire(creditCard.getDateOfExpire())
                .isActive(CardUtil.isCardActive(creditCard.getDateOfExpire()))
                .ownerName(creditCard.getOwnerName())
                .ownerSecondName(creditCard.getOwnerSecondName())
                .build();
    }

    private Function<MessageDto, Message> mapMessageDtoToMessageFunction() {
        return messageDto -> {
            MessageStatus messageStatus = singleValueMap.getEntity(
                    MessageStatus.class,
                    "value",
                    MessageStatusEnum.UNREAD.getValue());
            MessageType messageType = singleValueMap.getEntity(
                    MessageType.class,
                    "value",
                    "value");

            return Message.builder()
                    .sender(messageDto.getSender())
                    .title(messageDto.getTitle())
                    .message(messageDto.getMessage())
                    .messageUniqNumber(getUUID())
                    .dateOfSend(System.currentTimeMillis())
                    .receivers(messageDto.getReceivers())
                    .subscribe("subscribe")
                    .type(messageType)
                    .status(messageStatus)
                    .build();
        };
    }

    private Function<Message, ResponseMessageOverviewDto> mapMessageToResponseMessageOverviewDtoFunction() {
        return message -> ResponseMessageOverviewDto.builder()
                .sender(message.getSender())
                .title(message.getTitle())
                .dateOfSend(message.getDateOfSend())
                .receivers(message.getReceivers())
                .subscribe(message.getSubscribe())
                .type(message.getType().getValue())
                .status(message.getStatus().getValue())
                .build();
    }

    private Function<Message, ResponseMessagePayloadDto> mapMessageToResponseMessagePayloadDtoFunction() {
        return message -> ResponseMessagePayloadDto.builder()
                .message(message.getMessage())
                .build();
    }

    private Function<ContactPhoneDto, ContactPhone> mapContactPhoneDtoToContactPhoneFunction() {
        return contactPhoneDto -> {
            CountryPhoneCode countryPhoneCode = singleValueMap.getEntity(
                    CountryPhoneCode.class,
                    "code",
                    contactPhoneDto.getCountryPhoneCode());
            return ContactPhone.builder()
                    .phoneNumber(contactPhoneDto.getPhoneNumber())
                    .countryPhoneCode(countryPhoneCode)
                    .build();
        };
    }

    private Function<ContactPhone, ContactPhoneDto> mapContactPhoneToContactPhoneDtoFunction() {
        return contactPhone -> ContactPhoneDto.builder()
                .phoneNumber(contactPhone.getPhoneNumber())
                .countryPhoneCode(contactPhone.getCountryPhoneCode().getValue())
                .build();
    }

    private Function<UserProfile, ContactPhoneDto> mapUserProfileToContactPhoneDtoFunction() {
        return userProfile -> {
            ContactPhone contactPhone = userProfile.getContactInfo().getContactPhone();
            return mapContactPhoneToContactPhoneDtoFunction().apply(contactPhone);
        };
    }

    private Function<OrderItemData, List<ContactPhoneDto>> mapOrderItemToContactPhoneDtoFunction() {
        return orderItem -> orderItem.getBeneficiaries().stream()
                .map(beneficiary -> mapContactPhoneToContactPhoneDtoFunction().apply(beneficiary.getContactPhone()))
                .toList();
    }

    private Function<BeneficiaryDto, Beneficiary> mapContactInfoDtoToContactInfoFunction() {
        return beneficiaryDto -> {
            CountryPhoneCode countryPhoneCode = singleValueMap.getEntity(
                    CountryPhoneCode.class,
                    "code",
                    beneficiaryDto.getContactPhone().getCountryPhoneCode());
            ContactPhone contactPhone = ContactPhone.builder()
                    .phoneNumber(beneficiaryDto.getContactPhone().getPhoneNumber())
                    .countryPhoneCode(countryPhoneCode)
                    .build();
            return Beneficiary.builder()
                    .firstName(beneficiaryDto.getFirstName())
                    .lastName(beneficiaryDto.getLastName())
                    .contactPhone(contactPhone)
                    .build();
        };
    }

    Function<OrderItemData, List<Beneficiary>> mapOrderItemToContactInfoListFunction() {
        return OrderItemData::getBeneficiaries;
    }

    Function<UserProfile, ContactInfoDto> mapUserProfileToContactInfoDtoFunction() {
        return userProfile -> {
            ContactInfo contactInfo = userProfile.getContactInfo();
            ContactPhone contactPhone = contactInfo.getContactPhone();
            return ContactInfoDto.builder()
                    .firstName(contactInfo.getFirstName())
                    .lastName(contactInfo.getLastName())
                    .contactPhone(ContactPhoneDto.builder()
                            .phoneNumber(contactPhone.getPhoneNumber())
                            .countryPhoneCode(contactPhone.getCountryPhoneCode().getValue())
                            .build())
                    .build();
        };
    }

    private Function<RegistrationUserProfileDto, UserProfile> mapRegistrationUserProfileDtoToUserProfileFunction() {
        return registrationUserProfileDto -> UserProfile.builder()
                .username(registrationUserProfileDto.getUsername())
                .email(registrationUserProfileDto.getEmail())
                .dateOfCreate(System.currentTimeMillis())
                .build();
    }

    private Function<UserProfile, UserProfileDto> mapUserProfileToUserProfileDtoFunction() {
        return userProfile -> {
            ContactInfoDto contactInfoDto = mapUserProfileToContactInfoDtoFunction().apply(userProfile);
            AddressDto addressDto = mapUserProfileToAddressDtoFunction().apply(userProfile);
            List<ResponseCreditCardDto> responseCreditCardDtoList = userProfile.getCreditCardList().stream()
                    .map(c -> mapCreditCardToResponseCardDtoFunction().apply(c))
                    .toList();
            return UserProfileDto.builder()
                    .email(userProfile.getEmail())
                    .contactInfo(contactInfoDto)
                    .creditCards(responseCreditCardDtoList)
                    .addressDto(addressDto)
                    .build();
        };
    }

    private Function<ReviewDto, Review> mapReviewDtoToReviewFunction() {
        return reviewDto -> Review.builder()
                .title(reviewDto.getTitle())
                .message(reviewDto.getMessage())
                .dateOfCreate(System.currentTimeMillis())
                .build();
    }

    private Function<Review, ResponseReviewDto> mapReviewToResponseReviewDtoFunction() {
        return review -> ResponseReviewDto.builder()
                .title(review.getTitle())
                .message(review.getMessage())
                .ratingValue(review.getRating().getValue())
                .dateOfCreate(new Date(System.currentTimeMillis()))
                .build();
    }

    private Function<PriceDto, Price> mapPriceDtoToPriceFunction() {
        return priceDto -> {
            Currency currency = singleValueMap.getEntity(
                    Currency.class,
                    "value",
                    priceDto.getCurrency());
            return Price.builder()
                    .amount(priceDto.getAmount())
                    .currency(currency)
                    .build();
        };
    }

    private Function<OrderItemData, PriceDto> mapOrderItemToFullPriceDtoFunction() {
        return orderItem -> {
            Price fullPrice = orderItem.getPayment().getFullPrice();
            return PriceDto.builder()
                    .amount(fullPrice.getAmount())
                    .currency(fullPrice.getCurrency().getValue())
                    .build();
        };
    }

    private Function<OrderItemData, PriceDto> mapOrderItemToTotalPriceDtoFunction() {
        return orderItem -> {
            Price fullPrice = orderItem.getPayment().getTotalPrice();
            return PriceDto.builder()
                    .amount(fullPrice.getAmount())
                    .currency(fullPrice.getCurrency().getValue())
                    .build();
        };
    }

    private Function<ItemDataOption, PriceDto> mapItemDataOptionToFullPriceDtoFunction() {
        return itemDataOption -> {
            Price fullPrice = itemDataOption.getFullPrice();
            return PriceDto.builder()
                    .amount(fullPrice.getAmount())
                    .currency(fullPrice.getCurrency().getValue())
                    .build();
        };
    }

    private Function<ItemDataOption, PriceDto> mapItemDataOptionToTotalPriceDtoFunction() {
        return itemDataOption -> {
            Price fullPrice = itemDataOption.getTotalPrice();
            return PriceDto.builder()
                    .amount(fullPrice.getAmount())
                    .currency(fullPrice.getCurrency().getValue())
                    .build();
        };
    }

    private Function<Price, PriceDto> mapPriceToPriceDtoFunction() {
        return price -> PriceDto.builder()
                .amount(price.getAmount())
                .currency(price.getCurrency().getValue())
                .build();
    }

    private Function<ItemDataOption, Price> mapItemDataOptionToFullPriceFunction() {
        return ItemDataOption::getFullPrice;
    }

    private Function<ItemDataOption, Price> mapItemDataOptionToTotalPriceFunction() {
        return ItemDataOption::getTotalPrice;
    }

    private Function<OrderItemData, Price> mapOrderItemToFullPriceFunction() {
        return orderItem -> orderItem.getPayment().getFullPrice();
    }

    private Function<OrderItemData, Price> mapOrderItemToTotalPriceFunction() {
        return orderItem -> orderItem.getPayment().getTotalPrice();
    }

    Function<OptionItemDto, Set<OptionItem>> mapOptionItemDtoToOptionItemSet() {
        return optionItemDto -> optionItemDto.getOptionGroupOptionItemsMap().entrySet().stream()
                .flatMap(entry -> {
                    OptionGroup optionGroup = singleValueMap.getEntity(OptionGroup.class, "value", entry.getKey());
                    OptionGroup newOG = optionGroup != null ? optionGroup : OptionGroup.builder()
                            .value(entry.getKey())
                            .build();
                    return entry.getValue().stream()
                            .map(optionItemValue -> OptionItem.builder()
                                    .optionName(optionItemValue)
                                    .optionGroup(newOG)
                                    .build());
                })
                .collect(Collectors.toSet());
    }

    Function<Set<OptionItem>, OptionItemDto> mapOptionItemSetToOptionItemDto() {
        return optionItems -> {
            Map<String, Set<String>> optionGroupOptionItemsMap = optionItems.stream()
                    .collect(Collectors.groupingBy(oi -> oi.getOptionGroup().getValue(),
                            Collectors.mapping(OptionItem::getOptionName, Collectors.toSet())));
            return OptionItemDto.builder()
                    .optionGroupOptionItemsMap(optionGroupOptionItemsMap)
                    .build();
        };
    }

    Function<DiscountDto, Discount> mapDiscountDtoToDiscountFunction() {
        return discountDto -> {
            Currency currency = singleValueMap.getEntity(
                    Currency.class,
                    "value",
                    discountDto.getCurrency());
            return Discount.builder()
                    .currency(currency)
                    .amount(discountDto.getAmount())
                    .charSequenceCode(discountDto.getCharSequenceCode())
                    .isActive(discountDto.isActive())
                    .isPercent(currency == null)
                    .build();
        };
    }

    private Function<ItemDataDto, ItemData> mapItemDataDtoToItemDataFunction() {
        return itemDataDto -> {
            Category category = singleValueMap.getEntity(Category.class, "name", itemDataDto.getCategoryName());
            Brand brand = singleValueMap.getEntity(Brand.class, "value", itemDataDto.getBrandName());
            ItemType itemType = singleValueMap.getEntity(ItemType.class, "value", itemDataDto.getItemTypeName());
            ItemStatus itemStatus = singleValueMap.getEntity(ItemStatus.class, "value", itemDataDto.getItemStatus());
            return ItemData.builder()
                    .category(category)
                    .brand(brand)
                    .itemType(itemType)
                    .status(itemStatus)
                    .build();
        };
    }

    private Function<ItemData, ResponseItemDataDto> mapItemDataToItemDataDtoFunction() {
        return itemData -> ResponseItemDataDto.builder()
                .brandName(itemData.getBrand().getValue())
                .categoryName(itemData.getCategory().getName())
                .itemTypeName(itemData.getItemType().getValue())
                .itemStatus(itemData.getStatus().getValue())
//                    .description(itemData.ge)
                .build();
    }

    private Function<DeliveryDto, Delivery> mapDeliveryDtoToDeliveryFunction() {
        return deliveryDto -> {
            Address address = mapAddressDtoToAddressFunction().apply(deliveryDto.getDeliveryAddress());
            DeliveryType deliveryType = singleValueMap.getEntity(
                    DeliveryType.class,
                    "value",
                    deliveryDto.getDeliveryType());

            return Delivery.builder()
                    .address(address)
                    .deliveryType(deliveryType)
                    .build();
        };
    }

    private Function<Delivery, DeliveryDto> mapDeliveryToDeliveryDtoFunction() {
        return delivery -> {
            AddressDto addressDto = mapAddressToAddressDtoFunction().apply(delivery.getAddress());

            return DeliveryDto.builder()
                    .deliveryAddress(addressDto)
                    .deliveryType(delivery.getDeliveryType().getValue())
                    .build();
        };
    }



}
