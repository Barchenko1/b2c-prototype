package com.b2c.prototype.configuration.transform;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.constant.FeeType;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.order.ArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.payload.commission.CommissionValueDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.order.ContactPhoneDto;
import com.b2c.prototype.modal.dto.payload.order.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.order.single.DeliveryDto;
import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewCommentDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewCommentDto;
import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.InitDiscountDto;
import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.dto.payload.user.UserAddressDto;
import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;
import com.b2c.prototype.modal.dto.payload.option.item.ZoneOptionDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;
import com.b2c.prototype.modal.dto.payload.store.ResponseStoreDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.UserAddress;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.message.MessageTemplate;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.order.CustomerSingleDeliveryOrder;
import com.b2c.prototype.modal.entity.payment.CommissionValue;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.review.ReviewComment;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.util.CardUtil;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Util.getUUID;

@Configuration
public class TransformationEntityConfiguration {

    private <T extends AbstractConstantEntity> Function<ConstantPayloadDto, T> mapConstantEntityPayloadDtoToConstantEntityFunction(Supplier<T> entitySupplier) {
        return constantPayloadDto -> {
            T entity = entitySupplier.get();
            entity.setValue(constantPayloadDto.getValue());
            entity.setKey(constantPayloadDto.getKey());
            return entity;
        };
    }

    private <T extends AbstractConstantEntity> Function<T, ConstantPayloadDto> mapConstantEntityToConstantEntityPayloadDtoFunction() {
        return abstractConstantEntity -> ConstantPayloadDto.builder()
                .value(abstractConstantEntity.getValue())
                .key(abstractConstantEntity.getKey())
                .build();
    }

    private BiFunction<Session, AddressDto, Address> mapAddressDtoToAddressFunction() {
        return (session, addressDto) -> Address.builder()
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .buildingNumber(addressDto.getBuildingNumber())
                .florNumber(addressDto.getFlorNumber())
                .apartmentNumber(addressDto.getApartmentNumber())
                .zipCode(addressDto.getZipCode())
                .build();
    }

    private BiFunction<Session, UserAddressDto, UserAddress> mapUserAddressDtoToUserAddressFunction() {
        return (session, userAddressDto) -> {
            AddressDto addressDto = userAddressDto.getAddress();
            String userAddressCombination = combineUserAddress(
                    String.valueOf(addressDto.getCountry()),
                    addressDto.getCity(),
                    addressDto.getStreet(),
                    addressDto.getBuildingNumber(),
                    addressDto.getApartmentNumber());
            return UserAddress.builder()
                    .address(mapAddressDtoToAddressFunction().apply(session, userAddressDto.getAddress()))
                    .isDefault(userAddressDto.isDefault())
                    .build();
        };
    }

    private Function<Address, AddressDto> mapAddressToAddressDtoFunction() {
        return address -> AddressDto.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .buildingNumber(address.getBuildingNumber())
                .florNumber(address.getFlorNumber())
                .apartmentNumber(address.getApartmentNumber())
                .zipCode(address.getZipCode())
                .build();
    }

    private Function<UserAddress, UserAddressDto> mapUserAddressToUserAddressDtoFunction() {
        return userAddress -> UserAddressDto.builder()
                .address(mapAddressToAddressDtoFunction().apply(userAddress.getAddress()))
                .isDefault(userAddress.isDefault())
                .build();
    }

    private Function<UserAddress, AddressDto> mapUserAddressToAddressDtoDtoFunction() {
        return userAddress -> mapAddressToAddressDtoFunction().apply(userAddress.getAddress());
    }

    private Function<DeliveryArticularItemQuantity, AddressDto> mapOrderItemToAddressDtoFunction() {
        return orderItem -> {
            Address address = orderItem.getDelivery().getAddress();
            return mapAddressToAddressDtoFunction().apply(address);
        };
    }

    private Function<CreditCardDto, UserCreditCard> mapCreditCardDtoUserCreditCardDtoFunction() {
        return creditCardDto -> UserCreditCard.builder()
                .creditCard(mapCreditCardDtoToCreditCardFunction().apply(creditCardDto))
                .isDefault(true)
                .build();
    }

