package com.lk.ecommerce.service.admin.category;

import com.lk.ecommerce.dto.core.CategoryDTO;
import org.hibernate.Cache;

import java.util.List;

public interface CategoryService {

    public int SaveCategory(CategoryDTO categoryDTO);

    public List<CategoryDTO> findAll ();
}
