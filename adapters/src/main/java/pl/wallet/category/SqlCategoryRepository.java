package pl.wallet.category;

import org.springframework.data.jpa.repository.JpaRepository;

interface SqlCategoryRepository extends CategoryRepository, JpaRepository<Category, Long> {
}
