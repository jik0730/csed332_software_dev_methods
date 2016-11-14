INSERT INTO icdcodes(Code, Description, Chronic, URL) VALUES
('250.10', 'Diabetes with ketoacidosis', 'yes',''),
('250.30','Diabetes with other coma', 'yes',''),
('487.00', 'Influenza', 'no',''),
('79.10', 'Echovirus', 'no',''),
('84.50', 'Malaria', 'no',''),
('79.30', 'Coxsackie', 'yes',''),
('11.40', 'Tuberculosis of the lung', 'no',''),
('15.00', 'Tuberculosis of vertebral column', 'no',''),
('42.00', 'Human Immunodeficiency Virus', 'yes',''),
('70.10', 'Viral hepatitis A, infectious', 'yes',''),
('250.00','Acute Lycanthropy', 'yes',''),
('715.09', 'Osteoarthrosis, generalized, multiple sites', 'yes',''),
('72.00','Mumps', 'no','')  ON DUPLICATE KEY UPDATE Code = Code;
INSERT INTO icdcodes(Code, Description, Chronic, URL, Ophthalmology) VALUES
('26.8', 'Cataracts', 'yes','https://nei.nih.gov/health/cataract/cataract_facts','yes'),
('35.30','Age-Related Macular Degeneration', 'yes','https://nei.nih.gov/health/maculardegen/armd_facts','yes'),
('35.001', 'Amblyopia', 'no','https://nei.nih.gov/health/amblyopia/amblyopia_guide','yes'),
('40.89', 'Glaucoma', 'no','https://nei.nih.gov/health/glaucoma/glaucoma_facts','yes') ON DUPLICATE KEY UPDATE Code = Code;
INSERT INTO icdcodes(Code, Description, Chronic, Orthopedic) VALUES
('83.5', 'Anterior cruciate ligament injury', 'no', 'yes'), 
('83.20', 'Meniscus tear', 'no', 'yes'),
('05.44', 'Rheumatoid arthritis of hand', 'no', 'yes'), 
('94.2', 'Chondromalacia', 'no', 'yes'),
('66.7', 'Congenital pes cavus', 'no', 'yes'),
('13.4', 'Whiplash injury', 'no', 'yes') ON DUPLICATE KEY UPDATE Code = Code;