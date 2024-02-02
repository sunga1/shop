package sun0aaa.s.shop.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public enum ItemCategory {
    CAKE, TARTE , MADELEINE, FINANCIER, MACARON, SCONE, MUFFIN;

    public static ItemCategory of(String category){
        if(category.equalsIgnoreCase("cake")){
            return CAKE;
        } else if(category.equalsIgnoreCase("tarte")){
            return TARTE;
        } else if(category.equalsIgnoreCase("madeleine")){
            return MADELEINE;
        } else if(category.equalsIgnoreCase("financier")){
            return FINANCIER;
        } else if(category.equalsIgnoreCase("macaron")){
            return MACARON;
        } else if(category.equalsIgnoreCase("scone")){
            return SCONE;
        } else if(category.equalsIgnoreCase("muffin")){
            return MUFFIN;
        }
        else return null;
    }
}
