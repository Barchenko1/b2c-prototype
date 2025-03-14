package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.order.IOrderItemDataDao;
import com.b2c.prototype.modal.constant.PaymentMethodEnum;
import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;
import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.DeliveryDto;
import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.ArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.payload.OrderArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.payload.PaymentDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.payload.UserDetailsDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseOrderDetails;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantityPrice;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.finder.parameter.Parameter;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeliveryArticularItemQuantityPriceManagerTest {

    @Mock
    private IOrderItemDataDao orderItemDataDao;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private OrderArticularItemQuantityManager orderArticularItemQuantityManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOrderItemData_shouldSaveEntityArticular() {
        OrderArticularItemQuantityDto dto = getOrderArticularItemQuantityDto();
        DeliveryArticularItemQuantity entity = getOrderItemData();
        when(transformationFunctionService.getEntity(DeliveryArticularItemQuantity.class, dto))
                .thenReturn(entity);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(entity);
            return null;
        }).when(orderItemDataDao).executeConsumer(any(Consumer.class));

        orderArticularItemQuantityManager.saveOrderArticularItemQuantity(dto);

        verify(orderItemDataDao).executeConsumer(any(Consumer.class));
//        assertNotNull(entity.getOrderId());
    }

    @Test
    void updateOrderItemData_shouldUpdateEntityArticular() {
        String orderId = "orderId";
        OrderArticularItemQuantityDto orderArticularItemQuantityDto = getOrderArticularItemQuantityDto();
        DeliveryArticularItemQuantity existingEntity = getOrderItemData();
        DeliveryArticularItemQuantity newEntity = getOrderItemData();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        when(orderItemDataDao.getNamedQueryEntity("", parameter)).thenReturn(existingEntity);
        when(transformationFunctionService.getEntity(DeliveryArticularItemQuantity.class, orderArticularItemQuantityDto))
                .thenReturn(newEntity);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(newEntity);
            return null;
        }).when(orderItemDataDao).executeConsumer(any(Consumer.class));

        orderArticularItemQuantityManager.updateOrderArticularItemQuantity(orderId, orderArticularItemQuantityDto);

        verify(orderItemDataDao).executeConsumer(any(Consumer.class));
