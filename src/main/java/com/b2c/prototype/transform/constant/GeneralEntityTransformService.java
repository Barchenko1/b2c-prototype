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
import org.springframework.stereotype.Service;

@Service
public class GeneralEntityTransformService implements IGeneralEntityTransformService {

    @Override
    public Currency mapConstantPayloadDtoToCurrency(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapCurrencyToConstantPayloadDto(Currency currency) {
        return null;
    }

    @Override
    public Discount mapDiscountDtoToDiscount(DiscountDto discountDto) {
        return null;
    }

    @Override
    public DiscountDto mapDiscountToDiscountDto(Discount discount) {
        return null;
    }

    @Override
    public Address mapAddressDtoToAddress(AddressDto addressDto) {
        return null;
    }

    @Override
    public AddressDto mapAddressToAddressDto(Address address) {
        return null;
    }

    @Override
    public ContactInfo mapContactInfoDtoToContactInfo(ContactInfoDto contactInfoDto) {
        return null;
    }

    @Override
    public ContactInfoDto mapContactInfoToContactInfoDto(ContactInfo contactInfo) {
        return null;
    }

    @Override
    public MinMaxCommission mapMinMaxCommissionDtoToMinMaxCommission(MinMaxCommissionDto minMaxCommissionDto) {
        return null;
    }

    @Override
    public ResponseMinMaxCommissionDto mapMinMaxCommissionToResponseMinMaxCommissionDto(MinMaxCommission minMaxCommission) {
        return null;
    }

    @Override
    public Country mapConstantPayloadDtoToCountry(CountryDto countryDto) {
        return null;
    }

    @Override
    public CountryDto mapCountryToConstantPayloadDto(Country country) {
        return null;
    }
}
