package org.pablog.pills.repositories;
import java.util.List;

import org.bson.types.ObjectId;
import org.pablog.pills.domain.PUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<PUser, ObjectId> {

    List<PUser> findAll();
    PUser findByUsername(String username);
    PUser findById(ObjectId id);
}