    private Function<CreditCardDto, CreditCard> mapCreditCardDtoToCreditCardFunction() {
        return creditCardDto -> CreditCard.builder()
                .cardNumber(creditCardDto.getCardNumber())
                .monthOfExpire(creditCardDto.getMonthOfExpire())
                .yearOfExpire(creditCardDto.getYearOfExpire())
                .isActive(CardUtil.isCardActive(creditCardDto.getMonthOfExpire(), creditCardDto.getYearOfExpire()))
                .cvv(creditCardDto.getCvv())
                .ownerName(creditCardDto.getOwnerName())
                .ownerSecondName(creditCardDto.getOwnerSecondName())
                .build();
    }

    private Function<UserCreditCardDto, UserCreditCard> mapUserCreditCardDtoToUserCreditCardFunction() {
        return userCreditCardDto -> UserCreditCard.builder()
                .creditCard(mapCreditCardDtoToCreditCardFunction().apply(userCreditCardDto.getCreditCard()))
                .isDefault(userCreditCardDto.isDefault())
                .build();
    }

    private BiFunction<Session, MessageDto, Message> mapMessageDtoToMessageFunction() {
        return (session, messageDto) -> {
//            MessageStatus messageStatus = sessionEntityFetcher.fetchMessageStatus(session, messageDto.getStatus());
//            MessageType messageType = sessionEntityFetcher.fetchMessageType(session, messageDto.getMessageTemplate().getType());

            return Message.builder()
                    .messageTemplate(MessageTemplate.builder()
                            .title(messageDto.getMessageTemplate().getTitle())
                            .message(messageDto.getMessageTemplate().getMessage())
                            .build())
//                    .status(messageStatus)
                    .build();
        };
    }

    private Function<Message, ResponseMessageOverviewDto> mapMessageToResponseMessageOverviewDtoFunction() {
        return message -> {
            MessageTemplate messageTemplate = message.getMessageTemplate();
            return ResponseMessageOverviewDto.builder()
                    .title(messageTemplate.getTitle())
                    .type(message.getType().getKey())
                    .status(message.getStatus().getKey())
                    .build();
        };
    }

    private Function<Message, ResponseMessagePayloadDto> mapMessageToResponseMessagePayloadDtoFunction() {
        return message -> ResponseMessagePayloadDto.builder()
                .message(message.getMessageTemplate().getMessage())
                .build();
    }

    private BiFunction<Session, ContactPhoneDto, ContactPhone> mapContactPhoneDtoToContactPhoneFunction() {
        return (session, contactPhoneDto) -> {
//            CountryPhoneCode countryPhoneCode = sessionEntityFetcher.fetchCountryPhoneCode(session, contactPhoneDto.getCountryPhoneCode());
            return ContactPhone.builder()
                    .phoneNumber(contactPhoneDto.getPhoneNumber())
//                    .countryPhoneCode(countryPhoneCode)
                    .build();
        };
    }

    private Function<DeviceDto, Device> mapDeviceDtoToDeviceFunction() {
        return deviceDto -> Device.builder()
                .loginTime(LocalDateTime.now())
                .screenHeight(deviceDto.getScreenHeight())
                .screenWidth(deviceDto.getScreenWidth())
                .userAgent(deviceDto.getUserAgent())
                .timezone(deviceDto.getTimezone())
                .language(deviceDto.getLanguage())
                .platform(deviceDto.getPlatform())
                .build();
    }

    private BiFunction<Session, PostDto, Post> mapPostDtoToPostFunction() {
        return (session, postDto) -> Post.builder()
                .title(postDto.getTitle())
                .message(postDto.getMessage())
                .authorEmail(postDto.getAuthorEmail())
                .authorName(postDto.getAuthorName())
//                .parent(sessionEntityFetcher.fetchPost(session, postDto.getParentPostId()).orElse(null))
                .postUniqId(getUUID())
//                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .build();
    }

    private BiFunction<Session, ReviewDto, Review> mapReviewDtoToReviewFunction() {
        return (session, reviewDto) -> {
//            ReviewStatus reviewStatus = sessionEntityFetcher.fetchReviewStatus(session, ReviewStatusEnum.PENDING.name());
//            Rating rating = sessionEntityFetcher.fetchRating(session, reviewDto.getRatingValue());
            return Review.builder()
                    .title(reviewDto.getTitle())
                    .message(reviewDto.getMessage())
//                    .dateOfCreate(getCurrentTimeMillis())
                    .reviewUniqId(getUUID())
//                    .comments(sessionEntityFetcher.fetchReviewComments(session, reviewDto.getReviewId()))
//                    .status(reviewStatus)
//                    .rating(rating)
                    .build();
        };
    }

