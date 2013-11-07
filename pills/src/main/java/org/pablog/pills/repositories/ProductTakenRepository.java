package org.pablog.pills.repositories;
import java.util.List;

import org.bson.types.ObjectId;
import org.pablog.pills.domain.ProductTaken;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTakenRepository extends PagingAndSortingRepository<ProductTaken, ObjectId> {

    List<ProductTaken> findAll();
    ProductTaken findById(ObjectId id);
}
