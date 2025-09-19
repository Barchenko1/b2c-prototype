package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.modal.constant.PaymentMethodEnum;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.order.ContactPhoneDto;
import com.b2c.prototype.modal.dto.payload.order.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.order.single.DeliveryDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.order.ArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.payload.order.single.CustomerSingleDeliveryOrderDto;
import com.b2c.prototype.modal.dto.payload.order.PaymentDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.util.CardUtil;
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

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeliveryArticularItemQuantityManagerTest {

    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private CustomerSingleDeliveryOrderManager orderArticularItemQuantityManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOrderItemData_shouldSaveEntityArticular() {
        CustomerSingleDeliveryOrderDto dto = getOrderArticularItemQuantityDto();
        DeliveryArticularItemQuantity entity = getOrderItemData();
        when(transformationFunctionService.getEntity(DeliveryArticularItemQuantity.class, dto))
                .thenReturn(entity);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(entity);
            return null;
        });
//                .when(orderItemDataDao).executeConsumer(any(Consumer.class));

        orderArticularItemQuantityManager.saveCustomerOrder(dto);

//        verify(orderItemDataDao).executeConsumer(any(Consumer.class));
//        assertNotNull(entity.getOrderId());
    }

    @Test
    void updateOrderItemData_shouldUpdateEntityArticular() {
        String orderId = "orderId";
        CustomerSingleDeliveryOrderDto customerSingleDeliveryOrderDto = getOrderArticularItemQuantityDto();
        DeliveryArticularItemQuantity existingEntity = getOrderItemData();
        DeliveryArticularItemQuantity newEntity = getOrderItemData();


        
//        when(orderItemDataDao.getNamedQueryEntity("", parameter)).thenReturn(existingEntity);
        when(transformationFunctionService.getEntity(DeliveryArticularItemQuantity.class, customerSingleDeliveryOrderDto))
                .thenReturn(newEntity);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(newEntity);
            return null;
        });
//                .when(orderItemDataDao).executeConsumer(any(Consumer.class));

        orderArticularItemQuantityManager.updateCustomerOrder(orderId, customerSingleDeliveryOrderDto);

//        verify(orderItemDataDao).executeConsumer(any(Consumer.class));
//        assertEquals(existingEntity.getOrderId(), newEntity.getOrderId());
    }

    @Test
    void getOrderItemData_shouldReturnDto() {
        String orderId = "test-order-id";
        DeliveryArticularItemQuantity entity = getOrderItemData();
        ResponseCustomerOrderDetails responseCustomerOrderDetails = getResponseOrderDetailsDto();


        Function<DeliveryArticularItemQuantity, ResponseCustomerOrderDetails> function = mock(Function.class);
//        when(orderItemDataDao.getGraphEntity(anyString(), eq(parameter)))
//                .thenReturn(entity);
        
        when(transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ResponseCustomerOrderDetails.class))
                .thenReturn(function);
        when(function.apply(entity)).thenReturn(responseCustomerOrderDetails);

        ResponseCustomerOrderDetails result = orderArticularItemQuantityManager.getResponseCustomerOrderDetails(orderId);

        assertEquals(responseCustomerOrderDetails, result);
    }

    @Test
    void getOrderItemListData_shouldReturnDtoList() {
        ResponseCustomerOrderDetails responseCustomerOrderDetails = getResponseOrderDetailsDto();
        DeliveryArticularItemQuantity orderItemDataOption = getOrderItemData();
//        when(orderItemDataDao.getEntityList()).thenReturn(List.of(orderItemDataOption));
        Function<DeliveryArticularItemQuantity, ResponseCustomerOrderDetails> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ResponseCustomerOrderDetails.class))
                .thenReturn(function);
        when(function.apply(orderItemDataOption)).thenReturn(responseCustomerOrderDetails);
        List<ResponseCustomerOrderDetails> list = orderArticularItemQuantityManager.getResponseCustomerOrderDetailsList();

        list.forEach(result -> {
            assertEquals(responseCustomerOrderDetails, result);
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
                .paymentUniqId("1")
                .discount(discount)
                .paymentMethod(paymentMethod)
                .creditCard(creditCard)
//                .commissionPrice(price)
//                .totalPrice(price)
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
//        MetaData itemData = prepareTestItemData();
        ArticularItem articularItem = ArticularItem.builder()
                .optionItems(Set.of(optionItem))
                .articularUniqId("1")
                .build();
        return ArticularItemQuantity.builder()
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

        return UserDetails.builder()
                .id(1L)
                .username("username")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
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
                .discountCharSequenceCode("")
                .build();
    }

    private AddressDto getAddressDto() {
        return AddressDto.builder()
//                .country("USA")
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

    private ArticularItemQuantityDto getArticularItemQuantityDto() {
        return ArticularItemQuantityDto.builder()
                .articularId("articularId")
                .quantity(1)
                .build();
    }


    private CustomerSingleDeliveryOrderDto getOrderArticularItemQuantityDto() {
        return CustomerSingleDeliveryOrderDto.builder()
                .beneficiary(getContactInfoDto())
                .payment(getPaymentDto())
                .delivery(getDeliveryDto())
                .articularItemQuantityList(List.of(getArticularItemQuantityDto()))
                .user(null)
                .note("note")
                .build();
    }

    private ResponseCustomerOrderDetails getResponseOrderDetailsDto() {
        return ResponseCustomerOrderDetails.builder()
                .beneficiary(getContactInfoDto())
                .payment(getPaymentDto())
                .delivery(getDeliveryDto())
                .itemDataOptionQuantities(Set.of(getArticularItemQuantityDto()))
                .userDetails(getUserDetailsDto())
                .note("note")
                .build();
    }
}
