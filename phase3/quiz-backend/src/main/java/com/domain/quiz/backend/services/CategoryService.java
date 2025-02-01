package com.domain.quiz.backend.services;


import com.domain.quiz.backend.models.Category;
import com.domain.quiz.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Fetches all categories created by the specified designer.
     */
    public List<Category> getCategoriesByDesigner(String designerId) {
        return categoryRepository.findByDesignerId(designerId);
    }

    /**
     * Creates a new category.
     */
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
}
