package com.b2c.prototype.service.base.order.base;

import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.dao.order.IOrderItemDao;
import com.b2c.prototype.dao.user.IUserInfoDao;
import com.b2c.prototype.dao.cashed.IEntityStringMapWrapper;
import com.b2c.prototype.modal.constant.OrderStatusEnum;
import com.b2c.prototype.modal.dto.request.RequestAddressDto;
import com.b2c.prototype.modal.dto.request.RequestDeliveryDto;
import com.b2c.prototype.modal.dto.request.RequestItemDto;
import com.b2c.prototype.modal.dto.request.RequestOrderItemDto;
import com.b2c.prototype.modal.dto.request.RequestPaymentDto;
import com.b2c.prototype.modal.dto.request.RequestUserInfoDto;
import com.b2c.prototype.modal.dto.update.RequestOrderItemDtoUpdate;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.order.OrderItem;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.user.UserInfo;
import com.b2c.prototype.processor.IAsyncProcessor;
import com.b2c.prototype.processor.Task;
import com.b2c.prototype.service.base.order.IOrderItemService;
import com.b2c.prototype.service.general.AbstractGeneralEntityService;
import com.tm.core.dao.general.IGeneralEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;

import static com.b2c.prototype.util.UniqueIdUtil.getUUID;

@Slf4j
public class OrderItemService extends AbstractGeneralEntityService implements IOrderItemService {

    private ThreadLocalSessionManager sessionManager;
    private final IAsyncProcessor asyncProcessor;
    private final IOrderItemDao orderItemDao;
    private final IItemDao itemDao;
    private final IUserInfoDao userInfoDao;
    private final IEntityStringMapWrapper<DeliveryType> deliveryTypeMapWrapper;
    private final IEntityStringMapWrapper<PaymentMethod> paymentMethodMapWrapper;
    private final IEntityStringMapWrapper<OrderStatus> orderStatusEntityMapWrapper;


    public OrderItemService(IAsyncProcessor asyncProcessor,
                            IOrderItemDao orderItemDao,
                            IItemDao itemDao,
                            IUserInfoDao userInfoDao,
                            IEntityStringMapWrapper<DeliveryType> deliveryTypeMapWrapper,
                            IEntityStringMapWrapper<PaymentMethod> paymentMethodMapWrapper,
                            IEntityStringMapWrapper<OrderStatus> orderStatusEntityMapWrapper) {
        this.asyncProcessor = asyncProcessor;
        this.orderItemDao = orderItemDao;
        this.itemDao = itemDao;
        this.userInfoDao = userInfoDao;
        this.deliveryTypeMapWrapper = deliveryTypeMapWrapper;
        this.paymentMethodMapWrapper = paymentMethodMapWrapper;
        this.orderStatusEntityMapWrapper = orderStatusEntityMapWrapper;
    }

    @Override
    protected IGeneralEntityDao getEntityDao() {
        return this.orderItemDao;
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
//                    .itemList((List<Item>) processResultMap.get(List.class))
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
        Parameter parameter =
                parameterFactory.createStringParameter("order_id", requestOrderItemDtoUpdate.getSearchField());
        super.deleteEntity(parameter);
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
//                            .category(requestAddressDto.getCountry())
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
                    CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                            .build();

                    Payment payment = Payment.builder()
                            .paymentMethod(paymentMethod)
//                            .amount(requestPaymentDto.getAmount())
                            .currencyDiscount(currencyDiscount)
                            .build();
                    return payment;
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
                    return getEntityDao().getGeneralEntityList(parameters);
                },
                List.class
        );

        Task userInfoTask = new Task(
                () -> {
                    List<RequestUserInfoDto> requestItemDtoList = requestOrderItemDto.getRequestUserInfoDtoList();
                    Parameter[] parameters = requestItemDtoList.stream()
                            .map(requestItemDto -> new Parameter("name", requestItemDto.getName()))
                            .toArray(Parameter[]::new);
                    return getEntityDao().getGeneralEntityList(parameters);
                },
                List.class
        );

        return asyncProcessor.process(orderItemStatusTask, deliveryTask, paymentTask, itemTask, userInfoTask);
    }

}
