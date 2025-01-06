import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.post.Post;

//package com.b2c.prototype.dao.embedded.processor;
//
//import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
//import com.b2c.prototype.dao.EntityDataSet;
//import com.b2c.prototype.modal.entity.address.Address;
//import com.b2c.prototype.modal.entity.address.Country;
//import com.b2c.prototype.modal.entity.item.Brand;
//import com.b2c.prototype.modal.entity.item.Category;
//import com.b2c.prototype.modal.entity.item.Item;
//import com.b2c.prototype.modal.entity.item.ItemStatus;
//import com.b2c.prototype.modal.entity.item.ItemType;
//import com.b2c.prototype.modal.entity.item.Rating;
//import com.b2c.prototype.modal.entity.option.OptionGroup;
//import com.b2c.prototype.modal.entity.option.OptionItem;
//import com.b2c.prototype.modal.entity.payment.Card;
//import com.b2c.prototype.modal.entity.post.Post;
//import com.b2c.prototype.modal.entity.price.Currency;
//import com.b2c.prototype.modal.entity.price.Price;
//import com.b2c.prototype.modal.entity.review.Review;
//import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
//import com.b2c.prototype.modal.entity.user.ContactInfo;
//import com.b2c.prototype.modal.entity.user.UserProfile;
//import com.b2c.prototype.modal.entity.wishlist.Wishlist;
//import com.b2c.prototype.util.CardUtil;
//import com.tm.core.dao.identifier.EntityIdentifierDao;
//import com.tm.core.processor.finder.manager.EntityMappingManager;
//import com.tm.core.processor.finder.manager.IEntityMappingManager;
//import com.tm.core.processor.finder.table.EntityTable;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//
//import java.sql.Connection;
//import java.sql.Statement;
//import java.util.List;
//import java.util.Set;
//
//class BasicWishListDaoTest extends AbstractSingleEntityDaoTest {
//
//    @BeforeAll
//    public static void setup() {
//        IEntityMappingManager entityMappingManager = new EntityMappingManager();
//        entityMappingManager.addEntityTable(new EntityTable(Wishlist.class, "wishlist"));
//        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
//        dao = new BasicWishListDao(sessionFactory, entityIdentifierDao);
//    }
//
//    @BeforeEach
//    public void cleanUpMiddleTable() {
//        try (Connection connection = connectionHolder.getConnection()) {
//            connection.setAutoCommit(false);
//            Statement statement = connection.createStatement();
//            statement.execute("DELETE FROM wishlist_item");
//            connection.commit();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to clean table: ", e);
//        }
//    }
//
//    private Category prepareCategories() {
//        Category parent = Category.builder()
//                .id(1L)
//                .name("parent")
//                .build();
//        Category root = Category.builder()
//                .id(2L)
//                .name("root")
//                .parent(parent)
//                .build();
//        Category child = Category.builder()
//                .id(3L)
//                .name("child")
//                .build();
//
//        parent.setChildNodeList(List.of(root));
//        root.setParent(parent);
//        root.setChildNodeList(List.of(child));
//        child.setParent(root);
//
//        return child;
//    }
//
//    private void addReview(Item item) {
//        Rating rating = Rating.builder()
//                .id(5L)
//                .value(5)
//                .build();
//
//        Review review = Review.builder()
//                .id(1L)
//                .title("title")
//                .message("message")
//                .dateOfCreate(100)
//                .rating(rating)
//                .build();
//
//        item.addReview(review);
//    }
//
//    private Post createParentPost() {
//        return Post.builder()
//                .id(1L)
//                .title("parent")
//                .uniquePostId("1")
//                .authorEmail("parent@email.com")
//                .authorUserName("parent")
//                .message("parent")
//                .dateOfCreate(100000)
//                .build();
//    }
//
//    private Post createRootPost() {
//        return Post.builder()
//                .id(2L)
//                .title("root")
//                .uniquePostId("2")
//                .authorEmail("root@email.com")
//                .authorUserName("root")
//                .message("root")
//                .dateOfCreate(100000)
//                .build();
//    }
//
//    private Post createChildPost() {
//        return Post.builder()
//                .id(3L)
//                .title("child")
//                .uniquePostId("3")
//                .authorEmail("child@email.com")
//                .authorUserName("child")
//                .message("child")
//                .dateOfCreate(100000)
//                .build();
//    }
//
//    private void addPosts(Item item) {
//        Post parent = createParentPost();
//        Post root = createRootPost();
//        Post child = createChildPost();
//
//        parent.addChildPost(root);
//        root.addChildPost(child);
//
//        item.addPost(parent);
//        item.addPost(root);
//        item.addPost(child);
//    }

