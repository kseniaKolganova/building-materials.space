package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.Categories;
import com.example.administratorpanel.model.Product;
import com.example.administratorpanel.repo.CategoriesRepository;
import com.example.administratorpanel.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import java.util.Iterator;


@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepo;
    private final CategoriesRepository categoriesRepo;

    @Autowired
    public ProductController(ProductRepository productRepo, CategoriesRepository categoriesRepo) {
        this.productRepo = productRepo;
        this.categoriesRepo = categoriesRepo;
    }

    @ModelAttribute("categories")
    public List<Categories> getCategories() {
        return categoriesRepo.findAll();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Categories.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Categories category = categoriesRepo.findById(Long.parseLong(text)).orElse(null);
                setValue(category);
            }
        });
    }

    @GetMapping()
    public String listProducts(Model model) {
        List<Product> products = (List<Product>) productRepo.findAll();
        model.addAttribute("products", products);
        return "products/allProducts";
    }

    @GetMapping("/addProduct")
    public String showAddProductForm(Model model) {
        List<Categories> categories = categoriesRepo.findAll();
        model.addAttribute("categories", categories);

        Product product = new Product();
        model.addAttribute("product", product);
        return "products/addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "products/addProduct";
        }
        productRepo.save(product);
        return "redirect:/products";
    }

    @GetMapping("/editProduct/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productRepo.findById(id).orElse(null);
        if (product == null) {
            return "redirect:/products";
        }

        List<Categories> categories = categoriesRepo.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "products/editProduct";
    }

    @PostMapping("/editProduct/{id}")
    public String editProduct(@PathVariable("id") Long id, @Valid @ModelAttribute("product") Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "products/editProduct";
        }
        product.setId(id);
        productRepo.save(product);
        return "redirect:/products";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        try {
            productRepo.deleteById(id);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        }
        return "redirect:/products";
    }

    @GetMapping("/exportToExcel")
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        List<Product> products = (List<Product>) productRepo.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products");
            createHeaderRow(sheet);

            int rowNum = 1;
            for (Product product : products) {
                Row row = sheet.createRow(rowNum++);
                createProductRow(product, row);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "products.xlsx");

            return new ResponseEntity<>(outputStream.toByteArray(), headers,  HttpStatus.OK);
        }
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Название", "Артикул", "Доступное количество", "Цена", "Описание", "Цвета", "Страна производства",
                "Модели", "Марка", "Вес", "Категория"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    private void createProductRow(Product product, Row row) {
        // Create cells and set values for each product attribute
        // You can modify this based on your actual product attributes
        row.createCell(0).setCellValue(product.getName());
        row.createCell(1).setCellValue(product.getArticul());
        row.createCell(2).setCellValue(product.getAvailableQuantity());
        row.createCell(3).setCellValue(product.getPrice());
        row.createCell(4).setCellValue(product.getDescription());
        row.createCell(5).setCellValue(product.getColors());
        row.createCell(6).setCellValue(product.getCountry());
        row.createCell(7).setCellValue(product.getModels());
        row.createCell(8).setCellValue(product.getMarka());
        row.createCell(9).setCellValue(product.getWeight());
        row.createCell(10).setCellValue(product.getCategory().getName());
    }

    @PostMapping("/importFromExcel")
    public ResponseEntity<String> importFromExcel(@RequestParam("file") MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = sheet.iterator();
            if (iterator.hasNext()) {
                iterator.next();
            }

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                try {
                    Product product = createProductFromRow(currentRow);
                    productRepo.save(product);
                } catch (Exception e) {
                    System.err.println("Error processing row: " + e.getMessage());
                }
            }

            return ResponseEntity.ok("Data imported successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error importing data.");
        }
    }

    private Product createProductFromRow(Row row) {
        // Create a new Product object and set its attributes from the Excel row
        // You can modify this based on your actual product attributes
        Product product = new Product();
        product.setName(getCellValueAsString(row.getCell(0)));
        product.setArticul(getCellValueAsString(row.getCell(1)));
        product.setAvailableQuantity(getCellValueAsInt(row.getCell(2)));
        product.setPrice(getCellValueAsDouble(row.getCell(3)));
        product.setDescription(getCellValueAsString(row.getCell(4)));
        product.setColors(getCellValueAsString(row.getCell(5)));
        product.setCountry(getCellValueAsString(row.getCell(6)));
        product.setModels(getCellValueAsString(row.getCell(7)));
        product.setMarka(getCellValueAsString(row.getCell(8)));
        product.setWeight(getCellValueAsString(row.getCell(9)));

        // Assuming the category is a string in the Excel file
        product.setCategory(categoriesRepo.findByName(getCellValueAsString(row.getCell(10))));

        return product;
    }

    // Helper method to get cell value as string, handling different cell types
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Handle numeric cells as strings
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null;
        }
    }

    // Helper method to get cell value as integer
    private int getCellValueAsInt(Cell cell) {
        if (cell == null) {
            return 0; // Default value or handle accordingly
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            default:
                return 0; // Default value or handle accordingly
        }
    }

    // Helper method to get cell value as double
    private double getCellValueAsDouble(Cell cell) {
        if (cell == null) {
            return 0.0; // Default value or handle accordingly
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            default:
                return 0.0; // Default value or handle accordingly
        }
    }

}
