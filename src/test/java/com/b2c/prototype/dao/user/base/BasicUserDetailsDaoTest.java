package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.AbstractCustomEntityDaoTest;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.post.Post;
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
import org.hibernate.query.NativeQuery;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BasicUserDetailsDaoTest extends AbstractCustomEntityDaoTest {

    @BeforeAll
    public static void setup() {
        queryService = new QueryService(getEntityMappingManager());
        dao = new BasicUserDetailsDao(sessionFactory, queryService);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("TRUNCATE TABLE contact_info RESTART IDENTITY CASCADE");
            statement.execute("TRUNCATE TABLE contact_phone RESTART IDENTITY CASCADE");
            statement.execute("TRUNCATE TABLE address RESTART IDENTITY CASCADE");
            statement.execute("TRUNCATE TABLE credit_card RESTART IDENTITY CASCADE");
            statement.execute("TRUNCATE TABLE post RESTART IDENTITY CASCADE");

            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
    }

    private static IEntityMappingManager getEntityMappingManager() {
        EntityTable countryEntityTable = new EntityTable(Country.class, "country");
        EntityTable addressEntityTable = new EntityTable(Address.class, "address");
        EntityTable creditCardEntityTable = new EntityTable(CreditCard.class, "credit_card");
        EntityTable itemEntityTable = new EntityTable(UserDetails.class, "user_details");
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(countryEntityTable);
        entityMappingManager.addEntityTable(addressEntityTable);
        entityMappingManager.addEntityTable(creditCardEntityTable);
        entityMappingManager.addEntityTable(itemEntityTable);
        return entityMappingManager;
    }

    private UserDetails prepareTestUserDetails() {
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
                .city("city")
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
                .uniquePostId("1")
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

    private UserDetails prepareSaveUserDetails() {
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
                .city("city")
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

        return UserDetails.builder()
                .userId("123")
                .username("username")
                .dateOfCreate(100)
                .isActive(true)
                .contactInfo(contactInfo)
//                .addresses(Set.of(address))
                .userCreditCards(Set.of(userCreditCard))
                .build();
    }

    private UserDetails prepareUpdateUserDetails() {
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
                .street("Update street")
                .buildingNumber("1")
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .city("city")
                .build();
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .monthOfExpire(6)
                .yearOfExpire(28)
                .ownerName("Update name")
                .ownerSecondName("Update secondName")
                .isActive(CardUtil.isCardActive(6, 28))
                .cvv("818")
                .build();
        UserCreditCard userCreditCard = UserCreditCard.builder()
                .creditCard(creditCard)
                .isDefault(false)
                .build();
        Post parent = Post.builder()
                .id(1L)
                .title("Update parent")
                .uniquePostId("1")
                .message("Update parent")
                .dateOfCreate(100000)
                .build();

        return UserDetails.builder()
                .id(1L)
                .userId("123")
                .username("Update username")
                .dateOfCreate(200)
                .isActive(false)
                .contactInfo(contactInfo)
//                .addresses(Set.of(address))
                .userCreditCards(Set.of(userCreditCard))
                .build();
    }

    private void checkUserDetails(UserDetails expectedUserDetails, UserDetails actualUserDetails) {
        assertEquals(expectedUserDetails.getId(), actualUserDetails.getId());
        assertEquals(expectedUserDetails.getUsername(), actualUserDetails.getUsername());
        assertEquals(expectedUserDetails.getDateOfCreate(), actualUserDetails.getDateOfCreate());
        assertEquals(expectedUserDetails.isActive(), actualUserDetails.isActive());


    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/user/user_details/testUserDetails.yml");
        Parameter parameter = new Parameter("id", 1L);

        UserDetails userDetails = prepareTestUserDetails();
        List<UserDetails> resultList =
                dao.getNamedQueryEntityList("", parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> checkUserDetails(userDetails, result));
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
        loadDataSet("/datasets/user/user_details/emptyUserDetails.yml");
        UserDetails userDetails = prepareSaveUserDetails();
        dao.mergeEntity(userDetails);
        verifyExpectedData("/datasets/user/user_details/saveUserDetails.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/user/user_details/emptyUserDetailsWithoutDetails.yml");
        UserDetails userDetails = prepareSaveUserDetails();
        ContactInfo contactInfo = userDetails.getContactInfo();
        contactInfo.setId(0L);
        ContactPhone contactPhone = contactInfo.getContactPhone();
        contactPhone.setId(0L);
//        Address address = userDetails.getAddresses().stream().toList().get(0);
//        address.setId(0L);
        CreditCard creditCard = userDetails.getUserCreditCards().stream().toList().get(0).getCreditCard();
        creditCard.setId(0L);

        dao.persistEntity(userDetails);
        verifyExpectedData("/datasets/user/user_details/saveUserDetailsWithDetails.yml");
    }

    @Test
    void saveRelationshipEntity_transactionFailure() {
        UserDetails userDetails = prepareUpdateUserDetails();
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
        doThrow(new RuntimeException()).when(session).persist(userDetails);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(userDetails);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/user/user_details/emptyUserDetails.yml");
        Consumer<Session> consumer = (Session s) -> {
            UserDetails userDetails = prepareSaveUserDetails();
//            userDetails.setAddresses(s.merge(userDetails.getAddresses()));
            userDetails.setContactInfo(s.merge(userDetails.getContactInfo()));
            userDetails.setUserCreditCards(userDetails.getUserCreditCards().stream()
                    .peek(uc -> s.merge(uc.getCreditCard()))
                    .collect(Collectors.toSet()));
            s.persist(userDetails);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/user/user_details/saveUserDetails.yml");
    }

    @Test
    void saveRelationshipEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/user/user_details/emptyUserDetails.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/user/user_details/emptyUserDetails.yml");
    }

    @Test
    void updateRelationshipEntity_success() {
        loadDataSet("/datasets/user/user_details/testUserDetails.yml");
        Supplier<UserDetails> supplier = this::prepareUpdateUserDetails;
        dao.updateEntity(supplier);
        verifyExpectedData("/datasets/user/user_details/updateUserDetails.yml");
    }

    @Test
    void updateRelationshipEntity_transactionFailure() {
        Supplier<UserDetails> supplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(supplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/user/user_details/testUserDetails.yml");
        Consumer<Session> consumer = (Session s) -> {
            UserDetails userDetails = prepareUpdateUserDetails();
            s.merge(userDetails);
        };
        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/user/user_details/updateUserDetails.yml");
    }

    @Test
    void updateRelationshipEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/user/user_details/testUserDetails.yml");
        Consumer<Session> consumer = (Session s) -> {
            UserDetails userDetails = prepareUpdateUserDetails();
            s.merge(userDetails);
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteRelationshipEntity_success() {
        loadDataSet("/datasets/user/user_details/testUserDetails.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData("/datasets/user/user_details/emptyUserDetailsWithoutDetails.yml");
    }

    @Test
    void deleteRelationshipEntityByConsumer_success() {
        loadDataSet("/datasets/user/user_details/testUserDetails.yml");
        Consumer<Session> consumer = (Session s) -> {
            UserDetails userDetails = prepareTestUserDetails();
//            s.remove(userDetails.getAddresses());
            s.remove(userDetails);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/user/user_details/emptyUserDetailsWithoutDetails.yml");
    }

    @Test
    void deleteRelationshipEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/user/user_details/testUserDetails.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteRelationshipEntityByGeneralEntity_success() {
        loadDataSet("/datasets/user/user_details/testUserDetails.yml");
        UserDetails userDetails = prepareTestUserDetails();

        dao.deleteEntity(userDetails);
        verifyExpectedData("/datasets/user/user_details/emptyUserDetailsWithoutDetails.yml");
    }

    @Test
    void deleteRelationshipEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/user/user_details/testUserDetails.yml");
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

        UserDetails userDetails = prepareTestUserDetails();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(userDetails);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteRelationshipEntity_transactionFailure() {
        loadDataSet("/datasets/user/user_details/testUserDetails.yml");
        UserDetails userDetails = prepareTestUserDetails();
        Parameter parameter = new Parameter("id", 1L);

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);
        NativeQuery<UserDetails> nativeQuery = mock(NativeQuery.class);

        try {
            Field sessionManagerField = AbstractEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
//        when(session.createNativeQuery(anyString(), eq(UserDetails.class))).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(userDetails);
        doThrow(new RuntimeException()).when(session).remove(userDetails);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.findEntityAndDelete(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(session).close();
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/user/user_details/testUserDetails.yml");
        Parameter parameter = new Parameter("id", 1L);
        UserDetails userDetails = prepareTestUserDetails();
        Optional<UserDetails> resultOptional =dao.getNamedQueryOptionalEntity("", parameter);

        assertTrue(resultOptional.isPresent());
        UserDetails result = resultOptional.get();

        checkUserDetails(userDetails, result);
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
        loadDataSet("/datasets/user/user_details/testUserDetails.yml");
        Parameter parameter = new Parameter("id", 1L);

        UserDetails userDetails = prepareTestUserDetails();
        UserDetails result = dao.getNamedQueryEntity("", parameter);

        checkUserDetails(userDetails, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getNamedQueryEntity("", parameter);
        });
    }

}