package com.jitai.amqDemo.dao;

import com.jitai.amqDemo.model.Demand;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class DemandDao extends JdbcDao<Demand, String> {
	
	public Demand getById(String id) throws SQLException{
		String sql = "SELECT id, customer, input, output FROM `DEMAND` WHERE id = ?";
		return querySingleBean(sql, new String[]{id},Demand.class);
	}

}