package xyz.tilmais.brincandojwt.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.tilmais.brincandojwt.database.entity.Item;
import xyz.tilmais.brincandojwt.database.repository.ItemRepository;
import xyz.tilmais.brincandojwt.models.ItemModel;

import java.sql.SQLException;
import java.util.List;

@Service
public class ItemService {

    private Logger logger = LogManager.getLogger(this.getClass());
    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Integer cadastrarItem(ItemModel itemModel) throws SQLException {

        logger.info("Item para ser salvo: " + itemModel);
        logger.info("Salvando Item...");
        Integer id = this.itemRepository.salvar(itemModel);
        logger.info("Item salvo! :)");

        return id;
    }

    public List<Item> obterTodos() throws SQLException {
        return this.itemRepository.obterTodos();
    }
}
