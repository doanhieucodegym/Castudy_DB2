package com.DoanHieu.controller;

import com.DoanHieu.model.Department;
import com.DoanHieu.model.Employee;
import com.DoanHieu.service.DepartmentService;
import com.DoanHieu.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    //goi do tuong
    @Autowired
    private EmployeeService employeeService;

    // hien thi
    @GetMapping("/departments")
    public ModelAndView listDepartment() {
        Iterable<Department> departments = departmentService.findAll();
        ModelAndView modelAndView = new ModelAndView("/department/list");
        modelAndView.addObject("department", departments);
        return modelAndView;
    }

    // tao moi
    @GetMapping("/create-department")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/department/create");
        modelAndView.addObject("department", new Department());
        return modelAndView;
    }

    //
    @PostMapping("/create-department")
    public ModelAndView saveDepartment(@ModelAttribute("department") Department department) {
        departmentService.save(department);
        ModelAndView modelAndView = new ModelAndView("/department/create");
        modelAndView.addObject("department", new Department());
        modelAndView.addObject("message", "New department create successfully!");
        return modelAndView;
    }

    //edit sua
    @GetMapping("/edit-department/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Department department = departmentService.findById(id);
        if (department != null) {
            ModelAndView modelAndView = new ModelAndView("/department/edit");
            modelAndView.addObject("department", department);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    //thuc hien  sua
    @PostMapping("/edit-department")
    public ModelAndView updateDepartment(@ModelAttribute("department") Department department) {
        departmentService.save(department);
        ModelAndView modelAndView = new ModelAndView("/department/edit");
        modelAndView.addObject("department", department);
        modelAndView.addObject("message", "Department updated successfully");
        return modelAndView;
    }

    //delete
    @GetMapping("/delete-department/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Department department = departmentService.findById(id);
        if (department != null) {
            ModelAndView modelAndView = new ModelAndView("/department/delete");
            modelAndView.addObject("department", department);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-department")
    public String deleteProvince(@ModelAttribute("department") Department department) {
        departmentService.remove(department.getId());
        return "redirect:departments";
    }

    // view department
    @GetMapping("/view-department/{id}")
    public ModelAndView viewDepartment(@PathVariable("id") Long id) {
        Department department = departmentService.findById(id);
        if (department == null) {
            return new ModelAndView("/error.404");
        }

        Iterable<Employee> employees = employeeService.finAllByDepartment(department);

        ModelAndView modelAndView = new ModelAndView("/department/view");
        modelAndView.addObject("department", department);
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }
}
