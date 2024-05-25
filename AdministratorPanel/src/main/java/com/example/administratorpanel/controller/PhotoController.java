package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.Photo;
import com.example.administratorpanel.model.Product;
import com.example.administratorpanel.repo.PhotoRepository;
import com.example.administratorpanel.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.List;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    private final PhotoRepository photoRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PhotoController(PhotoRepository photoRepository, ProductRepository productRepository) {
        this.photoRepository = photoRepository;
        this.productRepository = productRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Product.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Product product = productRepository.findById(Long.parseLong(text)).orElse(null);
                setValue(product);
            }
        });
    }

    @GetMapping()
    public String listPhotos(Model model) {
        List<Photo> photos = (List<Photo>) photoRepository.findAll();
        model.addAttribute("photos", photos);
        return "photos/allPhotos";
    }

    @GetMapping("/addPhoto")
    public String showAddPhotosForm(Model model) {
        Photo photo = new Photo();
        List<Product> products = (List<Product>) productRepository.findAll();
        model.addAttribute("photo", photo);
        model.addAttribute("products", products);
        return "photos/addPhoto";
    }

    @PostMapping("/addPhoto")
    public String addPhoto(@Valid @ModelAttribute Photo photo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "photos/addPhoto";
        }
        photoRepository.save(photo);
        return "redirect:/photos";
    }

    @GetMapping("/editPhoto/{id}")
    public String showEditPhotosForm(@PathVariable Long id, Model model) {
        Photo photo = photoRepository.findById(id).orElse(null);
        List<Product> products = (List<Product>) productRepository.findAll();
        if (photo == null) {
            return "redirect:/photos";
        }
        model.addAttribute("photo", photo);
        model.addAttribute("products", products);
        return "photos/editPhoto";
    }

    @PostMapping("/editPhoto/{id}")
    public String editPhoto(@PathVariable Long id, @Valid @ModelAttribute Photo photo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "photos/editPhoto";
        }
        photoRepository.save(photo);
        return "redirect:/photos";
    }

    @GetMapping("/deletePhoto/{id}")
    public String deletePhoto(@PathVariable Long id) {
        try {
            photoRepository.deleteById(id);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        }
        return "redirect:/photos";
    }
}
