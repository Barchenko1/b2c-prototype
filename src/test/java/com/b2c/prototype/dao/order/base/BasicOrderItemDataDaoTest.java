package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.item.ItemDataOptionQuantity;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.order.OrderItemData;
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
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.EntityIdentifierDao;
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

class BasicOrderItemDataDaoTest extends AbstractGeneralEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(OrderItemData.class, "order_item_data"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicOrderItemDao(sessionFactory, entityIdentifierDao);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
//            statement.execute("DELETE FROM item_data_quantity_item_data");
//            statement.execute("DELETE FROM order_data_quantity_item");
            statement.execute("DELETE FROM orderitemdata_quantityitem");
            statement.execute("DELETE FROM order_item_data_contact_info");
            statement.execute("DELETE FROM order_item_data");
            statement.execute("DELETE FROM item_data_option_quantity");
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
                .cvv("818")
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
        Discount discount = Discount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
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
        Price price = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();

        ItemData itemData = ItemData.builder()
                .id(1L)
                .category(category)
                .brand(brand)
                .status(itemStatus)
                .itemType(itemType)
                .build();

        return itemData;
    }

    private ItemDataOptionQuantity prepareTestOrderItemQuantity() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();
        OptionItem optionItem = OptionItem.builder()
                .id(1L)
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
        ItemData itemData = prepareTestOrderItemData();
        ItemDataOption itemDataOption = ItemDataOption.builder()
                .optionItem(optionItem)
                .articularId("1")
                .itemData(itemData)
                .build();
        return ItemDataOptionQuantity.builder()
                .id(1L)
                .itemDataOption(itemDataOption)
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
                .cvv("818")
                .build();
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .value("Card")
                .build();
        Currency currency = Currency.builder()
                .id(1L)
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

    private OrderItemData prepareTestOrderItem() {

        return OrderItemData.builder()
                .id(1L)
                .dateOfCreate(100L)
                .beneficiaries(List.of(prepareContactInfo()))
                .payment(prepareTestPayment())
                .delivery(prepareTestDelivery())
                .itemDataOptionQuantitySet(Set.of(prepareTestOrderItemQuantity()))
                .orderStatus(prepareTestOrderStatus())
                .userProfile(prepareTestUserProfile())
                .orderId("100")
                .note("note")
                .build();
    }

    private OrderItemData prepareSaveOrderItem() {

        return OrderItemData.builder()
                .dateOfCreate(100L)
                .beneficiaries(List.of(prepareContactInfo()))
                .payment(prepareTestPayment())
                .delivery(prepareTestDelivery())
                .itemDataOptionQuantitySet(Set.of(prepareTestOrderItemQuantity()))
                .orderStatus(prepareTestOrderStatus())
                .userProfile(prepareTestUserProfile())
                .orderId("100")
                .note("note")
                .build();
    }

    private OrderItemData prepareUpdateOrderItem() {
        return OrderItemData.builder()
                .id(1L)
                .dateOfCreate(100L)
                .beneficiaries(List.of(prepareContactInfo()))
                .payment(prepareTestPayment())
                .delivery(prepareTestDelivery())
                .itemDataOptionQuantitySet(Set.of(prepareTestOrderItemQuantity()))
                .orderStatus(prepareTestOrderStatus())
                .userProfile(prepareTestUserProfile())
                .orderId("100")
                .note("note")
                .build();
    }

    private void checkOrderItem(OrderItemData expectedOrderItemData, OrderItemData actualOrderItemData) {

    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        OrderItemData orderItemData = prepareTestOrderItem();
        List<OrderItemData> resultList = dao.getEntityList(parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            checkOrderItem(orderItemData, result);
        });
    }

    @Test
    void getEntityList_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getEntityList(parameter);
        });
    }

    @Test
    void saveEntity_success() {
        loadDataSet("/datasets/order/order_item/emptyOrderItemDataSet.yml");
        OrderItemData orderItemData = prepareSaveOrderItem();

        dao.persistEntity(orderItemData);
        verifyExpectedData("/datasets/order/order_item/saveOrderItemDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/order/order_item/emptyOrderItemDataSet.yml");
        OrderItemData orderItemData = prepareSaveOrderItem();
        dao.persistEntity(orderItemData);
        verifyExpectedData("/datasets/order/order_item/saveOrderItemDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        OrderItemData orderItemData = prepareSaveOrderItem();

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
        doThrow(new RuntimeException()).when(session).persist(orderItemData);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(orderItemData);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/order/order_item/emptyOrderItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            OrderItemData orderItemData = prepareSaveOrderItem();
            s.persist(orderItemData);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/order/order_item/saveOrderItemDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/order/order_item/emptyOrderItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/order/order_item/emptyOrderItemDataSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Supplier<OrderItemData> supplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(supplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            OrderItemData orderItemData = prepareUpdateOrderItem();
            Address address = createAddress();
            s.merge(address);
            orderItemData.getDelivery().setAddress(address);
            orderItemData.getUserProfile().setAddress(address);

            CreditCard creditCard = prepareCard();
            s.merge(creditCard);
            orderItemData.getPayment().setCreditCard(creditCard);
            orderItemData.getUserProfile().setCreditCardList(List.of(creditCard));

            ContactInfo contactInfo = prepareContactInfo();
            s.merge(contactInfo);
            orderItemData.setBeneficiaries(List.of(contactInfo));
            orderItemData.getUserProfile().setContactInfo(contactInfo);

            s.merge(orderItemData);
        };
        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/order/order_item/updateOrderItemDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Consumer<Session> itemConsumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(itemConsumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntity_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData("/datasets/order/order_item/emptyOrderItemDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            OrderItemData orderItemData = prepareTestOrderItem();
            s.remove(orderItemData);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/order/order_item/emptyOrderItemDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntityByGeneralEntity_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        OrderItemData orderItemData = prepareTestOrderItem();

        dao.deleteEntity(orderItemData);
        verifyExpectedData("/datasets/order/order_item/emptyOrderItemDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
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

        OrderItemData orderItemData = prepareTestOrderItem();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(orderItemData);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntity_transactionFailure() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        OrderItemData orderItemData = new OrderItemData();

        Parameter parameter = new Parameter("id", 1L);

        IThreadLocalSessionManager sessionManager = mock(IThreadLocalSessionManager.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractEntityDao.class.getDeclaredField("sessionManager");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionManager.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(transaction).commit();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.findEntityAndDelete(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        OrderItemData orderItemData = prepareTestOrderItem();
        Optional<OrderItemData> resultOptional =
                dao.getOptionalEntity(parameter);

        assertTrue(resultOptional.isPresent());
        OrderItemData result = resultOptional.get();
        checkOrderItem(orderItemData, result);
    }

    @Test
    void getOptionalEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalEntity(parameter);
        });

    }

    @Test
    void getEntityWithDependencies_success() {
        loadDataSet("/datasets/order/order_item/testOrderItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        OrderItemData orderItemData = prepareTestOrderItem();
        OrderItemData result = dao.getEntity(parameter);

        checkOrderItem(orderItemData, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getEntity(parameter);
        });
    }

}