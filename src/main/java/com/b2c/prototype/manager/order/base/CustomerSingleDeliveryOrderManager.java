package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.ISessionEntityFetcher;
import com.b2c.prototype.dao.order.ICustomerOrderDao;
import com.b2c.prototype.modal.constant.CommissionType;
import com.b2c.prototype.modal.constant.OrderStatusEnum;
import com.b2c.prototype.modal.dto.payload.order.PaymentDto;
import com.b2c.prototype.modal.dto.payload.order.PaymentPriceDto;
import com.b2c.prototype.modal.dto.payload.order.single.CustomerSingleDeliveryOrderDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantityPrice;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.order.CustomerSingleDeliveryOrder;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.order.ICustomerSingleDeliveryOrderManager;
import com.b2c.prototype.transform.help.calculate.IPriceCalculationService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.dao.query.IFetchHandler;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Util.getCurrentTimeMillis;
import static com.b2c.prototype.util.Util.getUUID;

public class CustomerSingleDeliveryOrderManager implements ICustomerSingleDeliveryOrderManager {

    private final IEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final IFetchHandler fetchHandler;
    private final ISessionEntityFetcher sessionEntityFetcher;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;
    private final IPriceCalculationService priceCalculationService;

    public CustomerSingleDeliveryOrderManager(ICustomerOrderDao orderItemDao,
                                              IQueryService queryService,
                                              IFetchHandler fetchHandler,
                                              ISessionEntityFetcher sessionEntityFetcher,
                                              ITransformationFunctionService transformationFunctionService,
                                              IParameterFactory parameterFactory, IPriceCalculationService priceCalculationService) {
        this.entityOperationManager = new EntityOperationManager(orderItemDao);
        this.queryService = queryService;
        this.fetchHandler = fetchHandler;
        this.sessionEntityFetcher = sessionEntityFetcher;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
        this.priceCalculationService = priceCalculationService;
    }

    @Override
    public void saveCustomerOrder(CustomerSingleDeliveryOrderDto customerSingleDeliveryOrderDto) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails = getUserDetails(session, customerSingleDeliveryOrderDto.getUser().getUserId());
            OrderStatus orderStatus = sessionEntityFetcher.fetchOrderStatus(session, OrderStatusEnum.CREATED.name());
            List<ArticularItemQuantityPrice> articularItemQuantityPriceList = customerSingleDeliveryOrderDto.getArticularItemQuantityList().stream()
                            .map(articularItemQuantityDto -> transformationFunctionService.getEntity(session, ArticularItemQuantityPrice.class, articularItemQuantityDto))
                            .toList();
            Delivery delivery = transformationFunctionService
                    .getEntity(session, Delivery.class, customerSingleDeliveryOrderDto.getDelivery());
            PaymentPriceDto paymentPriceDto =
                    getPaymentPriceDto(articularItemQuantityPriceList, customerSingleDeliveryOrderDto.getPayment());
            Payment payment = mapPaymentPriceDtoToPayment().apply(session, paymentPriceDto);
            ContactInfo contactInfo = transformationFunctionService.getEntity(session, ContactInfo.class, customerSingleDeliveryOrderDto.getContactInfo());
            ContactInfo beneficiary = transformationFunctionService.getEntity(session, ContactInfo.class, customerSingleDeliveryOrderDto.getBeneficiary());

            CustomerSingleDeliveryOrder customerSingleDeliveryOrder = CustomerSingleDeliveryOrder.builder()
                    .orderId(getUUID())
                    .dateOfCreate(getCurrentTimeMillis())
                    .userDetails(userDetails)
                    .status(orderStatus)
                    .contactInfo(contactInfo)
                    .beneficiary(beneficiary)
                    .delivery(delivery)
                    .articularItemQuantityPrices(articularItemQuantityPriceList)
                    .payment(payment)
                    .note(customerSingleDeliveryOrderDto.getNote())
                    .build();

