package com.DoanHieu.repository;

import com.DoanHieu.model.Department;
import com.DoanHieu.model.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee ,Long> {
    Iterable<Employee> findAllByDepartment(Department department);
}
