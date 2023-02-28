package cloud.aprender.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor 
@JsonIgnoreProperties
public class Pokemon {
    private int id;
    private String name;
    private String url;
}
