package com.b2c.prototype.transform.constant;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseMinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryPhoneCodeDto;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.order.ContactPhoneDto;
import com.b2c.prototype.modal.dto.payload.region.RegionDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.region.Region;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

import static com.b2c.prototype.util.Constant.KEY;

@Service
public class GeneralEntityTransformService implements IGeneralEntityTransformService {

    private final IGeneralEntityDao generalEntityDao;

    public GeneralEntityTransformService(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    @Override
    public Address mapAddressDtoToAddress(AddressDto addressDto) {
        return Address.builder()
                .country(generalEntityDao.findEntity("Country.findByKey",
                        Pair.of(KEY, addressDto.getCountry().getKey())))
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
}
