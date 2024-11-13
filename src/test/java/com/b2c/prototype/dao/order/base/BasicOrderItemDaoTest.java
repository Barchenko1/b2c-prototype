package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemQuantity;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.order.OrderItem;
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
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.dao.general.AbstractGeneralEntityDao;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.modal.GeneralEntity;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.processor.finder.table.EntityTable;
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
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

class BasicOrderItemDaoTest extends AbstractGeneralEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(OrderItem.class, "order_item"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicOrderItemDao(sessionFactory, entityIdentifierDao);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM item_quantity_item_data");
            statement.execute("DELETE FROM order_quantity_item");
            statement.execute("DELETE FROM order_item_contact_info");
            statement.execute("DELETE FROM order_item");
            statement.execute("DELETE FROM item_quantity");
            statement.execute("DELETE FROM item_post");
            statement.execute("DELETE FROM item_review");
            statement.execute("DELETE FROM item_data_option");
            statement.execute("DELETE FROM item");
            statement.execute("DELETE FROM item_data");
            statement.execute("DELETE FROM payment");
            statement.execute("DELETE FROM user_profile_credit_card");
            statement.execute("DELETE FROM user_profile_post");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
    }

    private ContactInfo prepareContactInfo() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .code("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .phoneNumber("111-111-111")
                .countryPhoneCode(countryPhoneCode)
                .build();
        return ContactInfo.builder()
                .id(1L)
                .name("Wolter")
                .secondName("White")
                .contactPhone(contactPhone)
                .build();
    }

    private Address createAddress() {
        Country country = Country.builder()
                .id(1L)
                .value("USA")
                .build();
        return Address.builder()
                .id(1L)
                .country(country)
                .street("street")
                .buildingNumber(1)
                .apartmentNumber(101)
                .flor(9)
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

    private Category prepareCategories() {
        Category parent = Category.builder()
                .id(1L)
                .name("parent")
                .build();
        Category root = Category.builder()
                .id(2L)
                .name("root")
                .parent(parent)
                .build();
        Category child = Category.builder()
                .id(3L)
                .name("child")
                .build();

        parent.setChildNodeList(List.of(root));
        root.setParent(parent);
        root.setChildNodeList(List.of(child));
        child.setParent(root);

        return child;
    }

    private CreditCard prepareCard() {
        return CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv(818)
                .build();
    }

    private UserProfile prepareTestUserProfile() {
        CreditCard creditCard = prepareCard();
        Post parent = Post.builder()
                .id(1L)
                .title("parent")
                .uniquePostId("1")
                .authorEmail("parent@email.com")
                .authorUserName("parent")
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
                .postList(List.of(parent))
                .build();
    }

    private ItemData prepareTestOrderItemData() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .build();
        Category category = prepareCategories();
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .build();
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .currency(currency)
                .build();
        ItemStatus itemStatus = ItemStatus.builder()
                .id(1L)
                .value("NEW")
                .build();
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Clothes")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();
        OptionItem OptionItem1 = OptionItem.builder()
                .id(1L)
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
        OptionItem OptionItem2 = OptionItem.builder()
                .id(2L)
                .optionName("M")
                .optionGroup(optionGroup)
                .build();
        Price price = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();

        ItemData itemData = ItemData.builder()
                .id(1L)
                .name("name")
                .articularId("100")
                .dateOfCreate(100L)
                .category(category)
                .brand(brand)
                .currencyDiscount(currencyDiscount)
                .status(itemStatus)
                .itemType(itemType)
                .price(price)
                .build();

        itemData.addOptionItem(OptionItem1);
        itemData.addOptionItem(OptionItem2);

        return itemData;
    }

    private ItemQuantity prepareTestOrderItemQuantity() {
        ItemData itemData = prepareTestOrderItemData();
        return ItemQuantity.builder()
                .id(1L)
                .itemDataSet(Set.of(itemData))
                .quantity(1)
                .build();
    }

    private OrderStatus prepareTestOrderStatus() {
        return OrderStatus.builder()
                .id(1L)
                .value("Pending")
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
                .cvv(818)
                .build();
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .method("Card")
                .build();
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .build();
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .id(1L)
                .amount(5)
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
                .currencyDiscount(currencyDiscount)
                .paymentMethod(paymentMethod)
                .creditCard(creditCard)
                .fullPrice(price)
                .build();
    }

    private OrderItem prepareTestOrderItem() {

        return OrderItem.builder()
                .id(1L)
                .dateOfCreate(100L)
                .beneficiaries(List.of(prepareContactInfo()))
                .payment(prepareTestPayment())
                .delivery(prepareTestDelivery())
                .itemQuantitySet(Set.of(prepareTestOrderItemQuantity()))
                .orderStatus(prepareTestOrderStatus())
                .userProfile(prepareTestUserProfile())
                .orderId("100")
                .note("note")
                .build();
    }

    private OrderItem prepareSaveOrderItem() {

        return OrderItem.builder()
                .dateOfCreate(100L)
                .beneficiaries(List.of(prepareContactInfo()))
                .payment(prepareTestPayment())
                .delivery(prepareTestDelivery())
                .itemQuantitySet(Set.of(prepareTestOrderItemQuantity()))
                .orderStatus(prepareTestOrderStatus())
                .userProfile(prepareTestUserProfile())
                .orderId("100")
                .note("note")
                .build();
    }

    private OrderItem prepareUpdateOrderItem() {
        OrderItem orderItem =  OrderItem.builder()
                .id(1L)
                .dateOfCreate(100L)
                .beneficiaries(List.of(prepareContactInfo()))
                .payment(prepareTestPayment())
                .delivery(prepareTestDelivery())
                .itemQuantitySet(Set.of(prepareTestOrderItemQuantity()))
                .orderStatus(prepareTestOrderStatus())
                .userProfile(prepareTestUserProfile())
                .orderId("100")
                .note("note")
                .build();

        return orderItem;
    }

    private void checkOrderItem(OrderItem expectedOrderItem, OrderItem actualOrderItem) {

    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        OrderItem orderItem = prepareTestOrderItem();
        List<OrderItem> resultList = dao.getGeneralEntityList(parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            checkOrderItem(orderItem, result);
        });
    }

    @Test
    void getEntityListWithClass_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        OrderItem orderItem = prepareTestOrderItem();
        List<OrderItem> resultList = dao.getGeneralEntityList(OrderItem.class, parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            checkOrderItem(orderItem, result);
        });
    }

    @Test
    void getEntityList_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getGeneralEntityList(parameter);
        });
    }

    @Test
    void getEntityListWithClass_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getGeneralEntityList(Object.class, parameter);
        });
    }

    @Test
    void saveEntity_success() {
        loadDataSet("/datasets/order/order_item/emptyOrderItemDataSet.yml");
        OrderItem orderItem = prepareSaveOrderItem();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, orderItem);

        dao.saveGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/order/order_item/saveOrderItemDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/order/order_item/emptyOrderItemDataSet.yml");
        OrderItem orderItem = prepareSaveOrderItem();
        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, orderItem);
        dao.saveGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/order/order_item/saveOrderItemDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        OrderItem orderItem = prepareSaveOrderItem();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, orderItem);

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractGeneralEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).persist(orderItem);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveGeneralEntity(generalEntity);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/order/order_item/emptyOrderItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            OrderItem orderItem = prepareSaveOrderItem();
            s.persist(orderItem);
        };

        dao.saveGeneralEntity(consumer);
        verifyExpectedData("/datasets/order/order_item/saveOrderItemDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/order/order_item/emptyOrderItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveGeneralEntity(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/order/order_item/emptyOrderItemDataSet.yml");
    }

