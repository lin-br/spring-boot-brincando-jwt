package xyz.tilmais.brincandojwt.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.tilmais.brincandojwt.database.dao.ItensDAO;
import xyz.tilmais.brincandojwt.database.entity.Item;
import xyz.tilmais.brincandojwt.models.ItemModel;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ItemRepository {

    private ItensDAO itensDAO;

    @Autowired
    public ItemRepository(ItensDAO itensDAO) {
        this.itensDAO = itensDAO;
    }

    public Integer salvar(ItemModel itemModel) throws SQLException {
        return this.itensDAO.inserirItem(itemModel);
    }

    public List<Item> obterTodos() throws SQLException {
        return this.itensDAO.obterTodos();
    }
}
