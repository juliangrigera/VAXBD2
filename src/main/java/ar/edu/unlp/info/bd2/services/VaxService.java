package ar.edu.unlp.info.bd2.services;
import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.VaxException;

import java.util.Date;
import java.util.Optional;


public interface VaxService extends VaxStatisticsService{

	/**
	 *
	 * @param email email del usuario con el cual ingresa al sitio
	 * @param fullname nombre y apellido del usuario
	 * @param password clave con la que el usuario ingresa al sitio
	 * @param dayOfBirth fecha de nacimiento del usuario
	 * @return el usuario creado
	 * @throws VaxException
	 */
	Patient createPatient(String email, String fullname, String password, Date dayOfBirth) throws VaxException;

	/**
	 *
	 * @param name nombre de la vacuna
	 * @return la vacuna creada
	 * @throws VaxException
	 */
	Vaccine createVaccine(String name) throws VaxException;

	/**
	 *
	 * @param patient paciente vacunado
	 * @param vaccine vacuna aplicada
	 * @param date fecha de aplicación
	 * @param centre el centro de vacunación donde se aplicó
	 * @param nurse enfermero/a que aplico la vacuna
	 * @return el usuario creado
	 * @throws VaxException
	 */
	Shot createShot(Patient patient, Vaccine vaccine, Date date, Centre centre, Nurse nurse) throws VaxException;
	

	/**
	 * 
	 * @param email email del usuario
	 * @return
	 */
	Optional<Patient> getPatientByEmail(String email);


	/**
	 *
	 * @param name nombre de la vacuna
	 * @return
	 */
	Optional<Vaccine> getVaccineByName(String name);

	/**
	 *
	 * @param name nombre del centro de vacunación
	 * @return el centro de vacunación nuevo
	 * @throws VaxException
	 */
	Centre createCentre(String name) throws VaxException;

	/**
	 * @param dni el dni
	 * @param fullName nombre del/la enfermero/a
	 * @param experience experiencia en años
	 * @return el enfermero creado
	 * @throws VaxException
	 */
	Nurse createNurse(String dni, String fullName, Integer experience) throws VaxException;

	/**
	* @param dni el dni
	* @param fullName nombre completo
	* @param area el area o areas de trabajo
	* @return el personal de apoyo creado
	* @throws VaxException
	* */
	SupportStaff createSupportStaff(String dni, String fullName, String area) throws VaxException;

	/**
	 * @return el esquema nueva vacío
	 * @throws VaxException
	 * */
	VaccinationSchedule createVaccinationSchedule() throws VaxException;

	/**
	 * @param id el id del esquema
	 * @return el esquema de vacunación
	 * */
	VaccinationSchedule getVaccinationScheduleById(Long id) throws VaxException;

	/**
	 * @param name el nombre del centro a buscar
	 * @return el centro
	 * */
	Optional<Centre> getCentreByName(String name) throws VaxException;

	/**
	 * @param staff el staff a actualizar
	 * @return el staff
	 * @throws VaxException 
	 */
	SupportStaff updateSupportStaff(SupportStaff staff) throws VaxException;

	/**
	 * @param centre el centre a actualizar
	 * @return el centre
	 * @throws VaxException 
	 */
	Centre updateCentre(Centre centre);

	/**
	 * @param dni el dni del SupportStaff a buscar
	 * @return el SupportStaff
	 * */
	Optional<SupportStaff> getSupportStaffByDni(String dni);
	
	/**
	 * 
	 * @param schedule
	 * @return el VaccinationSchedule actualizado.
	 */
	VaccinationSchedule updateVaccinationSchedule(VaccinationSchedule schedule);

	
}
