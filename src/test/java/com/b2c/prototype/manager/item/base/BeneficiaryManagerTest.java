package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.order.IBeneficiaryDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.searchfield.BeneficiaryArrayDtoSearchField;
import com.b2c.prototype.modal.dto.payload.BeneficiaryDto;
import com.b2c.prototype.modal.dto.searchfield.BeneficiarySearchFieldOrderNumberDto;
import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.order.Beneficiary;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.order.base.BeneficiaryManager;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.parameter.Parameter;
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

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BeneficiaryManagerTest {

    @Mock
    private IBeneficiaryDao beneficiaryDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private BeneficiaryManager beneficiaryManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateContactInfoByOrderId() {
        BeneficiaryDto[] beneficiaryDtoArray = new BeneficiaryDto[] {getBeneficiaryDto()};
        BeneficiaryArrayDtoSearchField beneficiaryArrayDtoSearchField = BeneficiaryArrayDtoSearchField.builder()
                .searchField("123")
                .newEntityArray(beneficiaryDtoArray)
                .build();
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Beneficiary beneficiary = mock(Beneficiary.class);
        when(orderItemDataOption.getBeneficiaries())
                .thenReturn(List.of(beneficiary));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("order_id", beneficiaryArrayDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier))
                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(eq(Beneficiary.class), any(BeneficiaryDto.class)))
                .thenReturn(beneficiary);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(orderItemDataOption);
            return null;
        }).when(beneficiaryDao).executeConsumer(any(Consumer.class));

        beneficiaryManager.saveUpdateContactInfoByOrderId(beneficiaryArrayDtoSearchField);

        verify(beneficiaryDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateContactInfoListByOrderId() {
        BeneficiaryDto[] beneficiaryDtoArray = new BeneficiaryDto[] {getBeneficiaryDto()};
        BeneficiaryArrayDtoSearchField beneficiaryArrayDtoSearchField = BeneficiaryArrayDtoSearchField.builder()
                .searchField("123")
                .newEntityArray(beneficiaryDtoArray)
                .build();
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Beneficiary beneficiary = mock(Beneficiary.class);
        List<Beneficiary> expectedBeneficiaryList = new ArrayList<>(){{
            add(beneficiary);
            add(beneficiary);
        }};
        when(orderItemDataOption.getBeneficiaries())
                .thenReturn(expectedBeneficiaryList);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, beneficiaryArrayDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier))
                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(eq(Beneficiary.class), any(BeneficiaryDto.class)))
                .thenReturn(beneficiary);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(orderItemDataOption);
            return null;
        }).when(beneficiaryDao).executeConsumer(any(Consumer.class));

        beneficiaryManager.saveUpdateContactInfoByOrderId(beneficiaryArrayDtoSearchField);

        verify(beneficiaryDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteContactInfoByOrderId() {
        BeneficiarySearchFieldOrderNumberDto beneficiarySearchFieldOrderNumberDto = BeneficiarySearchFieldOrderNumberDto.builder()
                .orderNumber(0)
                .value("123")
                .build();
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Beneficiary beneficiary = getBeneficiary();
        when(orderItemDataOption.getBeneficiaries()).thenReturn(List.of(beneficiary));

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, beneficiarySearchFieldOrderNumberDto.getValue()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(eq(OrderArticularItem.class), any(Supplier.class)))
                .thenReturn(orderItemDataOption);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(beneficiary);
            return null;
        }).when(beneficiaryDao).executeConsumer(any(Consumer.class));

        beneficiaryManager.deleteContactInfoByOrderId(beneficiarySearchFieldOrderNumberDto);

        verify(beneficiaryDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testGetContactInfoListByOrderId() {
        String orderId = "123";
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Beneficiary beneficiary = getBeneficiary();
//        BeneficiaryDto beneficiaryDto = getBeneficiaryDto();
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(orderId);
        Parameter parameter = mock(Parameter.class);

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);

        Function<OrderArticularItem, BeneficiaryDto> transformationFunction = oi -> {
            Beneficiary iob = oi.getBeneficiaries().get(0);
            return BeneficiaryDto.builder()
                    .firstName(iob.getFirstName())
                    .lastName(iob.getLastName())
                    .contactPhone(ContactPhoneDto.builder()
                            .phoneNumber(iob.getContactPhone().getPhoneNumber())
                            .countryPhoneCode(iob.getContactPhone().getCountryPhoneCode().getValue())
                            .build())
                    .build();
        };
        when(transformationFunctionService.getTransformationFunction(OrderArticularItem.class, BeneficiaryDto.class))
                .thenReturn(transformationFunction);

        when(queryService.getSubEntityDtoList(eq(OrderArticularItem.class), any(Supplier.class), any(Function.class)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<OrderArticularItem, BeneficiaryDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return List.of(functionArg.apply(orderItemDataOption));
                });

        when(orderItemDataOption.getBeneficiaries()).thenReturn(List.of(beneficiary));

        List<BeneficiaryDto> contactInfoDtoList = beneficiaryManager.getContactInfoListByOrderId(oneFieldEntityDto);
        BeneficiaryDto beneficiaryDto = getBeneficiaryDto();
        assertEquals(1, contactInfoDtoList.size());
        contactInfoDtoList.forEach(result ->  {
            assertEquals(beneficiaryDto.getContactPhone().getCountryPhoneCode(), result.getContactPhone().getCountryPhoneCode());
            assertEquals(beneficiaryDto.getFirstName(), result.getFirstName());
            assertEquals(beneficiaryDto.getContactPhone().getPhoneNumber(), result.getContactPhone().getPhoneNumber());
            assertEquals(beneficiaryDto.getLastName(), result.getLastName());
        });
    }

    private Beneficiary getBeneficiary() {
        return Beneficiary.builder()
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

    private BeneficiaryDto getBeneficiaryDto() {
        return BeneficiaryDto.builder()
                .contactPhone(ContactPhoneDto.builder()
                        .countryPhoneCode("USA")
                        .phoneNumber("newPhoneNumber")
                        .build())
                .firstName("newName")
                .lastName("newLastName")
                .build();
    }
}