package org.ibo.manager.api.model;

import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemDTO implements Serializable {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    private double price;
    private double calories;
    private double fat;
    private double protein;
    private double carbohydrates;
    private String imagePath;
    private String description;

    private Set<TypeDTO> types = new HashSet<>();
}
