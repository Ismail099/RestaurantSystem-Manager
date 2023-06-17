package org.ibo.manager.api.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.ibo.manager.api.model.ItemDTO;
import org.ibo.manager.api.model.TypeDTO;
import org.ibo.manager.models.Item;
import org.ibo.manager.models.Type;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-17T16:43:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class ItemMapperImpl implements ItemMapper {

    @Override
    public ItemDTO itemToItemDTO(Item item) {
        if ( item == null ) {
            return null;
        }

        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setId( item.getId() );
        itemDTO.setName( item.getName() );
        if ( item.getPrice() != null ) {
            itemDTO.setPrice( item.getPrice() );
        }
        itemDTO.setCalories( item.getCalories() );
        itemDTO.setFat( item.getFat() );
        itemDTO.setProtein( item.getProtein() );
        itemDTO.setCarbohydrates( item.getCarbohydrates() );
        itemDTO.setImagePath( item.getImagePath() );
        itemDTO.setDescription( item.getDescription() );
        itemDTO.setTypes( typeSetToTypeDTOSet( item.getTypes() ) );

        return itemDTO;
    }

    protected TypeDTO typeToTypeDTO(Type type) {
        if ( type == null ) {
            return null;
        }

        TypeDTO typeDTO = new TypeDTO();

        typeDTO.setName( type.getName() );

        return typeDTO;
    }

    protected Set<TypeDTO> typeSetToTypeDTOSet(Set<Type> set) {
        if ( set == null ) {
            return null;
        }

        Set<TypeDTO> set1 = new LinkedHashSet<TypeDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Type type : set ) {
            set1.add( typeToTypeDTO( type ) );
        }

        return set1;
    }
}
