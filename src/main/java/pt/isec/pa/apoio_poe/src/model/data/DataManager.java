package pt.isec.pa.apoio_poe.src.model.data;

import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.data.applications.Application;
import pt.isec.pa.apoio_poe.src.model.data.applications.ApplicationsData;
import pt.isec.pa.apoio_poe.src.model.data.attribution.ProposalAttributionManager;
import pt.isec.pa.apoio_poe.src.model.data.attribution.SupervisorAttributionManager;
import pt.isec.pa.apoio_poe.src.model.data.flags.Flags;
import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalsData;
import pt.isec.pa.apoio_poe.src.model.data.student.Student;
import pt.isec.pa.apoio_poe.src.model.data.student.StudentsData;
import pt.isec.pa.apoio_poe.src.model.data.teacher.Teacher;
import pt.isec.pa.apoio_poe.src.model.data.teacher.TeachersData;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;
import pt.isec.pa.apoio_poe.src.utils.FileUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

public class DataManager implements Serializable {

    public final StateEnum currentState;
    private final StudentsData students;
    private final ProposalsData proposals;
    private final TeachersData teachers;
    private final ApplicationsData applications;
    private final ProposalAttributionManager proposalAttributions;
    private final SupervisorAttributionManager supervisorAttributions;
    private final Flags flags;
    private final ConflictData conflictData;

    public DataManager(){
        currentState = StateEnum.INITIAL;
        students = new StudentsData();
        proposals = new ProposalsData();
        teachers = new TeachersData();
        applications = new ApplicationsData();
        proposalAttributions = new ProposalAttributionManager();
        supervisorAttributions = new SupervisorAttributionManager();
        flags = new Flags();
        conflictData = new ConflictData();
    }

    // FLAGS
    public boolean isClosedPhase1(){return flags.isClosedPhase1();}
    public void setClosedPhase1(){flags.setClosedPhase1();}
    public boolean isClosedPhase2(){return flags.isClosedPhase2();}
    public void setClosedPhase2(){flags.setClosedPhase2();}
    public boolean isClosedPhase3(){return flags.isClosedPhase3();}
    public void setClosedPhase3(){flags.setClosedPhase3();}
    public boolean isClosedPhase4(){return flags.isClosedPhase4();}
    public void setClosedPhase4(){flags.setClosedPhase4();}

    // STUDENTS
    public void insertStudentsFromFile(String filename) throws Exception {students.insertStudentsFromFile(filename, teachers.getTeachersCopy().values());}
    public Student findStudent(Long key) {return students.findStudent(key);}
    public String getStudentsAsString(){return students.toString();}
    public int getNrStudents(String type){return students.countStudents(type);}
    public void exportStudentsToFile(String filename) throws Exception {students.exportStudentsToFile(filename);}

    // TEACHERS
    public void insertTeachersFromFile(String filename) throws Exception {teachers.insertTeachersFromFile(filename);}
    public String getTeachersAsString(){return teachers.toString();}
    public void exportTeachersToFile(String filename) throws Exception {teachers.exportTeachersToFile(filename);}

    // PROPOSALS
    public void insertProposalsFromFile(String filename) throws Exception {proposals.insertProposalsFromFile(filename, teachers.toList(), students.toList());}
    public String getProposalsAsString(){return proposals.toString();}
    public int getNrProposals(String area){return proposals.getNrProposals(area);}
    public void exportProposalsToFile(String filename) throws Exception {proposals.exportProposalsToFile(filename);}

    // APPLICATIONS
    public void insertApplicationsFromFile(String filename) throws Exception {applications.insertApplicationsFromFile(filename, proposals.toList(), students.toList());}
    public String getApplicationsAsString(){return applications.toString();}
    public Set<Long> getAppliedStudentsNrs() {return applications.getAppliedStudentsNrs();}
    public void exportApplicationsToFile(String filename) throws Exception {applications.exportApplicationsToFile(filename);}

