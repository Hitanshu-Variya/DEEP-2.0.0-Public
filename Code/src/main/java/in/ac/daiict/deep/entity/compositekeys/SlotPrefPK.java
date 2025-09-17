package in.ac.daiict.deep.entity.compositekeys;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SlotPrefPK {
    private String sid;
    private int pref;
}
