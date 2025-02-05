package com.b2c.prototype.processor.order;

import com.b2c.prototype.manager.order.IOrderItemDataOptionManager;

public class OrderProcessor implements IOrderProcessor {

    private final IOrderItemDataOptionManager orderItemDataOptionManager;

    public OrderProcessor(IOrderItemDataOptionManager orderItemDataOptionManager) {
        this.orderItemDataOptionManager = orderItemDataOptionManager;
    }
}
