package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.manager.userdetails.basic.ContactPhoneManager;
import com.b2c.prototype.modal.dto.payload.BeneficiaryDto;
import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.order.Beneficiary;
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

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ContactPhoneManagerTest {

    @Mock
    private IContactPhoneDao contactPhoneDao;
    @Mock
    private ISearchService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private ContactPhoneManager contactPhoneManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateContactPhoneByUserId() {
        String userId = "code";
        ContactPhoneDto contactPhoneDto = getContactPhoneDto();

        ContactPhone contactPhone = getTestContactPhone();
        Parameter mockParameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        UserDetails userDetails = mock(UserDetails.class);
        ContactInfo contactInfo = mock(ContactInfo.class);

//        when(queryService.getEntity(eq(UserDetails.class), any(Supplier.class)))
//                .thenReturn(userDetails);
        when(transformationFunctionService.getEntity(
                ContactPhone.class,
                contactPhoneDto)).thenReturn(contactPhone);
        when(supplierService.parameterStringSupplier(USER_ID, userId))
                .thenReturn(parameterSupplier);
        when(userDetails.getContactInfo()).thenReturn(contactInfo);
        when(contactInfo.getContactPhone()).thenReturn(contactPhone);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(contactInfo);
            return null;
        }).when(contactPhoneDao).executeConsumer(any(Consumer.class));

        contactPhoneManager.saveUpdateContactPhoneByUserId(userId, contactPhoneDto);

        verify(contactPhoneDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateContactPhoneByOrderId() {
        String orderId = "code";
        BeneficiaryDto beneficiaryDto = getBeneficiaryDto();
        ContactPhone contactPhone = getTestContactPhone();
        Parameter mockParameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Beneficiary beneficiary = mock(Beneficiary.class);
        List<Beneficiary> orderItemList = List.of(beneficiary);

//        when(queryService.getEntity(eq(OrderArticularItem.class), any(Supplier.class)))
//                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(
                ContactPhone.class,
                beneficiaryDto)).thenReturn(contactPhone);
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        when(orderItemDataOption.getBeneficiaries()).thenReturn(orderItemList);
        when(beneficiary.getContactInfo().getContactPhone()).thenReturn(contactPhone);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(beneficiary);
            return null;
        }).when(contactPhoneDao).executeConsumer(any(Consumer.class));

        contactPhoneManager.saveUpdateContactPhoneByOrderId(orderId, beneficiaryDto);

        verify(contactPhoneDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteContactPhoneByUserId() {
        String userId = "123";
        Parameter mockParameter = mock(Parameter.class);
        UserDetails userDetails = mock(UserDetails.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        ContactPhone contactPhone = getTestContactPhone();
        Supplier<ContactPhone> contactPhoneSupplier = () -> contactPhone;

        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        Function<UserDetails, ContactPhone> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(UserDetails.class, ContactPhone.class))
                .thenReturn(function);
//        when(supplierService.entityFieldSupplier(UserDetails.class, parameterSupplier, function))
//                .thenReturn(contactPhoneSupplier);
        when(supplierService.parameterStringSupplier(USER_ID, userId))
                .thenReturn(parameterSupplier);
        when(userDetails.getContactInfo()).thenReturn(contactInfo);
        when(contactInfo.getContactPhone()).thenReturn(getTestContactPhone());

        contactPhoneManager.deleteContactPhoneByUserId(userId);

        verify(contactPhoneDao).deleteEntity(any(Supplier.class));
    }

    @Test
    void testDeleteContactPhoneByOrderId() {
        String orderId = "123";
        Parameter mockParameter = mock(Parameter.class);
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Beneficiary beneficiary = mock(Beneficiary.class);
        ContactPhone contactPhone = getTestContactPhone();
        List<Beneficiary> beneficiaryList = List.of(beneficiary);

        Supplier<Parameter> parameterSupplier = () -> mockParameter;
//        when(queryService.getEntity(eq(OrderArticularItem.class), any(Supplier.class)))
//                .thenReturn(orderItemDataOption);
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        when(orderItemDataOption.getBeneficiaries()).thenReturn(beneficiaryList);
        when(beneficiary.getContactInfo().getContactPhone()).thenReturn(contactPhone);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(contactPhone);
            return null;
        }).when(contactPhoneDao).executeConsumer(any(Consumer.class));

        contactPhoneManager.deleteContactPhoneByOrderId(orderId, 0);

        verify(contactPhoneDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testGetContactPhoneByUserId() {
        String userId = "123";
        ContactPhoneDto contactPhoneDto = createContactPhoneDto();
        Parameter mockParameter = mock(Parameter.class);
        UserDetails userDetails = mock(UserDetails.class);
        ContactInfo contactInfo = mock(ContactInfo.class);
        ContactPhone contactPhone = getTestContactPhone();

        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        Function<UserDetails, ContactPhoneDto> mapFunction = ud -> contactPhoneDto;

        when(supplierService.parameterStringSupplier(USER_ID, userId))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(UserDetails.class, ContactPhoneDto.class))
                .thenReturn(mapFunction);
//        when(queryService.getEntityDto(eq(UserDetails.class), any(Supplier.class), any(Function.class)))
//                .thenAnswer(invocation -> {
//                    Supplier<Parameter> paramSupplier = invocation.getArgument(1);
//                    Function<UserDetails, ContactPhoneDto> mappingFunction = invocation.getArgument(2);
//                    assertEquals(mockParameter, paramSupplier.get());
//                    return mappingFunction.apply(userDetails);
//                });
        when(userDetails.getContactInfo()).thenReturn(contactInfo);
        when(contactInfo.getContactPhone()).thenReturn(contactPhone);

        ContactPhoneDto result = contactPhoneManager.getContactPhoneByUserId(userId);

        assertEquals(contactPhoneDto.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(contactPhoneDto.getCountryPhoneCode(), result.getCountryPhoneCode());
    }

    @Test
    void testGetContactPhoneByOrderId() {
        String orderId = "123";
        ContactPhoneDto contactPhoneDto = createContactPhoneDto();
        Parameter mockParameter = mock(Parameter.class);
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Beneficiary beneficiary = mock(Beneficiary.class);
        ContactPhone contactPhone = getTestContactPhone();

        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        Function<OrderArticularItem, ContactPhoneDto> mapFunction = oai -> contactPhoneDto;

        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(OrderArticularItem.class, ContactPhoneDto.class, "list"))
                .thenReturn(mapFunction);

        when(queryService.getSubNamedQueryEntityDtoList(eq(OrderArticularItem.class), anyString(), any(Parameter.class), any(Function.class)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> paramSupplier = invocation.getArgument(1);
                    Function<OrderArticularItem, ContactPhoneDto> mappingFunction = invocation.getArgument(2);
                    assertEquals(mockParameter, paramSupplier.get());
                    return List.of(mappingFunction.apply(orderItemDataOption));
                });
        when(orderItemDataOption.getBeneficiaries()).thenReturn(List.of(beneficiary));
        when(beneficiary.getContactInfo().getContactPhone()).thenReturn(contactPhone);

        List<ContactPhoneDto> resultList = contactPhoneManager.getContactPhoneByOrderId(orderId);
        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            assertEquals(contactPhoneDto.getPhoneNumber(), result.getPhoneNumber());
            assertEquals(contactPhoneDto.getCountryPhoneCode(), result.getCountryPhoneCode());
        });
    }

    @Test
    void testGetContactPhoneList() {
//        when(contactPhoneDao.getEntityList()).thenReturn(List.of(
//                ContactPhone.builder()
//                        .phoneNumber("1234567890")
//                        .countryPhoneCode(getCountryPhoneCode())
//                        .build(),
//                ContactPhone.builder()
//                        .phoneNumber("0987654321")
//                        .countryPhoneCode(getCountryPhoneCode())
//                        .build()
//        ));

        Function<ContactPhone, ContactPhoneDto> mapFunction = contactPhone -> ContactPhoneDto.builder()
                .phoneNumber(contactPhone.getPhoneNumber())
                .countryPhoneCode(contactPhone.getCountryPhoneCode().getValue())
                .build();
        when(transformationFunctionService.getTransformationFunction(ContactPhone.class, ContactPhoneDto.class))
                .thenReturn(mapFunction);

        List<ContactPhoneDto> resultListDto = contactPhoneManager.getContactPhoneList();

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
                .value("US")
                .build();
    }

    private ContactPhoneDto getContactPhoneDto() {
        return ContactPhoneDto.builder()
                .phoneNumber("48")
                .countryPhoneCode("US")
                .phoneNumber("1234567890")
                .build();
    }

    private BeneficiaryDto getBeneficiaryDto() {
        return BeneficiaryDto.builder()
                .orderNumber(0)
                .contactInfo(ContactInfoDto.builder()
                        .contactPhone(getContactPhoneDto())
                        .email("email@email.com")
                        .firstName("firstName")
                        .lastName("lastName")
                        .build())
                .build();
    }
}
