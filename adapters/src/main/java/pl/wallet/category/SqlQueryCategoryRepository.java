package pl.wallet.category;

import org.springframework.data.repository.Repository;

interface SqlQueryCategoryRepository extends CategoryQueryRepository, Repository<CategoryDto, Long> {
}
