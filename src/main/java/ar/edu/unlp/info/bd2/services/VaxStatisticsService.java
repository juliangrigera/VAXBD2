package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;

import java.util.Date;
import java.util.List;

public interface VaxStatisticsService {
	
	/**
	 * @return Una lista con todos los pacientes
	 */
	List<Patient> getAllPatients();
	
	/**
	 * @return Una lista con todos los enfermeros que tengan más de <code>years</code> años de experiencia
	 */
	List<Nurse> getNurseWithMoreThanNYearsExperience(int years);
	
	/**
	 * @return Una lista con los <code>n</code> centros que más staff tiene
	 */
	List<Centre> getCentresTopNStaff(int n);
	
	/**
	 * @return El centro que más vacunas aplico
	 */
	Centre getTopShotCentre();
	
	/**
	 * @return Una lista de los enfermeros que no aplicaron vacunas
	 */
	List<Nurse> getNurseNotShot();
	
	/**
	 * @return El area de Support Staff con menor cantidad de empleados
	 */
	String getLessEmployeesSupportStaffArea();
	
	/**
	 * @return Los empleados cuyo fullName contenga <code>name</code>
	 */
	List<Staff> getStaffWithName(String name);
	
	/**
	 * @return Una lista de las vacunas de las que no se aplicaron dosis
	 */
	List<Vaccine> getUnappliedVaccines();
	
	/**
	 * @return Una lista de los certificados emitidos en un rango de fechas
	 */
	List <ShotCertificate> getShotCertificatesBetweenDates(Date startDate, Date endDate);

}
