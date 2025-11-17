package com.b2c.prototype.dao.order;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.order.CustomerSingleDeliveryOrder;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.CurrencyCoefficient;
import com.b2c.prototype.modal.entity.payment.MultiCurrencyPriceInfo;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.payment.PaymentStatus;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.util.CardUtil;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.b2c.prototype.util.Converter.getLocalDate;
import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerSingleDeliveryOrderDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    private static final Map<String, Currency> CACHE = new HashMap<>(){{
        put("USD", Currency.builder().id(1L).key("USD").value("USD").build());
        put("EUR", Currency.builder().id(2L).key("EUR").value("EUR").build());
    }};

    @Test
    @DataSet(value = "datasets/dao/order/customer_order/emptyCustomerSingleDeliveryOrderDataSet.yml", cleanBefore = true,
    executeStatementsBefore = {
            "TRUNCATE TABLE price RESTART IDENTITY CASCADE",
    })
    @ExpectedDataSet(value = "datasets/dao/order/customer_order/saveCustomerSingleDeliveryOrderDataSet.yml", orderBy = "id", ignoreCols = {"id", "amount", "currency_id", "original_price_id", "current_price_id", "commission_price_info_id", "full_multi_currency_price_info_id", "total_multi_currency_price_info_id", "discount_multi_currency_price_info_id", "phone_number", "first_name", "last_name", "contact_info_id", "beneficiary_id", "full_price_id", "address_id"})
    public void persistEntity_success() {
        CustomerSingleDeliveryOrder entity = getCustomerSingleDeliveryOrder();
        entity.setId(0);

        entity.getContactInfo().setId(0);
        entity.getContactInfo().getContactPhone().setId(0);
        entity.getBeneficiary().setId(0);
        entity.getBeneficiary().getContactPhone().setId(0);

        entity.getDelivery().setId(0);
        entity.getDelivery().getAddress().setId(0);

        entity.getPayment().setId(0);
        entity.getPayment().getCreditCard().setId(0);

        entity.getPayment().getCommissionPriceInfo().setId(0);
        entity.getPayment().getCommissionPriceInfo().getOriginalPrice().setId(0);
        entity.getPayment().getCommissionPriceInfo().getCurrentPrice().setId(0);

        entity.getPayment().getFullMultiCurrencyPriceInfo().setId(0);
        entity.getPayment().getFullMultiCurrencyPriceInfo().getOriginalPrice().setId(0);
        entity.getPayment().getFullMultiCurrencyPriceInfo().getCurrentPrice().setId(0);

        entity.getPayment().getDiscountMultiCurrencyPriceInfo().setId(0);
        entity.getPayment().getDiscountMultiCurrencyPriceInfo().getOriginalPrice().setId(0);
        entity.getPayment().getDiscountMultiCurrencyPriceInfo().getCurrentPrice().setId(0);

        entity.getPayment().getTotalMultiCurrencyPriceInfo().setId(0);
        entity.getPayment().getTotalMultiCurrencyPriceInfo().getOriginalPrice().setId(0);
        entity.getPayment().getTotalMultiCurrencyPriceInfo().getCurrentPrice().setId(0);

        entity.getArticularItemQuantities().forEach(articularItemQuantity -> {
            articularItemQuantity.setId(0);
        });

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/order/customer_order/testCustomerSingleDeliveryOrderDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/order/customer_order/updateCustomerSingleDeliveryOrderDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        CustomerSingleDeliveryOrder entity = getCustomerSingleDeliveryOrder();

        OrderStatus orderStatus = OrderStatus.builder()
                .id(2L)
                .key("Done")
                .value("Done")
                .build();
        entity.setStatus(orderStatus);

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/order/customer_order/testCustomerSingleDeliveryOrderDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/order/customer_order/emptyCustomerSingleDeliveryOrderDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        CustomerSingleDeliveryOrder entity = getCustomerSingleDeliveryOrder();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/order/customer_order/testCustomerSingleDeliveryOrderDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        CustomerSingleDeliveryOrder expected = getCustomerSingleDeliveryOrder();

        Pair<String, Long> pair = Pair.of("id", 1L);
        CustomerSingleDeliveryOrder entity = generalEntityDao.findEntity("CustomerSingleDeliveryOrder.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/order/customer_order/testCustomerSingleDeliveryOrderDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        CustomerSingleDeliveryOrder expected = getCustomerSingleDeliveryOrder();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<CustomerSingleDeliveryOrder> optionEntity = generalEntityDao.findOptionEntity("CustomerSingleDeliveryOrder.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        CustomerSingleDeliveryOrder entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/order/customer_order/testCustomerSingleDeliveryOrderDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        CustomerSingleDeliveryOrder entity = getCustomerSingleDeliveryOrder();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<CustomerSingleDeliveryOrder> entityList = generalEntityDao.findEntityList("CustomerSingleDeliveryOrder.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private ContactInfo getContactInfo() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .key("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("111-111-111")
                .build();
        return ContactInfo.builder()
                .id(1L)
                .firstName("Wolter")
                .lastName("White")
                .email("email")
//                .birthdayDate(getDate("2024-03-03"))
                .contactPhone(contactPhone)
                .isEmailVerified(false)
                .isContactPhoneVerified(false)
                .build();
    }

    private ContactInfo getBeneficiaryInfo() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .key("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(2L)
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("111-111-222")
                .build();
        return ContactInfo.builder()
                .id(2L)
                .firstName("Wolter2")
                .lastName("White2")
                .email("email")
//                .birthdayDate(getDate("2024-03-03"))
                .contactPhone(contactPhone)
                .isEmailVerified(false)
                .isContactPhoneVerified(false)
                .build();
    }

    private OrderStatus getOrderStatus() {
        return OrderStatus.builder()
                .id(1L)
                .value("Pending")
                .key("Pending")
                .build();
    }

    private Delivery getDelivery() {
        Country country = Country.builder()
                .id(1L)
                .key("USA")
                .value("USA")
                .build();
        Address address = Address.builder()
                .id(1L)
                .country(country)
                .street("street")
                .buildingNumber("1")
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .build();
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .value("Type")
                .key("Type")
                .build();

        return Delivery.builder()
                .id(1L)
                .address(address)
                .deliveryType(deliveryType)
                .build();
    }

    private ArticularItem getArticularItem() {
        Currency currency = CACHE.get("USD");
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .key("Size")
                .build();
        OptionItem optionItemL = OptionItem.builder()
                .id(1L)
                .value("L")
                .key("L")
                .build();
        OptionItem optionItemM = OptionItem.builder()
                .id(2L)
                .value("M")
                .key("M")
                .build();

        optionGroup.addOptionItem(optionItemL);
        optionGroup.addOptionItem(optionItemM);
        Price price1 = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();
        Price price2 = Price.builder()
                .id(2L)
                .amount(20)
                .currency(currency)
                .build();
        Discount discount = Discount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
                .currency(currency)
                .build();
        ArticularStatus articularStatus = ArticularStatus.builder()
                .id(1L)
                .key("NEW")
                .value("NEW")
                .build();

        return ArticularItem.builder()
                .id(1L)
                .articularUniqId("123")
                .productName("Mob 1")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .optionItems(Set.of(optionItemL, optionItemM))
                .status(articularStatus)
                .fullPrice(price1)
                .discount(discount)
                .totalPrice(price2)
                .build();
    }

    private ArticularItemQuantity getArticularItemQuantity() {
        return ArticularItemQuantity.builder()
                .id(1L)
                .articularItem(getArticularItem())
                .quantity(1)
                .build();
    }

    private Payment getPayment() {
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
                .key("Card")
                .build();
        Currency currencyUsd = CACHE.get("USD");
        Currency currencyEur = CACHE.get("EUR");
        Discount discount = Discount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
                .currency(currencyUsd)
                .build();

        Price price1 = Price.builder()
                .id(3L)
                .amount(100)
                .currency(currencyUsd)
                .build();
        Price price2 = Price.builder()
                .id(4L)
                .amount(95)
                .currency(currencyEur)
                .build();
        Price price3 = Price.builder()
                .id(5L)
                .amount(85)
                .currency(currencyUsd)
                .build();
        Price price4 = Price.builder()
                .id(6L)
                .amount(80.75)
                .currency(currencyEur)
                .build();
        Price price5 = Price.builder()
                .id(7L)
                .amount(10)
                .currency(currencyUsd)
                .build();
        Price price6 = Price.builder()
                .id(8L)
                .amount(9.5)
                .currency(currencyEur)
                .build();
        Price price7 = Price.builder()
                .id(9L)
                .amount(5)
                .currency(currencyUsd)
                .build();
        Price price8 = Price.builder()
                .id(10L)
                .amount(4.75)
                .currency(currencyEur)
                .build();

        CurrencyCoefficient currencyCoefficient = CurrencyCoefficient.builder()
                .id(1L)
                .currencyFrom(currencyUsd)
                .coefficient(0.95)
                .currencyTo(currencyEur)
                .dateOfCreate(getLocalDate("2024-03-03"))
                .build();

        MultiCurrencyPriceInfo fullMultiCurrencyPriceInfo = MultiCurrencyPriceInfo.builder()
                .id(1L)
                .originalPrice(price1)
                .currencyCoefficient(currencyCoefficient)
                .currentPrice(price2)
                .build();
        MultiCurrencyPriceInfo totalMultiCurrencyPriceInfo = MultiCurrencyPriceInfo.builder()
                .id(2L)
                .originalPrice(price3)
                .currencyCoefficient(currencyCoefficient)
                .currentPrice(price4)
                .build();
        MultiCurrencyPriceInfo discountMultiCurrencyPriceInfo = MultiCurrencyPriceInfo.builder()
                .id(3L)
                .originalPrice(price5)
                .currencyCoefficient(currencyCoefficient)
                .currentPrice(price6)
                .build();
        MultiCurrencyPriceInfo commissionPriceInfo = MultiCurrencyPriceInfo.builder()
                .id(4L)
                .originalPrice(price7)
                .currencyCoefficient(currencyCoefficient)
                .currentPrice(price8)
                .build();

        PaymentStatus paymentStatus = PaymentStatus.builder()
                .id(1L)
                .key("Done")
                .value("Done")
                .build();

        return Payment.builder()
                .id(1L)
                .paymentUniqId("123")
                .paymentTime(getLocalDateTime("2024-03-03 12:00:00"))
                .discount(discount)
                .paymentMethod(paymentMethod)
                .creditCard(creditCard)
                .paymentStatus(paymentStatus)
                .fullMultiCurrencyPriceInfo(fullMultiCurrencyPriceInfo)
                .totalMultiCurrencyPriceInfo(totalMultiCurrencyPriceInfo)
                .discountMultiCurrencyPriceInfo(discountMultiCurrencyPriceInfo)
                .commissionPriceInfo(commissionPriceInfo)
                .build();
    }

    private CustomerSingleDeliveryOrder getCustomerSingleDeliveryOrder() {
        
        return CustomerSingleDeliveryOrder.builder()
                .id(1L)
                .orderUniqId("123")
                .contactInfo(getContactInfo())
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .beneficiary(getBeneficiaryInfo())
                .status(getOrderStatus())
                .delivery(getDelivery())
//                .articularItemQuantities(List.of(getArticularItemQuantity()))
                .payment(getPayment())
                .note("note")

                .original(null)
                .build();
    }

}
