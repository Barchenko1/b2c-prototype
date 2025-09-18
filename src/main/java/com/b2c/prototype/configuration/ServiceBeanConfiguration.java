package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.dao.ISessionEntityFetcher;
import com.b2c.prototype.dao.SessionEntityFetcher;
import com.b2c.prototype.manager.address.IUserAddressManager;
import com.b2c.prototype.manager.address.base.UserAddressManager;
import com.b2c.prototype.manager.item.IArticularItemManager;
import com.b2c.prototype.manager.item.ICategoryManager;
import com.b2c.prototype.manager.item.IDiscountManager;
import com.b2c.prototype.manager.item.IItemDataManager;
import com.b2c.prototype.manager.item.base.ArticularItemManager;
import com.b2c.prototype.manager.item.base.CategoryManager;
import com.b2c.prototype.manager.item.base.DiscountManager;
import com.b2c.prototype.manager.item.base.ItemDataManager;
import com.b2c.prototype.manager.message.IMessageManager;
import com.b2c.prototype.manager.message.base.MessageManager;
import com.b2c.prototype.manager.option.IOptionItemManager;
import com.b2c.prototype.manager.option.ITimeDurationOptionManager;
import com.b2c.prototype.manager.option.IZoneOptionManager;
import com.b2c.prototype.manager.option.base.OptionItemManager;
import com.b2c.prototype.manager.option.base.TimeDurationOptionManager;
import com.b2c.prototype.manager.option.base.ZoneOptionManager;
import com.b2c.prototype.manager.order.ICustomerSingleDeliveryOrderManager;
import com.b2c.prototype.manager.order.base.CustomerSingleDeliveryOrderManager;
import com.b2c.prototype.manager.payment.ICommissionManager;
import com.b2c.prototype.manager.payment.ICurrencyCoefficientManager;
import com.b2c.prototype.manager.payment.base.CommissionManager;
import com.b2c.prototype.manager.review.IReviewManager;
import com.b2c.prototype.manager.review.base.ReviewManager;
import com.b2c.prototype.manager.store.IStoreAddressManager;
import com.b2c.prototype.manager.store.IStoreManager;
import com.b2c.prototype.manager.store.base.StoreAddressManager;
import com.b2c.prototype.manager.store.base.StoreManager;
import com.b2c.prototype.manager.userdetails.DeviceManager;
import com.b2c.prototype.manager.userdetails.IDeviceManager;
import com.b2c.prototype.manager.post.IPostManager;
import com.b2c.prototype.manager.post.base.PostManager;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.transform.function.TransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.factory.ParameterFactory;
import com.tm.core.process.dao.query.IQueryService;
import com.tm.core.process.dao.IFetchHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfiguration {

    @Value("${thread.pool.size}")
    private int threadCount;

    // support service

    @Bean
    public IParameterFactory parameterFactory() {
        return new ParameterFactory();
    }

    @Bean
    public ITransformationFunctionService transformationFunctionService() {
        return new TransformationFunctionService();
    }

    @Bean
    public ISessionEntityFetcher sessionEntityFetcher(IQueryService queryService, IParameterFactory parameterFactory) {
        return new SessionEntityFetcher(queryService, parameterFactory);
    }

    // app service

    @Bean
    public IMessageManager messageManager(IGeneralEntityDao generalEntityDao,
                                          IFetchHandler fetchHandler,
                                          IQueryService queryService,
                                          ITransformationFunctionService transformationFunctionService,
                                          IParameterFactory parameterFactory) {
        return new MessageManager(generalEntityDao, fetchHandler, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IDeviceManager deviceManager(IGeneralEntityDao deviceDao,
                                        IFetchHandler fetchHandler,
                                        ITransformationFunctionService transformationFunctionService,
                                        IParameterFactory parameterFactory) {
        return new DeviceManager(deviceDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IDiscountManager discountManager(IGeneralEntityDao discountDao,
                                            IFetchHandler fetchHandler,
                                            ITransformationFunctionService transformationFunctionService,
                                            IParameterFactory parameterFactory) {
        return new DiscountManager(discountDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IItemDataManager itemDataManager(IGeneralEntityDao itemDataDao,
                                            ITransformationFunctionService transformationFunctionService,
                                            IParameterFactory parameterFactory) {
        return new ItemDataManager(itemDataDao, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IArticularItemManager itemDataOptionManager(IGeneralEntityDao itemDataOptionDao,
                                                       IFetchHandler fetchHandler,
                                                       ITransformationFunctionService transformationFunctionService,
                                                       IParameterFactory parameterFactory) {
        return new ArticularItemManager(itemDataOptionDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IOptionItemManager optionItemManager(IGeneralEntityDao optionItemDao,
                                                IFetchHandler fetchHandler,
                                                ITransformationFunctionService transformationFunctionService,
                                                IParameterFactory parameterFactory) {
        return new OptionItemManager(optionItemDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

//    @Bean
//    public IOptionItemCostManager optionItemCostManager(ITransactionEntityDao optionItemCostDao,
//                                                        IQueryService queryService,
//                                                        ITransformationFunctionService transformationFunctionService,
//                                                        IParameterFactory parameterFactory) {
//        return new OptionItemCostManager(optionItemCostDao, queryService, transformationFunctionService, parameterFactory);
//    }

    @Bean
    public ITimeDurationOptionManager timeDurationOptionManager(IGeneralEntityDao timeDurationOptionDao,
                                                                ITransformationFunctionService transformationFunctionService,
                                                                IParameterFactory parameterFactory) {
        return new TimeDurationOptionManager(timeDurationOptionDao, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IZoneOptionManager zoneOptionManager(IGeneralEntityDao zoneOptionDao,
                                                ITransformationFunctionService transformationFunctionService,
                                                IParameterFactory parameterFactory) {
        return new ZoneOptionManager(zoneOptionDao, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IUserAddressManager userAddressManager(IGeneralEntityDao addressDao,
                                                  IFetchHandler fetchHandler,
                                                  ITransformationFunctionService transformationFunctionService,
                                                  IParameterFactory parameterFactory) {
        return new UserAddressManager(addressDao, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public ICategoryManager categoryManager(IGeneralEntityDao categoryDao,
                                            IQueryService queryService,
                                            ITransformationFunctionService transformationFunctionService,
                                            IParameterFactory parameterFactory) {
        return new CategoryManager(categoryDao, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IPostManager postManager(IGeneralEntityDao postDao,
                                    IQueryService queryService,
                                    ITransformationFunctionService transformationFunctionService,
                                    IParameterFactory parameterFactory) {
        return new PostManager(postDao, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IReviewManager reviewManager(IGeneralEntityDao reviewDao,
                                        IQueryService queryService,
                                        IFetchHandler fetchHandler,
                                        ITransformationFunctionService transformationFunctionService,
                                        IParameterFactory parameterFactory) {
        return new ReviewManager(reviewDao, queryService, fetchHandler, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IStoreManager storeManager(IGeneralEntityDao storeDao,
                                      IQueryService queryService,
                                      ITransformationFunctionService transformationFunctionService,
                                      IParameterFactory parameterFactory) {
        return new StoreManager(storeDao, queryService, transformationFunctionService, parameterFactory);
    }

    @Bean
    public IStoreAddressManager storeAddressManager(IGeneralEntityDao addressDao,
                                                    IQueryService queryService,
                                                    ITransformationFunctionService transformationFunctionService,
                                                    IParameterFactory parameterFactory) {
        return new StoreAddressManager(addressDao, queryService, transformationFunctionService, parameterFactory);
    }

    //process
}
