package pt.isec.pa.apoio_poe.src.model.data.applications;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.data.student.Student;
import pt.isec.pa.apoio_poe.src.utils.FileUtils;

import java.io.*;
import java.util.*;

import static pt.isec.pa.apoio_poe.src.utils.FileUtils.parseStringIntoList;

public class ApplicationsData implements Serializable, Cloneable {
    final private Map<Long, Application> applications;

    public ApplicationsData(){
        applications = new HashMap<>();
    }

    private ApplicationsData(Map<Long, Application> toCopy){
        this.applications = toCopy;
    }

    private Map<Long, Application> getApplications() {
        return this.applications;
    }

    public void insertApplication(Application application, List<ProposalAdapter> proposals, List<Student> students){
        if(isValidInsertApplication(application, proposals, students)){
            applications.put(application.getNrStudent(), application);
        }
    }

    public void insertApplication(String application, List<ProposalAdapter> proposals, List<Student> students) throws Exception {

        List<String> temp = parseStringIntoList(application);

        if(!Application.isListValidAsApplication(temp))
            throw new Exception("A candidatura nao e valida.");

        Application applicationToInsert = new Application(temp);

        if(isValidInsertApplication(applicationToInsert, proposals, students)){
            insertApplication(applicationToInsert, proposals, students);
        }
        else
            throw new Exception("A candidatura nao e valida.");
    }

    public List<Application> toList() {
        List<Application> list = new ArrayList<>(applications.values());
        list.sort(Collections.reverseOrder());

        return list;
    }

    public void insertApplicationsFromFile(String filename, List<ProposalAdapter> proposals, List<Student> students) throws Exception {
        File file = new File(filename);
        List<List<String>> list = FileUtils.importCSVFileAsListOfList(file);
        ApplicationsData tempInsert = new ApplicationsData();

        for(List<String> listApplication : list){
            if(!Application.isListValidAsApplication(listApplication)){
                continue;
            }

            Application temp = new Application(listApplication);

            if(isValidInsertApplication(temp, proposals, students) && tempInsert.isValidInsertApplication(temp, proposals, students))
                tempInsert.insertApplication(temp, proposals, students);
        }

        try{
            applications.putAll(tempInsert.applications);
        }
        catch (Exception ex){
            Logger.logError("Erro a importar ficheiro CSV: " + ex);
            throw new Exception("Erro a importar ficheiro CSV!");
        }

    }

    public void removeApplication(Application temp) throws Exception {
        if(isValidRemoveApplication(temp)){
            applications.remove(temp.getNrStudent(), temp);
        }
        else
            throw new Exception("A candidatura nao pode ser removida.");
    }

    private boolean isValidInsertApplication(Application newApplication, List<ProposalAdapter> pdata, List<Student> sdata){
        if(applications.containsKey(newApplication.getNrStudent())
                || pdata.stream().anyMatch(s -> Objects.equals(s.getNrStudent(), newApplication.getNrStudent()))
                || sdata.stream().noneMatch(s -> Objects.equals(s.getNrStudent(), newApplication.getNrStudent()))){
            Logger.appendMessage("A candidatura " + newApplication + " tem um numero de estudante invalido!" );
            return false;
        }

        for (String currentCode : newApplication.getApplicationsList()){
            boolean isCodePresent = false;
            boolean isProposalTaken = false;

            for(ProposalAdapter temp : pdata) {
                if(temp.getCode().equals(currentCode)) {
                    isCodePresent = true;
                    if(temp.getNrStudent() != null) {
                        isProposalTaken = true;
                    }
                    break;
                }
            }
            if(!isCodePresent || isProposalTaken){
                Logger.appendMessage(String.format("A candidatura %s tem um codigo " +
                        "de candidatura invalido! (%s)",newApplication, currentCode));
                return false;
            }
        }
        return true;
    }

    private boolean isValidRemoveApplication(Application temp) {
        if(!applications.containsKey(temp.getNrStudent())){
            Logger.appendMessage("A proposta " + temp + " nao existe ou tem um numero de estudante errado!" );
            return false;
        }
        return true;
    }

    public Application findApplicationByKey(Long applicationKey){
        if(this.applications.containsKey(applicationKey))
            return this.applications.get(applicationKey);

        return null;
    }

    public Set<Long> getAppliedStudentsNrs() {
        return applications.keySet();
    }

    public Map<Long, Application> getApplicationsCopy(){
        ApplicationsData copy = (ApplicationsData) this.clone();
        return copy.getApplications();
    }

    public void exportApplicationsToFile(String filename) throws Exception {
        try(Writer writer = new FileWriter(filename)){
            for (Map.Entry<Long, Application> entry : this.getApplicationsCopy().entrySet()){
                writer.append(entry.getValue().toString())
                        .append("\r\n");
            }
        } catch (IOException ex) {
            Logger.logError("Erro a exportar ficheiro CSV: " + ex);
            throw new Exception(ex);
        }
    }

    @Override
    public String toString(){
        if(this.toList().isEmpty())
            return null;

        StringBuilder sb = new StringBuilder();
        for (Object o : this.toList())
            sb.append("\n\t-").append(o.toString());

        return sb.toString();
    }

    @Override
    public Object clone() {
        HashMap<Long, Application> newHashmap = new HashMap<>();
        for (Map.Entry<Long, Application> entry : this.getApplications().entrySet()) {
            newHashmap.put(entry.getKey(), new Application(entry.getValue()));
        }
        return new ApplicationsData(newHashmap);
    }


}
