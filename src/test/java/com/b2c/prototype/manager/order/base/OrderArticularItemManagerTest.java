package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.order.IOrderItemDataDao;
import com.b2c.prototype.manager.order.base.OrderItemDataOptionManager;
import com.b2c.prototype.modal.constant.PaymentMethodEnum;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.payload.BeneficiaryDto;
import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;
import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.DeliveryDto;
import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.ItemDataOptionQuantityDto;
import com.b2c.prototype.modal.dto.payload.OrderItemDataDto;
import com.b2c.prototype.modal.dto.payload.PaymentDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.payload.UserProfileDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseOrderItemDataDto;
import com.b2c.prototype.modal.dto.searchfield.OrderItemDataSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.order.Beneficiary;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderArticularItemManagerTest {

    @Mock
    private IOrderItemDataDao orderItemDataDao;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private OrderItemDataOptionManager orderItemDataOptionManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOrderItemData_shouldSaveEntity() {
        OrderItemDataDto dto = getOrderItemDataDto();
        OrderArticularItem entity = getOrderItemData();
        when(transformationFunctionService.getEntity(OrderArticularItem.class, dto))
                .thenReturn(entity);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(entity);
            return null;
        }).when(orderItemDataDao).executeConsumer(any(Consumer.class));

        orderItemDataOptionManager.saveOrderItemData(dto);

        verify(orderItemDataDao).executeConsumer(any(Consumer.class));
        assertNotNull(entity.getOrderId());
    }

    @Test
    void updateOrderItemData_shouldUpdateEntity() {
        OrderItemDataSearchFieldEntityDto dtoUpdate = OrderItemDataSearchFieldEntityDto.builder()
                .searchField("searchField")
                .newEntity(getOrderItemDataDto())
                .build();
        OrderArticularItem existingEntity = getOrderItemData();
        OrderArticularItem newEntity = getOrderItemData();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, dtoUpdate.getSearchField()))
                .thenReturn(parameterSupplier);
        when(orderItemDataDao.getEntity(parameter)).thenReturn(existingEntity);
        when(transformationFunctionService.getEntity(OrderArticularItem.class, dtoUpdate.getNewEntity()))
                .thenReturn(newEntity);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(newEntity);
            return null;
        }).when(orderItemDataDao).executeConsumer(any(Consumer.class));

        orderItemDataOptionManager.updateOrderItemData(dtoUpdate);

        verify(orderItemDataDao).executeConsumer(any(Consumer.class));
        assertEquals(existingEntity.getOrderId(), newEntity.getOrderId());
    }

    @Test
    void deleteOrderItemData_shouldDeleteEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto();
        dto.setValue("test-order-id");

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getValue()))
                .thenReturn(parameterSupplier);

        orderItemDataOptionManager.deleteOrderItemData(dto);

        verify(orderItemDataDao).findEntityAndDelete(parameter);
    }

    @Test
    void getOrderItemData_shouldReturnDto() {
        OneFieldEntityDto dto = new OneFieldEntityDto();
        dto.setValue("test-order-id");
        OrderArticularItem entity = getOrderItemData();
        ResponseOrderItemDataDto responseOrderItemDataDto = getResponseOrderItemDataDto();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        Function<OrderArticularItem, ResponseOrderItemDataDto> function = mock(Function.class);
        when(orderItemDataDao.getEntityGraph(anyString(), eq(parameter)))
                .thenReturn(entity);
        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getValue()))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(OrderArticularItem.class, ResponseOrderItemDataDto.class))
                .thenReturn(function);
        when(function.apply(entity)).thenReturn(responseOrderItemDataDto);

        ResponseOrderItemDataDto result = orderItemDataOptionManager.getResponseOrderItemData(dto);

        assertEquals(responseOrderItemDataDto, result);
    }

    @Test
    void getOrderItemListData_shouldReturnDtoList() {
        ResponseOrderItemDataDto responseOrderItemDataDto = getResponseOrderItemDataDto();
        OrderArticularItem orderItemDataOption = getOrderItemData();
        when(orderItemDataDao.getEntityList()).thenReturn(List.of(orderItemDataOption));
        Function<OrderArticularItem, ResponseOrderItemDataDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OrderArticularItem.class, ResponseOrderItemDataDto.class))
                .thenReturn(function);
        when(function.apply(orderItemDataOption)).thenReturn(responseOrderItemDataDto);
        List<ResponseOrderItemDataDto> list = orderItemDataOptionManager.getResponseOrderItemDataList();

        list.forEach(result -> {
            assertEquals(responseOrderItemDataDto, result);
        });
    }

    private Beneficiary prepareBeneficiary() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .label("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .phoneNumber("111-111-111")
                .countryPhoneCode(countryPhoneCode)
                .build();
        return Beneficiary.builder()
                .id(1L)
                .firstName("Wolter")
                .lastName("White")
                .contactPhone(contactPhone)
                .build();
    }

    private ContactInfo prepareContactInfo() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .label("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .phoneNumber("111-111-111")
                .countryPhoneCode(countryPhoneCode)
                .build();
        return ContactInfo.builder()
                .id(1L)
                .firstName("Wolter")
                .lastName("White")
                .contactPhone(contactPhone)
                .build();
    }

    private Address createAddress() {
        Country country = Country.builder()
                .id(1L)
                .label("USA")
                .value("USA")
                .build();
        return Address.builder()
                .id(1L)
                .country(country)
                .street("street")
                .street2("street2")
                .buildingNumber(1)
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .build();
    }

    private Delivery prepareTestDelivery() {
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .value("Post")
                .build();

        return Delivery.builder()
                .id(1L)
                .address(createAddress())
                .deliveryType(deliveryType)
                .build();
    }

    private Payment prepareTestPayment() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv("818")
                .build();
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .value("Card")
                .build();
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        Discount discount = Discount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
                .currency(currency)
                .build();
        Price price = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();

        return Payment.builder()
                .id(1L)
                .paymentId("1")
                .discount(discount)
                .paymentMethod(paymentMethod)
                .creditCard(creditCard)
                .fullPrice(price)
                .totalPrice(price)
                .build();
    }

    private ArticularItemQuantity prepareTestOrderItemQuantity() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();
        OptionItem optionItem = OptionItem.builder()
                .id(1L)
                .value("L")
