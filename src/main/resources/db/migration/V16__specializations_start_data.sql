SELECT setval('public.specializations_id_seq', 20, true);


INSERT INTO specializations (id, specialization)
VALUES
    (1,'CARDIOLOGY'),
    (2,'DERMATOLOGY'),
    (3,'NEUROLOGY'),
    (4,'ONCOLOGY'),
    (5,'ORTHOPEDICS'),
    (6,'PSYCHIATRY'),
    (7,'PEDIATRICS'),
    (8,'RADIOLOGY'),
    (9,'GASTROENTEROLOGY'),
    (10,'OPHTHALMOLOGY'),
    (11,'UROLOGY'),
    (12,'PULMONOLOGY'),
    (13,'HEMATOLOGY'),
    (14,'ENDOCRINOLOGY'),
    (15,'RHEUMATOLOGY'),
    (16,'NEPHROLOGY'),
    (17,'GYNECOLOGY'),
    (18,'ALLERGOLOGY'),
    (19,'IMMUNOLOGY'),
    (20,'OTOLARYNGOLOGY');
