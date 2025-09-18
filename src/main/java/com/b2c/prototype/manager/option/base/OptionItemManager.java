package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.option.OptionGroupOptionItemSetDto;
import com.b2c.prototype.modal.dto.payload.option.SingleOptionItemDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.option.IOptionItemManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.IFetchHandler;
import com.tm.core.process.manager.common.ITransactionEntityOperationManager;
import com.tm.core.process.manager.common.operator.TransactionEntityOperationManager;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class OptionItemManager implements IOptionItemManager {

    private final ITransactionEntityOperationManager entityOperationManager;
    private final IFetchHandler fetchHandler;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public OptionItemManager(IGeneralEntityDao optionItemDao,
                             IFetchHandler fetchHandler,
                             ITransformationFunctionService transformationFunctionService,
                             IParameterFactory parameterFactory) {
        this.entityOperationManager = new TransactionEntityOperationManager(null);
        this.fetchHandler = fetchHandler;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateOptionItemByArticularId(String articularId, String optionItemValue, SingleOptionItemDto singleOptionItemDto) {
        entityOperationManager.executeConsumer(session -> {
            OptionGroup newOptionGroup = transformationFunctionService.getEntity(
                    OptionGroup.class,
                    singleOptionItemDto);
            OptionGroup existingOptionGroup = fetchHandler.getNamedQueryOptionalEntityClose(
                            OptionGroup.class,
                            "OptionGroup.findByValueWithOptionItems",
                            parameterFactory.createStringParameter(VALUE, singleOptionItemDto.getOptionGroup().getValue()))
                    .orElseGet(() -> {
                        session.persist(newOptionGroup);
                        return newOptionGroup;
                    });

            ArticularItem articularItem = fetchHandler.getNamedQueryEntityClose(
                    ArticularItem.class,
                    "ArticularItem.optionItems",
                    parameterFactory.createStringParameter(ARTICULAR_ID, articularId));

//            OptionItem optionItem = newOptionGroup.getOptionItems().get(0);
//            existingOptionGroup.addOptionItem(optionItem);
//            articularItem.addOptionItem(optionItem);

            session.merge(articularItem);
        });
    }


    @Override
    public void saveUpdateOptionItemByOptionGroup(String optionGroupValue, String optionItemValue, SingleOptionItemDto singleOptionItemDto) {
        entityOperationManager.executeConsumer(session -> {
            OptionGroup existingOptionGroup = fetchHandler.getNamedQueryEntityClose(
                    OptionGroup.class,
                    "OptionGroup.findByValueWithOptionItems",
                    parameterFactory.createStringParameter(VALUE, optionGroupValue));

            OptionItem existingOptionItem = existingOptionGroup.getOptionItems().stream()
                    .filter(eOI -> optionItemValue.equals(eOI.getValue()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Option item with value " + optionItemValue + " not found"));

            OptionGroup newOptionGroup = transformationFunctionService.getEntity(OptionGroup.class, singleOptionItemDto);
//            OptionItem newOptionItem = newOptionGroup.getOptionItems().get(0);
//            newOptionItem.setId(existingOptionItem.getId());

            if (existingOptionGroup.getValue().equals(newOptionGroup.getValue())) {
                existingOptionGroup.getOptionItems().remove(existingOptionItem);
//                existingOptionGroup.addOptionItem(newOptionItem);
                session.merge(existingOptionGroup);
            } else {
                session.merge(newOptionGroup);
            }
        });
    }


    @Override
    public void saveOptionItemSet(Set<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDtoList) {
        entityOperationManager.executeConsumer(session -> {
            Set<OptionGroup> optionGroups =
                    (Set<OptionGroup>) transformationFunctionService.getCollectionTransformationCollectionBiFunction(OptionGroupOptionItemSetDto.class, OptionGroup.class, "set")
                            .apply((Session) session, optionGroupOptionItemSetDtoList);
            optionGroups.forEach(session::merge);
        });
    }

    @Override
    public void deleteOptionItemByArticularId(String articularId, String optionValue) {
        entityOperationManager.executeConsumer(session -> {
            ArticularItem articularItem = fetchHandler.getNamedQueryEntityClose(
                    ArticularItem.class,
                    "ArticularItem.findByOptionItemValueAndGroup",
                    parameterFactory.createStringParameter(ARTICULAR_ID, articularId));
            if (articularItem != null) {
                articularItem.getOptionItems().stream()
                        .filter(oi -> oi.getValue().equals(optionValue))
                        .findFirst()
                        .ifPresent(optionItem -> {
                            articularItem.removeOptionItem(optionItem);
                            session.merge(articularItem);
                            OptionGroup optionGroup = optionItem.getOptionGroup();
                            optionGroup.removeOptionItem(optionItem);
                            session.merge(optionGroup);
                        });
            }
        });
    }

    @Override
    public void deleteOptionItemByOptionGroup(String optionGroupValue, String optionValue) {
        entityOperationManager.executeConsumer(session -> {
            OptionGroup existingOptionGroup = fetchHandler.getNamedQueryEntityClose(
                    OptionGroup.class,
                    "OptionGroup.withOptionItemsAndArticularItems",
                    parameterFactory.createStringParameter(VALUE, optionGroupValue));
            if (existingOptionGroup != null) {
                existingOptionGroup.getOptionItems().stream()
                        .filter(optionItem -> optionItem.getValue().equals(optionValue))
                        .findFirst()
                        .ifPresent(optionItem -> {
                            List<ArticularItem> articularItemList = fetchHandler.getNamedQueryEntityListClose(
                                    ArticularItem.class,
                                    "ArticularItem.findByOptionItemValue",
                                    parameterFactory.createStringParameter(VALUE, optionValue));
                            articularItemList.forEach(articularItem -> {
                                articularItem.removeOptionItem(optionItem);
                                session.merge(articularItem);
                            });
                            existingOptionGroup.removeOptionItem(optionItem);
                            session.merge(existingOptionGroup);
                        });
            }
        });
    }

    @Override
    public OptionGroupOptionItemSetDto getOptionItemListByOptionGroup(String optionGroupValue) {
        OptionGroup optionGroup = fetchHandler.getNamedQueryEntityClose(
                OptionGroup.class,
                "OptionGroup.findByValueWithOptionItems",
                parameterFactory.createStringParameter(VALUE, optionGroupValue));
        return Optional.of(optionGroup)
                .map(transformationFunctionService.getTransformationFunction(OptionGroup.class, OptionGroupOptionItemSetDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public List<OptionGroupOptionItemSetDto> getOptionItemByItemArticularId(String articularId) {
        ArticularItem articularItem = fetchHandler.getNamedQueryEntityClose(
                ArticularItem.class,
                "ArticularItem.optionItems",
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId));

        return (List<OptionGroupOptionItemSetDto>) transformationFunctionService
                .getTransformationCollectionFunction(ArticularItem.class, OptionGroupOptionItemSetDto.class, "list")
                .apply(articularItem);
    }

    @Override
    public List<OptionGroupOptionItemSetDto> getOptionItemList() {
        List<OptionGroup> optionGroupList = fetchHandler.getNamedQueryEntityListClose(
                OptionGroup.class,
                "OptionGroup.findWithOptionItems");
        return optionGroupList.stream()
                .map(transformationFunctionService.getTransformationFunction(OptionGroup.class, OptionGroupOptionItemSetDto.class))
                .toList();
    }

}