//    private Item prepareTestItem() {
//        Brand brand = Brand.builder()
//                .id(1L)
//                .name("Hermes")
//                .build();
//        Category category = prepareCategories();
//        Currency currency = Currency.builder()
//                .id(1L)
//                .value("USD")
//                .build();
//        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
//                .id(1L)
//                .amount(5)
//                .charSequenceCode("abc")
//                .currency(currency)
//                .build();
//        ItemStatus itemStatus = ItemStatus.builder()
//                .id(1L)
//                .name("NEW")
//                .build();
//        ItemType itemType = ItemType.builder()
//                .id(1L)
//                .name("Clothes")
//                .build();
//        OptionGroup optionGroup = OptionGroup.builder()
//                .id(1L)
//                .name("Size")
//                .build();
//        OptionItem optionItem1 = OptionItem.builder()
//                .id(1L)
//                .optionName("L")
//                .optionGroup(optionGroup)
//                .build();
//        OptionItem optionItem2 = OptionItem.builder()
//                .id(2L)
//                .optionName("M")
//                .optionGroup(optionGroup)
//                .build();
//        Price price = Price.builder()
//                .id(1L)
//                .amount(100)
//                .currency(currency)
//                .build();
//
//        Item item = Item.builder()
//                .id(1L)
//                .name("name")
//                .articularId("100")
//                .dateOfCreate(100L)
//                .category(category)
//                .brand(brand)
//                .currencyDiscount(currencyDiscount)
//                .status(itemStatus)
//                .itemType(itemType)
//                .price(price)
//                .build();
//
//        item.addOptionItem(optionItem1);
//        item.addOptionItem(optionItem2);
//        addReview(item);
//        addPosts(item);
//
//        return item;
//    }
//
//    private UserProfile prepareTestUserProfile() {
//        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
//                .id(1L)
//                .code("+11")
//                .build();
//        ContactInfo contactInfo = ContactInfo.builder()
//                .id(1L)
//                .name("Wolter")
//                .secondName("White")
//                .phoneNumber("111-111-111")
//                .countryPhoneCode(countryPhoneCode)
//                .build();
//        Country country = Country.builder()
//                .id(1L)
//                .name("USA")
//                .build();
//        Address address = Address.builder()
//                .id(1L)
//                .country(country)
//                .street("street")
//                .buildingNumber(1)
//                .apartmentNumber(101)
//                .flor(9)
//                .zipCode("90000")
//                .build();
//        Card credit_card = Card.builder()
//                .id(1L)
//                .cardNumber("4444-1111-2222-3333")
//                .dateOfExpire("06/28")
//                .ownerName("name")
//                .ownerSecondName("secondName")
//                .isActive(CardUtil.isCardActive("06/28"))
//                .cvv("818")
//                .build();
//
//        Post parent = createParentPost();
//        Post root = createRootPost();
//        Post child = createChildPost();
//
//        return UserProfile.builder()
//                .id(1L)
//                .username("username")
//                .email("email")
//                .dateOfCreate(100)
//                .isActive(true)
//                .contactInfo(contactInfo)
//                .address(address)
//                .cardList(List.of(credit_card))
//                .postList(List.of(parent, root, child))
//                .build();
//    }
//
//    @Override
//    protected String getEmptyDataSetPath() {
//        return "/datasets/wishlist/emptyWishlistDataSet.yml";
//    }
//
//    @Override
//    protected EntityDataSet<?> getTestDataSet() {
//        Wishlist wishlist = Wishlist.builder()
//                .id(1L)
//                .dateOfUpdate(100)
//                .items(Set.of(prepareTestItem()))
//                .userProfile(prepareTestUserProfile())
//                .build();
//        return new EntityDataSet<>(wishlist, "/datasets/wishlist/testWishlistDataSet.yml");
//    }
//
//    @Override
//    protected EntityDataSet<?> getSaveDataSet() {
//        Wishlist wishlist = Wishlist.builder()
//                .dateOfUpdate(100)
//                .items(Set.of(prepareTestItem()))
//                .userProfile(prepareTestUserProfile())
//                .build();
//        return new EntityDataSet<>(wishlist, "/datasets/wishlist/saveWishlistDataSet.yml");
//    }
//
//    @Override
//    protected EntityDataSet<?> getUpdateDataSet() {
//        Item item2 = prepareTestItem();
//        item2.setId(2L);
//        item2.setDateOfCreate(200);
//        item2.setArticularId("200");
//        Wishlist wishlist = Wishlist.builder()
//                .id(1L)
//                .dateOfUpdate(200)
//                .items(Set.of(prepareTestItem(), item2))
//                .userProfile(prepareTestUserProfile())
//                .build();
//        return new EntityDataSet<>(wishlist, "/datasets/wishlist/updateWishlistDataSet.yml");
//    }
//}