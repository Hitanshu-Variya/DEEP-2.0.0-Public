package in.ac.daiict.deep.util.allocation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InstituteRequirement {
    private String program;
    private int semester;
    private String category;
    private int courseCnt;
}
