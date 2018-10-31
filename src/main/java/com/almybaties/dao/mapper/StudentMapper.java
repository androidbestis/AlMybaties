package com.almybaties.dao.mapper;

import com.almybaties.entity.Student;

public interface StudentMapper {

    public Student findbyid(int id);

    public Student findbygradeid(int id);

}
