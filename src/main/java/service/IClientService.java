package service;

import java.util.List;

import model.Client;

public interface IClientService {

	public Client createClient(String name);
	public Client updateClient(Client client);
	public Client findClientByID(int id);
	public List<Client> findAllClient();
	public void deleteClient(int id);
 
	
}
