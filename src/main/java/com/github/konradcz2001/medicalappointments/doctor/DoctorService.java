package com.github.konradcz2001.medicalappointments.doctor;


import com.github.konradcz2001.medicalappointments.doctor.DTO.*;
import com.github.konradcz2001.medicalappointments.doctor.schedule.Schedule;
import com.github.konradcz2001.medicalappointments.doctor.schedule.WeekDay;
import com.github.konradcz2001.medicalappointments.exception.exceptions.*;
import com.github.konradcz2001.medicalappointments.leave.Leave;
import com.github.konradcz2001.medicalappointments.leave.LeaveRepository;
import com.github.konradcz2001.medicalappointments.review.ReviewRepository;
import com.github.konradcz2001.medicalappointments.specialization.SpecializationRepository;
import com.github.konradcz2001.medicalappointments.visit.type.TypeOfVisit;
import com.github.konradcz2001.medicalappointments.visit.type.TypeOfVisitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.konradcz2001.medicalappointments.common.Utils.returnResponse;
import static com.github.konradcz2001.medicalappointments.exception.MessageType.DOCTOR;
import static com.github.konradcz2001.medicalappointments.exception.MessageType.SPECIALIZATION;

/**
 * Service class for managing doctors.
 * This class provides methods for retrieving, creating, updating, and deleting doctors.
 * It also includes methods for adding and removing leaves and specializations for a doctor.
 * Additionally, it provides methods for retrieving all available doctors based on a specified date,
 * as well as retrieving all the leaves and reviews for a specific doctor.
 */
@Service
class DoctorService {
    private static final int MAX_VISIT_TYPES_PER_DOCTOR  = 30;

    private final DoctorRepository repository;
    private final SpecializationRepository specializationRepository;
    private final ReviewRepository reviewRepository;
    private final LeaveRepository leaveRepository;
    private final TypeOfVisitRepository typeOfVisitRepository;
    private final DoctorDTOMapper dtoMapper;

    DoctorService(final DoctorRepository repository, final SpecializationRepository specializationRepository,
                  final ReviewRepository reviewRepository, final LeaveRepository leaveRepository, final TypeOfVisitRepository typeOfVisitRepository, final DoctorDTOMapper dtoMapper) {
        this.repository = repository;
        this.specializationRepository = specializationRepository;
        this.reviewRepository = reviewRepository;
        this.leaveRepository = leaveRepository;
        this.typeOfVisitRepository = typeOfVisitRepository;
        this.dtoMapper = dtoMapper;
    }


    /**
     * Retrieves all doctors with pagination.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of doctors in the body
     * @throws EmptyPageException if the page is empty
     */
    ResponseEntity<Page<DoctorDTO>> readAll(Pageable pageable){
        return returnResponse(() -> repository.findAll(pageable), dtoMapper);
    }

