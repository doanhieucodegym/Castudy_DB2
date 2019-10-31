package com.DoanHieu.service;

import com.DoanHieu.model.Department;
import com.DoanHieu.model.Employee;

import java.util.List;

public interface EmployeeService {
    Iterable<Employee> findAll();
    Employee findById(Long id);

    void save(Employee employee);

    void remove(Long id);
    Iterable<Employee>finAllByDepartment(Department department);
}
