package com.b2c.prototype.manager.userprofile.basic;

import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ContactInfoManagerTest {
    @Mock
    private IContactInfoDao contactInfoDao;
    @Mock
    private ISearchService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private ContactInfoManager contactInfoManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateAppUserAddress() {
        String userId = "123";
        ContactInfoDto contactInfoDto = getContactInfoDto();
        UserDetails userProfile = mock(UserDetails.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        when(userProfile.getContactInfo()).thenReturn(contactInfo);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(USER_ID, userId))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(UserDetails.class, parameterSupplier))
                .thenReturn(userProfile);
        when(transformationFunctionService.getEntity(ContactInfo.class, contactInfoDto))
                .thenReturn(contactInfo);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userProfile);
            return null;
        }).when(contactInfoDao).executeConsumer(any(Consumer.class));

        contactInfoManager.saveUpdateContactInfoByUserId(userId, contactInfoDto);

        verify(contactInfoDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateAppUserAddressContactInfoNull() {
        String userId = "123";
        ContactInfoDto contactInfoDto = getContactInfoDto();
        UserDetails userProfile = mock(UserDetails.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        when(userProfile.getContactInfo()).thenReturn(null);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(USER_ID, userId))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(UserDetails.class, parameterSupplier))
                .thenReturn(userProfile);
        when(transformationFunctionService.getEntity(ContactInfo.class, contactInfoDto))
                .thenReturn(contactInfo);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userProfile);
            return null;
        }).when(contactInfoDao).executeConsumer(any(Consumer.class));

        contactInfoManager.saveUpdateContactInfoByUserId(userId, contactInfoDto);

        verify(contactInfoDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteContactInfoByUserId() {
        String userId = "123";
        UserDetails userProfile = mock(UserDetails.class);
        ContactInfo contactInfo = getContactInfo();
        when(userProfile.getContactInfo()).thenReturn(contactInfo);
        Supplier<ContactInfo> supplier = () -> contactInfo;

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(USER_ID, userId))
                .thenReturn(parameterSupplier);

        Function<UserDetails, ContactInfo> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(UserDetails.class, ContactInfo.class))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                UserDetails.class,
                parameterSupplier,
                function
        )).thenReturn(supplier);

        contactInfoManager.deleteContactInfoByUserId(userId);

        verify(contactInfoDao).deleteEntity(any(Supplier.class));
    }

    @Test
    void testGetContactInfoByUserId() {
        String userId = "123";
        UserDetails userProfile = mock(UserDetails.class);
        ContactInfo contactInfo = getContactInfo();
        ContactInfoDto contactInfoDto = getContactInfoDto();
        Parameter parameter = mock(Parameter.class);

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(USER_ID, userId)).thenReturn(parameterSupplier);

        Function<UserDetails, ContactInfoDto> transformationFunction = user -> contactInfoDto;
        when(transformationFunctionService.getTransformationFunction(UserDetails.class, ContactInfoDto.class))
                .thenReturn(transformationFunction);

        when(queryService.getEntityDto(eq(UserDetails.class), eq(parameterSupplier), eq(transformationFunction)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<UserDetails, ContactInfoDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return functionArg.apply(userProfile);
                });

        when(userProfile.getContactInfo()).thenReturn(contactInfo);

        ContactInfoDto contactInfoResult = contactInfoManager.getContactInfoByUserId(userId);
        ContactInfoDto expectedContactInfoDto = getContactInfoDto();
        assertEquals(expectedContactInfoDto, contactInfoResult);
    }

    private ContactInfoDto getContactInfoDto() {
        return ContactInfoDto.builder()
                .contactPhone(ContactPhoneDto.builder()
                        .countryPhoneCode("USA")
                        .phoneNumber("newPhoneNumber")
                        .build())
                .firstName("newName")
                .lastName("newLastName")
                .build();
    }

    private ContactInfo getContactInfo() {
        return ContactInfo.builder()
                .firstName("newName")
                .lastName("newLastName")
                .contactPhone(ContactPhone.builder()
                        .countryPhoneCode(CountryPhoneCode.builder()
                                .value("USA")
                                .build())
                        .phoneNumber("newPhoneNumber")
                        .build())
                .build();
    }
}