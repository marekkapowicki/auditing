package pl.marekk.auditing.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;

interface CategoryRepository extends RevisionRepository<Category, Long, Integer>, JpaRepository<Category, Long>{

}
