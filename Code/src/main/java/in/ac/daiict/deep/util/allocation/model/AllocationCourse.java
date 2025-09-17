package in.ac.daiict.deep.util.allocation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AllocationCourse {
    private String cid;
    private String name;
    private int credits;
    private String slot; // Why String and not int?
}
