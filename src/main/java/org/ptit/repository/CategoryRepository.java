package org.ptit.repository;

import org.ptit.domain.Category;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}
