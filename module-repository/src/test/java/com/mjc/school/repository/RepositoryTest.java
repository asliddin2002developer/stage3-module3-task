package com.mjc.school.repository;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.mjc.school.repository.utils.Constants.*;

@AnalyzeClasses(importOptions = ImportOption.DoNotIncludeTests.class)
public class RepositoryTest {
    @Test
    void Context_should_have_two_Repositories(){
        JavaClasses classes = new ClassFileImporter().importPackages(repositoryImplPackage);
        Assertions.assertEquals(2, classes.size());
    }

    @Test
    void Repository_should_only_be_accessed_through_Service(){
        JavaClasses importedClasses = new ClassFileImporter().importPackages(mainPackage);

        ArchRule myRule = ArchRuleDefinition.classes()
                .that().resideInAPackage(repositoryPackage)
                .should().onlyBeAccessed().byAnyPackage(servicePackage, repositoryPackage);

        myRule.check(importedClasses);
    }


}