    private Function<Review, ResponseReviewDto> mapReviewToResponseReviewDtoFunction() {
        return review -> {
            UserDetails userDetails = review.getUserDetails();
            ContactInfo contactInfo = userDetails.getContactInfo();
            List<ResponseReviewCommentDto> responseReviewCommentDtoList = review.getComments().stream()
                    .map(mapReviewCommentToResponseReviewCommentDtoFunction())
                    .toList();
            return ResponseReviewDto.builder()
                    .title(review.getTitle())
                    .message(review.getMessage())
//                    .ratingValue(review.getRating().getValue())
//                    .dateOfCreate(new Date(review.getDateOfCreate()))
                    .status(mapConstantEntityToConstantEntityPayloadDtoFunction().apply(review.getStatus()))
                    .username(userDetails.getUsername())
                    .email(contactInfo.getEmail())
                    .firstName(contactInfo.getFirstName())
                    .lastName(contactInfo.getLastName())
                    .comments(responseReviewCommentDtoList)
                    .build();
        };
    }

    private Function<ReviewCommentDto, ReviewComment> mapReviewCommentDtoToReviewCommentFunction() {
        return (reviewCommentDto) -> ReviewComment.builder()
                .title(reviewCommentDto.getTitle())
                .message(reviewCommentDto.getMessage())
//                .dateOfCreate(getCurrentTimeMillis())
                .reviewCommentUniqId(getUUID())
                .build();
    }

    private Function<ReviewComment, ResponseReviewCommentDto> mapReviewCommentToResponseReviewCommentDtoFunction() {
        return (reviewCommentDto) -> {
            UserDetails userDetails = reviewCommentDto.getUserDetails();
            ContactInfo contactInfo = userDetails.getContactInfo();
            return ResponseReviewCommentDto.builder()
                    .title(reviewCommentDto.getTitle())
                    .message(reviewCommentDto.getMessage())
//                    .dateOfCreate(reviewCommentDto.getDateOfCreate())
                    .commentId(reviewCommentDto.getReviewCommentUniqId())
                    .authorEmail(contactInfo.getEmail())
                    .authorName(contactInfo.getFirstName())
                    .authorLastName(contactInfo.getLastName())
                    .build();
        };
    }

    private BiFunction<Session, PriceDto, Price> mapPriceDtoToPriceFunction() {
        return (session, priceDto) -> {
//            Currency currency = sessionEntityFetcher.fetchCurrency(session, priceDto.getCurrency());
            return Price.builder()
                    .amount(priceDto.getAmount())
//                    .currency(currency)
                    .build();
        };
    }

    private Function<CustomerSingleDeliveryOrder, PriceDto> mapOrderToFullPriceDtoFunction() {
        return customerSingleDeliveryOrder -> {
//            Price fullPrice = customerSingleDeliveryOrder.getPayment().getCommissionPriceInfo();
            return PriceDto.builder()
//                    .amount(fullPrice.getAmount())
//                    .currency(fullPrice.getCurrency().getValue())
                    .build();
        };
    }

