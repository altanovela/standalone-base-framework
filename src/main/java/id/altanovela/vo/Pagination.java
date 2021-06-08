package id.altanovela.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pagination<T> {

    private Integer draw;
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private List<T> data;
    
    public Pagination(Integer draw, Integer recordsTotal, Integer recordsFiltered, List<T> data){
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }
    
}
