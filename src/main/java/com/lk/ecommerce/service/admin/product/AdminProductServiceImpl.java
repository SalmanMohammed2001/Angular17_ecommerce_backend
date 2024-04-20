package com.lk.ecommerce.service.admin.product;

import com.lk.ecommerce.dto.core.ProductDTO;
import com.lk.ecommerce.entity.Category;
import com.lk.ecommerce.entity.Product;
import com.lk.ecommerce.repo.CategoryRepository;
import com.lk.ecommerce.repo.ProductRepository;
import com.lk.ecommerce.util.VarList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public int saveProduct(ProductDTO dto) throws IOException {
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImg(dto.getImg().getBytes());
        product.setCategory(category);
        Product save = productRepository.save(product);
        return VarList.Created;
    }

    @Override
    public List<ProductDTO> allProduct() {
        List<Product> allProduct = productRepository.findAll();
        return allProduct.stream().map(Product::getDto).collect(Collectors.toList());
      //  return modelMapper.map(allProduct,new TypeToken<List<ProductDTO>>(){}.getType());
    }

    @Override
    public List<ProductDTO> findProductByName(String name) {
        List<Product> byNameContaining = productRepository.findByNameContaining(name);
        return byNameContaining.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public int delete(UUID uuid) {
        Optional<Product> optionId = productRepository.findById(uuid);
        if(optionId.isPresent()){
            productRepository.deleteById(optionId.get().getId());
            return VarList.No_Content;
        }else {
            return VarList.Not_Acceptable;
        }
    }


    public ProductDTO getProductDto(UUID productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.map(Product::getDto).orElse(null);
    }


    public ProductDTO updateProduct(UUID productId,ProductDTO productDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(optionalProduct.isPresent() && optionalCategory.isPresent()){

            Product product = optionalProduct.get();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            //  product.setImg(productDto.getImg().getBytes());
            product.setCategory(optionalCategory.get());
            if(productDto.getImg() !=null){
                product.setImg(productDto.getImg().getBytes());
            }
            return productRepository.save(product).getDto();
        }else {
            return  null;
        }
    }


}
