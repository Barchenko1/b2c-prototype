package com.b2c.prototype.service.order.base;

import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.dao.order.IOrderItemDao;
import com.b2c.prototype.dao.user.IUserInfoDao;
import com.b2c.prototype.dao.wrapper.IEntityStringMapWrapper;
import com.b2c.prototype.modal.constant.OrderStatusEnum;
import com.b2c.prototype.modal.dto.request.RequestAddressDto;
import com.b2c.prototype.modal.dto.request.RequestDeliveryDto;
import com.b2c.prototype.modal.dto.request.RequestItemDto;
import com.b2c.prototype.modal.dto.request.RequestOrderItemDto;
import com.b2c.prototype.modal.dto.request.RequestPaymentDto;
import com.b2c.prototype.modal.dto.update.RequestOrderItemDtoUpdate;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.order.OrderItem;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.user.UserInfo;
import com.b2c.prototype.processor.IAsyncProcessor;
import com.b2c.prototype.processor.Task;
import com.b2c.prototype.service.order.IOrderItemService;
import com.tm.core.processor.ThreadLocalSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;

import static com.b2c.prototype.util.Query.DELETE_ORDER_BY_ORDER_ID;
import static com.b2c.prototype.util.Query.SELECT_ITEM_BY_ARTICLE;
import static com.b2c.prototype.util.UniqueIdUtil.getUUID;

@Slf4j
public class OrderItemService implements IOrderItemService {

    private final ThreadLocalSessionManager sessionManager;
    private final IAsyncProcessor asyncProcessor;
    private final IOrderItemDao orderItemDao;
    private final IItemDao itemDao;
    private final IUserInfoDao userInfoDao;
    private final IEntityStringMapWrapper<DeliveryType> deliveryTypeMapWrapper;
    private final IEntityStringMapWrapper<PaymentMethod> paymentMethodMapWrapper;
    private final IEntityStringMapWrapper<OrderStatus> orderStatusEntityMapWrapper;


    public OrderItemService(ThreadLocalSessionManager sessionManager,
                            IAsyncProcessor asyncProcessor,
                            IOrderItemDao orderItemDao,
                            IItemDao itemDao, IUserInfoDao userInfoDao, IEntityStringMapWrapper<DeliveryType> deliveryTypeMapWrapper, IEntityStringMapWrapper<PaymentMethod> paymentMethodMapWrapper,
                            IEntityStringMapWrapper<OrderStatus> orderStatusEntityMapWrapper) {
        this.sessionManager = sessionManager;
        this.asyncProcessor = asyncProcessor;
        this.orderItemDao = orderItemDao;
        this.itemDao = itemDao;
        this.userInfoDao = userInfoDao;
        this.deliveryTypeMapWrapper = deliveryTypeMapWrapper;
        this.paymentMethodMapWrapper = paymentMethodMapWrapper;
        this.orderStatusEntityMapWrapper = orderStatusEntityMapWrapper;
    }


    @Override
    public void createOrderItem(RequestOrderItemDto requestOrderItemDto) {
        Transaction transaction = null;
        try (Session session = sessionManager.getSession()) {
            transaction = session.beginTransaction();
            Map<Class<?>, Object> processResultMap = executeAsyncProcess(requestOrderItemDto);



            OrderItem orderItem = OrderItem.builder()
                    .orderId(getUUID())
                    .dateOfCreate(System.currentTimeMillis())
                    .itemList((List<Item>) processResultMap.get(List.class))
                    .delivery((Delivery) processResultMap.get(Delivery.class))
                    .userInfoList((List<UserInfo>) processResultMap.get(List.class))
                    .orderStatus((OrderStatus) processResultMap.get(OrderStatus.class))
                    .payment((Payment) processResultMap.get(Payment.class))
                    .note(requestOrderItemDto.getNote())
                    .build();

            session.persist(orderItem);

            transaction.commit();
        } catch (Exception e) {
            log.warn("transaction error {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            sessionManager.closeSession();
        }
    }

    @Override
    public void updateOrderItem(RequestOrderItemDtoUpdate requestOrderItemDtoUpdate) {

        Transaction transaction = null;
        try (Session session = sessionManager.getSession()) {
            transaction = session.beginTransaction();



            transaction.commit();
        } catch (Exception e) {
            log.warn("transaction error {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            sessionManager.closeSession();
        }
    }

    @Override
    public void deleteOrderItem(RequestOrderItemDtoUpdate requestOrderItemDtoUpdate) {
        orderItemDao.mutateEntityBySQLQueryWithParams(DELETE_ORDER_BY_ORDER_ID, requestOrderItemDtoUpdate.getSearchField());
    }


    private Map<Class<?>, Object> executeAsyncProcess(RequestOrderItemDto requestOrderItemDto) {
        Task orderItemStatusTask = new Task(
                () -> orderStatusEntityMapWrapper.getEntity(OrderStatusEnum.NEW.name()),
                OrderStatus.class
        );

        Task deliveryTask = new Task(
                () -> {
                    RequestDeliveryDto requestDeliveryDto = requestOrderItemDto.getRequestDeliveryDto();
                    RequestAddressDto requestAddressDto = requestDeliveryDto.getDeliveryAddressDto();
                    DeliveryType deliveryType =
                            deliveryTypeMapWrapper.getEntity(requestDeliveryDto.getDeliveryType());
                    Address address = Address.builder()
                            .country(requestAddressDto.getCountry())
                            .street(requestAddressDto.getStreet())
                            .buildingNumber(requestAddressDto.getBuildingNumber())
                            .apartmentNumber(requestAddressDto.getApartmentNumber())
                            .flor(requestAddressDto.getFlor())
                            .build();

                    Delivery delivery = Delivery.builder()
                            .deliveryType(deliveryType)
                            .address(address)
                            .build();
                    return delivery;
                },
                Delivery.class
        );

        Task paymentTask = new Task(
                () -> {
                    RequestPaymentDto requestPaymentDto = requestOrderItemDto.getRequestPaymentDto();
                    PaymentMethod paymentMethod =
                            paymentMethodMapWrapper.getEntity(requestPaymentDto.getPaymentMethod());
                    Discount discount = Discount.builder()
                            .build();

                    Payment payment = Payment.builder()
                            .paymentMethod(paymentMethod)
                            .amount(requestPaymentDto.getAmount())
                            .discount(discount)
                            .build();
                    return payment;
                },
                Payment.class
        );

        Task itemTask = new Task(
                () -> {
                    List<RequestItemDto> requestItemDtoList = requestOrderItemDto.getRequestItemDtoList();
                    return itemDao.getEntityListBySQLQueryWithParams(SELECT_ITEM_BY_ARTICLE, requestItemDtoList);
                },
                List.class
        );

        Task userInfoTask = new Task(
                () -> {
                    List<RequestItemDto> requestItemDtoList = requestOrderItemDto.getRequestItemDtoList();
                    return itemDao.getEntityListBySQLQueryWithParams(SELECT_ITEM_BY_ARTICLE, requestItemDtoList);
                },
                List.class
        );

        return asyncProcessor.process(orderItemStatusTask, deliveryTask, paymentTask, itemTask, userInfoTask);
    }

}
