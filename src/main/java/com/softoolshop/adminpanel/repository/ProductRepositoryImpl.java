package com.softoolshop.adminpanel.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.softoolshop.adminpanel.dto.ProductFilterDTO;
import com.softoolshop.adminpanel.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<Product> getFilteredProducts(ProductFilterDTO req) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        if (req.getMinPrice() >= 0 && req.getMaxPrice() > 0) {
            predicates.add(cb.between(product.get("numericPrice"), req.getMinPrice(), req.getMaxPrice()));
        }

        if (req.getCategoryId() != null && req.getCategoryId() > 0) {
        	predicates.add(cb.equal(product.get("categoryId"), req.getCategoryId()));
        }

        if (req.getSearchText() != null && !req.getSearchText().isEmpty()) {
            predicates.add(cb.like(cb.lower(product.get("title")), "%" + req.getSearchText().toLowerCase() + "%"));
        }
        query.select(product).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
	}

}