//        assertEquals(existingEntity.getOrderId(), newEntity.getOrderId());
    }

    @Test
    void deleteOrderItemData_shouldDeleteEntity() {
        String orderId = "test-order-id";

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);

        orderArticularItemQuantityManager.deleteOrder(orderId);

        verify(orderItemDataDao).findEntityAndDelete(parameter);
    }

    @Test
    void getOrderItemData_shouldReturnDto() {
        String orderId = "test-order-id";
        DeliveryArticularItemQuantity entity = getOrderItemData();
        ResponseOrderDetails responseOrderDetails = getResponseOrderDetailsDto();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        Function<DeliveryArticularItemQuantity, ResponseOrderDetails> function = mock(Function.class);
//        when(orderItemDataDao.getGraphEntity(anyString(), eq(parameter)))
//                .thenReturn(entity);
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ResponseOrderDetails.class))
                .thenReturn(function);
        when(function.apply(entity)).thenReturn(responseOrderDetails);

        ResponseOrderDetails result = orderArticularItemQuantityManager.getResponseOrderDetails(orderId);

        assertEquals(responseOrderDetails, result);
    }

    @Test
    void getOrderItemListData_shouldReturnDtoList() {
        ResponseOrderDetails responseOrderDetails = getResponseOrderDetailsDto();
        DeliveryArticularItemQuantity orderItemDataOption = getOrderItemData();
//        when(orderItemDataDao.getEntityList()).thenReturn(List.of(orderItemDataOption));
        Function<DeliveryArticularItemQuantity, ResponseOrderDetails> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ResponseOrderDetails.class))
                .thenReturn(function);
        when(function.apply(orderItemDataOption)).thenReturn(responseOrderDetails);
        List<ResponseOrderDetails> list = orderArticularItemQuantityManager.getResponseOrderDetailsList();

        list.forEach(result -> {
            assertEquals(responseOrderDetails, result);
        });
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
                .buildingNumber("1")
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
                .monthOfExpire(6)
                .yearOfExpire(28)
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive(6, 28))
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
                .commissionPrice(price)
                .totalPrice(price)
                .build();
    }

    private ArticularItemQuantityPrice prepareTestOrderItemQuantity() {
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
        return ArticularItemQuantityPrice.builder()
                .id(1L)
//                .articularItem(articularItem)
//                .quantity(1)
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
                .monthOfExpire(6)
                .yearOfExpire(28)
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive(6, 28))
                .cvv("818")
                .build();
    }

    private UserDetails prepareTestUserDetails() {
        CreditCard creditCard = prepareCard();
        UserCreditCard userCreditCard = UserCreditCard.builder()
                .creditCard(creditCard)
                .isDefault(false)
                .build();
        Post parent = Post.builder()
                .id(1L)
                .title("parent")
                .uniquePostId("1")
                .message("parent")
                .dateOfCreate(100000)
                .build();

        return UserDetails.builder()
                .id(1L)
                .username("username")
                .dateOfCreate(100)
                .isActive(true)
                .contactInfo(prepareContactInfo())
//                .addresses(Set.of(createAddress()))
                .userCreditCards(Set.of(userCreditCard))
                .build();
    }

    private DeliveryArticularItemQuantity getOrderItemData() {
        return DeliveryArticularItemQuantity.builder()
                .id(1L)
//                .orderId("orderId")
//                .dateOfCreate(100L)
//                .beneficiaries(List.of(prepareBeneficiary()))
//                .payment(prepareTestPayment())
                .delivery(prepareTestDelivery())
//                .articularItemQuantityPriceList(List.of(prepareTestOrderItemQuantity()))
//                .orderStatus(prepareTestOrderStatus())
//                .userDetails(prepareTestUserDetails())
//                .orderId("100")
//                .note("note")
                .build();
    }

    private Currency getCurrency() {
        return Currency.builder()
                .id(1L)
                .value("USD")
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
                .monthOfExpire(6)
                .yearOfExpire(29)
                .cvv("123")
                .ownerName("John")
                .ownerSecondName("Doe")
                .build();
    }

    private ResponseCreditCardDto getResponseCreditCardDto() {
        return ResponseCreditCardDto.builder()
                .cardNumber("1234-5678-9012-3456")
                .monthOfExpire(6)
                .yearOfExpire(28)
                .isActive(true)
                .ownerName("John")
                .ownerSecondName("Doe")
                .build();
    }

    private PaymentDto getPaymentDto() {
        return PaymentDto.builder()
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
                .buildingNumber("1")
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

    private UserDetailsDto getUserDetailsDto() {
        return UserDetailsDto.builder()
                .creditCard(getCreditCardDto())
                .address(getAddressDto())
                .contactInfo(getContactInfoDto())
                .build();
    }

    private ArticularItemQuantityDto getItemDataOptionQuantityDto() {
        return ArticularItemQuantityDto.builder()
                .articularId("articularId")
                .quantity(1)
                .build();
    }


    private OrderArticularItemQuantityDto getOrderArticularItemQuantityDto() {
        return OrderArticularItemQuantityDto.builder()
                .beneficiary(getContactInfoDto())
                .payment(getPaymentDto())
                .delivery(getDeliveryDto())
                .itemDataOptionQuantities(List.of(getItemDataOptionQuantityDto()))
                .user(null)
                .note("note")
                .build();
    }

    private ResponseOrderDetails getResponseOrderDetailsDto() {
        return ResponseOrderDetails.builder()
                .beneficiary(getContactInfoDto())
                .payment(getPaymentDto())
                .delivery(getDeliveryDto())
                .itemDataOptionQuantities(Set.of(getItemDataOptionQuantityDto()))
                .userDetails(getUserDetailsDto())
                .note("note")
                .build();
    }
}
