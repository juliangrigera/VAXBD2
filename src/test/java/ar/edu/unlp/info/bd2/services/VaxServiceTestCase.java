package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.config.SpringDataConfiguration;
import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.VaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Rollback(true)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {SpringDataConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
public class VaxServiceTestCase {
	private Date dob;

    @Autowired
    @Qualifier("springDataJpaService")
    VaxService service;

	@BeforeEach
	public void setUp() throws VaxException{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1982);
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 17);
		dob = cal.getTime();
	}

    @Test
    public void testCreatePatient() throws VaxException{
		Patient fede = this.service.createPatient("federico.orlando@info.unlp.edu.ar", "Federico Orlando", "pas$w0rd", dob);
    	assertNotNull (fede.getId());
    	assertEquals("Federico Orlando", fede.getFullname());
    	Optional<Patient> us = this.service.getPatientByEmail("federico.orlando@info.unlp.edu.ar");
    	if (!us.isPresent()) {
			throw new VaxException("Patient doesn't exist");
		}
		Patient user = us.get();
		assertNotNull (user.getId());
    	assertEquals("Federico Orlando",user.getFullname());
    	assertEquals(dob, user.getDayOfBirth());
    	assertEquals("pas$w0rd", user.getPassword());
    	VaxException ex = assertThrows(VaxException.class, () -> this.service.createPatient("federico.orlando@info.unlp.edu.ar", "Federico Orlando", "pas$w0rd", dob));
    	assertEquals("Constraint Violation",ex.getMessage());
    }

	@Test
	public void testCreateVaccine() throws VaxException{
		Vaccine sp = this.service.createVaccine("Sinopharm");
		assertNotNull(sp.getId());
		assertEquals("Sinopharm",  sp.getName());
		Optional<Vaccine> vax = this.service.getVaccineByName("Sinopharm");
		if (!vax.isPresent()) {
			throw new VaxException("Vaccine doesn't exist");
		}
		Vaccine saved = vax.get();
		assertNotNull(saved.getId());
		assertEquals("Sinopharm", saved.getName());
		VaxException ex = assertThrows(VaxException.class, () -> this.service.createVaccine("Sinopharm"));
		assertEquals("Constraint Violation",ex.getMessage());
	}

	@Test
	public void testCreateShot() throws VaxException{
		Patient fede = this.service.createPatient("federico.orlando@info.unlp.edu.ar", "Federico Orlando", "pas$w0rd", dob);
		Vaccine az = this.service.createVaccine("AstraZeneca");
		Centre htal = this.service.createCentre("Hospital Español");
		Nurse nurse = this.service.createNurse("22314678","Mary Poppins", 2);
		Shot shot = this.service.createShot(fede,az,dob,htal,nurse);
		assertNotNull(shot.getId());
		assertEquals(fede, shot.getPatient());
		assertEquals(az, shot.getVaccine());
		assertEquals("Hospital Español", shot.getCentre().getName());
		assertEquals("Mary Poppins", shot.getNurse().getFullName());
		assertTrue(fede.getShots().contains(shot));
		assertNotNull(shot.getShotCertificate());
		assertNotNull(shot.getShotCertificate().getSerialNumber());
		
	}

	@Test
	public void testCreateCentre() throws VaxException{
		Centre nuevo = this.service.createCentre("Calle 2");
		Nurse fabian = this.service.createNurse("43142333", "Fabian Ayala", 4);
		nuevo.addStaff(fabian);
		assertNotNull(nuevo.getId());
		Optional<Centre> calle2Saved = this.service.getCentreByName("Calle 2");
		if (!calle2Saved.isPresent()){throw new VaxException("No existe el centro con ese nombre");};
		Centre calle2 = calle2Saved.get();
		assertEquals("Calle 2", calle2.getName());
		assertTrue(calle2.getStaffs().contains(fabian));
		assertTrue(fabian.getCentres().contains(calle2));
	}

	@Test
	public void testCreateSupportStaff() throws VaxException{
		Centre h = this.service.createCentre("Hospital Italiano");
		SupportStaff ana = this.service.createSupportStaff("23331324", "Ana Mederos", "Ingresos");
		assertEquals(0,ana.getCentres().size());
		h.addStaff(ana);
		Centre italiano = this.service.updateCentre(h);
		Optional<SupportStaff> anaModified = this.service.getSupportStaffByDni(ana.getDni());
		if (!anaModified.isPresent()){throw new VaxException("No existe el centro con ese nombre");};
		SupportStaff AnaSaved = anaModified.get();
		assertEquals("Ana Mederos",AnaSaved.getFullName());
		assertEquals("Ingresos",AnaSaved.getArea());
		assertEquals(1,AnaSaved.getCentres().size());
		assertEquals(1,italiano.getStaffs().size());
		assertTrue(italiano.getStaffs().contains(AnaSaved));
		
	}

	@Test
	public void testVaccinationSchedule() throws VaxException{
		Vaccine az = this.service.createVaccine("AstraZeneca");
		Vaccine sv = this.service.createVaccine("Sputnik V");
		Vaccine jh = this.service.createVaccine("Johnson");
		Vaccine md = this.service.createVaccine("Moderna");

		VaccinationSchedule schedule = this.service.createVaccinationSchedule();
		schedule.addVaccine(az);
		schedule.addVaccine(az);
		schedule.addVaccine(md);
		VaccinationSchedule shortSchedule = this.service.createVaccinationSchedule();
		shortSchedule.addVaccine(jh);
		shortSchedule.addVaccine(az);

		try {
			VaccinationSchedule savedSchedule = this.service.getVaccinationScheduleById(schedule.getId());
			assertNotNull (savedSchedule.getId());
			assertTrue(savedSchedule.getVaccines().contains(az));
			assertTrue(savedSchedule.getVaccines().contains(md));
			assertEquals(az, (Vaccine)schedule.getVaccines().get(0));
			assertEquals(az, (Vaccine)schedule.getVaccines().get(1));
			assertEquals(md, (Vaccine)schedule.getVaccines().get(2));
			VaccinationSchedule savedShort = this.service.getVaccinationScheduleById(shortSchedule.getId());
			assertNotNull (savedShort.getId());
			assertTrue(savedShort.getVaccines().contains(az));
			assertTrue(savedShort.getVaccines().contains(jh));
			assertEquals(jh, (Vaccine)savedShort.getVaccines().get(0));
			assertEquals(az, (Vaccine)savedShort.getVaccines().get(1));
		}
		catch (Exception e) {
			throw new VaxException("Schedule doesn't exists");
		}

	}

}
