/*
 * Copyright 2009 Igor Azarnyi, Denys Pavlov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.yes.cart.shoppingcart.impl;

import org.junit.Test;
import org.yes.cart.BaseCoreDBTestCase;
import org.yes.cart.shoppingcart.AmountCalculationStrategy;
import org.yes.cart.shoppingcart.ShoppingCart;
import org.yes.cart.shoppingcart.ShoppingCartCommand;
import org.yes.cart.shoppingcart.ShoppingCartCommandFactory;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 09-May-2011
 * Time: 14:12:54
 */
public class SetCarrierSlaCartCommandImplTest extends BaseCoreDBTestCase {

    @Test
    public void testExecuteNoAddressParams() {
        ShoppingCart shoppingCart = new ShoppingCartImpl();
        shoppingCart.initialise(ctx().getBean("amountCalculationStrategy", AmountCalculationStrategy.class));
        final ShoppingCartCommandFactory commands = ctx().getBean("shoppingCartCommandFactory", ShoppingCartCommandFactory.class);

        assertNull(shoppingCart.getCarrierSlaId());
        assertNull(shoppingCart.getOrderInfo().getBillingAddressId());
        assertNull(shoppingCart.getOrderInfo().getDeliveryAddressId());

        commands.execute(shoppingCart, new HashMap<String, Object>() {{
            put(ShoppingCartCommand.CMD_CHANGECURRENCY, "EUR");
            put(ShoppingCartCommand.CMD_CHANGELOCALE, "en");
            put(ShoppingCartCommand.CMD_SETSHOP, "10");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA, "123");
        }});

