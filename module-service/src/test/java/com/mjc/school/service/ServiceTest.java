package com.mjc.school.service;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;

import static com.mjc.school.service.utils.Constants.*;

public class ServiceTest {

    @Test
    void Context_should_have_three_Services(){
        JavaClasses classes = new ClassFileImporter().importPackages(serviceImplPackage);
        Assertions.assertEquals(3, classes.size());
    }

    ImportOption ignoreTest = location -> !location.contains("/test/");

    @Test
    void ServiceClasses_should_only_be_accessed_through_Controller(){
        JavaClasses importedClasses = new ClassFileImporter().withImportOption(ignoreTest).importPackages(mainPackage);

        ArchRule myRule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(servicePackage)
                .should()
                .onlyBeAccessed()
                .byAnyPackage(controllerPackage, servicePackage);

        myRule.check(importedClasses);
    }

    @Test
    void ServiceImplClasses_should_implement_BaseService(){
        JavaClasses importedClasses = new ClassFileImporter().withImportOption(ignoreTest).importPackages(serviceImplPackage);

        ArchRule myRule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(serviceImplPackage)
                .should()
                .implement(BaseService.class);

        myRule.check(importedClasses);
    }

    @Test
    void Service_layer_should_have_model_dto_mapper_or_use_mapstruct_Annotation(){
        JavaClasses importedClasses = new ClassFileImporter().withImportOption(ignoreTest).importPackages(serviceMapperPackage);

        ArchRule myRule = ArchRuleDefinition.classes()
                .that()
                .resideInAPackage(serviceMapperPackage)
                .should()
                .haveSimpleNameEndingWith("Mapper")
                .orShould()
                .haveSimpleNameEndingWith("Impl")
                .orShould()
                .beAnnotatedWith(Mapper.class);

        myRule.check(importedClasses);
    }
}