    // PROPOSAL ATTRIBUTIONS
    public Map<ProposalAdapter, Student> getAttributions() {return proposalAttributions.getAttributions();}
    public void insertAttribution(ProposalAdapter prop, Student student) throws Exception {proposalAttributions.insertAttribution(prop, student);}
    public void exportAttributionsToFile(String filename) {

        String separator = ",";
        List<Student> tempStudentList = new ArrayList<>(students.toList());
        StringBuilder sb = new StringBuilder("ALUNO | OPCOES DE CANDIDATURA | PROPOSTA ATRIBUIDA | Nº DA OPCAO ATRIBUIDA");

        for(Student currentStudent : tempStudentList){
            String currentLine = "\n" + currentStudent.toString() + separator;
            sb.append(currentLine);

            Application tempApplication = applications.findApplicationByKey(currentStudent.getNrStudent());
            ProposalAdapter attributedProposal = proposalAttributions.findProposalAttribution(currentStudent);

            if(tempApplication!=null)
                sb.append(tempApplication.getApplicationsList()).append(separator);
            else
                sb.append("Sem candidatura apresentada").append(separator);

            if(proposalAttributions.isStudentAttributed(currentStudent))
                sb.append(attributedProposal).append(separator);
            else
                sb.append("Sem proposta atribuida").append(separator);

            if(tempApplication!=null)
                if(tempApplication.getApplicationsList().contains(attributedProposal.getCode()))
                    sb.append("Ordem de preferencia ").append(tempApplication.getPreferenceOrder(attributedProposal.getCode()));
                else
                    sb.append("Nao lhe foi atribuida uma das opcoes escolhida");
            else
                sb.append("Sem candidatura apresentada");

        }
        FileUtils.exportStringToCSV(filename, sb.toString());
    }

    // SUPERVISOR ATTRIBUTIONS
    public String listSupervisorAttributions() { return supervisorAttributions.toString();}
    public void exportSupervisorAttributionsToFile(String filename) {

        String separator = ",";
        List<Student> tempStudentList = new ArrayList<>(students.toList());
        StringBuilder sb = new StringBuilder("ALUNO | OPCOES DE CANDIDATURA | PROPOSTA ATRIBUIDA | Nº DA OPCAO ATRIBUIDA");

        for(Student currentStudent : tempStudentList){
            String currentLine = "\n" + currentStudent.toString() + separator;
            sb.append(currentLine);

            Application tempApplication = applications.findApplicationByKey(currentStudent.getNrStudent());
            ProposalAdapter attributedProposal = proposalAttributions.findProposalAttribution(currentStudent);

            if(tempApplication!=null)
                sb.append(tempApplication.getApplicationsList().toString()).append(separator);
            else
                sb.append("Sem candidatura apresentada").append(separator);

            if(proposalAttributions.isStudentAttributed(currentStudent))
                sb.append(attributedProposal).append(separator);
            else
                sb.append("Sem proposta atribuida").append(separator);

            if(tempApplication!=null)
                if(tempApplication.getApplicationsList().contains(attributedProposal.getCode()))
                    sb.append("Ordem de preferencia ").append(tempApplication.getPreferenceOrder(attributedProposal.getCode())).append(separator);
                else
                    sb.append("Nao lhe foi atribuida uma das opcoes escolhida").append(separator);
            else
                sb.append("Sem candidatura apresentada").append(separator);

            if(supervisorAttributions.isProposalAttributed(attributedProposal))
                sb.append(supervisorAttributions.findProposalAttribution(attributedProposal).toString());
            else
                sb.append("Sem orientador atribuido");

        }
        FileUtils.exportStringToCSV(filename, sb.toString());
    }

