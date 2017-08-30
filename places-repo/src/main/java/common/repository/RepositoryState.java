package common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import common.data.City;
import common.data.State;

@Repository
public interface RepositoryState extends JpaRepository<State, Long> {
	List<State> findByStateName(String Name);
}
