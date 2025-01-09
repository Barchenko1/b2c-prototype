package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.post.Post;
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
import com.tm.core.processor.thread.ThreadLocalSessionManager;
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
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BasicUserProfileDaoTest extends AbstractGeneralEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IThreadLocalSessionManager sessionManager = new ThreadLocalSessionManager(sessionFactory);
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, getEntityMappingManager());
        dao = new BasicUserProfileDao(sessionFactory, entityIdentifierDao);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM user_profile_credit_card");

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
        EntityTable itemEntityTable = new EntityTable(UserProfile.class, "user_profile");
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(countryEntityTable);
        entityMappingManager.addEntityTable(addressEntityTable);
        entityMappingManager.addEntityTable(creditCardEntityTable);
        entityMappingManager.addEntityTable(itemEntityTable);
        return entityMappingManager;
    }

    private UserProfile prepareTestUserProfile() {
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
                .buildingNumber(1)
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .city("city")
                .build();
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv("818")
                .build();
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
                .contactInfo(contactInfo)
                .address(address)
                .creditCardList(List.of(creditCard))
                .build();
    }

    private UserProfile prepareSaveUserProfile() {
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
                .street2("street2")
                .buildingNumber(1)
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .build();
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv("818")
                .build();

        return UserProfile.builder()
                .username("username")
                .email("email")
                .dateOfCreate(100)
                .isActive(true)
                .contactInfo(contactInfo)
                .address(address)
                .creditCardList(List.of(creditCard))
                .build();
    }

    private UserProfile prepareUpdateUserProfile() {
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
                .street2("street2")
                .buildingNumber(1)
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .city("city")
                .build();
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("Update name")
                .ownerSecondName("Update secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv("818")
                .build();
        Post parent = Post.builder()
                .id(1L)
                .title("Update parent")
                .uniquePostId("1")
                .message("Update parent")
                .dateOfCreate(100000)
                .build();

        return UserProfile.builder()
                .id(1L)
                .username("Update username")
                .email("Update email")
                .dateOfCreate(200)
                .isActive(false)
                .contactInfo(contactInfo)
                .address(address)
                .creditCardList(List.of(creditCard))
                .build();
    }

    private void checkUserProfile(UserProfile expectedUserProfile, UserProfile actualUserProfile) {
        assertEquals(expectedUserProfile.getId(), actualUserProfile.getId());
        assertEquals(expectedUserProfile.getUsername(), actualUserProfile.getUsername());
        assertEquals(expectedUserProfile.getEmail(), actualUserProfile.getEmail());
        assertEquals(expectedUserProfile.getDateOfCreate(), actualUserProfile.getDateOfCreate());
        assertEquals(expectedUserProfile.isActive(), actualUserProfile.isActive());


    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/user/user_profile/testUserProfileDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        UserProfile userProfile = prepareTestUserProfile();
        List<UserProfile> resultList =
                dao.getEntityList(parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> checkUserProfile(userProfile, result));
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
        loadDataSet("/datasets/user/user_profile/emptyUserProfileDataSet.yml");
        UserProfile userProfile = prepareSaveUserProfile();
        dao.mergeEntity(userProfile);
        verifyExpectedData("/datasets/user/user_profile/saveUserProfileDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/user/user_profile/emptyUserProfileWithoutDetailsDataSet.yml");
        UserProfile userProfile = prepareSaveUserProfile();
        ContactInfo contactInfo = userProfile.getContactInfo();
        contactInfo.setId(0L);
        ContactPhone contactPhone = contactInfo.getContactPhone();
        contactPhone.setId(0L);
        Address address = userProfile.getAddress();
        address.setId(0L);
        CreditCard creditCard = userProfile.getCreditCardList().get(0);
        creditCard.setId(0L);

        dao.persistEntity(userProfile);
        verifyExpectedData("/datasets/user/user_profile/saveUserProfileWithDetailsDataSet.yml");
    }

    @Test
    void saveRelationshipEntity_transactionFailure() {
        UserProfile userProfile = prepareUpdateUserProfile();
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
        doThrow(new RuntimeException()).when(session).persist(userProfile);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(userProfile);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/user/user_profile/emptyUserProfileDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            UserProfile userProfile = prepareSaveUserProfile();
            userProfile.setAddress(s.merge(userProfile.getAddress()));
            userProfile.setContactInfo(s.merge(userProfile.getContactInfo()));
            userProfile.setCreditCardList(userProfile.getCreditCardList().stream().map(s::merge).toList());
            s.persist(userProfile);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/user/user_profile/saveUserProfileDataSet.yml");
    }

    @Test
    void saveRelationshipEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/user/user_profile/emptyUserProfileDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/user/user_profile/emptyUserProfileDataSet.yml");
    }

    @Test
    void updateRelationshipEntity_success() {
        loadDataSet("/datasets/user/user_profile/testUserProfileDataSet.yml");
        Supplier<UserProfile> supplier = this::prepareUpdateUserProfile;
        dao.updateEntity(supplier);
        verifyExpectedData("/datasets/user/user_profile/updateUserProfileDataSet.yml");
    }

    @Test
    void updateRelationshipEntity_transactionFailure() {
        Supplier<UserProfile> supplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(supplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/user/user_profile/testUserProfileDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            UserProfile userProfile = prepareUpdateUserProfile();
            s.merge(userProfile);
        };
        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/user/user_profile/updateUserProfileDataSet.yml");
    }

    @Test
    void updateRelationshipEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/user/user_profile/testUserProfileDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            UserProfile userProfile = prepareUpdateUserProfile();
            s.merge(userProfile);
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteRelationshipEntity_success() {
        loadDataSet("/datasets/user/user_profile/testUserProfileDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData("/datasets/user/user_profile/emptyUserProfileWithoutDetailsDataSet.yml");
    }

    @Test
    void deleteRelationshipEntityByConsumer_success() {
        loadDataSet("/datasets/user/user_profile/testUserProfileDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            UserProfile userProfile = prepareTestUserProfile();
            s.remove(userProfile.getAddress());
            s.remove(userProfile);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/user/user_profile/emptyUserProfileWithoutDetailsDataSet.yml");
    }

    @Test
    void deleteRelationshipEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/user/user_profile/testUserProfileDataSet.yml");
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
        loadDataSet("/datasets/user/user_profile/testUserProfileDataSet.yml");
        UserProfile userProfile = prepareTestUserProfile();

        dao.deleteEntity(userProfile);
        verifyExpectedData("/datasets/user/user_profile/emptyUserProfileWithoutDetailsDataSet.yml");
    }

    @Test
    void deleteRelationshipEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/user/user_profile/testUserProfileDataSet.yml");
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

        UserProfile userProfile = prepareTestUserProfile();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(userProfile);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteRelationshipEntity_transactionFailure() {
        loadDataSet("/datasets/user/user_profile/testUserProfileDataSet.yml");

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
        doThrow(new RuntimeException()).when(session).remove(any(UserProfile.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.findEntityAndDelete(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/user/user_profile/testUserProfileDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        UserProfile userProfile = prepareTestUserProfile();
        Optional<UserProfile> resultOptional = dao.getOptionalEntity(parameter);

        assertTrue(resultOptional.isPresent());
        UserProfile result = resultOptional.get();

        checkUserProfile(userProfile, result);
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
        loadDataSet("/datasets/user/user_profile/testUserProfileDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        UserProfile userProfile = prepareTestUserProfile();
        UserProfile result = dao.getEntity(parameter);

        checkUserProfile(userProfile, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getEntity(parameter);
        });
    }

}