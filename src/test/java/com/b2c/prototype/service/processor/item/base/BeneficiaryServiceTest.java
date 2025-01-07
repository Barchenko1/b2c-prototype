package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.order.IBeneficiaryDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.searchfield.BeneficiaryArrayDtoSearchField;
import com.b2c.prototype.modal.dto.request.BeneficiaryDto;
import com.b2c.prototype.modal.dto.searchfield.BeneficiarySearchFieldOrderNumberDto;
import com.b2c.prototype.modal.dto.request.ContactPhoneDto;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.order.Beneficiary;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.order.base.BeneficiaryService;
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

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BeneficiaryServiceTest {

    @Mock
    private IBeneficiaryDao beneficiaryDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private BeneficiaryService beneficiaryService;

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
        OrderItemData orderItemData = mock(OrderItemData.class);
        Beneficiary beneficiary = mock(Beneficiary.class);
        when(orderItemData.getBeneficiaries())
                .thenReturn(List.of(beneficiary));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("order_id", beneficiaryArrayDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(eq(Beneficiary.class), any(BeneficiaryDto.class)))
                .thenReturn(beneficiary);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(orderItemData);
            return null;
        }).when(beneficiaryDao).executeConsumer(any(Consumer.class));

        beneficiaryService.saveUpdateContactInfoByOrderId(beneficiaryArrayDtoSearchField);

        verify(beneficiaryDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateContactInfoListByOrderId() {
        BeneficiaryDto[] beneficiaryDtoArray = new BeneficiaryDto[] {getBeneficiaryDto()};
        BeneficiaryArrayDtoSearchField beneficiaryArrayDtoSearchField = BeneficiaryArrayDtoSearchField.builder()
                .searchField("123")
                .newEntityArray(beneficiaryDtoArray)
                .build();
        OrderItemData orderItemData = mock(OrderItemData.class);
        Beneficiary beneficiary = mock(Beneficiary.class);
        List<Beneficiary> expectedBeneficiaryList = new ArrayList<>(){{
            add(beneficiary);
            add(beneficiary);
        }};
        when(orderItemData.getBeneficiaries())
                .thenReturn(expectedBeneficiaryList);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, beneficiaryArrayDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(eq(Beneficiary.class), any(BeneficiaryDto.class)))
                .thenReturn(beneficiary);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(orderItemData);
            return null;
        }).when(beneficiaryDao).executeConsumer(any(Consumer.class));

        beneficiaryService.saveUpdateContactInfoByOrderId(beneficiaryArrayDtoSearchField);

        verify(beneficiaryDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteContactInfoByOrderId() {
        BeneficiarySearchFieldOrderNumberDto beneficiarySearchFieldOrderNumberDto = BeneficiarySearchFieldOrderNumberDto.builder()
                .orderNumber(0)
                .value("123")
                .build();
        OrderItemData orderItemData = mock(OrderItemData.class);
        Beneficiary beneficiary = getBeneficiary();
        when(orderItemData.getBeneficiaries()).thenReturn(List.of(beneficiary));

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, beneficiarySearchFieldOrderNumberDto.getValue()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(eq(OrderItemData.class), any(Supplier.class)))
                .thenReturn(orderItemData);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(beneficiary);
            return null;
        }).when(beneficiaryDao).executeConsumer(any(Consumer.class));

        beneficiaryService.deleteContactInfoByOrderId(beneficiarySearchFieldOrderNumberDto);

        verify(beneficiaryDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testGetContactInfoListByOrderId() {
        String orderId = "123";
        OrderItemData orderItemData = mock(OrderItemData.class);
        Beneficiary beneficiary = getBeneficiary();
//        BeneficiaryDto beneficiaryDto = getBeneficiaryDto();
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(orderId);
        Parameter parameter = mock(Parameter.class);

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);

        Function<OrderItemData, BeneficiaryDto> transformationFunction = oi -> {
            Beneficiary iob = oi.getBeneficiaries().get(0);
            return BeneficiaryDto.builder()
                    .firstName(iob.getFirstName())
                    .lastName(iob.getLastName())
                    .contactPhone(ContactPhoneDto.builder()
                            .phoneNumber(iob.getContactPhone().getPhoneNumber())
                            .countryPhoneCode(iob.getContactPhone().getCountryPhoneCode().getCode())
                            .build())
                    .build();
        };
        when(transformationFunctionService.getTransformationFunction(OrderItemData.class, BeneficiaryDto.class))
                .thenReturn(transformationFunction);

        when(queryService.getSubEntityDtoList(eq(OrderItemData.class), any(Supplier.class), any(Function.class)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<OrderItemData, BeneficiaryDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return List.of(functionArg.apply(orderItemData));
                });

        when(orderItemData.getBeneficiaries()).thenReturn(List.of(beneficiary));

        List<BeneficiaryDto> contactInfoDtoList = beneficiaryService.getContactInfoListByOrderId(oneFieldEntityDto);
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
                                .code("USA")
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