    // COLLECT INFO MULTIPLE SOURCES
    public String listStudentsAuto() {
        // No caso de nao existirem auto-propostas, retornar
        if(proposals.getProposals().values().stream().noneMatch(s -> s.getType().equals("T3")))
            return null;

        // Obter nº dos alunos que apresentaram auto-proposta, atraves das propostas
        List<Long> alunos = new ArrayList<>();
        for(ProposalAdapter proposta : proposals.getProposals().values()){
            if (proposta.getType().equals("T3"))
                alunos.add(proposta.getNrStudent());
        }

        // Obter os alunos que apresentaram auto-proposta, atraves do seu numero de aluno
        StringBuilder sb = new StringBuilder();
        for(Long index : alunos)
            sb.append(this.findStudent(index).toString()).append("\n");

        return sb.toString();
    }
    public String listStudentsApplied() {
        // No caso de nao existirem alunos com candidatura, retornar
        if(applications.getApplicationsCopy().values().isEmpty())
            return null;

        // Obter nº dos alunos que apresentaram candidatura, atraves das candidaturas
        List<Long> alunosComCandidatura = new ArrayList<>();
        for(Application candidatura : applications.getApplicationsCopy().values()){
            alunosComCandidatura.add(candidatura.getNrStudent());
        }

        // Obter os alunos que apresentaram auto-proposta, atraves do seu numero de aluno
        List<Long> alunosUnicos = alunosComCandidatura.stream().distinct().toList();
        StringBuilder sb = new StringBuilder();
        for(Long index : alunosUnicos) {
            sb.append(this.findStudent(index).toString()).append("\n");
        }

        return sb.toString();
    }
    public String listStudentsNotApplied() {
        // No caso só existirem alunos com candidatura, retornar
        if(applications.getApplicationsCopy().values().size() == students.getStudents().values().size())
            return null;

        // Reunir numero de aluno de todos os alunos + numero de todos os alunos com candidaturas
        List<Long> alunosTodos = new ArrayList<>();
        List<Long> alunosComCandidatura = new ArrayList<>();
        for(Student aluno : students.getStudentsCopy().values())
            alunosTodos.add(aluno.getNrStudent());
        for(Application candidatura : applications.getApplicationsCopy().values())
            alunosComCandidatura.add(candidatura.getNrStudent());

        // Remover duplicados (em teoria nunca é necessario)
        List<Long> alunosTodosUnicos = alunosTodos.stream().distinct().toList();
        List<Long> alunosComCandidaturaUnicos = alunosComCandidatura.stream().distinct().toList();

        // Criar nova lista com todos os alunos e remover os que tem candidatura
        List<Long> alunosSemCandidaturaUnicos = new ArrayList<>(alunosTodosUnicos);
        for(Long index : alunosComCandidaturaUnicos)
            alunosSemCandidaturaUnicos.remove(index);

        StringBuilder sb = new StringBuilder();
        for(Long index : alunosSemCandidaturaUnicos) {
            sb.append(this.findStudent(index).toString());
            sb.append("\n");
        }

        return sb.toString();
    }
    public String listProposalsFilteredPhase2(boolean [] filters) {

        StringBuilder sb = new StringBuilder();
        Set<ProposalAdapter> outputProposalsList = new HashSet<>();
        Set<ProposalAdapter> proposalsWithApplications = new HashSet<>();

        if (filters[0]){
            List<ProposalAdapter> proposalsTempList = proposals.toList();
            proposalsTempList.removeIf(s -> !s.getType().equals("T3"));
            outputProposalsList.addAll(proposalsTempList);
        }

        if (filters[1]){
            List<ProposalAdapter> proposalsTempList = proposals.toList();
            proposalsTempList.removeIf(s -> !s.getType().equals("T2"));
            outputProposalsList.addAll(proposalsTempList);
        }

        if(filters[2]){
            List<Application> applicationList = applications.getApplicationsCopy().values().stream().toList();
            for(Application candidatura : applicationList){
                for(String tempProposta : candidatura.getApplicationsList()){
                    proposalsWithApplications.add(proposals.findProposal(tempProposta));
                }
            }
            outputProposalsList.addAll(proposalsWithApplications);
        }

        if(filters[3]){
            List<ProposalAdapter> proposalsTempList = proposals.toList();
            List<Application> applicationList = applications.getApplicationsCopy().values().stream().toList();
            for(Application candidatura : applicationList){
                for(String tempProposta : candidatura.getApplicationsList()){
                    proposalsWithApplications.add(proposals.findProposal(tempProposta));
                }
            }
            proposalsTempList.removeAll(proposalsWithApplications);
            outputProposalsList.addAll(proposalsTempList);
        }

        for(ProposalAdapter it : outputProposalsList) {
            sb.append(it.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
    public String listProposalsFilteredPhase3and5(boolean [] filters) {
        StringBuilder sb = new StringBuilder();
        Set<ProposalAdapter> outputProposalsList = new HashSet<>();

        if (filters[0]){
            List<ProposalAdapter> proposalsTempList = proposals.toList();
            proposalsTempList.removeIf(s -> !s.getType().equals("T3"));
            outputProposalsList.addAll(proposalsTempList);
        }

        if (filters[1]){
            List<ProposalAdapter> proposalsTempList = proposals.toList();
            proposalsTempList.removeIf(s -> !s.getType().equals("T2"));
            outputProposalsList.addAll(proposalsTempList);
        }

        if (filters[2]){
            List<ProposalAdapter> proposalsTempList = proposals.toList();
            List<ProposalAdapter> proposalAttributionsTempList = proposalAttributions.toList();
            proposalsTempList.removeAll(proposalAttributionsTempList);
            outputProposalsList.addAll(proposalsTempList);
        }

        if (filters[3]){
            List<ProposalAdapter> proposalAttributionsTempList = proposalAttributions.toList();
            outputProposalsList.addAll(proposalAttributionsTempList);
        }

        for(ProposalAdapter it : outputProposalsList) {
            sb.append(it.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
    public String listStudentsAttributed() {

        StringBuilder sb = new StringBuilder();

        for(Student tempStudent : students.getStudents().values()){

            String preference;

            if(proposalAttributions.isStudentAttributed(tempStudent)) {
                for (Map.Entry<ProposalAdapter, Student> entry : proposalAttributions.getAttributions().entrySet()) {
                    ProposalAdapter key = entry.getKey();
                    Student value = entry.getValue();

                    if(value.equals(tempStudent)){
                        if(key.getType().equals("T3")) {
                            sb.append(String.format("%s | proposta %s, como preferencia nº1 (autoproposto) \n", tempStudent, key.getCode()));
                            break;
                        } else if (applications.findApplicationByKey(value.getNrStudent()) != null) {
                            preference = applications.findApplicationByKey(value.getNrStudent()).getPreferenceOrder(key.getCode());
                            sb.append(String.format("%s | proposta %s, como preferencia nº%s \n", tempStudent, key.getCode(), preference));
                            break;
                        }
                        else{
                            sb.append(String.format("%s | proposta %s, atribuída por defeito \n", tempStudent, key.getCode()));
                            break;
                        }
                    }
                }
            }
        }

        return sb.toString();
    }
    public String listStudentsNotAttributed() {

        StringBuilder sb = new StringBuilder();

        for(Student tempStudent : students.getStudents().values()){
            if(!proposalAttributions.getAttributions().containsValue(tempStudent))
                sb.append(tempStudent.toString()).append("\n");
        }

        return sb.toString();
    }
    public String listProposalsNotAttributed() {

        StringBuilder sb = new StringBuilder();

        for(ProposalAdapter tempProposal : proposals.getProposals().values()){
            if(!proposalAttributions.getAttributions().containsKey(tempProposal))
                sb.append(tempProposal.toString()).append("\n");
        }

        return sb.toString();
    }
    public String listStudentsAssignedWithSupervisor(){
        StringBuilder sb = new StringBuilder("Alunos com proposta atribuida e orientator associado:\n");
        for (Map.Entry<ProposalAdapter, Student> entryOuter : proposalAttributions.getAttributions().entrySet()) {
            ProposalAdapter keyOuter = entryOuter.getKey();
            Student valueOuter = entryOuter.getValue();

            for (Map.Entry<ProposalAdapter, Teacher> entryInner : supervisorAttributions.getSupervisorAttributions().entrySet()) {
                ProposalAdapter keyInner = entryInner.getKey();
                Teacher valueInner = entryInner.getValue();

                if(keyOuter.equals(keyInner))
                    sb.append(String.format("Proposta: %s\nAluno: %s\nOrientador: %s\n\n", keyOuter, valueOuter, valueInner));
            }
        }

        return sb.toString();
    }
    public String listStudentsAssignedWithoutSupervisor(){
        if(proposalAttributions.getAttributions().entrySet().isEmpty())
            return null;

        //Alunos com proposta atribuida mas sem orientator associado
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<ProposalAdapter, Student> entryOuter : proposalAttributions.getAttributions().entrySet()) {
            ProposalAdapter keyOuter = entryOuter.getKey();
            Student valueOuter = entryOuter.getValue();

            if(!supervisorAttributions.isProposalAttributed(keyOuter))
                sb.append(String.format("Proposta: %s\nAluno: %s\n\n", keyOuter, valueOuter));

        }

        return sb.toString();
    }
    public String listSupervisorStats() throws CloneNotSupportedException {
        if (teachers.getTeachersCopy().values().isEmpty())
            return null;

        // Número de orientações por docente, em média, mínimo, máximo, e por docente
        StringBuilder sb = new StringBuilder();
        Teacher value;
        int accumulated = 0;

        List<Integer> lista = new ArrayList<>();
        for(Teacher tempTeacher : teachers.getTeachers().values()){
            int count = 0;
            for (Map.Entry<ProposalAdapter, Teacher> entryInner : supervisorAttributions.getSupervisorAttributions().entrySet()) {
                value = entryInner.getValue();

                if(tempTeacher.equals(value))
                    count++;
            }

            sb.append(String.format("Orientador %s nºprojectos: %s\n", tempTeacher.toString(), count));
            lista.add(count);
            accumulated+=count;
        }

        sb.append(String.format("Média: %s\n", (double)accumulated / (double)lista.size()));
        sb.append(String.format("Mínimo: %s\n", Collections.min(lista)));
        sb.append(String.format("Maximo: %s\n", Collections.max(lista)));

        return sb.toString();
    }
    public String listStudentsNotAttributedWithApplications(){
        if (students.getStudents().values().isEmpty())
            return null;

        // Alunos sem proposta atribuida e com opções de candidatura
        StringBuilder sb = new StringBuilder();
        for(Student tempStudent : students.getStudents().values()){
            if(!proposalAttributions.getAttributions().containsValue(tempStudent)){
                for(Map.Entry<Long, Application> entry: applications.getApplicationsCopy().entrySet()){
                    Long key = entry.getKey();
                    Application value = entry.getValue();
                    if(key.equals(tempStudent.getNrStudent())){
                        sb.append(String.format("%s | candidatura %s\n",tempStudent.getNrStudent(), value.toString()));
                        break;
                    }
                }
            }
        }

        return sb.toString();
    }

    // Auto Assign
    public void autoAssignProposalsStudents() throws Exception {

        for(ProposalAdapter proposta : proposals.getProposals().values()){
            String tempType = proposta.getType();
            if (tempType.equals("T2") || tempType.equals("T3")){

                if(proposta.getNrStudent() == null)
                    continue;

                Long tempStudentNR = proposta.getNrStudent();
                String tempProposalNR = proposta.getCode();

                proposalAttributions.insertAttribution(
                    proposals.findProposal(tempProposalNR),
                    students.findStudent(tempStudentNR)
                );
            }
        }
    }
    public List<ProposalAdapter> getUnassignedProposalsList(boolean hasAccess){
        List<ProposalAdapter> propList = new ArrayList<>();

        for(ProposalAdapter tempProposal : proposals.getProposals().values()){
            if(!proposalAttributions.getAttributions().containsKey(tempProposal))
                if(!tempProposal.getType().equals("T1") || hasAccess)
                    propList.add(tempProposal);

        }

        return propList;
    }
    public List<Student> listStudentsEligible(ProposalAdapter proposal) {

        boolean isEstagio = proposal.getType().equals("T1");
        List<Student> eligible = new ArrayList<>();

        for(Student tempStudent : students.getStudents().values()){
            if(!proposalAttributions.getAttributions().containsValue(tempStudent))
                if(isEstagio){
                    if(tempStudent.getHasAccess()){
                        eligible.add(tempStudent);
                    }
                }
                else {
                    eligible.add(tempStudent);
                }
        }

        if(!eligible.isEmpty() && eligible.size() > 1){
            eligible.sort(Collections.reverseOrder());
            double maxScore = Collections.max(eligible).getClassification();
            eligible.removeIf(s -> s.getClassification() != maxScore);
        }

        return eligible;
    }
    public ProposalAdapter getRandomUnassignedProposal(boolean hasAccess){
        List<ProposalAdapter> listProp = getUnassignedProposalsList(hasAccess);
        int indexRandomProposal;

        if (listProp.size() == 0){
            return null;
        }

        indexRandomProposal = (int)(Math.random() * listProp.size());

        return listProp.get(indexRandomProposal);
    }
    public void autoAssignProposalsTeachers() {

        for(ProposalAdapter proposta : proposals.getProposals().values()){
            String tempType = proposta.getType();
            if (tempType.equals("T2")){

                String tempTeacherEmail = proposta.getTeacherEmail();
                String tempProposalNR = proposta.getCode();

                supervisorAttributions.insertSupervisorAttribution(
                    proposals.findProposal(tempProposalNR),
                    teachers.findTeacher(tempTeacherEmail)
                );
            }
        }
    }
    public boolean autoAttributionStudent() throws Exception {

        ProposalAdapter prop = getFirstProposalHighestScoreStudent();

        if (prop == null) {
            throw new Exception("Não existem mais propostas para atribuir.");
        } else {
            List<Student> listEligible = listStudentsEligible(prop);
            if(listEligible.isEmpty()){
                throw new Exception("Nao existem alunos elegiveis para a proposta.");
            } else if (listEligible.size() == 1){
                insertAttribution(prop, listEligible.get(0));
                Logger.log("Proposal was attributed: " + prop + " -> " + listEligible.get(0));
            } else{
                conflictData.setProposal(prop);
                conflictData.setStudentList(listEligible);
                return true;
            }
        }
        return false;
    }
    private ProposalAdapter getFirstProposalHighestScoreStudent() {
        List<Student> listSorted = new ArrayList<>(students.getStudents().values());
        listSorted.sort(Collections.reverseOrder());

        for(Student tempStudent : listSorted) {
            if (!proposalAttributions.getAttributions().containsValue(tempStudent)) {
                Application tempApplication = applications.findApplicationByKey(tempStudent.getNrStudent());
                if(tempApplication != null){
                    for (String tempCode : tempApplication.getApplicationsList()) {
                        ProposalAdapter tempProposal = proposals.findProposal(tempCode);
                        if (!proposalAttributions.isProposalAttributed(tempProposal) && (!tempProposal.getType().equals("T1") || tempStudent.getHasAccess())) {
                            return tempProposal;
                        }
                    }
                }
                return getRandomUnassignedProposal(tempStudent.getHasAccess());
            }
        }

        return null;
    }

    // RESOLVER CONFLITO ATRIBUIÇÃO AUTOMATICA
    public void resolveConflict(int option) throws Exception {
        insertAttribution(conflictData.getProposal(), conflictData.getStudentList().get(option-1));
        conflictData.clear();
    }
    public ProposalAdapter getConflictProposal() {
        return conflictData.getProposal();
    }
    public String[] getConflictStudents() {
        List<String> list = new ArrayList<>();
        for(Student tempStudent : conflictData.getStudentList()){
            Application application = applications.findApplicationByKey(tempStudent.getNrStudent());
            if(application == null)
                list.add(tempStudent + " | Não apresentou candidatura" );
            else
                list.add(tempStudent + " | "+ application);
        }

        String [] arr = new String[conflictData.getStudentList().size()];
        arr = list.toArray(arr);
        return arr;
    }

    //Listagem UI
    public List<String> getList() throws Exception{
        List<String> list = new ArrayList<>();

        try{
            list.add("Número de estudantes:");
            list.add(String.valueOf(students.getStudentsCopy().size()));
            list.add("Número de docentes:");
            list.add(String.valueOf(teachers.getTeachersCopy().size()));
            list.add("Número de propostas:");
            list.add(String.valueOf(proposals.getProposalsCopy().size()));
            list.add("Número de alunos com candidatura:");
            if(applications.getApplicationsCopy()==null)
                list.add("0");
            else
                list.add(String.valueOf(applications.getApplicationsCopy().size()));
            list.add("Número de alunos com proposta atribuída:");
            list.add(String.valueOf(proposalAttributions.getAttributions().size()));
            list.add("Número de docentes com proposta atribuída:");
            list.add(String.valueOf(supervisorAttributions.getSupervisorAttributions().size()));
        }
        catch (Exception ex){
            Logger.logError("Não foi possível obter os valores da lista: " + ex);
            throw new Exception("Não foi possível obter os valores da lista: " + ex);
        }

        return list;
    }

    //Fase5UI Graficos
    public int getNumberProposalAttributionPerAbbField(String abbField){

        int result=0;
        if(proposalAttributions.getAttributions().isEmpty())
            return result;

        for (Map.Entry<ProposalAdapter, Student> entry : proposalAttributions.getAttributions().entrySet()) {
            if(entry.getValue().getAbbBranch().equals(abbField))
                result++;
        }
        return result;
    }
    public int getNumberProposalsAvailable(){
        return proposals.getProposalsCopy().size()-getNumberProposalsAttributed();
    }
    public int getNumberProposalsAttributed(){
        return proposalAttributions.getAttributions().size();
    }
    public LinkedHashMap<String, Integer> getTeachersOrderedByNumberOfProposalsAttributed() throws CloneNotSupportedException {
        if (supervisorAttributions.getSupervisorAttributions().isEmpty())
            return null;

        //obter um mapa não ordenado Numero de Atribuicoes - Email do Docente
        Teacher value;
        Map <String, Integer> unsortedMap = new HashMap<>();
        for(Teacher tempTeacher : teachers.getTeachersCopy().values()){
            int count = 0;
            for (Map.Entry<ProposalAdapter, Teacher> entryInner : supervisorAttributions.getSupervisorAttributions().entrySet()) {
                value = entryInner.getValue();

                if(tempTeacher.equals(value))
                    count++;
            }
            unsortedMap.put(tempTeacher.getEmail(),count);
        }

        return unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(/* Optional: Comparator.reverseOrder() */))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
    public LinkedHashMap<String, Integer> getCompaniesOrderedByNumberOfInternshipsAttributed() {
        if (proposalAttributions.getAttributions().isEmpty())
            return null;

        Map <String, Integer> unsortedMap = new HashMap<>();
        for (Map.Entry<ProposalAdapter, Student> entry : proposalAttributions.getAttributions().entrySet()) {
            if(entry.getKey().getType().equals("T1")){
                //if key do not exists, put 1 as value
                //otherwise sum 1 to the value linked to key
                unsortedMap.merge(entry.getKey().getEntity(),1,Integer::sum);
            }
        }

        //ordenar o mapa

        return unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(/* Optional: Comparator.reverseOrder() */))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    // INSERCAO, EDIÇAO, REMOÇAO, E REMOCAO TOTAL
    public void manualInsertStudent(String readString) throws Exception {students.insertStudent(readString, teachers.getTeachersCopy().values());}
    public void manualUpdateStudent(String readString) {throw new UnsupportedOperationException("fdsfs");}
    public void manualRemoveStudent(String readString) throws Exception {
        Student tempStudent = students.findStudent(parseLong(readString));

        if(tempStudent != null && isValidRemoveStudent(tempStudent)){
            proposals.removeStudentProposals(tempStudent.getNrStudent());
            students.removeStudent(tempStudent);
        }
        else
            throw new Exception("O estudante " + tempStudent + " tem propostas associadas já atribuídas ou já foi designado como orientando.");
    }
    private boolean isValidRemoveStudent(Student tempStudent) {

        if(proposals.studentHasProposals(tempStudent.getNrStudent()))
            for(ProposalAdapter tempProposal : proposals.getProposalsCopy().values()){
                if(tempProposal.getNrStudent() == null)
                    continue;

                if(tempProposal.getNrStudent().equals(tempStudent.getNrStudent()) && proposalAttributions.isProposalAttributed(tempProposal))
                    return false;
            }

        return !proposalAttributions.isStudentAttributed(tempStudent);
    }
    public void removeAllStudents() throws Exception {
        for(Student temp : students.getStudentsCopy().values()){
            if(isValidRemoveStudent(temp))
                students.removeStudent(temp);
            else
                Logger.appendMessage("O estudante" + temp.getName() + " não pôde ser removido.");
        }
    }

    public void manualInsertProposal(String readString) throws Exception {proposals.insertProposal(readString, teachers.toList(), students.toList());}
    public void manualUpdateProposal(String readString) {throw new UnsupportedOperationException("fdsfs");}
    public void manualRemoveProposal(String readString) throws Exception {
        ProposalAdapter tempProposal = proposals.findProposal(readString);

        if(tempProposal != null && isValidRemoveProposal(tempProposal)){
            proposals.removeProposal(tempProposal);
        }
        else
            throw new Exception("A proposta " + tempProposal + " nao existe ou já foi atribuída a um aluno ou orientador.");

    }
    private boolean isValidRemoveProposal(ProposalAdapter temp) {

        if(temp.getTeacherEmail() != null)  // Se tem orientador associado nao pode ser removida
            return false;
        if(temp.getNrStudent() != null) // Se tem aluno associado nao pode ser removida
            return false;

        return !proposalAttributions.isProposalAttributed(temp) && !supervisorAttributions.isProposalAttributed(temp) ; // Se já tiver sido atribuida a um aluno ou professor nao pode ser atribuida
    }
    public void removeAllProposals() throws Exception {
        for(ProposalAdapter temp : proposals.getProposalsCopy().values()){
            if(isValidRemoveProposal(temp))
                proposals.removeProposal(temp);
            else
                Logger.appendMessage("A proposta" + temp + " não pôde ser removida.");
        }
    }

    public void manualInsertTeacher(String readString) throws Exception {teachers.insertTeacher(readString);}
    public void manualUpdateTeacher(String readString) {throw new UnsupportedOperationException("fdsfs");}
    public void manualRemoveTeacher(String readString) throws Exception {
        Teacher tempTeacher = teachers.findTeacher(readString);

        if(tempTeacher != null && isValidRemoveTeacher(tempTeacher)){
            proposals.removeTeacherProposals(tempTeacher.getEmail());
            teachers.removeTeacher(tempTeacher);
        }
        else
            throw new Exception("O professor " + tempTeacher + " tem propostas associadas já atribuídas ou já foi designado como orientador.");
    }
    private boolean isValidRemoveTeacher(Teacher tempTeacher) {

        if(proposals.teacherHasProposals(tempTeacher.getEmail()))
            for(ProposalAdapter tempProposal : proposals.getProposalsCopy().values()){
                if(tempProposal.getTeacherEmail() == null)
                    continue;

                if(tempProposal.getTeacherEmail().equals(tempTeacher.getEmail()) && proposalAttributions.isProposalAttributed(tempProposal))
                    return false;
            }
        return !supervisorAttributions.isTeacherAttributed(tempTeacher);
    }
    public void removeAllTeachers() throws Exception {
        for(Teacher temp : teachers.getTeachersCopy().values()){
            if(isValidRemoveTeacher(temp))
                teachers.removeTeacher(temp);
            else
                Logger.appendMessage("O docente" + temp.getName() + " não pôde ser removido.");
        }
    }

    public void manualInsertApplication(String readString) throws Exception {applications.insertApplication(readString, proposals.toList(), students.toList());}
    public void manualUpdateApplication(String readString) {throw new UnsupportedOperationException("fdsfs");}
    public void manualRemoveApplication(String readString) throws Exception {
        Application tempApplication = applications.findApplicationByKey(parseLong(readString));

        if(tempApplication != null){
            applications.removeApplication(tempApplication);
        }
        else
            throw new Exception("A candidatura nao existe ou tem um numero de estudante invalido.");


    }
    public void removeAllApplications() {
        for(Application temp : applications.getApplicationsCopy().values()){
            try {
                applications.removeApplication(temp);
            }
            catch (Exception ex) {
                Logger.appendMessage("A candidatura" + temp + " não pôde ser removida.");
            }
        }
    }

    public void manualAssignAttribution(String nrStudentAsString, String codeProposal) throws Exception {
        try{
            Long nrStudentAsLong = parseLong(nrStudentAsString);

            Student studentToInsert = students.findStudent(nrStudentAsLong);
            ProposalAdapter proposalToInsert = proposals.findProposal(codeProposal);

            if(proposalAttributions.isAttributionPossible(proposalToInsert, studentToInsert)){
                Student newStudent = (Student) studentToInsert.clone();
                ProposalAdapter newProposal = (ProposalAdapter) proposalToInsert.clone();

                proposalAttributions.addAttributionCommand(newStudent, newProposal);
            }
        }
        catch (Exception ex) {
            throw new Exception("Não foi possível atribuir a proposta ao estudante escolhido.");
        }
    }
    public void manualUnassignAttribution(String nrStudentAsString, String codeProposal) throws Exception {

        Student tempStudent = students.findStudent(parseLong(nrStudentAsString));

        if(tempStudent == null)
            throw new Exception("Nao é possivel fazer a atribuição: o estudante nao existe.");

        ProposalAdapter tempProposal = proposals.findProposal(codeProposal);

        if(tempProposal == null)
            throw new Exception("Nao é possivel fazer a atribuição: a proposta nao existe.");


        if(proposalAttributions.isAttributedToStudent(tempProposal, tempStudent))
            proposalAttributions.removeAttributionCommand(tempStudent, tempProposal);
        else
            throw new Exception("Nao é possivel fazer a atribuição: o estudante nao esta atribuido à proposta.");
    }
    public void removeAllAttributions() throws Exception {
        proposalAttributions.removeAllAttributions();
    }

    public void manualAssignSupervisorAttribution(String nrStudentAsString, String teacherEmail) throws Exception {

        try{
            Student temp = findStudent(parseLong(nrStudentAsString));
            ProposalAdapter prop = proposalAttributions.findProposalAttribution(temp);

            Teacher teacherToInsert = teachers.findTeacher(teacherEmail);
            ProposalAdapter proposalToInsert = proposals.findProposal(prop.getCode());

            if(supervisorAttributions.isAttributionPossible(proposalToInsert, teacherToInsert)){
                Teacher newTeacher = (Teacher) teacherToInsert.clone();
                ProposalAdapter newProposal = (ProposalAdapter) proposalToInsert.clone();

                supervisorAttributions.addSupervisorAttributionCommand(newTeacher, newProposal);
            }
        }
        catch (Exception ex){
            Logger.logError("Não foi possível atribuir a proposta ao docentes escolhido: " + ex);
            throw new Exception("Não foi possível atribuir a proposta ao docentes escolhido: " + ex);
        }

    }
    public void manualUnassignSupervisorAttribution(String nrStudentAsString, String teacherEmail) throws Exception {
        Teacher tempTeacher = teachers.findTeacher(teacherEmail);

        if(tempTeacher == null)
            throw new Exception("Nao é possivel remover a atribuição: o docente nao existe.");

        Student tempStudent = students.findStudent(parseLong(nrStudentAsString));

        if(tempStudent == null)
            throw new Exception("Nao é possivel remover a atribuição: o estudante nao existe.");

        if (!proposalAttributions.isStudentAttributed(tempStudent))
            throw new Exception("Nao é possivel remover a atribuição: o estudante nao tem uma proposta atribuida.");

        ProposalAdapter tempProposal = proposalAttributions.findProposalAttribution(tempStudent);

        if(supervisorAttributions.isAttributedToTeacher(tempProposal, tempTeacher))
            supervisorAttributions.removeSupervisorAttributionCommand(tempTeacher, tempProposal);
        else
            throw new Exception("Nao é possivel fazer a atribuição: o docente nao esta atribuido a proposta do aluno.");
    }
    public void removeAllSupervisorAttributions() throws Exception {
        supervisorAttributions.removeAllSupervisorAttributions();
    }

    public void undoPhase3() throws Exception {
        if(!proposalAttributions.hasUndo())
            throw new Exception("Nao é possível fazer undo de momento");

        proposalAttributions.undo();
    }

    public void redoPhase3() throws Exception {
        if(!proposalAttributions.hasRedo())
            throw new Exception("Nao é possível fazer redo de momento");

        proposalAttributions.redo();
    }

    public void undoPhase4() throws Exception {
        if(!supervisorAttributions.hasUndo())
            throw new Exception("Nao é possível fazer undo de momento");

        supervisorAttributions.undo();
    }

    public void redoPhase4() throws Exception {
        if(!supervisorAttributions.hasRedo())
            throw new Exception("Nao é possível fazer redo de momento");

        supervisorAttributions.redo();
    }
}
