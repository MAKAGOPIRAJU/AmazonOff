package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepoistryLayer {

   static HashMap<String , Order> OrderData = new HashMap<>();// orderId(uniqueId) and order

   static  HashMap<String , HashSet<String>> orderListOfDeliveryPartner = new HashMap<>();// partnerId and allDeliversAssignedTODeliveryPartner


    public  void  addOrder(Order ord) {

        // add this order into orderData

        String key = ord.getId();

        OrderData.put(key , ord);// add the order into  the orderData
    }

    public void addPartner(String partnerId) {

        orderListOfDeliveryPartner.put(partnerId , new HashSet<>());// for now add as temporary orders as zero orders
    }

    public  void addOrderPartnerPair(String orderId , String partnerId) {

        // here there is a chance of there is no deliverAgen who having this partnerId(need to check this)

        if(orderListOfDeliveryPartner.containsKey(partnerId)) {

            // if the partner was already exist
            HashSet<String> set = orderListOfDeliveryPartner.get(partnerId);

            set.add(orderId); // add that order into this delivery partner

            orderListOfDeliveryPartner.put(partnerId,set);
        }

        else{

            HashSet<String> set = new HashSet<>();
            set.add(orderId);
            orderListOfDeliveryPartner.put(partnerId,set);
        }
    }

    public Order getOrderById(String orderId) {

        if(!OrderData.containsKey(orderId)) return null; // if there is no order with this order

        Order ans = OrderData.get(orderId);

        return ans;
    }

    public static int getOrderCountByPartnerId(String partnerId) {

        if(!orderListOfDeliveryPartner.containsKey(partnerId)) return 0; // if there is no delivery partner

        return orderListOfDeliveryPartner.get(partnerId).size();

    }

    public static DeliveryPartner getPartnerById(String partnerId) {

        int totalOrders;

        if(!orderListOfDeliveryPartner.containsKey(partnerId)) return null;//if the user was not exist return null

        totalOrders = orderListOfDeliveryPartner.get(partnerId).size();

        DeliveryPartner obj = new DeliveryPartner(partnerId);

        obj.setNumberOfOrders(totalOrders); // update the number of orders


        return obj;
    }

    public static  HashSet<String> getOrdersByPartnerId(String partnerId) {

        if(!orderListOfDeliveryPartner.containsKey(partnerId)) return new HashSet<>();

        return orderListOfDeliveryPartner.get(partnerId);
    }

    public  static List<String> getAllOrders() {

        List<String> allOrders = new ArrayList<>();

        for(String orderId : OrderData.keySet()) {

            allOrders.add(orderId);
        }

        return allOrders;
    }

    public  static int getCountOfUnassignedOrders() {

        int totalOrders = OrderData.size();

        int numberOfOrdersAssignedToDeliveryPartner = 0;


        for(HashSet<String> ordersList: orderListOfDeliveryPartner.values()) {

            numberOfOrdersAssignedToDeliveryPartner = numberOfOrdersAssignedToDeliveryPartner + ordersList.size();
        }

        return totalOrders - numberOfOrdersAssignedToDeliveryPartner;
    }
    public static  int getOrdersLeftAfterGivenTimeByPartnerId(String time , String partnerId) {

        HashSet<String> ordersList = orderListOfDeliveryPartner.get(partnerId);

        int count = 0;

        for(String orderId : ordersList) {

            int givenDeadLine = Integer.parseInt(time.substring(0,2)) * 60 + Integer.parseInt(time.substring(2));//conver the given string into integer

            int currentOrderIdDeadLine = OrderData.get(orderId).getDeliveryTime();// get the current order time

            if(currentOrderIdDeadLine > givenDeadLine) count++;
        }

        return count;
    }

    public  static  String getLastDeliveryTimeByPartnerId(String partnerId) {


        if(!orderListOfDeliveryPartner.containsKey(partnerId)) return null;

        HashSet<String> ordersList = orderListOfDeliveryPartner.get(partnerId);

        int maxTime = 0;
        for(String orderId : ordersList) {

           int currTime = OrderData.get(orderId).getDeliveryTime();

           maxTime = Math.max(maxTime , currTime);
        }

        int hours = maxTime / 60;

        int minutes = maxTime % 60;

        String time = "";

        if(hours < 10) time = "0" + hours;
        else time = time + hours;

        if(minutes < 10) time = time + ":0" + minutes;
        else time = time + ":"+minutes;

        return time;
    }

    public  static void deletePartnerById(String partnerId) {

        if(!orderListOfDeliveryPartner.containsKey(partnerId)) return;

        orderListOfDeliveryPartner.remove(partnerId);
    }

    public  static  void deleteOrderById(String orderId) {

        if(!OrderData.containsKey(orderId)) return;//if the orderId was not already exist return

        for(HashSet<String> orderIdList : orderListOfDeliveryPartner.values()) {

            if(orderIdList.contains(orderId)) orderIdList.remove(orderId);//remove that orderId from delivery partner orders list
        }

        OrderData.remove(orderId);//remove from the orderList also
    }

}
