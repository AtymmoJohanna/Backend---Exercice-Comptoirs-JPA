package comptoirs.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import comptoirs.entity.Commande;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {

    @Query( value = """
            SELECT numero, SUM(quantite*prix_unitaire*(1- remise)) AS montant FROM ligne
            INNER JOIN produit ON Produit_Reference = Reference
            INNER JOIN commande ON commande_numero = numero
            WHERE commande_numero = :numeroCommande
            GROUP BY numero
    """,
            nativeQuery = true )
    List<CoutCommande> coutCommande(Integer numeroCommande);


    @Query( value = """
            SELECT numero, port, SUM(quantite*prix_unitaire*(1- remise)) AS montant FROM ligne
            INNER JOIN produit ON Produit_Reference = Reference
            INNER JOIN commande ON commande_numero = numero
            WHERE client_code = :codeClient
            GROUP BY numero, port
    """,
            nativeQuery = true )
    List<coutCommandesParClient> coutParCommande(String codeClient);




}