        assertEquals(123, shoppingCart.getCarrierSlaId().intValue());
        assertNull(shoppingCart.getOrderInfo().getBillingAddressId());
        assertNull(shoppingCart.getOrderInfo().getDeliveryAddressId());
        assertFalse(shoppingCart.getOrderInfo().isBillingAddressNotRequired());
        assertFalse(shoppingCart.getOrderInfo().isBillingAddressNotRequired());
    }

    @Test
    public void testExecuteWithAddressParams() {
        ShoppingCart shoppingCart = new ShoppingCartImpl();
        shoppingCart.initialise(ctx().getBean("amountCalculationStrategy", AmountCalculationStrategy.class));
        final ShoppingCartCommandFactory commands = ctx().getBean("shoppingCartCommandFactory", ShoppingCartCommandFactory.class);

        assertNull(shoppingCart.getCarrierSlaId());
        assertNull(shoppingCart.getOrderInfo().getBillingAddressId());
        assertNull(shoppingCart.getOrderInfo().getDeliveryAddressId());

        commands.execute(shoppingCart, new HashMap<String, Object>() {{
            put(ShoppingCartCommand.CMD_CHANGECURRENCY, "EUR");
            put(ShoppingCartCommand.CMD_CHANGELOCALE, "en");
            put(ShoppingCartCommand.CMD_SETSHOP, "10");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA, "123");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_BILLING_ADDRESS, "234");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_DELIVERY_ADDRESS, "345");
        }});

        assertEquals(123, shoppingCart.getCarrierSlaId().intValue());
        assertEquals(234, shoppingCart.getOrderInfo().getBillingAddressId().intValue());
        assertEquals(345, shoppingCart.getOrderInfo().getDeliveryAddressId().intValue());
        assertFalse(shoppingCart.getOrderInfo().isBillingAddressNotRequired());
        assertFalse(shoppingCart.getOrderInfo().isDeliveryAddressNotRequired());
    }

    @Test
    public void testExecuteWithAddressParamsBillingNotRequired() {
        ShoppingCart shoppingCart = new ShoppingCartImpl();
        shoppingCart.initialise(ctx().getBean("amountCalculationStrategy", AmountCalculationStrategy.class));
        final ShoppingCartCommandFactory commands = ctx().getBean("shoppingCartCommandFactory", ShoppingCartCommandFactory.class);

        assertNull(shoppingCart.getCarrierSlaId());
        assertNull(shoppingCart.getOrderInfo().getBillingAddressId());
        assertNull(shoppingCart.getOrderInfo().getDeliveryAddressId());

        commands.execute(shoppingCart, new HashMap<String, Object>() {{
            put(ShoppingCartCommand.CMD_CHANGECURRENCY, "EUR");
            put(ShoppingCartCommand.CMD_CHANGELOCALE, "en");
            put(ShoppingCartCommand.CMD_SETSHOP, "10");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA, "123");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_BILLING_NOT_REQUIRED, Boolean.TRUE);
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_DELIVERY_NOT_REQUIRED, Boolean.FALSE);
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_BILLING_ADDRESS, "234");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_DELIVERY_ADDRESS, "345");
        }});

        assertEquals(123, shoppingCart.getCarrierSlaId().intValue());
        assertNull(shoppingCart.getOrderInfo().getBillingAddressId());
        assertEquals(345, shoppingCart.getOrderInfo().getDeliveryAddressId().intValue());
        assertTrue(shoppingCart.getOrderInfo().isBillingAddressNotRequired());
        assertFalse(shoppingCart.getOrderInfo().isDeliveryAddressNotRequired());
    }

    @Test
    public void testExecuteWithAddressParamsShippingNotRequired() {
        ShoppingCart shoppingCart = new ShoppingCartImpl();
        shoppingCart.initialise(ctx().getBean("amountCalculationStrategy", AmountCalculationStrategy.class));
        final ShoppingCartCommandFactory commands = ctx().getBean("shoppingCartCommandFactory", ShoppingCartCommandFactory.class);

        assertNull(shoppingCart.getCarrierSlaId());
        assertNull(shoppingCart.getOrderInfo().getBillingAddressId());
        assertNull(shoppingCart.getOrderInfo().getDeliveryAddressId());

        commands.execute(shoppingCart, new HashMap<String, Object>() {{
            put(ShoppingCartCommand.CMD_CHANGECURRENCY, "EUR");
            put(ShoppingCartCommand.CMD_CHANGELOCALE, "en");
            put(ShoppingCartCommand.CMD_SETSHOP, "10");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA, "123");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_BILLING_NOT_REQUIRED, Boolean.FALSE);
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_DELIVERY_NOT_REQUIRED, Boolean.TRUE);
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_BILLING_ADDRESS, "234");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_DELIVERY_ADDRESS, "345");
        }});

        assertEquals(123, shoppingCart.getCarrierSlaId().intValue());
        assertEquals(234, shoppingCart.getOrderInfo().getBillingAddressId().intValue());
        assertNull(shoppingCart.getOrderInfo().getDeliveryAddressId());
        assertFalse(shoppingCart.getOrderInfo().isBillingAddressNotRequired());
        assertTrue(shoppingCart.getOrderInfo().isDeliveryAddressNotRequired());
    }

    @Test
    public void testExecuteWithAddressParamsBillingNotRequiredOverwrite() {
        ShoppingCart shoppingCart = new ShoppingCartImpl();
        shoppingCart.initialise(ctx().getBean("amountCalculationStrategy", AmountCalculationStrategy.class));
        final ShoppingCartCommandFactory commands = ctx().getBean("shoppingCartCommandFactory", ShoppingCartCommandFactory.class);

        shoppingCart.getOrderInfo().setBillingAddressId(234L);
        shoppingCart.getOrderInfo().setDeliveryAddressId(345L);

        assertNull(shoppingCart.getCarrierSlaId());
        assertNotNull(shoppingCart.getOrderInfo().getBillingAddressId());
        assertNotNull(shoppingCart.getOrderInfo().getDeliveryAddressId());

        commands.execute(shoppingCart, new HashMap<String, Object>() {{
            put(ShoppingCartCommand.CMD_CHANGECURRENCY, "EUR");
            put(ShoppingCartCommand.CMD_CHANGELOCALE, "en");
            put(ShoppingCartCommand.CMD_SETSHOP, "10");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA, "123");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_BILLING_NOT_REQUIRED, Boolean.TRUE);
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_DELIVERY_NOT_REQUIRED, Boolean.FALSE);
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_BILLING_ADDRESS, "234");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_DELIVERY_ADDRESS, "345");
        }});

        assertEquals(123, shoppingCart.getCarrierSlaId().intValue());
        assertNull(shoppingCart.getOrderInfo().getBillingAddressId());
        assertEquals(345, shoppingCart.getOrderInfo().getDeliveryAddressId().intValue());
        assertTrue(shoppingCart.getOrderInfo().isBillingAddressNotRequired());
        assertFalse(shoppingCart.getOrderInfo().isDeliveryAddressNotRequired());
    }

    @Test
    public void testExecuteWithAddressParamsShippingNotRequiredOverwrite() {
        ShoppingCart shoppingCart = new ShoppingCartImpl();
        shoppingCart.initialise(ctx().getBean("amountCalculationStrategy", AmountCalculationStrategy.class));
        final ShoppingCartCommandFactory commands = ctx().getBean("shoppingCartCommandFactory", ShoppingCartCommandFactory.class);

        shoppingCart.getOrderInfo().setBillingAddressId(234L);
        shoppingCart.getOrderInfo().setDeliveryAddressId(345L);

        assertNull(shoppingCart.getCarrierSlaId());
        assertNotNull(shoppingCart.getOrderInfo().getBillingAddressId());
        assertNotNull(shoppingCart.getOrderInfo().getDeliveryAddressId());

        commands.execute(shoppingCart, new HashMap<String, Object>() {{
            put(ShoppingCartCommand.CMD_CHANGECURRENCY, "EUR");
            put(ShoppingCartCommand.CMD_CHANGELOCALE, "en");
            put(ShoppingCartCommand.CMD_SETSHOP, "10");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA, "123");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_BILLING_NOT_REQUIRED, Boolean.FALSE);
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_DELIVERY_NOT_REQUIRED, Boolean.TRUE);
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_BILLING_ADDRESS, "234");
            put(ShoppingCartCommand.CMD_SETCARRIERSLA_P_DELIVERY_ADDRESS, "345");
        }});

        assertEquals(123, shoppingCart.getCarrierSlaId().intValue());
        assertEquals(234, shoppingCart.getOrderInfo().getBillingAddressId().intValue());
        assertNull(shoppingCart.getOrderInfo().getDeliveryAddressId());
        assertFalse(shoppingCart.getOrderInfo().isBillingAddressNotRequired());
        assertTrue(shoppingCart.getOrderInfo().isDeliveryAddressNotRequired());
    }
}
