package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Client;

public class SQLClientDAO extends ClientDAO {

	public Client update(Client client) {
		Connection cn = null;
		PreparedStatement st = null;
		// initialisation du result set
		try {
			cn = getConnection();

			String sql = "UPDATE client SET name= ? WHERE id=?";

			st = cn.prepareStatement(sql);

			st.setString(1, client.getName());
			st.setInt(2, client.getId());

			st.executeUpdate();
			cn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client;
	}

	public Client create(Client client) {
		Connection cn = null;
		PreparedStatement st = null;
		// initialisation du result set
		try {
			cn = getConnection();

			String sql = "INSERT INTO client(nom) VALUES (?)";

			st = cn.prepareStatement(sql);

			st.setString(1, client.getName());

			st.executeUpdate();
			cn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client;
	}

	@Override
	public Client delete(Client client) {
		// TODO: Implements...
		return client;
	}

	@Override
	public Client read(Client client) {
		Connection cn = null;
		PreparedStatement st = null;
		// initialisation du result set
		ResultSet rs = null;
		Client cl = new Client();

		try {
			cn = getConnection();

			String sql = "SELECT * FROM client WHERE id =" + client.getId();

			st = cn.prepareStatement(sql);

			rs = st.executeQuery();

			// NE PAS OUBLIER car la connection �tablie d�sactive le
			// le commit automatique
			cn.commit();

			while (rs.next()) {
				cl.setName(rs.getString(2));
				cl.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cl;
	}

	@Override
	public List<Client> findAll() {
		// nouvelle liste pour r�sultats
		List<Client> results = new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		// initialisation du result set
		ResultSet rs = null;

		try {
			cn = getConnection();

			String sql = "SELECT * FROM client";

			st = cn.prepareStatement(sql);

			rs = st.executeQuery();

			// NE PAS OUBLIER car la connection �tablie d�sactive le
			// le commit automatique
			cn.commit();

			while (rs.next()) {
				Client cl = new Client();
				cl.setName(rs.getString(2));
				cl.setId(rs.getInt(1));
				results.add(cl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

}
