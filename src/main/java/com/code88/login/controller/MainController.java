package com.code88.login.controller;

import com.code88.login.dto.ProductDto;
import com.code88.login.entity.Product;
import com.code88.login.entity.Staff;
import com.code88.login.service.ProductService;
import com.code88.login.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ProductService productService;

    @Autowired
    private StaffService staffService;

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String home(Model model){
        String keyword=null;
        return findPaginatedByStaff(1,"firstName","asc",keyword,model);
//        return findPaginatedByProduct(1,keyword,model);
//        model.addAttribute("productLists",productService.getALlProdcts());
//        return "home";

    }

//-----------------------------------------------------------------------
    // start of staff

    @GetMapping("showNewStaffForm")
    public String showNewStaffForm(Model model){
        Staff staff=new Staff();
        model.addAttribute("staff",staff);
        return "staff/add_new_staff";
    }

    @PostMapping("savedStaff")
    public String savedStaff(@ModelAttribute("staff")Staff staff) {
    staffService.savedStaff(staff);
    return "redirect:/";
}
    //end of new staff

    //start updating staff with id
    @GetMapping("update_staff/{id}")
    public String updateStaff(@PathVariable("id")Long id, Model model){

        Staff staff=staffService.getStaffById(id);
        model.addAttribute("staff",staff);
        return "staff/update_staff";

    }
    //end of updated staff with id


    //start deleting staff
    @GetMapping("deleteStaff/{id}")
    public String deleteStaff(@PathVariable("id")Long id){
        //call delete staff method
        staffService.deleteStaff(id);
        return "redirect:/";
    }

    //creating page
    @GetMapping("page/{pageNo}")
    public String findPaginatedByStaff(@PathVariable("pageNo")int pageNo,
                                @RequestParam(value = "sortField",required = false,defaultValue = "firstName")String sortField,
                                @RequestParam(value = "sortDirection",required = false,defaultValue = "asc")String sortDirection,
                                @RequestParam(value = "keyword",required = false)String keyword,
                                Model model){
        int pageSize=5;

        Page<Staff> page=staffService.findPaginated(pageNo,pageSize,sortField,sortDirection,keyword);
        List<Staff> staffList=page.getContent();

        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());

        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDirection",sortDirection);
        model.addAttribute("reverseSortDirection",sortDirection.equals("asc")?"desc":"asc");
        model.addAttribute("staffLists",staffList);
        model.addAttribute("keyword",keyword);

        return "home";
    }

    //This is end of staff role

  //  -----------------------------------------------------------------

    //Start of product