    private Set<ArticularItem> mapArticularItemSet(Session session,
                                                   Set<ArticularItemDto> articularItemDtoSet,
                                                   Set<OptionItem> optionItems,
                                                   Map<String, ArticularItem> articularItemMap) {
        return articularItemDtoSet.stream()
                .map(articularItemDto -> {
//                    Discount discount = sessionEntityFetcher.fetchOptionArticularDiscount(session, articularItemDto.getDiscount().getCharSequenceCode())
//                            .orElse(mapInitDiscountDtoToDiscount().apply(session, articularItemDto.getDiscount()));

//                    ArticularStatus articularStatus = sessionEntityFetcher.fetchArticularStatus(session, articularItemDto.getStatus());
                    ArticularItem existingArticularItem = articularItemMap.get(articularItemDto.getArticularId());
                    long articularId = Optional.ofNullable(existingArticularItem).map(ArticularItem::getId).orElse(0L);
                    long fullPriceId = Optional.ofNullable(existingArticularItem).map(item -> item.getFullPrice().getId()).orElse(0L);
                    long totalPriceId = Optional.ofNullable(existingArticularItem).map(item -> item.getTotalPrice().getId()).orElse(0L);
                    long discountId = Optional.ofNullable(existingArticularItem).map(item -> item.getDiscount().getId()).orElse(0L);
                    ArticularItem articularItem = ArticularItem.builder()
                            .id(articularId)
                            .articularUniqId(articularItemDto.getArticularId() != null ? articularItemDto.getArticularId() : getUUID())
//                            .dateOfCreate(getCurrentTimeMillis())
                            .productName(articularItemDto.getProductName())
//                            .status(articularStatus)
                            .fullPrice(mapPriceDtoToPriceFunction().apply(session, articularItemDto.getFullPrice()))
                            .totalPrice(mapPriceDtoToPriceFunction().apply(session, articularItemDto.getTotalPrice()))
//                            .discount(discount)
                            .build();

//                    articularItemDto.getOptions().stream()
//                            .flatMap(soi -> optionItems.stream()
//                                    .filter(optionItem -> optionItem.getValue().equals(soi.getOptionItem().getValue()))
//                            )
//                            .forEach(optionItem -> {
//                                optionItem.getArticularItems().stream()
//                                        .filter(ai -> ai.getArticularUniqId().equals(articularItem.getArticularUniqId()))
//                                        .collect(Collectors.toSet())
//                                        .forEach(optionItem::removeArticularItem);
//
//                                optionItem.addArticularItem(articularItem);
//                            });

                    articularItem.getFullPrice().setId(fullPriceId);
                    articularItem.getTotalPrice().setId(totalPriceId);
                    articularItem.getDiscount().setId(discountId);

                    return articularItem;
                })
                .collect(Collectors.toSet());
    }

    private BiFunction<Session, DeliveryDto, Delivery> mapDeliveryDtoToDelivery() {
        return (session, deliveryDto) -> {
//            DeliveryType deliveryType = sessionEntityFetcher.fetchDeliveryType(session, deliveryDto.getDeliveryType());
            Address address = mapAddressDtoToAddressFunction().apply(session, deliveryDto.getDeliveryAddress());
//            TimeDurationOption timeDurationOption = sessionEntityFetcher.fetchTimeDurationOption(session, deliveryDto.getTimeDurationOption());
            return Delivery.builder()
//                    .deliveryType(deliveryType)
                    .address(address)
//                    .timeDurationOption(timeDurationOption)
                    .build();
        };
    }

    private BiFunction<Session, ContactInfoDto, ContactInfo> mapContactInfoDtoToContactInfo() {
        return (session, contactInfoDto) -> {
            ContactPhone contactPhone = mapContactPhoneDtoToContactPhoneFunction().apply(session, contactInfoDto.getContactPhone());
            return ContactInfo.builder()
                    .email(contactInfoDto.getEmail())
                    .firstName(contactInfoDto.getFirstName())
                    .lastName(contactInfoDto.getLastName())
                    .contactPhone(contactPhone)
                    .build();
        };
    }

    private BiFunction<Session, ArticularItemQuantityDto, ArticularItemQuantity> mapArticularItemQuantityDtoToArticularItemQuantityFunction() {
        return (session, articularItemQuantityDto) -> {
            ArticularItem articularItem = null;
//            ArticularItem articularItem = sessionEntityFetcher.fetchArticularItem(session, articularItemQuantityDto.getArticularId());
//            ArticularItemQuantity articularItemQuantity = ArticularItemQuantity.builder()
//                    .articularItem(articularItem)
//                    .quantity(articularItemQuantityDto.getQuantity())
//                    .build();
            Price fullSumPrice = mapPrice().apply(articularItem.getFullPrice().getCurrency(),
                    articularItem.getFullPrice().getAmount() * articularItemQuantityDto.getQuantity());
            Price totalSumPrice = mapPrice().apply(articularItem.getTotalPrice().getCurrency(),
                    articularItem.getTotalPrice().getAmount() * articularItemQuantityDto.getQuantity());
            Discount itemDiscount = articularItem.getDiscount();
            Price discountSumPrice = null;
            if (itemDiscount != null) {
                discountSumPrice = mapPrice().apply(itemDiscount.getCurrency(),
                        itemDiscount.getAmount() * articularItemQuantityDto.getQuantity());
            }
            return ArticularItemQuantity.builder()
//                    .articularItemQuantity(articularItemQuantity)
//                    .fullPriceSum(fullSumPrice)
//                    .totalPriceSum(totalSumPrice)
//                    .discountPriceSum(discountSumPrice)
                    .build();
        };
    }

