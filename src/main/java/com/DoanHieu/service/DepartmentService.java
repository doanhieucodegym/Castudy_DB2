package com.DoanHieu.service;

import com.DoanHieu.model.Department;
import org.springframework.beans.factory.annotation.Autowired;

public interface DepartmentService {
   Iterable<Department> findAll();
   Department findById(Long id);
   void save(Department department);
   void remove(Long id);

}
