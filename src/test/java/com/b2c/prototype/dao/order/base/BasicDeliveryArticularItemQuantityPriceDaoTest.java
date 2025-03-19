package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.dao.AbstractCustomEntityDaoTest;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantityPrice;
import com.b2c.prototype.modal.entity.item.ItemType;
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
import com.b2c.prototype.util.CardUtil;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.finder.table.EntityTable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BasicDeliveryArticularItemQuantityPriceDaoTest extends AbstractCustomEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(DeliveryArticularItemQuantity.class, "order_articular_item"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicOrderArticularItemQuantityDao(sessionFactory, queryService);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM beneficiary");
            statement.execute("DELETE FROM articular_item_quantity");
            statement.execute("DELETE FROM order_articular_item");
            statement.execute("DELETE FROM item_review");
            statement.execute("DELETE FROM articular_item_option_item");
            statement.execute("DELETE FROM articular_item");
            statement.execute("DELETE FROM option_item");
            statement.execute("DELETE FROM item");
            statement.execute("DELETE FROM item_data");
            statement.execute("DELETE FROM payment");
            statement.execute("DELETE FROM price");
            statement.execute("DELETE FROM credit_card");
            statement.execute("DELETE FROM delivery");
            statement.execute("DELETE FROM user_details");
            statement.execute("DELETE FROM address");
            statement.execute("DELETE FROM country");

            statement.execute("TRUNCATE TABLE price RESTART IDENTITY CASCADE");
            statement.execute("TRUNCATE TABLE address RESTART IDENTITY CASCADE");
            statement.execute("TRUNCATE TABLE credit_card RESTART IDENTITY CASCADE");
            statement.execute("TRUNCATE TABLE contact_phone RESTART IDENTITY CASCADE");

            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
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
        String dateString = "2025-01-01";
        Date parsedDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            parsedDate = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ContactInfo.builder()
                .id(1L)
                .firstName("Wolter")
                .lastName("White")
                .birthdayDate(parsedDate)
                .build();
    }

    private Address createAddress() {
        Country country = Country.builder()
                .id(1L)
                .value("USA")
                .label("USA")
                .flagImagePath(null)
                .build();
        return Address.builder()
                .country(country)
                .city("New York")
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
                .label("Post")
                .build();

        return Delivery.builder()
                .address(createAddress())
                .deliveryType(deliveryType)
                .build();
    }

    private Category prepareCategories() {
        Category parent = Category.builder()
                .id(1L)
                .value("parent")
                .build();
        Category root = Category.builder()
                .id(2L)
                .value("root")
                .parent(parent)
                .build();
        Category child = Category.builder()
                .id(3L)
                .value("child")
                .build();

        parent.setChildList(List.of(root));
        root.setParent(parent);
        root.setChildList(List.of(child));
        child.setParent(root);

        return child;
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
                .dateOfCreate(100)
                .isActive(true)
                .contactInfo(prepareContactInfo())
//                .addresses(Set.of(createAddress()))
                .userCreditCards(Set.of(userCreditCard))
                .build();
    }

    private ItemData prepareTestItemData() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .label("Hermes")
                .build();
        Category category = prepareCategories();
        ArticularStatus articularStatus = ArticularStatus.builder()
                .id(1L)
                .value("NEW")
                .label("NEW")
                .build();
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Clothes")
                .label("Clothes")
                .build();

        ItemData itemData = ItemData.builder()
                .id(1L)
                .category(category)
                .brand(brand)
                .itemType(itemType)
                .build();

        return itemData;
    }

    private ArticularItemQuantityPrice prepareTestOrderItemQuantity() {
        OptionItem optionItem = OptionItem.builder()
                .id(1L)
                .value("L")
                .label("L")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .label("Size")
                .build();
        optionGroup.addOptionItem(optionItem);
        ArticularItem articularItem = ArticularItem.builder()
                .id(1L)
                .dateOfCreate(10000)
                .articularId("1")
                .build();
        articularItem.addOptionItem(optionItem);
        return ArticularItemQuantityPrice.builder()
//                .articularItem(articularItem)
//                .quantity(1)
                .build();
    }

    private OrderStatus prepareTestOrderStatus() {
        return OrderStatus.builder()
                .id(1L)
                .value("Pending")
                .label("Pending")
                .build();
    }

    private Payment prepareTestPayment() {
        CreditCard creditCard = CreditCard.builder()
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
                .label("Card")
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
                .amount(100)
                .currency(currency)
                .build();

        return Payment.builder()
                .paymentId("1")
                .discount(discount)
                .paymentMethod(paymentMethod)
                .creditCard(creditCard)
                .commissionPrice(price)
                .totalPrice(price)
                .build();
    }

    private DeliveryArticularItemQuantity prepareTestOrderItemData() {
        ArticularItemQuantityPrice articularItemQuantityPrice = prepareTestOrderItemQuantity();
        articularItemQuantityPrice.setId(1L);
        UserDetails userDetails = prepareTestUserDetails();
        userDetails.getContactInfo().setId(1L);
        DeliveryArticularItemQuantity orderItemDataOption = DeliveryArticularItemQuantity.builder()
                .id(1L)
//                .dateOfCreate(100L)
//                .beneficiaries(List.of(beneficiary))
//                .payment(prepareTestPayment())
                .delivery(prepareTestDelivery())
//                .articularItemQuantityPriceList(List.of(articularItemQuantityPrice))
//                .orderStatus(prepareTestOrderStatus())
//                .userDetails(userDetails)
//                .orderId("100")
//                .note("note")
                .build();

        orderItemDataOption.getDelivery().setId(1L);
        orderItemDataOption.getDelivery().getAddress().setId(1L);

//        orderItemDataOption.getPayment().setId(1L);
//        orderItemDataOption.getPayment().getCommissionPrice().setId(1L);
//        orderItemDataOption.getPayment().getTotalPrice().setId(1L);
//        orderItemDataOption.getPayment().getCreditCard().setId(1L);

        return orderItemDataOption;
    }

    private DeliveryArticularItemQuantity prepareSaveOrderItem() {

        return DeliveryArticularItemQuantity.builder()
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

    private DeliveryArticularItemQuantity prepareUpdateOrderItem() {
        ArticularItemQuantityPrice articularItemQuantityPrice = prepareTestOrderItemQuantity();
        articularItemQuantityPrice.setId(1L);
        UserDetails userDetails = prepareTestUserDetails();
        userDetails.getContactInfo().setId(1L);
        DeliveryArticularItemQuantity orderItemDataOption = DeliveryArticularItemQuantity.builder()
                .id(1L)
//                .dateOfCreate(100L)
//                .beneficiaries(List.of(beneficiary))
//                .payment(prepareTestPayment())
                .delivery(prepareTestDelivery())
//                .articularItemQuantityPriceList(List.of(articularItemQuantityPrice))
//                .orderStatus(prepareTestOrderStatus())
//                .userDetails(userDetails)
//                .orderId("100")
//                .note("note")
                .build();
        orderItemDataOption.getDelivery().setId(1L);
        orderItemDataOption.getDelivery().getAddress().setId(1L);

//        orderItemDataOption.getPayment().setId(1L);
//        orderItemDataOption.getPayment().getCommissionPrice().setId(1L);
//        orderItemDataOption.getPayment().getTotalPrice().setId(1L);
//        orderItemDataOption.getPayment().getCreditCard().setId(1L);

        return orderItemDataOption;
    }

    private void checkOrderItem(DeliveryArticularItemQuantity expectedOrderItemDataOption, DeliveryArticularItemQuantity actualOrderItemDataOption) {

    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/order/order_articular_item/testOrderArticularItem.yml");
        Parameter parameter = new Parameter("id", 1L);
        DeliveryArticularItemQuantity orderItemDataOption = prepareTestOrderItemData();
        List<DeliveryArticularItemQuantity> resultList = dao.getNamedQueryEntityList("", parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            checkOrderItem(orderItemDataOption, result);
        });
    }

    @Test
    void getEntityList_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getNamedQueryEntity("", parameter);
        });
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/order/order_articular_item/emptyOrderArticularItem.yml");
        DeliveryArticularItemQuantity orderItemDataOption = prepareSaveOrderItem();

        dao.persistEntity(orderItemDataOption);
        verifyExpectedData("/datasets/order/order_articular_item/saveOrderArticularItem.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        DeliveryArticularItemQuantity orderItemDataOption = prepareSaveOrderItem();

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).persist(orderItemDataOption);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(orderItemDataOption);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/order/order_articular_item/emptyOrderArticularItem.yml");
        Consumer<Session> consumer = (Session s) -> {
            DeliveryArticularItemQuantity orderItemDataOption = prepareSaveOrderItem();
            s.persist(orderItemDataOption);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/order/order_articular_item/saveOrderArticularItem.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/order/order_articular_item/emptyOrderArticularItem.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/order/order_articular_item/emptyOrderArticularItem.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Supplier<DeliveryArticularItemQuantity> supplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(supplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/order/order_articular_item/testOrderArticularItem.yml");
        Consumer<Session> consumer = (Session s) -> {
            DeliveryArticularItemQuantity deliveryArticularItemQuantity = prepareUpdateOrderItem();
            Address address = deliveryArticularItemQuantity.getDelivery().getAddress();
            s.merge(address);
            deliveryArticularItemQuantity.getDelivery().setAddress(address);
//            deliveryArticularItemQuantity.getUserDetails().setAddresses(Set.of(address));
//
//            CreditCard creditCard = deliveryArticularItemQuantity.getPayment().getCreditCard();
//            s.merge(creditCard);
//            deliveryArticularItemQuantity.getPayment().setCreditCard(creditCard);
//            Set<UserCreditCard> userCreditCardList = deliveryArticularItemQuantity.getUserDetails().getUserCreditCardList();
//            userCreditCardList.forEach(s::merge);
//            deliveryArticularItemQuantity.getUserDetails().setUserCreditCardList(userCreditCardList);
//
//            ContactInfo contactInfo = deliveryArticularItemQuantity.getUserDetails().getContactInfo();
//            Beneficiary beneficiary = deliveryArticularItemQuantity.getBeneficiaries().get(0);
//            s.merge(contactInfo);
//            s.merge(beneficiary);
//            deliveryArticularItemQuantity.setBeneficiaries(List.of(beneficiary));
//            deliveryArticularItemQuantity.getUserDetails().setContactInfo(contactInfo);
//
//            ArticularItemQuantityPrice articularItemQuantityPrice = deliveryArticularItemQuantity.getArticularItemQuantityPriceList().get(0);
//
//            s.merge(articularItemQuantityPrice);
//            deliveryArticularItemQuantity.setArticularItemQuantityPriceList(List.of(articularItemQuantityPrice));

            s.merge(deliveryArticularItemQuantity);
        };
        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/order/order_articular_item/updateOrderArticularItem.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/order/order_articular_item/testOrderArticularItem.yml");
        Consumer<Session> itemConsumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(itemConsumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void findEntityAndDelete_success() {
        loadDataSet("/datasets/order/order_articular_item/testOrderArticularItem.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData("/datasets/order/order_articular_item/deleteOrderArticularItem.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/order/order_articular_item/testOrderArticularItem.yml");
        Consumer<Session> consumer = (Session s) -> {
            DeliveryArticularItemQuantity orderItemDataOption = prepareTestOrderItemData();
            s.remove(orderItemDataOption);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/order/order_articular_item/deleteOrderArticularItem.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/order/order_articular_item/testOrderArticularItem.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntity_success() {
        loadDataSet("/datasets/order/order_articular_item/testOrderArticularItem.yml");
        DeliveryArticularItemQuantity orderItemDataOption = prepareTestOrderItemData();

        dao.deleteEntity(orderItemDataOption);
        verifyExpectedData("/datasets/order/order_articular_item/deleteOrderArticularItem.yml");
    }

    @Test
    void deleteEntity_transactionFailure() {
        loadDataSet("/datasets/order/order_articular_item/testOrderArticularItem.yml");
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).remove(any(Object.class));

        DeliveryArticularItemQuantity orderItemDataOption = prepareTestOrderItemData();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(orderItemDataOption);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/order/order_articular_item/testOrderArticularItem.yml");
        Parameter parameter = new Parameter("id", 1L);
        DeliveryArticularItemQuantity orderItemDataOption = prepareTestOrderItemData();
        Optional<DeliveryArticularItemQuantity> resultOptional =
                dao.getNamedQueryOptionalEntity("", parameter);

        assertTrue(resultOptional.isPresent());
        DeliveryArticularItemQuantity result = resultOptional.get();
        checkOrderItem(orderItemDataOption, result);
    }

    @Test
    void getOptionalEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getNamedQueryOptionalEntity("", parameter);
        });

    }

    @Test
    void getEntityWithDependencies_success() {
        loadDataSet("/datasets/order/order_articular_item/testOrderArticularItem.yml");
        Parameter parameter = new Parameter("id", 1L);

        DeliveryArticularItemQuantity orderItemDataOption = prepareTestOrderItemData();
        DeliveryArticularItemQuantity result = dao.getNamedQueryEntity("", parameter);

        checkOrderItem(orderItemDataOption, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getNamedQueryEntity("", parameter);
        });
    }

}