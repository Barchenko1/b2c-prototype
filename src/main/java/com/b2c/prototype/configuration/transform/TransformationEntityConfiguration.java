package com.b2c.prototype.configuration.transform;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import com.b2c.prototype.modal.constant.FeeType;
import com.b2c.prototype.modal.constant.MessageStatusEnum;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.dto.common.SearchFieldUpdateCollectionEntityDto;
import com.b2c.prototype.modal.dto.common.SearchFieldUpdateEntityDto;
import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.payload.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.ArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.payload.commission.CommissionDto;
import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;
import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.DeliveryDto;
import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.InitDiscountDto;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.user.MessageDto;
import com.b2c.prototype.modal.dto.payload.option.OptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.OptionGroupOptionItemSetDto;
import com.b2c.prototype.modal.dto.payload.option.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.OrderArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.payload.PaymentDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.option.SingleOptionItemDto;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.payload.user.UserAddressDto;
import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;
import com.b2c.prototype.modal.dto.payload.constant.BrandDto;
import com.b2c.prototype.modal.dto.payload.constant.CategoryValueDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.constant.ItemTypeDto;
import com.b2c.prototype.modal.dto.response.ResponseArticularItemDto;
import com.b2c.prototype.modal.dto.response.ResponseCommissionDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseDeviceDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.response.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;
import com.b2c.prototype.modal.dto.response.ResponseStoreDto;
import com.b2c.prototype.modal.dto.response.ResponseTimeDurationOptionDto;
import com.b2c.prototype.modal.dto.response.ResponseUserAddressDto;
import com.b2c.prototype.modal.dto.response.ResponseUserCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseUserDetailsDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.address.UserAddress;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantityPrice;
import com.b2c.prototype.modal.entity.item.AvailabilityStatus;
import com.b2c.prototype.modal.entity.message.MessageBox;
import com.b2c.prototype.modal.entity.message.MessageTemplate;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.modal.entity.order.CustomerOrder;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.BuyerCommission;
import com.b2c.prototype.modal.entity.payment.SellerCommission;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.payment.PaymentStatus;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.service.function.ITransformationFunction;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.help.calculate.IPriceCalculationService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.ARTICULAR_ITEM_FULL;
import static com.b2c.prototype.util.Constant.CHAR_SEQUENCE_CODE;
import static com.b2c.prototype.util.Constant.USER_ID;
import static com.b2c.prototype.util.Constant.VALUE;
import static com.b2c.prototype.util.Util.getCurrentTimeMillis;
import static com.b2c.prototype.util.Util.getEmailPrefix;
import static com.b2c.prototype.util.Util.getUUID;

@Configuration
public class TransformationEntityConfiguration {

    private final IQueryService queryService;
    private final IParameterFactory parameterFactory;
    private final IPriceCalculationService priceCalculationService;

    public TransformationEntityConfiguration(ITransformationFunctionService transformationFunctionService,
                                             IQueryService queryService,
                                             IParameterFactory parameterFactory,
                                             IPriceCalculationService priceCalculationService, ISearchService searchService) {
        this.queryService = queryService;
        this.parameterFactory = parameterFactory;
        this.priceCalculationService = priceCalculationService;
        addConstantEntityTransformationFunctions(transformationFunctionService, Brand.class, Brand::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, CountType.class, CountType::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, CountryPhoneCode.class, CountryPhoneCode::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, Currency.class, Currency::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, DeliveryType.class, DeliveryType::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, ItemType.class, ItemType::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, ArticularStatus.class, ArticularStatus::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, MessageStatus.class, MessageStatus::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, MessageType.class, MessageType::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, OptionGroup.class, OptionGroup::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, OrderStatus.class, OrderStatus::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, PaymentMethod.class, PaymentMethod::new);
        addConstantEntityTransformationFunctions(transformationFunctionService, AvailabilityStatus.class, AvailabilityStatus::new);
        transformationFunctionService.addTransformationFunction(NumberConstantPayloadDto.class, Rating.class, mapOneIntegerFieldEntityDtoRatingFunction());

        loadCountryFunctions(transformationFunctionService);

        transformationFunctionService.addTransformationFunction(DeliveryArticularItemQuantity.class, AddressDto.class, mapOrderItemToAddressDtoFunction());

        loadOptionItemFunctions(transformationFunctionService);
        loadItemDataFunctions(transformationFunctionService);
        loadArticularItemFunctions(transformationFunctionService);
        loadDiscountFunctions(transformationFunctionService);
        loadOrderFunctions(transformationFunctionService);
        loadUserDetailsFunctions(transformationFunctionService);
        loadContactInfoFunctions(transformationFunctionService);
        loadTimeDurationOptionFunctions(transformationFunctionService);
        loadZoneOptionFunctions(transformationFunctionService);
        loadAddressFunctions(transformationFunctionService);
        loadCreditCardFunctions(transformationFunctionService);
        loadDeviceFunctions(transformationFunctionService);
        loadCommissionFunctions(transformationFunctionService);
        loadStoreFunctions(transformationFunctionService);
        loadMessageFunctions(transformationFunctionService);
        loadPostFunctions(transformationFunctionService);
        loadReviewFunctions(transformationFunctionService);

    }