    private BiFunction<Session, InitDiscountDto, Discount> mapInitDiscountDtoToDiscount() {
        return (session, initDiscountDto) -> {
//            Currency currency = sessionEntityFetcher.fetchCurrency(session, initDiscountDto.getCurrency());
            return Discount.builder()
//                    .currency(currency)
                    .amount(initDiscountDto.getAmount())
                    .charSequenceCode(initDiscountDto.getCharSequenceCode())
                    .isActive(initDiscountDto.getIsActive())
//                    .isPercent(currency == null)
                    .build();
        };
    }

    private BiFunction<Currency, Double, Price> mapPrice() {
        return (currency, amount) -> Price.builder()
                .currency(currency)
                .amount(amount)
                .build();
    }

    private Function<Discount, InitDiscountDto> mapDiscountToInitDiscountDto() {
        return discount -> InitDiscountDto.builder()
                .currency(discount.getCurrency().getKey())
                .amount(discount.getAmount())
                .charSequenceCode(discount.getCharSequenceCode())
                .isActive(discount.isActive())
                .isPercent(discount.isPercent())
                .build();
    }

    private BiFunction<Session, DiscountDto, Discount> mapDiscountDtoToDiscountFunction() {
        return (session, discountDto) -> {
//            Currency currency = sessionEntityFetcher.fetchCurrency(session, discountDto.getCurrency());
            return Discount.builder()
//                    .currency(currency)
                    .amount(discountDto.getAmount())
                    .charSequenceCode(discountDto.getCharSequenceCode())
                    .isActive(discountDto.getIsActive())
//                    .isPercent(currency == null)
                    .build();
        };
    }

    private Function<Discount, DiscountDto> mapDiscountToDiscountDtoFunction() {
        return discount -> DiscountDto.builder()
                .currency(discount.getCurrency().getKey())
                .amount(discount.getAmount())
                .charSequenceCode(discount.getCharSequenceCode())
                .isActive(discount.isActive())
                .build();
    }

    private Function<List<ArticularItem>, DiscountDto> mapItemDataOptionListToDiscountDtoFunction() {
        return itemDataOptionList -> {
            Discount discount = itemDataOptionList.get(0).getDiscount();
            return DiscountDto.builder()
                    .charSequenceCode(discount.getCharSequenceCode())
                    .amount(discount.getAmount())
                    .isActive(discount.isActive())
                    .currency(discount.getCurrency() != null ? discount.getCurrency().getKey() : null)
                    .articularIdSet(
                            itemDataOptionList.stream()
                                    .map(ArticularItem::getArticularUniqId)
                                    .collect(Collectors.toSet())
                    )
                    .build();
        };
    }

