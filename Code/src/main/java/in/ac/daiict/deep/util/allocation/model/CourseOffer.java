package in.ac.daiict.deep.util.allocation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CourseOffer {
    private String program;
    private String cid;
    private int semester;
    private String category;
    private int seats;
}
