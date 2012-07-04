package org.yes.cart.utils.impl;


import org.yes.cart.payment.persistence.entity.CustomerOrderPayment;

import java.util.Comparator;

/**
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 09-May-2011
 * Time: 14:12:54
 */
public class CustomerOrderPaymentComparator implements Comparator<CustomerOrderPayment> {

    /**
     * {@inheritDoc}
     */
    public int compare(final CustomerOrderPayment orderPayment1, final CustomerOrderPayment orderPayment2) {
        return (orderPayment1.getCustomerOrderPaymentId() < orderPayment2.getCustomerOrderPaymentId() ?
                -1 : (orderPayment1.getCustomerOrderPaymentId() == orderPayment2.getCustomerOrderPaymentId() ? 0 : 1));
    }
}