//                .optionGroup(optionGroup)
                .build();
//        ItemData itemData = prepareTestItemData();
        ArticularItem articularItem = ArticularItem.builder()
                .optionItems(Set.of(optionItem))
                .articularId("1")
                .build();
        return ArticularItemQuantity.builder()
                .id(1L)
                .articularItem(articularItem)
                .quantity(1)
                .build();
    }

    private OrderStatus prepareTestOrderStatus() {
        return OrderStatus.builder()
                .id(1L)
                .value("Pending")
                .build();
    }

    private CreditCard prepareCard() {
        return CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv("818")
                .build();
    }

    private UserProfile prepareTestUserProfile() {
        CreditCard creditCard = prepareCard();
        Post parent = Post.builder()
                .id(1L)
                .title("parent")
                .uniquePostId("1")
                .message("parent")
                .dateOfCreate(100000)
                .build();

        return UserProfile.builder()
                .id(1L)
                .username("username")
                .email("email")
                .dateOfCreate(100)
                .isActive(true)
                .contactInfo(prepareContactInfo())
                .address(createAddress())
                .creditCardList(List.of(creditCard))
                .build();
    }

    private OrderArticularItem getOrderItemData() {
        return OrderArticularItem.builder()
                .id(1L)
                .orderId("orderId")
                .dateOfCreate(100L)
                .beneficiaries(List.of(prepareBeneficiary()))
                .payment(prepareTestPayment())
                .delivery(prepareTestDelivery())
                .articularItemQuantityList(List.of(prepareTestOrderItemQuantity()))
                .orderStatus(prepareTestOrderStatus())
                .userProfile(prepareTestUserProfile())
                .orderId("100")
                .note("note")
                .build();
    }

    private Currency getCurrency() {
        return Currency.builder()
                .id(1L)
                .value("USD")
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

    private PriceDto getPriceDto(double amount) {
        return PriceDto.builder()
                .amount(amount)
                .currency(getCurrency().getValue())
                .build();
    }

    private DiscountDto getDiscountDto() {
        return DiscountDto.builder()
                .charSequenceCode("CODE123")
                .amount(20)
                .currency("USA")
                .build();
    }

    private CreditCardDto getCreditCardDto() {
        return CreditCardDto.builder()
                .cardNumber("1234-5678-9012-3456")
                .dateOfExpire("06/28")
                .cvv("123")
                .ownerName("John")
                .ownerSecondName("Doe")
                .build();
    }

    private ResponseCreditCardDto getResponseCreditCardDto() {
        return ResponseCreditCardDto.builder()
                .cardNumber("1234-5678-9012-3456")
                .dateOfExpire("06/28")
                .isActive(true)
                .ownerName("John")
                .ownerSecondName("Doe")
                .build();
    }

    private PaymentDto getPaymentDto() {
        return PaymentDto.builder()
                .orderId("123")
                .paymentMethod(PaymentMethodEnum.CARD.getValue())
                .creditCard(getCreditCardDto())
                .fullPrice(getPriceDto(120))
                .totalPrice(getPriceDto(100))
                .discount(getDiscountDto())
                .build();
    }

    private AddressDto getAddressDto() {
        return AddressDto.builder()
                .country("USA")
                .city("city")
                .street("street")
                .street2("street2")
                .buildingNumber(1)
                .florNumber(9)
                .apartmentNumber(101)
                .zipCode("91000")
                .build();
    }

    private DeliveryDto getDeliveryDto() {
        return DeliveryDto.builder()
                .deliveryAddress(getAddressDto())
                .deliveryType("STH")
                .build();
    }

    private UserProfileDto getUserProfileDto() {
        return UserProfileDto.builder()
                .creditCards(List.of(getResponseCreditCardDto()))
                .addressDto(getAddressDto())
                .email("email")
                .contactInfo(getContactInfoDto())
                .build();
    }

    private ItemDataOptionQuantityDto getItemDataOptionQuantityDto() {
        return ItemDataOptionQuantityDto.builder()
                .orderId("orderId")
                .articularId("articularId")
                .quantity(1)
                .build();
    }


    private OrderItemDataDto getOrderItemDataDto() {
        return OrderItemDataDto.builder()
                .beneficiaries(List.of(getBeneficiaryDto()))
                .payment(getPaymentDto())
                .delivery(getDeliveryDto())
                .itemDataOptionQuantities(Set.of(getItemDataOptionQuantityDto()))
                .userProfile(getUserProfileDto())
                .note("note")
                .build();
    }

    private ResponseOrderItemDataDto getResponseOrderItemDataDto() {
        return ResponseOrderItemDataDto.builder()
                .beneficiaries(List.of(getBeneficiaryDto()))
                .payment(getPaymentDto())
                .delivery(getDeliveryDto())
                .itemDataOptionQuantities(Set.of(getItemDataOptionQuantityDto()))
                .userProfile(getUserProfileDto())
                .note("note")
                .build();
    }
}
