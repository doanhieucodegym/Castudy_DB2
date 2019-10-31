package com.DoanHieu.controller;

import com.DoanHieu.model.Department;
import com.DoanHieu.model.Employee;
import com.DoanHieu.model.EmployeeForm;
import com.DoanHieu.service.DepartmentService;
import com.DoanHieu.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@PropertySource("classpath:global_config_app.properties")
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    // tien doi tuong
    @Autowired
    private DepartmentService departmentService;
    //
    @Autowired
    Environment env;
    //bo xung phuong thuc lay tinh ve
    @ModelAttribute("departments")
    public Iterable<Department> departments(){
        return departmentService.findAll();
    }


    //hien thi nhan vien
        @GetMapping("/employees")
    public ModelAndView listEmployee(@RequestParam("s") Optional<String> name  , @PageableDefault(value = 5) Pageable pageable){
            Page<Employee> employees;
//                    =employeeService.findAll(pageable);
            if(name.isPresent()){
                employees = employeeService.findAllByNameContaining(name.get(), pageable);
            } else {
                employees = employeeService.findAll(pageable);
            }
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
//    @PostMapping("/create-employee")
//    public ModelAndView saveEmployee(@ModelAttribute("employee")Employee employees){
//            employeeService.save(employees);
//            ModelAndView modelAndView =new ModelAndView("/employee/create");
//            modelAndView.addObject("employee" ,new Employee());
//            modelAndView.addObject("message","New employee successfully!");
//            return modelAndView;
//    }

    @PostMapping("/create-employee")
    public ModelAndView saveEmployee(@ModelAttribute EmployeeForm employeeForm, BindingResult result) {

        // thong bao neu xay ra loi
        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        // lay ten file
        MultipartFile multipartFile = employeeForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();

        // luu file len server
        try {
            //multipartFile.transferTo(imageFile);
            FileCopyUtils.copy(employeeForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // tham khảo: https://github.com/codegym-vn/spring-static-resources

        // tao doi tuong de luu vao db
        Employee employeeObject = new Employee(employeeForm.getName(), employeeForm.getBirthDate(),employeeForm.getAddress(), fileName,employeeForm.getSalary(),employeeForm.getDepartment());

        // luu vao db
        //productService.save(productObject);
        employeeService.save(employeeObject);

        ModelAndView modelAndView = new ModelAndView("/employee/create");
        modelAndView.addObject("employee", new EmployeeForm());
        modelAndView.addObject("message"," Employee create successes!");
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
        modelAndView.addObject("message", "Employee updated successfully");
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
