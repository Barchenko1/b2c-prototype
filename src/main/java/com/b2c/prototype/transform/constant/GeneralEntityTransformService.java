package com.b2c.prototype.transform.constant;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.constant.FeeType;
import com.b2c.prototype.modal.dto.payload.commission.CommissionValueDto;
import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryPhoneCodeDto;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.order.ContactPhoneDto;
import com.b2c.prototype.modal.dto.payload.region.RegionDto;
import com.b2c.prototype.modal.dto.payload.store.AvailabilityStatusDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.payment.CommissionValue;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.region.Region;
import com.b2c.prototype.modal.entity.store.AvailabilityStatus;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

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
    public RegionDto mapRegionToRegionDto(Region region) {
        return RegionDto.builder()
                .code(region.getCode())
                .value(region.getValue())
                .language(region.getLanguage())
                .defaultLocale(region.getDefaultLocale())
                .primaryCurrency(CurrencyDto.builder()
                        .key(region.getPrimaryCurrency().getKey())
                        .value(region.getPrimaryCurrency().getValue())
                        .build())
                .timezone(region.getTimezone().toString())
                .build();
    }

    @Override
    public Region mapRegionDtoToRegion(RegionDto regionDto) {
        return Region.builder()
                .code(regionDto.getCode())
                .value(regionDto.getValue())
                .language(regionDto.getLanguage())
                .primaryCurrency(generalEntityDao.<Currency>findOptionEntity(
                                "Currency.findByKey",
                                Pair.of(KEY, regionDto.getPrimaryCurrency().getKey()))
                        .orElseGet(() -> Currency.builder()
                                .key(regionDto.getPrimaryCurrency().getKey())
                                .value(regionDto.getPrimaryCurrency().getValue())
                                .build()))
                .defaultLocale(regionDto.getDefaultLocale())
                .timezone(ZoneId.of(regionDto.getTimezone()))
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
                .country(CountryDto.builder()
                        .value(address.getCountry().getValue())
                        .key(address.getCountry().getKey())
                        .build())
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
                .region(generalEntityDao.findEntity("Region.findByCode",
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
                .region(minMaxCommission.getRegion().getCode())
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
}
