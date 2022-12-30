package com.driver;
import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {
    private HashMap<String,Student> studentMap;
    private HashMap<String,Teacher> teacherMap;
    private HashMap<String,List<String>> teacherorStudentMapping;

    public StudentRepository(){
        this.studentMap=new HashMap<String, Student>();
        this.teacherMap=new HashMap<String, Teacher>();
        this.teacherorStudentMapping=new HashMap<String, List<String>>();
    }

    public void saveStudent(Student student){
        studentMap.put(student.getName(),student);
    }
    public void saveTeacher(Teacher teacher){
        teacherMap.put(teacher.getName(),teacher);
    }
    public void saveStudentTeacherPair(String student,String teacher){
        if(studentMap.containsKey(student)&&teacherMap.containsKey(teacher)){
            List<String> currentStudentsByTeacher = new ArrayList<>();
            if(teacherorStudentMapping.containsKey(teacher))
                currentStudentsByTeacher=teacherorStudentMapping.get(teacher);
                currentStudentsByTeacher.add(student);
                teacherorStudentMapping.put(teacher,currentStudentsByTeacher);
        }
    }
    public Student findStudent(String student){
        return studentMap.get(student);
    }
    public Teacher findTeacher(String teacher){
        return teacherMap.get(teacher);
    }
    public List<String> findStudentsFromTeacher(String teacher){
        List<String> studentsList = new ArrayList<String>();
        if(teacherorStudentMapping.containsKey(teacher))
            studentsList = teacherorStudentMapping.get(teacher);
        return studentsList;
    }
    public List<String> findAllStudents(){
        return new ArrayList<>(studentMap.keySet());
    }
    public void deleteTeacher(String teacher){

        List<String> students = new ArrayList<String>();
        if(teacherorStudentMapping.containsKey(teacher)){

            students = teacherorStudentMapping.get(teacher);
            for(String student: students){
                if(studentMap.containsKey(student)){
                    studentMap.remove(student);
                }
            }


            teacherorStudentMapping.remove(teacher);
        }


        if(teacherMap.containsKey(teacher)){
            teacherMap.remove(teacher);
        }
    }

    public void deleteAllTeachers(){

        HashSet<String> studentSet = new HashSet<String>();


        teacherMap = new HashMap<>();
        for(String teacher: teacherorStudentMapping.keySet()){
            for(String student: teacherorStudentMapping.get(teacher)){
                studentSet.add(student);
            }
        }
        for(String student: studentSet){
            if(studentMap.containsKey(student)){
                studentMap.remove(student);
            }
        }

        teacherorStudentMapping = new HashMap<>();
    }
}
