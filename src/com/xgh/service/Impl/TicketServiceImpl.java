package com.xgh.service.Impl;

import com.nxy.model.TrainTable;
import com.xgh.service.TicketService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TRY-CATCH 和 各种CLOSE() 有问题!!!!!!!!!!!!
public class TicketServiceImpl implements TicketService{
    public List<TrainTable> SearchByID(String TrainID){
        List<TrainTable> list = new ArrayList<>();
        Connection conn = ConnectionGenerator.GetConnetct();
        String sql = "Select * \n" +
                "From 94train.section\n" +
                "where section.TrainID = ?\n" +
                "order by section.Index";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, TrainID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                TrainTable temp = new TrainTable();
                temp.setStrainID(rs.getString("TrainID"));
                temp.setArrivalTime(rs.getTime("ArrivalTime"));
                temp.setRepatureTime(rs.getTime("DepartureTime"));
                temp.setCountLeft(rs.getInt("Count"));
                temp.setPrice(rs.getDouble("Price"));
                temp.setStartStation(GetStationNameByID(rs.getInt("StationID")));
                list.add(temp);
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<TrainTable> SearchByStation(String StartStation,String EndStation){
        //返回的结果
        List<TrainTable> list = new ArrayList<>() ;

        Connection conn = ConnectionGenerator.GetConnetct();
        String sql = "SELECT *\n" +
                "FROM (SELECT *\n" +
                "from 94train.section\n" +
                "where section.StationID =(SELECT StationID FROM 94train.station where 94train.station.StationName=?)) as s  join (SELECT *\n" +
                "from 94train.section\n" +
                "where section.StationID =(SELECT StationID FROM 94train.station where 94train.station.StationName=?)) as e on s.TrainID = e.TrainID\n" +
                "where s.index < e.index;";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, StartStation);
            pstmt.setString(2, EndStation);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                TrainTable temp = new TrainTable();
                temp.setStrainID(rs.getString("s.TrainID"));
                temp.setStartStation(StartStation);
                temp.setEndStartion(EndStation);
                temp.setArrivalTime(rs.getTime("e.ArrivalTime"));
                temp.setRepatureTime(rs.getTime("s.DepartureTime"));
                //大工程~~~~?
                CalSumAndTicketCount(conn,temp,rs.getInt("s.Index"),rs.getInt("e.Index"),temp.getStrainID());
                list.add(temp);
            }
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }


    private void CalSumAndTicketCount(Connection conn,TrainTable temp,int s,int e,String TrainID){
        String sql = "SELECT sum(Price),min(count)\n" +
                "From 94train.section\n" +
                "where 94train.section.Index>=? and 94train.section.Index<? and section.TrainID = ?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, s);
            pstmt.setInt(2, e);
            pstmt.setString(3, TrainID);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            temp.setPrice(rs.getDouble(1));
            temp.setCountLeft(rs.getInt(2));
            pstmt.close();
            rs.close();
            conn.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private String GetStationNameByID(int id){
        Connection conn = ConnectionGenerator.GetConnetct();
        String name = null;
        String sql = "SELECT StationName\n" +
                "FROM 94train.station\n" +
                "where StationID = ?";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            name = rs.getString(1);
            pstmt.close();
            rs.close();
            conn.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return name;
    }
}
