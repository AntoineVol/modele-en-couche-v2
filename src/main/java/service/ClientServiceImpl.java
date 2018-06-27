package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import dao.ClientRepository;
import model.Client;

public class ClientServiceImpl  implements IClientService{

	@Autowired
	private ClientRepository repo;

	@Override
	public Client createClient(String name) {
		return this.save(new Client(name));
	}

	@Override
	public Client updateClient(Client client) {
		if(client.getId()==null) {
			throw new IllegalArgumentException("Cannot update client without id");
		}
		return this.save(client);
	}

	@Override
	public Client findClientByID(int id) {
		Client clt = new Client();
		Optional<Client> Oclt = this.repo.findById(id);
		if(Oclt.isPresent()) {
			clt.setId(Oclt.get().getId());
			clt.setName(Oclt.get().getName());
		}
		return clt;
	}
	public void deleteClient(int id) {
		this.repo.deleteById(id);
	}
	@Override
	public List<Client> findAllClient() {
		return this.repo.findAll();
	}
	
	private Client save(Client client) {
		return this.repo.save(client);
	}
	
}
