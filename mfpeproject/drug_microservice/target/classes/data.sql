INSERT INTO DRUG_DETAIL (drug_id, drug_name, MANUFACTURER_NAME,MANUFACTURED_DATE, EXPIRY_DATE) VALUES ('PR1', 'Paracetamol', 'cysco', parsedatetime('22-11-2024', 'dd-MM-yyyy'), parsedatetime('22-22-2020', 'dd-MM-yyyy'));
INSERT INTO DRUG_DETAIL (drug_id, drug_name, MANUFACTURER_NAME,MANUFACTURED_DATE, EXPIRY_DATE) VALUES ('CR2', 'Crocin', 'mpl', parsedatetime('22-11-2024', 'dd-MM-yyyy'), parsedatetime('22-22-2020', 'dd-MM-yyyy'));
INSERT INTO DRUG_DETAIL (drug_id, drug_name, MANUFACTURER_NAME,MANUFACTURED_DATE, EXPIRY_DATE) VALUES ('PD3', 'Penadol', 'mastermind',parsedatetime('22-11-2024', 'dd-MM-yyyy'), parsedatetime('22-22-2020', 'dd-MM-yyyy'));
INSERT INTO DRUG_DETAIL (drug_id, drug_name, MANUFACTURER_NAME,MANUFACTURED_DATE, EXPIRY_DATE) VALUES ('DD4', 'Dolo', 'rohit',parsedatetime('22-11-2024', 'dd-MM-yyyy'), parsedatetime('22-22-2020', 'dd-MM-yyyy'));


INSERT INTO DRUG_LOCATION (id, location, quantity, drug_id) VALUES('1', 'Chennai', 500, 'PR1');
INSERT INTO DRUG_LOCATION (id, location, quantity, drug_id) VALUES('2', 'Bangalore', 500, 'PR1');
INSERT INTO DRUG_LOCATION (id, location, quantity, drug_id) VALUES('3', 'Pune', 500, 'PR1');
INSERT INTO DRUG_LOCATION (id, location, quantity, drug_id) VALUES('4', 'Hyderabad', 500, 'PR1');
INSERT INTO DRUG_LOCATION (id, location, quantity, drug_id) VALUES('5', 'Chennai', 500, 'CR2');
INSERT INTO DRUG_LOCATION (id, location, quantity, drug_id) VALUES('6', 'Mumbai', 500, 'PD3');
INSERT INTO DRUG_LOCATION (id, location, quantity, drug_id) VALUES('7', 'Kolkata', 500, 'DD4');

