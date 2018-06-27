package dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {

}
