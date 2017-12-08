package br.com.iagolaguna.senior.challange.db;

import br.com.iagolaguna.senior.challange.pojo.CityByStateDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Integer> {
    List<City> findByCapitalOrderByNoAccents(boolean capital);

    @Query("select c.name from City c where  c.state = ?1")
    List<String> findNameByState(State state);

    @Query("select new br.com.iagolaguna.senior.challange.pojo.CityByStateDto(c.state.uf, count(c.state) as quantity) from City c  group by c.state")
    List<CityByStateDto> findQuantityOfCitiesForState();

}

