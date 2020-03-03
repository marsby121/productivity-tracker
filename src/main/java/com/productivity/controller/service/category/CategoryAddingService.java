package com.productivity.controller.service.category;

import com.productivity.controller.service.record.RecordAddingResult;
import com.productivity.controller.service.record.RecordAddingService;
import com.productivity.model.category.Category;
import com.productivity.model.category.CategoryManager;
import com.productivity.model.category.CategoryType;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import static com.productivity.controller.service.category.CategoryAddingResult.*;

public class CategoryAddingService extends Service<CategoryAddingResult> {

    private Category category;
    private CategoryManager categoryManager;

    public CategoryAddingService(Category category, CategoryManager categoryManager) {
        this.category = category;
        this.categoryManager = categoryManager;
    }

    @Override
    protected Task<CategoryAddingResult> createTask() {
        return new Task<CategoryAddingResult>() {
            @Override
            protected CategoryAddingResult call() throws Exception {
                return addCategory();
            }
        };
    }

    private CategoryAddingResult addCategory() {
        CategoryAddingResult result = validateCategory();
        addIfSuccess(result);
        return result;
    }

    private void addIfSuccess(CategoryAddingResult result) {
        if (result == SUCCESS) {
            categoryManager.addCategory(category);
        }
    }

    private CategoryAddingResult validateCategory() {
        String name = category.getName();
        if (name.isEmpty()) {
            return SHOULD_HAVE_NAME;
        }
        if (categoryManager.hasCategoryWith(name)) {
            return DUPLICATED;
        }
        return SUCCESS;
    }
}
