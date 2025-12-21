package com.b2c.prototype.transform.constant;

import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.constant.ArticularStatusDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountResponseDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemCostDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.tenant.TenantDto;
import com.b2c.prototype.modal.dto.payload.store.AvailabilityStatusDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.tenant.Tenant;
import com.b2c.prototype.modal.entity.store.AvailabilityStatus;
import com.b2c.prototype.modal.entity.user.ContactInfo;

public interface IGeneralEntityTransformService {

    Currency mapCurrencyDtoToCurrency(CurrencyDto currencyDto);
    CurrencyDto mapCurrencyToCurrencyDto(Currency currency);

    TenantDto mapRegionToRegionDto(Tenant tenant);
    Tenant mapRegionDtoToRegion(TenantDto tenantDto);

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

    Discount mapDiscountDtoToDiscount(DiscountDto discountDto);
    DiscountDto mapDiscountToDiscountDto(Discount discount);
    DiscountResponseDto mapDiscountToDiscountResponseDto(Discount discount);

    OptionItemDto mapOptionItemToOptionItemDto(OptionItem optionItem);
    OptionItemCostDto mapOptionItemCostToOptionItemCostDto(OptionItemCost optionItemCost);

    ArticularStatusDto mapArticularStatusToArticularStatusDto(ArticularStatus articularStatus);

    ArticularItem mapArticularItemDtoToArticularItem(ArticularItemDto articularItemDto);
    ArticularItemDto mapArticularItemToArticularItemDto(ArticularItem articularItem);
}