    /**
     * Retrieves a doctor by their ID.
     *
     * @param id the ID of the doctor to retrieve
     * @return the ResponseEntity containing the DoctorDTO if found, or throws a ResourceNotFoundException if not found
     * @throws ResourceNotFoundException if the doctor with the specified ID is not found
     */
    ResponseEntity<DoctorDTO> readById(Long id){
        return repository.findById(id)
                .map(dtoMapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));
    }

    /**
     * Retrieves all doctors with a matching first name, ignoring case sensitivity, and returns them in a pageable format.
     *
     * @param firstName the first name to search for
     * @param pageable  the pageable object specifying the page number and size
     * @return a ResponseEntity containing a Page of DoctorDTO objects representing the matching doctors
     */
    ResponseEntity<Page<DoctorDTO>> readAllByFirstName(String firstName, Pageable pageable){
        return returnResponse(() -> repository.findAllByFirstNameContainingIgnoreCase(firstName, pageable), dtoMapper);
    }

    /**
     * Retrieves a page of doctors with the given last name, ignoring case.
     *
     * @param lastName the last name to search for
     * @param pageable the pagination information
     * @return a ResponseEntity containing a page of DoctorDTO objects
     */
    ResponseEntity<Page<DoctorDTO>> readAllByLastName(String lastName, Pageable pageable){
        return returnResponse(() -> repository.findAllByLastNameContainingIgnoreCase(lastName, pageable), dtoMapper);
    }

    /**
     * Retrieves a page of doctors with the specified specialization.
     *
     * @param specialization the specialization to search for
     * @param pageable       the pagination information
     * @return a ResponseEntity containing a page of DoctorDTO objects
     */
    ResponseEntity<Page<DoctorDTO>> readAllBySpecialization(String specialization, Pageable pageable){
        return returnResponse(() -> repository.findAllByAnySpecializationContainingIgnoreCase(specialization, pageable), dtoMapper);
    }

    /**
     * Deletes a doctor with the specified ID.
     *
     * @param id the ID of the doctor to delete
     * @return a ResponseEntity with no content if the doctor is successfully deleted
     * @throws ResourceNotFoundException if the doctor with the specified ID is not found
     */
    @Transactional
    ResponseEntity<?> deleteDoctor(Long id){
        return repository.findById(id)
                .map(doctor -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));
    }


    /**
     * Adds a leave for the doctor with the specified ID.
     *
     * @param id The ID of the doctor to add the leave for
     * @param leaveDTO The DTO containing the leave information
     * @return ResponseEntity representing the result of the operation
     * @throws ResourceNotFoundException if the doctor with the specified ID is not found
     * @throws WrongLeaveException if the start date of the leave is later than the end date
     */
    @Transactional
    ResponseEntity<?> addLeave(Long id, DoctorLeaveDTO leaveDTO) {
        System.out.println(LocalDateTime.now());
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));

        Leave leave = dtoMapper.mapFromDoctorLeaveDTO(leaveDTO, new Leave());

        if (leave.getStartDate().isAfter(leave.getEndDate()))
            throw new WrongLeaveException("The beginning of the leave cannot be later than the end");

        List<Leave> leavesToRemove = new ArrayList<>();


        for (Leave existingLeave : doctor.getLeaves()) {
            if (!(leave.getEndDate().isBefore(existingLeave.getStartDate()) || leave.getStartDate().isAfter(existingLeave.getEndDate()))) {

                leave.setStartDate(leave.getStartDate().isBefore(existingLeave.getStartDate()) ? leave.getStartDate() : existingLeave.getStartDate());
                leave.setEndDate(leave.getEndDate().isAfter(existingLeave.getEndDate()) ? leave.getEndDate() : existingLeave.getEndDate());

                leavesToRemove.add(existingLeave);
            }
        }

        leave.setDoctor(doctor);
        doctor.getLeaves().removeAll(leavesToRemove);
        doctor.addLeave(leave);

        repository.save(doctor);

        return ResponseEntity.noContent().build();
    }

    /**
     * Removes a leave from a doctor's list of leaves.
     *
     * @param doctorId the ID of the doctor
     * @param leaveId the ID of the leave to be removed
     * @return a ResponseEntity with a no content status if the leave was successfully removed
     * @throws ResourceNotFoundException if the doctor with the specified ID is not found
     * @throws WrongLeaveException if the doctor with the specified ID does not have the specified leave
     */
    @Transactional
    ResponseEntity<?> removeLeave(Long doctorId, Long leaveId){
        return repository.findById(doctorId)
                .map(doctor -> doctor.getLeaves().stream()
                        .filter(leave -> leave.getId().equals(leaveId)).findAny()
                        .map(leave -> {
                            doctor.removeLeave(leave);
                            repository.save(doctor);
                            return ResponseEntity.noContent().build();
                        })
                        .orElseThrow(() -> new WrongLeaveException("Doctor with id = " + doctorId + " does not have the specified leave with id = " + leaveId)))
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, doctorId));
    }

    /**
     * Removes a specialization from a doctor.
     *
     * @param doctorId The ID of the doctor.
     * @param specializationId The ID of the specialization to be removed.
     * @return A ResponseEntity with no content if the specialization is successfully removed.
     * @throws ResourceNotFoundException If the doctor with the specified ID is not found.
     * @throws WrongSpecializationException If the doctor does not have the specified specialization.
     */
    @Transactional
    ResponseEntity<?> removeSpecialization(Long doctorId, Integer specializationId){
        return repository.findById(doctorId)
                .map(doctor -> doctor.getSpecializations().stream()
                        .filter(spec -> spec.getId().equals(specializationId)).findAny()
                        .map(spec ->{
                              doctor.removeSpecialization(spec);
                              repository.save(doctor);
                              return ResponseEntity.noContent().build();
                        })
                        .orElseThrow(() -> new WrongSpecializationException("Doctor with id = " + doctorId + " does not have the specified specialization with id = " + specializationId)))
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, doctorId));
    }

    /**
     * Retrieves all available doctors based on the specified date.
     *
     * @param date     the date to filter the available doctors
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of DoctorDTO objects representing the available doctors
     */
    ResponseEntity<Page<DoctorDTO>> readAllAvailableByDate(LocalDateTime date, Pageable pageable){
        return returnResponse(() -> repository.findAllAvailableByDate(date, pageable), dtoMapper);
    }

