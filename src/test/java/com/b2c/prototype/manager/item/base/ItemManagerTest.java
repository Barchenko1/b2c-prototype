//package com.b2c.prototype.manager.item.base;
//
//import com.b2c.prototype.dao.item.IItemDao;
//import com.b2c.prototype.modal.dto.payload.ItemDto;
//import com.b2c.prototype.modal.entity.item.ArticularItem;
//import com.b2c.prototype.modal.entity.item.ArticularStatus;
//import com.b2c.prototype.modal.entity.item.Brand;
//import com.b2c.prototype.modal.entity.item.Category;
//import com.b2c.prototype.modal.entity.item.Discount;
//import com.b2c.prototype.modal.entity.item.Item;
//import com.b2c.prototype.modal.entity.item.ItemData;
//import com.b2c.prototype.modal.entity.item.ItemType;
//import com.b2c.prototype.modal.entity.option.OptionGroup;
//import com.b2c.prototype.modal.entity.option.OptionItem;
//import com.b2c.prototype.modal.entity.price.Currency;
//import com.b2c.prototype.modal.entity.price.Price;
//import com.b2c.prototype.service.function.ITransformationFunctionService;
//
//import com.tm.core.finder.parameter.Parameter;
//import org.hibernate.Session;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.function.Supplier;
//
//import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//class ItemManagerTest {
//
//    @Mock
//    private IItemDao itemDao;
//    @Mock
//    private ISearchService queryService;
//    @Mock
//    private ITransformationFunctionService transformationFunctionService;
//    @Mock
//    private ISupplierService supplierService;
//    @InjectMocks
//    private ItemManager itemManager;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void saveUpdateItem_shouldSaveOrUpdateItem() {
//        Item item = mock(Item.class);
//        ItemDto itemDto = mock(ItemDto.class);
//        ItemData itemData = mock(ItemData.class);
//        String articularId = "articularId";
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
//        when(supplierService.parameterStringSupplier(ARTICULAR_ID, articularId))
//                .thenReturn(parameterSupplier);
////        when(queryService.getEntity(ItemData.class, parameterSupplier))
////                .thenReturn(itemData);
//        when(transformationFunctionService.getEntity(Item.class, itemDto))
//                .thenReturn(item);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            Session session = mock(Session.class);
//            consumer.accept(session);
//            verify(session).merge(item);
//            return null;
//        }).when(itemDao).executeConsumer(any(Consumer.class));
//
//        itemManager.saveUpdateItem(articularId, itemDto);
//
//        verify(itemDao).executeConsumer(any(Consumer.class));
//    }
//
//    @Test
//    void deleteItem_shouldDeleteItem() {
//        Item item = mock(Item.class);
//        Supplier<Item> itemSupplier = () -> item;
//        String articularId = "test-articular-id";
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
//        when(supplierService.parameterStringSupplier(ARTICULAR_ID, articularId))
//                .thenReturn(parameterSupplier);
//        Function<ItemData, Item> function = mock(Function.class);
//        when(transformationFunctionService.getTransformationFunction(ItemData.class, Item.class))
//                .thenReturn(function);
////        when(supplierService.entityFieldSupplier(
////                ItemData.class,
////                parameterSupplier,
////                function
////        )).thenReturn(itemSupplier);
//        itemManager.deleteItem(articularId);
//
//        verify(itemDao).deleteEntity(itemSupplier);
//    }
//
//    @Test
//    void getItemByItemId_shouldReturnItem() {
//        String articularId = "test-articular-id";
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
//        Item item = mock(Item.class);
//        when(supplierService.parameterStringSupplier(ARTICULAR_ID, articularId))
//                .thenReturn(parameterSupplier);
//        Function<ItemData, Item> function = mock(Function.class);
//        when(transformationFunctionService.getTransformationFunction(ItemData.class, Item.class))
//                .thenReturn(function);
////        when(queryService.getEntityDto(
////                ItemData.class,
////                parameterSupplier,
////                function)).thenReturn(item);
//
//        Item result = itemManager.getItemByItemId(articularId);
//
//        assertNotNull(result);
//        assertEquals(item, result);
////        verify(queryService).getEntityDto(
////                eq(ItemData.class),
////                any(),
////                any());
//    }
//
//    private ItemDto getItemDto() {
//        return ItemDto.builder()
//                .itemId("test-item-id")
//                .build();
//    }
//
//    private Item getItem() {
//        return Item.builder()
//                .id(1L)
//                .articularItem(prepareArticularItem())
//                .build();
//    }
//
//    private Category prepareCategories() {
//        Category parent = Category.builder()
//                .id(1L)
//                .label("parent")
//                .value("parent")
//                .build();
//        Category root = Category.builder()
//                .id(2L)
//                .label("root")
//                .value("root")
//                .parent(parent)
//                .build();
//        Category child = Category.builder()
//                .id(3L)
//                .label("child")
//                .value("child")
//                .build();
//
//        parent.setChildList(List.of(root));
//        root.setParent(parent);
//        root.setChildList(List.of(child));
//        child.setParent(root);
//
//        return child;
//    }
//
//    private ItemData prepareToItemData() {
//        Brand brand = Brand.builder()
//                .id(1L)
//                .value("Hermes")
//                .build();
//        Category category = prepareCategories();
//        Currency currency = Currency.builder()
//                .id(1L)
//                .label("USD")
//                .value("USD")
//                .build();
//        Discount discount = Discount.builder()
//                .id(1L)
//                .amount(5)
//                .charSequenceCode("abc")
//                .isPercent(false)
//                .isActive(true)
//                .currency(currency)
//                .build();
//        ArticularStatus articularStatus = ArticularStatus.builder()
//                .id(1L)
//                .value("NEW")
//                .build();
//        ItemType itemType = ItemType.builder()
//                .id(1L)
//                .value("Clothes")
//                .build();
//        OptionGroup optionGroup = OptionGroup.builder()
//                .id(1L)
//                .value("Size")
//                .build();
//        OptionItem optionItem1 = OptionItem.builder()
//                .id(1L)
//                .value("L")
//                .label("L")
//                .optionGroup(optionGroup)
//                .build();
//        OptionItem optionItem2 = OptionItem.builder()
//                .id(2L)
//                .value("M")
//                .optionGroup(optionGroup)
//                .build();
//        Price price = Price.builder()
//                .id(1L)
//                .amount(100)
//                .currency(currency)
//                .build();
//
//        return ItemData.builder()
//                .id(1L)
//                .category(category)
//                .brand(brand)
//                .itemType(itemType)
//                .build();
//    }
//
//    private ArticularItem prepareArticularItem() {
//        return ArticularItem.builder()
//
//                .build();
//    }
//}