//    @Test
//    void updateEntity_success() {
//        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
//        Supplier<OrderItem> supplier = this::prepareUpdateOrderItem;
//        dao.updateGeneralEntity(supplier);
//        verifyExpectedData("/datasets/order/order_item/updateOrderItemDataSet.yml");
//    }

    @Test
    void updateEntity_transactionFailure() {
        Supplier<OrderItem> supplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateGeneralEntity(supplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            OrderItem orderItem = prepareUpdateOrderItem();
            Address address = createAddress();
            s.merge(address);
            orderItem.getDelivery().setAddress(address);
            orderItem.getUserProfile().setAddress(address);

            CreditCard creditCard = prepareCard();
            s.merge(creditCard);
            orderItem.getPayment().setCreditCard(creditCard);
            orderItem.getUserProfile().setCreditCardList(List.of(creditCard));

            ContactInfo contactInfo = prepareContactInfo();
            s.merge(contactInfo);
            orderItem.setBeneficiaries(List.of(contactInfo));
            orderItem.getUserProfile().setContactInfo(contactInfo);

            s.merge(orderItem);
        };
        dao.updateGeneralEntity(consumer);
        verifyExpectedData("/datasets/order/order_item/updateOrderItemDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Consumer<Session> itemConsumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.updateGeneralEntity(itemConsumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntity_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.deleteGeneralEntity(parameter);
        verifyExpectedData("/datasets/order/order_item/emptyOrderItemDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            OrderItem orderItem = prepareTestOrderItem();
            s.remove(orderItem);
        };

        dao.deleteGeneralEntity(consumer);
        verifyExpectedData("/datasets/order/order_item/emptyOrderItemDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntityByGeneralEntity_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        OrderItem orderItem = prepareTestOrderItem();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, orderItem);

        dao.deleteGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/order/order_item/emptyOrderItemDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractGeneralEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).remove(any(Object.class));

        OrderItem orderItem = prepareTestOrderItem();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, orderItem);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(generalEntity);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntityWithClass_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.deleteGeneralEntity(OrderItem.class, parameter);
        verifyExpectedData("/datasets/order/order_item/emptyOrderItemDataSet.yml");
    }

    @Test
    void deleteEntity_transactionFailure() {
        OrderItem orderItem = new OrderItem();

        Parameter parameter = new Parameter("id", 1L);

        IThreadLocalSessionManager sessionManager = mock(IThreadLocalSessionManager.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractGeneralEntityDao.class.getDeclaredField("sessionManager");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionManager.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(transaction).commit();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, orderItem);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntityWithClass_transactionFailure() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        OrderItem orderItem = new OrderItem();

        Parameter parameter = new Parameter("id", 1L);

        IThreadLocalSessionManager sessionManager = mock(IThreadLocalSessionManager.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractGeneralEntityDao.class.getDeclaredField("sessionManager");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionManager.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).remove(any(Object.class));

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, orderItem);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(OrderItem.class, parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        OrderItem orderItem = prepareTestOrderItem();
        Optional<OrderItem> resultOptional =
                dao.getOptionalGeneralEntity(parameter);

        assertTrue(resultOptional.isPresent());
        OrderItem result = resultOptional.get();
        checkOrderItem(orderItem, result);
    }

    @Test
    void getOptionalEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalGeneralEntity(parameter);
        });

    }

    @Test
    void getOptionalEntityWithDependenciesWithClass_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        OrderItem orderItem = prepareTestOrderItem();

        Optional<OrderItem> resultOptional =
                dao.getOptionalGeneralEntity(OrderItem.class, parameter);

        assertTrue(resultOptional.isPresent());
        OrderItem result = resultOptional.get();

        checkOrderItem(orderItem, result);
    }

    @Test
    void getOptionalEntityWithDependenciesWithClass_Failure() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalGeneralEntity(OrderItem.class, parameter);
        });
    }

    @Test
    void getOptionalEntityWithDependencies_OptionEmpty() {
        Parameter parameter = new Parameter("id", 100L);

        Optional<OrderItem> result =
                dao.getOptionalGeneralEntity(OrderItem.class, parameter);

        assertTrue(result.isEmpty());
    }

    @Test
    void getEntityWithDependencies_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        OrderItem orderItem = prepareTestOrderItem();
        OrderItem result = dao.getGeneralEntity(parameter);

        checkOrderItem(orderItem, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getGeneralEntity(parameter);
        });
    }


    @Test
    void getEntityWithDependenciesWithClass_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        OrderItem orderItem = prepareTestOrderItem();
        OrderItem result = dao.getGeneralEntity(OrderItem.class, parameter);

        checkOrderItem(orderItem, result);
    }

    @Test
    void getEntityWithDependenciesWithClass_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getGeneralEntity(OrderItem.class, parameter);
        });
    }

}