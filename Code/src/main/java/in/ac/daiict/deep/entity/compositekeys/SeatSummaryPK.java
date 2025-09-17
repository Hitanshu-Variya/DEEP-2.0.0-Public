package in.ac.daiict.deep.entity.compositekeys;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SeatSummaryPK {
    private String cid;
    private String program;
    private int semester;
}
