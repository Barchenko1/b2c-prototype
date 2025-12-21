package com.b2c.prototype.transform.constant;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.constant.FeeType;
import com.b2c.prototype.modal.dto.payload.commission.CommissionValueDto;
import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.constant.ArticularStatusDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryPhoneCodeDto;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountResponseDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemCostDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.order.ContactPhoneDto;
import com.b2c.prototype.modal.dto.payload.tenant.TenantDto;
import com.b2c.prototype.modal.dto.payload.store.AvailabilityStatusDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import com.b2c.prototype.modal.entity.payment.CommissionValue;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.tenant.Tenant;
import com.b2c.prototype.modal.entity.store.AvailabilityStatus;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.CODE;
import static com.b2c.prototype.util.Constant.KEY;
import static com.b2c.prototype.util.Util.getUUID;

@Service
public class GeneralEntityTransformService implements IGeneralEntityTransformService {

    private final IGeneralEntityDao generalEntityDao;

    public GeneralEntityTransformService(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    @Override
    public Currency mapCurrencyDtoToCurrency(CurrencyDto currencyDto) {
        return generalEntityDao.findEntity("Currency.findByKey",
                Pair.of(KEY, currencyDto.getKey()));
    }

    @Override
    public CurrencyDto mapCurrencyToCurrencyDto(Currency currency) {
        return CurrencyDto.builder()
                .key(currency.getKey())
                .value(currency.getValue())
                .build();
    }

    @Override
    public TenantDto mapRegionToRegionDto(Tenant tenant) {
        return TenantDto.builder()
                .code(tenant.getCode())
                .value(tenant.getValue())
                .language(tenant.getLanguage())
                .defaultLocale(tenant.getDefaultLocale())
                .primaryCurrency(CurrencyDto.builder()
                        .key(tenant.getPrimaryCurrency().getKey())
                        .value(tenant.getPrimaryCurrency().getValue())
                        .build())
                .timezone(tenant.getTimezone().toString())
                .build();
    }

    @Override
    public Tenant mapRegionDtoToRegion(TenantDto tenantDto) {
        return Tenant.builder()
                .code(tenantDto.getCode())
                .value(tenantDto.getValue())
                .language(tenantDto.getLanguage())
                .primaryCurrency(generalEntityDao.<Currency>findOptionEntity(
                                "Currency.findByKey",
                                Pair.of(KEY, tenantDto.getPrimaryCurrency().getKey()))
                        .orElseGet(() -> Currency.builder()
                                .key(tenantDto.getPrimaryCurrency().getKey())
                                .value(tenantDto.getPrimaryCurrency().getValue())
                                .build()))
                .defaultLocale(tenantDto.getDefaultLocale())
                .timezone(ZoneId.of(tenantDto.getTimezone()))
                .build();
    }

    @Override
    public Price mapPriceDtoToPrice(PriceDto priceDto) {
        return Price.builder()
                .amount(priceDto.getAmount())
                .currency(mapCurrencyDtoToCurrency(priceDto.getCurrency()))
                .build();
    }

    @Override
    public PriceDto mapPriceToPriceDto(Price price) {
        return PriceDto.builder()
                .amount(price.getAmount())
                .currency(mapCurrencyToCurrencyDto(price.getCurrency()))
                .build();
    }

    @Override
    public Country mapCountryDtoToCountry(CountryDto countryDto) {
        return generalEntityDao.findEntity("Country.findByKey", Pair.of(KEY, countryDto.getKey()));
    }

    @Override
    public CountryDto mapCountryToCountryDto(Country country) {
        return CountryDto.builder()
                .key(country.getKey())
                .value(country.getValue())
                .flagImagePath(country.getFlagImagePath())
                .build();
    }

    @Override
    public Address mapAddressDtoToAddress(AddressDto addressDto) {
        return Address.builder()
                .country(mapCountryDtoToCountry(addressDto.getCountry()))
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .buildingNumber(addressDto.getBuildingNumber())
                .florNumber(addressDto.getFlorNumber())
                .apartmentNumber(addressDto.getApartmentNumber())
                .zipCode(addressDto.getZipCode())
                .build();
    }

    @Override
    public AddressDto mapAddressToAddressDto(Address address) {
        return AddressDto.builder()
                .country(this.mapCountryToCountryDto(address.getCountry()))
                .city(address.getCity())
                .street(address.getStreet())
                .buildingNumber(address.getBuildingNumber())
                .florNumber(address.getFlorNumber())
                .apartmentNumber(address.getApartmentNumber())
                .zipCode(address.getZipCode())
                .build();
    }

    @Override
    public ContactInfo mapContactInfoDtoToContactInfo(ContactInfoDto contactInfoDto) {
        return ContactInfo.builder()
                .firstName(contactInfoDto.getFirstName())
                .lastName(contactInfoDto.getLastName())
                .email(contactInfoDto.getEmail())
                .isEmailVerified(contactInfoDto.isEmailVerified())
                .isContactPhoneVerified(contactInfoDto.isPhoneVerified())
                .contactPhone(ContactPhone.builder()
                        .countryPhoneCode(generalEntityDao.findEntity("CountryPhoneCode.findByKey",
                                Pair.of(KEY, contactInfoDto.getContactPhone().getCountryPhoneCode().getKey())))
                        .phoneNumber(contactInfoDto.getContactPhone().getPhoneNumber())
                        .build())
                .birthdayDate(contactInfoDto.getBirthdayDate())
                .build();
    }

    @Override
    public ContactInfoDto mapContactInfoToContactInfoDto(ContactInfo contactInfo) {
        return ContactInfoDto.builder()
                .firstName(contactInfo.getFirstName())
                .lastName(contactInfo.getLastName())
                .email(contactInfo.getEmail())
                .isEmailVerified(contactInfo.isEmailVerified())
                .isPhoneVerified(contactInfo.isContactPhoneVerified())
                .contactPhone(ContactPhoneDto.builder()
                        .countryPhoneCode(CountryPhoneCodeDto.builder()
                                .value(contactInfo.getContactPhone().getCountryPhoneCode().getValue())
                                .key(contactInfo.getContactPhone().getCountryPhoneCode().getKey())
                                .build())
                        .phoneNumber(contactInfo.getContactPhone().getPhoneNumber())
                        .build())
                .birthdayDate(contactInfo.getBirthdayDate())
                .build();
    }

    @Override
    public MinMaxCommission mapMinMaxCommissionDtoToMinMaxCommission(MinMaxCommissionDto minMaxCommissionDto) {
        return MinMaxCommission.builder()
                .minCommission(mapCommissionValueDtoToCommissionValue(minMaxCommissionDto.getMinCommissionValue()))
                .maxCommission(mapCommissionValueDtoToCommissionValue(minMaxCommissionDto.getMaxCommissionValue()))
                .changeCommissionPrice(mapPriceDtoToPrice(minMaxCommissionDto.getChangeCommissionPrice()))
                .lastUpdateTimestamp(LocalDateTime.now())
                .key(getUUID())
                .tenant(generalEntityDao.findEntity("Tenant.findByCode",
                                Pair.of(CODE, minMaxCommissionDto.getRegion())))
                .build();
    }

    @Override
    public MinMaxCommissionDto mapMinMaxCommissionToResponseMinMaxCommissionDto(MinMaxCommission minMaxCommission) {
        return MinMaxCommissionDto.builder()
                .minCommissionValue(mapCommissionValueToCommissionValueDto(minMaxCommission.getMinCommission()))
                .maxCommissionValue(mapCommissionValueToCommissionValueDto(minMaxCommission.getMaxCommission()))
                .changeCommissionPrice(mapPriceToPriceDto(minMaxCommission.getChangeCommissionPrice()))
                .lastUpdateTimestamp(minMaxCommission.getLastUpdateTimestamp())
                .key(minMaxCommission.getKey())
                .region(minMaxCommission.getTenant().getCode())
                .build();
    }

    private CommissionValue mapCommissionValueDtoToCommissionValue(CommissionValueDto commissionValueDto) {
        return CommissionValue.builder()
                .amount(commissionValueDto.getAmount())
                .currency(commissionValueDto.getCurrency() != null
                        ? mapCurrencyDtoToCurrency(commissionValueDto.getCurrency())
                        : null)
                .feeType(FeeType.valueOf(commissionValueDto.getFeeType()))
                .build();
    }

    private CommissionValueDto mapCommissionValueToCommissionValueDto(CommissionValue commissionValue) {
        return CommissionValueDto.builder()
                .amount(commissionValue.getAmount())
                .currency(commissionValue.getCurrency() != null
                        ? mapCurrencyToCurrencyDto(commissionValue.getCurrency())
                        : null)
                .feeType(commissionValue.getFeeType().name())
                .build();
    }

    @Override
    public AvailabilityStatus mapAvailabilityStatusDtoToAvailabilityStatus(AvailabilityStatusDto availabilityStatusDto) {
        return AvailabilityStatus.builder()
                .key(availabilityStatusDto.getKey())
                .value(availabilityStatusDto.getValue())
                .build();
    }

    @Override
    public AvailabilityStatusDto mapAvailabilityStatusToAvailabilityStatusDto(AvailabilityStatus availabilityStatus) {
        return AvailabilityStatusDto.builder()
                .key(availabilityStatus.getKey())
                .value(availabilityStatus.getValue())
                .build();
    }

    @Override
    public Discount mapDiscountDtoToDiscount(DiscountDto discountDto) {
        return Discount.builder()
                .charSequenceCode(discountDto.getCharSequenceCode())
                .amount(discountDto.getAmount())
                .isPercent(discountDto.isPercent())
                .isActive(discountDto.isActive())
                .currency(discountDto.getCurrency() != null
                        ? this.mapCurrencyDtoToCurrency(discountDto.getCurrency())
                        : null)
                .articularItemList(generalEntityDao.findEntityList("ArticularItem.findByArticularIds",
                        Pair.of("articularId", discountDto.getArticularIdSet())))
                .build();
    }

    @Override
    public DiscountDto mapDiscountToDiscountDto(Discount discount) {
        Set<String> articularIds = discount.getArticularItemList().stream()
                .map(ArticularItem::getArticularUniqId)
                .collect(Collectors.toSet());
        return DiscountDto.builder()
                .charSequenceCode(discount.getCharSequenceCode())
                .amount(discount.getAmount())
                .isPercent(discount.isPercent())
                .isActive(discount.isActive())
                .currency(discount.getCurrency() != null
                        ? this.mapCurrencyToCurrencyDto(discount.getCurrency())
                        : null)
//                .articularIdSet(articularIds)
//                .articularItemList(generalEntityDao.findEntityList("ArticularItem.findByArticularIds",
//                        Pair.of("articularId", discount.getArticularIdSet())))
                .build();
    }

    @Override
    public DiscountResponseDto mapDiscountToDiscountResponseDto(Discount discount) {
        return DiscountResponseDto.builder()
                .charSequenceCode(discount.getCharSequenceCode())
                .amount(discount.getAmount())
                .isPercent(discount.isPercent())
                .isActive(discount.isActive())
                .currency(discount.getCurrency() != null
                        ? this.mapCurrencyToCurrencyDto(discount.getCurrency())
                        : null)
                .articularItems(discount.getArticularItemList().stream()
                        .map(this::mapArticularItemToArticularItemDto)
                        .toList())
                .build();
    }

    @Override
    public OptionItemDto mapOptionItemToOptionItemDto(OptionItem optionItem) {
        return OptionItemDto.builder()
                .key(optionItem.getKey())
                .value(optionItem.getValue())
                .build();
    }

    @Override
    public OptionItemCostDto mapOptionItemCostToOptionItemCostDto(OptionItemCost optionItemCost) {
        return OptionItemCostDto.builder()
                .key(optionItemCost.getKey())
                .value(optionItemCost.getValue())
                .price(this.mapPriceToPriceDto(optionItemCost.getPrice()))
                .build();
    }

    @Override
    public ArticularStatusDto mapArticularStatusToArticularStatusDto(ArticularStatus articularStatus) {
        return ArticularStatusDto.builder()
                .key(articularStatus.getKey())
                .value(articularStatus.getValue())
                .build();
    }

    @Override
    public ArticularItem mapArticularItemDtoToArticularItem(ArticularItemDto articularItemDto) {
        return null;
    }

    @Override
    public ArticularItemDto mapArticularItemToArticularItemDto(ArticularItem articularItem) {
        return ArticularItemDto.builder()
                .articularId(articularItem.getArticularUniqId())
                .productName(articularItem.getProductName())
                .dateOfCreate(articularItem.getDateOfCreate())
                .options(articularItem.getOptionItems().stream()
                        .map(this::mapOptionItemToOptionItemDto)
                        .toList())
                .costOptions(articularItem.getOptionItemCosts().stream()
                        .map(this::mapOptionItemCostToOptionItemCostDto)
                        .toList())
                .fullPrice(this.mapPriceToPriceDto(articularItem.getFullPrice()))
                .totalPrice(this.mapPriceToPriceDto(articularItem.getTotalPrice()))
                .status(this.mapArticularStatusToArticularStatusDto(articularItem.getStatus()))
                .discount(articularItem.getDiscount() != null
                        ? this.mapDiscountToDiscountDto(articularItem.getDiscount())
                        : null)
                .build();
    }
}
