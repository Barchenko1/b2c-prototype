package com.b2c.prototype.service.delivery.base;

import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.dao.wrapper.IEntityStringMapWrapper;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.RequestAddressDto;
import com.b2c.prototype.modal.dto.request.RequestDeliveryDto;
import com.b2c.prototype.modal.dto.update.RequestDeliveryDtoUpdate;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.processor.IAsyncProcessor;
import com.b2c.prototype.processor.Task;
import com.b2c.prototype.service.delivery.IDeliveryService;
import com.tm.core.processor.ThreadLocalSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;
import java.util.Optional;

import static com.b2c.prototype.util.Query.DELETE_DELIVERY_BY_ORDER_ID;
import static com.b2c.prototype.util.Query.SELECT_DELIVERY_BY_ORDER_ID;

@Slf4j
public class DeliveryService implements IDeliveryService {

    private final ThreadLocalSessionManager sessionManager;
    private final IAsyncProcessor asyncProcessor;
    private final IDeliveryDao deliveryDao;
    private final IEntityStringMapWrapper<DeliveryType> deliveryTypeEntityMapWrapper;

    public DeliveryService(ThreadLocalSessionManager sessionManager,
                           IAsyncProcessor asyncProcessor,
                           IDeliveryDao deliveryDao,
                           IEntityStringMapWrapper<DeliveryType> deliveryTypeEntityMapWrapper) {
        this.sessionManager = sessionManager;
        this.asyncProcessor = asyncProcessor;
        this.deliveryDao = deliveryDao;
        this.deliveryTypeEntityMapWrapper = deliveryTypeEntityMapWrapper;
    }

    @Override
    public void saveDelivery(RequestDeliveryDto requestDeliveryDto) {
        RequestAddressDto requestAddressDto = requestDeliveryDto.getDeliveryAddressDto();
        Map<Class<?>, Object> resultProcessMap = executeAsyncProcess(requestDeliveryDto);

        Address address = Address.builder()
                .country(requestAddressDto.getCountry())
                .flor(requestAddressDto.getFlor())
                .apartmentNumber(requestAddressDto.getApartmentNumber())
                .buildingNumber(requestAddressDto.getBuildingNumber())
                .street(requestAddressDto.getStreet())
                .build();

        Delivery delivery = Delivery.builder()
                .deliveryType((DeliveryType) resultProcessMap.get(DeliveryType.class))
                .address(address)
                .build();

        Transaction transaction = null;
        try (Session session = sessionManager.getSession()) {
            transaction = session.beginTransaction();
            session.persist(address);
            session.persist(delivery);
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
    public void updateDelivery(RequestDeliveryDtoUpdate requestDeliveryDtoUpdate) {
        RequestDeliveryDto requestDeliveryDto = requestDeliveryDtoUpdate.getNewEntityDto();
        String requestDeliveryTypeDto = requestDeliveryDto.getDeliveryType();
        RequestAddressDto requestAddressDto = requestDeliveryDto.getDeliveryAddressDto();

        Map<Class<?>, Object> resultProcessMap = executeAsyncProcess(requestDeliveryDtoUpdate);

        Optional<String> optionalDeliveryTypeName = Optional.ofNullable(requestDeliveryTypeDto);
        Optional<RequestAddressDto> optionalAddressDto = Optional.ofNullable(requestAddressDto);
        Transaction transaction = null;
        try (Session session = sessionManager.getSession()) {
            transaction = session.beginTransaction();

            optionalDeliveryTypeName
                    .ifPresent(deliveryTypeDto -> {
                        DeliveryType deliveryType = (DeliveryType) resultProcessMap.get(Optional.class);
                        session.merge(deliveryType);
                    });

            Address address = null;
            if (optionalAddressDto.isPresent()) {
                address = Address.builder()
                        .country(requestAddressDto.getCountry())
                        .flor(requestAddressDto.getFlor())
                        .apartmentNumber(requestAddressDto.getApartmentNumber())
                        .buildingNumber(requestAddressDto.getBuildingNumber())
                        .street(requestAddressDto.getStreet())
                        .build();
                session.merge(address);
            }

            if(optionalAddressDto.isPresent() && optionalDeliveryTypeName.isPresent()) {
                Delivery delivery = Delivery.builder()
                        .deliveryType((DeliveryType) resultProcessMap.get(DeliveryType.class))
                        .address(address)
                        .build();

                session.merge(delivery);
            }
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
    public void deleteDelivery(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        String searchField = requestOneFieldEntityDto.getRequestValue();
        deliveryDao.mutateEntityBySQLQueryWithParams(
                DELETE_DELIVERY_BY_ORDER_ID,
                searchField
        );
    }

    private Map<Class<?>, Object> executeAsyncProcess(RequestDeliveryDto requestDeliveryDto) {
        Task deliveryTypeTask = new Task(
                () -> deliveryTypeEntityMapWrapper.getEntity(requestDeliveryDto.getDeliveryType()),
                DeliveryType.class
        );

        return asyncProcessor.process(deliveryTypeTask);
    }

    private Map<Class<?>, Object> executeAsyncProcess(RequestDeliveryDtoUpdate requestDeliveryDtoUpdate) {
        String deliveryTypeStr = requestDeliveryDtoUpdate.getNewEntityDto().getDeliveryType();
        String searchField = requestDeliveryDtoUpdate.getSearchField();

        Task deliveryTypeTask = new Task(
                () -> deliveryTypeEntityMapWrapper.getEntity(deliveryTypeStr),
                DeliveryType.class
        );

        Task deliveryTask = new Task(
                () -> deliveryDao.getOptionalEntityBySQLQueryWithParams(
                        SELECT_DELIVERY_BY_ORDER_ID, searchField),
                Delivery.class
        );

        return asyncProcessor.process(deliveryTypeTask, deliveryTask);
    }
}
