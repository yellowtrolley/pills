package org.pablog.pills.repositories;
import java.util.List;

import org.bson.types.ObjectId;
import org.pablog.pills.domain.Day;
import org.pablog.pills.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends PagingAndSortingRepository<Day, ObjectId> {

    List<Day> findByUser(User user);
    List<Day> findByUser(User user, Pageable pageable);
    Day findByTheDateAndUser(String theDate, User user);
    Day findById(ObjectId id);
}