//    private boolean isAvailableByDate(LocalDateTime date, Doctor doctor){
//        return doctor.getLeaves().stream()
//                .allMatch(leave -> (leave.getStartDate().isAfter(date) || leave.getEndDate().isBefore(date)));
//    }

    /**
     * Creates a new doctor.
     * <p>
     * This method creates a new doctor with the provided information. The doctor's ID is set to null, and the specializations,
     * leaves, and reviews are initialized as empty collections. The doctor is then saved in the repository.
     *
     * @param doctor The doctor object containing the information of the new doctor to be created.
     * @return A ResponseEntity containing the created doctor's DTO and a status code of 201 (Created).
     */
    @Transactional
    ResponseEntity<DoctorDTO> createDoctor(Doctor doctor){
        doctor.setId(null);
        doctor.setSpecializations(new HashSet<>());
        doctor.setLeaves(new ArrayList<>());
        doctor.setReviews(new ArrayList<>());
        Doctor created =  repository.save(doctor);
        return ResponseEntity.created(URI.create("/" + created.getId())).body(dtoMapper.mapToDTO(created));
    }

    /**
     * Updates a doctor with the specified ID using the provided DoctorDTO object.
     *
     * @param id       The ID of the doctor to update.
     * @param toUpdate The DoctorDTO object containing the updated information.
     * @return A ResponseEntity with a status of 204 No Content if the doctor is successfully updated.
     * @throws ResourceNotFoundException If the doctor with the specified ID is not found.
     */
    @Transactional
    ResponseEntity<?> updateDoctor(Long id, DoctorDTO toUpdate){
        return repository.findById(id)
                .map(doctor -> {
                    repository.save(dtoMapper.mapFromDTO(toUpdate, doctor));
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));
    }

    /**
     * Retrieves all the leaves for a specific doctor.
     *
     * @param id       the ID of the doctor
     * @param pageable the pageable object for pagination
     * @return a ResponseEntity containing a Page of DoctorLeaveDTO objects representing the leaves
     * @throws EmptyPageException        if the page is empty
     * @throws ResourceNotFoundException if the doctor with the specified ID is not found
     */
    ResponseEntity<Page<DoctorLeaveDTO>> readAllLeaves(Long id, Pageable pageable){
        var doctors = leaveRepository.findAllByDoctorId(id, pageable)
                .map(dtoMapper::mapToDoctorLeaveDTO);

        if(doctors.isEmpty())
            throw new EmptyPageException();
        return ResponseEntity.ok(doctors);
    }

    /**
     * Retrieves all specializations of a doctor with the given ID.
     *
     * @param id The ID of the doctor.
     * @return ResponseEntity containing a Set of DoctorSpecializationDTO objects representing the specializations of the doctor.
     * @throws ResourceNotFoundException if the doctor with the given ID is not found.
     */
    ResponseEntity<Set<DoctorSpecializationDTO>> readAllSpecializations(Long id){
        return repository.findById(id)
                .map(Doctor::getSpecializations)
                .map(spec -> spec.stream().map(dtoMapper::mapToDoctorSpecializationDTO)
                        .collect(Collectors.toSet()))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));
    }

    /**
     * Adds specializations to a doctor.
     * <p>
     * This method adds the specified specializations to the doctor with the given ID. The doctor's current number of specializations
     * is checked, and if the total number of specializations after adding the new ones exceeds 5, a WrongSpecializationException is thrown.
     * For each specialization ID in the set of specialization IDs, the method checks if the specialization exists in the specialization repository.
     * If it exists, the specialization is added to the doctor's set of specializations. If it doesn't exist, a ResourceNotFoundException is thrown.
     * After adding the specializations, the doctor is saved in the repository.
     *
     * @param doctorId The ID of the doctor to add the specializations to.
     * @param specializationIds The set of specialization IDs to add to the doctor.
     * @return A ResponseEntity with no content if the specializations were added successfully.
     * @throws ResourceNotFoundException If the doctor with the given ID is not found.
     * @throws WrongSpecializationException If the total number of specializations after adding the new ones exceeds 5.
     */
    @Transactional
    ResponseEntity<?> addSpecializations(Long doctorId, Set<Integer> specializationIds) {
        return repository.findById(doctorId)
                .map(doctor -> {
                    if((doctor.getSpecializations().size() + specializationIds.size()) > 5)
                        throw new WrongSpecializationException("Doctor with id = " + doctorId + " can not has more than 5 specializations, he has already " + specializationIds.size() + " specializations and in request there are next " + specializationIds.size() + " specializations");
                    specializationIds.forEach(id -> specializationRepository.findById(id)
                            .ifPresentOrElse(spec -> {
                                        if(doctor.getSpecializations().contains(spec))
                                            throw new WrongSpecializationException("Doctor with id = " + doctorId + " already has a specialization with id = " + spec.getId());
                                        doctor.addSpecialization(spec);
                                    },
                                    () -> {throw new ResourceNotFoundException(SPECIALIZATION, id.longValue());}
                            ));

                    repository.save(doctor);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, doctorId));
    }

    /**
     * Retrieves all the reviews for a specific doctor.
     *
     * @param id       the ID of the doctor
     * @param pageable the pageable object for pagination
     * @return a ResponseEntity containing a Page of DoctorReviewDTO objects representing the reviews
     * @throws EmptyPageException if the page is empty
     */
    ResponseEntity<Page<DoctorReviewDTO>> readAllReviews(Long id, Pageable pageable) {
        var doctors = reviewRepository.findAllByDoctorId(id, pageable)
                .map(dtoMapper::mapToDoctorReviewDTO);
        if(doctors.isEmpty())
            throw new EmptyPageException();

        return ResponseEntity.ok(doctors);
    }