            session.merge(customerSingleDeliveryOrder);
        });
    }

    @Override
    public void updateCustomerOrder(String orderId, CustomerSingleDeliveryOrderDto customerSingleDeliveryOrderDto) {
        entityOperationManager.executeConsumer(session -> {
            CustomerSingleDeliveryOrder existingCustomerSingleDeliveryOrder = entityOperationManager.getNamedQueryEntity(
                    "CustomerSingleDeliveryOrder.findByOrderIdWithPayment",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            CustomerSingleDeliveryOrder newCustomerSingleDeliveryOrder =
                    transformationFunctionService.getEntity(session, CustomerSingleDeliveryOrder.class, customerSingleDeliveryOrderDto);
            existingCustomerSingleDeliveryOrder.setId(newCustomerSingleDeliveryOrder.getId());
            session.merge(existingCustomerSingleDeliveryOrder);
        });
    }

    @Override
    public void deleteCustomerOrder(String orderId) {
        entityOperationManager.deleteEntityByParameter(
                parameterFactory.createStringParameter(ORDER_ID, orderId));
    }

    @Override
    public void updateCustomerOrderStatus(String orderId, String statusValue) {
        entityOperationManager.executeConsumer(session -> {
            CustomerSingleDeliveryOrder existingCustomerSingleDeliveryOrder = entityOperationManager.getNamedQueryEntity(
                    "CustomerSingleDeliveryOrder.findByOrderIdWithPayment",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            OrderStatus orderStatus = transformationFunctionService.getEntity(session, OrderStatus.class, orderId);
            existingCustomerSingleDeliveryOrder.setStatus(orderStatus);
            session.merge(existingCustomerSingleDeliveryOrder);
        });
    }

    @Override
    public ResponseCustomerOrderDetails getResponseCustomerOrderDetails(String orderId) {
        return entityOperationManager.getNamedQueryOptionalEntity(
                        "CustomerSingleDeliveryOrder.findByOrderIdWithPayment",
                        parameterFactory.createStringParameter(ORDER_ID, orderId))
                .map(o -> transformationFunctionService.getTransformationFunction(CustomerSingleDeliveryOrder.class, ResponseCustomerOrderDetails.class)
                        .apply((CustomerSingleDeliveryOrder) o))
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<ResponseCustomerOrderDetails> getResponseCustomerOrderDetailsList() {
        List<CustomerSingleDeliveryOrder> customerSingleDeliveryOrders = entityOperationManager.getNamedQueryEntityList("CustomerSingleDeliveryOrder.all");

        return customerSingleDeliveryOrders.stream()
                .map(transformationFunctionService.getTransformationFunction(
                        CustomerSingleDeliveryOrder.class, ResponseCustomerOrderDetails.class))
                .toList();
    }

    private UserDetails getUserDetails(Session session, String userId) {
        Optional<UserDetails> userDetailsOptional = Optional.empty();
        if (userId != null) {
            userDetailsOptional = sessionEntityFetcher.fetchUserDetails(session, userId);
        }

        return userDetailsOptional.orElse(null);
    }

    private PaymentPriceDto getPaymentPriceDto(List<ArticularItemQuantityPrice> articularItemQuantityPriceList, PaymentDto paymentDto) {
        Currency currency = articularItemQuantityPriceList.get(0).getTotalPriceSum().getCurrency();
        Price fullPaymentPrice = getFullPriceSum(articularItemQuantityPriceList, currency);
        Price totalPaymentPrice = getTotalPriceSum(articularItemQuantityPriceList, currency);
        Price discountPriceSum = getDiscountPriceSum(articularItemQuantityPriceList, currency);

        return PaymentPriceDto.builder()
                .paymentMethod(paymentDto.getPaymentMethod())
                .creditCard(paymentDto.getCreditCard())
                .discountCharSequenceCode(paymentDto.getDiscountCharSequenceCode())
                .fullPaymentPrice(fullPaymentPrice)
                .totalPaymentPrice(totalPaymentPrice)
                .discountPrice(discountPriceSum)
                .build();
    }

    private Price getFullPriceSum(List<ArticularItemQuantityPrice> articularItemQuantityPriceList, Currency currency) {
        double fullSum = articularItemQuantityPriceList.stream()
                .mapToDouble(articularItemQuantityPrice -> articularItemQuantityPrice.getFullPriceSum().getAmount())
                .sum();

        return mapToPrice(fullSum, currency);
    }

    private Price getTotalPriceSum(List<ArticularItemQuantityPrice> articularItemQuantityPriceList, Currency currency) {
        double totalSum = articularItemQuantityPriceList.stream()
                .mapToDouble(articularItemQuantityPrice -> articularItemQuantityPrice.getTotalPriceSum().getAmount())
                .sum();

        return mapToPrice(totalSum, currency);
    }

    private Price getDiscountPriceSum(List<ArticularItemQuantityPrice> articularItemQuantityPriceList, Currency currency) {
        double discountPriceSum = articularItemQuantityPriceList.stream()
                .filter(articularItemQuantityPrice -> articularItemQuantityPrice.getDiscountPriceSum() != null)
                .mapToDouble(articularItemQuantityPrice -> articularItemQuantityPrice.getDiscountPriceSum().getAmount())
                .sum();

        return mapToPrice(discountPriceSum, currency);
    }

    private Price mapToPrice(Double amount, Currency currency) {
        return Price.builder()
                .currency(currency)
                .amount(amount)
                .build();
    }

    private BiFunction<Session, PaymentPriceDto, Payment> mapPaymentPriceDtoToPayment() {
        return (session, paymentPriceDto) -> {
            Optional<Discount> orderDiscountOptional = paymentPriceDto.getDiscountCharSequenceCode() != null
                    ? sessionEntityFetcher.fetchDiscountOptional(session, paymentPriceDto.getDiscountCharSequenceCode())
                    : Optional.empty();
            Discount orderDiscount = orderDiscountOptional.orElse(null);
            Optional<MinMaxCommission> optionalMinMaxCommission = sessionEntityFetcher.fetchMinMaxCommission(session, CommissionType.SELLER);
            Price commissionPrice = null;
            if (optionalMinMaxCommission.isPresent()) {
                MinMaxCommission minMaxCommission = optionalMinMaxCommission.get();
                commissionPrice = priceCalculationService
                        .calculateCommissionPrice(minMaxCommission, paymentPriceDto.getTotalPaymentPrice());
            }

            Price paymentTotalPrice = priceCalculationService
                    .calculateCurrentPrice(paymentPriceDto.getTotalPaymentPrice(), orderDiscount, commissionPrice);
            return Payment.builder()
                    .paymentId(getUUID())
                    .paymentMethod(sessionEntityFetcher.fetchPaymentMethod(session, paymentPriceDto.getPaymentMethod()))
                    .paymentStatus(sessionEntityFetcher.fetchPaymentStatus(session, "Done"))
                    .creditCard(paymentPriceDto.getCreditCard() != null
                            ? transformationFunctionService.getEntity(CreditCard.class, paymentPriceDto.getCreditCard())
                            : null)
                    .discount(orderDiscount)
                    .commissionPrice(commissionPrice)
//                    .fullPrice(paymentPriceDto.getFullPaymentPrice())
//                    .totalPrice(paymentTotalPrice)
                    .discountPrice(paymentPriceDto.getDiscountPrice())
                    .build();
        };
    }

}
