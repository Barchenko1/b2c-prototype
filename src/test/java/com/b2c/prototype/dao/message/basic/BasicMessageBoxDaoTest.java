package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.AbstractCustomEntityDaoTest;
import com.b2c.prototype.dao.message.BasicMessageBoxDao;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageBox;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageTemplate;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.process.dao.common.session.AbstractSessionFactoryDao;
import com.tm.core.process.dao.query.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.finder.table.EntityTable;
import jakarta.persistence.EntityManager;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BasicMessageBoxDaoTest extends AbstractCustomEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(MessageBox.class, "message_box"));
        entityMappingManager.addEntityTable(new EntityTable(Message.class, "message"));

        queryService = new QueryService(entityMappingManager);
        dao = new BasicMessageBoxDao(sessionFactory, queryService);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM message_box_message");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: ", e);
        }
    }

    private UserDetails prepareTestUserDetails() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .label("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .phoneNumber("111-111-111")
                .countryPhoneCode(countryPhoneCode)
                .build();
        ContactInfo contactInfo = ContactInfo.builder()
                .id(1L)
                .firstName("Wolter")
                .lastName("White")
                .contactPhone(contactPhone)
                .build();
        Country country = Country.builder()
                .id(1L)
                .label("USA")
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
        UserCreditCard userCreditCard = UserCreditCard.builder()
                .creditCard(creditCard)
                .isDefault(false)
                .build();
        Post parent = Post.builder()
                .id(1L)
                .title("parent")
                .postId("1")
                .message("parent")
                .dateOfCreate(100000)
                .build();

        return UserDetails.builder()
                .id(1L)
                .username("username")
                .dateOfCreate(100)
                .isActive(true)
                .contactInfo(contactInfo)
//                .addresses(Set.of(address))
                .userCreditCards(Set.of(userCreditCard))
                .build();
    }

    private MessageBox prepareTestMessageBox() {
        MessageStatus messageStatus = MessageStatus.builder()
                .id(1L)
                .value("New")
                .build();
        MessageType messageType = MessageType.builder()
                .id(1L)
                .value("InMail")
                .build();
        Message message = Message.builder()
                .id(1L)
                .messageTemplate(MessageTemplate.builder()
                        .id(1L)
                        .title("title")
                        .message("message")
                        .messageUniqNumber("messageUniqNumber1")
                        .sender("sender@email.com")
                        .receivers(List.of("receiver1@email.com", "receiver2@email.com"))
                        .sendSystem("system")
                        .dateOfSend(100)
                        .type(messageType)
                        .build())
                .status(messageStatus)
                .build();

        MessageBox messageBox = MessageBox.builder()
                .id(1L)
                .userDetails(prepareTestUserDetails())
                .build();
        messageBox.addMessage(message);

        return messageBox;
    }

    private MessageBox prepareSaveMessageBox() {
        MessageStatus messageStatus = MessageStatus.builder()
                .id(1L)
                .value("New")
                .build();
        MessageType messageType = MessageType.builder()
                .id(1L)
                .value("InMail")
                .build();
        Message message = Message.builder()
                .id(1L)
                .messageTemplate(MessageTemplate.builder()
                        .id(1L)
                        .title("title")
                        .message("message")
                        .messageUniqNumber("messageUniqNumber1")
                        .sender("sender@email.com")
                        .receivers(List.of("receiver1@email.com", "receiver2@email.com"))
                        .sendSystem("system")
                        .dateOfSend(100)
                        .type(messageType)
                        .build())
                .status(messageStatus)
                .build();

        MessageBox messageBox = MessageBox.builder()
                .userDetails(prepareTestUserDetails())
                .build();
        messageBox.addMessage(message);

        return messageBox;
    }

    private MessageBox prepareUpdateMessageBox() {
        MessageStatus messageStatus = MessageStatus.builder()
                .id(1L)
                .value("New")
                .build();
        MessageType messageType = MessageType.builder()
                .id(1L)
                .value("InMail")
                .build();
        Message message1 = Message.builder()
                .id(1L)
                .messageTemplate(MessageTemplate.builder()
                        .id(1L)
                        .title("title")
                        .message("message")
                        .messageUniqNumber("messageUniqNumber1")
                        .sender("sender@email.com")
                        .receivers(List.of("receiver1@email.com", "receiver2@email.com"))
                        .sendSystem("system")
                        .dateOfSend(100)
                        .type(messageType)
                        .build())
                .status(messageStatus)
                .build();
        Message message2 = Message.builder()
                .messageTemplate(MessageTemplate.builder()
                        .id(1L)
                        .title("title")
                        .message("message")
                        .sender("sender@email.com")
                        .messageUniqNumber("messageUniqNumber2")
                        .receivers(List.of("receiver1@email.com", "receiver2@email.com"))
                        .sendSystem("system")
                        .dateOfSend(100)
                        .type(messageType)
                        .build())
                .status(messageStatus)
                .build();

        MessageBox messageBox = MessageBox.builder()
                .id(1L)
                .userDetails(prepareTestUserDetails())
                .build();
        messageBox.addMessage(message1);
        messageBox.addMessage(message2);

        return messageBox;
    }

    private Message prepareNewMessage() {
        MessageStatus messageStatus = MessageStatus.builder()
                .id(1L)
                .value("New")
                .build();
        MessageType messageType = MessageType.builder()
                .id(1L)
                .value("InMail")
                .build();
        return Message.builder()
                .messageTemplate(MessageTemplate.builder()
                        .title("title")
                        .message("message")
                        .messageUniqNumber("messageUniqNumber1")
                        .sender("sender@email.com")
                        .receivers(List.of("receiver1@email.com", "receiver2@email.com"))
                        .sendSystem("system")
                        .dateOfSend(100)
                        .type(messageType)
                        .build())
                .status(messageStatus)
                .build();
    }
    
    private void checkMessageBox(MessageBox expectedMessageBox, MessageBox actualMessageBox) {
        assertEquals(expectedMessageBox.getId(), actualMessageBox.getId());
    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/message/message_box/testMessageBoxDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        MessageBox messageBox = prepareTestMessageBox();
        List<MessageBox> resultList = dao.getNamedQueryEntityList("", parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            checkMessageBox(messageBox, result);
        });
    }

    @Test
    void getEntityList_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getNamedQueryEntityList("", parameter);
        });
    }

    @Test
    void saveEntity_success() {
        loadDataSet("/datasets/message/message_box/emptyMessageBoxDataSet.yml");
        MessageBox messageBox = prepareSaveMessageBox();
        dao.persistEntity(messageBox);
        verifyExpectedData("/datasets/message/message_box/saveMessageBoxDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/message/message_box/emptyMessageBoxDataSet.yml");
        MessageBox messageBox = prepareSaveMessageBox();
        dao.persistEntity(messageBox);
        verifyExpectedData("/datasets/message/message_box/saveMessageBoxDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        MessageBox messageBox = prepareSaveMessageBox();

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractSessionFactoryDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).persist(messageBox);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(messageBox);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/message/message_box/emptyMessageBoxDataSet.yml");
        Consumer<EntityManager> consumer = (EntityManager session) -> {
            MessageBox messageBox = prepareSaveMessageBox();
            session.persist(messageBox);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/message/message_box/saveMessageBoxDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/message/message_box/emptyMessageBoxDataSet.yml");
        Consumer<EntityManager> consumer = (EntityManager session) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/message/message_box/emptyMessageBoxDataSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/message/message_box/testMessageBoxDataSet.yml");
        Message newMessage = prepareNewMessage();
        Supplier<MessageBox> messageBoxSupplier = () -> {
            Parameter parameter = new Parameter("id", 1L);
            MessageBox oldMessageBox = queryService.getNamedQueryEntity(sessionFactory.openSession(),MessageBox.class, "",parameter);
            newMessage.setId(2L);
            oldMessageBox.addMessage(newMessage);
            return oldMessageBox;
        };
        dao.mergeSupplier(messageBoxSupplier);
        verifyExpectedData("/datasets/message/message_box/updateMessageBoxDataSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Supplier<MessageBox> messageBoxSupplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.mergeSupplier(messageBoxSupplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/message/message_box/testMessageBoxDataSet.yml");
        Message newMessage = prepareNewMessage();
        Consumer<EntityManager> consumer = (EntityManager session) -> {
            MessageBox messageBox = prepareTestMessageBox();
            messageBox.addMessage(newMessage);
            newMessage.setId(2L);
            session.merge(messageBox);
        };
        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/message/message_box/updateMessageBoxDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/message/message_box/testMessageBoxDataSet.yml");
        Consumer<EntityManager> consumer = (EntityManager session) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/message/message_box/testMessageBoxDataSet.yml");
        Consumer<EntityManager> consumer = (EntityManager session) -> {
            MessageBox messageBox = prepareTestMessageBox();
            session.remove(messageBox);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/message/message_box/emptyMessageBoxDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/message/message_box/testMessageBoxDataSet.yml");
        Consumer<EntityManager> consumer = (EntityManager session) -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntityByGeneralEntity_success() {
        loadDataSet("/datasets/message/message_box/testMessageBoxDataSet.yml");
        MessageBox messageBox = prepareTestMessageBox();

        dao.deleteEntity(messageBox);
        verifyExpectedData("/datasets/message/message_box/emptyMessageBoxDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/message/message_box/testMessageBoxDataSet.yml");
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractSessionFactoryDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).remove(any(Object.class));

        MessageBox messageBox = prepareTestMessageBox();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(messageBox);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/message/message_box/testMessageBoxDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        MessageBox messageBox = prepareTestMessageBox();
        Optional<MessageBox> resultOptional =
               dao.getNamedQueryOptionalEntity("", parameter);

        assertTrue(resultOptional.isPresent());
        MessageBox result = resultOptional.get();
        checkMessageBox(messageBox, result);
    }

    @Test
    void getOptionalEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
           dao.getNamedQueryOptionalEntity("", parameter);
        });

    }

    @Test
    void getEntityWithDependencies_success() {
        loadDataSet("/datasets/message/message_box/testMessageBoxDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        MessageBox messageBox = prepareTestMessageBox();
        MessageBox result = dao.getNamedQueryEntity("", parameter);

        checkMessageBox(messageBox, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getNamedQueryEntity("", parameter);
        });
    }

}