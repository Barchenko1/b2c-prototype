package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ContactInfoSearchFieldOrderNumberDto;
import com.b2c.prototype.modal.dto.request.ContactPhoneDto;
import com.b2c.prototype.modal.dto.update.ContactPhoneDtoUpdate;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ContactPhoneServiceTest {

    @Mock
    private IContactPhoneDao contactPhoneDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @Mock
    private ISingleValueMap singleValueMap;
    @InjectMocks
    private ContactPhoneService contactPhoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateContactPhoneByUserId() {
        ContactPhoneDtoUpdate contactPhoneDtoUpdate = ContactPhoneDtoUpdate.builder()
                .newEntity(ContactPhoneDto.builder()
                        .phoneNumber("48")
                        .countryPhoneCode("US")
                        .phoneNumber("1234567890")
                        .build())
                .searchField("code")
                .build();

        ContactPhone contactPhone = getTestContactPhone();
        Parameter mockParameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        UserProfile userProfile = mock(UserProfile.class);
        ContactInfo contactInfo = mock(ContactInfo.class);

        when(singleValueMap.getEntity(CountryPhoneCode.class, "code", "US")).thenReturn(getCountryPhoneCode());
        when(queryService.getEntity(eq(UserProfile.class), any(Supplier.class)))
                .thenReturn(userProfile);
        when(transformationFunctionService.getEntity(
                ContactPhone.class,
                contactPhoneDtoUpdate.getNewEntity())).thenReturn(contactPhone);
        when(supplierService.parameterStringSupplier("user_id", contactPhoneDtoUpdate.getSearchField()))
                .thenReturn(parameterSupplier);
        when(userProfile.getContactInfo()).thenReturn(contactInfo);
        when(contactInfo.getContactPhone()).thenReturn(contactPhone);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(contactInfo);
            return null;
        }).when(contactPhoneDao).executeConsumer(any(Consumer.class));

        contactPhoneService.saveUpdateContactPhoneByUserId(contactPhoneDtoUpdate);

        verify(contactPhoneDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateContactPhoneByOrderId() {
        ContactPhoneDtoUpdate contactPhoneDtoUpdate = ContactPhoneDtoUpdate.builder()
                .orderNumber(0)
                .newEntity(ContactPhoneDto.builder()
                        .phoneNumber("48")
                        .countryPhoneCode("US")
                        .phoneNumber("1234567890")
                        .build())
                .searchField("code")
                .build();

        ContactPhone contactPhone = getTestContactPhone();
        Parameter mockParameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        OrderItemData orderItemData = mock(OrderItemData.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        List<ContactInfo> orderItemList = List.of(contactInfo);

        when(singleValueMap.getEntity(CountryPhoneCode.class, "code", "US")).thenReturn(getCountryPhoneCode());
        when(queryService.getEntity(eq(OrderItemData.class), any(Supplier.class)))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(
                ContactPhone.class,
                contactPhoneDtoUpdate.getNewEntity())).thenReturn(contactPhone);
        when(supplierService.parameterStringSupplier("order_id", contactPhoneDtoUpdate.getSearchField()))
                .thenReturn(parameterSupplier);
        when(orderItemData.getBeneficiaries()).thenReturn(orderItemList);
        when(contactInfo.getContactPhone()).thenReturn(contactPhone);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(contactInfo);
            return null;
        }).when(contactPhoneDao).executeConsumer(any(Consumer.class));

        contactPhoneService.saveUpdateContactPhoneByOrderId(contactPhoneDtoUpdate);

        verify(contactPhoneDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteContactPhoneByUserId() {
        String userId = "123";
        Parameter mockParameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(userId);
        UserProfile userProfile = mock(UserProfile.class);
        ContactInfo contactInfo = mock(ContactInfo.class);

        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        when(queryService.getEntity(eq(UserProfile.class), any(Supplier.class)))
                .thenReturn(userProfile);
        when(supplierService.parameterStringSupplier("user_id", userId))
                .thenReturn(parameterSupplier);
        when(userProfile.getContactInfo()).thenReturn(contactInfo);
        when(contactInfo.getContactPhone()).thenReturn(getTestContactPhone());
        doNothing().when(contactPhoneDao).deleteEntity(any(Supplier.class));

        contactPhoneService.deleteContactPhoneByUserId(oneFieldEntityDto);

        verify(contactPhoneDao).deleteEntity(any(Supplier.class));
    }

    @Test
    void testDeleteContactPhoneByOrderId() {
        String orderId = "123";
        Parameter mockParameter = mock(Parameter.class);
        ContactInfoSearchFieldOrderNumberDto contactInfoSearchFieldOrderNumberDto =
                ContactInfoSearchFieldOrderNumberDto.builder()
                        .orderNumber(0)
                        .value(orderId)
                        .build();
        OrderItemData orderItemData = mock(OrderItemData.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        List<ContactInfo> contactInfoList = List.of(contactInfo);

        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        when(queryService.getEntity(eq(OrderItemData.class), any(Supplier.class)))
                .thenReturn(orderItemData);
        when(supplierService.parameterStringSupplier("order_id", orderId))
                .thenReturn(parameterSupplier);
        when(orderItemData.getBeneficiaries()).thenReturn(contactInfoList);
        when(contactInfo.getContactPhone()).thenReturn(getTestContactPhone());
        doNothing().when(contactPhoneDao).deleteEntity(any(Supplier.class));

        contactPhoneService.deleteContactPhoneByOrderId(contactInfoSearchFieldOrderNumberDto);

        verify(contactPhoneDao).deleteEntity(any(Supplier.class));
    }

    @Test
    void testGetContactPhoneByUserId() {
        String userId = "123";
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(userId);
        ContactPhoneDto contactPhoneDto = createContactPhoneDto();
        Parameter mockParameter = mock(Parameter.class);
        UserProfile userProfile = mock(UserProfile.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        ContactPhone contactPhone = getTestContactPhone();

        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        Function<UserProfile, ContactPhoneDto> mapFunction = profile -> contactPhoneDto;

        when(supplierService.parameterStringSupplier("user_id", oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(UserProfile.class, ContactPhoneDto.class))
                .thenReturn(mapFunction);
        when(queryService.getEntityDto(eq(UserProfile.class), any(Supplier.class), any(Function.class)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> paramSupplier = invocation.getArgument(1);
                    Function<UserProfile, ContactPhoneDto> mappingFunction = invocation.getArgument(2);
                    assertEquals(mockParameter, paramSupplier.get());
                    return mappingFunction.apply(userProfile);
                });
        when(userProfile.getContactInfo()).thenReturn(contactInfo);
        when(contactInfo.getContactPhone()).thenReturn(contactPhone);

        ContactPhoneDto result = contactPhoneService.getContactPhoneByUserId(oneFieldEntityDto);

        assertEquals(contactPhoneDto.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(contactPhoneDto.getCountryPhoneCode(), result.getCountryPhoneCode());
    }

    @Test
    void testGetContactPhoneByOrderId() {
        String orderId = "123";
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(orderId);
        ContactPhoneDto contactPhoneDto = createContactPhoneDto();
        Parameter mockParameter = mock(Parameter.class);
        OrderItemData orderItemData = mock(OrderItemData.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        ContactPhone contactPhone = getTestContactPhone();

        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        Function<OrderItemData, ContactPhoneDto> mapFunction = profile -> contactPhoneDto;

        when(supplierService.parameterStringSupplier("order_id", oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(OrderItemData.class, ContactPhoneDto.class, "list"))
                .thenReturn(mapFunction);

        when(queryService.getSubEntityDtoList(eq(OrderItemData.class), any(Supplier.class), any(Function.class)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> paramSupplier = invocation.getArgument(1);
                    Function<OrderItemData, ContactPhoneDto> mappingFunction = invocation.getArgument(2);
                    assertEquals(mockParameter, paramSupplier.get());
                    return List.of(mappingFunction.apply(orderItemData));
                });
        when(orderItemData.getBeneficiaries()).thenReturn(List.of(contactInfo));
        when(contactInfo.getContactPhone()).thenReturn(contactPhone);

        List<ContactPhoneDto> resultList = contactPhoneService.getContactPhoneByOrderId(oneFieldEntityDto);
        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            assertEquals(contactPhoneDto.getPhoneNumber(), result.getPhoneNumber());
            assertEquals(contactPhoneDto.getCountryPhoneCode(), result.getCountryPhoneCode());
        });
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

        Function<ContactPhone, ContactPhoneDto> mapFunction = contactPhone -> ContactPhoneDto.builder()
                .phoneNumber(contactPhone.getPhoneNumber())
                .countryPhoneCode(contactPhone.getCountryPhoneCode().getCode())
                .build();
        when(transformationFunctionService.getTransformationFunction(ContactPhone.class, ContactPhoneDto.class))
                .thenReturn(mapFunction);

        List<ContactPhoneDto> resultListDto = contactPhoneService.getContactPhoneList();

        assertEquals(2, resultListDto.size());
        assertEquals("1234567890", resultListDto.get(0).getPhoneNumber());
        assertEquals("0987654321", resultListDto.get(1).getPhoneNumber());
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
