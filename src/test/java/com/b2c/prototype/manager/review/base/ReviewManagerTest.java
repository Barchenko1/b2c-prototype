package com.b2c.prototype.manager.review.base;

import com.b2c.prototype.dao.review.IReviewDao;
import com.b2c.prototype.manager.review.base.ReviewManager;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.ReviewDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;
import com.b2c.prototype.modal.dto.searchfield.ReviewSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.ITEM_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReviewManagerTest {

    @Mock
    private IReviewDao reviewDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private ReviewManager reviewManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateReview_addsNewReview() {
        Session session = mock(Session.class);
        ReviewDto reviewDto = getReviewDto();
        ReviewSearchFieldEntityDto searchFieldDto = ReviewSearchFieldEntityDto.builder()
                .searchField("searchField")
                .newEntity(reviewDto)
                .build();

        Item item = mock(Item.class);
        Review review = getReview();
        when(item.getReviews()).thenReturn(new ArrayList<>(){{add(review);}});

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ITEM_ID, searchFieldDto.getSearchField()))
                .thenReturn(parameterSupplier);
        NativeQuery<Item> query = mock(NativeQuery.class);
        when(session.createNativeQuery(anyString(), eq(Item.class))).thenReturn(query);
        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(item);
        when(transformationFunctionService.getEntity(Review.class, reviewDto)).thenReturn(review);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(item);
            return null;
        }).when(reviewDao).executeConsumer(any(Consumer.class));

        reviewManager.saveUpdateReview(searchFieldDto);

        verify(reviewDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateReview_updatesExistingReview() {
        Session session = mock(Session.class);
        ReviewDto reviewDto = getReviewDto();
        reviewDto.setReviewId(null);
        ReviewSearchFieldEntityDto searchFieldDto = ReviewSearchFieldEntityDto.builder()
                .searchField("searchField")
                .newEntity(reviewDto)
                .build();

        Item item = mock(Item.class);
        Review review = getReview();
        when(item.getReviews()).thenReturn(new ArrayList<>(){{add(review);}});

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ITEM_ID, searchFieldDto.getSearchField()))
                .thenReturn(parameterSupplier);
        NativeQuery<Item> query = mock(NativeQuery.class);
        when(session.createNativeQuery(anyString(), eq(Item.class))).thenReturn(query);
        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(item);
        when(transformationFunctionService.getEntity(Review.class, reviewDto)).thenReturn(review);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(item);
            return null;
        }).when(reviewDao).executeConsumer(any(Consumer.class));

        reviewManager.saveUpdateReview(searchFieldDto);

        verify(reviewDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteReview_removesReviewFromEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("123");
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        Review review = getReview();
        Supplier<Review> reviewSupplier = () -> review;

        when(supplierService.parameterStringSupplier(ITEM_ID, dto.getValue()))
                .thenReturn(parameterSupplier);
        Function<ItemData, Review> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemData.class, Review.class))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                ItemData.class,
                parameterSupplier,
                function
        )).thenReturn(reviewSupplier);

        reviewManager.deleteReview(dto);

        verify(reviewDao).deleteEntity(reviewSupplier);
    }

    @Test
    void testGetReviewListByItemId_returnsListOfReviews() {
        OneFieldEntityDto dto = new OneFieldEntityDto("item-123");
        Review review = getReview();
        ResponseReviewDto responseReviewDto = getResponseReviewDto();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        List<ResponseReviewDto> expectedResponse = List.of(responseReviewDto);
        when(supplierService.parameterStringSupplier(ITEM_ID, dto.getValue()))
                .thenReturn(parameterSupplier);
        Function<Review, Collection<ResponseReviewDto>> function = mock(Function.class);
        when(transformationFunctionService.getTransformationCollectionFunction(Review.class, ResponseReviewDto.class))
                .thenReturn(function);
        when(function.apply(review)).thenReturn(expectedResponse);
        when(queryService.getEntityDto(Review.class, parameterSupplier, function)).thenReturn(expectedResponse);

        List<ResponseReviewDto> actualResponse = reviewManager.getReviewListByItemId(dto);

        assertEquals(1, actualResponse.size());
        assertEquals(responseReviewDto, actualResponse.get(0));
    }

    private Rating getRating() {
        return Rating.builder()
                .value(5)
                .build();
    }

    private Review getReview() {
        return Review.builder()
                .uniqueId("review_id")
                .title("title")
                .message("message")
                .dateOfCreate(100)
                .rating(getRating())
                .build();
    }

    private ReviewDto getReviewDto() {
        return ReviewDto.builder()
                .reviewId("review_id")
                .title("title")
                .message("message")
                .ratingValue(5)
                .build();
    }

    private ResponseReviewDto getResponseReviewDto() {
        return ResponseReviewDto.builder()
                .title("title")
                .message("message")
                .dateOfCreate(new Date())
                .ratingValue(5)
                .build();
    }
}
