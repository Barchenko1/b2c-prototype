package com.b2c.prototype.dao.payment;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.CurrencyCoefficient;
import com.b2c.prototype.modal.entity.payment.MultiCurrencyPriceInfo;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.payment.PaymentStatus;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.util.CardUtil;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Converter.getLocalDate;
import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/payment/payment/emptyPaymentDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE contact_phone RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE credit_card RESTART IDENTITY CASCADE"
            })
    @ExpectedDataSet(value = "datasets/dao/payment/payment/savePaymentDataSet.yml", orderBy = "id", ignoreCols = {"id", "amount", "currency_id", "current_price_id", "original_price_id", "commission_price_info_id", "discount_multi_currency_price_info_id", "full_multi_currency_price_info_id", "total_multi_currency_price_info_id"})
    public void persistEntity_success() {
        Payment entity = getPayment();
        entity.setId(0);
        entity.getCreditCard().setId(0);
        entity.getFullMultiCurrencyPriceInfo().setId(0);
        entity.getDiscountMultiCurrencyPriceInfo().setId(0);
        entity.getTotalMultiCurrencyPriceInfo().setId(0);
        entity.getCommissionPriceInfo().setId(0);

        entity.getFullMultiCurrencyPriceInfo().getOriginalPrice().setId(0);
        entity.getFullMultiCurrencyPriceInfo().getCurrentPrice().setId(0);
        entity.getDiscountMultiCurrencyPriceInfo().getOriginalPrice().setId(0);
        entity.getDiscountMultiCurrencyPriceInfo().getCurrentPrice().setId(0);
        entity.getTotalMultiCurrencyPriceInfo().getOriginalPrice().setId(0);
        entity.getTotalMultiCurrencyPriceInfo().getCurrentPrice().setId(0);
        entity.getCommissionPriceInfo().getOriginalPrice().setId(0);
        entity.getCommissionPriceInfo().getCurrentPrice().setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/payment/payment/testPaymentDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/payment/payment/updatePaymentDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Payment entity = getPayment();
        PaymentStatus paymentStatus = PaymentStatus.builder()
                .id(2L)
                .key("Approved")
                .value("Approved")
                .build();
        entity.setPaymentStatus(paymentStatus);

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/payment/payment/testPaymentDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/payment/payment/removePaymentDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Payment entity = getPayment();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/payment/payment/testPaymentDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Payment expected = getPayment();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Payment entity = generalEntityDao.findEntity("Payment.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/payment/payment/testPaymentDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Payment expected = getPayment();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Payment> optionEntity = generalEntityDao.findOptionEntity("Payment.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Payment entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/payment/payment/testPaymentDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Payment entity = getPayment();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Payment> entityList = generalEntityDao.findEntityList("Payment.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
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
        Currency currencyUsd = Currency.builder()
                .id(1L)
                .key("USD")
                .value("USD")
                .build();
        Currency currencyEur = Currency.builder()
                .id(2L)
                .key("EUR")
                .value("EUR")
                .build();
        Discount discount = Discount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
                .currency(currencyUsd)
                .build();

        Price price1 = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currencyUsd)
                .build();
        Price price2 = Price.builder()
                .id(2L)
                .amount(95)
                .currency(currencyEur)
                .build();
        Price price3 = Price.builder()
                .id(3L)
                .amount(85)
                .currency(currencyUsd)
                .build();
        Price price4 = Price.builder()
                .id(4L)
                .amount(80.75)
                .currency(currencyEur)
                .build();
        Price price5 = Price.builder()
                .id(5L)
                .amount(10)
                .currency(currencyUsd)
                .build();
        Price price6 = Price.builder()
                .id(6L)
                .amount(9.5)
                .currency(currencyEur)
                .build();
        Price price7 = Price.builder()
                .id(7L)
                .amount(5)
                .currency(currencyUsd)
                .build();
        Price price8 = Price.builder()
                .id(8L)
                .amount(4.75)
                .currency(currencyEur)
                .build();

        CurrencyCoefficient currencyCoefficient = CurrencyCoefficient.builder()
                .id(1L)
                .currencyFrom(currencyUsd)
                .coefficient(0.95)
                .currencyTo(currencyEur)
                .dateOfCreate(getLocalDate("2024-03-03 12:00:00"))
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

}
