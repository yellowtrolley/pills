package org.pablog.pills.repositories;
import java.util.List;

import org.bson.types.ObjectId;
import org.pablog.pills.domain.Day;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends PagingAndSortingRepository<Day, ObjectId> {

    List<Day> findAll();
    Day findByTheDay(String theDay);
    Day findById(ObjectId id);
}
