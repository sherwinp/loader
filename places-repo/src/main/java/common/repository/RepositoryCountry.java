package common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import common.data.City;

@Repository
public interface RepositoryCountry extends JpaRepository<City, String> {


}
