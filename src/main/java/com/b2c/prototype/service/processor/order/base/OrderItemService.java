package com.b2c.prototype.service.processor.order.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.dao.order.IOrderItemDao;
import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.modal.constant.OrderStatusEnum;
import com.b2c.prototype.modal.dto.request.AddressDto;
import com.b2c.prototype.modal.dto.request.RequestDeliveryDto;
import com.b2c.prototype.modal.dto.request.RequestItemDto;
import com.b2c.prototype.modal.dto.request.RequestOrderItemDto;
import com.b2c.prototype.modal.dto.request.RequestPaymentDto;
import com.b2c.prototype.modal.dto.request.ContactInfoDto;
import com.b2c.prototype.modal.dto.update.OrderItemDtoUpdate;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.processor.IAsyncProcessor;
import com.b2c.prototype.processor.Task;
import com.b2c.prototype.service.processor.order.IOrderItemService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;

public class OrderItemService implements IOrderItemService {

    private final IAsyncProcessor asyncProcessor;
    private final IOrderItemDao orderItemDao;
    private final IItemDao itemDao;
    private final IContactInfoDao contactInfoDao;
    private final IEntityCachedMap entityCachedMap;


    public OrderItemService(IAsyncProcessor asyncProcessor,
                            IOrderItemDao orderItemDao,
                            IItemDao itemDao,
                            IContactInfoDao contactInfoDao,
                            IEntityCachedMap entityCachedMap) {
        this.asyncProcessor = asyncProcessor;
        this.orderItemDao = orderItemDao;
        this.itemDao = itemDao;
        this.contactInfoDao = contactInfoDao;
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void createOrderItem(RequestOrderItemDto requestOrderItemDto) {
        Transaction transaction = null;
//        try (Session session = sess.getSession()) {
//            transaction = session.beginTransaction();
//            Map<Class<?>, Object> processResultMap = executeAsyncProcess(requestOrderItemDto);
//
//
//
//            OrderItem orderItem = OrderItem.builder()
//                    .orderId(getUUID())
//                    .dateOfCreate(System.currentTimeMillis())
////                    .itemList((List<Item>) processResultMap.get(List.class))
//                    .delivery((Delivery) processResultMap.get(Delivery.class))
////                    .userInfoList((List<ContactInfo>) processResultMap.get(List.class))
//                    .orderStatus((OrderStatus) processResultMap.get(OrderStatus.class))
//                    .payment((Payment) processResultMap.get(Payment.class))
//                    .note(requestOrderItemDto.getNote())
//                    .build();
//
//            session.persist(orderItem);
//
//            transaction.commit();
//        } catch (Exception e) {
//            log.warn("transaction error {}", e.getMessage());
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            throw new RuntimeException(e);
//        } finally {
//            sessionManager.closeSession();
//        }
    }

    @Override
    public void updateOrderItem(OrderItemDtoUpdate requestOrderItemDtoUpdate) {

    }

    @Override
    public void deleteOrderItem(OrderItemDtoUpdate requestOrderItemDtoUpdate) {
//        Parameter parameter = parameterFactory
//                .createStringParameter("order_id", requestOrderItemDtoUpdate.getSearchField());
//        super.deleteEntity(parameter);
    }


    private Map<Class<?>, Object> executeAsyncProcess(RequestOrderItemDto requestOrderItemDto) {
        Task orderItemStatusTask = new Task(
                () -> entityCachedMap.getEntity(OrderStatus.class, "value", OrderStatusEnum.NEW.name()),
                OrderStatus.class
        );

        Task deliveryTask = new Task(
                () -> {
                    RequestDeliveryDto requestDeliveryDto = requestOrderItemDto.getRequestDeliveryDto();
                    AddressDto addressDto = requestDeliveryDto.getDeliveryAddressDto();
                    DeliveryType deliveryType =
                            entityCachedMap.getEntity(OrderStatus.class, "value", requestDeliveryDto.getDeliveryType());
                    Address address = Address.builder()
//                            .category(requestAddressDto.getCountry())
                            .street(addressDto.getStreet())
                            .buildingNumber(addressDto.getBuildingNumber())
                            .apartmentNumber(addressDto.getApartmentNumber())
                            .flor(addressDto.getFlor())
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
                            entityCachedMap.getEntity(OrderStatus.class, "value", requestPaymentDto.getPaymentMethod());
                    CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                            .build();

                    return Payment.builder()
                            .paymentMethod(paymentMethod)
//                            .amount(requestPaymentDto.getAmount())
                            .currencyDiscount(currencyDiscount)
                            .build();
                },
                Payment.class
        );

        Task itemTask = new Task(
                () -> {
                    List<RequestItemDto> requestItemDtoList = requestOrderItemDto.getRequestItemDtoList();
                    Parameter[] parameters = requestItemDtoList.stream()
                            .map(requestItemDto ->
                                    new Parameter("articularId", requestItemDto.getArticularId()))
                            .toArray(Parameter[]::new);
//                    return getEntityDao().getGeneralEntityList(parameters);
                    return null;
                },
                List.class
        );

        Task contactInfoTask = new Task(
                () -> {
                    List<ContactInfoDto> requestItemDtoList = requestOrderItemDto.getContactInfoDtoList();
                    Parameter[] parameters = requestItemDtoList.stream()
                            .map(requestItemDto -> new Parameter("name", requestItemDto.getName()))
                            .toArray(Parameter[]::new);
//                    return getEntityDao().getGeneralEntityList(parameters);
                    return null;
                },
                List.class
        );

        return asyncProcessor.process(orderItemStatusTask, deliveryTask, paymentTask, itemTask, contactInfoTask);
    }

}
