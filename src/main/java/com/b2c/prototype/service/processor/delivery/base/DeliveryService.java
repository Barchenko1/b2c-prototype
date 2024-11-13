package com.b2c.prototype.service.processor.delivery.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.AddressDto;
import com.b2c.prototype.modal.dto.request.RequestDeliveryDto;
import com.b2c.prototype.modal.dto.update.DeliveryDtoUpdate;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.processor.IAsyncProcessor;
import com.b2c.prototype.processor.Task;
import com.b2c.prototype.service.processor.delivery.IDeliveryService;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class DeliveryService implements IDeliveryService {

    private ThreadLocalSessionManager sessionManager;
    private final IAsyncProcessor asyncProcessor;
    private final IDeliveryDao deliveryDao;
    private final IEntityCachedMap entityCachedMap;

    public DeliveryService(IAsyncProcessor asyncProcessor,
                           IDeliveryDao deliveryDao,
                           IEntityCachedMap entityCachedMap) {
        this.asyncProcessor = asyncProcessor;
        this.deliveryDao = deliveryDao;
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void saveDelivery(RequestDeliveryDto requestDeliveryDto) {
        AddressDto requestAddressDto = requestDeliveryDto.getDeliveryAddressDto();
        Map<Class<?>, Object> resultProcessMap = executeAsyncProcess(requestDeliveryDto);

        Address address = Address.builder()
//                .category(requestAddressDto.getCountry())
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
    public void updateDelivery(DeliveryDtoUpdate requestDeliveryDtoUpdate) {
        RequestDeliveryDto requestDeliveryDto = requestDeliveryDtoUpdate.getNewEntityDto();
        String requestDeliveryTypeDto = requestDeliveryDto.getDeliveryType();
        AddressDto addressDto = requestDeliveryDto.getDeliveryAddressDto();

        Map<Class<?>, Object> resultProcessMap = executeAsyncProcess(requestDeliveryDtoUpdate);

        Optional<String> optionalDeliveryTypeName = Optional.ofNullable(requestDeliveryTypeDto);
        Optional<AddressDto> optionalAddressDto = Optional.ofNullable(addressDto);
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
//                        .category(requestAddressDto.getCountry())
                        .flor(addressDto.getFlor())
                        .apartmentNumber(addressDto.getApartmentNumber())
                        .buildingNumber(addressDto.getBuildingNumber())
                        .street(addressDto.getStreet())
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
    public void deleteDelivery(OneFieldEntityDto oneFieldEntityDto) {
        String searchField = oneFieldEntityDto.getValue();
//        Parameter parameter = parameterFactory.createStringParameter("searchField", searchField);
//        super.deleteEntity(parameter);
    }

    private Map<Class<?>, Object> executeAsyncProcess(RequestDeliveryDto requestDeliveryDto) {
        Task deliveryTypeTask = new Task(
                () -> entityCachedMap.getEntity(DeliveryType.class, "value", requestDeliveryDto.getDeliveryType()),
                DeliveryType.class
        );

        return asyncProcessor.process(deliveryTypeTask);
    }

    private Map<Class<?>, Object> executeAsyncProcess(DeliveryDtoUpdate requestDeliveryDtoUpdate) {
        String deliveryTypeStr = requestDeliveryDtoUpdate.getNewEntityDto().getDeliveryType();
        String searchField = requestDeliveryDtoUpdate.getSearchField();

        Task deliveryTypeTask = new Task(
                () -> entityCachedMap.getEntity(DeliveryType.class, "value", deliveryTypeStr),
                DeliveryType.class
        );

        Task deliveryTask = new Task(
                () -> {
                    Parameter parameter = new Parameter("order_id", searchField);
                    return deliveryDao.getOptionalGeneralEntity(parameter);
                },
                Delivery.class
        );

        return asyncProcessor.process(deliveryTypeTask, deliveryTask);
    }
}
