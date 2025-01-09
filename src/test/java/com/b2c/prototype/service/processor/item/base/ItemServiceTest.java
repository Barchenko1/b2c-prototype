package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.ItemDto;
import com.b2c.prototype.modal.dto.searchfield.ItemSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.ITEM_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private IItemDao itemDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUpdateItem_shouldSaveOrUpdateItem() {
        Item item = mock(Item.class);
        ItemDto itemDto = mock(ItemDto.class);
        ItemData itemData = mock(ItemData.class);
        ItemSearchFieldEntityDto itemSearchFieldEntityDto = ItemSearchFieldEntityDto.builder()
                .searchField("test-item-id")
                .newEntity(itemDto)
                .build();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ITEM_ID, itemSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemData.class, parameterSupplier))
                .thenReturn(itemData);
        when(transformationFunctionService.getEntity(Item.class, itemSearchFieldEntityDto.getNewEntity()))
                .thenReturn(item);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(item);
            return null;
        }).when(itemDao).executeConsumer(any(Consumer.class));

        itemService.saveUpdateItem(itemSearchFieldEntityDto);

        verify(itemDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void deleteItem_shouldDeleteItem() {
        Item item = mock(Item.class);
        Supplier<Item> itemSupplier = () -> item;
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("test-item-id");

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ITEM_ID, oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        Function<ItemData, Item> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemData.class, Item.class))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                ItemData.class,
                parameterSupplier,
                function
        )).thenReturn(itemSupplier);
        itemService.deleteItem(oneFieldEntityDto);

        verify(itemDao).deleteEntity(itemSupplier);
    }

    @Test
    void getItemByItemId_shouldReturnItem() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("test-item-id");

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        Item item = mock(Item.class);
        when(supplierService.parameterStringSupplier(ITEM_ID, oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        Function<ItemData, Item> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemData.class, Item.class))
                .thenReturn(function);
        when(queryService.getEntityDto(
                ItemData.class,
                parameterSupplier,
                function)).thenReturn(item);

        Item result = itemService.getItemByItemId(oneFieldEntityDto);

        assertNotNull(result);
        assertEquals(item, result);
        verify(queryService).getEntityDto(
                eq(ItemData.class),
                any(),
                any());
    }

    private ItemDto getItemDto() {
        return ItemDto.builder()
                .itemId("test-item-id")
                .build();
    }

    private Item getItem() {
        return Item.builder()
                .id(1L)
                .itemData(prepareToItemData())
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

    private ItemData prepareToItemData() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .build();
        Category category = prepareCategories();
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        Discount discount = Discount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .isPercent(false)
                .isActive(true)
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
        OptionItem optionItem1 = OptionItem.builder()
                .id(1L)
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
        OptionItem optionItem2 = OptionItem.builder()
                .id(2L)
                .optionName("M")
                .optionGroup(optionGroup)
                .build();
        Price price = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();

        return ItemData.builder()
                .id(1L)
                .category(category)
                .brand(brand)
                .status(itemStatus)
                .itemType(itemType)
                .build();
    }
}
