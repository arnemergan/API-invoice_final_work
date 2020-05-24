package com.api.invoice.services.implementation;

import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeService {
    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    public StripeService() {
    }

    public String createCustomer(String email, String token) {
        String id = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("description", "Customer for " + email);
            customerParams.put("email", email);
            customerParams.put("source", token);
            Customer customer = Customer.create(customerParams);
            id = customer.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public String createSubscription(String customerId, String plan) {
        String id = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            List<Object> items = new ArrayList<>();
            Map<String, Object> item1 = new HashMap<>();
            item1.put("plan", plan);
            items.add(item1);
            Map<String, Object> params = new HashMap<>();
            params.put("customer", customerId);
            params.put("items", items);
            Subscription sub = Subscription.create(params);
            id = sub.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public boolean cancelSubscription(String subscriptionId) {
        boolean status;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Subscription sub = Subscription.retrieve(subscriptionId);
            sub.cancel(null);
            status = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            status = false;
        }
        return status;
    }
}
