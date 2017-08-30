package common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import common.data.City;
import common.data.Country;
import common.data.State;

@Repository
public interface RepositoryCountry extends JpaRepository<Country, Long> {

	List<Country> findByCountryName(String Name);

}
