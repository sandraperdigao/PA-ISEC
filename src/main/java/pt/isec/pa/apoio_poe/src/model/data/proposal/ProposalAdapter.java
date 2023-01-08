package pt.isec.pa.apoio_poe.src.model.data.proposal;

import java.io.Serializable;
import java.util.Objects;

public abstract class ProposalAdapter implements Serializable, Cloneable, Comparable<ProposalAdapter> {

    protected final String type;
    protected final String code;
    protected final String title;
    protected String abbField;
    private Long nrStudent;

    //Estagio / Projeto
    protected ProposalAdapter(String type, String code, String abbField, String title){
        this.type = type;
        this.code = code;
        this.abbField = abbField;
        this.title = title;
        this.nrStudent = null;
    }

    //AutoProposta
    protected ProposalAdapter(String type, String code, String title){
        this.type = type;
        this.code = code;
        this.title = title;
        this.abbField = "";
        this.nrStudent = null;
    }

    public final String getType(){return type;}
    public final String getCode(){return code;}
    public final String getTitle(){return title;}
    public final String getAbbField(){return abbField;}
    public String getEntity(){return null;}
    public final Long getNrStudent(){return nrStudent;}
    public String getTeacherEmail() {return null;}
    public void setNrStudent(Long nrStudent){
        this.nrStudent = nrStudent;
    }
    public final boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;

        ProposalAdapter proposalTemp = (ProposalAdapter) o;
        return Objects.equals(this.getCode(), proposalTemp.getCode());
    }
    public final int hashCode(){
        return this.code.hashCode();
    }
    public final int compareTo(ProposalAdapter o){
        return super.hashCode() - o.hashCode();
    }
    public final Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
