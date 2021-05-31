package com.jitai.amqDemo.dao;

import com.jitai.amqDemo.model.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao extends JdbcDao<Student, String> {


}