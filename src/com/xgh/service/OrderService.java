package com.xgh.service;

import com.nxy.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    public static List<Order> FindOrder(int UserID)
    {
        List<Order> list = new ArrayList<>();


        Connection conn = ConnectionGenerator.GetConnetct();
        String sql = "SELECT * FROM 94train.order WHERE OrderID = ?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            pstmt.setInt(1,UserID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                Order temp = new Order();
                temp.setOrderID(rs.getInt("OrderID"));
                temp.setUserID(rs.getInt("UserID"));
                temp.setTrainID(rs.getString("TrainID"));
                temp.setStartStation(rs.getString("StartStation"));
                temp.setEndStation(rs.getString("EndStation"));
                temp.setDepartureTime(rs.getTime("DepartureTime"));
                temp.setArrivalTime(rs.getTime("ArrivalTime"));
                temp.setType(rs.getInt("Type"));
                temp.setStatus(rs.getInt("Statua"));
                temp.setPrice(rs.getDouble("Price"));
                temp.setOrderDate(rs.getDate("OrderDate"));
                list.add(temp);
            }
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean ChangeOrder(Order order)
    {
        int i =0;
        Connection conn = ConnectionGenerator.GetConnetct();
        String sql = "UPDATE 94train.order SET Status = ? \n" +
                "WHERE 94train.order.OrderID = ?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            pstmt.setInt(1,order.getStatus());
            pstmt.setInt(2,order.getOrderID());
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(i==1)
            return true;
        return false;
    }
}
