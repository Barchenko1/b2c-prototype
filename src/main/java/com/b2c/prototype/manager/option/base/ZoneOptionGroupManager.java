package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.option.IZoneOptionGroupManager;
import com.b2c.prototype.modal.dto.payload.option.group.ZoneOptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.ZoneOptionDto;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.modal.entity.option.ZoneOptionGroup;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.generator.IKeyGeneratorService;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static com.b2c.prototype.util.Constant.KEY;
import static java.util.stream.Collectors.toMap;

@Service
public class ZoneOptionGroupManager implements IZoneOptionGroupManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IOrderTransformService orderTransformService;
    private final IGeneralEntityTransformService generalEntityTransformService;
    private final IKeyGeneratorService keyGeneratorService;

    public ZoneOptionGroupManager(IGeneralEntityDao generalEntityDao,
                                  IOrderTransformService orderTransformService,
                                  IGeneralEntityTransformService generalEntityTransformService,
                                  IKeyGeneratorService keyGeneratorService) {
        this.generalEntityDao = generalEntityDao;
        this.orderTransformService = orderTransformService;
        this.generalEntityTransformService = generalEntityTransformService;
        this.keyGeneratorService = keyGeneratorService;
    }

    @Transactional
    @Override
    public void persistEntity(ZoneOptionGroupDto zoneOptionGroupDto) {
        ZoneOptionGroup zoneOptionGroup = orderTransformService.mapZoneOptionGroupDtoToZoneOptionGroup(zoneOptionGroupDto);
        zoneOptionGroup.setKey(keyGeneratorService.generateKey("zone_group"));
        generalEntityDao.persistEntity(zoneOptionGroup);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, ZoneOptionGroupDto zoneOptionGroupDto) {
        ZoneOptionGroup group = generalEntityDao.findEntity(
                "ZoneOptionGroup.findByKey",
                Pair.of(KEY, searchValue)
        );
        ZoneOptionGroup entity = syncItemsAllowingKeyChange(searchValue, group, zoneOptionGroupDto);
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    @Override
    public void removeEntity(String key) {
        ZoneOptionGroup zoneOptionGroup = generalEntityDao.findEntity("ZoneOptionGroup.findByKey",
                Pair.of(KEY, key));
        generalEntityDao.removeEntity(zoneOptionGroup);
    }

    @Override
    public ZoneOptionGroup getEntity(String value) {
        return generalEntityDao.findEntity("ZoneOptionGroup.findByKey", Pair.of(KEY, value));
    }

    @Override
    public Optional<ZoneOptionGroup> getEntityOptional(String value) {
        return generalEntityDao.findOptionEntity("ZoneOptionGroup.findByKey", Pair.of(KEY, value));
    }

    @Override
    public List<ZoneOptionGroup> getEntities() {
        return generalEntityDao.findEntityList("ZoneOptionGroup.all", (Pair<String, ?>) null);
    }

    private ZoneOptionGroup syncItemsAllowingKeyChange(String searchValue, ZoneOptionGroup group, ZoneOptionGroupDto dto) {
        if (group == null) {
            throw new IllegalArgumentException("OptionGroup not found by old value: " + searchValue);
        }

        copyUpdatableFields(group, dto);

        final Map<String, ZoneOption> currentByValue = group.getZoneOptions().stream()
                .filter(Objects::nonNull)
                .filter(t -> t.getKey() != null)
                .collect(toMap(ZoneOption::getKey, Function.identity(), (a, b) -> a, LinkedHashMap::new));

        final Set<ZoneOption> matchedExisting = Collections.newSetFromMap(new IdentityHashMap<>());
        final List<ZoneOptionDto> incoming = Optional.ofNullable(dto.getZoneOptions())
                .orElseGet(Collections::emptyList);

        incoming.forEach(itemDto -> {
            if (itemDto == null) return;
            ZoneOption existing = currentByValue.get(itemDto.getKey());
            if (existing != null) {
                if (itemDto.getKey() != null && !itemDto.getKey().equals(existing.getKey())) {
                    if (currentByValue.containsKey(itemDto.getKey()) && currentByValue.get(itemDto.getKey()) != existing) {
                        throw new IllegalStateException("TimeDurationOption value collision on rename: " + itemDto.getKey());
                    }
                    currentByValue.remove(existing.getKey());
                    existing.setValue(itemDto.getKey());
                    currentByValue.put(itemDto.getKey(), existing);
                }

                if (itemDto.getKey() != null) existing.setKey(itemDto.getKey());
                if (itemDto.getValue() != null) existing.setValue(itemDto.getValue());

                if (itemDto.getPrice() != null) {
                    Price p = existing.getPrice();
                    if (p == null) {
                        p = Price.builder().build();
                        existing.setPrice(p);
                    }
                    if (itemDto.getPrice().getAmount() != null) {
                        p.setAmount(itemDto.getPrice().getAmount());
                    }
                    if (itemDto.getPrice().getCurrency() != null && itemDto.getPrice().getCurrency().getKey() != null) {
                        p.setCurrency(generalEntityTransformService.mapCurrencyDtoToCurrency(itemDto.getPrice().getCurrency()));
                    }
                }

                matchedExisting.add(existing);
            }
        });

        incoming.stream()
                .filter(Objects::nonNull)
                .forEach(d -> {
                    String newKey = d.getKey();
                    if (newKey == null || newKey.trim().isEmpty()) {
                        newKey = keyGeneratorService.generateKey("zone_option");
                    }
                    if (!currentByValue.containsKey(newKey)) {
                        ZoneOption created = ZoneOption.builder()
                                .value(d.getValue())
                                .key(newKey)
                                .price(d.getPrice() != null
                                        ? Price.builder()
                                            .amount(d.getPrice().getAmount())
                                            .currency(generalEntityTransformService.mapCurrencyDtoToCurrency(d.getPrice().getCurrency()))
                                            .build()
                                        : null)
                                .build();
                        group.addZoneOption(created);
                        currentByValue.put(newKey, created);
                        matchedExisting.add(created);
                    }
                });

        // REMOVE ORPHANS (unchanged)
        group.getZoneOptions().stream()
                .filter(Objects::nonNull)
                .filter(t -> !matchedExisting.contains(t))
                .toList()
                .forEach(group::removeZoneOption);

        return group;
    }

    private void copyUpdatableFields(ZoneOptionGroup target, ZoneOptionGroupDto source) {
        if (source.getValue() != null) {
            target.setValue(source.getValue());
        }
        if (source.getCity() != null) {
            target.setCity(source.getCity());
        }
        if (source.getCountry() != null) {
            target.setCountry(generalEntityTransformService.mapCountryDtoToCountry(source.getCountry()));
        }
    }
}
