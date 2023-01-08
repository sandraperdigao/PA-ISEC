package pt.isec.pa.apoio_poe.src.model.data.flags;

import java.io.Serializable;

public class Flags implements Serializable {
    public boolean isClosedStateDataPhase1;
    public boolean isClosedStateDataPhase2;
    public boolean isClosedStateDataPhase3;
    public boolean isClosedStateDataPhase4;

    public Flags(){
        this.isClosedStateDataPhase1 = false;
        this.isClosedStateDataPhase2 = false;
        this.isClosedStateDataPhase3 = false;
        this.isClosedStateDataPhase4 = false;
    }

    public void setClosedPhase1(){this.isClosedStateDataPhase1 = true;}
    public void setClosedPhase2(){this.isClosedStateDataPhase2 = true;}
    public void setClosedPhase3(){this.isClosedStateDataPhase3 = true;}
    public void setClosedPhase4(){this.isClosedStateDataPhase4 = true;}

    public boolean isClosedPhase1(){return this.isClosedStateDataPhase1;}
    public boolean isClosedPhase2(){return this.isClosedStateDataPhase2;}
    public boolean isClosedPhase3(){return this.isClosedStateDataPhase3;}
    public boolean isClosedPhase4(){return this.isClosedStateDataPhase4;}
}
