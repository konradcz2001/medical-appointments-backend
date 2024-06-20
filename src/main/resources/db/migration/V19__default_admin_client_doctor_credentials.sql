SELECT setval('public.clients_id_seq', 102, true);
INSERT INTO clients (id, first_name, last_name, email, role, password)
VALUES
    (101,'Michael','Williams','admin@admin','ADMIN','$2a$10$dxnCJO/5bgPmQJzGpBYrA.mdyl/Tzd7X.c/O8.JujLo4MPGymjI7W'),
    (102,'Emily','Johnson','client@client','CLIENT','$2a$10$8kXXPSroUSt.Jl4Ew2MY/OLHnlKz9jmcto2iXQAHGOzzhRQCMmihG');




SELECT setval('public.doctors_id_seq', 101, true);
INSERT INTO doctors (id, first_name, last_name, email, role, password, zip_code, state, country, city, street, house_number, is_verified, schedule_id, profile_description)
VALUES
    (101,'John','Smith','doctor@doctor','DOCTOR','$2a$10$uJkavznlgUQzwThO6DCheOe8m/J8MWa/7o2EDD8m0n65igm0TMBuK', '48778', 'Tamil Nadu', 'Fiji', 'Salzburg', '8626 Eget Rd.', '35c', true, 81, 'As a knowledgeable and experienced healthcare professional, I am dedicated to providing comprehensive medical care tailored to your unique needs. With a focus on building strong doctor-patient relationships, I strive to create a comfortable and supportive environment where you can openly discuss your concerns. Together, we will develop a personalized treatment plan that addresses your specific goals and promotes long-term well-being. Schedule an appointment today, and let''s embark on a journey towards optimal health.');



SELECT setval('public.schedules_id_seq', 81, true);
INSERT INTO schedules (id, monday_start, monday_end, tuesday_start, tuesday_end, wednesday_start, wednesday_end, thursday_start, thursday_end, friday_start, friday_end, saturday_start, saturday_end, sunday_start, sunday_end)
VALUES
    (81, '08:00', '16:00', '09:00', '17:00', '10:00', '18:00', '08:00', '16:00', '09:00', '17:00', '10:00', '14:00', NULL, NULL);


INSERT INTO doctor_specialization (doctor_id, specialization_id)
VALUES
    (101, 6),
    (101, 16);



SELECT setval('public.types_of_visits_id_seq', 305, true);
INSERT INTO types_of_visits (id, type, price, currency, duration, is_active, doctor_id)
VALUES
    (300, 'Neurological MRI Scan', 160.00 , '€', 45, true, 101),
    (301, 'Neurological Consultation', 150.00 , '$', 45, true, 101),
    (302, 'Neurological Stress Test', 200.00 , '$', 60, true, 101),
    (303, 'Psychiatric Consultation', 120.00 , '€', 30, true, 101),
    (304, 'Psychiatric Skin Biopsy', 180.00 , '€', 60, true, 101),
    (305, 'Psychiatric Allergy Testing', 160.00 , '€', 45, true, 101);




SELECT setval('public.visits_id_seq', 223, true);
INSERT INTO visits (id, date, notes, is_cancelled, type_id, client_id)
VALUES
    (201, '2024-09-03 10:00:00', 'Patient complains of frequent headaches.', false, 301, 78),
    (202, '2024-08-08 12:15:00', NULL, false, 302, 21),
    (203, '2024-08-16 15:00:00', 'MRI scan required due to neurological symptoms.', false, 300, 43),
    (204, '2024-12-17 09:30:00', NULL, false, 304, 65),
    (205, '2024-11-25 10:45:00', 'Patient undergoing ongoing chemotherapy treatment.', false, 305, 87),
    (206, '2024-10-22 14:00:00', NULL, false, 304, 19),
    (207, '2024-09-19 08:15:00', 'Post-surgery consultation for psychiatric evaluation.', false, 303, 31),
    (208, '2024-08-24 11:30:00', 'Patient complains of joint pain.', false, 304, 53),
    (209, '2024-08-11 13:45:00', 'Patient undergoing physical therapy session.', false, 305, 75),
    (210, '2024-08-01 10:00:00', NULL, false, 16, 101),
    (211, '2024-09-15 14:30:00', 'Therapy session', false, 17, 101),
    (212, '2024-10-09 09:15:00', NULL, false, 18, 101),
    (213, '2024-08-27 11:00:00', 'Developmental screening', false, 21, 101),
    (214, '2024-09-20 08:45:00', NULL, false, 22, 101),
    (215, '2024-10-14 13:30:00', 'X-ray needed', false, 23, 102),
    (216, '2024-11-11 10:00:00', 'Abdominal pain', false, 24, 102),
    (217, '2024-10-28 10:00:00', NULL, false, 16, 27),
    (218, '2024-11-03 14:30:00', 'Therapy session', false, 17, 102),
    (219, '2024-12-30 09:15:00', NULL, false, 18, 10),
    (220, '2024-10-31 11:00:00', 'Developmental screening', false, 21, 102),
    (221, '2024-11-23 08:45:00', NULL, false, 22, 102),
    (222, '2024-08-09 13:30:00', 'X-ray needed', false, 23, 102),
    (223, '2024-09-12 10:00:00', 'Abdominal pain', false, 24, 102);




