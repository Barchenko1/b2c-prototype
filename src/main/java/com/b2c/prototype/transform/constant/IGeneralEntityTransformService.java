package com.b2c.prototype.transform.constant;

import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseMinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.region.RegionDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.region.Region;
import com.b2c.prototype.modal.entity.user.ContactInfo;

public interface IGeneralEntityTransformService {

    Address mapAddressDtoToAddress(AddressDto addressDto);
    AddressDto mapAddressToAddressDto(Address address);

    ContactInfo mapContactInfoDtoToContactInfo(ContactInfoDto contactInfoDto);
    ContactInfoDto mapContactInfoToContactInfoDto(ContactInfo contactInfo);

    MinMaxCommission mapMinMaxCommissionDtoToMinMaxCommission(MinMaxCommissionDto minMaxCommissionDto);
    ResponseMinMaxCommissionDto mapMinMaxCommissionToResponseMinMaxCommissionDto(MinMaxCommission minMaxCommission);

    Country mapConstantPayloadDtoToCountry(CountryDto countryDto);
    CountryDto mapCountryToConstantPayloadDto(Country country);

    RegionDto mapRegionToRegionDto(Region region);
    Region mapRegionDtoToRegion(RegionDto regionDto);
}
