package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Modèle de DAO pour l'accès au tables de la base de données "blog".
 * 
 * @author Arcanis
 *
 * @param <ENTITY> POJO représentant une table de la base de données "blog".
 */
public abstract class AbstractDAO<ENTITY> {

	/**
	 * Déclaration "SonarQube compliant" d'un Logger Java.
	 */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(AbstractDAO.class);
	/**
	 * URL de connexion JDBC utilisant les paramètres décrits par MySQL <a href=
	 * "https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html">ici</a>.
	 */
	private final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/blog?useSSL=false&user=root&password=root";

	/**
	 * Instance singleton de l'objet de connexion JDBC.
	 */
	private static Connection CONNECTION;

	/**
	 * Méthode utile aux classes enfant pour récupérer l'objet de connexion et
	 * l'utiliser dans des requêtes SQL.
	 * 
	 * @return {@link Connection} l'instance de connexion SQL unique.
	 */
	protected Connection getConnection() {
		if (AbstractDAO.CONNECTION == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				AbstractDAO.CONNECTION = DriverManager
						.getConnection(CONNECTION_URL);
			} catch (SQLException | ClassNotFoundException e) {
				AbstractDAO.LOGGER.debug(
						"URL ou driver de connection à la base de données incorrecte : "
								+ "impossible d'établir la connexion avec la base de données MySQL.");
				throw new RuntimeException(
						"Connexion à la base de données impossible, veuillez arrêter l'application.",
						e);
			}
		}
		return AbstractDAO.CONNECTION;
	}

	/**
	 * Méthode de Object utilisée pour libérer des ressources juste avant que la
	 * classe soit collectée et détruite définitivement par le garbage
	 * collector.
	 */
	@Override
	protected void finalize() throws Throwable {
		if (AbstractDAO.CONNECTION != null) {
			AbstractDAO.CONNECTION.close();
		}
	}

	/**
	 * Créer une entité dans la base de données.
	 * 
	 * @param entity l'entité à créer avec id=null.
	 * @return ENTITY l'entité créée avec l'id rempli ou null.
	 */
	public abstract ENTITY create(final ENTITY entity);

	/**
	 * Récupérer une entité depuis la base de données.
	 * 
	 * @param entity l'entité à recherche avec id rempli.
	 * @return ENTITY l'entité trouvée ou null.
	 */
	public abstract ENTITY read(final ENTITY entity);

	/**
	 * Mise à jour d'une entité dans la base de données.
	 * 
	 * @param entity l'entité à mettre à jour avec id rempli.
	 * @return ENTITY l'entité mise à jour ou null.
	 */
	public abstract ENTITY update(final ENTITY entity);

	/**
	 * Suppression d'une entité dans la base de données.
	 * 
	 * @param entity l'entité à supprimer avec id rempli.
	 * @return ENTITY l'entité supprimée ou null.
	 */
	public abstract ENTITY delete(final ENTITY entity);

	/**
	 * Récupération de toute les lignes de la table SQL concernée.
	 * 
	 * @return List<ENTITY> la liste des entités représentant chaque lignes de
	 *         la table SQL.
	 */
	public abstract List<ENTITY> findAll();
}