//    ResponseEntity<Page<DoctorDTO>> searchDoctors(String word, String specialization, Pageable pageable) {
//
//        if (specialization == null)
//            return returnResponse(() -> repository.search(word, pageable), dtoMapper);
//        return returnResponse(() -> repository.searchWithSpecialization(word, specialization, pageable), dtoMapper);
//    }


    /**
     * Searches for doctors based on the given search word and specialization.
     *
     * @param word The search word to look for in the doctor's information.
     * @param specialization The specialization to filter the search results, can be null.
     * @param pageable The pagination information for the search results.
     * @return A ResponseEntity containing a Page of DoctorDTO objects matching the search criteria.
     * @throws EmptyPageException if the search word is empty or no results are found.
     */
     ResponseEntity<Page<DoctorDTO>> searchDoctors(String word, String specialization, Pageable pageable) {
        String[] parts = word.split(" ");
        List<String> stringsList = Arrays.stream(parts)
                .filter(part -> !part.isEmpty())
                .toList();

        if (stringsList.isEmpty())
            throw new EmptyPageException();

        List<Set<Doctor>> resultList = new ArrayList<>();

        for (String wordPart : stringsList) {
            List<Doctor> doctorsList;

            if (specialization == null)
                doctorsList = repository.search(wordPart);
            else
                doctorsList = repository.searchWithSpecialization(wordPart, specialization);

            resultList.add(new HashSet<>(doctorsList));
        }

        Set<Doctor> commonDoctors = findCommonElements(resultList);

        List<Doctor> commonDoctorList = new ArrayList<>(commonDoctors);
        commonDoctorList.sort(Comparator.comparing(Doctor::getFirstName));

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), commonDoctorList.size());
        Page<Doctor> doctorPage = new PageImpl<>(commonDoctorList.subList(start, end), pageable, commonDoctorList.size());

        return returnResponse(() -> doctorPage, dtoMapper);
    }


    /**
     * Finds the common elements among the list of sets of Doctors.
     *
     * @param resultList the list of sets of Doctors to find common elements from
     * @return a set of Doctors that are common among all sets in the input list
     */
    private Set<Doctor> findCommonElements(List<Set<Doctor>> resultList) {
        if (resultList.isEmpty()) return Collections.emptySet();

        Set<Doctor> commonDoctors = new HashSet<>(resultList.get(0));
        for (Set<Doctor> set : resultList) {
            commonDoctors.retainAll(set);
        }
        return commonDoctors;
    }


    ResponseEntity<Page<DoctorTypeOfVisitDTO>> readAllTypesOfVisits(Long id, Pageable pageable) {
        var doctors = typeOfVisitRepository.findAllByDoctorIdAndIsActive(id, true, pageable)
                .map(dtoMapper::mapToDoctorTypeOfVisitDTO);
        if(doctors.isEmpty())
            throw new EmptyPageException();

        return ResponseEntity.ok(doctors);
    }

    @Transactional
    ResponseEntity<?> removeTypeOfVisit(Long doctorId, Long typeOfVisitId){
        return repository.findById(doctorId)
                .map(doctor -> doctor.getTypesOfVisits().stream()
                        .filter(type -> type.getId().equals(typeOfVisitId)).findAny()
                        .map(type -> {
                            doctor.getTypesOfVisits().stream().filter(t -> Objects.equals(t.getId(), type.getId())).findFirst().get().setActive(false);
                            repository.save(doctor);
                            return ResponseEntity.noContent().build();
                        })
                        .orElseThrow(() -> new WrongTypeOfVisitException("Doctor with id = " + doctorId + " does not have the specified type of visit with id = " + typeOfVisitId)))
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, doctorId));
    }


    @Transactional
    ResponseEntity<?> addTypeOfVisit(Long id, DoctorTypeOfVisitDTO typeOfVisitDTO) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));

        if(doctor.getTypesOfVisits().size() >= MAX_VISIT_TYPES_PER_DOCTOR)
            throw new WrongTypeOfVisitException("Doctor with id = " + id + " has reached the maximum number of types of " + MAX_VISIT_TYPES_PER_DOCTOR);

        TypeOfVisit typeOfVisit = new TypeOfVisit();
        typeOfVisit.setDoctor(doctor);
        typeOfVisit = dtoMapper.mapFromDoctorTypeOfVisitDTO(typeOfVisitDTO, typeOfVisit);

        doctor.addTypeOfVisit(typeOfVisit);
        repository.save(doctor);

        return ResponseEntity.noContent().build();
    }

    @Transactional
    ResponseEntity<?> updateSchedule(Long id, Schedule schedule){
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DOCTOR, id));

        List<WeekDay> days = schedule.getListOfDays();
        for(int i = 0; i < days.size(); i++){
            WeekDay day = days.get(i);

            if((day.getStart() == null && day.getEnd() != null) || (day.getStart() != null && day.getEnd() == null))
                throw new WrongScheduleException(i, false);
            if(day.getStart() != null && day.getStart().isAfter(day.getEnd()))
                throw new WrongScheduleException(i, true);
        }

        if(doctor.getSchedule() != null)
            schedule.setId(doctor.getSchedule().getId());

        doctor.setSchedule(schedule);
        repository.save(doctor);
        return ResponseEntity.noContent().build();
    }
}
