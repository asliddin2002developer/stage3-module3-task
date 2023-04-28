package com.mjc.school;


import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;


import static com.mjc.school.utils.utils.Constants.*;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideOutsideOfPackages;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
public class MainTest {

    DescribedPredicate<? super JavaClass> moduleMain = resideInAPackage(mainPackage)
            .and(resideOutsideOfPackages(controllerPackage, servicePackage, repositoryPackage ));
    DescribedPredicate<? super JavaClass> moduleWeb = resideInAPackage(controllerPackage);
    DescribedPredicate<? super JavaClass> moduleService = resideInAPackage(servicePackage);
    DescribedPredicate<? super JavaClass> moduleRepository = resideInAPackage(repositoryPackage);

    @Test
    void projectShouldFollowLayeredArchitecture(){
        var javaClasses = new ClassFileImporter().importPackages(mainPackage);
        var layeredArchitecture =
                layeredArchitecture().consideringOnlyDependenciesInLayers()
                        .layer("main")
                        .definedBy(moduleMain)
                        .layer("web")
                        .definedBy(moduleWeb)
                        .layer("service")
                        .definedBy(moduleService)
                        .layer("repository")
                        .definedBy(moduleRepository)
                        .whereLayer("main")
                        .mayOnlyBeAccessedByLayers("web", "main")
                        .whereLayer("web")
                        .mayOnlyBeAccessedByLayers("main", "web")
                        .whereLayer("service")
                        .mayOnlyBeAccessedByLayers("web", "service")
                        .whereLayer("repository")
                        .mayOnlyBeAccessedByLayers("repository", "service");

        layeredArchitecture.check(javaClasses);

    }


}
