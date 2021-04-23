package net.ddns.varuzhan.demo.dto;

import net.ddns.varuzhan.demo.model.ClassMaterial;

public class ClassMaterialsShowAndQuizCountDto implements Comparable {
    private ClassMaterial material;
    private Integer countOfQuestions;

    public ClassMaterialsShowAndQuizCountDto(ClassMaterial classMaterial, Integer countOfQuestions) {
        this.material = classMaterial;
        this.countOfQuestions = countOfQuestions;
    }

    public ClassMaterial getMaterial() {
        return material;
    }

    public void setMaterial(ClassMaterial material) {
        this.material = material;
    }

    public Integer getCountOfQuestions() {
        return countOfQuestions;
    }

    public void setCountOfQuestions(Integer countOfQuestions) {
        this.countOfQuestions = countOfQuestions;
    }


    @Override
    public int compareTo(Object o) {
        return this.material.compareTo(((ClassMaterialsShowAndQuizCountDto)o).material);
    }
}