SELECT setval('public.reviews_id_seq', 108, true);
INSERT INTO reviews (id, date, rating, description, doctor_id, client_id)
VALUES
    (91, '2021-01-01 08:30:00', 4, 'Exceptionally skilled and caring doctor. Always takes the time to listen to my concerns and provides thorough explanations.', 101, 45),
    (92, '2021-02-15 10:15:00', 3, 'Very informative and helpful. I appreciate the friendly and welcoming atmosphere in the clinic.', 101, 77),
    (93, '2022-03-20 13:45:00', 2, 'The appointment was satisfactory overall, but there were some issues with communication that could be improved.', 101, 32),
    (94, '2022-04-10 14:20:00', 1, 'I expected more empathy from the doctor. The lack of understanding left me feeling disappointed.', 101, 14),
    (95, '2022-05-05 16:00:00', 0, 'The experience was dreadful. Long wait times and poor treatment from the staff.', 101, 91),
    (96, '2023-06-25 11:30:00', 4, 'The care provided was exceptional. The doctor went above and beyond to ensure my comfort and well-being.', 101, 3),
    (97, '2023-07-12 09:45:00', 3, 'I appreciated the compassionate care provided by the doctor. They showed genuine concern for my health.', 101, 56),
    (98, '2023-08-18 17:00:00', 2, 'The service was mediocre. There were some issues with organization and communication.', 101, 27),
    (99, '2023-09-30 14:10:00', 1, 'I left the appointment frustrated with the outcome. My concerns were not adequately addressed.', 101, 68),
    (100, '2021-10-11 12:20:00', 0, 'The visit was appalling. The doctor seemed disinterested and dismissive of my concerns.', 101, 39),
    (101, '2022-01-05 08:50:00', 4, 'Impressive expertise and exceptional care. The doctor demonstrated extensive knowledge during the appointment, addressing all concerns thoroughly. Highly recommended for their professionalism and dedication to patient well-being.', 33, 101),
    (102, '2022-02-20 10:25:00', 3, 'Positive experience with attentive care. The doctor listened attentively and provided helpful advice. Overall, a pleasant visit with thorough explanations.', 61, 101),
    (103, '2022-03-15 13:15:00', 2, 'Fair treatment but room for improvement. While the staff were friendly, the waiting time was longer than expected. The consultation itself was satisfactory, but there is room for improvement in terms of efficiency and communication.', 74, 11),
    (104, '2023-04-08 14:40:00', 1, 'Could improve communication. Although the doctor seemed knowledgeable, there was a lack of clear communication during the appointment. Better communication would greatly improve the patient experience.', 29, 101),
    (105, '2022-05-25 16:30:00', 0, 'Disappointing experience. Unfortunately, the overall experience at the clinic was disappointing due to unprofessional behavior. The atmosphere was tense, and the appointment did not meet expectations.', 96, 102),
    (106, '2022-06-10 11:55:00', 4, 'Exceptional care and professionalism. The doctor provided exceptional care, addressing all concerns with empathy and professionalism. Highly recommended for their exceptional service.', 50, 102),
    (107, '2023-07-22 10:10:00', 3, 'Great bedside manner and clear explanations. The doctor demonstrated great bedside manner and provided clear explanations throughout the appointment. Overall, a positive experience.', 17, 102),
    (108, '2023-08-30 17:20:00', 2, 'Satisfactory service but nothing exceptional. The service at the clinic was satisfactory, but there was nothing particularly exceptional about the experience. The staff were polite, but the overall atmosphere was average.', 83, 102);



SELECT setval('public.leaves_id_seq', 34, true);
INSERT INTO leaves (id, start_date, end_date, doctor_id)
VALUES
    (31, '2025-01-05 08:00:00', '2025-01-10 17:00:00', 101),
    (32, '2025-02-10 09:30:00', '2025-02-15 18:30:00', 101),
    (33, '2024-08-18 00:00:00', '2024-08-23 23:59:59', 101),
    (34, '2024-10-01 00:00:00', '2024-10-05 23:59:59', 101);