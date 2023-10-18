package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceLayer {
    @Autowired
    static OrderRepoistryLayer orderRepoistryLayerObj;

    public  void addOrder(Order ord) {

        orderRepoistryLayerObj.addOrder(ord);
    }

    public static void  addPartner(String partnerId) {

        orderRepoistryLayerObj.addPartner(partnerId);
    }

    public static void addOrderPartnerPair(String orderId, String partnerId) {

        orderRepoistryLayerObj.addOrderPartnerPair(orderId,partnerId);
    }

    public  static Order getOrderById(String orderId) {

        Order ans = orderRepoistryLayerObj.getOrderById(orderId);

        return ans;
    }

    public static int getOrderCountByPartnerId(String partnerId) {

        return OrderRepoistryLayer.getOrderCountByPartnerId(partnerId);
    }

    public  static DeliveryPartner getPartnerById(String  partnerId) {

        return OrderRepoistryLayer.getPartnerById(partnerId);
    }
    public  static  List<String> getOrdersByPartnerId(String  partnerId) {

        HashSet<String> ordersList = OrderRepoistryLayer.getOrdersByPartnerId(partnerId);//get from the database

        List<String> ans = new ArrayList<>();

        if(ordersList.isEmpty()) return ans;// if the no orders are assigned to him till this point

        for(String orders : ordersList) {
            ans.add(orders);
        }

        return ans;
    }

    public  static  List<String> getAllOrders() {

        return OrderRepoistryLayer.getAllOrders();
    }

    public static int getCountOfUnassignedOrders() {

        return  OrderRepoistryLayer.getCountOfUnassignedOrders();
    }

    public  static  int getOrdersLeftAfterGivenTimeByPartnerId(String time , String partnerId) {

        return OrderRepoistryLayer.getOrdersLeftAfterGivenTimeByPartnerId(time , partnerId);
    }

    public static String getLastDeliveryTimeByPartnerId(String  partnerId) {

         return OrderRepoistryLayer.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public  static  void deletePartnerById(String partnerId) {
        OrderRepoistryLayer.deletePartnerById(partnerId);
    }
    public  static  void deleteOrderById(String orderId) {
        OrderRepoistryLayer.deleteOrderById(orderId);
    }
}
