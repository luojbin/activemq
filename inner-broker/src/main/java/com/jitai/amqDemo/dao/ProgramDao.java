package com.jitai.amqDemo.dao;

import com.jitai.amqDemo.model.Program;
import org.springframework.stereotype.Repository;

@Repository
public class ProgramDao extends JdbcDao<Program, String> {


}