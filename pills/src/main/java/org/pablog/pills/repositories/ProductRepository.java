package org.pablog.pills.repositories;
import java.util.List;

import org.bson.types.ObjectId;
import org.pablog.pills.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, ObjectId> {

    List<Product> findAll();
    Product findById(ObjectId id);
}
