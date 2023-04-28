package com.mjc.school.controller;

import com.mjc.school.controller.annotations.CommandHandler;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.mjc.school.controller.utils.Constants.*;

@AnalyzeClasses(packages = "com.mjc.school")
public class ControllerTest {

    @Test
    void Context_should_have_three_Controller(){
        JavaClasses classes = new ClassFileImporter().importPackages(controllerImplPackage);
        Assertions.assertEquals(3, classes.size());
    }

    ImportOption ignoreTest = location -> !location.contains("/test/");

    @ArchTest
    static final ArchRule rule = ArchRuleDefinition.classes()
            .that()
            .resideInAPackage(controllerPackage)
            .should()
            .onlyBeAccessed().
            byAnyPackage(controllerPackage, mainPackage);

    @Test
    void Controller_methods_should_be_annotated_with_CommandHandler(){
        JavaClasses classes = new ClassFileImporter().withImportOption(ignoreTest).importPackages(controllerImplPackage);

        ArchRule myRule = ArchRuleDefinition.methods()
                .that()
                .arePublic()
                .and()
                .areDeclaredInClassesThat()
                .resideInAPackage(controllerImplPackage)
                .should()
                .beAnnotatedWith(CommandHandler.class);

        myRule.check(classes);
    }

    @Test
    void ControllerClasses_should_implement_BaseController(){
        JavaClasses classes = new ClassFileImporter().withImportOption(ignoreTest).importPackages(controllerImplPackage);
        classes.forEach(System.out::println);

        ArchRule myRule = ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Controller")
                .should().resideInAPackage(controllerImplPackage)
                .andShould().implement(BaseController.class);

        myRule.check(classes);
    }
}

