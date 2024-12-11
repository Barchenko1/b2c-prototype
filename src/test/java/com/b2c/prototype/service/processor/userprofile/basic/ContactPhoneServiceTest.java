package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.order.IOrderItemDao;
import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.AddressDto;
import com.b2c.prototype.modal.dto.request.ContactPhoneDto;
import com.b2c.prototype.modal.dto.update.ContactPhoneDtoUpdate;
import com.b2c.prototype.modal.entity.order.OrderItem;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContactPhoneServiceTest {

    @Mock
    private IParameterFactory parameterFactory;
    @Mock
    private IContactPhoneDao contactPhoneDao;
    @Mock
    private IUserProfileDao userProfileDao;
    @Mock
    private IOrderItemDao orderItemDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private IEntityCachedMap entityCachedMap;
    @InjectMocks
    private ContactPhoneService contactPhoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveContactPhone() {
        ContactPhoneDto contactPhoneDto = createContactPhoneDto();
        CountryPhoneCode countryPhoneCode = getCountryPhoneCode();
        ContactPhone contactPhone = getTestContactPhone();

        when(entityCachedMap.getEntity(CountryPhoneCode.class, "code", "US")).thenReturn(countryPhoneCode);

        contactPhoneService.saveContactPhone(contactPhoneDto);

        ArgumentCaptor<Supplier<ContactPhone>> captor = ArgumentCaptor.forClass(Supplier.class);
        verify(contactPhoneDao).saveEntity(captor.capture());
        assertEquals(contactPhone, captor.getValue().get());
    }

    @Test
    void testUpdateContactPhoneByEmail() {
        ContactPhoneDtoUpdate contactPhoneDtoUpdate = new ContactPhoneDtoUpdate();
        contactPhoneDtoUpdate.setNewEntityDto(ContactPhoneDto.builder()
                        .phoneNumber("48")
                        .countryPhoneCode("US")
                        .build());
        contactPhoneDtoUpdate.setSearchField("code");

        ContactPhone contactPhone = getTestContactPhone();
        Parameter mockParameter = mock(Parameter.class);
        ContactPhone expectedContactPhone = ContactPhone.builder()
                .phoneNumber("48")
                .countryPhoneCode(getCountryPhoneCode())
                .build();
        UserProfile userProfile = mock(UserProfile.class);
        ContactInfo contactInfo = mock(ContactInfo.class);

        when(entityCachedMap.getEntity(CountryPhoneCode.class, "code", "US")).thenReturn(getCountryPhoneCode());
        when(parameterFactory.createStringParameter("code", "code")).thenReturn(mockParameter);
        when(userProfileDao.getEntity(any())).thenReturn(userProfile);
        when(userProfile.getContactInfo()).thenReturn(contactInfo);
        when(contactInfo.getContactPhone()).thenReturn(contactPhone);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(expectedContactPhone);
            return null;
        }).when(contactPhoneDao).updateEntity(any(Consumer.class));

        contactPhoneService.updateContactPhoneByEmail(contactPhoneDtoUpdate);

        verify(contactPhoneDao).updateEntity(any(Consumer.class));
    }

    @Test
    void testUpdateContactPhoneByOrderId() {
        ContactPhoneDtoUpdate contactPhoneDtoUpdate = new ContactPhoneDtoUpdate();
        contactPhoneDtoUpdate.setNewEntityDto(ContactPhoneDto.builder()
                .phoneNumber("48")
                .countryPhoneCode("US")
                .build());
        contactPhoneDtoUpdate.setSearchField("code");

        ContactPhone contactPhone = getTestContactPhone();
        Parameter mockParameter = mock(Parameter.class);
        ContactPhone expectedContactPhone = ContactPhone.builder()
                .phoneNumber("48")
                .countryPhoneCode(getCountryPhoneCode())
                .build();
        OrderItem orderItem = mock(OrderItem.class);
        ContactInfo contactInfo = mock(ContactInfo.class);

        when(entityCachedMap.getEntity(CountryPhoneCode.class, "code", "US")).thenReturn(getCountryPhoneCode());
        when(parameterFactory.createStringParameter("code", "code")).thenReturn(mockParameter);
        when(orderItemDao.getEntity(any())).thenReturn(orderItem);
        when(orderItem.getBeneficiaries()).thenReturn(List.of(contactInfo));
        when(contactInfo.getContactPhone()).thenReturn(contactPhone);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(expectedContactPhone);
            return null;
        }).when(contactPhoneDao).updateEntity(any(Consumer.class));

        contactPhoneService.updateContactPhoneByOrderId(contactPhoneDtoUpdate);

        verify(contactPhoneDao).updateEntity(any(Consumer.class));
    }

    @Test
    void testDeleteContactPhoneByEmail() {
        String email = "test@test.com";
        Parameter mockParameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(email);

        when(parameterFactory.createStringParameter("email", email)).thenReturn(mockParameter);

        contactPhoneService.deleteContactPhoneByEmail(oneFieldEntityDto);

        verify(contactPhoneDao).findEntityAndDelete(mockParameter);
    }

    @Test
    void testDeleteContactPhoneByOrderId() {
        String orderId = "123";
        Parameter mockParameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(orderId);

        when(parameterFactory.createStringParameter("order_id", orderId)).thenReturn(mockParameter);

        contactPhoneService.deleteContactPhoneByOrderId(oneFieldEntityDto);

        verify(contactPhoneDao).findEntityAndDelete(mockParameter);
    }

    @Test
    void testGetContactPhoneByEmail() {
        String email = "test@test.com";
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(email);
        ContactPhoneDto contactPhoneDto = createContactPhoneDto();
        Parameter mockParameter = mock(Parameter.class);
        UserProfile userProfile = mock(UserProfile.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        ContactPhone contactPhone = getTestContactPhone();

        when(parameterFactory.createStringParameter("email", email)).thenReturn(mockParameter);
        when(queryService.getEntityDto(eq(UserProfile.class), any(Supplier.class), any(Function.class)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> paramSupplier = invocation.getArgument(1);
                    Function<UserProfile, ContactPhoneDto> mappingFunction = invocation.getArgument(2);
                    assertEquals(mockParameter, paramSupplier.get());
                    return mappingFunction.apply(userProfile);
                });
        when(userProfile.getContactInfo()).thenReturn(contactInfo);
        when(contactInfo.getContactPhone()).thenReturn(contactPhone);

        ContactPhoneDto result = contactPhoneService.getContactPhoneByEmail(oneFieldEntityDto);

        assertEquals(contactPhoneDto.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(contactPhoneDto.getCountryPhoneCode(), result.getCountryPhoneCode());
    }

    @Test
    void testGetContactPhoneByOrderId() {
        String orderId = "123";
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(orderId);
        ContactPhoneDto contactPhoneDto = createContactPhoneDto();
        Parameter mockParameter = mock(Parameter.class);
        OrderItem orderItem = mock(OrderItem.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        ContactPhone contactPhone = getTestContactPhone();

        when(parameterFactory.createStringParameter("order_id", orderId)).thenReturn(mockParameter);
        when(queryService.getEntityDto(eq(OrderItem.class), any(Supplier.class), any(Function.class)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> paramSupplier = invocation.getArgument(1);
                    Function<OrderItem, ContactPhoneDto> mappingFunction = invocation.getArgument(2);
                    assertEquals(mockParameter, paramSupplier.get());
                    return mappingFunction.apply(orderItem);
                });
        when(orderItem.getBeneficiaries()).thenReturn(List.of(contactInfo));
        when(contactInfo.getContactPhone()).thenReturn(contactPhone);

        ContactPhoneDto result = contactPhoneService.getContactPhoneByOrderId(oneFieldEntityDto);

        assertEquals(contactPhoneDto.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(contactPhoneDto.getCountryPhoneCode(), result.getCountryPhoneCode());
    }

    @Test
    void testGetContactPhoneList() {
        when(contactPhoneDao.getEntityList()).thenReturn(List.of(
                ContactPhone.builder()
                        .phoneNumber("1234567890")
                        .countryPhoneCode(getCountryPhoneCode())
                        .build(),
                ContactPhone.builder()
                        .phoneNumber("0987654321")
                        .countryPhoneCode(getCountryPhoneCode())
                        .build()
        ));

        List<ContactPhoneDto> result = contactPhoneService.getContactPhoneList();

        assertEquals(2, result.size());
        assertEquals("1234567890", result.get(0).getPhoneNumber());
        assertEquals("0987654321", result.get(1).getPhoneNumber());
    }

    private ContactPhoneDto createContactPhoneDto() {
        return ContactPhoneDto.builder()
                .phoneNumber("1234567890")
                .countryPhoneCode("US")
                .build();
    }

    private ContactPhone getTestContactPhone() {
        return ContactPhone.builder()
                .phoneNumber("1234567890")
                .countryPhoneCode(getCountryPhoneCode())
                .build();
    }

    private CountryPhoneCode getCountryPhoneCode() {
        return CountryPhoneCode.builder()
                .code("US")
                .build();
    }
}
