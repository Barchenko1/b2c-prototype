package com.b2c.prototype.transform.constant;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseMinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.user.ContactInfo;

public interface IGeneralEntityTransformService {
    Currency mapConstantPayloadDtoToCurrency(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapCurrencyToConstantPayloadDto(Currency currency);

    Discount mapDiscountDtoToDiscount(DiscountDto discountDto);
    DiscountDto mapDiscountToDiscountDto(Discount discount);

    Address mapAddressDtoToAddress(AddressDto addressDto);
    AddressDto mapAddressToAddressDto(Address address);

    ContactInfo mapContactInfoDtoToContactInfo(ContactInfoDto contactInfoDto);
    ContactInfoDto mapContactInfoToContactInfoDto(ContactInfo contactInfo);

    MinMaxCommission mapMinMaxCommissionDtoToMinMaxCommission(MinMaxCommissionDto minMaxCommissionDto);
    ResponseMinMaxCommissionDto mapMinMaxCommissionToResponseMinMaxCommissionDto(MinMaxCommission minMaxCommission);

    Country mapConstantPayloadDtoToCountry(CountryDto countryDto);
    CountryDto mapCountryToConstantPayloadDto(Country country);
}
