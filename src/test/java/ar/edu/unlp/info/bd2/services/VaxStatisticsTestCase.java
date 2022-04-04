package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.config.DBInitializerConfig;
import ar.edu.unlp.info.bd2.config.HibernateConfiguration;
import ar.edu.unlp.info.bd2.utils.DBInitializer;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, HibernateConfiguration.class, DBInitializerConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class VaxStatisticsTestCase {
    @Autowired
    DBInitializer initializer;

    @Autowired
    VaxService service;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeAll
    public void prepareDB() throws Exception {
        this.initializer.prepareDB();
    }
    
    private <T> void assertListEquality(List<T> list1, List<T> list2) {
        if (list1.size() != list2.size()) {
          Assert.fail("Lists have different size");
        }

        for (T objectInList1 : list1) {
          if (!list2.contains(objectInList1)) {
            Assert.fail(objectInList1 + " is not present in list2");
          }
        }
      }
    

}
