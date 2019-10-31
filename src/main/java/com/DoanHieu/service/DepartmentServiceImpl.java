package com.DoanHieu.service;

import com.DoanHieu.model.Department;
import com.DoanHieu.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(Long id) {
        return departmentRepository.findOne(id);
    }

    @Override
    public void save(Department department) {
    departmentRepository.save(department);
    }

    @Override
    public void remove(Long id) {
    departmentRepository.delete(id);
    }
}