    private Function<List<ArticularItem>, List<DiscountDto>> mapItemDataOptionListToDiscountDtoListFunction() {
        return itemDataOptionList -> itemDataOptionList.stream()
                .collect(Collectors.groupingBy(ArticularItem::getDiscount))
                .entrySet().stream()
                .map(entry -> {
                    Discount discount = entry.getKey();
                    Set<String> articularIdSet = entry.getValue().stream()
                            .map(ArticularItem::getArticularUniqId)
                            .collect(Collectors.toSet());

                    return DiscountDto.builder()
                            .charSequenceCode(discount.getCharSequenceCode())
                            .amount(discount.getAmount())
                            .isActive(discount.isActive())
                            .currency(discount.getCurrency() != null ? discount.getCurrency().getKey() : null)
                            .articularIdSet(articularIdSet)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private Function<ArticularStatus, ConstantPayloadDto> mapArticularStatusToConstantPayloadDtoFunction() {
        return articularStatus -> ConstantPayloadDto.builder()
                .value(articularStatus.getValue())
                .key(articularStatus.getKey())
                .build();
    }

    private BiFunction<Session, DeliveryDto, Delivery> mapDeliveryDtoToDeliveryFunction() {
        return (session, deliveryDto) -> {
            Address address = mapAddressDtoToAddressFunction().apply(session, deliveryDto.getDeliveryAddress());
//            DeliveryType deliveryType = sessionEntityFetcher.fetchDeliveryType(session, deliveryDto.getDeliveryType());

            return Delivery.builder()
                    .address(address)
//                    .deliveryType(deliveryType)
                    .build();
        };
    }

    private Function<Delivery, DeliveryDto> mapDeliveryToDeliveryDtoFunction() {
        return delivery -> {
            AddressDto addressDto = mapAddressToAddressDtoFunction().apply(delivery.getAddress());

            return DeliveryDto.builder()
                    .deliveryAddress(addressDto)
                    .deliveryType(delivery.getDeliveryType().getKey())
                    .build();
        };
    }

    private BiFunction<Session, ZoneOptionDto, ZoneOption> mapZoneOptionDtoToZoneOptionFunction() {
        return (session, zoneOptionDto) -> {
            Price price = mapPriceDtoToPriceFunction().apply(session, zoneOptionDto.getPrice());
            return ZoneOption.builder()
//                    .zoneName(zoneOptionDto.getZoneName())
//                    .city(zoneOptionDto.getCity())
                    .price(price)
                    .build();
        };
    }

    private BiFunction<Session, MinMaxCommissionDto, MinMaxCommission> mapMinMaxCommissionDtoToMinMaxCommission() {
        return (session, minMaxCommissionDto) -> MinMaxCommission.builder()
                .minCommission(mapCommissionValueDtoToCommissionValue().apply(session, minMaxCommissionDto.getMinCommissionValue()))
                .maxCommission(mapCommissionValueDtoToCommissionValue().apply(session, minMaxCommissionDto.getMaxCommissionValue()))
//                .commissionType(CommissionType.valueOf(minMaxCommissionDto.getCommissionType()))
                .changeCommissionPrice(mapPriceDtoToPriceFunction().apply(session, minMaxCommissionDto.getChangeCommissionPrice()))
                .build();
    }

    private Function<CommissionValue, CommissionValueDto> mapCommissionValueToCommissionValueDto() {
        return cv -> CommissionValueDto.builder()
                .amount(cv.getAmount())
                .feeType(cv.getFeeType().name())
                .currency(cv.getCurrency() != null ? cv.getCurrency().getKey() : null)
                .build();
    }

    private BiFunction<Session, CommissionValueDto, CommissionValue> mapCommissionValueDtoToCommissionValue() {
        return (session, commissionValueDto) -> {
            FeeType feeType = FeeType.valueOf(commissionValueDto.getFeeType());
            if (FeeType.FIXED.equals(feeType)) {
//                Currency currency = sessionEntityFetcher.fetchCurrency(session, commissionValueDto.getCurrency());
                return CommissionValue.builder()
                        .amount(commissionValueDto.getAmount())
                        .feeType(FeeType.valueOf(commissionValueDto.getFeeType()))
//                        .currency(currency)
                        .build();
            }
            return CommissionValue.builder()
                    .amount(commissionValueDto.getAmount())
                    .feeType(FeeType.valueOf(commissionValueDto.getFeeType()))
                    .build();
        };
    }

    private BiFunction<Session, StoreDto, Store> mapStoreDtoToStoreFunction() {
        return (session, storeDto) -> Store.builder()
                .storeUniqId(getUUID())
                .storeName(storeDto.getStoreName())
                .address(storeDto.getAddress() != null ? mapAddressDtoToAddressFunction().apply(session, storeDto.getAddress()) : null)
                .isActive(true)
                .build();
    }

    private Function<Store, ResponseStoreDto> mapStoreToResponseStoreDtoFunction() {
        return store -> {
//            List<ArticularItemQuantityDto> articularItemQuantityList = store.getArticularItemQuantities().stream()
//                    .map(mapArticularItemQuantityToArticularItemQuantityDtoFunction())
//                    .toList();
            return ResponseStoreDto.builder()
                    .storeName(store.getStoreName())
                    .storeId(store.getStoreUniqId())
                    .address(mapAddressToAddressDtoFunction().apply(store.getAddress()))
                    .articularItemQuantityList(null)
                    .build();
        };
    }

    private String combineUserAddress(String country, String city, String street, String buildingNumber, int flatNumber) {
        return country +
                "/" +
                city +
                "/" +
                street +
                "/" +
                buildingNumber +
                "/" +
                flatNumber;
    }

}