    private void loadCountryFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(CountryDto.class, Country.class, mapCountryDtoCountryFunction());
        transformationFunctionService.addTransformationFunction(Country.class, CountryDto.class, mapCountryEntityCountryDtoFunction());
    }

    private void loadDiscountFunctions(ITransformationFunction transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(DiscountDto.class, Discount.class, mapDiscountDtoToDiscountFunction());
        transformationFunctionService.addTransformationFunction(Discount.class, DiscountDto.class, mapDiscountToDiscountDtoFunction());
        transformationFunctionService.addTransformationFunction(ArticularItem.class, DiscountDto.class, mapItemDataOptionListToDiscountDtoFunction());
        transformationFunctionService.addTransformationFunction(ArticularItem.class, DiscountDto.class, "list", mapItemDataOptionListToDiscountDtoListFunction());
    }

    private void loadItemDataFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(ItemDataDto.class, ItemData.class, mapItemDataDtoToItemDataFunction());
        transformationFunctionService.addTransformationFunction(SearchFieldUpdateEntityDto.class, ItemData.class, mapSearchFieldUpdateEntityDtoToItemDataFunction());
        transformationFunctionService.addTransformationFunction(ItemData.class, ResponseItemDataDto.class, mapItemDataToResponseItemDataDtoFunction());
    }

    private void loadArticularItemFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(SearchFieldUpdateCollectionEntityDto.class, ItemData.class, mapSearchFieldUpdateCollectionEntityDtoToItemDataFunction());
        transformationFunctionService.addTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class, mapArticularItemToResponseArticularDto());
    }

    private void loadOptionItemFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(OptionGroupOptionItemSetDto.class, OptionGroup.class, mapOptionItemDtoToOptionGroup());
        transformationFunctionService.addTransformationFunction(OptionGroupOptionItemSetDto.class, OptionGroup.class, "set", mapOptionGroupOptionItemSetDtoListToOptionGroupSet());
        transformationFunctionService.addTransformationFunction(ArticularItem.class, OptionItem.class, mapItemDataOptionToOptionItemFunction());
        transformationFunctionService.addTransformationFunction(OptionGroup.class, OptionGroupOptionItemSetDto.class, mapOptionGroupToOptionItemDto());
        transformationFunctionService.addTransformationFunction(ArticularItem.class, OptionGroupOptionItemSetDto.class, "list", mapArticularItemToOptionItemDtoList());

        transformationFunctionService.addTransformationFunction(SingleOptionItemDto.class, OptionGroup.class, mapSingleOptionItemDtoToOptionGroup());
    }

    private void loadOrderFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(OrderArticularItemQuantityDto.class, DeliveryArticularItemQuantity.class, mapOrderArticularItemQuantityToOrderArticularItem());
    }

    private void loadUserDetailsFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(RegistrationUserDetailsDto.class, UserDetails.class, mapRegistrationUserDetailsDtoToUserDetailsFunction());
        transformationFunctionService.addTransformationFunction(UserDetailsDto.class, UserDetails.class, mapUserDetailsDtoToUserDetailsFunction());
        transformationFunctionService.addTransformationFunction(SearchFieldUpdateEntityDto.class, UserDetails.class, mapSearchFieldUpdateEntityDtoToUserDetailsFunction());
        transformationFunctionService.addTransformationFunction(UserDetails.class, ResponseUserDetailsDto.class, mapUserDetailsToUserResponseUserDetailsDtoFunction());
    }

    private void loadContactInfoFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(ContactInfoDto.class, ContactInfo.class, mapContactInfoDtoToContactInfoFunction());
        transformationFunctionService.addTransformationFunction(ContactInfo.class, ContactInfoDto.class, mapContactInfoToContactInfoDtoFunction());
        transformationFunctionService.addTransformationFunction(UserDetails.class, ContactInfoDto.class, mapUserDetailsToContactInfoDtoFunction());
    }

    private void loadTimeDurationOptionFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(TimeDurationOptionDto.class, TimeDurationOption.class, mapTimeDurationOptionDtoToTimeDurationOptionFunction());
        transformationFunctionService.addTransformationFunction(TimeDurationOption.class, ResponseTimeDurationOptionDto.class, mapTimeDurationOptionToResponseTimeDurationOptionDtoFunction());
    }

    private void loadZoneOptionFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(ZoneOptionDto.class, ZoneOption.class, mapZoneOptionDtoToZoneOptionFunction());
        transformationFunctionService.addTransformationFunction(ZoneOption.class, ZoneOptionDto.class, mapZoneOptionToZoneOptionDtoFunction());
    }

    private void loadAddressFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(UserAddressDto.class, UserAddress.class, mapUserAddressDtoToUserAddressFunction());
        transformationFunctionService.addTransformationFunction(UserAddress.class, ResponseUserAddressDto.class, mapUserAddressToResponseUserAddressDtoFunction());
        transformationFunctionService.addTransformationFunction(UserAddress.class, AddressDto.class, mapUserAddressToAddressDtoDtoFunction());
        transformationFunctionService.addTransformationFunction(AddressDto.class, Address.class, mapAddressDtoToAddressFunction());
        transformationFunctionService.addTransformationFunction(Address.class, AddressDto.class, mapAddressToAddressDtoFunction());
    }

    private void loadCreditCardFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(UserCreditCardDto.class, UserCreditCard.class, mapUserCreditCardDtoToUserCreditCardFunction());
        transformationFunctionService.addTransformationFunction(UserCreditCard.class, ResponseUserCreditCardDto.class, mapUserCreditCardToResponseUserCardDtoFunction());
        transformationFunctionService.addTransformationFunction(CreditCard.class, ResponseCreditCardDto.class, mapCreditCardToResponseCardDtoFunction());
    }

    private void loadDeviceFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(DeviceDto.class, Device.class, mapDeviceDtoToDeviceFunction());
        transformationFunctionService.addTransformationFunction(Device.class, ResponseDeviceDto.class, mapDeviceToResponseDeviceDtoFunction());
    }

    private void loadCommissionFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(SellerCommission.class, ResponseCommissionDto.class, mapSellerCommissionToResponseCommissionDtoFunction());
        transformationFunctionService.addTransformationFunction(CommissionDto.class, SellerCommission.class, mapCommissionDtoToSellerCommissionFunction());
        transformationFunctionService.addTransformationFunction(BuyerCommission.class, ResponseCommissionDto.class, mapBuyerCommissionToResponseCommissionDtoFunction());
        transformationFunctionService.addTransformationFunction(CommissionDto.class, BuyerCommission.class, mapCommissionDtoToBuyerCommissionFunction());
    }

    private void loadStoreFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(StoreDto.class, Store.class, mapStoreDtoToStoreFunction());
        transformationFunctionService.addTransformationFunction(Store.class, ResponseStoreDto.class, mapStoreToResponseStoreDtoFunction());
    }

    private void loadMessageFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(MessageDto.class, Message.class, mapMessageDtoToMessageFunction());
        transformationFunctionService.addTransformationFunction(MessageBox.class, ResponseMessageOverviewDto.class, mapMessageToResponseMessageOverviewDtoFunction());
        transformationFunctionService.addTransformationFunction(Message.class, ResponseMessageOverviewDto.class, mapMessageToResponseMessageOverviewDtoFunction());
        transformationFunctionService.addTransformationFunction(Message.class, ResponseMessagePayloadDto.class, mapMessageToResponseMessagePayloadDtoFunction());

    }

    private void loadPostFunctions(ITransformationFunctionService transformationFunctionService) {

    }

    private void loadReviewFunctions(ITransformationFunctionService transformationFunctionService) {
        transformationFunctionService.addTransformationFunction(ReviewDto.class, Review.class, mapReviewDtoToReviewFunction());
        transformationFunctionService.addTransformationFunction(Review.class, ResponseReviewDto.class, mapReviewToResponseReviewDtoFunction());
    }

    private <T extends AbstractConstantEntity> Function<ConstantPayloadDto, T> mapConstantEntityPayloadDtoToConstantEntityFunction(Supplier<T> entitySupplier) {
        return constantPayloadDto -> {
            T entity = entitySupplier.get();
            entity.setLabel(constantPayloadDto.getLabel());
            entity.setValue(constantPayloadDto.getValue());
            return entity;
        };
    }

    private <T extends AbstractConstantEntity> Function<T, ConstantPayloadDto> mapConstantEntityToConstantEntityPayloadDtoFunction() {
        return abstractConstantEntity -> ConstantPayloadDto.builder()
                .label(abstractConstantEntity.getLabel())
                .value(abstractConstantEntity.getValue())
                .build();
    }

    private <T extends AbstractConstantEntity> void addConstantEntityTransformationFunctions(ITransformationFunctionService transformationFunctionService,
                                                                                             Class<T> entityClass,
                                                                                             Supplier<T> entitySupplier) {
        transformationFunctionService.addTransformationFunction(ConstantPayloadDto.class, entityClass, mapConstantEntityPayloadDtoToConstantEntityFunction(entitySupplier));
        transformationFunctionService.addTransformationFunction(entityClass, ConstantPayloadDto.class, mapConstantEntityToConstantEntityPayloadDtoFunction());
    }

    private Function<NumberConstantPayloadDto, Rating> mapOneIntegerFieldEntityDtoRatingFunction() {
        return constantIntegerEntityPayloadDto -> Rating.builder()
                .value((Integer) constantIntegerEntityPayloadDto.getValue())
                .build();
    }

    private Function<Country, CountryDto> mapCountryEntityCountryDtoFunction() {
        return entity -> CountryDto.builder()
                .label(entity.getLabel())
                .value(entity.getValue())
                .flagImagePath(entity.getFlagImagePath())
                .build();
    }

    private Function<CountryDto, Country> mapCountryDtoCountryFunction() {
        return countryDto -> Country.builder()
                .label(countryDto.getLabel())
                .value(countryDto.getValue())
                .flagImagePath(countryDto.getFlagImagePath())
                .build();
    }

    private BiFunction<Session, AddressDto, Address> mapAddressDtoToAddressFunction() {
        return (session, addressDto) -> Address.builder()
                .country(fetchCountry(session, addressDto.getCountry()))
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
                    addressDto.getCountry(),
                    addressDto.getCity(),
                    addressDto.getStreet(),
                    addressDto.getBuildingNumber(),
                    addressDto.getApartmentNumber());
            return UserAddress.builder()
                    .address(mapAddressDtoToAddressFunction().apply(session, userAddressDto.getAddress()))
                    .isDefault(userAddressDto.isDefault())
                    .userAddressCombination(userAddressCombination)
                    .build();
        };
    }

    private Function<Address, AddressDto> mapAddressToAddressDtoFunction() {
        return address -> AddressDto.builder()
                .country(address.getCountry().getValue())
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

    private Function<UserAddress, ResponseUserAddressDto> mapUserAddressToResponseUserAddressDtoFunction() {
        return userAddress -> ResponseUserAddressDto.builder()
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

    private Function<UserCreditCard, ResponseUserCreditCardDto> mapUserCreditCardToResponseUserCardDtoFunction() {
        return userCreditCard -> {
            CreditCard creditCard = userCreditCard.getCreditCard();
            return ResponseUserCreditCardDto.builder()
                    .creditCard(mapCreditCardToResponseCardDtoFunction().apply(creditCard))
                    .isDefault(userCreditCard.isDefault())
                    .build();
        };
    }

    private Function<CreditCard, ResponseCreditCardDto> mapCreditCardToResponseCardDtoFunction() {
        return creditCard -> ResponseCreditCardDto.builder()
                .cardNumber(creditCard.getCardNumber())
                .monthOfExpire(creditCard.getMonthOfExpire())
                .yearOfExpire(creditCard.getYearOfExpire())
                .isActive(CardUtil.isCardActive(creditCard.getMonthOfExpire(), creditCard.getYearOfExpire()))
                .ownerName(creditCard.getOwnerName())
                .ownerSecondName(creditCard.getOwnerSecondName())
                .build();
    }

    private BiFunction<Session, MessageDto, Message> mapMessageDtoToMessageFunction() {
        return (session, messageDto) -> {
            MessageStatus messageStatus = fetchMessageStatus(session, messageDto.getStatus());
            MessageType messageType = fetchMessageType(session, messageDto.getMessageTemplate().getType());

            return Message.builder()
                    .messageTemplate(MessageTemplate.builder()
                            .sender(messageDto.getMessageTemplate().getSender())
                            .title(messageDto.getMessageTemplate().getTitle())
                            .message(messageDto.getMessageTemplate().getMessage())
                            .messageUniqNumber(getUUID())
                            .dateOfSend(getCurrentTimeMillis())
                            .receivers(messageDto.getMessageTemplate().getReceivers())
                            .sendSystem("APP")
                            .type(messageType)
                            .build())
                    .status(messageStatus)
                    .build();
        };
    }

    private Function<Message, ResponseMessageOverviewDto> mapMessageToResponseMessageOverviewDtoFunction() {
        return message -> {
            MessageTemplate messageTemplate = message.getMessageTemplate();
            return ResponseMessageOverviewDto.builder()
                    .sender(messageTemplate.getSender())
                    .title(messageTemplate.getTitle())
                    .dateOfSend(messageTemplate.getDateOfSend())
                    .type(messageTemplate.getType().getValue())
                    .status(message.getStatus().getValue())
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
            CountryPhoneCode countryPhoneCode = fetchCountryPhoneCode(session, contactPhoneDto.getCountryPhoneCode());
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

    private Function<UserDetails, ContactPhoneDto> mapUserDetailsToContactPhoneDtoFunction() {
        return userDetails -> {
            ContactPhone contactPhone = userDetails.getContactInfo().getContactPhone();
            return mapContactPhoneToContactPhoneDtoFunction().apply(contactPhone);
        };
    }

    private BiFunction<Session, ContactInfoDto, ContactInfo> mapContactInfoDtoToContactInfoFunction() {
        return (session, contactInfoDto) -> ContactInfo.builder()
                .email(contactInfoDto.getEmail())
                .firstName(contactInfoDto.getFirstName())
                .lastName(contactInfoDto.getLastName())
                .contactPhone(mapContactPhoneDtoToContactPhoneFunction().apply(session, contactInfoDto.getContactPhone()))
                .birthdayDate(contactInfoDto.getBirthdayDate())
                .build();
    }

    private BiFunction<Session, ContactInfo, ContactInfoDto> mapContactInfoToContactInfoDtoFunction() {
        return (session, contactInfo) -> ContactInfoDto.builder()
                .email(contactInfo.getEmail())
                .firstName(contactInfo.getFirstName())
                .lastName(contactInfo.getLastName())
                .contactPhone(mapContactPhoneToContactPhoneDtoFunction().apply(contactInfo.getContactPhone()))
                .birthdayDate(contactInfo.getBirthdayDate())
                .build();
    }

    Function<UserDetails, ContactInfoDto> mapUserDetailsToContactInfoDtoFunction() {
        return userDetails -> {
            ContactInfo contactInfo = userDetails.getContactInfo();
            ContactPhone contactPhone = contactInfo.getContactPhone();
            return ContactInfoDto.builder()
                    .firstName(contactInfo.getFirstName())
                    .lastName(contactInfo.getLastName())
                    .email(contactInfo.getEmail())
                    .birthdayDate(contactInfo.getBirthdayDate())
                    .contactPhone(ContactPhoneDto.builder()
                            .phoneNumber(contactPhone.getPhoneNumber())
                            .countryPhoneCode(contactPhone.getCountryPhoneCode().getValue())
                            .build())
                    .build();
        };
    }

    private Function<RegistrationUserDetailsDto, UserDetails> mapRegistrationUserDetailsDtoToUserDetailsFunction() {
        return registrationUserDetailsDto -> {
            ContactInfo contactInfo = ContactInfo.builder()
                    .email(registrationUserDetailsDto.getEmail())
                    .build();
            return UserDetails.builder()
                    .username(getEmailPrefix(registrationUserDetailsDto.getEmail()))
                    .userId(getUUID())
                    .dateOfCreate(getCurrentTimeMillis())
                    .contactInfo(contactInfo)
                    .build();
        };
    }

    private BiFunction<Session, SearchFieldUpdateEntityDto<UserDetailsDto>, UserDetails> mapSearchFieldUpdateEntityDtoToUserDetailsFunction() {
        return (session, userDetailsDtoSearchFieldUpdateEntityDto) -> mapUserDetailsDtoToUserDetailsFunction()
                .apply(session, userDetailsDtoSearchFieldUpdateEntityDto.getUpdateDto());
    }

    private BiFunction<Session, UserDetailsDto, UserDetails> mapUserDetailsDtoToUserDetailsFunction() {
        return (session, userDetailsDto) -> {
            ContactInfo contactInfo = mapContactInfoDtoToContactInfoFunction().apply(session, userDetailsDto.getContactInfo());
            Address address = mapAddressDtoToAddressFunction().apply(session, userDetailsDto.getAddress());
            UserAddress userAddress = UserAddress.builder()
                    .address(address)
                    .isDefault(false)
                    .build();
            UserCreditCard userCreditCard = mapCreditCardDtoUserCreditCardDtoFunction().apply(userDetailsDto.getCreditCard());
            return UserDetails.builder()
                    .username(getEmailPrefix(userDetailsDto.getContactInfo().getEmail()))
                    .userId(getUUID())
                    .dateOfCreate(getCurrentTimeMillis())
                    .contactInfo(contactInfo)
                    .userAddresses(Set.of(userAddress))
                    .userCreditCards(Set.of(userCreditCard))
                    .build();
        };
    }

    private Function<UserDetails, ResponseUserDetailsDto> mapUserDetailsToUserResponseUserDetailsDtoFunction() {
        return userDetails -> {
            ContactInfoDto contactInfoDto = mapUserDetailsToContactInfoDtoFunction().apply(userDetails);
            List<UserAddressDto> addressDtoList = userDetails.getUserAddresses().stream()
                    .map(mapUserAddressToUserAddressDtoFunction())
                    .toList();
            List<ResponseUserCreditCardDto> responseCreditCardDtoList = userDetails.getUserCreditCards().stream()
                    .map(ucc -> mapUserCreditCardToResponseUserCardDtoFunction().apply(ucc))
                    .toList();
            List<ResponseDeviceDto> responseDeviceDtoList = userDetails.getDevices().stream()
                    .map(d -> mapDeviceToResponseDeviceDtoFunction().apply(d))
                    .toList();
            return ResponseUserDetailsDto.builder()
                    .contactInfo(contactInfoDto)
                    .creditCards(responseCreditCardDtoList)
                    .addresses(addressDtoList)
                    .devices(responseDeviceDtoList)
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

    private Function<Device, ResponseDeviceDto> mapDeviceToResponseDeviceDtoFunction() {
        return device -> ResponseDeviceDto.builder()
                .ipAddress(device.getIpAddress())
                .loginTime(device.getLoginTime().atZone(ZoneId.of("UTC")))
                .screenHeight(device.getScreenHeight())
                .screenWidth(device.getScreenWidth())
                .userAgent(device.getUserAgent())
                .timezone(device.getTimezone())
                .language(device.getLanguage())
                .platform(device.getPlatform())
                .build();
    }

    private Function<ReviewDto, Review> mapReviewDtoToReviewFunction() {
        return reviewDto -> Review.builder()
                .title(reviewDto.getTitle())
                .message(reviewDto.getMessage())
                .dateOfCreate(getCurrentTimeMillis())
                .build();
    }

    private Function<Review, ResponseReviewDto> mapReviewToResponseReviewDtoFunction() {
        return review -> ResponseReviewDto.builder()
                .title(review.getTitle())
                .message(review.getMessage())
                .ratingValue(review.getRating().getValue())
                .dateOfCreate(new Date(getCurrentTimeMillis()))
                .build();
    }

    private BiFunction<Session, PriceDto, Price> mapPriceDtoToPriceFunction() {
        return (session, priceDto) -> {
            Currency currency = fetchCurrency(session, priceDto.getCurrency());
            return Price.builder()
                    .amount(priceDto.getAmount())
                    .currency(currency)
                    .build();
        };
    }

    private Function<CustomerOrder, PriceDto> mapOrderToFullPriceDtoFunction() {
        return customerOrder -> {
            Price fullPrice = customerOrder.getPayment().getCommissionPrice();
            return PriceDto.builder()
                    .amount(fullPrice.getAmount())
                    .currency(fullPrice.getCurrency().getValue())
                    .build();
        };
    }

    private Function<CustomerOrder, PriceDto> mapOrderToTotalPriceDtoFunction() {
        return customerOrder -> {
            Price fullPrice = customerOrder.getPayment().getTotalPrice();
            return PriceDto.builder()
                    .amount(fullPrice.getAmount())
                    .currency(fullPrice.getCurrency().getValue())
                    .build();
        };
    }

    private Function<ArticularItem, PriceDto> mapItemDataOptionToFullPriceDtoFunction() {
        return itemDataOption -> {
            Price fullPrice = itemDataOption.getFullPrice();
            return PriceDto.builder()
                    .amount(fullPrice.getAmount())
                    .currency(fullPrice.getCurrency().getValue())
                    .build();
        };
    }

    private Function<ArticularItem, PriceDto> mapItemDataOptionToTotalPriceDtoFunction() {
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

    private Function<ArticularItem, Price> mapItemDataOptionToFullPriceFunction() {
        return ArticularItem::getFullPrice;
    }

    private Function<ArticularItem, Price> mapItemDataOptionToTotalPriceFunction() {
        return ArticularItem::getTotalPrice;
    }

    Function<ArticularItem, Set<OptionItem>> mapItemDataOptionToOptionItemFunction() {
        return ArticularItem::getOptionItems;
    }

    BiFunction<Session, OptionGroupOptionItemSetDto, OptionGroup> mapOptionItemDtoToOptionGroup() {
        return (session, optionItemDto) -> {
            Optional<OptionGroup> optionalResult = fetchOptionGroup(session, "optionGroup.withOptionItems", optionItemDto.getOptionGroup().getValue());
            return optionalResult.map(existingOG -> {
                optionItemDto.getOptionItems().stream()
                        .filter(oi -> existingOG.getOptionItems().stream()
                                .noneMatch(v -> v.getValue().equals(oi.getValue())))
                        .forEach(newOption -> existingOG.getOptionItems().add(
                                OptionItem.builder()
                                        .label(newOption.getLabel())
                                        .value(newOption.getValue())
                                        .build()
                        ));

                return existingOG;
            }).orElseGet(() -> {
                OptionGroup newOG = OptionGroup.builder()
                        .label(optionItemDto.getOptionGroup().getLabel())
                        .value(optionItemDto.getOptionGroup().getValue())
                        .build();

                optionItemDto.getOptionItems().stream()
                        .map(item -> OptionItem.builder()
                                .label(item.getLabel())
                                .value(item.getValue())
                                .build())
                        .forEach(newOG::addOptionItem);

                return newOG;
            });
        };
    }

    Function<OptionGroup, OptionGroupOptionItemSetDto> mapOptionGroupToOptionItemDto() {
        return optionGroup -> {
            OptionGroupDto optionGroupDto = OptionGroupDto.builder()
                    .label(optionGroup.getLabel())
                    .value(optionGroup.getValue())
                    .build();

            Set<OptionItemDto> constantPayloadDtoList = optionGroup.getOptionItems().stream()
                    .map(item -> OptionItemDto.builder()
                            .label(item.getLabel())
                            .value(item.getValue())
                            .build())
                    .collect(Collectors.toSet());


            return OptionGroupOptionItemSetDto.builder()
                    .optionGroup(optionGroupDto)
                    .optionItems(constantPayloadDtoList)
                    .build();
        };
    }

    private Function<ArticularItem, List<OptionGroupOptionItemSetDto>> mapArticularItemToOptionItemDtoList() {
        return articularItem -> articularItem.getOptionItems().stream()
                .map(optionItem -> OptionGroupOptionItemSetDto.builder()
                        .optionGroup(OptionGroupDto.builder()
                                .label(optionItem.getOptionGroup().getLabel())
                                .value(optionItem.getOptionGroup().getValue())
                                .build())
                        .optionItems(Set.of(OptionItemDto.builder()
                                .label(optionItem.getLabel())
                                .value(optionItem.getValue())
                                .build()))
                        .build())
                .toList();
    }

    private Function<SingleOptionItemDto, OptionGroup> mapSingleOptionItemDtoToOptionGroup() {
        return singleOptionItemDto -> {
            OptionGroup og = OptionGroup.builder()
                    .label(singleOptionItemDto.getOptionGroup().getLabel())
                    .value(singleOptionItemDto.getOptionGroup().getValue())
                    .build();
            og.addOptionItem(OptionItem.builder()
                    .value(singleOptionItemDto.getOptionItem().getValue())
                    .label(singleOptionItemDto.getOptionItem().getLabel())
                    .build());
            return og;
        };
    }

    private Set<OptionGroupOptionItemSetDto> getOptionGroupOptionItemSetDtoSet(Set<ArticularItemDto> articularItemDtoSet) {
        return articularItemDtoSet.stream()
                .flatMap(articularItem -> articularItem.getOptions().stream())
                .collect(Collectors.groupingBy(
                        SingleOptionItemDto::getOptionGroup,
                        Collectors.mapping(
                                SingleOptionItemDto::getOptionItem,
                                Collectors.toSet())
                ))
                .entrySet().stream()
                .map(entry ->
                        OptionGroupOptionItemSetDto.builder()
                            .optionGroup(OptionGroupDto.builder()
                                    .label(entry.getKey().getLabel())
                                    .value(entry.getKey().getValue())
                                    .build())
                            .optionItems(entry.getValue().stream()
                                    .map(optionItem -> new OptionItemDto(optionItem.getLabel(), optionItem.getValue()))
                                    .collect(Collectors.toSet()))
                            .build()
                )
                .collect(Collectors.toSet());
    }

    private BiFunction<Session, ItemDataDto, ItemData> mapItemDataDtoToItemDataFunction() {
        return (session, itemDataDto) -> {
            Category category = fetchOptionalCategory(session, itemDataDto.getCategory().getValue())
                    .orElse(Category.builder()
                            .label(itemDataDto.getCategory().getLabel())
                            .value(itemDataDto.getCategory().getValue())
                            .build());
            Brand brand = fetchOptionalBrand(session, itemDataDto.getBrand().getValue())
                    .orElse(Brand.builder()
                            .label(itemDataDto.getBrand().getLabel())
                            .value(itemDataDto.getBrand().getValue())
                            .build());
            ItemType itemType = fetchOptionalItemType(session, itemDataDto.getItemType().getValue())
                    .orElse(ItemType.builder()
                            .label(itemDataDto.getItemType().getLabel())
                            .value(itemDataDto.getItemType().getValue())
                            .build());

            Set<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDtoSet = getOptionGroupOptionItemSetDtoSet(itemDataDto.getArticularItemSet());
            Set<OptionGroup> optionGroups = mapOptionGroupOptionItemSetDtoListToOptionGroupSet().apply(session, optionGroupOptionItemSetDtoSet);
            Set<OptionItem> optionItems = optionGroups.stream()
                    .flatMap(og -> og.getOptionItems().stream())
                    .collect(Collectors.toSet());

            Set<ArticularItem> articularItemSet = mapArticularItemSet(
                    session,
                    itemDataDto.getArticularItemSet(),
                    optionItems,
                    Collections.emptyMap());

            return ItemData.builder()
                    .description(itemDataDto.getDescription())
                    .category(category)
                    .brand(brand)
                    .itemType(itemType)
                    .articularItemSet(articularItemSet)
                    .build();
        };
    }

    private BiFunction<Session, SearchFieldUpdateCollectionEntityDto<ArticularItemDto>, ItemData> mapSearchFieldUpdateCollectionEntityDtoToItemDataFunction() {
        return ((session, searchFieldUpdateCollectionEntityDto) -> {
            String itemId = searchFieldUpdateCollectionEntityDto.getSearchField();
            List<ArticularItemDto> articularItemDtoList = searchFieldUpdateCollectionEntityDto.getUpdateDtoSet();
            Set<ArticularItemDto> articularItemDtoSet = new HashSet<>(articularItemDtoList);
            ItemData existingItemData = queryService.getGraphEntity(
                    session,
                    ItemData.class,
                    "itemData.full",
                    new Parameter("itemId", itemId));
            Map<String, ArticularItem> existingArticularItemMap = existingItemData.getArticularItemSet().stream()
                    .collect(Collectors.toMap(
                            ArticularItem::getArticularId,
                            articularItem -> articularItem
                    ));
            Set<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDtoSet = getOptionGroupOptionItemSetDtoSet(articularItemDtoSet);
            Set<OptionGroup> optionGroups = mapOptionGroupOptionItemSetDtoListToOptionGroupSet().apply(session, optionGroupOptionItemSetDtoSet);
            Set<OptionItem> optionItems = optionGroups.stream()
                    .flatMap(og -> og.getOptionItems().stream())
                    .collect(Collectors.toSet());

            Set<ArticularItem> articularItemSet = mapArticularItemSet(
                    session,
                    articularItemDtoSet,
                    optionItems,
                    existingArticularItemMap);
            List<ArticularItem> nonUpdatedArticularList = existingItemData.getArticularItemSet().stream()
                    .filter(existingItem -> articularItemSet.stream()
                            .noneMatch(newItem -> newItem.getArticularId().equals(existingItem.getArticularId())))
                    .toList();
            Set<ArticularItem> mergedArticularItemSet = Stream.concat(articularItemSet.stream(), nonUpdatedArticularList.stream())
                            .collect(Collectors.toSet());

            return ItemData.builder()
                    .id(existingItemData.getId())
                    .itemId(existingItemData.getItemId())
                    .description(existingItemData.getDescription())
                    .category(existingItemData.getCategory())
                    .brand(existingItemData.getBrand())
                    .itemType(existingItemData.getItemType())
                    .articularItemSet(mergedArticularItemSet)
                    .build();
        });
    }

    private BiFunction<Session, SearchFieldUpdateEntityDto<ItemDataDto>, ItemData> mapSearchFieldUpdateEntityDtoToItemDataFunction() {
        return (session, itemDataSearchFieldUpdateDto) -> {
            String itemId = itemDataSearchFieldUpdateDto.getSearchField();
            ItemDataDto itemDataDto = itemDataSearchFieldUpdateDto.getUpdateDto();
            ItemData existingItemData = queryService.getGraphEntity(
                    session,
                    ItemData.class,
                    "itemData.full",
                    new Parameter("itemId", itemId));
            Map<String, ArticularItem> existingArticularItemMap = existingItemData.getArticularItemSet().stream()
                    .collect(Collectors.toMap(
                            ArticularItem::getArticularId,
                            articularItem -> articularItem
                    ));

            Category category = fetchOptionalCategory(session, itemDataDto.getCategory().getValue())
                    .orElse(Category.builder()
                            .label(itemDataDto.getCategory().getLabel())
                            .value(itemDataDto.getCategory().getValue())
                            .build());
            Brand brand = fetchOptionalBrand(session, itemDataDto.getBrand().getValue())
                    .orElse(Brand.builder()
                            .label(itemDataDto.getBrand().getLabel())
                            .value(itemDataDto.getBrand().getValue())
                            .build());
            ItemType itemType = fetchOptionalItemType(session, itemDataDto.getItemType().getValue())
                    .orElse(ItemType.builder()
                            .label(itemDataDto.getItemType().getLabel())
                            .value(itemDataDto.getItemType().getValue())
                            .build());

            Set<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDtoSet = getOptionGroupOptionItemSetDtoSet(itemDataDto.getArticularItemSet());
            Set<OptionGroup> optionGroups = mapOptionGroupOptionItemSetDtoListToOptionGroupSet().apply(session, optionGroupOptionItemSetDtoSet);
            Set<OptionItem> optionItems = optionGroups.stream()
                    .flatMap(og -> og.getOptionItems().stream())
                    .collect(Collectors.toSet());

            Set<ArticularItem> articularItemSet = mapArticularItemSet(
                    session,
                    itemDataDto.getArticularItemSet(),
                    optionItems,
                    existingArticularItemMap);

            return ItemData.builder()
                    .id(existingItemData.getId())
                    .itemId(existingItemData.getItemId())
                    .description(itemDataDto.getDescription())
                    .category(category)
                    .brand(brand)
                    .itemType(itemType)
                    .articularItemSet(articularItemSet)
                    .build();
        };
    }

    private Set<ArticularItem> mapArticularItemSet(Session session,
                                                   Set<ArticularItemDto> articularItemDtoSet,
                                                   Set<OptionItem> optionItems,
                                                   Map<String, ArticularItem> articularItemMap) {
        return articularItemDtoSet.stream()
                .map(articularItemDto -> {
                    Discount discount = fetchOptionDiscount(session, articularItemDto.getDiscount().getCharSequenceCode())
                            .orElse(mapInitDiscountDtoToDiscount().apply(session, articularItemDto.getDiscount()));

                    ArticularStatus articularStatus = fetchArticularStatus(session, articularItemDto.getStatus());
                    ArticularItem existingArticularItem = articularItemMap.get(articularItemDto.getArticularId());
                    long articularId = Optional.ofNullable(existingArticularItem).map(ArticularItem::getId).orElse(0L);
                    long fullPriceId = Optional.ofNullable(existingArticularItem).map(item -> item.getFullPrice().getId()).orElse(0L);
                    long totalPriceId = Optional.ofNullable(existingArticularItem).map(item -> item.getTotalPrice().getId()).orElse(0L);
                    long discountId = Optional.ofNullable(existingArticularItem).map(item -> item.getDiscount().getId()).orElse(0L);
                    ArticularItem articularItem = ArticularItem.builder()
                            .id(articularId)
                            .articularId(articularItemDto.getArticularId() != null ? articularItemDto.getArticularId() : getUUID())
                            .dateOfCreate(getCurrentTimeMillis())
                            .productName(articularItemDto.getProductName())
                            .status(articularStatus)
                            .fullPrice(mapPriceDtoToPriceFunction().apply(session, articularItemDto.getFullPrice()))
                            .totalPrice(mapPriceDtoToPriceFunction().apply(session, articularItemDto.getTotalPrice()))
                            .discount(discount)
                            .build();

                    articularItemDto.getOptions().stream()
                            .flatMap(soi -> optionItems.stream()
                                    .filter(optionItem -> optionItem.getValue().equals(soi.getOptionItem().getValue()))
                            )
                            .forEach(optionItem -> {
                                optionItem.getArticularItems().stream()
                                        .filter(ai -> ai.getArticularId().equals(articularItem.getArticularId()))
                                        .collect(Collectors.toSet())
                                        .forEach(optionItem::removeArticularItem);

                                optionItem.addArticularItem(articularItem);
                            });

                    articularItem.getFullPrice().setId(fullPriceId);
                    articularItem.getTotalPrice().setId(totalPriceId);
                    articularItem.getDiscount().setId(discountId);

                    return articularItem;
                })
                .collect(Collectors.toSet());
    }

    private BiFunction<Session, OrderArticularItemQuantityDto, DeliveryArticularItemQuantity> mapOrderArticularItemQuantityToOrderArticularItem() {
        return (session, orderArticularItemQuantityDto) -> {
            Optional<UserDetails> userDetailsOptional = Optional.empty();
            if (orderArticularItemQuantityDto.getUser().getUserId() != null) {
                userDetailsOptional = queryService.getNamedQueryOptionalEntity(
                        session,
                        UserDetails.class,
                        "UserDetails.findByUserId",
                        parameterFactory.createStringParameter(USER_ID, orderArticularItemQuantityDto.getUser().getUserId()));
            }
            List<ArticularItemQuantityPrice> articularItemQuantityPriceList = mapArticularItemQuantityDtoToArticularItemQuantity()
                    .apply(session, orderArticularItemQuantityDto.getItemDataOptionQuantities());
            Delivery delivery = mapDeliveryDtoToDelivery().apply(session, orderArticularItemQuantityDto.getDelivery());
            Payment payment = mapPaymentDtoToPayment().apply(session, orderArticularItemQuantityDto.getPayment());
            Optional<SellerCommission> commissionOptional = fetchCommission(session);
//            PaymentPriceDto paymentPriceDto = PaymentPriceDto.builder()
//                    .articularItemQuantityPriceList(articularItemQuantityPriceList)
//                    .delivery(delivery)
//                    .discount(payment.getDiscount())
//                    .sellerCommission(commissionOptional.get())
//                    .build();

            return DeliveryArticularItemQuantity.builder()
//                    .userDetails(userDetailsOptional.orElse(null))
//                    .dateOfCreate(getCurrentTimeMillis())
//                    .orderId(getUUID())
//                    .articularItemQuantityPriceList(articularItemQuantityPriceList)
                    .delivery(delivery)
//                    .beneficiaries(mapBeneficiariesDtoToBeneficiaries().apply(session, orderArticularItemQuantityDto.getBeneficiaries()))
//                    .payment(mapPaymentDtoToPayment().apply(session, orderArticularItemQuantityDto.getPayment()))
//                    .orderStatus(fetchOrderStatus(session, "CREATED"))
//                    .note(orderArticularItemQuantityDto.getNote())
                    .build();
        };
    }

    private BiFunction<Session, DeliveryDto, Delivery> mapDeliveryDtoToDelivery() {
        return (session, deliveryDto) -> {
            DeliveryType deliveryType = fetchDeliveryType(session, deliveryDto.getDeliveryType());
            Address address = mapAddressDtoToAddressFunction().apply(session, deliveryDto.getDeliveryAddress());
            TimeDurationOption timeDurationOption = fetchTimeDurationOption(session, deliveryDto.getTimeDurationOption());
            return Delivery.builder()
                    .deliveryType(deliveryType)
                    .address(address)
                    .timeDurationOption(timeDurationOption)
                    .build();
        };
    }

    private BiFunction<Session, List<ArticularItemQuantityDto>, List<ArticularItemQuantityPrice>> mapArticularItemQuantityDtoToArticularItemQuantity() {
        return (session, articularItemQuantityDtos) -> articularItemQuantityDtos.stream()
                .map(articularItemQuantityDto -> {
                    ArticularItem articularItem = fetchArticularItem(session, articularItemQuantityDto.getArticularId());
                    ArticularItemQuantity articularItemQuantity = null;
                    return ArticularItemQuantityPrice.builder()
                            .articularItemQuantity(articularItemQuantity)
                            .totalPrice(articularItem.getTotalPrice())
                            .build();
                })
                .toList();
    }

    private BiFunction<Session, PaymentDto, Payment> mapPaymentDtoToPayment() {
        return (session, paymentDto) -> {


            return Payment.builder()
                    .paymentId(getUUID())
                    .paymentMethod(fetchPaymentMethod(session, paymentDto.getPaymentMethod()))
                    .paymentStatus(fetchPaymentStatus(session, "Done"))
                    .creditCard(paymentDto.getCreditCard() != null
                            ? mapCreditCardDtoToCreditCardFunction().apply(paymentDto.getCreditCard())
                            : null)
                    .discount(paymentDto.getDiscount() != null
                            ? mapDiscountDtoToDiscountFunction().apply(session, paymentDto.getDiscount())
                            : null)
//                    .commissionPrice(mapPriceDtoToPriceFunction().apply(session, paymentDto.getFullPrice()))
//                    .totalPrice(mapPriceDtoToPriceFunction().apply(session, paymentDto.getTotalPrice()))
                    .build();
        };
    }

    private BiFunction<Session, InitDiscountDto, Discount> mapInitDiscountDtoToDiscount() {
        return (session, initDiscountDto) -> {
            Currency currency = fetchCurrency(session, initDiscountDto.getCurrency());
            return Discount.builder()
                    .currency(currency)
                    .amount(initDiscountDto.getAmount())
                    .charSequenceCode(initDiscountDto.getCharSequenceCode())
                    .isActive(initDiscountDto.getIsActive())
                    .isPercent(currency == null)
                    .build();
        };
    }

    private Function<Discount, InitDiscountDto> mapDiscountToInitDiscountDto() {
        return discount -> InitDiscountDto.builder()
                .currency(discount.getCurrency().getValue())
                .amount(discount.getAmount())
                .charSequenceCode(discount.getCharSequenceCode())
                .isActive(discount.isActive())
                .isPercent(discount.isPercent())
                .build();
    }

    private BiFunction<Session, Set<OptionGroupOptionItemSetDto>, Set<OptionGroup>> mapOptionGroupOptionItemSetDtoListToOptionGroupSet() {
        return (session, optionItemDtoList) -> optionItemDtoList.stream()
                .map(optionItemDto -> {
                    OptionGroup existingOG = fetchOptionGroup(session, "optionGroup.withOptionItemsAndArticularItems", optionItemDto.getOptionGroup().getValue())
                            .orElseGet(() -> OptionGroup.builder()
                                    .label(optionItemDto.getOptionGroup().getLabel())
                                    .value(optionItemDto.getOptionGroup().getValue())
                                    .build());

                    optionItemDto.getOptionItems().forEach(dtoOptionItem -> {
                        if (existingOG.getOptionItems().stream().noneMatch(oi -> oi.getValue().equals(dtoOptionItem.getValue()))) {
                            existingOG.addOptionItem(OptionItem.builder()
                                    .label(dtoOptionItem.getLabel())
                                    .value(dtoOptionItem.getValue())
                                    .build());
                        }
                    });

                    return existingOG;
                })
                .collect(Collectors.toSet());
    }

    private BiFunction<Session, DiscountDto, Discount> mapDiscountDtoToDiscountFunction() {
        return (session, discountDto) -> {
            Currency currency = fetchCurrency(session, discountDto.getCurrency());
            return Discount.builder()
                    .currency(currency)
                    .amount(discountDto.getAmount())
                    .charSequenceCode(discountDto.getCharSequenceCode())
                    .isActive(discountDto.getIsActive())
                    .isPercent(currency == null)
                    .build();
        };
    }

    private Function<Discount, DiscountDto> mapDiscountToDiscountDtoFunction() {
        return discount -> DiscountDto.builder()
                .currency(discount.getCurrency().getValue())
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
                    .currency(discount.getCurrency() != null ? discount.getCurrency().getValue() : null)
                    .articularIdSet(
                            itemDataOptionList.stream()
                                    .map(ArticularItem::getArticularId)
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
                            .map(ArticularItem::getArticularId)
                            .collect(Collectors.toSet());

                    return DiscountDto.builder()
                            .charSequenceCode(discount.getCharSequenceCode())
                            .amount(discount.getAmount())
                            .isActive(discount.isActive())
                            .currency(discount.getCurrency() != null ? discount.getCurrency().getValue() : null)
                            .articularIdSet(articularIdSet)
                            .build();
                })
                .collect(Collectors.toList());
    }


    private Function<ItemData, ResponseItemDataDto> mapItemDataToResponseItemDataDtoFunction() {
        return itemData -> ResponseItemDataDto.builder()
                .itemId(itemData.getItemId())
                .description(itemData.getDescription())
                .brand(BrandDto.builder()
                        .label(itemData.getBrand().getLabel())
                        .value(itemData.getBrand().getValue())
                        .build())
                .itemType(ItemTypeDto.builder()
                        .label(itemData.getItemType().getLabel())
                        .value(itemData.getItemType().getValue())
                        .build())
                .category(CategoryValueDto.builder()
                        .label(itemData.getCategory().getLabel())
                        .value(itemData.getCategory().getValue())
                        .build())
                .articularItems(itemData.getArticularItemSet().stream()
                        .map(mapArticularItemToResponseArticularDto())
                        .collect(Collectors.toSet()))
                .build();
    }

    private Function<ArticularItem, ResponseArticularItemDto> mapArticularItemToResponseArticularDto() {
        return articularItem -> ResponseArticularItemDto.builder()
                .articularId(articularItem.getArticularId())
                .productName(articularItem.getProductName())
                .fullPrice(mapPriceToPriceDtoFunction().apply(articularItem.getFullPrice()))
                .totalPrice(mapPriceToPriceDtoFunction().apply(articularItem.getTotalPrice()))
                .status(mapArticularStatusToConstantPayloadDtoFunction().apply(articularItem.getStatus()))
                .discount(mapDiscountToInitDiscountDto().apply(articularItem.getDiscount()))
                .options(mapOptionItemSetToSingleOptionItemDtoSetFunction().apply(articularItem.getOptionItems()))
                .build();
    }

    private Function<Set<OptionItem>, Set<OptionGroupOptionItemSetDto>> mapOptionItemSetToSingleOptionItemDtoSetFunction() {
        return optionItemSet -> optionItemSet.stream()
                .collect(Collectors.groupingBy(
                        OptionItem::getOptionGroup,
                        Collectors.mapping(
                                optionItem -> optionItem,
                                Collectors.toSet())
                ))
                .entrySet().stream()
                .map(entry ->
                        OptionGroupOptionItemSetDto.builder()
                                .optionGroup(OptionGroupDto.builder()
                                        .label(entry.getKey().getLabel())
                                        .value(entry.getKey().getValue())
                                        .build())
                                .optionItems(entry.getValue().stream()
                                        .map(optionItem -> new OptionItemDto(optionItem.getLabel(), optionItem.getValue()))
                                        .collect(Collectors.toSet()))
                                .build()
                )
                .collect(Collectors.toSet());
    }

    private Function<ArticularStatus, ConstantPayloadDto> mapArticularStatusToConstantPayloadDtoFunction() {
        return articularStatus -> ConstantPayloadDto.builder()
                .label(articularStatus.getLabel())
                .value(articularStatus.getValue())
                .build();
    }

    private BiFunction<Session, DeliveryDto, Delivery> mapDeliveryDtoToDeliveryFunction() {
        return (session, deliveryDto) -> {
            Address address = mapAddressDtoToAddressFunction().apply(session, deliveryDto.getDeliveryAddress());
            DeliveryType deliveryType = fetchDeliveryType(session, deliveryDto.getDeliveryType());

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

    private BiFunction<Session, TimeDurationOptionDto, TimeDurationOption> mapTimeDurationOptionDtoToTimeDurationOptionFunction() {
        return (session, timeDurationOptionDto) -> {
            Price price = mapPriceDtoToPriceFunction().apply(session, timeDurationOptionDto.getPrice());
            return TimeDurationOption.builder()
                    .startTime(timeDurationOptionDto.getStartTime())
                    .endTime(timeDurationOptionDto.getEndTime())
                    .duration(timeDurationOptionDto.getDuration())
                    .label(timeDurationOptionDto.getLabel())
                    .value(timeDurationOptionDto.getValue())
                    .price(price)
                    .build();
        };
    }

    private Function<TimeDurationOption, ResponseTimeDurationOptionDto> mapTimeDurationOptionToResponseTimeDurationOptionDtoFunction() {
        return timeDurationOption -> {
            PriceDto price = mapPriceToPriceDtoFunction().apply(timeDurationOption.getPrice());
            LocalTime startTime = timeDurationOption.getStartTime();
            LocalTime endTime = timeDurationOption.getEndTime();
            return ResponseTimeDurationOptionDto.builder()
                    .startTime(startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                    .endTime(endTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                    .duration(timeDurationOption.getDuration())
                    .label(timeDurationOption.getLabel())
                    .value(timeDurationOption.getValue())
                    .price(price)
                    .build();
        };
    }

    private BiFunction<Session, ZoneOptionDto, ZoneOption> mapZoneOptionDtoToZoneOptionFunction() {
        return (session, zoneOptionDto) -> {
            Price price = mapPriceDtoToPriceFunction().apply(session, zoneOptionDto.getPrice());
            return ZoneOption.builder()
                    .zoneName(zoneOptionDto.getZoneName())
                    .city(zoneOptionDto.getCity())
                    .label(zoneOptionDto.getLabel())
                    .value(zoneOptionDto.getValue())
                    .price(price)
                    .build();
        };
    }

    private Function<ZoneOption, ZoneOptionDto> mapZoneOptionToZoneOptionDtoFunction() {
        return zoneOption -> {
            PriceDto price = mapPriceToPriceDtoFunction().apply(zoneOption.getPrice());
            return ZoneOptionDto.builder()
                    .city(zoneOption.getCity())
                    .zoneName(zoneOption.getZoneName())
                    .label(zoneOption.getLabel())
                    .value(zoneOption.getValue())
                    .price(price)
                    .build();
        };
    }

    private Function<SellerCommission, ResponseCommissionDto> mapSellerCommissionToResponseCommissionDtoFunction() {
        return sellerCommission -> ResponseCommissionDto.builder()
                .amount(sellerCommission.getAmount())
                .currency(sellerCommission.getCurrency() != null ? sellerCommission.getCurrency().getValue() : null)
                .feeType(sellerCommission.getFeeType().name())
                .effectiveDate(sellerCommission.getEffectiveDate().atZone(ZoneId.systemDefault()))
                .build();
    }

    private BiFunction<Session, CommissionDto, SellerCommission> mapCommissionDtoToSellerCommissionFunction() {
        return (session, commissionDto) -> SellerCommission.builder()
                .amount(commissionDto.getAmount())
                .currency(commissionDto.getCurrency() != null ? fetchCurrency(session, commissionDto.getCurrency()) : null)
                .feeType(FeeType.valueOf(commissionDto.getFeeType()))
                .effectiveDate(LocalDateTime.now())
                .build();
    }

    private Function<BuyerCommission, ResponseCommissionDto> mapBuyerCommissionToResponseCommissionDtoFunction() {
        return buyerCommission -> ResponseCommissionDto.builder()
                .amount(buyerCommission.getAmount())
                .currency(buyerCommission.getCurrency() != null ? buyerCommission.getCurrency().getValue() : null)
                .feeType(buyerCommission.getFeeType().name())
                .effectiveDate(buyerCommission.getEffectiveDate().atZone(ZoneId.systemDefault()))
                .build();
    }

    private BiFunction<Session, CommissionDto, BuyerCommission> mapCommissionDtoToBuyerCommissionFunction() {
        return (session, commissionDto) -> BuyerCommission.builder()
                .amount(commissionDto.getAmount())
                .currency(commissionDto.getCurrency() != null ? fetchCurrency(session, commissionDto.getCurrency()) : null)
                .feeType(FeeType.valueOf(commissionDto.getFeeType()))
                .effectiveDate(LocalDateTime.now())
                .build();
    }

    private BiFunction<Session, StoreDto, Store> mapStoreDtoToStoreFunction() {
        return (session, storeDto) -> Store.builder()
                .storeId(getUUID())
                .storeName(storeDto.getStoreName())
                .address(storeDto.getAddress() != null ? mapAddressDtoToAddressFunction().apply(session, storeDto.getAddress()) : null)
                .isActive(true)
                .build();
    }

    private Function<Store, ResponseStoreDto> mapStoreToResponseStoreDtoFunction() {
        return store -> {
            List<ArticularItemQuantityDto> articularItemQuantityList = store.getArticularItemQuantities().stream()
                    .map(mapArticularItemQuantityToArticularItemQuantityDtoFunction())
                    .toList();
            return ResponseStoreDto.builder()
                    .storeName(store.getStoreName())
                    .storeId(store.getStoreId())
                    .address(mapAddressToAddressDtoFunction().apply(store.getAddress()))
                    .articularItemQuantityList(articularItemQuantityList)
                    .build();
        };
    }

    private Function<ArticularItemQuantity, ArticularItemQuantityDto> mapArticularItemQuantityToArticularItemQuantityDtoFunction() {
        return articularItemQuantity -> ArticularItemQuantityDto.builder()
                .articularId(articularItemQuantity.getArticularItem().getArticularId())
                .quantity(articularItemQuantity.getQuantity())
                .build();
    }

    private PaymentMethod fetchPaymentMethod(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                PaymentMethod.class,
                "PaymentMethod.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    private PaymentStatus fetchPaymentStatus(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                PaymentStatus.class,
                "PaymentStatus.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    private Currency fetchCurrency(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                Currency.class,
                "Currency.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    private CountryPhoneCode fetchCountryPhoneCode(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                CountryPhoneCode.class,
                "CountryPhoneCode.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    private Country fetchCountry(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                Country.class,
                "Country.findByValue",
                parameterFactory.createStringParameter(VALUE, value)
        );
    }

    private Discount fetchDiscount(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                Discount.class,
                "ArticularItem.findByDiscountCharSequenceCode",
                parameterFactory.createStringParameter(CHAR_SEQUENCE_CODE, value));
    }

    private Optional<Discount> fetchOptionDiscount(Session session, String value) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                Discount.class,
                "ArticularItem.findByDiscountCharSequenceCode",
                parameterFactory.createStringParameter(CHAR_SEQUENCE_CODE, value));
    }

    private ArticularStatus fetchArticularStatus(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                ArticularStatus.class,
                "ArticularStatus.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    private Optional<OptionGroup> fetchOptionGroup(Session session, String namedQuery, String value) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                OptionGroup.class,
                namedQuery,
                parameterFactory.createStringParameter(VALUE, value));
    }

    private OrderStatus fetchOrderStatus(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                OrderStatus.class,
                "OrderStatus.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    private ArticularItem fetchArticularItem(Session session, String articularId) {
        return queryService.getGraphEntity(
                session,
                ArticularItem.class,
                ARTICULAR_ITEM_FULL,
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId));
    }

    private DeliveryType fetchDeliveryType(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                DeliveryType.class,
                "DeliveryType.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    private TimeDurationOption fetchTimeDurationOption(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                TimeDurationOption.class,
                "TimeDurationOption.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    private MessageStatus fetchMessageStatus(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                MessageStatus.class,
                "MessageStatus.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    private MessageType fetchMessageType(Session session, String value) {
        return queryService.getNamedQueryEntity(
                session,
                MessageType.class,
                "MessageType.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    private Optional<Category> fetchOptionalCategory(Session session, String value) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                Category.class,
                "Category.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    private Optional<Brand> fetchOptionalBrand(Session session, String value) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                Brand.class,
                "Brand.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    private Optional<ItemType> fetchOptionalItemType(Session session, String value) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                ItemType.class,
                "ItemType.findByValue",
                parameterFactory.createStringParameter(VALUE, value));
    }

    private Optional<SellerCommission> fetchCommission(Session session) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                SellerCommission.class,
                "SellerCommission.getCommissionList");
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
