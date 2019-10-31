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
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    // tien doi tuong
    @Autowired
    private DepartmentService departmentService;
    //bo xung phuong thuc lay tinh ve
    @ModelAttribute("departments")
    public Iterable<Department> departments(){
        return departmentService.findAll();
    }


    //hien thi nhan vien
        @GetMapping("/employees")
    public ModelAndView listEmployee(){
            Iterable<Employee> employees =employeeService.findAll();
            ModelAndView modelAndView = new ModelAndView("/employee/list");
            modelAndView.addObject("employees", employees);
            return modelAndView;
        }
        //them  moi  employee
    // hien thu  fomre them  de viet du  lieu
    @GetMapping("/create-employee")
    public ModelAndView showCreateForm(){
            ModelAndView modelAndView =new ModelAndView("/employee/create");
            modelAndView.addObject("employee",new Employee());
            return modelAndView;
    }
    //thuc hien lay du lieu
    @PostMapping("/create-employee")
    public ModelAndView saveEmployee(@ModelAttribute("employee")Employee employees){
            employeeService.save(employees);
            ModelAndView modelAndView =new ModelAndView("/employee/create");
            modelAndView.addObject("employee" ,new Employee());
            modelAndView.addObject("message","New employee successfully!");
            return modelAndView;
    }
    // edit employee
    @GetMapping("/edit-employee/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
            Employee employee =employeeService.findById(id);
            if(employee !=null){
                ModelAndView modelAndView =new ModelAndView("/employee/edit");
                modelAndView.addObject("employee" ,employee);
                return modelAndView;
            }else{
                ModelAndView modelAndView = new ModelAndView("/error.404");
                return modelAndView;
            }
    }
    // nha thong tin  phan tich
    @PostMapping("/edit-employee")
    public ModelAndView updateCustomer(@ModelAttribute("employee") Employee employee){
        employeeService.save(employee);
        ModelAndView modelAndView = new ModelAndView("/employee/edit");
        modelAndView.addObject("employee", employee);
        modelAndView.addObject("message", "Customer updated successfully");
        return modelAndView;
    }
    //xoa
    @GetMapping("/delete-employee/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Employee employee = employeeService.findById(id);
        if(employee != null) {
            ModelAndView modelAndView = new ModelAndView("/employee/delete");
            modelAndView.addObject("employee", employee);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-employee")
    public String deleteCustomer(@ModelAttribute("employee") Employee employee){
        employeeService.remove(employee.getId());
        return "redirect:employees";
    }
    //


}
