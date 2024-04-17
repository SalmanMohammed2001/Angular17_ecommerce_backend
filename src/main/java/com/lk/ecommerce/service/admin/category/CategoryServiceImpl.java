package com.lk.ecommerce.service.admin.category;

import com.lk.ecommerce.dto.core.CategoryDTO;
import com.lk.ecommerce.entity.Category;
import com.lk.ecommerce.repo.CategoryRepository;
import com.lk.ecommerce.util.VarList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public int SaveCategory(CategoryDTO categoryDTO) {
        if (!categoryRepository.existsByName(categoryDTO.getName())) {
            Category category = modelMapper.map(categoryDTO, Category.class);
            categoryRepository.save(category);
            return VarList.Created;
        } else {
            return VarList.Not_Acceptable;
        }
    }

    @Override
    public List<CategoryDTO> findAll() {
        return modelMapper.map(categoryRepository.findAll(), new TypeToken<List<CategoryDTO>>() {
        }.getType());
    }
}
