package org.ibo.manager.services;

import org.ibo.manager.repositories.Database;
import org.ibo.manager.transactions.AddItemsToReservationTransaction;
import org.springframework.stereotype.Service;
import org.ibo.manager.api.mapper.ItemMapper;
import org.ibo.manager.api.model.ItemDTO;
import org.ibo.manager.models.Item;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemMapper itemMapper;

    public ItemServiceImpl( ItemMapper itemMapper ) {
        this.itemMapper = itemMapper;
    }

    @Override
    public List<ItemDTO> getItems() {
        return Database.getItems().stream().map( itemMapper::itemToItemDTO )
                .collect( Collectors.toList() );
    }

    @Override
    public boolean allItemsInMenu( List<Item> orderedItems ) {
        for ( Item item : orderedItems ) {
            Item itemFromDatabase = Database.getItemById( item.getId() );
            if ( !item.deepEquals( itemFromDatabase ) )
                return false;
        }

        return true;
    }

    @Override
    public void addItemsToOrder( Long tableId, List<Item> orderedItems ) throws IllegalArgumentException {
        if ( !allItemsInMenu( orderedItems ) )
            throw new IllegalArgumentException( "One or more of the provided items are not served by the restaurant." );

        AddItemsToReservationTransaction serviceTransaction = new AddItemsToReservationTransaction( tableId );
        for ( Item item : orderedItems )
            serviceTransaction.addItem( item.getId() );
        serviceTransaction.execute();
    }
}
