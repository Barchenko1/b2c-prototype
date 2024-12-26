package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ContactInfoArrayDtoSearchField;
import com.b2c.prototype.modal.dto.request.ContactInfoDto;
import com.b2c.prototype.modal.dto.request.ContactInfoDtoSearchField;
import com.b2c.prototype.modal.dto.request.ContactInfoSearchFieldOrderNumberDto;
import com.b2c.prototype.modal.dto.request.ContactPhoneDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ContactInfoServiceTest {
    @Mock
    private IContactInfoDao contactInfoDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private ContactInfoService contactInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateAppUserAddress() {
        ContactInfoDtoSearchField contactInfoDtoSearchField = ContactInfoDtoSearchField.builder()
                .searchField("123")
                .newEntity(getContactInfoDto())
                .build();
        UserProfile userProfile = mock(UserProfile.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        when(userProfile.getContactInfo()).thenReturn(contactInfo);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("user_id", contactInfoDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(UserProfile.class, parameterSupplier))
                .thenReturn(userProfile);
        when(transformationFunctionService.getEntity(ContactInfo.class, contactInfoDtoSearchField.getNewEntity()))
                .thenReturn(contactInfo);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userProfile);
            return null;
        }).when(contactInfoDao).executeConsumer(any(Consumer.class));

        contactInfoService.saveUpdateContactInfoByUserId(contactInfoDtoSearchField);

        verify(contactInfoDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateAppUserAddressContactInfoNull() {
        ContactInfoDtoSearchField contactInfoDtoSearchField = ContactInfoDtoSearchField.builder()
                .searchField("123")
                .newEntity(getContactInfoDto())
                .build();
        UserProfile userProfile = mock(UserProfile.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        when(userProfile.getContactInfo()).thenReturn(null);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("user_id", contactInfoDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(UserProfile.class, parameterSupplier))
                .thenReturn(userProfile);
        when(transformationFunctionService.getEntity(ContactInfo.class, contactInfoDtoSearchField.getNewEntity()))
                .thenReturn(contactInfo);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userProfile);
            return null;
        }).when(contactInfoDao).executeConsumer(any(Consumer.class));

        contactInfoService.saveUpdateContactInfoByUserId(contactInfoDtoSearchField);

        verify(contactInfoDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateContactInfoByOrderId() {
        ContactInfoDto[] contactInfoArray = new ContactInfoDto[] {getContactInfoDto()};
        ContactInfoArrayDtoSearchField contactInfoArrayDtoSearchField = ContactInfoArrayDtoSearchField.builder()
                .searchField("123")
                .newEntityArray(contactInfoArray)
                .build();
        OrderItemData orderItemData = mock(OrderItemData.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        when(orderItemData.getBeneficiaries())
                .thenReturn(List.of(contactInfo));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("order_id", contactInfoArrayDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(eq(ContactInfo.class), any(ContactInfoDto.class)))
                .thenReturn(contactInfo);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(orderItemData);
            return null;
        }).when(contactInfoDao).executeConsumer(any(Consumer.class));

        contactInfoService.saveUpdateContactInfoByOrderId(contactInfoArrayDtoSearchField);

        verify(contactInfoDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateContactInfoListByOrderId() {
        ContactInfoDto[] contactInfoArray = new ContactInfoDto[] {getContactInfoDto()};
        ContactInfoArrayDtoSearchField contactInfoArrayDtoSearchField = ContactInfoArrayDtoSearchField.builder()
                .searchField("123")
                .newEntityArray(contactInfoArray)
                .build();
        OrderItemData orderItemData = mock(OrderItemData.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        List<ContactInfo> expectedContactInfoList = new ArrayList<>(){{
            add(contactInfo);
            add(contactInfo);
        }};
        when(orderItemData.getBeneficiaries())
                .thenReturn(expectedContactInfoList);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("order_id", contactInfoArrayDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(eq(ContactInfo.class), any(ContactInfoDto.class)))
                .thenReturn(contactInfo);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(orderItemData);
            return null;
        }).when(contactInfoDao).executeConsumer(any(Consumer.class));

        contactInfoService.saveUpdateContactInfoByOrderId(contactInfoArrayDtoSearchField);

        verify(contactInfoDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteContactInfoByUserId() {
        OneFieldEntityDto dto = new OneFieldEntityDto("123");
        UserProfile userProfile = mock(UserProfile.class);
        ContactInfo contactInfo = getContactInfo();
        when(userProfile.getContactInfo()).thenReturn(contactInfo);
        Supplier<ContactInfo> supplier = () -> contactInfo;

        Function<UserProfile, ContactInfo> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(UserProfile.class, ContactInfo.class))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                UserProfile.class,
                "user_id",
                dto.getValue(),
                function
        )).thenReturn(supplier);

        contactInfoService.deleteContactInfoByUserId(dto);

        verify(contactInfoDao).deleteEntity(any(Supplier.class));
    }

    @Test
    void testDeleteContactInfoByOrderId() {
        ContactInfoSearchFieldOrderNumberDto contactInfoSearchFieldOrderNumberDto = ContactInfoSearchFieldOrderNumberDto.builder()
                .orderNumber(0)
                .value("123")
                .build();
        OrderItemData orderItemData = mock(OrderItemData.class);
        ContactInfo contactInfo = getContactInfo();
        when(orderItemData.getBeneficiaries()).thenReturn(List.of(contactInfo));

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("order_id", contactInfoSearchFieldOrderNumberDto.getValue()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(eq(OrderItemData.class), any(Supplier.class)))
                .thenReturn(orderItemData);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(contactInfo);
            return null;
        }).when(contactInfoDao).executeConsumer(any(Consumer.class));

        contactInfoService.deleteContactInfoByOrderId(contactInfoSearchFieldOrderNumberDto);

        verify(contactInfoDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testGetContactInfoByUserId() {
        String userId = "123";
        UserProfile userProfile = mock(UserProfile.class);
        ContactInfo contactInfo = getContactInfo();
        ContactInfoDto contactInfoDto = getContactInfoDto();
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(userId);
        Parameter parameter = mock(Parameter.class);

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("user_id", userId)).thenReturn(parameterSupplier);

        Function<UserProfile, ContactInfoDto> transformationFunction = user -> contactInfoDto;
        when(transformationFunctionService.getTransformationFunction(UserProfile.class, ContactInfoDto.class))
                .thenReturn(transformationFunction);

        when(queryService.getEntityDto(eq(UserProfile.class), eq(parameterSupplier), eq(transformationFunction)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<UserProfile, ContactInfoDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return functionArg.apply(userProfile);
                });

        when(userProfile.getContactInfo()).thenReturn(contactInfo);

        ContactInfoDto contactInfoResult = contactInfoService.getContactInfoByUserId(oneFieldEntityDto);
        ContactInfoDto expectedContactInfoDto = getContactInfoDto();
        assertEquals(expectedContactInfoDto, contactInfoResult);
    }

    @Test
    void testGetContactInfoListByOrderId() {
        String orderId = "123";
        OrderItemData orderItemData = mock(OrderItemData.class);
        ContactInfo contactInfo = getContactInfo();
        ContactInfoDto contactInfoDto = getContactInfoDto();
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(orderId);
        Parameter parameter = mock(Parameter.class);

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("order_id", orderId))
                .thenReturn(parameterSupplier);

        Function<OrderItemData, ContactInfoDto> transformationFunction = oi -> {
            ContactInfo ci = oi.getBeneficiaries().get(0);
            return ContactInfoDto.builder()
                    .name(ci.getName())
                    .secondName(ci.getSecondName())
                    .contactPhone(ContactPhoneDto.builder()
                            .phoneNumber(ci.getContactPhone().getPhoneNumber())
                            .countryPhoneCode(ci.getContactPhone().getCountryPhoneCode().getCode())
                            .build())
                    .build();
        };
        when(transformationFunctionService.getTransformationFunction(OrderItemData.class, ContactInfoDto.class))
                .thenReturn(transformationFunction);

        when(queryService.getSubEntityDtoList(eq(OrderItemData.class), any(Supplier.class), any(Function.class)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<OrderItemData, ContactInfoDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return List.of(functionArg.apply(orderItemData));
                });

        when(orderItemData.getBeneficiaries()).thenReturn(List.of(contactInfo));

        List<ContactInfoDto> contactInfoDtoList = contactInfoService.getContactInfoListByOrderId(oneFieldEntityDto);
        ContactInfoDto expectedContactInfoDto = getContactInfoDto();
        assertEquals(1, contactInfoDtoList.size());
        contactInfoDtoList.forEach(result ->  {
            assertEquals(expectedContactInfoDto.getContactPhone().getCountryPhoneCode(), result.getContactPhone().getCountryPhoneCode());
            assertEquals(expectedContactInfoDto.getName(), result.getName());
            assertEquals(expectedContactInfoDto.getContactPhone().getPhoneNumber(), result.getContactPhone().getPhoneNumber());
            assertEquals(expectedContactInfoDto.getSecondName(), result.getSecondName());
        });
    }

    private ContactInfoDto getContactInfoDto() {
        return ContactInfoDto.builder()
                .contactPhone(ContactPhoneDto.builder()
                        .countryPhoneCode("USA")
                        .phoneNumber("newPhoneNumber")
                        .build())
                .name("newName")
                .secondName("newSecondName")
                .build();
    }

    private ContactInfo getContactInfo() {
        return ContactInfo.builder()
                .name("newName")
                .orderNumber(0)
                .contactPhone(ContactPhone.builder()
                        .countryPhoneCode(CountryPhoneCode.builder()
                                .code("USA")
                                .build())
                        .phoneNumber("newPhoneNumber")
                        .build())
                .secondName("newSecondName")
                .build();
    }
}