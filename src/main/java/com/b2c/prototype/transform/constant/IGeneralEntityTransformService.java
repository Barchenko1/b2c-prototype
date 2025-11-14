package com.b2c.prototype.transform.constant;

import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.region.RegionDto;
import com.b2c.prototype.modal.dto.payload.store.AvailabilityStatusDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.region.Region;
import com.b2c.prototype.modal.entity.store.AvailabilityStatus;
import com.b2c.prototype.modal.entity.user.ContactInfo;

public interface IGeneralEntityTransformService {

    Currency mapCurrencyDtoToCurrency(CurrencyDto currencyDto);
    CurrencyDto mapCurrencyToCurrencyDto(Currency currency);

    RegionDto mapRegionToRegionDto(Region region);
    Region mapRegionDtoToRegion(RegionDto regionDto);

    Price mapPriceDtoToPrice(PriceDto priceDto);
    PriceDto mapPriceToPriceDto(Price price);

    Country mapCountryDtoToCountry(CountryDto countryDto);
    CountryDto mapCountryToCountryDto(Country country);

    Address mapAddressDtoToAddress(AddressDto addressDto);
    AddressDto mapAddressToAddressDto(Address address);

    ContactInfo mapContactInfoDtoToContactInfo(ContactInfoDto contactInfoDto);
    ContactInfoDto mapContactInfoToContactInfoDto(ContactInfo contactInfo);

    MinMaxCommission mapMinMaxCommissionDtoToMinMaxCommission(MinMaxCommissionDto minMaxCommissionDto);
    MinMaxCommissionDto mapMinMaxCommissionToResponseMinMaxCommissionDto(MinMaxCommission minMaxCommission);

    AvailabilityStatus mapAvailabilityStatusDtoToAvailabilityStatus(AvailabilityStatusDto availabilityStatusDto);
    AvailabilityStatusDto mapAvailabilityStatusToAvailabilityStatusDto(AvailabilityStatus availabilityStatus);

}
