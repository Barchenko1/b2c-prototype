package com.b2c.prototype.configuration.transform;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneIntegerFieldEntityDto;
import com.b2c.prototype.modal.dto.request.AddressDto;
import com.b2c.prototype.modal.dto.request.ContactInfoDto;
import com.b2c.prototype.modal.dto.request.ContactPhoneDto;
import com.b2c.prototype.modal.dto.request.CreditCardDto;
import com.b2c.prototype.modal.dto.request.DeliveryDto;
import com.b2c.prototype.modal.dto.request.DiscountDto;
import com.b2c.prototype.modal.dto.request.ItemDataDto;
import com.b2c.prototype.modal.dto.request.MessageDto;
import com.b2c.prototype.modal.dto.request.OptionItemDto;
import com.b2c.prototype.modal.dto.request.PriceDto;
import com.b2c.prototype.modal.dto.request.RegistrationUserProfileDto;
import com.b2c.prototype.modal.dto.request.ReviewDto;
import com.b2c.prototype.modal.dto.request.UserProfileDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.response.ResponseMessagePayloadDto;
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
        transformationFunctionService.addOneFieldEntityDtoTransformationFunction(Brand.class, mapOneFieldEntityDtoBrandFunction());
        transformationFunctionService.addOneFieldEntityDtoTransformationFunction(CountType.class, mapOneFieldEntityDtoCountTypeFunction());
        transformationFunctionService.addOneFieldEntityDtoTransformationFunction(CountryPhoneCode.class, mapOneFieldEntityDtoCountryPhoneCodeFunction());
        transformationFunctionService.addOneFieldEntityDtoTransformationFunction(Country.class, mapOneFieldEntityDtoCountryFunction());
        transformationFunctionService.addOneFieldEntityDtoTransformationFunction(Currency.class, mapOneFieldEntityDtoCurrencyFunction());
        transformationFunctionService.addOneFieldEntityDtoTransformationFunction(DeliveryType.class, mapOneFieldEntityDtoDeliveryTypeFunction());
        transformationFunctionService.addOneFieldEntityDtoTransformationFunction(ItemType.class, mapOneFieldEntityDtoItemTypeFunction());
        transformationFunctionService.addOneFieldEntityDtoTransformationFunction(ItemStatus.class, mapOneFieldEntityDtoItemStatusFunction());
        transformationFunctionService.addOneFieldEntityDtoTransformationFunction(MessageStatus.class, getOneFieldEntityDtoMessageStatusFunction());
        transformationFunctionService.addOneFieldEntityDtoTransformationFunction(MessageType.class, mapOneFieldEntityDtoMessageTypeFunction());
        transformationFunctionService.addOneFieldEntityDtoTransformationFunction(OptionGroup.class, mapOneFieldEntityDtoOptionGroupFunction());
        transformationFunctionService.addOneFieldEntityDtoTransformationFunction(OrderStatus.class, mapOneFieldEntityDtoOrderStatusFunction());
        transformationFunctionService.addOneFieldEntityDtoTransformationFunction(PaymentMethod.class, mapOneFieldEntityDtoPaymentMethodFunction());

        transformationFunctionService.addTransformationFunction(OneIntegerFieldEntityDto.class, Rating.class,  mapOneIntegerFieldEntityDtoRatingFunction());

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
        transformationFunctionService.addTransformationFunction(OrderItemData.class, ContactPhoneDto.class, "list",mapOrderItemToContactPhoneDtoFunction());

        transformationFunctionService.addTransformationFunction(ContactInfoDto.class, ContactInfo.class, mapContactInfoDtoToContactInfoFunction());

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

        transformationFunctionService.addTransformationFunction(OrderItemData.class, ContactInfo.class, "list", mapOrderItemToContactInfoListFunction());
    }

    private Function<OneFieldEntityDto, Brand> mapOneFieldEntityDtoBrandFunction() {
        return requestOneFieldEntityDto -> Brand.builder()
                .value(requestOneFieldEntityDto.getValue())
                .build();
    }

    private Function<OneFieldEntityDto, CountType> mapOneFieldEntityDtoCountTypeFunction() {
        return oneFieldEntityDto -> CountType.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    private Function<OneFieldEntityDto, CountryPhoneCode> mapOneFieldEntityDtoCountryPhoneCodeFunction() {
        return oneFieldEntityDto -> CountryPhoneCode.builder()
                .code(oneFieldEntityDto.getValue())
                .build();
    }

    private Function<OneFieldEntityDto, Country> mapOneFieldEntityDtoCountryFunction() {
        return oneFieldEntityDto -> Country.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    private Function<OneFieldEntityDto, Currency> mapOneFieldEntityDtoCurrencyFunction() {
        return oneFieldEntityDto -> Currency.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    private Function<OneFieldEntityDto, DeliveryType> mapOneFieldEntityDtoDeliveryTypeFunction() {
        return oneFieldEntityDto -> DeliveryType.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    private Function<OneFieldEntityDto, ItemStatus> mapOneFieldEntityDtoItemStatusFunction() {
        return oneFieldEntityDto -> ItemStatus.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    private Function<OneFieldEntityDto, ItemType> mapOneFieldEntityDtoItemTypeFunction() {
        return oneFieldEntityDto -> ItemType.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    private Function<OneFieldEntityDto, MessageStatus> getOneFieldEntityDtoMessageStatusFunction() {
        return oneFieldEntityDto -> MessageStatus.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    private Function<OneFieldEntityDto, MessageType> mapOneFieldEntityDtoMessageTypeFunction() {
        return oneFieldEntityDto -> MessageType.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    private Function<OneFieldEntityDto, OptionGroup> mapOneFieldEntityDtoOptionGroupFunction() {
        return oneFieldEntityDto -> OptionGroup.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    private Function<OneFieldEntityDto, OrderStatus> mapOneFieldEntityDtoOrderStatusFunction() {
        return oneFieldEntityDto -> OrderStatus.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    private Function<OneFieldEntityDto, PaymentMethod> mapOneFieldEntityDtoPaymentMethodFunction() {
        return oneFieldEntityDto -> PaymentMethod.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    private Function<OneIntegerFieldEntityDto, Rating> mapOneIntegerFieldEntityDtoRatingFunction() {
        return oneFieldEntityDto -> Rating.builder()
                .value(oneFieldEntityDto.getValue())
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
        MessageStatus messageStatus = singleValueMap.getEntity(
                MessageStatus.class,
                "value",
                "value");
        MessageType messageType = singleValueMap.getEntity(
                MessageType.class,
                "value",
                "value");
        return messageDto -> Message.builder()
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
                .countryPhoneCode(contactPhone.getCountryPhoneCode().getCode())
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

    private Function<ContactInfoDto, ContactInfo> mapContactInfoDtoToContactInfoFunction() {
        return contactInfoDto -> {
            CountryPhoneCode countryPhoneCode = singleValueMap.getEntity(
                    CountryPhoneCode.class,
                    "code",
                    contactInfoDto.getContactPhone().getCountryPhoneCode());
            ContactPhone contactPhone = ContactPhone.builder()
                    .phoneNumber(contactInfoDto.getContactPhone().getPhoneNumber())
                    .countryPhoneCode(countryPhoneCode)
                    .build();
            return ContactInfo.builder()
                    .name(contactInfoDto.getName())
                    .secondName(contactInfoDto.getSecondName())
                    .contactPhone(contactPhone)
                    .build();
        };
    }

    Function<OrderItemData, List<ContactInfo>> mapOrderItemToContactInfoListFunction() {
        return OrderItemData::getBeneficiaries;
    }

    Function<UserProfile, ContactInfoDto> mapUserProfileToContactInfoDtoFunction() {
        return userProfile -> {
            ContactInfo contactInfo = userProfile.getContactInfo();
            ContactPhone contactPhone = contactInfo.getContactPhone();
            return ContactInfoDto.builder()
                    .name(contactInfo.getName())
                    .secondName(contactInfo.getSecondName())
                    .contactPhone(ContactPhoneDto.builder()
                            .phoneNumber(contactPhone.getPhoneNumber())
                            .countryPhoneCode(contactPhone.getCountryPhoneCode().getCode())
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
//            Price price = mapPriceDtoToPriceFunction().apply(itemDataDto.getPrice());
//            Discount discount = null;
//            if (itemDataDto.getDiscount() != null) {
//                discount = mapDiscountDtoToDiscountFunction().apply(itemDataDto.getDiscount());
//            }
            return ItemData.builder()
                    .category(category)
                    .brand(brand)
                    .itemType(itemType)
                    .status(itemStatus)
                    .build();
        };
    }

    private Function<ItemData, ResponseItemDataDto> mapItemDataToItemDataDtoFunction() {
        return itemData -> {
//            PriceDto fullPrice = mapItemDataToFullPriceDtoFunction().apply(itemData);
//            PriceDto currentPrice = priceCalculationService.calculateCurrentPrice(
//                    itemData.getFullPrice(), itemData.getDiscount());

            return ResponseItemDataDto.builder()
                    .brandName(itemData.getBrand().getValue())
                    .categoryName(itemData.getCategory().getName())
                    .itemTypeName(itemData.getItemType().getValue())
                    .itemStatus(itemData.getStatus().getValue())
//                    .description(itemData.ge)
                    .build();
        };
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
