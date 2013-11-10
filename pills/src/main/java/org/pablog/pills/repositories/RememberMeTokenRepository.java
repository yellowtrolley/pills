package org.pablog.pills.repositories;

import java.io.Serializable;
import java.util.List;

import org.pablog.pills.domain.RememberMeToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RememberMeTokenRepository extends CrudRepository<RememberMeToken, Serializable> {
	RememberMeToken findBySeries(String series);
    List<RememberMeToken> findByUsername(String username);
}
