package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.payment.ICommissionManager;
import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.entity.payment.CommissionValue;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import com.b2c.prototype.transform.help.calculate.IPriceCalculationService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.CODE;
import static com.b2c.prototype.util.Constant.KEY;

@Service
public class CommissionManager implements ICommissionManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IGeneralEntityTransformService generalEntityTransformService;
    private final IPriceCalculationService priceCalculationService;

    public CommissionManager(IGeneralEntityDao generalEntityDao,
                             IGeneralEntityTransformService generalEntityTransformService,
                             IPriceCalculationService priceCalculationService) {
        this.generalEntityDao = generalEntityDao;
        this.generalEntityTransformService = generalEntityTransformService;
        this.priceCalculationService = priceCalculationService;
    }

    @Override
    @Transactional
    public void saveCommission(MinMaxCommissionDto minMaxCommissionDto) {
        MinMaxCommission minMaxCommission = generalEntityTransformService.mapMinMaxCommissionDtoToMinMaxCommission(minMaxCommissionDto);
        generalEntityDao.mergeEntity(minMaxCommission);
    }

    @Override
    @Transactional
    public void updateCommission(String region, String key, MinMaxCommissionDto minMaxCommissionDto) {
        MinMaxCommission newMinMaxCommission = generalEntityTransformService.mapMinMaxCommissionDtoToMinMaxCommission(minMaxCommissionDto);
        MinMaxCommission fetchedMinMaxCommission = (MinMaxCommission) generalEntityDao.findOptionEntity(
                "MinMaxCommission.findByRegionAndKey",
                        List.of(Pair.of(KEY, key), Pair.of(CODE, region)))
                .orElseThrow(() -> new RuntimeException("MinMaxCommission not found"));

        updateCommissionValue(fetchedMinMaxCommission.getMinCommission(), newMinMaxCommission.getMinCommission());
        updateCommissionValue(fetchedMinMaxCommission.getMaxCommission(), newMinMaxCommission.getMaxCommission());

        Price changeCommissionPrice = fetchedMinMaxCommission.getChangeCommissionPrice();
        changeCommissionPrice.setAmount(newMinMaxCommission.getChangeCommissionPrice().getAmount());
        changeCommissionPrice.setCurrency(newMinMaxCommission.getChangeCommissionPrice().getCurrency());

        fetchedMinMaxCommission.setLastUpdateTimestamp(newMinMaxCommission.getLastUpdateTimestamp());
        fetchedMinMaxCommission.setRegion(newMinMaxCommission.getRegion());

        generalEntityDao.mergeEntity(fetchedMinMaxCommission);
    }

    @Override
    @Transactional
    public void deleteCommission(String region, String key) {
        generalEntityDao.findAndRemoveEntity("MinMaxCommission.findByRegionAndKey",
                List.of(
                        Pair.of(CODE, region),
                        Pair.of(KEY, key)
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MinMaxCommissionDto> getCommissionList(String regionCode) {
        List<MinMaxCommission> minMaxCommissionList = generalEntityDao.findEntityList(
                "MinMaxCommission.findAllByRegion", Pair.of(CODE, regionCode));

        return minMaxCommissionList.stream()
                .map(generalEntityTransformService::mapMinMaxCommissionToResponseMinMaxCommissionDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MinMaxCommissionDto getCommission(String region, String key) {
        Optional<MinMaxCommission> optionalMinMaxCommission = generalEntityDao.findOptionEntity(
                "MinMaxCommission.findByRegionAndKey",
                List.of(
                        Pair.of(CODE, region),
                        Pair.of(KEY, key))
        );

        return optionalMinMaxCommission
                .map(generalEntityTransformService::mapMinMaxCommissionToResponseMinMaxCommissionDto)
                .orElseThrow(() -> new RuntimeException("MinMaxCommission not found"));
    }

    private void updateCommissionValue(CommissionValue existingCommissionValue, CommissionValue newCommissionValue) {
        existingCommissionValue.setAmount(newCommissionValue.getAmount());
        existingCommissionValue.setCurrency(newCommissionValue.getCurrency());
        existingCommissionValue.setFeeType(newCommissionValue.getFeeType());
    }
}
