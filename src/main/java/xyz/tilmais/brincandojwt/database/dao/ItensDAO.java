package xyz.tilmais.brincandojwt.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.tilmais.brincandojwt.database.entity.Item;
import xyz.tilmais.brincandojwt.models.ItemModel;
import xyz.tilmais.brincandojwt.services.ConectorService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItensDAO {

    private ConectorService conector;

    @Autowired
    public ItensDAO(ConectorService conector) {
        this.conector = conector;
    }

    public Integer inserirItem(ItemModel itemModel) throws SQLException {
        Integer id = null;
        String query = "INSERT INTO itens_tbl(nome, descricao, quantidade, preco) VALUES(?, ?, ?, ?);";

        try (Connection connection = this.conector.getConnection()) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, itemModel.getNome());
                preparedStatement.setString(2, itemModel.getDescricao());
                preparedStatement.setInt(3, itemModel.getQuantidade());
                preparedStatement.setBigDecimal(4, itemModel.getPreco());
                preparedStatement.execute();

                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        id = resultSet.getInt("GENERATED_KEY");
                    }
                }
            }
        }
        return id;
    }

    public List<Item> obterTodos() throws SQLException {
        List<Item> listaItem = new ArrayList<>();
        String query = "SELECT * FROM itens_tbl WHERE data_exclusao IS NULL;";

        try (Connection connection = this.conector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Item item = new Item();

                        item.setId(resultSet.getInt("id_itens"));
                        item.setNome(resultSet.getString("nome"));
                        item.setDescricao(resultSet.getString("descricao"));
                        item.setQuantidade(resultSet.getInt("quantidade"));
                        item.setPreco(resultSet.getBigDecimal("preco"));
                        item.setDataCriacao(resultSet.getTimestamp("data_criacao"));
                        item.setDataAlteracao(resultSet.getTimestamp("data_alteracao"));
                        item.setDataExclusao(resultSet.getTimestamp("data_exclusao"));

                        listaItem.add(item);
                    }
                }
            }
        }
        return listaItem;
    }
}
