package com.b2c.prototype.service.processor.price.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.price.IPriceDao;
import com.b2c.prototype.modal.dto.request.PriceDto;
import com.b2c.prototype.modal.dto.request.PriceDtoSearchField;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.order.OrderItem;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.tm.core.processor.finder.factory.IParameterFactory;
import jakarta.websocket.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class PriceServiceTest {

    @Mock
    private IParameterFactory parameterFactory;
    @Mock
    private IPriceDao priceDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private IEntityCachedMap entityCachedMap;
    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveFullPriceByOrderId() {
        // Arrange
        PriceDtoSearchField priceDtoSearchField = new PriceDtoSearchField();
        priceDtoSearchField.setSearchField("order123");
        priceDtoSearchField.setNewEntityDto(PriceDto.builder()
                .amount(100.0)
                .currency("USD")
                .build());

        OrderItem orderItem = mock(OrderItem.class);
        Payment payment = mock(Payment.class);
        Session session = mock(Session.class);

        when(queryService.getEntity(eq(OrderItem.class), any())).thenReturn(orderItem);
        when(orderItem.getPayment()).thenReturn(payment);

        // Act
        priceService.saveFullPriceByOrderId(priceDtoSearchField);

        // Assert
//        verify(commonSingleEntityService).saveEntity(any(Consumer.class));
//        verify(session, never()).merge(payment); // Simulate session persistence.
    }

    @Test
    void testSaveTotalPriceByArticularId() {
        // Arrange
        PriceDtoSearchField priceDtoSearchField = new PriceDtoSearchField();
        priceDtoSearchField.setSearchField("item123");
        priceDtoSearchField.setNewEntityDto(PriceDto.builder()
                .amount(100.0)
                .currency("USD")
                .build());

        ItemData itemData = mock(ItemData.class);
        Session session = mock(Session.class);

        when(queryService.getEntity(eq(ItemData.class), any())).thenReturn(itemData);

        // Act
        priceService.saveTotalPriceByArticularId(priceDtoSearchField);

        // Assert
//        verify(commonSingleEntityService).saveEntity(any(Consumer.class));
//        verify(session, never()).merge(itemData); // Simulate session persistence.
    }

//    @Test
//    void savePrice_shouldSavePrice() {
//        PriceDto priceDto = createTestPriceDto();
//        Currency currency = creatTestCurrency();
//        when(entityCachedMap.getEntity(Currency.class, "value", priceDto.getCurrency())).thenReturn(currency);
//
//        priceService.savePrice(priceDto);
//
//        ArgumentCaptor<Price> captor = ArgumentCaptor.forClass(Price.class);
//        verify(priceDao).saveEntity(captor.capture());
//        Price capturedEntity = captor.getValue();
//        assertEquals(priceDto.getAmount(), capturedEntity.getAmount());
//        assertEquals(currency, capturedEntity.getCurrency());
//    }
//
//    @Test
//    void updatePrice_shouldUpdatePrice() {
//        PriceDto newDto = PriceDto.builder()
//                .amount(150.0)
//                .currency("USA")
//                .build();
//        PriceSearchFieldDto searchFieldDto = new PriceSearchFieldDto();
//        searchFieldDto.setSearchField("ITEM123");
//        searchFieldDto.setNewEntityDto(newDto);
//        Currency currency = creatTestCurrency();
//        when(entityCachedMap.getEntity(Currency.class, "value", newDto.getCurrency())).thenReturn(currency);
//        Parameter mockParameter = mock(Parameter.class);
//        when(parameterFactory.createStringParameter("articularId", searchFieldDto.getSearchField())).thenReturn(mockParameter);
//
//        priceService.updatePrice(searchFieldDto);
//
//        ArgumentCaptor<Price> captor = ArgumentCaptor.forClass(Price.class);
//        verify(priceDao).findEntityAndUpdate(captor.capture(), eq(mockParameter));
//        Price capturedEntity = captor.getValue();
//        assertEquals(newDto.getAmount(), capturedEntity.getAmount());
//        assertEquals(currency, capturedEntity.getCurrency());
//    }
//
//    @Test
//    void deletePrice_shouldDeletePrice() {
//        PriceSearchFieldDto searchFieldDto = new PriceSearchFieldDto();
//        searchFieldDto.setSearchField("ITEM123");
//        searchFieldDto.setNewEntityDto(null);
//
//        Parameter mockParameter = mock(Parameter.class);
//        when(parameterFactory.createStringParameter("articularId", searchFieldDto.getSearchField())).thenReturn(mockParameter);
//
//        priceService.deletePrice(searchFieldDto);
//
//        verify(priceDao).findEntityAndDelete(mockParameter);
//    }
//
//    @Test
//    void getPrice_shouldReturnPriceDto() {
//        String articularId = "ITEM123";
//        Parameter mockParameter = mock(Parameter.class);
//        when(parameterFactory.createStringParameter("articularId", articularId)).thenReturn(mockParameter);
//        Price price = Price.builder()
//                .amount(100.0)
//                .currency(creatTestCurrency())
//                .build();
//        ItemData itemData = ItemData.builder()
//                .articularId(articularId)
//                .fullPrice(price)
//                .totalPrice(price)
//                .build();
//        when(priceDao.getEntity(ItemData.class, mockParameter)).thenReturn(itemData);
//
//        PriceDto result = priceService.getPrice(articularId);
//
//        assertNotNull(result);
//        assertEquals(price.getAmount(), result.getAmount());
//        assertEquals(price.getCurrency().getValue(), result.getCurrency());
//    }
//
//    @Test
//    void getOptionPrice_shouldReturnOptionalPriceDto() {
//        String articularId = "ITEM123";
//        Parameter mockParameter = mock(Parameter.class);
//        when(parameterFactory.createStringParameter("articularId", articularId)).thenReturn(mockParameter);
//        Price price = Price.builder()
//                .amount(100.0)
//                .currency(creatTestCurrency())
//                .build();
//        ItemData itemData = ItemData.builder()
//                .articularId(articularId)
//                .fullPrice(price)
//                .totalPrice(price)
//                .build();
//        when(priceDao.getEntity(ItemData.class, mockParameter)).thenReturn(itemData);
//
//        Optional<PriceDto> result = priceService.getOptionPrice(articularId);
//
//        assertTrue(result.isPresent());
//        assertEquals(price.getAmount(), result.get().getAmount());
//        assertEquals(price.getCurrency().getValue(), result.get().getCurrency());
//    }
//
//    @Test
//    void getPrices_shouldReturnListOfPriceDtos() {
//        List<PriceDto> expectedList = List.of(
//                createTestPriceDto(),
//                PriceDto.builder()
//                        .currency("EUR")
//                        .amount(200.0)
//                        .build()
//        );
//        when(priceDao.getEntityList()).thenReturn(List.of(
//                createTestPriceDto(),
//                PriceDto.builder()
//                        .currency("EUR")
//                        .amount(200.0)
//                        .build()
//        ));
//
//        List<PriceDto> result = priceService.getPrices();
//
//        assertEquals(expectedList, result);
//    }
//
//    private PriceDto createTestPriceDto() {
//        return PriceDto.builder()
//                .amount(100.0)
//                .currency("USA")
//                .build();
//    }
//
//    private Currency creatTestCurrency() {
//        return Currency.builder()
//                .value("USA")
//                .build();
//    }
}