//    @GetMapping("createNewProduct")
//    public String createNewProduct(Model model){
//        ProductDto productDto=new ProductDto();
//        model.addAttribute("productDto",productDto);
//        return "product/createProduct";
//    }
//
//    @PostMapping("savedProduct")
//    public String savedProduct(
//            @Valid @ModelAttribute ProductDto productDto,
//            BindingResult bindingResult
//    ){
//        if(productDto.getImageFile().isEmpty()){
//            bindingResult.addError(new FieldError("productDto","imageFile","The image file is required"));
//
//        }
//
//        if (bindingResult.hasErrors()){
//            return "product/createProduct";
//        }
//
//        //save image file
//
//        MultipartFile image=productDto.getImageFile();
//        Date createdAt=new Date();
//        String storageFileName=createdAt.getTime()+"_"+image.getOriginalFilename();
//
//        try {
//            String uploadDir="public/images/";
//            Path uploadPath= Paths.get(uploadDir);
//
//            if(!Files.exists(uploadPath)){
//                Files.createDirectories(uploadPath);
//            }
//            try (InputStream inputStream=image.getInputStream()){
//                Files.copy(inputStream,Paths.get(uploadDir + storageFileName),
//                        StandardCopyOption.REPLACE_EXISTING);
//
//            }
//        }catch (Exception e){
//            System.out.println("Exception"+e.getMessage());
//        }
//
//        Product product=new Product();
//        product.setName(productDto.getName());
//        product.setBrand(productDto.getBrand());
//        product.setCategory(productDto.getCategory());
//        product.setPrice(productDto.getPrice());
//        product.setDescription(productDto.getDescription());
//        product.setCreatedAt(createdAt);
//        product.setImageFileName(storageFileName);
//
//        productService.saveProduct(product);
//        return "redirect:/";
//    }
//
//
//    @GetMapping("update")
//    public String showUpdatePage(Model model,@RequestParam Long id){
//
//        try{
//            Product product=productService.getById(id).get();
//            model.addAttribute("product",product);
//
//            ProductDto productDto=new ProductDto();
//            productDto.setName(product.getName());
//            productDto.setBrand(product.getBrand());
//            productDto.setCategory(product.getCategory());
//            productDto.setPrice(product.getPrice());
//            productDto.setDescription(product.getDescription());
//
//            model.addAttribute("productDto",productDto);
//        }catch (Exception e){
//            System.out.println("Exception "+e.getMessage());
//            return "redirect:/";
//        }
//        return "product/updateProduct";
//    }
//
//    @PostMapping("update")
//    public String update(Model model,
//                         @RequestParam Long id,
//                         @Valid @ModelAttribute ProductDto productDto,
//                         BindingResult bindingResult){
//
//        try {
//            Product product=productService.getById(id).get();
//            model.addAttribute("product",product);
//
//            if(bindingResult.hasErrors()){
//                return "product/updateProduct";
//            }
//            if(!productDto.getImageFile().isEmpty()){
//                //delete old image
//                String uploadDir="public/images/";
//                Path oldImagePath= Paths.get(uploadDir+product.getImageFileName());
//
//                try {
//                    Files.delete(oldImagePath);
//                }catch (Exception e){
//                    System.out.println("Exception "+e.getMessage());
//                }
//
//                //save new image file
//                MultipartFile image=productDto.getImageFile();
//                Date createdAt=new Date();
//                String storageFileName=createdAt.getTime()+"_"+image.getOriginalFilename();
//
//                try (InputStream inputStream=image.getInputStream()){
//                    Files.copy(inputStream,Paths.get(uploadDir + storageFileName),
//                            StandardCopyOption.REPLACE_EXISTING);
//                }
//                product.setImageFileName(storageFileName);
//            }
//            product.setName(productDto.getName());
//            product.setBrand(productDto.getBrand());
//            product.setCategory(productDto.getCategory());
//            product.setPrice(productDto.getPrice());
//            product.setDescription(productDto.getDescription());
//
//            productService.saveProduct(product);
//        }catch (Exception e){
//            System.out.println("Exception"+e.getMessage());
//        }
//
//        return "redirect:/";
//    }
//
//    @GetMapping("deleteProduct")
//    public String deleteProduct(@RequestParam Long id){
//
//        try {
//            Product product=productService.getById(id).get();
//
//            Path imagePath= Paths.get("public/images/"+product.getImageFileName());
//            try{
//                Files.delete(imagePath);
//            }catch (Exception e){
//                System.out.println("Exception"+e.getMessage());
//            }
//            productService.delete(product);
//
//        }catch (Exception e){
//            System.out.println("Exception "+e.getMessage());
//        }
//        return "redirect:/";
//    }
//
//    @GetMapping("page/{pageNo}")
//    public String findPaginatedByProduct(@PathVariable("pageNo")int pageNo,
//                                @RequestParam(value = "keyword",required = false)String keyword,
//                                Model model){
//        int pageSize=5;
//
//        Page<Product> page=productService.findByAll(pageNo,pageSize,keyword);
//        List<Product> productList=page.getContent();
//
//        model.addAttribute("currentPage",pageNo);
//        model.addAttribute("totalPages",page.getTotalPages());
//        model.addAttribute("totalItems",page.getTotalElements());
//
//
//        model.addAttribute("productLists",productList);
//        model.addAttribute("keyword",keyword);
//
//        return "home";
//    }

    //This is end of product






}
