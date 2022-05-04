package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.config.DBInitializerConfig;
import ar.edu.unlp.info.bd2.config.HibernateConfiguration;
import ar.edu.unlp.info.bd2.utils.DBInitializer;
import ar.edu.unlp.info.bd2.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void testTrue(){ assertEquals(1,1);}

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
    
    @Test
    public void testGetAllPatients() {
    	assertEquals(322,this.service.getAllPatients().size());
    }
    
    @Test
    public void testGetNurseWithMoreThanNYearsExperience() {
    	List<Nurse> nurses =this.service.getNurseWithMoreThanNYearsExperience(9); 
    	assertEquals(4,nurses.size());
    	this.assertListEquality(nurses.stream().map(property -> property.getFullName()).collect(Collectors.toList()),Arrays.asList("Arneris Ibáñez","Emir Vidal","Cornelio Sánchez","Kristin Vega"));
    }
    
    @Test
    public void testGetCentresTopNStaff() {
    	List<Centre> centres = this.service.getCentresTopNStaff(5);
    	assertEquals(5,centres.size());
    	this.assertListEquality(centres.stream().map(property -> property.getName()).collect(Collectors.toList()), Arrays.asList("Hospital San Juan de Dios","SADOP","PAMI","ATE","Abasto"));
    }
    
    @Test
    public void testGetTopShotCentre() {
    	Centre centre = this.service.getTopShotCentre();
    	assertEquals("Hospital de Romero",centre.getName());
    }
    
    @Test
    public void testGetNurseNotShot() {
    	List<Nurse> nurses = this.service.getNurseNotShot();
    	assertEquals(1,nurses.size());
    	assertEquals("Kristin Vega",nurses.get(0).getFullName());
    	assertEquals("46768509",nurses.get(0).getDni());
    	assertEquals(Integer.valueOf(10),nurses.get(0).getExperience());
    }
    
    @Test
    public void testGetLessEmployeesSupportStaffArea() {
    	String area = this.service.getLessEmployeesSupportStaffArea();
    	assertEquals("Observaciones",area);
    }
    
    @Test
    public void testGetStaffWithName() {
    	List<Staff> staffs = this.service.getStaffWithName("Hernández");
    	assertEquals(3,staffs.size());
    	this.assertListEquality(staffs.stream().map(property -> property.getFullName()).collect(Collectors.toList()), Arrays.asList("Ceasar Hernández","Kasim Hernández","Modesty Hernández"));
    }
    
    @Test
    public void testGetUnappliedVaccines() {
    	List<Vaccine> vaccines = this.service.getUnappliedVaccines();
    	assertEquals(1,vaccines.size());
    	assertEquals("Bharat",vaccines.get(0).getName());
    }
    
    @Test
    public void testGetShotCertificatesBetweenDates() {
    	List<ShotCertificate> certificates;
		try {
			certificates = this.service.getShotCertificatesBetweenDates(sdf.parse("1/7/2020"), sdf.parse("7/7/2020"));
			assertEquals(10, certificates.size());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
