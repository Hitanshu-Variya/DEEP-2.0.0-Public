package in.ac.daiict.deep.entity.compositekeys;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CoursePrefPK {
    private String sid;
    private String slot;
    private int pref;
}
