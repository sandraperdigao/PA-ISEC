package pt.isec.pa.apoio_poe.src.model.data.applications;

import pt.isec.pa.apoio_poe.src.log.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Long.parseLong;

public class Application  implements Serializable, Cloneable, Comparable<Application> {
    protected final Long nrStudent;
    protected final ArrayList<String> applicationsList;

    public Application(List<String> args) {
        this.nrStudent = parseLong(args.get(0));
        this.applicationsList = new ArrayList<>();

        for (int index = 1; index < args.size(); index++) {
            this.applicationsList.add(args.get(index));
        }
    }

    Application(Application toCopy){
        this.nrStudent = toCopy.getNrStudent();
        this.applicationsList = new ArrayList<>();
        this.applicationsList.addAll(toCopy.getApplicationsList());
    }

    public Long getNrStudent() {
        return nrStudent;
    }

    public ArrayList<String> getApplicationsList() {
        return applicationsList;
    }

    public static boolean isListValidAsApplication(List<String> args) {
        if(args.size() >= 2)
            return true;

        Logger.appendMessage("A linha " + args + " nao está correctamente formatada.");
        return false;
    }

    public String getPreferenceOrder(String code) {
        int preference = 1;
        for(String currentApplication : applicationsList) {
            if(currentApplication.equals(code))
                return Integer.toString(preference);
            preference++;
        }

        return Integer.toString(preference);
    }

    @Override
    public String toString() {
        return String.format("-> %s,%s", nrStudent, applicationsList.toString());
    }
    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;

        Application application = (Application) o;
        return Objects.equals(this.getNrStudent(), application.getNrStudent());
    }
    @Override
    public int hashCode(){
        return this.getNrStudent().intValue();
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    @Override
    public int compareTo(Application o){
        return super.hashCode() - o.hashCode();
    }

}
