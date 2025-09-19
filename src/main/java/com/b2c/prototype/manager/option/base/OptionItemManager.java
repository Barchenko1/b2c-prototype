package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.option.OptionGroupOptionItemSetDto;
import com.b2c.prototype.modal.dto.payload.option.SingleOptionItemDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.manager.option.IOptionItemManager;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class OptionItemManager implements IOptionItemManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public OptionItemManager(IGeneralEntityDao generalEntityDao,
                             IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    @Override
    public void saveUpdateOptionItemByArticularId(String articularId, String optionItemValue, SingleOptionItemDto singleOptionItemDto) {
//        OptionGroup newOptionGroup = itemTransformService.getEntity(
//                OptionGroup.class,
//                singleOptionItemDto);
        OptionGroup newOptionGroup = new OptionGroup();
        OptionGroup existingOptionGroup = (OptionGroup) generalEntityDao.findOptionEntity(
                        "OptionGroup.findByValueWithOptionItems",
                        Pair.of(VALUE, singleOptionItemDto.getOptionGroup().getValue()))
                .orElseGet(() -> {
                    generalEntityDao.persistEntity(newOptionGroup);
                    return newOptionGroup;
                });

        ArticularItem articularItem = generalEntityDao.findEntity(
                "ArticularItem.optionItems",
                Pair.of(ARTICULAR_ID, articularId));

//            OptionItem optionItem = newOptionGroup.getOptionItems().get(0);
//            existingOptionGroup.addOptionItem(optionItem);
//            articularItem.addOptionItem(optionItem);

        generalEntityDao.mergeEntity(articularItem);
    }


    @Override
    public void saveUpdateOptionItemByOptionGroup(String optionGroupValue, String optionItemValue, SingleOptionItemDto singleOptionItemDto) {
        OptionGroup existingOptionGroup = generalEntityDao.findEntity(
                "OptionGroup.findByValueWithOptionItems",
                Pair.of(VALUE, optionGroupValue));

        OptionItem existingOptionItem = existingOptionGroup.getOptionItems().stream()
                .filter(eOI -> optionItemValue.equals(eOI.getValue()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Option item with value " + optionItemValue + " not found"));

        OptionGroup newOptionGroup = new OptionGroup();
//        OptionGroup newOptionGroup = transformationFunctionService.getEntity(OptionGroup.class, singleOptionItemDto);
//            OptionItem newOptionItem = newOptionGroup.getOptionItems().get(0);
//            newOptionItem.setId(existingOptionItem.getId());

        if (existingOptionGroup.getValue().equals(newOptionGroup.getValue())) {
            existingOptionGroup.getOptionItems().remove(existingOptionItem);
//                existingOptionGroup.addOptionItem(newOptionItem);
            generalEntityDao.mergeEntity(existingOptionGroup);
        } else {
            generalEntityDao.mergeEntity(newOptionGroup);
        }
    }


    @Override
    public void saveOptionItemSet(Set<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDtoList) {
//        entityOperationManager.executeConsumer(session -> {
//            Set<OptionGroup> optionGroups =
//                    (Set<OptionGroup>) transformationFunctionService.getCollectionTransformationCollectionBiFunction(OptionGroupOptionItemSetDto.class, OptionGroup.class, "set")
//                            .apply((Session) session, optionGroupOptionItemSetDtoList);
//            optionGroups.forEach(session::merge);
//        });
    }

    @Override
    public void deleteOptionItemByArticularId(String articularId, String optionValue) {
        ArticularItem articularItem = generalEntityDao.findEntity(
                "ArticularItem.findByOptionItemValueAndGroup",
                Pair.of(ARTICULAR_ID, articularId));
        if (articularItem != null) {
            articularItem.getOptionItems().stream()
                    .filter(oi -> oi.getValue().equals(optionValue))
                    .findFirst()
                    .ifPresent(optionItem -> {
                        articularItem.removeOptionItem(optionItem);
                        generalEntityDao.mergeEntity(articularItem);
                        OptionGroup optionGroup = optionItem.getOptionGroup();
                        optionGroup.removeOptionItem(optionItem);
                        generalEntityDao.mergeEntity(optionGroup);
                    });
        }
    }

    @Override
    public void deleteOptionItemByOptionGroup(String optionGroupValue, String optionValue) {
        OptionGroup existingOptionGroup = generalEntityDao.findEntity(
                "OptionGroup.withOptionItemsAndArticularItems",
                Pair.of(VALUE, optionValue));
        if (existingOptionGroup != null) {
            existingOptionGroup.getOptionItems().stream()
                    .filter(optionItem -> optionItem.getValue().equals(optionValue))
                    .findFirst()
                    .ifPresent(optionItem -> {
                        List<ArticularItem> articularItemList = generalEntityDao.findEntityList(
                                "ArticularItem.findByOptionItemValue",
                                Pair.of(VALUE, optionValue));                            articularItemList.forEach(articularItem -> {
                            articularItem.removeOptionItem(optionItem);
                            generalEntityDao.mergeEntity(articularItem);
                        });
                        existingOptionGroup.removeOptionItem(optionItem);
                        generalEntityDao.mergeEntity(existingOptionGroup);
                    });
        }
    }

    @Override
    public OptionGroupOptionItemSetDto getOptionItemListByOptionGroup(String optionGroupValue) {
//        OptionGroup optionGroup = generalEntityDao.findEntity(
//                "OptionGroup.findByValueWithOptionItems",
//                Pair.of(VALUE, optionGroupValue));
//        return Optional.of(optionGroup)
//                .map(transformationFunctionService.getTransformationFunction(OptionGroup.class, OptionGroupOptionItemSetDto.class))
//                .orElseThrow(() -> new RuntimeException(""));
        return null;
    }

    @Override
    public List<OptionGroupOptionItemSetDto> getOptionItemByItemArticularId(String articularId) {
//        ArticularItem articularItem = generalEntityDao.findEntity(
//                "ArticularItem.optionItems",
//                Pair.of(ARTICULAR_ID, articularId));
//
//        return (List<OptionGroupOptionItemSetDto>) transformationFunctionService
//                .getTransformationCollectionFunction(ArticularItem.class, OptionGroupOptionItemSetDto.class, "list")
//                .apply(articularItem);
        return null;
    }

    @Override
    public List<OptionGroupOptionItemSetDto> getOptionItemList() {
//        List<OptionGroup> optionGroupList = generalEntityDao.findEntityList(
//                "OptionGroup.findWithOptionItems", (Pair<String, ?>) null);
//        return optionGroupList.stream()
//                .map(itemTransformService::mapOptionGroupToConstantPayloadDto)
//                .toList();
        return null;
    }

}
